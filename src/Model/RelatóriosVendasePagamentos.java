/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Mayro
 */
public class RelatóriosVendasePagamentos extends Relatórios{
    private float valordavenda;
    private float valordadivida;
    private ArrayList<String> produtosvendidos;
    private String situacao;
    private int quantidadedeprodutosvendidos;
    private String formadepagamento;
    private String tipo;
    private String cliente;
    
    //___________________________________________________________________________
    //CONSTRUTORES
    //PAGAMENTO DE PLANO
    public RelatóriosVendasePagamentos(float valordavenda, ArrayList<String> produtosvendidos, String situacao, String formadepagamento, String tipo, String cliente, String evento, Date data, Date hora) {
        super(evento, data, hora);
        this.valordavenda = valordavenda;
        this.produtosvendidos = produtosvendidos;
        this.situacao = situacao;
        this.formadepagamento = formadepagamento;
        this.tipo = tipo;
        this.cliente = cliente;
    }
    //PAGAMENTO NO CAIXA - CLIENTE SEM CADASTRO
    public RelatóriosVendasePagamentos(float valordavenda, ArrayList<String> produtosvendidos, String situacao, int quantidadedeprodutosvendidos, String formadepagamento, String tipo, String evento, Date data, Date hora) {
        super(evento, data, hora);
        this.valordavenda = valordavenda;
        this.produtosvendidos = produtosvendidos;
        this.situacao = situacao;
        this.quantidadedeprodutosvendidos = quantidadedeprodutosvendidos;
        this.formadepagamento = formadepagamento;
        this.tipo = tipo;
    }
    //PAGAMENTO NO CAIXA - CLIENTE COM CADASTRO
    public RelatóriosVendasePagamentos(float valordavenda, ArrayList<String> produtosvendidos, String situacao, int quantidadedeprodutosvendidos, String formadepagamento, String tipo, String cliente, String evento, Date data, Date hora) {
        super(evento, data, hora);
        this.valordavenda = valordavenda;
        this.produtosvendidos = produtosvendidos;
        this.situacao = situacao;
        this.quantidadedeprodutosvendidos = quantidadedeprodutosvendidos;
        this.formadepagamento = formadepagamento;
        this.tipo = tipo;
        this.cliente = cliente;
    }
    //CONTRA-SERVIÇOS
    public RelatóriosVendasePagamentos(float valordadivida, String situacao, String tipo, String evento, Date data, Date hora) {
        super(evento, data, hora);
        this.valordadivida = valordadivida;
        this.situacao = situacao;
        this.tipo = tipo;
    }
    
    
    //___________________________________________________________________________
    //GETTERS

    public float getValordavenda() {
        return valordavenda;
    }

    public float getValordadivida() {
        return valordadivida;
    }

    public ArrayList<String> getProdutosvendidos() {
        return produtosvendidos;
    }

    public String getSituacao() {
        return situacao;
    }

    public int getQuantidadedeprodutosvendidos() {
        return quantidadedeprodutosvendidos;
    }

    public String getFormadepagamento() {
        return formadepagamento;
    }

    public String getTipo() {
        return tipo;
    }

    public String getCliente() {
        return cliente;
    }
    
}
