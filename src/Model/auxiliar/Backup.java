/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.auxiliar;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author Mayro
 */
public class Backup {
    private int codBackup;
    private String nome;
    private Timestamp data;
    private String enderecoBackup;
    private String tabelasBackup;

    public Backup(int codBackup, String nome, Date data, String enderecoBackup, String tabelasBackup) {
        this.codBackup = codBackup;
        this.nome = nome;
        ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();
        this.data = converterData.getSqlDateAndTime(data);
        this.enderecoBackup = enderecoBackup;
        this.tabelasBackup = tabelasBackup;
    }
    
    public Backup(String nome, Date data, String enderecoBackup, String tabelasBackup) {
        this.nome = nome;
        ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();
        this.data = converterData.getSqlDateAndTime(data);
        this.enderecoBackup = enderecoBackup;
        this.tabelasBackup = tabelasBackup;
    }

    public int getCodBackup() {
        return codBackup;
    }

    public String getNome() {
        return nome;
    }

    public Timestamp getData() {
        return data;
    }

    public String getEnderecoBackup() {
        return enderecoBackup;
    }

    public String getTabelasBackup() {
        return tabelasBackup;
    }
    
    
    
}
