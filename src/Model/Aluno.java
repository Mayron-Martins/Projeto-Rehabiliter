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
public class Aluno extends Pessoa {
   private String nomedamae;
   private String nomedopai;
   private String telefonedamae;
   private String telefonedopai;
   private String cpfdamae;
   private String cpfdopai;
   private int turma;
   private int plano;

   //***************************************************************************
   //CONSTRUTORES PARA ADICIONAR
   //___________________________________________________________________________
   //ALUNO MENOR DE IDADE
   //SEM CPF e RG
    public Aluno(String nomedamae, String nomedopai, String telefonedamae, String telefonedopai, String cpfdamae, String cpfdopai, int turma, int plano, int codBanco, String nome, char sexo, String telefone, String email, Date datadenascimento, String endereco_rua, String endereco_bairro, String endereco_numero, String endereco_complemento, String endereco_cep, String endereco_cidade, String endereco_estado) {
        super(codBanco, nome, sexo, telefone, email, datadenascimento, endereco_rua, endereco_bairro, endereco_numero, endereco_complemento, endereco_cep, endereco_cidade, endereco_estado);
        this.nomedamae = nomedamae;
        this.nomedopai = nomedopai;
        this.telefonedamae = telefonedamae;
        this.telefonedopai = telefonedopai;
        this.cpfdamae = cpfdamae;
        this.cpfdopai = cpfdopai;
        this.turma = turma;
        this.plano = plano;
    }
    
    
    //___________________________________________________________________________
    //ALUNO MAIOR DE IDADE
    //COMPLETO
    public Aluno(int turma, int plano, int codBanco, String nome, String cpf, String rg, char sexo, String telefone, String email, Date datadenascimento, String endereco_rua, String endereco_bairro, String endereco_numero, String endereco_complemento, String endereco_cep, String endereco_cidade, String endereco_estado) {
        super(codBanco,nome, cpf, rg, sexo, telefone, email, datadenascimento, endereco_rua, endereco_bairro, endereco_numero, endereco_complemento, endereco_cep, endereco_cidade, endereco_estado);
        this.turma = turma;
        this.plano = plano;
    }
    
    
    
    //***************************************************************************
    //CONSTRUTORES PARA EDITAR
    //___________________________________________________________________________
    //ALUNO MENOR DE IDADE
    //COMPLETO
    public Aluno(String telefonedamae, String telefonedopai, int turma, int plano, String telefone, String email, Date datadenascimento, String endereco_rua, String endereco_bairro, String endereco_numero, String endereco_complemento, String endereco_cep, String endereco_cidade, String endereco_estado) {
        super(telefone, email, datadenascimento, endereco_rua, endereco_bairro, endereco_numero, endereco_complemento, endereco_cep, endereco_cidade, endereco_estado);
        this.telefonedamae = telefonedamae;
        this.telefonedopai = telefonedopai;
        this.turma = turma;
        this.plano = plano;
    }
    
    //___________________________________________________________________________
    //ALUNO MAIOR DE IDADE
    //COMPLETO
    public Aluno(int turma, int plano, String telefone, String email, Date datadenascimento, String endereco_rua, String endereco_bairro, String endereco_numero, String endereco_complemento, String endereco_cep, String endereco_cidade, String endereco_estado) {
        super(telefone, email, datadenascimento, endereco_rua, endereco_bairro, endereco_numero, endereco_complemento, endereco_cep, endereco_cidade, endereco_estado);
        this.turma = turma;
        this.plano = plano;
    }
    
    
    
    //___________________________________________________________________________
    //GETTERS

    public String getNomedamae() {
        return nomedamae;
    }

    public String getNomedopai() {
        return nomedopai;
    }

    public String getTelefonedamae() {
        return telefonedamae;
    }

    public String getTelefonedopai() {
        return telefonedopai;
    }

    public String getCpfdamae() {
        return cpfdamae;
    }

    public String getCpfdopai() {
        return cpfdopai;
    }

    public int getTurma() {
        return turma;
    }

    public int getPlano() {
        return plano;
    }

    
   
}
