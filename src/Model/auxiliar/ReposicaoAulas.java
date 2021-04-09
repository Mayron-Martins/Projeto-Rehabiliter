/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.auxiliar;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import java.util.Date;

/**
 *
 * @author Mayro
 */
public class ReposicaoAulas {
    private int codBanco;
    private Date data;
    private int codTurma;
    private int codAluno;
    private String situacao;

    public ReposicaoAulas(int codBanco, Date data, int codTurma, int codAluno, String situacao) {
        this.codBanco = codBanco;
        
        ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();
        this.data = converterData.getSqlDate(data);
        this.codTurma = codTurma;
        this.codAluno = codAluno;
        this.situacao = situacao;
    }
    
    public ReposicaoAulas(Date data, int codTurma, int codAluno, String situacao) {
        ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();
        this.data = converterData.getSqlDate(data);
        this.codTurma = codTurma;
        this.codAluno = codAluno;
        this.situacao = situacao;
    }

    public ReposicaoAulas(int codBanco, String situacao) {
        this.codBanco = codBanco;
        this.situacao = situacao;
    }
    
    public int getCodBanco() {
        return codBanco;
    }

    
    public Date getData() {
        return data;
    }

    public int getCodTurma() {
        return codTurma;
    }

    public int getCodAluno() {
        return codAluno;
    }

    public String getSituacao() {
        return situacao;
    }
}
