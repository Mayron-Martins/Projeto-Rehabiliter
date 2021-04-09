/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.auxiliar;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Mayro
 */
public class Gastos {
    private int codBanco;
    private String motivo;
    private float quantidade;
    private String formaPagamento;
    private BigDecimal valorGasto;
    private Date dataCadastro;
    private long chaveTransacao;

    public Gastos(String motivo, float quantidade, String formaPagamento, BigDecimal valorGasto, Date dataCadastro) {
        this.motivo = motivo;
        this.quantidade = quantidade;
        this.formaPagamento = formaPagamento;
        this.valorGasto = valorGasto;
        ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();    
        this.dataCadastro = converterData.getSqlDate(dataCadastro);
        this.chaveTransacao = converterData.dataEHoraCodificada();
    }

    public Gastos(int codBanco, String motivo, float quantidade, String formaPagamento, BigDecimal valorGasto, Date dataCadastro, long chaveTransacao) {
        this.codBanco = codBanco;
        this.motivo = motivo;
        this.quantidade = quantidade;
        this.formaPagamento = formaPagamento;
        this.valorGasto = valorGasto;
        ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();    
        this.dataCadastro = converterData.getSqlDate(dataCadastro);
        this.chaveTransacao = chaveTransacao;
    }

    public int getCodBanco() {
        return codBanco;
    }

    public String getMotivo() {
        return motivo;
    }

    public float getQuantidade() {
        return quantidade;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public BigDecimal getValorGasto() {
        return valorGasto;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public long getChaveTransacao() {
        return chaveTransacao;
    }
}
