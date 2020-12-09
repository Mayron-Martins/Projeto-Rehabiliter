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
public class LogAçoesFuncionario {
    private int codFuncionario;
    private Timestamp dataEvento;
    private String acao;
    private String descricao;

    public LogAçoesFuncionario(int codFuncionario, Date dataEvento, String acao, String descricao) {
        this.codFuncionario = codFuncionario;
        ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();
        this.dataEvento = converterData.getSqlDateAndTime(dataEvento);
        this.acao = acao;
        this.descricao = descricao;
    }
    
    public int getCodFuncionario() {
        return codFuncionario;
    }

    public Timestamp getDataEvento() {
        return dataEvento;
    }

    public String getAcao() {
        return acao;
    }

    public String getDescricao() {
        return descricao;
    }
    
}
