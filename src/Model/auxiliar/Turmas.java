/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.auxiliar;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import java.sql.Time;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private Time horario;

    
    public Turmas(int codBanco, String nomeTurma, String subgrupo, int quantidadeAlunos, int quantidadeMaximaAlunos, String diasDaSemana, String horario) {
        this.codBanco = codBanco;
        this.nomeTurma = nomeTurma;
        this.subgrupo = subgrupo;
        this.quantidadeAlunos = quantidadeAlunos;
        this.quantidadeMaximaAlunos = quantidadeMaximaAlunos;
        this.diasDaSemana = diasDaSemana;
        ConversaodeDataParaPadraoDesignado converter = new ConversaodeDataParaPadraoDesignado();
        try {
            this.horario = converter.parseHour(horario);
        } catch (ParseException ex) {
            System.err.println("Erro ao converter hora - classe Horarios");
            Logger.getLogger(Horarios.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public Time getHorario() {
        return horario;
    }
    
    
    
}
