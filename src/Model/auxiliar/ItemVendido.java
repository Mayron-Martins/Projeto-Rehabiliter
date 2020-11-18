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
public class ItemVendido {
    private long chaveVenda;
    private int codProduto;
    private float quantidade;
    private BigDecimal valor;
    private BigDecimal subtotal;

    public ItemVendido(long chaveVenda, int codProduto, float quantidade, BigDecimal valor, BigDecimal subtotal) {
        this.chaveVenda = chaveVenda;
        this.codProduto = codProduto;
        this.quantidade = quantidade;
        this.valor = valor;
        this.subtotal = subtotal;
    }

    public long getChaveVenda() {
        return chaveVenda;
    }

    public int getCodProduto() {
        return codProduto;
    }

    public float getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }
    
    
    
    
}
