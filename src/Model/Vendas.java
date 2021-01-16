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
    private int codPlano;
    private BigDecimal valorVenda;
    private BigDecimal valorPago;
    private BigDecimal valorTroco;
    private Date dataVenda;
    private String formaPagamento;
    private long chaveVenda;
    private String plano;
    private int parcelas;
    
    public Vendas(int codVenda, int codCliente, int codAluno, int codPlano, BigDecimal valorVenda, BigDecimal valorPago, BigDecimal valorTroco, Date dataVenda, String formaPagamento, String plano, int parcelas) {
        this.codVenda = codVenda;
        this.codCliente = codCliente;
        this.codAluno = codAluno;
        this.codPlano = codPlano;
        this.valorVenda = new BigDecimal(valorVenda.toString());
        this.valorPago = new BigDecimal(valorPago.toString());
        this.valorTroco = new BigDecimal(valorTroco.toString());
        this.formaPagamento = formaPagamento;
       
        ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado(); 
        this.dataVenda = converterData.getSqlDate(dataVenda);
        this.chaveVenda = converterData.dataEHoraCodificada();
        this.plano = plano;
        this.parcelas = parcelas;
    }
    
    public Vendas(int codVenda, int codCliente, int codAluno, int codPlano, BigDecimal valorVenda, BigDecimal valorPago, BigDecimal valorTroco, Date dataVenda, String formaPagamento, String plano, long chaveVenda, int parcelas) {
        this.codVenda = codVenda;
        this.codCliente = codCliente;
        this.codAluno = codAluno;
        this.codPlano = codPlano;
        this.valorVenda = new BigDecimal(valorVenda.toString());
        this.valorPago = new BigDecimal(valorPago.toString());
        this.valorTroco = new BigDecimal(valorTroco.toString());
        this.formaPagamento = formaPagamento;
       
        ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado(); 
        this.dataVenda = converterData.getSqlDate(dataVenda);
        this.chaveVenda = chaveVenda;
        this.plano = plano;
        this.parcelas = parcelas;
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

    public int getCodPlano() {
        return codPlano;
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

    public int getParcelas() {
        return parcelas;
    }
    

}
