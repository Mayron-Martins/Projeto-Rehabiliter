/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Mayro
 */
public class Vendas {
    private int codVenda;
    private int codCliente;
    private int codAluno;
    private BigDecimal valorVenda;
    private BigDecimal valorPago;
    private BigDecimal valorTroco;
    private Date dataVenda;
    private String formaPagamento;
    private long chaveVenda;
    private String plano;

    public Vendas(int codVenda, int codCliente, int codAluno, BigDecimal valorVenda, BigDecimal valorPago, BigDecimal valorTroco, Date dataVenda, String formaPagamento, String plano) {
        this.codVenda = codVenda;
        this.codCliente = codCliente;
        this.codAluno = codAluno;
        this.valorVenda = new BigDecimal(valorVenda.toString());
        this.valorPago = new BigDecimal(valorPago.toString());
        this.valorTroco = new BigDecimal(valorTroco.toString());
        this.formaPagamento = formaPagamento;
       
        ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado(); 
        this.dataVenda = converterData.getSqlDate(dataVenda);
        this.chaveVenda = converterData.dataEHoraCodificada();
        this.plano = plano;
    }

    public int getCodVenda() {
        return codVenda;
    }

    public int getCodCliente() {
        return codCliente;
    }

    public int getCodAluno() {
        return codAluno;
    }

    public BigDecimal getValorVenda() {
        return valorVenda;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public BigDecimal getValorTroco() {
        return valorTroco;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public long getChaveVenda() {
        return chaveVenda;
    }

    public String getPlano() {
        return plano;
    }

}
