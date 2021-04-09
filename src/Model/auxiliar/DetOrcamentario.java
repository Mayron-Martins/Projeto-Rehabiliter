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
public class DetOrcamentario {
    private int codBanco;
    private String tipo;
    private String formaPagamento;
    private BigDecimal valor;
    private Date dataCadastro;
    private long chave;

    public DetOrcamentario(int codBanco, String tipo, String formaPagamento, BigDecimal valor, Date dataCadastro, long chave) {
        this.codBanco = codBanco;
        this.tipo = tipo;
        this.formaPagamento = formaPagamento;
        this.valor = new BigDecimal(valor.toString());
        
        ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();
        this.dataCadastro = converterData.getSqlDate(dataCadastro);
        this.chave = chave;
    }
    
    public DetOrcamentario(String tipo, String formaPagamento, BigDecimal valor, Date dataCadastro, long chave) {
        this.tipo = tipo;
        this.formaPagamento = formaPagamento;
        this.valor = new BigDecimal(valor.toString());
        
        ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();
        this.dataCadastro = converterData.getSqlDate(dataCadastro);
        this.chave = chave;
    }

    public String getTipo() {
        return tipo;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }   

    public int getCodBanco() {
        return codBanco;
    }

    public long getChave() {
        return chave;
    }
    
}
