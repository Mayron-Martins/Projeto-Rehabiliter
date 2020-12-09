/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.auxiliar.ConversaoDeDinheiro;
import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Dao.EnderecoFuncionarioDao;
import Dao.FuncionarioDao;
import Dao.LogAçoesFuncionarioDao;
import Dao.ProdutosDao;
import Model.Funcionario;
import Model.Produtos;
import Model.auxiliar.LogAçoesFuncionario;
import View.ProdutosView;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mayro
 */
public class ProdutosController {
    private final ProdutosView view;
    private final DefaultTableModel tabelaDeProdutos;
    private final ProdutosDao produtosDao = new ProdutosDao();
    private final EnderecoFuncionarioDao enderecoDao = new EnderecoFuncionarioDao();
    private final FuncionarioDao funcionarioDao = new FuncionarioDao();
    private final LogAçoesFuncionarioDao logDao = new LogAçoesFuncionarioDao();
    private final ConversaoDeDinheiro converterDinheiro = new ConversaoDeDinheiro();
    private final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();

    public ProdutosController(ProdutosView view) {
        this.view = view;
        this.tabelaDeProdutos = (DefaultTableModel) view.getTabelaProdutos().getModel();
    }
    
    
    //Limpar tabela
    public void limparTabela(){
        int quantLinhas = this.view.getTabelaProdutos().getRowCount();
        for(int quant=0; quant<quantLinhas; quant++){
            this.tabelaDeProdutos.removeRow(0);
        }
    }
        
    //Lista todas as turmas 
    public void listarProdutos() throws SQLException, ParseException, Exception{
        ArrayList <Produtos> produtos= this.produtosDao.selecionarTodosProdutos();
        this.buscas(produtos);
    }
    
    public void editarProdutos() throws SQLException, ParseException, Exception{
        if(this.view.getTabelaProdutos().getSelectedRow()!= -1){
            //Dados Alunos
            int linhaSelecionada = this.view.getTabelaProdutos().getSelectedRow();
            int codProduto = Integer.parseInt(tabelaDeProdutos.getValueAt(linhaSelecionada, 0).toString());
            String nome = this.tabelaDeProdutos.getValueAt(linhaSelecionada, 1).toString();
            BigDecimal valorBruto = new BigDecimal(converterDinheiro.converterParaBigDecimal(this.tabelaDeProdutos.getValueAt(linhaSelecionada, 2).toString()).toString());
            float quantidade = valorBruto.floatValue();
           BigDecimal valorProduto = new BigDecimal(converterDinheiro.converterParaBigDecimal(this.tabelaDeProdutos.getValueAt(linhaSelecionada, 3).toString()).toString());
            
            Produtos produtoAnterior = this.produtoAnterior(codProduto);
            
            Produtos produto = new Produtos(codProduto, nome, "", "", quantidade, produtoAnterior.getDescricao(), new BigDecimal("0"), "", valorProduto, 0);
        
//Inserindo Dados
        if(nome.equals("")|| quantidade<0||valorProduto.compareTo(BigDecimal.ZERO)<=0){
         view.exibeMensagem("Campos Preenchidos Incorretamente");
        } else{
            produtosDao.atualizarDados(produto);
            
            ArrayList <Funcionario> funcionarios = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE status = 'Ativo'");
            if(funcionarios!=null){
                String acao = "Edição de Dados de Produto";
                String descricao = "Editou os dados do produto "+nome;
                this.setarLog(funcionarios, acao, descricao);
            }
            view.exibeMensagem("Sucesso!");
            //Limpando Campos
            listarProdutos();
        }
        }
        
        else{this.view.exibeMensagem("Erro, Nenhum Produto Selecionado!");}
        
    
    }
    
    
    public void removerProduto() throws SQLException, ParseException, Exception{
        if(this.view.getTabelaProdutos().getSelectedRow()!= -1){
            int linhaSelecionada = this.view.getTabelaProdutos().getSelectedRow();
                int codProduto = Integer.parseInt(tabelaDeProdutos.getValueAt(linhaSelecionada, 0).toString());
                String nomeProduto = tabelaDeProdutos.getValueAt(linhaSelecionada, 1).toString();
                produtosDao.removerProduto(codProduto);
                
                ArrayList <Funcionario> funcionarios = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE status = 'Ativo'");
                if(funcionarios!=null){
                    String acao = "Remoção de Produto";
                    String descricao = "Remoção do produto "+nomeProduto;
                    this.setarLog(funcionarios, acao, descricao);
                }
                this.view.exibeMensagem("Sucesso");
                listarProdutos();
        }
        else{this.view.exibeMensagem("Erro, Nenhum Produto Selecionado!");}
    }
    
    //Buscar Produtos no campo de busca
    public void buscarProdutos() throws Exception{
        String produtoPesquisa = view.getCampoPesquisa().getText();
        ArrayList <Produtos> produtos = produtosDao.pesquisarPorNome(produtoPesquisa);
        if(produtoPesquisa.equals("")){listarProdutos();}
        else{this.buscas(produtos);}        
    }
        
    
    //Listar
    public void listar() throws ParseException, Exception{
        String comboListar = view.getComboListar().getSelectedItem().toString();
        switch(comboListar){
            case "Todos":
                listarProdutos();
            break;
        }
    }

    
    private Produtos produtoAnterior(int codProduto) throws SQLException, ParseException{
         return  produtosDao.pesquisarProdutos("SELECT * FROM tblProdutos WHERE codProduto = "+codProduto).get(0);
    }
    
    private void buscas(ArrayList <Produtos> listar) throws Exception{
        ArrayList<Produtos> produtos = listar; 
        
        if(produtos==null){view.exibeMensagem("Produto Não Encontrado!"); limparTabela();}
        else{
            limparTabela();
            for(int linhas = 0; linhas<produtos.size(); linhas++){
            Object[] dadosDaTabelaFuncionarios = {produtos.get(linhas).getCodBanco(), 
            produtos.get(linhas).getNomeProduto(),produtos.get(linhas).getQuantidade(), 
            produtos.get(linhas).getValorDeVenda()};
            this.tabelaDeProdutos.addRow(dadosDaTabelaFuncionarios);

            }
        }
    }
    
    private LogAçoesFuncionario setarLog(ArrayList <Funcionario> funcionarios, String acao, String descricao){
        Funcionario funcionario = funcionarios.get(0);
        Date dataEvento = new Date();
        LogAçoesFuncionario logAcao = new LogAçoesFuncionario(funcionario.getCodBanco(), dataEvento, acao, descricao);
        return logAcao;
    }
}
