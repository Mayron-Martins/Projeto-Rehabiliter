/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.auxiliar;

import java.math.BigDecimal;


/**
 *
 * @author Mayro
 */
public class Servicos {
    private final int codBanco;
    private final String nome;
    private final String periodo;
    private final String formaPagamento;
    private final BigDecimal valor;
    private final BigDecimal valorVista;
    private final BigDecimal valorBoleto;
    private final BigDecimal valorPrazoCredito;
    private final BigDecimal valorPrazoDebito;
    private int periodDays;
    private String situacao;

    public Servicos(int codBanco, String nome, String periodo, String formaPagamento,BigDecimal valor, BigDecimal valorVista, BigDecimal valorBoleto, BigDecimal valorPrazoCredito, BigDecimal valorPrazoDebito, int periodDays, String situacao) {
        this.codBanco = codBanco;
        this.nome = nome;
        this.periodo = periodo;
        this.formaPagamento = formaPagamento;
        this.valor = new BigDecimal(valor.toString());
        this.valorVista = new BigDecimal (valorVista.toString());
        this.valorBoleto = new BigDecimal (valorBoleto.toString());
        this.valorPrazoCredito = new BigDecimal(valorPrazoCredito.toString());
        this.valorPrazoDebito = new BigDecimal (valorPrazoDebito.toString());
        this.periodDays = periodDays;
        this.situacao = situacao;
    }

    public int getCodBanco() {
        return codBanco;
    }

    public String getNome() {
        return nome;
    }

    public String getPeriodo() {
        return periodo;
    }

    public BigDecimal getValorVista() {
        return valorVista;
    }

    public BigDecimal getValorBoleto() {
        return valorBoleto;
    }

    public BigDecimal getValorPrazoCredito() {
        return valorPrazoCredito;
    }

    public BigDecimal getValorPrazoDebito() {
        return valorPrazoDebito;
    }
    
    

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public int getPeriodDays() {
        return periodDays;
    }

    public String getSituacao() {
        return situacao;
    }
    

}
