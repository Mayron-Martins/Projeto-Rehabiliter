/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.adicionais;

import Controller.auxiliar.ConversaoDeDinheiro;
import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Controller.auxiliar.VerificarCodigoNoBanco;
import Dao.EntradasDao;
import Model.auxiliar.Entradas;
import View.FinanceiroPlanodeEntradasAdc;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author Mayro
 */
public class AdicionarEntradasController {
    private final FinanceiroPlanodeEntradasAdc view;
    private final EntradasDao entradasDao = new EntradasDao();
    private final VerificarCodigoNoBanco verificar = new VerificarCodigoNoBanco();
    private final ConversaoDeDinheiro converterDinheiro = new ConversaoDeDinheiro();
    private final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();

    public AdicionarEntradasController(FinanceiroPlanodeEntradasAdc view) {
        this.view = view;
    }
    
    public void adicionarEntrada() throws SQLException{
        //Dados Entrada
        int codEntrada = verificar.verificarUltimo("tblEntradas", "codEntrada") +1;
        String referencia = view.getCampoReferencia().getText();
        
        BigDecimal quantidadeGrande = new BigDecimal(converterDinheiro.converterParaBigDecimal(view.getCampoQuantidade().getText()).toString());
        float quantidade = quantidadeGrande.floatValue();
        
        String formaPagamento = this.retornarFormaPagamento();
        BigDecimal valorEntrada = new BigDecimal(converterDinheiro.converterParaBigDecimal(view.getCampoValor().getText()).toString());
        Date dataCadastro = view.getCampoData().getDate();
        
        Entradas entrada = new Entradas(codEntrada, referencia, quantidade, formaPagamento, valorEntrada, dataCadastro);
        
        if(referencia.equals("")||quantidade==0||formaPagamento.equals("[Nenhum]")||valorEntrada.compareTo(BigDecimal.ZERO)==0
                || dataCadastro == null){
            view.exibeMensagem("Dados Preenchidos Incorretamente!");
        }
        else{
          //Adicionando no Banco
          entradasDao.inserirDados(entrada);
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
}