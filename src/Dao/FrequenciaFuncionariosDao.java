/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Controller.auxiliar.LogsSystem;
import Model.auxiliar.FrequenciaFuncionarios;
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
public class FrequenciaFuncionariosDao extends Conexao{
    private final String inserir = "INSERT INTO ";
    private final String atualizar = "UPDATE ";
    private final String remover = "DELETE FROM ";
    private final String selecionarTudo = "SELECT * FROM ";
    private ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();
    
    
    //Inserir dados na tabela de Frequeência de turma X
    public void inserirDados (FrequenciaFuncionarios frequencia){
        try{
            //Adicionando Turma
            String inFrequencia = inserir.concat("tblFreqFuncionarios("
                    + "data, codFuncionario, situacao)"
                    + "VALUES("
                    + "?,?,?);");
            PreparedStatement statement = gerarStatement(inFrequencia);
            statement.setDate(1, (Date) frequencia.getDataInsert());
            statement.setInt(2, frequencia.getCodFuncinario());
            statement.setString(3, frequencia.getSituacao());
            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            gerarLog(ex);
        }
        
    }
    
    
    
    //Atualizar dados
    public void atualizarDados(FrequenciaFuncionarios frequencia){
        try{
            //atualizando a tabela de turmas
            String inFrequencia = atualizar.concat("tblFreqFuncionarios "
                    + "SET situacao=? where codFuncionario = ? AND data = ?");

            PreparedStatement statement = gerarStatement(inFrequencia);
            statement.setString(1, frequencia.getSituacao());
            statement.setInt(2, frequencia.getCodFuncinario());
            statement.setDate(3, (Date) frequencia.getDataInsert());

            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            gerarLog(ex);
        }
        
    }    
    
    public ArrayList <FrequenciaFuncionarios> selecionarTodasFrequenciasFuncionarias(){
        String inFrequencia = selecionarTudo.concat("tblFreqFuncionarios");
        return pesquisarFrequencias(inFrequencia);
    }
    
    public ArrayList <FrequenciaFuncionarios> pesquisarFrequencias(String comando){
        try{
            ArrayList <FrequenciaFuncionarios> frequencias = new ArrayList();
            PreparedStatement statement = gerarStatement(comando);
            statement.execute();
            ResultSet resultset = statement.getResultSet();
            boolean next = resultset.next();

            if(next==false){
                return null;
            }

            do{
            int codFuncionario = resultset.getInt("codFuncionario");
            String dataInpult = converterData.parseDate(resultset.getDate("data"));
            String situacao = resultset.getString("situacao");

            FrequenciaFuncionarios frequencia = new FrequenciaFuncionarios(codFuncionario, dataInpult, situacao);

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
