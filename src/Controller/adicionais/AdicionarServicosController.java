/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.adicionais;

import Controller.auxiliar.ConversaoDeDinheiro;
import Controller.auxiliar.VerificarCodigoNoBanco;
import Dao.ServicosDao;
import Model.auxiliar.Servicos;
import View.ServicosAdicionar;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.plaf.basic.BasicComboBoxUI;

/**
 *
 * @author Mayro
 */
public class AdicionarServicosController {
    private final ServicosAdicionar view;
    private final ServicosDao servicosDao = new ServicosDao();
    private final VerificarCodigoNoBanco verificar = new VerificarCodigoNoBanco();
    private final ConversaoDeDinheiro converterDinheiro = new ConversaoDeDinheiro();
    
    public AdicionarServicosController(ServicosAdicionar view) {
        this.view = view;
    }
    
    public void adicionarServico() throws SQLException{
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
        
        Servicos servico = new Servicos(codBancoServico, nomeServico, periodo, metodoDePagamento, valor, valorAVista, valorBoleto, valorAPrazoCredito, valorAPrazoDebito);
        //Inserindo Dados
        if(nomeServico.equals("")|| periodo.equals("[Nenhum]")){
         view.exibeMensagem("Campos Preenchidos Incorretamente");
        } else{
            servicosDao.inserirDados(servico);
            view.exibeMensagem("Sucesso!");
            //Limpando Campos
            view.getNomeServico().setText("");
            preencherComboPeriodo();
            view.getComboPeriodo().setSelectedIndex(0);
            view.getMetodoPagamento().setSelectedIndex(0);
            view.getValorDinheiro().setText("");
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
}
