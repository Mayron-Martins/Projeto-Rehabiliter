/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

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
        super(codBanco, nome, cpf, rg, telefone, celular, email, datadenascimento, usuario);
        this.senha = senha;
        this.salario = new BigDecimal(salario.toString());
        this.cargo = cargo;
        this.telasPermitidas = telasPermitidas;
        this.status = status;
        this.situacao = situacao;
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
