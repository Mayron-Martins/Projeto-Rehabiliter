/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Controller.auxiliar.LogsSystem;
import Scripts.Sqlcmd;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Mayro
 */
public class BackupDao extends ConexaoMaster{
    private final String user = "sa";
    private final String pass = "164829";
    private final DatabaseCriator dataBaseCriator = new DatabaseCriator();
    private final Sqlcmd sqlcmd = new Sqlcmd();
    
    public void exportarBanco(){
        /*try{
            PreparedStatement statement = gerarStatement("USE master;"
                    + "BACKUP DATABASE Rehabiliter_Database TO DISK = '"+System.getProperty("user.home")+"\\documents\\Rehabiliter\\Backups\\Local\\LocalBackup.bak'");
            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            gerarLog(ex);
        } */       
        sqlcmd.executeFullBackup();
    }
    
    public void importarBanco(String endereco){
        try{
            
            PreparedStatement statement;
            if(dataBaseCriator.databaseConfirmed(user, pass)){
                sqlcmd.executeStopDatabase();
                statement = gerarStatement("USE master;  "
                        + "DROP DATABASE Rehabiliter_Database");
                statement.execute(); 
                statement.close();
            }
            statement = gerarStatement("RESTORE DATABASE Rehabiliter_Database FROM DISK= '"+endereco+"'");
            statement.execute();
            statement.close();
            
        } catch (SQLException ex) {
            gerarLog(ex);
        }
        
    }
    
    public String buscarBackup(){
        FileNameExtensionFilter extensao = new FileNameExtensionFilter("Arquivos do SQL", "bak");
        
        JFileChooser arquivoEscolhido = new JFileChooser();
        arquivoEscolhido.setFileFilter(extensao);
        arquivoEscolhido.setCurrentDirectory(new File(System.getProperty("user.home")+"/documents/Rehabiliter/Backups/Local"));
        arquivoEscolhido.setDialogTitle("Escolha o arquivo de Backup");
        arquivoEscolhido.setFileSelectionMode(JFileChooser.FILES_ONLY);
        //arquivoEscolhido.setMultiSelectionEnabled(false);
        int resposta = arquivoEscolhido.showOpenDialog(null);
        
        if(resposta == JFileChooser.APPROVE_OPTION){
            return arquivoEscolhido.getSelectedFile().getAbsolutePath();
        }
        return null;
    }
    //Gerar arquivo com o log de erro, caso haja
    private void gerarLog(Throwable erro){
        LogsSystem gerarLog = new LogsSystem();
        gerarLog.gravarErro(erro);
        gerarLog.close();
    }
}
