/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Mayro
 */
public class Aluno extends Pessoa {
   private String nomedamae;
   private String nomedopai;
   private String telefonedamae;
   private String telefonedopai;
   private String cpfdamae;
   private String cpfdopai;
   private int turma;
   private int plano;
   private String descricao;
   private BigDecimal debito;
   private BigDecimal valorContrato;
   private Date dataCadastro;
   private BigDecimal valorMensal;
   private int renovacaoAutomatica;

    public Aluno(int codBanco, String nome, String cpf, String rg, String telefone, String celular, String email, Date datadenascimento, String nomedamae, String nomedopai, String telefonedamae, String telefonedopai, String cpfdamae, String cpfdopai, int turma, int plano, String descricao, BigDecimal debito, BigDecimal valorContrato, BigDecimal valorMensal, int renovacaoAutomatica) {
        super(codBanco, nome, cpf, rg, telefone, celular, email, datadenascimento);
        this.nomedamae = nomedamae;
        this.nomedopai = nomedopai;
        this.telefonedamae = telefonedamae;
        this.telefonedopai = telefonedopai;
        this.cpfdamae = cpfdamae;
        this.cpfdopai = cpfdopai;
        this.turma = turma;
        this.plano = plano;
        this.descricao = descricao;
        this.debito = new BigDecimal(debito.toString());
        this.valorContrato = new BigDecimal(valorContrato.toString());
        this.valorMensal = new BigDecimal(valorMensal.toString());
        this.renovacaoAutomatica = renovacaoAutomatica;
    }
    
    public Aluno(int codBanco, String nome, String cpf, String rg, String telefone, String celular, String email, Date datadenascimento, String nomedamae, String nomedopai, String telefonedamae, String telefonedopai, String cpfdamae, String cpfdopai, int turma, int plano, String descricao, BigDecimal debito, BigDecimal valorContrato, Date dataCadastro, BigDecimal valorMensal, int renovacaoAutomatica) {
        super(codBanco, nome, cpf, rg, telefone, celular, email, datadenascimento);
        this.nomedamae = nomedamae;
        this.nomedopai = nomedopai;
        this.telefonedamae = telefonedamae;
        this.telefonedopai = telefonedopai;
        this.cpfdamae = cpfdamae;
        this.cpfdopai = cpfdopai;
        this.turma = turma;
        this.plano = plano;
        this.descricao = descricao;
        this.debito = new BigDecimal(debito.toString());
        this.valorContrato = new BigDecimal(valorContrato.toString());
        ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();
        this.dataCadastro = converterData.getSqlDate(dataCadastro);
        this.valorMensal = new BigDecimal(valorMensal.toString());
        this.renovacaoAutomatica = renovacaoAutomatica;
    }
   
    //___________________________________________________________________________
    //GETTERS

    public String getNomedamae() {
        return nomedamae;
    }

    public String getNomedopai() {
        return nomedopai;
    }

    public String getTelefonedamae() {
        return telefonedamae;
    }

    public String getTelefonedopai() {
        return telefonedopai;
    }

    public String getCpfdamae() {
        return cpfdamae;
    }

    public String getCpfdopai() {
        return cpfdopai;
    }

    public int getTurma() {
        return turma;
    }

    public int getPlano() {
        return plano;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getDebito() {
        return debito;
    }

    public BigDecimal getValorContrato() {
        return valorContrato;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public BigDecimal getValorMensal() {
        return valorMensal;
    }

    public int getRenovacaoAutomatica() {
        return renovacaoAutomatica;
    }
    
    
}
