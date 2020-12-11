/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Model.auxiliar.Backup;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Mayro
 */
public class BackupBancoDao extends Conexao{
    private final String inserir = "INSERT INTO ";
    private final String selecionarTudo = "SELECT * FROM ";
    private ConversaodeDataParaPadraoDesignado converterData;
    
    public void inserirDados (Backup backup) throws SQLException{
        //Adicionando Turma
        String inBackups = inserir.concat("tblBackups("
                + "codBackup, nome, data, endArquivo, tabelas)"
                + "VALUES("
                + "?,?,?,?,?);");
        PreparedStatement statement = gerarStatement(inBackups);
        statement.setInt(1, backup.getCodBackup());
        statement.setString(2, backup.getNome());
        statement.setTimestamp(3, backup.getData());
        statement.setString(4, backup.getEnderecoBackup());
        statement.setString(5, backup.getTabelasBackup());
        statement.execute();
        statement.close();
    }
    
    public ArrayList <Backup> selecionarTodosBackups() throws SQLException, ParseException{
        String inBackups = selecionarTudo.concat("tblBackups");
        return pesquisarBackups(inBackups);
    }
    
    public ArrayList <Backup> pesquisarBackups(String comando) throws SQLException, ParseException{
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
    }
}
