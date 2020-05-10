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
public abstract class Relatórios {
    protected String evento;
    protected Date data;
    protected Date hora;
    
    //___________________________________________________________________________
    //CONSTRUTORES

    public Relatórios(String evento, Date data, Date hora) {
        this.evento = evento;
        this.data = data;
        this.hora = hora;
    }
    
    
    
    //___________________________________________________________________________
    //GETTERS

    public String getEvento() {
        return evento;
    }

    public Date getData() {
        return data;
    }

    public Date getHora() {
        return hora;
    }
    
}
