/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.adicionais;

import Controller.auxiliar.ConversaoDeDinheiro;
import Controller.auxiliar.VerificarCodigoNoBanco;
import Dao.FuncionarioDao;
import Dao.LogAçoesFuncionarioDao;
import Dao.ProdutosDao;
import Model.Funcionario;
import Model.Produtos;
import Model.auxiliar.LogAçoesFuncionario;
import View.LoginFuncionario;
import View.LoginGerente;
import View.ProdutosAdicionar;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Mayro
 */
public class AdicionarProdutosController {
    private final ProdutosAdicionar view;
    private final ProdutosDao produtosDao = new ProdutosDao();
    private final FuncionarioDao funcionarioDao = new FuncionarioDao();
    private final LogAçoesFuncionarioDao logDao = new LogAçoesFuncionarioDao();
    private final VerificarCodigoNoBanco verificar = new VerificarCodigoNoBanco();
    private final ConversaoDeDinheiro converterDinheiro = new ConversaoDeDinheiro();
    

    public AdicionarProdutosController(ProdutosAdicionar view) {
        this.view = view;
    }
    
        public void adicionarProduto(){
            //Pegando Dados Da tela
            int codBanco;
            if(view.getCampoCodigo().getText().equals("")){codBanco=0;}
            else{codBanco =Integer.parseInt(view.getCampoCodigo().getText());}
            String nome = view.getCampoNome().getText();
            String tipo= "";
            String unMedida = "";
            BigDecimal quantidadeGrande = new BigDecimal(converterDinheiro.converterParaBigDecimal(view.getCampoQuantidade().getText()).toString());
            float quantidade = quantidadeGrande.floatValue();
            String descricao = view.getCampoDescricao().getText();
            BigDecimal valorDeCompra = new BigDecimal("0");
            String dataCompra = "";
            BigDecimal valorDeVenda = new BigDecimal(converterDinheiro.converterParaBigDecimal(view.getCampoValor().getText()).toString());
            int chaveDeLote = 0;

            Produtos produto = new Produtos(codBanco, nome, tipo, unMedida, quantidade, descricao, valorDeCompra, dataCompra, valorDeVenda,chaveDeLote);
            //Inserindo Dados
            if(nome.equals("")|| codBanco<=0 || valorDeVenda.compareTo(BigDecimal.ZERO)<=0||this.verificarExistenciaNoBanco(codBanco)){
             view.exibeMensagem("Campos Preenchidos Incorretamente");
            } else{
                produtosDao.inserirDados(produto);

                
                this.setarLog("Cadastro de Produto", "Cadastrou o produto "+nome);
                
                view.exibeMensagem("Sucesso!");
                //Limpando Campos
                limparCampos();
            }

        }
    
    private boolean verificarExistenciaNoBanco(int codBanco){
        ArrayList <Produtos> produtos = produtosDao.pesquisarProdutos("SELECT * FROM tblProdutos WHERE codProduto = "+codBanco);
        if(produtos==null){return false;}
        view.exibeMensagem("Verifique o campo Código do Produto!");
        return true;   
    }
    
    public void limparCampos(){
        view.getCampoNome().setText("");
        view.getCampoDescricao().setText("");
        view.getCampoQuantidade().setText("");
        view.getCampoValor().setText("");
        view.getCampoCodigo().setText("");
    }
    
    public void sairTela(){
        funcionarioDao.atualizarStatusAll();
        Funcionario funcionario = this.setarLog("Saída do Sistema", null);
        view.getParent().dispose();
        if(funcionario==null||!funcionario.getCargo().equals("Gerente")){
            LoginFuncionario jump = new LoginFuncionario();
            jump.setVisible(true);
        }
        else{
            LoginGerente jump = new LoginGerente();
            jump.setVisible(true);
        }
    }
    
    private Funcionario setarLog(String acao, String referencia){
        ArrayList <Funcionario> funcionarios = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE status = 'Ativo'");
        if(funcionarios!=null){
            Funcionario funcionario = funcionarios.get(0);
            Date dataEvento = new Date();
            LogAçoesFuncionario logAcao = new LogAçoesFuncionario(funcionario.getCodBanco(), dataEvento, acao, referencia);
            return funcionario;
        }
        return null;
    } 
}
