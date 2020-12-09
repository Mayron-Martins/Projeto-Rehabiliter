/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.adicionais;

import Controller.auxiliar.ConversaoDeDinheiro;
import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
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
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
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
    
    public void adicionarEntrada() throws SQLException, ParseException{
        //Dados Entrada
        int codGasto = verificar.verificarUltimo("tblGastos", "codGasto") +1;
        int codOrcamentario = verificar.verificarUltimo("tblDetOrcamentario", "codBanco")+1;
        String motivo = view.getCampoReferencia().getText();
        
        BigDecimal quantidadeGrande = new BigDecimal(converterDinheiro.converterParaBigDecimal(view.getCampoQuantidade().getText()).toString());
        float quantidade = quantidadeGrande.floatValue();
        
        String formaPagamento = this.retornarFormaPagamento();
        BigDecimal valorGasto = new BigDecimal(converterDinheiro.converterParaBigDecimal(view.getCampoValor().getText()).toString());
        Date dataCadastro = view.getCampoData().getDate();
        
        Gastos gasto = new Gastos(codGasto, motivo, quantidade, formaPagamento, valorGasto, dataCadastro);
        DetOrcamentario orcamentario = new DetOrcamentario(codOrcamentario, "Gastos", formaPagamento, valorGasto, dataCadastro, gasto.getChaveTransacao());
        
        if(motivo.equals("")||quantidade==0||formaPagamento.equals("[Nenhum]")||valorGasto.compareTo(BigDecimal.ZERO)==0
                || dataCadastro == null){
            view.exibeMensagem("Dados Preenchidos Incorretamente!");
        }
        else{
          //Adicionando no Banco
          gastosDao.inserirDados(gasto);
          orcamentarioDao.inserirDados(orcamentario);
          
          ArrayList <Funcionario> funcionarios = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE status = 'Ativo'");
            if(funcionarios!=null){
                this.setarLog(funcionarios, motivo);
            }
          view.exibeMensagem("Sucesso!");
          view.getCampoFormaPagamento().setSelectedIndex(0);
          view.getCampoQuantidade().setText("");
          view.getCampoReferencia().setText("");
          view.getCampoValor().setText("");
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
    
    private LogAçoesFuncionario setarLog(ArrayList <Funcionario> funcionarios, String motivo){
        Funcionario funcionario = funcionarios.get(0);
        Date dataEvento = new Date();
        LogAçoesFuncionario logAcao = new LogAçoesFuncionario(funcionario.getCodBanco(), dataEvento, "Cadastro de Gasto", "Cadastrou o gasto "+motivo);
        return logAcao;
    } 
}
