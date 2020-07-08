/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import java.text.ParseException;
import java.util.Date;

/**
 *
 * @author Mayro
 */
public abstract class Pessoa {
    protected String nome;
    protected String cpf;
    protected String rg;
    protected char sexo;
    protected String telefone;
    protected String email;
    protected Date datadenascimento;
    protected String endereco_rua;
    protected String endereco_bairro;
    protected String endereco_numero;
    protected String endereco_complemento;
    protected String endereco_cep;
    protected String endereco_cidade;
    protected String endereco_estado;
    protected String usuario;

    //___________________________________________________________________________
    //CONSTRUTORES PARA ADICIONAR
    //PESSOA COM ENDEREÇO
    public Pessoa(String nome, String cpf, String rg, char sexo, String telefone, String email, Date datadenascimento, String endereco_rua, String endereco_bairro, String endereco_numero, String endereco_complemento, String endereco_cep, String endereco_cidade, String endereco_estado) {
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.sexo = sexo;
        this.telefone = telefone;
        this.email = email;
        this.datadenascimento = datadenascimento;
        this.endereco_rua = endereco_rua;
        this.endereco_bairro = endereco_bairro;
        this.endereco_numero = endereco_numero;
        this.endereco_complemento = endereco_complemento;
        this.endereco_cep = endereco_cep;
        this.endereco_cidade = endereco_cidade;
        this.endereco_estado = endereco_estado;
    }
    //PESSOA SEM CPF E RG
    public Pessoa(String nome, char sexo, String telefone, String email, Date datadenascimento, String endereco_rua, String endereco_bairro, String endereco_numero, String endereco_complemento, String endereco_cep, String endereco_cidade, String endereco_estado) {
        this.nome = nome;
        this.sexo = sexo;
        this.telefone = telefone;
        this.email = email;
        this.datadenascimento = datadenascimento;
        this.endereco_rua = endereco_rua;
        this.endereco_bairro = endereco_bairro;
        this.endereco_numero = endereco_numero;
        this.endereco_complemento = endereco_complemento;
        this.endereco_cep = endereco_cep;
        this.endereco_cidade = endereco_cidade;
        this.endereco_estado = endereco_estado;
    }   
    //PESSOA COM ID
    public Pessoa(String nome, String cpf, String rg, char sexo, String telefone, String email, Date datadenascimento, String endereco_rua, String endereco_bairro, String endereco_numero, String endereco_complemento, String endereco_cep, String endereco_cidade, String endereco_estado, String usuario) {    
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.sexo = sexo;
        this.telefone = telefone;
        this.email = email;
        this.datadenascimento = datadenascimento;
        this.endereco_rua = endereco_rua;
        this.endereco_bairro = endereco_bairro;
        this.endereco_numero = endereco_numero;
        this.endereco_complemento = endereco_complemento;
        this.endereco_cep = endereco_cep;
        this.endereco_cidade = endereco_cidade;
        this.endereco_estado = endereco_estado;
        this.usuario = usuario;
    }
    //PESSOA COM ID SEM ENDEREÇO
    public Pessoa(String nome, String cpf, String rg, char sexo, String telefone, String email, Date datadenascimento, String usuario) {
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.sexo = sexo;
        this.telefone = telefone;
        this.email = email;
        this.datadenascimento = datadenascimento;
        this.usuario = usuario;
    }
    
    
    
    //___________________________________________________________________________
    //CONSTRUTORES PARA EDITAR
    //PESSOA COM ENDEREÇO, TELEFONE E E-MAIL
    public Pessoa(String telefone, String email, Date datadenascimento, String endereco_rua, String endereco_bairro, String endereco_numero, String endereco_complemento, String endereco_cep, String endereco_cidade, String endereco_estado) {    
        this.telefone = telefone;
        this.email = email;
        this.datadenascimento = datadenascimento;
        this.endereco_rua = endereco_rua;
        this.endereco_bairro = endereco_bairro;
        this.endereco_numero = endereco_numero;
        this.endereco_complemento = endereco_complemento;
        this.endereco_cep = endereco_cep;
        this.endereco_cidade = endereco_cidade;
        this.endereco_estado = endereco_estado;
    }
    //PESSOA SEM ENDEREÇO COM TELEFONE E E-MAIL
    public Pessoa(String telefone, String email) {
        this.telefone = telefone;
        this.email = email;
    }

    
    //___________________________________________________________________________
    //CONSTRUTORES PARA LOGAR
    public Pessoa(String usuario) {
        this.usuario = usuario;
    }
    
    
    //___________________________________________________________________________
    //GETTERS

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getRg() {
        return rg;
    }

    public char getSexo() {
        return sexo;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public Date getDatadenascimento() {
        return datadenascimento;
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

    public String getEndereco_complemento() {
        return endereco_complemento;
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

    public String getUsuario() {
        return usuario;
    }
          
}
