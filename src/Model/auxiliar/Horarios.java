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
public class Horarios {
    private int codBanco;
    private String diaDaSemana;
    private String horarioInicio;
    private String horarioFim;
    private int codCliente;
    private int codTurma;

    public Horarios() {
    }

    public Horarios(int codBanco, String diaDaSemana, String horarioInicio, String horarioFim, int codCliente, int codTurma) {
        this.codBanco = codBanco;
        this.diaDaSemana = diaDaSemana;
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
        this.codCliente = codCliente;
        this.codTurma = codTurma;
    }

    

    public int getCodBanco() {
        return codBanco;
    }

    public String getDiaDaSemana() {
        return diaDaSemana;
    }

    public String getHorarioInicio() {
        return horarioInicio;
    }

    public String getHorarioFim() {
        return horarioFim;
    }

    
    public int getCodCliente() {
        return codCliente;
    }

    public int getCodTurma() {
        return codTurma;
    }
    
    
}
