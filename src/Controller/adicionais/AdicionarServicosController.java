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
import Dao.ServicosDao;
import Model.Funcionario;
import Model.auxiliar.LogAçoesFuncionario;
import Model.auxiliar.Servicos;
import View.ServicosAdicionar;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mayro
 */
public class AdicionarServicosController {
    private final ServicosAdicionar view;
    private final ServicosDao servicosDao = new ServicosDao();
    private final FuncionarioDao funcionarioDao = new FuncionarioDao();
    private final LogAçoesFuncionarioDao logDao = new LogAçoesFuncionarioDao();
    private final VerificarCodigoNoBanco verificar = new VerificarCodigoNoBanco();
    private final ConversaoDeDinheiro converterDinheiro = new ConversaoDeDinheiro();
    
    public AdicionarServicosController(ServicosAdicionar view) {
        this.view = view;
    }
    
    public void adicionarServico() throws SQLException, ParseException{
        //Pegando Dados Da tela
        int codBancoServico = verificar.verificarUltimo("tblServicos", "codServico")+1;
        String nomeServico = view.getNomeServico().getText();
        String periodo= this.retornarPeriodo();
        String metodoDePagamento = view.getMetodoPagamento().getSelectedItem().toString();
        
        BigDecimal valor = new BigDecimal(0);
        BigDecimal valorAVista= new BigDecimal(0);
        BigDecimal valorBoleto= new BigDecimal(0);
        BigDecimal valorAPrazoCredito= new BigDecimal(0);
        BigDecimal valorAPrazoDebito= new BigDecimal(0);
        
        String valorDinheiro = converterDinheiro.converterParaBigDecimal(view.getValorDinheiro().getText()).toString();
        if(metodoDePagamento.equals("[Nenhuma]")){valor = new BigDecimal(valorDinheiro);}
        if(metodoDePagamento.equals("Dinheiro")){valorAVista = new BigDecimal(valorDinheiro);}
        if(metodoDePagamento.equals("Boleto")){valorBoleto = new BigDecimal(valorDinheiro);}
        if(metodoDePagamento.equals("Cartão de Crédito")){valorAPrazoCredito = new BigDecimal(valorDinheiro);}
        if(metodoDePagamento.equals("Cartão de Débito")){valorAPrazoDebito = new BigDecimal(valorDinheiro);}
        int periodDays = this.periodDays();
        
        Servicos servico = new Servicos(codBancoServico, nomeServico, periodo, metodoDePagamento, valor, valorAVista, valorBoleto, valorAPrazoCredito, valorAPrazoDebito, periodDays);
        //Inserindo Dados
        if(nomeServico.equals("")|| periodo.equals("[Nenhum]")||periodDays==0){
         view.exibeMensagem("Campos Preenchidos Incorretamente");
        } else{
            
            if(this.verificarPeriodo(periodDays)){
                servicosDao.inserirDados(servico);

                ArrayList <Funcionario> funcionarios = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE status = 'Ativo'");
                if(funcionarios!=null){
                    this.setarLog(funcionarios, nomeServico);
                }
                view.exibeMensagem("Sucesso!");
                //Limpando Campos
                view.getNomeServico().setText("");
                preencherComboPeriodo();
                view.getComboPeriodo().setSelectedIndex(0);
                view.getMetodoPagamento().setSelectedIndex(0);
                view.getValorDinheiro().setText("");
                view.getCampoOutroTipo().setText("Outro");
                view.getCampoDias().setText("");
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
    
    public void preencherComboPeriodo() throws SQLException{
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
    
    private LogAçoesFuncionario setarLog(ArrayList <Funcionario> funcionarios, String nomeServico){
        Funcionario funcionario = funcionarios.get(0);
        Date dataEvento = new Date();
        LogAçoesFuncionario logAcao = new LogAçoesFuncionario(funcionario.getCodBanco(), dataEvento, "Cadastro de Serviço", "Cadastrou o serviço "+nomeServico);
        return logAcao;
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
            try {
                ArrayList <Servicos> servicos = servicosDao.pesquisarServicos("SELECT * FROM tblServicos WHERE periodo = "+tipo);
                if(servicos!=null){
                    return servicos.get(0).getPeriodDays();
                }
            } catch (SQLException ex) {
                Logger.getLogger(AdicionarServicosController.class.getName()).log(Level.SEVERE, null, ex);
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
}
