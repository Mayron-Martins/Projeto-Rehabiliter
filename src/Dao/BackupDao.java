/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Mayro
 */
public class BackupDao extends ConexaoMaster{
    private final String user = "sa";
    private final String pass = "164829";
    private final DatabaseCriator dataBaseCriator = new DatabaseCriator();
    
    public void exportarBanco(String data) throws SQLException{
        PreparedStatement statement = gerarStatement("BACKUP DATABASE Rehabiliter_Database TO DISK = '"+System.getProperty("user.home")+"/documents/Rehabiliter/Backups/Local/LocalBackup.bkk'");
        statement.execute();
        statement.close();
    }
    
    public void importarBanco() throws SQLException{
        PreparedStatement statement;
        if(dataBaseCriator.databaseConfirmed(user, pass)){
            statement = gerarStatement("DROP DATABASE Rehabiliter_Database");
            statement.execute();
            statement.close();
        }
        statement = gerarStatement("RESTORE DATABASE Rehabiliter_Database FROM DISK: '"+System.getProperty("user.home")+"/documents/Rehabiliter/Backups/Local/LocalBackup.bkk'");
        statement.execute();
        statement.close();
    }
}
