/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Controller.auxiliar.LogsSystem;
import Model.auxiliar.Backup;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Mayro
 */
public class BackupBancoDao extends Conexao{
    private final String inserir = "INSERT INTO ";
    private final String selecionarTudo = "SELECT * FROM ";
    private ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();
    
    public void inserirDados (Backup backup){
        try{
            //Adicionando Turma
            String inBackups = inserir.concat("tblBackups("
                    + "nome, data, endArquivo, tabelas)"
                    + "VALUES("
                    + "?,?,?,?);");
            PreparedStatement statement = gerarStatement(inBackups);
            statement.setString(1, backup.getNome());
            statement.setTimestamp(2, backup.getData());
            statement.setString(3, backup.getEnderecoBackup());
            statement.setString(4, backup.getTabelasBackup());
            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            gerarLog(ex);
        }
        
    }
    
    public ArrayList <Backup> selecionarTodosBackups(){
        String inBackups = selecionarTudo.concat("tblBackups");
        return pesquisarBackups(inBackups);
    }
    
    public ArrayList <Backup> pesquisarBackups(String comando){
        try{
            ArrayList <Backup> backups = new ArrayList();
            PreparedStatement statement = gerarStatement(comando);
            statement.execute();
            ResultSet resultset = statement.getResultSet();
            boolean next = resultset.next();

            if(next==false){
                return null;
            }

            do{
            int codBackup = resultset.getInt("codBackup");
            String nome = resultset.getString("nome");
            Date data = converterData.parseDateAndTime(resultset.getTimestamp("data"));
            String enderecoBackup = resultset.getString("endArquivo");
            String tabelas = resultset.getString("tabelas");

            Backup backup = new Backup(codBackup, nome, data, enderecoBackup, tabelas);


            backups.add(backup);
             }while(resultset.next());

            statement.close();
            return backups;
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
