/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Mayro
 */
public class Funcionario extends Pessoa{
    protected String senha;
    protected BigDecimal salario;
    protected String cargo;
    protected String telasPermitidas;
    protected String status;
    protected String situacao;

    public Funcionario(int codBanco, String nome, String cpf, String rg, String telefone, String celular, String email, Date datadenascimento, String usuario,String senha, BigDecimal salario, String cargo, String telasPermitidas, String status, String situacao) {
        super(nome, cpf, rg, telefone, celular, email, datadenascimento, usuario);
        this.codBanco = codBanco;
        this.senha = senha;
        this.salario = new BigDecimal(salario.toString());
        this.cargo = cargo;
        this.telasPermitidas = telasPermitidas;
        this.status = status;
        this.situacao = situacao;
    }
    
    public Funcionario(String nome, String cpf, String rg, String telefone, String celular, String email, Date datadenascimento, String usuario,String senha, BigDecimal salario, String cargo, String telasPermitidas, String status, String situacao) {
        super(nome, cpf, rg, telefone, celular, email, datadenascimento, usuario);
        this.senha = senha;
        this.salario = new BigDecimal(salario.toString());
        this.cargo = cargo;
        this.telasPermitidas = telasPermitidas;
        this.status = status;
        this.situacao = situacao;
    }
    
    public Funcionario(int codBanco, String nome, String cargo, String situacao){
        super(codBanco);
        this.nome = nome;
        this.cargo = cargo;
        this.situacao = situacao;
    }
    
    public Funcionario(int codBanco, String nome, String cpf, Date nascimento, String cargo, BigDecimal salario){
        super(codBanco);
        this.nome = nome;
        this.cpf = cpf;
        ConversaodeDataParaPadraoDesignado converter = new ConversaodeDataParaPadraoDesignado();
        this.datadenascimento = converter.getSqlDate(datadenascimento);
        this.cargo = cargo;
        this.salario = new BigDecimal(salario.toString());
    }
    
    public Funcionario(int codBanco, String telefone, String celular, String email, String usuario, String senha){
        super(codBanco, telefone, celular, email);
        this.usuario = usuario;
        this.senha = senha;
    }
    
    
    //***************************************************************************
    //CONSTRUTORES PARA LOGAR
    public Funcionario(String usuario, String senha) {
        super(usuario);
        this.senha = senha;
    }

    
     
    
    //___________________________________________________________________________
    //GETTERS

    public String getSenha() {
        return senha;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public String getCargo() {
        return cargo;
    }

    public String getTelasPermitidas() {
        return telasPermitidas;
    }

    public String getStatus() {
        return status;
    }

    public String getSituacao() {
        return situacao;
    }
    
    
}
