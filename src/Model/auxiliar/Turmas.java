/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.auxiliar;

/**
 *
 * @author Mayro
 */
public class Turmas {
    private int codBanco;
    private String nomeTurma;
    private String subgrupo;
    private int quantidadeAlunos;
    private int quantidadeMaximaAlunos;
    private String diasDaSemana;

    public Turmas(int codBanco, String nomeTurma, String subgrupo, int quantidadeAlunos, int quantidadeMaximaAlunos, String diasDaSemana) {
        this.codBanco = codBanco;
        this.nomeTurma = nomeTurma;
        this.subgrupo = subgrupo;
        this.quantidadeAlunos = quantidadeAlunos;
        this.quantidadeMaximaAlunos = quantidadeMaximaAlunos;
        this.diasDaSemana = diasDaSemana;
    }

    public int getCodBanco() {
        return codBanco;
    }

    public String getNomeTurma() {
        return nomeTurma;
    }

    public String getSubgrupo() {
        return subgrupo;
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
    
    
}
