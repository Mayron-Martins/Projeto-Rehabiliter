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
public class RelatóriosFuncionários extends Relatórios{
    private String nome_funcionario;
    private String cpf_funcionario;
    private int quantidadevendida;
    private float valordavenda;
    private int alunoscadastrados;
    
    
    //___________________________________________________________________________
    //CONSTRUTORES
    //ENTRADA E SAÍDA DO SISTEMA
    public RelatóriosFuncionários(String nome_funcionario, String cpf_funcionario, String evento, Date data, Date hora) {
        super(evento, data, hora);
        this.nome_funcionario = nome_funcionario;
        this.cpf_funcionario = cpf_funcionario;
    }
    //VENDA DE PRODUTOS
    public RelatóriosFuncionários(String nome_funcionario, String cpf_funcionario, int quantidadevendida, float valordavenda, String evento, Date data, Date hora) {
        super(evento, data, hora);
        this.nome_funcionario = nome_funcionario;
        this.cpf_funcionario = cpf_funcionario;
        this.quantidadevendida = quantidadevendida;
        this.valordavenda = valordavenda;
    }
    //CADASTRO DE ALUNOS
    public RelatóriosFuncionários(String nome_funcionario, String cpf_funcionario, int alunoscadastrados, String evento, Date data, Date hora) {
        super(evento, data, hora);
        this.nome_funcionario = nome_funcionario;
        this.cpf_funcionario = cpf_funcionario;
        this.alunoscadastrados = alunoscadastrados;
    }
    
    
    
    //___________________________________________________________________________
    //GETTERS

    public String getNome_funcionario() {
        return nome_funcionario;
    }

    public String getCpf_funcionario() {
        return cpf_funcionario;
    }

    public int getQuantidadevendida() {
        return quantidadevendida;
    }

    public float getValordavenda() {
        return valordavenda;
    }

    public int getAlunoscadastrados() {
        return alunoscadastrados;
    }
    
}
