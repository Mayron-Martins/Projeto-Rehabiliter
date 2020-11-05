/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.auxiliar;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import java.sql.Time;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mayro
 */
public class Horarios {
    private int codBanco;
    private String diaDaSemana;
    private Time horario;
    private int codCliente;
    private int codTurma;

    public Horarios(int codBanco, String diaDaSemana, String horario, int codCliente, int codTurma) {
        this.codBanco = codBanco;
        this.diaDaSemana = diaDaSemana;
        ConversaodeDataParaPadraoDesignado converter = new ConversaodeDataParaPadraoDesignado();
        try {
            this.horario = converter.parseHour(horario);
        } catch (ParseException ex) {
            System.err.println("Erro ao converter hora - classe Horarios");
            Logger.getLogger(Horarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.codCliente = codCliente;
        this.codTurma = codTurma;
    }

    

    public int getCodBanco() {
        return codBanco;
    }

    public String getDiaDaSemana() {
        return diaDaSemana;
    }

    public Time getHorario() {
        return horario;
    }

    public int getCodCliente() {
        return codCliente;
    }

    public int getCodTurma() {
        return codTurma;
    }
    
    
}