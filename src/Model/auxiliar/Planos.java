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
public class Planos {
    private int codAluno;
    private int codTurma;
    private int codServico;
    private int diaVencimento;
    private Date dataVencimento;
    private Date dataPagamento;
    private Date dataCancelamento;
    private Date dataRenovacao;
    private String situacao;
    private long chavePlano;

    public Planos(long chavePlano, int codAluno, int codTurma, int codServico, int diaVencimento, Date dataVencimento, Date dataPagamento, Date dataCancelamento, Date dataRenovacao, String situacao) {
        this.codAluno = codAluno;
        this.codTurma = codTurma;
        this.codServico = codServico;
        this.diaVencimento = diaVencimento;
        
        ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();
        this.dataPagamento = converterData.getSqlDate(dataPagamento);
        this.dataCancelamento = converterData.getSqlDate(dataCancelamento);
        this.dataRenovacao = converterData.getSqlDate(dataRenovacao);
        this.dataVencimento = converterData.getSqlDate(dataVencimento);
        this.situacao = situacao;
        this.chavePlano = chavePlano;
    }

    public int getCodAluno() {
        return codAluno;
    }

    public int getCodTurma() {
        return codTurma;
    }

    public int getCodServico() {
        return codServico;
    }

    public int getDiaVencimento() {
        return diaVencimento;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public Date getDataCancelamento() {
        return dataCancelamento;
    }

    public String getSituacao() {
        return situacao;
    }

    public long getChavePlano() {
        return chavePlano;
    }

    public Date getDataRenovacao() {
        return dataRenovacao;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }
}
