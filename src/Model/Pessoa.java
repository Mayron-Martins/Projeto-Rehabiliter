/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mayro
 */
public abstract class Pessoa {
    protected int codBanco;
    protected String nome;
    protected String cpf;
    protected String rg;
    protected String telefone;
    protected String celular;
    protected String email;
    protected Date datadenascimento;
    protected String usuario;
    
    
    //Pessoa Normal
    public Pessoa(int codBanco, String nome, String cpf, String rg, String telefone, String celular, String email, Date datadenascimento) {
        this.codBanco = codBanco;
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.telefone = telefone;
        this.celular = celular;
        this.email = email;
        
        ConversaodeDataParaPadraoDesignado converter = new ConversaodeDataParaPadraoDesignado();
        this.datadenascimento = converter.getSqlDate(datadenascimento);
    }
    
    //Pessoa com Login e Senha
    public Pessoa(int codBanco, String nome, String cpf, String rg, String telefone, String celular, String email, Date datadenascimento, String usuario) {
        this.codBanco = codBanco;
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.telefone = telefone;
        this.celular = celular;
        this.email = email;
        this.usuario = usuario;
        ConversaodeDataParaPadraoDesignado converter = new ConversaodeDataParaPadraoDesignado();
        this.datadenascimento = converter.getSqlDate(datadenascimento);
    }
    
    //___________________________________________________________________________
    //CONSTRUTORES PARA LOGAR
    public Pessoa(String usuario) {
        this.usuario = usuario;
    }
    
    
    //___________________________________________________________________________
    //GETTERS

    public int getCodBanco() {
        return codBanco;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getRg() {
        return rg;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCelular() {
        return celular;
    }

    public String getEmail() {
        return email;
    }

    public Date getDatadenascimento() {
        return datadenascimento;
    }

    public String getUsuario() {
        return usuario;
    }

    
}
