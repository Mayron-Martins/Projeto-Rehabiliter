/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Mayro
 */
public class Empresa {
    private String nome;
    private String setor;
    private String cnpj;
    private String telefone;
    private String email;
    private String endereco_rua;
    private String endereco_bairro;
    private String endereco_numero;
    private String endereco_complemento;
    private String endereco_cep;
    private String endereco_cidade;
    private String endereco_estado;

    //___________________________________________________________________________
    //CONSTRUTORES
    public Empresa(String nome, String setor, String cnpj, String telefone, String email, String endereco_rua, String endereco_bairro, String endereco_numero, String complemento, String endereco_cep, String endereco_cidade, String endereco_estado) {
        this.nome = nome;
        this.setor = setor;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.email = email;
        this.endereco_rua = endereco_rua;
        this.endereco_bairro = endereco_bairro;
        this.endereco_numero = endereco_numero;
        this.endereco_complemento = complemento;
        this.endereco_cep = endereco_cep;
        this.endereco_cidade = endereco_cidade;
        this.endereco_estado = endereco_estado;
    }
    
    
    //GETTERS

    public String getNome() {
        return nome;
    }

    public String getSetor() {
        return setor;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public String getEndereco_rua() {
        return endereco_rua;
    }

    public String getEndereco_bairro() {
        return endereco_bairro;
    }

    public String getEndereco_numero() {
        return endereco_numero;
    }

    public String getEndereco_cep() {
        return endereco_cep;
    }

    public String getEndereco_cidade() {
        return endereco_cidade;
    }

    public String getEndereco_estado() {
        return endereco_estado;
    }

    public String getEndereco_complemento() {
        return endereco_complemento;
    }
    
    
}
