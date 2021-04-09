/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.adicionais;

import Controller.auxiliar.ConversaoDeDinheiro;
import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Controller.auxiliar.LogsSystem;
import Controller.auxiliar.VerificarCodigoNoBanco;
import Dao.DetOrcamentarioDao;
import Dao.FuncionarioDao;
import Dao.GastosDao;
import Dao.LogAçoesFuncionarioDao;
import Model.Funcionario;
import Model.auxiliar.DetOrcamentario;
import Model.auxiliar.Gastos;
import Model.auxiliar.LogAçoesFuncionario;
import View.FinanceiroPlanodeContraAdc;
import View.LoginFuncionario;
import View.LoginGerente;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Mayro
 */
public class AdicionarGastosController {
    private final FinanceiroPlanodeContraAdc view;
    private final GastosDao gastosDao = new GastosDao();
    private final DetOrcamentarioDao orcamentarioDao = new DetOrcamentarioDao();
    private final FuncionarioDao funcionarioDao = new FuncionarioDao();
    private final LogAçoesFuncionarioDao logDao = new LogAçoesFuncionarioDao();
    private final VerificarCodigoNoBanco verificar = new VerificarCodigoNoBanco();
    private final ConversaoDeDinheiro converterDinheiro = new ConversaoDeDinheiro();
    private final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();

    public AdicionarGastosController(FinanceiroPlanodeContraAdc view) {
        this.view = view;
    }
    
    public void adicionarEntrada(){
        try{
            //Dados Entrada
            int codGasto = (int) (verificar.verificarUltimo("tblGastos", "codGasto") +1);
            String motivo = view.getCampoReferencia().getText();

            BigDecimal quantidadeGrande = new BigDecimal(converterDinheiro.converterParaBigDecimal(view.getCampoQuantidade().getText()).toString());
            float quantidade = quantidadeGrande.floatValue();

            String formaPagamento = this.retornarFormaPagamento();
            BigDecimal valorGasto = new BigDecimal(converterDinheiro.converterParaBigDecimal(view.getCampoValor().getText()).toString());
            Date dataCadastro = view.getCampoData().getDate();

            Gastos gasto = new Gastos(motivo, quantidade, formaPagamento, valorGasto, dataCadastro);
            DetOrcamentario orcamentario = new DetOrcamentario("Gastos", formaPagamento, valorGasto, dataCadastro, gasto.getChaveTransacao());

            if(motivo.equals("")||quantidade==0||formaPagamento.equals("[Nenhum]")||valorGasto.compareTo(BigDecimal.ZERO)==0
                    || dataCadastro == null){
                view.exibeMensagem("Dados Preenchidos Incorretamente!");
            }
            else{
              //Adicionando no Banco
              gastosDao.inserirDados(gasto);
              orcamentarioDao.inserirDados(orcamentario);

              
            this.setarLog("Cadastro de Gasto", "Cadastrou o gasto "+motivo);
                
              view.exibeMensagem("Sucesso!");
              limparCampos();
            }
        } catch (SQLException ex) {
            gerarLog(ex);
            view.exibeMensagem("Não foi possível salvar o Gasto corretamente!");
            limparCampos();
        }
        
    }
    
    private String retornarFormaPagamento(){
        int valorSelecionado = view.getCampoFormaPagamento().getSelectedIndex();
        
        switch(valorSelecionado){
            case 1:
                return "Dinheiro";
            case 2:
                return "Boleto";
            case 3:
                return "Crédito";
            case 4:
                return "Débito";
        }
        return "[Nenhum]";
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
    
    public void limparCampos(){
        view.getCampoFormaPagamento().setSelectedIndex(0);
        view.getCampoQuantidade().setText("");
        view.getCampoReferencia().setText("");
        view.getCampoValor().setText("");
    }
    
    private Funcionario setarLog(String acao, String motivo){
        ArrayList <Funcionario> funcionarios = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE status = 'Ativo'");
        if(funcionarios!=null){
            Funcionario funcionario = funcionarios.get(0);
            Date dataEvento = new Date();
            LogAçoesFuncionario logAcao = new LogAçoesFuncionario(funcionario.getCodBanco(), dataEvento, acao, motivo);
            return funcionario;
        }
        return null;
    }
    
    private void gerarLog(Throwable erro){
        LogsSystem gerarLog = new LogsSystem();
        gerarLog.gravarErro(erro);
        gerarLog.close();
    }
}
