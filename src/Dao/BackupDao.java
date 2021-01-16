/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Controller.auxiliar.LogsSystem;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mayro
 */
public class BackupDao extends ConexaoMaster{
    private final String user = "sa";
    private final String pass = "164829";
    private final DatabaseCriator dataBaseCriator = new DatabaseCriator();
    
    public void exportarBanco(){
        try{
            PreparedStatement statement = gerarStatement("USE master;"
                    + "BACKUP DATABASE Rehabiliter_Database TO DISK = '"+System.getProperty("user.home")+"\\documents\\Rehabiliter\\Backups\\Local\\LocalBackup.bak'");
            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            gerarLog(ex);
        }        
        
    }
    
    public void importarBanco(){
        try{
            PreparedStatement statement;
            if(dataBaseCriator.databaseConfirmed(user, pass)){
                statement = gerarStatement("USE master;  "
                        + "ALTER DATABASE Rehabiliter_Database SET single_user with rollback immediate;"
                        + "DROP DATABASE Rehabiliter_Database");
                statement.execute(); 
                statement.close();
            }
            statement = gerarStatement("RESTORE DATABASE Rehabiliter_Database FROM DISK= '"+System.getProperty("user.home")+"\\documents\\Rehabiliter\\Backups\\Local\\LocalBackup.bak'");
            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            gerarLog(ex);
        }
        
    }
    
    //Gerar arquivo com o log de erro, caso haja
    private void gerarLog(Throwable erro){
        LogsSystem gerarLog = new LogsSystem();
        gerarLog.gravarErro(erro);
        gerarLog.close();
    }
}
