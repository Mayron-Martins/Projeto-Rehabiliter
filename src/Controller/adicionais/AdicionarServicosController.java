/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.adicionais;

import Controller.auxiliar.ConversaoDeDinheiro;
import Controller.auxiliar.LogsSystem;
import Controller.auxiliar.VerificarCodigoNoBanco;
import Dao.FuncionarioDao;
import Dao.LogAçoesFuncionarioDao;
import Dao.ServicosDao;
import Model.Funcionario;
import Model.auxiliar.LogAçoesFuncionario;
import Model.auxiliar.Servicos;
import View.LoginFuncionario;
import View.LoginGerente;
import View.ServicosAdicionar;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Mayro
 */
public class AdicionarServicosController {
    private final ServicosAdicionar view;
    private final ServicosDao servicosDao = new ServicosDao();
    private final FuncionarioDao funcionarioDao = new FuncionarioDao();
    private final LogAçoesFuncionarioDao logDao = new LogAçoesFuncionarioDao();
    private final ConversaoDeDinheiro converterDinheiro = new ConversaoDeDinheiro();
    
    public AdicionarServicosController(ServicosAdicionar view) {
        this.view = view;
    }
    
    public void adicionarServico(){
        //Pegando Dados Da tela
        String nomeServico = view.getNomeServico().getText();
        String periodo= this.retornarPeriodo();
        String metodoDePagamento = "Unica";
        String situacao = "Aberto";

        BigDecimal valor = converterDinheiro.converterParaBigDecimal(view.getValorUnico().getText());
        BigDecimal valorBoleto = valor;
        BigDecimal valorAPrazoCredito = valor;
        BigDecimal valorAPrazoDebito = valor;
        
        if(!view.getRadioFormaPag1().isSelected()){
            metodoDePagamento = "Diversos";
            valor = converterDinheiro.converterParaBigDecimal(view.getValorDinheiro().getText());
            valorBoleto = converterDinheiro.converterParaBigDecimal(view.getValorBoleto().getText());
            valorAPrazoCredito = converterDinheiro.converterParaBigDecimal(view.getValorCredito().getText());
            valorAPrazoDebito = converterDinheiro.converterParaBigDecimal(view.getValorDebito().getText());
        }
        int periodDays = this.periodDays();

        Servicos servico = new Servicos(nomeServico, periodo, metodoDePagamento, valor, valorBoleto, valorAPrazoCredito, valorAPrazoDebito, periodDays, situacao);
        //Inserindo Dados
        if(nomeServico.equals("")|| periodo.equals("[Nenhum]")||periodDays==0){
         view.exibeMensagem("Campos Preenchidos Incorretamente");
        } else{

            if(this.verificarPeriodo(periodDays)){
                servicosDao.inserirDados(servico);

                this.setarLog("Cadastro de Serviço", "Cadastrou o serviço "+nomeServico);

                view.exibeMensagem("Sucesso!");
                limparCampos();
            }
            else{
                view.exibeMensagem("Por motivos de compatibilidade, esse período de dias não pôde ser aceito!");
            }

        }
    }
    
    private String retornarPeriodo(){
        if(view.getComboPeriodo().isEnabled()){
            return view.getComboPeriodo().getSelectedItem().toString();
        }
        else{
            return view.getCampoOutroTipo().getText();
        }
    }
    
    public void preencherComboPeriodo(){
        ArrayList <Servicos> servicos = servicosDao.selecionarTodosServicos();
        
        String periodoServicoAnterior="";
        String periodoServico;
        if(servicos!=null){
            view.getComboPeriodo().removeAllItems();
            view.getComboPeriodo().addItem("[Nenhum]");
            view.getComboPeriodo().addItem("Diária");
            view.getComboPeriodo().addItem("Semanal");
            view.getComboPeriodo().addItem("Mensal");
            view.getComboPeriodo().addItem("Trimestral");
            view.getComboPeriodo().addItem("Quadrimestral");
            view.getComboPeriodo().addItem("Semestral");
            view.getComboPeriodo().addItem("Anual");
            
            for(int linhas=0; linhas<servicos.size();linhas++){
                periodoServico = servicos.get(linhas).getPeriodo();
                
                if(!periodoServico.equals(periodoServicoAnterior)){
                    if(!periodoServico.equals("[Nenhum]")&&!periodoServico.equals("Diária")&&!periodoServico.equals("Semanal")
                        &&!periodoServico.equals("Mensal")&&!periodoServico.equals("Trimestral")&&!periodoServico.equals("Quadrimestral")
                        &&!periodoServico.equals("Semestral")&&!periodoServico.equals("Anual")){
                        view.getComboPeriodo().addItem(periodoServico);
                    }
                }
                periodoServicoAnterior=periodoServico;
            }
        }
    }
    
    
    private int periodDays(){
        if(view.getComboPeriodo().isEnabled()){
            String tipo = view.getComboPeriodo().getSelectedItem().toString();
            switch(tipo){
                case "Diária":
                    return 1;
                case "Semanal":
                    return 7;
                case "Mensal":
                    return 30;
                case "Trimestral":
                    return 90;
                case "Quadrimestral":
                    return 120;
                case "Semestral":
                    return 150;
                case "Anual":
                    return 365;
            }
            ArrayList <Servicos> servicos = servicosDao.pesquisarServicos("SELECT * FROM tblServicos WHERE periodo = "+tipo);
            if(servicos!=null){
                return servicos.get(0).getPeriodDays();
            }
        }
        else{
            
        }
        
        return this.periodOfOutro();
    }
    
    private int periodOfOutro(){
        String period = view.getComboDias().getSelectedItem().toString();
        int dias=0;
        if(view.getCampoDias() !=null){
         dias = Integer.parseInt(view.getCampoDias().getText());
        }
        
        if(period.equals("M")){
            return 30*dias;
        }
        if(period.equals("A")){
            return 365*dias;
        }
        return dias;
    }
    
    private boolean verificarPeriodo(int period){
        BigDecimal periodDays = new BigDecimal(period);
        
        String mensal = periodDays.divide(new BigDecimal(30), 2, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
        String anual = periodDays.divide(new BigDecimal(365), 3, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();

        
        boolean resultadoMensal = mensal.matches("[0-9]*");
        boolean resultadoAnual = anual.matches("[0-9]*");
        
        if(resultadoMensal||resultadoAnual){
            return true;
        }
        else{
            return period<30;
        }
    }
    
    
    public void limparCampos(){
        //Limpando Campos
        view.getNomeServico().setText("");
        preencherComboPeriodo();
        view.getComboPeriodo().setSelectedIndex(0);
        view.getCampoOutroTipo().setText("Outro");
        view.getCampoDias().setText(""); 
        view.getValorUnico().setText("");
        view.getValorDinheiro().setText("");
        view.getValorBoleto().setText("");
        view.getValorCredito().setText("");
        view.getValorDebito().setText("");
        view.getRadioFormaPag1().setSelected(true);
    }
    
    private void gerarLog(Throwable erro){
        LogsSystem gerarLog = new LogsSystem();
        gerarLog.gravarErro(erro);
        gerarLog.close();
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
