/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.auxiliar;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Model.Pessoa;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mayro
 */
public class FrequenciaTurmas {
    private int codTurma;
    private int codAluno;
    private Date dataInsert;
    private String situacao;

    public FrequenciaTurmas(int codTurma, int codAluno, String dataInsert, String situacao) {
        this.codTurma = codTurma;
        this.codAluno = codAluno;
         ConversaodeDataParaPadraoDesignado converter = new ConversaodeDataParaPadraoDesignado();

        this.dataInsert = converter.getSqlDate(converter.parseDate(dataInsert));

        
        this.situacao = situacao;
    }

    public int getCodTurma() {
        return codTurma;
    }

    public int getCodAluno() {
        return codAluno;
    }

    public Date getDataInsert() {
        return dataInsert;
    }

    public String getSituacao() {
        return situacao;
    }
    
    
}
