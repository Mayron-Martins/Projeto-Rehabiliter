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
public class Entradas {
    private int codBanco;
    private String referencia;
    private float quantidade;
    private String formaPagamento;
    private BigDecimal valorEntrada;
    private Date dataCadastro;

    public Entradas(int codBanco, String referencia, float quantidade, String formaPagamento, BigDecimal valor, Date dataCadastro) {
        this.codBanco = codBanco;
        this.referencia = referencia;
        this.quantidade = quantidade;
        this.formaPagamento = formaPagamento;
        this.valorEntrada = new BigDecimal(valor.toString());
        ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();
        this.dataCadastro = converterData.getSqlDate(dataCadastro);
    }

    
    
    public int getCodBanco() {
        return codBanco;
    }

    public String getReferencia() {
        return referencia;
    }

    public float getQuantidade() {
        return quantidade;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public BigDecimal getValorEntrada() {
        return valorEntrada;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }  
}
