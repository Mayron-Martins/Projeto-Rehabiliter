/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

/**
 *
 * @author Mayro
 */
public class Produtos {
    private int codBanco;
    private String nomeProduto;
    private String tipo;
    private String unMedida;
    private float quantidade;
    private String descricao;
    private BigDecimal valorDeCompra;
    private Date dataDeCompra;
    private BigDecimal valorDeVenda;
    private int chaveDeLote;

    //Construtor básico
    public Produtos(String nomeProduto, float quantidade, BigDecimal valorDeVenda) {
        this.nomeProduto = nomeProduto;
        this.quantidade = quantidade;
        this.valorDeVenda = valorDeVenda;
    }
    
    //Construtor completo
    public Produtos(int codProduto, String nomeProduto, String tipo, String unMedida, float quantidade, String descricao, BigDecimal valorDeCompra, String dataDeCompra, BigDecimal valorDeVenda, int chaveDeLote) {
        this.codBanco = codProduto;
        this.nomeProduto = nomeProduto;
        this.tipo = tipo;
        this.unMedida = unMedida;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.valorDeCompra = valorDeCompra;
        try{
            ConversaodeDataParaPadraoDesignado converter_data = new ConversaodeDataParaPadraoDesignado();
            this.dataDeCompra = converter_data.getSqlDate(converter_data.parseDate(dataDeCompra));
        }catch (ParseException erro){
            System.err.println("Erro em converter data de compra");
        }
        this.valorDeVenda = valorDeVenda;
        this.chaveDeLote = chaveDeLote;
    }

    public int getCodBanco() {
        return codBanco;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public String getTipo() {
        return tipo;
    }

    public String getUnMedida() {
        return unMedida;
    }

    public float getQuantidade() {
        return quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValorDeCompra() {
        return valorDeCompra;
    }

    public Date getDataDeCompra() {
        return dataDeCompra;
    }

    public BigDecimal getValorDeVenda() {
        return valorDeVenda;
    }


    public int getChaveDeLote() {
        return chaveDeLote;
    }
    
    
}
