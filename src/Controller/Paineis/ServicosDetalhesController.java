/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Paineis;

import Controller.ServicosController;
import Model.auxiliar.Servicos;
import View.Paineis.ServicosDetalhes;
import View.ServicosView;
import java.awt.Dialog;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 *
 * @author Mayro
 */
public class ServicosDetalhesController extends ServicosController{
    private final ServicosDetalhes viewSecundaria;
    
    public ServicosDetalhesController(ServicosView view, ServicosDetalhes viewSecundaria) {
        super(view);
        this.viewSecundaria = viewSecundaria;
    }
    
    public void selecionarTabela(){
      if(this.view.getTabelaServicos().getSelectedRow()!=-1){
        //Número da linha selecionada
          preencherComboPeriodo();
          int linhaSelecionada = this.view.getTabelaServicos().getSelectedRow();
          int codServico = Integer.parseInt(tabelaDeServicos.getValueAt(linhaSelecionada, 0).toString());

          Servicos servico = servicosDao.pesquisarServicos("SELECT * FROM tblServicos WHERE codServico = "+codServico).get(0);

          viewSecundaria.getNomeServico().setText(servico.getNome());
          viewSecundaria.getComboPeriodo().setSelectedItem(servico.getPeriodo());
          viewSecundaria.getCampoDias().setText(servico.getPeriodDays()+"");

          inserirMetodoPagamento(servico);

          if(!viewSecundaria.isVisible()){
              alterarLocalizacaoViews(true);
          }
      }
    }
    
    public void releasedTable(){
        if(viewSecundaria.isVisible()){
           limparCampos();
           selecionarTabela();
        }
    }
    
    private void inserirMetodoPagamento(Servicos servico){
        if(servico.getFormaPagamento().equals("Único")){
            viewSecundaria.getRadioFormaPag1().setSelected(true);
            viewSecundaria.formaPagamento(true);
            viewSecundaria.getValorUnico().setText(servico.getValor().toString());
            viewSecundaria.getValorDinheiro().setText(servico.getValor().toString());
            viewSecundaria.getValorBoleto().setText(servico.getValor().toString());
            viewSecundaria.getValorCredito().setText(servico.getValor().toString());
            viewSecundaria.getValorDebito().setText(servico.getValor().toString());
        }else{
            viewSecundaria.formaPagamento(false);
            viewSecundaria.getValorDinheiro().setText(servico.getValor().toString());
            viewSecundaria.getValorBoleto().setText(servico.getValorBoleto().toString());
            viewSecundaria.getValorCredito().setText(servico.getValorPrazoCredito().toString());
            viewSecundaria.getValorDebito().setText(servico.getValorPrazoDebito().toString());
        }
    }
    
    @Override
    public void editarServico(){
        int linhaSelecionada = view.getTabelaServicos().getSelectedRow();
        int codServico = Integer.parseInt(tabelaDeServicos.getValueAt(linhaSelecionada, 0).toString());
        String nomeServico = viewSecundaria.getNomeServico().getText();
        String periodo = retornarPeriodo();
        String metodoDePagamento = "Único";
        String situacao = tabelaDeServicos.getValueAt(linhaSelecionada, 2).toString();
        
        BigDecimal valor = converterDinheiro.converterParaBigDecimal(viewSecundaria.getValorUnico().getText());
        BigDecimal valorBoleto = valor;
        BigDecimal valorAPrazoCredito = valor;
        BigDecimal valorAPrazoDebito = valor;
        
        if(!viewSecundaria.getRadioFormaPag1().isSelected()){
            metodoDePagamento = "Diversos";
            valor = converterDinheiro.converterParaBigDecimal(viewSecundaria.getValorDinheiro().getText());
            valorBoleto = converterDinheiro.converterParaBigDecimal(viewSecundaria.getValorBoleto().getText());
            valorAPrazoCredito = converterDinheiro.converterParaBigDecimal(viewSecundaria.getValorCredito().getText());
            valorAPrazoDebito = converterDinheiro.converterParaBigDecimal(viewSecundaria.getValorDebito().getText());
        }
        int periodDays = this.periodDays();

        Servicos servico = new Servicos(codServico, nomeServico, periodo, metodoDePagamento, valor, valorBoleto, valorAPrazoCredito, valorAPrazoDebito, periodDays, situacao);
        //Inserindo Dados
        if(nomeServico.equals("")|| periodo.equals("[Nenhum]")||periodDays==0){
         view.mensagemCritica("Campos Preenchidos Incorretamente", "Aviso");
        } else{
            if(this.verificarPeriodo(periodDays)){
                servicosDao.atualizarDados(servico);
                viewSecundaria.getCampoDias().setText(periodDays+"");
                setarLog("Edição de Serviços", "Edição do serviço "+nomeServico);
                listarServicos();
                view.getTabelaServicos().addRowSelectionInterval(linhaSelecionada, linhaSelecionada);
                view.exibeMensagem("Sucesso!");
                viewSecundaria.trocaBotoes(false);
            }
            else{
                view.mensagemCritica("Por motivos de compatibilidade, esse período de dias não pôde ser aceito!", "Aviso");
            }

        } 
    }
    
    private String retornarPeriodo(){
        if(viewSecundaria.getComboPeriodo().isEnabled()){
            return viewSecundaria.getComboPeriodo().getSelectedItem().toString();
        }
        else{
            return viewSecundaria.getCampoOutroTipo().getText();
        }
    }
    
    public void preencherComboPeriodo(){
        ArrayList <Servicos> servicos = servicosDao.selecionarTodosServicos();
        
        String periodoServicoAnterior="";
        String periodoServico;
        if(servicos!=null){
            viewSecundaria.getComboPeriodo().removeAllItems();
            viewSecundaria.getComboPeriodo().addItem("[Nenhum]");
            viewSecundaria.getComboPeriodo().addItem("Diária");
            viewSecundaria.getComboPeriodo().addItem("Semanal");
            viewSecundaria.getComboPeriodo().addItem("Mensal");
            viewSecundaria.getComboPeriodo().addItem("Trimestral");
            viewSecundaria.getComboPeriodo().addItem("Quadrimestral");
            viewSecundaria.getComboPeriodo().addItem("Semestral");
            viewSecundaria.getComboPeriodo().addItem("Anual");
            
            for(int linhas=0; linhas<servicos.size();linhas++){
                periodoServico = servicos.get(linhas).getPeriodo();
                
                if(!periodoServico.equals(periodoServicoAnterior)){
                    if(!periodoServico.equals("[Nenhum]")&&!periodoServico.equals("Diária")&&!periodoServico.equals("Semanal")
                        &&!periodoServico.equals("Mensal")&&!periodoServico.equals("Trimestral")&&!periodoServico.equals("Quadrimestral")
                        &&!periodoServico.equals("Semestral")&&!periodoServico.equals("Anual")){
                        viewSecundaria.getComboPeriodo().addItem(periodoServico);
                    }
                }
                periodoServicoAnterior=periodoServico;
            }
        }
    }
    
    
    private int periodDays(){
        if(viewSecundaria.getComboPeriodo().isEnabled()){
            String tipo = viewSecundaria.getComboPeriodo().getSelectedItem().toString();
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
        return this.periodOfOutro();
    }
    
    private int periodOfOutro(){
        String period = viewSecundaria.getComboDias().getSelectedItem().toString();
        int dias=0;
        if(viewSecundaria.getCampoDiasNovo()!=null){
         dias = Integer.parseInt(viewSecundaria.getCampoDias().getText());
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
    
    private void alterarLocalizacaoViews(boolean ativarDetalhes){
        int view1X = view.getX();
        int view1Y = view.getY();
        int largView1 = view.getWidth();
        int largView2 = viewSecundaria.getWidth();
        int deslocamentoX = largView2/2;
        int espacoEntreViews = 1;
        
        if(ativarDetalhes){
            view.setLocation(view1X-deslocamentoX, view1Y);
            viewSecundaria.setLocation(view1X+largView1+espacoEntreViews-deslocamentoX, view1Y);
            viewSecundaria.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
            viewSecundaria.setVisible(true);
        }
        else{
            view.setLocation(view1X+deslocamentoX, view1Y);
            viewSecundaria.setLocation(view1X+largView1+espacoEntreViews+deslocamentoX, view1Y);
            limparCampos();
            viewSecundaria.dispose();
        }
        
    }
    
    public void sairTela(){
        alterarLocalizacaoViews(false);
    }
    
    private void limparCampos(){
        viewSecundaria.getNomeServico().setText("");
        viewSecundaria.getComboPeriodo().setSelectedIndex(0);
        viewSecundaria.getCampoOutroTipo().setText("");
        viewSecundaria.getComboDias().setSelectedIndex(0);
        viewSecundaria.getCampoDias().setText("");
        viewSecundaria.getValorUnico().setText("");
        viewSecundaria.getValorDinheiro().setText("");
        viewSecundaria.getValorBoleto().setText("");
        viewSecundaria.getValorCredito().setText("");
        viewSecundaria.getValorDebito().setText("");
        viewSecundaria.setarConfigsInciciais();
    }
    
}
