/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Controller.auxiliar.LogsSystem;
import Model.auxiliar.FrequenciaTurmas;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mayro
 */
public class FrequenciaTurmasDao extends Conexao{
    private final String inserir = "INSERT INTO ";
    private final String atualizar = "UPDATE ";
    private final String remover = "DELETE FROM ";
    private final String selecionarTudo = "SELECT * FROM ";
    private ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();
    
    
    //Inserir dados na tabela de Frequeência de turma X
    public void inserirDados (FrequenciaTurmas frequencia){
        try{
            //Adicionando Turma
            String inFrequencia = inserir.concat("tblFreqTurma"+frequencia.getCodTurma()+"("
                    + "codTurma, codAluno, data, situacao)"
                    + "VALUES("
                    + "?,?,?,?);");
            PreparedStatement statement = gerarStatement(inFrequencia);
            statement.setInt(1, frequencia.getCodTurma());
            statement.setInt(2, frequencia.getCodAluno());
            statement.setDate(3, (Date) frequencia.getDataInsert());
            statement.setString(4, frequencia.getSituacao());
            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            gerarLog(ex);
        }
        
    }
    
    
    
    //Atualizar dados
    public void atualizarDados(FrequenciaTurmas frequencia){
        try{
            //atualizando a tabela de turmas
            String inFrequencia = atualizar.concat("tblFreqTurma"+frequencia.getCodTurma()
                    + " SET situacao=? where codAluno = ? AND data = ?");

            PreparedStatement statement = gerarStatement(inFrequencia);
            statement.setString(1, frequencia.getSituacao());
            statement.setInt(2, frequencia.getCodAluno());
            statement.setDate(3, (Date) frequencia.getDataInsert());

            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            gerarLog(ex);
        }
        
    }
    
    
    public ArrayList <FrequenciaTurmas> selecionarTodasFrequenciasTurmas(int codBanco){
        String inFrequencia = selecionarTudo.concat("tblFreqTurma"+codBanco);
        return pesquisarFrequencias(inFrequencia);
    }
    
    public ArrayList <FrequenciaTurmas> pesquisarFrequencias(String comando){
        try{
            ArrayList <FrequenciaTurmas> frequencias = new ArrayList();
            PreparedStatement statement = gerarStatement(comando);
            statement.execute();
            ResultSet resultset = statement.getResultSet();
            boolean next = resultset.next();

            if(next==false){
                return null;
            }

            do{
            int codTurma = resultset.getInt("codTurma");
            int codAluno = resultset.getInt("codAluno");
            String dataInpult = converterData.parseDate(resultset.getDate("data"));
            String situacao = resultset.getString("situacao");

            FrequenciaTurmas frequencia = new FrequenciaTurmas(codTurma, codAluno, dataInpult, situacao);

            frequencias.add(frequencia);
             }while(resultset.next());

            statement.close();
            return frequencias;
        } catch (SQLException ex) {
            gerarLog(ex);
            return null;
        }
        
    }
    
    //Gerar arquivo com o log de erro, caso haja
    private void gerarLog(Throwable erro){
        LogsSystem gerarLog = new LogsSystem();
        gerarLog.gravarErro(erro);
        gerarLog.close();
    }
 
}
