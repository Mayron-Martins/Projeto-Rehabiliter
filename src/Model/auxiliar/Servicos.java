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
    private final BigDecimal valorVista;
    private final BigDecimal valorBoleto;
    private final BigDecimal valorPrazo;

    public Servicos(int codBanco, String nome, String periodo, BigDecimal valorVista, BigDecimal valorBoleto, BigDecimal valorPrazo) {
        this.codBanco = codBanco;
        this.nome = nome;
        this.periodo = periodo;
        this.valorVista = valorVista;
        this.valorBoleto = valorBoleto;
        this.valorPrazo = valorPrazo;
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

    public BigDecimal getValorPrazo() {
        return valorPrazo;
    }

    
    
    
    
}
