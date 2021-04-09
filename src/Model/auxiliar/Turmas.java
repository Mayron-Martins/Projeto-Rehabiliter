/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.auxiliar;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import java.sql.Time;

/**
 *
 * @author Mayro
 */
public class Turmas {
    private int codBanco;
    private String nomeTurma;
    private int quantidadeAlunos;
    private int quantidadeMaximaAlunos;
    private String diasDaSemana;
    private String horario;
    private String situacao;

    
    public Turmas(int codBanco, String nomeTurma, int quantidadeAlunos, int quantidadeMaximaAlunos, String diasDaSemana, String horario, String situacao) {
        this.codBanco = codBanco;
        this.nomeTurma = nomeTurma;
        this.quantidadeAlunos = quantidadeAlunos;
        this.quantidadeMaximaAlunos = quantidadeMaximaAlunos;
        this.diasDaSemana = diasDaSemana;
        this.horario = horario;
        this.situacao = situacao;
    }
    
    public Turmas(String nomeTurma, int quantidadeAlunos, int quantidadeMaximaAlunos, String diasDaSemana, String horario, String situacao) {
        this.nomeTurma = nomeTurma;
        this.quantidadeAlunos = quantidadeAlunos;
        this.quantidadeMaximaAlunos = quantidadeMaximaAlunos;
        this.diasDaSemana = diasDaSemana;
        this.horario = horario;
        this.situacao = situacao;
    }

    public int getCodBanco() {
        return codBanco;
    }

    public String getNomeTurma() {
        return nomeTurma;
    }

    public int getQuantidadeAlunos() {
        return quantidadeAlunos;
    }

    public int getQuantidadeMaximaAlunos() {
        return quantidadeMaximaAlunos;
    }

    public String getDiasDaSemana() {
        return diasDaSemana;
    }

    public String getHorario() {
        return horario;
    }

    public String getSituacao() {
        return situacao;
    }
    
    
    
}
