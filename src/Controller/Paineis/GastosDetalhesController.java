/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Paineis;

import Controller.PlanoGastosController;
import Model.auxiliar.DetOrcamentario;
import Model.auxiliar.Gastos;
import View.FinanceiroPlanodeContra;
import View.Paineis.GastosDetalhes;
import java.awt.Dialog;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Mayro
 */
public class GastosDetalhesController extends PlanoGastosController{
    private final GastosDetalhes viewSecundaria;
    
    public GastosDetalhesController(FinanceiroPlanodeContra view, GastosDetalhes viewSecundaria) {
        super(view);
        this.viewSecundaria = viewSecundaria;
    }
    
    public void selecionarTabela(){
        int linhaSelecionada = view.getTabelaGastos().getSelectedRow();
        if(linhaSelecionada!=-1){
            int codGasto = Integer.parseInt(tabelaGastos.getValueAt(linhaSelecionada, 0).toString());
            
            Gastos gasto = gastosDao.pesquisarGastos("SELECT * FROM tblGastos WHERE codGasto = "+codGasto).get(0);
            
            viewSecundaria.getCampoReferencia().setText(gasto.getMotivo());
            viewSecundaria.getComboFormaPagamento().setSelectedItem(gasto.getFormaPagamento());
            viewSecundaria.getComboStatus().setSelectedItem(gasto.getStatus());
            BigDecimal quantidadeGrande = new BigDecimal(String.valueOf(gasto.getQuantidade()));
            viewSecundaria.getCampoQuantidade().setText(quantidadeGrande.setScale(2).toString());
            viewSecundaria.getCampoValor().setText(gasto.getValorGasto().toString());
            viewSecundaria.getCampoData().setDate(gasto.getDataCadastro());
            viewSecundaria.getCampoChaveTransacao().setText(gasto.getChaveTransacao()+"");
            
            viewSecundaria.trocaBotoes(false);
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
    
    @Override
    public void editarGasto(){
        int linhaSelecionada = view.getTabelaGastos().getSelectedRow();
        int codGasto = Integer.parseInt(tabelaGastos.getValueAt(linhaSelecionada, 0).toString());
        String motivo = viewSecundaria.getCampoReferencia().getText();
        
        BigDecimal quantidadeGrande = new BigDecimal(converterDinheiro.converterParaBigDecimal(viewSecundaria.getCampoQuantidade().getText()).toString());
        float quantidade = quantidadeGrande.floatValue();
        
        String formaPagamento = viewSecundaria.getComboFormaPagamento().getSelectedItem().toString();
        BigDecimal valorGasto = new BigDecimal(converterDinheiro.converterParaBigDecimal(viewSecundaria.getCampoValor().getText()).toString());
        
        Date dataCadastro = viewSecundaria.getCampoData().getDate();
        String status = viewSecundaria.getComboStatus().getSelectedItem().toString();
        
        long chaveTransacao = Long.parseLong(viewSecundaria.getCampoChaveTransacao().getText());
        
        Gastos gasto = new Gastos(codGasto, motivo, quantidade, formaPagamento, valorGasto, dataCadastro, status, chaveTransacao);
        DetOrcamentario orcamentario = new DetOrcamentario("Gastos", formaPagamento, valorGasto, dataCadastro, chaveTransacao);
        
        if(motivo.trim().equals("")||quantidade==0||valorGasto.compareTo(BigDecimal.ZERO)==0||dataCadastro==null){
            view.mensagemCritica("Campos Preenchidos Incorretamente!", "Aviso");
        }else{
            gastosDao.atualizarDados(gasto);
            gastosDao.atualizarStatus(codGasto, status);
            orcamentarioDao.atualizarDados(orcamentario);
            
            this.setarLog("Edição de Gasto", "Editou o gasto "+motivo);

            view.exibeMensagem("Sucesso!");
            setarTabelas();
            view.getTabelaGastos().addRowSelectionInterval(linhaSelecionada, linhaSelecionada);
            viewSecundaria.trocaBotoes(false);
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
    
    @Override
    public void sairTela(){
        alterarLocalizacaoViews(false);
    }
   
    private void limparCampos(){
        //Painel Dados
        viewSecundaria.getCampoReferencia().setText("");
        viewSecundaria.getCampoQuantidade().setValue(null);
        viewSecundaria.getCampoValor().setValue(null);
        viewSecundaria.getCampoData().setDate(null);
    }
}
