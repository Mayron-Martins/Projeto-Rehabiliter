/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Model.auxiliar.FrequenciaTurmas;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

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
    public void inserirDados (FrequenciaTurmas frequencia) throws SQLException{
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
    }
    
    
    
    //Atualizar dados
    public void atualizarDados(FrequenciaTurmas frequencia) throws SQLException{
        //atualizando a tabela de turmas
        String inFrequencia = atualizar.concat("tblFreqTurma"+frequencia.getCodTurma()
                + " SET situacao=? where codAluno = ? AND data = ?");
        
        PreparedStatement statement = gerarStatement(inFrequencia);
        statement.setString(1, frequencia.getSituacao());
        statement.setInt(2, frequencia.getCodAluno());
        statement.setDate(3, (Date) frequencia.getDataInsert());
        
        statement.execute();
        statement.close();
    }
    
    /*
    //Remover Dados
    public void removerFrequencia(FrequenciaTurmas frequencia) throws SQLException{
        //Removendo Turmas
        String inServicos = remover.concat("tblFreqTurma WHERE codAluno = ? AND data = ?");
        
        PreparedStatement statement = gerarStatement(inServicos);
        statement.setInt(1, frequencia.getCodAluno());
        statement.setDate(1, (Date) frequencia.getDataInsert());
        statement.execute();
        statement.close();
        
    }*/
    
    public ArrayList <FrequenciaTurmas> selecionarTodasFrequenciasTurmas(int codBanco) throws SQLException, ParseException{
        String inFrequencia = selecionarTudo.concat("tblFreqTurma"+codBanco);
        return pesquisarFrequencias(inFrequencia);
    }
    
    public ArrayList <FrequenciaTurmas> pesquisarFrequencias(String comando) throws SQLException, ParseException{
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
    }
 
}
