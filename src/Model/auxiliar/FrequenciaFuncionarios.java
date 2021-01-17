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
public class FrequenciaFuncionarios {
    private int codFuncinario;
    private Date dataInsert;
    private String situacao;

    public FrequenciaFuncionarios(int codFuncinario, String dataInsert, String situacao) {
        this.codFuncinario = codFuncinario;
        this.situacao = situacao;
        
        ConversaodeDataParaPadraoDesignado converter = new ConversaodeDataParaPadraoDesignado();
        this.dataInsert = converter.getSqlDate(converter.parseDate(dataInsert));

    }

    public int getCodFuncinario() {
        return codFuncinario;
    }

    public Date getDataInsert() {
        return dataInsert;
    }

    public String getSituacao() {
        return situacao;
    }
    
    
    
}
