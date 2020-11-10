/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;

/**
 *
 * @author Mayro
 */
public class Funcionário extends Pessoa{
    protected String senha;
    protected String salario;
    protected String cargo;

    public Funcionário(int codBanco, String nome, String cpf, String rg, String telefone, String celular, String email, Date datadenascimento, String usuario,String senha, String salario, String cargo) {
        super(codBanco, nome, cpf, rg, telefone, celular, email, datadenascimento, usuario);
        this.senha = senha;
        this.salario = salario;
        this.cargo = cargo;
    }
    

    
    
    
    //***************************************************************************
    //CONSTRUTORES PARA LOGAR
    public Funcionário(String usuario, String senha) {
        super(usuario);
        this.senha = senha;
    }

    
     
    
    //___________________________________________________________________________
    //GETTERS

    public String getSenha() {
        return senha;
    }

    public String getSalario() {
        return salario;
    }

    public String getCargo() {
        return cargo;
    }
    
    
}
