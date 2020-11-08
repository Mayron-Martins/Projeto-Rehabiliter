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
import java.math.BigDecimal;
import java.sql.SQLException;

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
        String periodo= view.getComboPeriodo().getSelectedItem().toString();
        String metodoDePagamento = view.getMetodoPagamento().getSelectedItem().toString();
        BigDecimal valorAVista= new BigDecimal(0);
        BigDecimal valorBoleto= new BigDecimal(0);
        BigDecimal valorAPrazoCredito= new BigDecimal(0);
        BigDecimal valorAPrazoDebito= new BigDecimal(0);
        
        String valorDinheiro = converterDinheiro.converterParaBigDecimal(view.getValorDinheiro().getText()).toString();
        if(metodoDePagamento.equals("Dinheiro")){valorAVista = new BigDecimal(valorDinheiro);}
        if(metodoDePagamento.equals("Boleto")){valorBoleto = new BigDecimal(valorDinheiro);}
        if(metodoDePagamento.equals("Cartão de Crédito")){valorAPrazoCredito = new BigDecimal(valorDinheiro);}
        if(metodoDePagamento.equals("Cartão de Débito")){valorAPrazoDebito = new BigDecimal(valorDinheiro);}
        
        Servicos servico = new Servicos(codBancoServico, nomeServico, periodo, metodoDePagamento, valorAVista, valorBoleto, valorAPrazoCredito, valorAPrazoDebito);
        //Inserindo Dados
        if(nomeServico.equals("")|| periodo.equals("[Nenhum]") ||metodoDePagamento.equals("[Nenhuma]")){
         view.exibeMensagem("Campos Preenchidos Incorretamente");
        } else{
            servicosDao.inserirDados(servico);
            view.exibeMensagem("Sucesso!");
            //Limpando Campos
            view.getNomeServico().setText("");
            view.getComboPeriodo().setSelectedIndex(0);
            view.getMetodoPagamento().setSelectedIndex(0);
            view.getValorDinheiro().setText("");
        }
        
    }
    
}
