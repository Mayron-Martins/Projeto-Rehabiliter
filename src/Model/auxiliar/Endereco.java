/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.auxiliar;

/**
 *
 * @author Mayro
 */
public abstract class Endereco {
    protected int codBanco;
    protected String logradouro;
    protected String bairro;
    protected String numero;
    protected String complemento;
    protected String referencia;
    protected String cidade;
    protected String estado;
    protected String cep;

    public Endereco(int codBanco, String logradouro, String bairro, String numero, String complemento, String referencia, String cidade, String estado, String cep) {
        this.codBanco = codBanco;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.numero = numero;
        this.complemento = complemento;
        this.referencia = referencia;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }
    
    public Endereco(String logradouro, String bairro, String numero, String complemento, String referencia, String cidade, String estado, String cep) {
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.numero = numero;
        this.complemento = complemento;
        this.referencia = referencia;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    public int getCodBanco() {
        return codBanco;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public String getNumero() {
        return numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getReferencia() {
        return referencia;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public String getCep() {
        return cep;
    }

     
}
