/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Dao.AlunosDao;
import Dao.ProdutosDao;
import Dao.TurmasDao;
import Model.Aluno;
import Model.Produtos;
import Model.auxiliar.Turmas;
import View.Caixa;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mayro
 */
public class CaixaController {
    private final Caixa view;
    private final DefaultTableModel tabelaDeClientes;
    private final DefaultTableModel tabelaDeProdutos;
    private final DefaultTableModel tabelaDeCarrinho;
    private final AlunosDao alunosDao = new AlunosDao();
    private final TurmasDao turmasDao = new TurmasDao();
    private final ProdutosDao produtosDao = new ProdutosDao();

    public CaixaController(Caixa view) {
        this.view = view;
        this.tabelaDeClientes = (DefaultTableModel) view.getTabelaDeClientes().getModel();
        this.tabelaDeProdutos = (DefaultTableModel) view.getTabelaDeProdutos().getModel();
        this.tabelaDeCarrinho = (DefaultTableModel) view.getTabelaDeCarrinho().getModel();
    }
    
        //Limpar tabela
    public void limparTabelaClientes(){
        int quantLinhas = this.view.getTabelaDeClientes().getRowCount();
        for(int quant=0; quant<quantLinhas; quant++){
            this.tabelaDeClientes.removeRow(0);
        }
    }
    
    public void limparTabelaProdutos(){
        int quantLinhas = this.view.getTabelaDeProdutos().getRowCount();
        for(int quant=0; quant<quantLinhas; quant++){
            this.tabelaDeProdutos.removeRow(0);
        }
    }
       
    public void limparTabelaCarrinho(){
        int quantLinhas = this.view.getTabelaDeCarrinho().getRowCount();
        for(int quant=0; quant<quantLinhas; quant++){
            this.tabelaDeCarrinho.removeRow(0);
        }
    }
    
    //Funções para Tratamento de Clientes
    public void setarTabelaClientes() throws Exception{
        String alunoPesquisa = view.getCampoCliente().getText();
        ArrayList <Aluno> alunos = alunosDao.pesquisarPorNome(alunoPesquisa);
        if(alunoPesquisa.equals("")){listarAlunos();}
        else{this.buscasClientes(alunos);}        
    }
    
    public void listarAlunos() throws Exception{
        ArrayList <Aluno> alunos = this.alunosDao.selecionarTodosAlunos();
        this.buscasClientes(alunos);
    }
    
    private void buscasClientes(ArrayList <Aluno> listar) throws Exception{
        ArrayList<Turmas> turmas = new ArrayList<>();
        ArrayList <Aluno> alunos = listar;
        

        if(alunos==null){view.exibeMensagem("Cliente Não Encontrado!"); limparTabelaClientes();}
        else{
            limparTabelaClientes();
            for(int linhas = 0; linhas<alunos.size(); linhas++){
            turmas = this.turmasDao.pesquisarTurmas("SELECT * FROM tblTurmas WHERE codTurma = "+
                    alunos.get(linhas).getTurma());

            //Inserindo dados na tabela de alunos
            String subgrupo="";
            if(turmas.get(0).getSubgrupo()!=null){subgrupo = "-"+turmas.get(0).getSubgrupo();}


            Object[] dadosDaTabelaAlunos = {alunos.get(linhas).getCodBanco(), 
            alunos.get(linhas).getNome(),turmas.get(0).getCodBanco()+"."+turmas.get(0).getNomeTurma()+subgrupo};
            this.tabelaDeClientes.addRow(dadosDaTabelaAlunos);
            }
        }
    }
    
    //Fim das Funções para Clientes
    
    //Funções para Tratamento de Produtos
    //Buscar Produtos no campo de busca
    public void setarTabelaProdutos() throws Exception{
        if(view.getCampoProdutoNome().isVisible()){
            String produtoPesquisa = view.getCampoProdutoNome().getText();
            if(produtoPesquisa.equals("")){listarProdutos();}
            else{
                ArrayList <Produtos> produtos = produtosDao.pesquisarPorNome(produtoPesquisa);
                this.buscasProdutos(produtos);}
        }
        
        else{
            String produtoPesquisa = view.getCampoProdutoCodigo().getText();
            if(produtoPesquisa.equals("")){listarProdutos();}
            else{ArrayList <Produtos> produtos = produtosDao.pesquisarPorID(produtoPesquisa);
            this.buscasProdutos(produtos);}
        }
              
    }    
    
    public void listarProdutos() throws SQLException, ParseException, Exception{
        ArrayList <Produtos> produtos= this.produtosDao.selecionarTodosProdutos();
        this.buscasProdutos(produtos);
    }
    
    private void buscasProdutos(ArrayList <Produtos> listar) throws Exception{
        ArrayList<Produtos> produtos = listar; 
        
        if(produtos==null){view.exibeMensagem("Produto Não Encontrado!"); limparTabelaProdutos();}
        else{
            limparTabelaProdutos();
            for(int linhas = 0; linhas<produtos.size(); linhas++){
            Object[] dadosDaTabelaFuncionarios = {produtos.get(linhas).getCodBanco(), 
            produtos.get(linhas).getNomeProduto(), produtos.get(linhas).getValorDeVenda(),
            produtos.get(linhas).getQuantidade()};
            this.tabelaDeProdutos.addRow(dadosDaTabelaFuncionarios);
            }
        }
    }
    
    //Fim das Funções para Clientes
}
