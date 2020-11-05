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
    

    //***************************************************************************
    //CONSTRUTORES PARA ADICIONAR
    //___________________________________________________________________________
    //FUNCIONÁRIO COM ENDEREÇO
    public Funcionário(String senha, String salario, String cargo,int codBanco, String nome, String cpf, String rg, char sexo, String telefone, String email, Date datadenascimento, String endereco_rua, String endereco_bairro, String endereco_numero, String endereco_complemento, String endereco_cep, String endereco_cidade, String endereco_estado, String usuario) {
        super(codBanco, nome, cpf, rg, sexo, telefone, email, datadenascimento, endereco_rua, endereco_bairro, endereco_numero, endereco_complemento, endereco_cep, endereco_cidade, endereco_estado, usuario);
        this.senha = senha;
        this.salario = salario;
        this.cargo = cargo;
    }
    //FUNCIONÁRIO SEM ENDEREÇO
    public Funcionário(String senha, String salario, String cargo, int codBanco, String nome, String cpf, String rg, char sexo, String telefone, String email, Date datadenascimento, String usuario) {
        super(codBanco, nome, cpf, rg, sexo, telefone, email, datadenascimento, usuario);
        this.senha = senha;
        this.salario = salario;
        this.cargo = cargo;
    }
    
    
    //***************************************************************************
    //CONSTRUTORES PARA EDITAR
    //___________________________________________________________________________
    //FUNCIONÁRIO COM ENDEREÇO
    public Funcionário(String senha, String salario, String cargo, String telefone, String email, Date datadenascimento, String endereco_rua, String endereco_bairro, String endereco_numero, String endereco_complemento, String endereco_cep, String endereco_cidade, String endereco_estado) {    
        super(telefone, email, datadenascimento, endereco_rua, endereco_bairro, endereco_numero, endereco_complemento, endereco_cep, endereco_cidade, endereco_estado);
        this.senha = senha;
        this.salario = salario;
        this.cargo = cargo;
    }
    //FUNCIONÁRIO SEM ENDEREÇO
    public Funcionário(String senha, String salario, String cargo, String telefone, String email) {
        super(telefone, email);
        this.senha = senha;
        this.salario = salario;
        this.cargo = cargo;
    }
    
    
    //***************************************************************************
    //CONSTRUTORES PARA LOGAR
    public Funcionário(String senha, String usuario) {
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
