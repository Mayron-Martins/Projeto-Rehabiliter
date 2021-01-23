/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scripts;

import Controller.auxiliar.ExportarArquivos;
import Controller.auxiliar.LogsSystem;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Mayro
 */
public class Sqlcmd {
    private final ExportarArquivos exportar = new ExportarArquivos();
    
    public void gerarBats(){
        //Comandos
        String masterBat = "sqlcmd -S DESKTOP-61BKSM4\\SQLEXPRESS -E -i \"C:/Rehabiliter/Components/System/Scripts/Script_Master.sql\" -o \"C:/Rehabiliter/Components/Logs/Script_Master.log\"";
        String fullBat = "sqlcmd -S DESKTOP-61BKSM4\\SQLEXPRESS -E -i \"C:/Rehabiliter/Components/System/Scripts/Full_Backup.sql\" -o \"C:/Rehabiliter/Components/Logs/Full_Backup.log\"";
        String stopBat = "sqlcmd -S DESKTOP-61BKSM4\\SQLEXPRESS -E -i \"C:/Rehabiliter/Components/System/Scripts/Stop_Database.sql\" -o \"C:/Rehabiliter/Components/Logs/Stop_Database.log\"";
        
        //Arquivos
        File master = new File("C:/Rehabiliter/Components/System/Scripts/Bat_Master.bat");
        File full = new File("C:/Rehabiliter/Components/System/Scripts/Bat_Full.bat");
        File stop = new File("C:/Rehabiliter/Components/System/Scripts/Bat_Stop.bat");
        
        //Verificação de não existência
        
        if(!master.exists()){
            exportar.geraArquivoTxt(masterBat, "C:/Rehabiliter/Components/System/Scripts/Bat_Master.bat");
        }
        if(!full.exists()){
            exportar.geraArquivoTxt(fullBat, "C:/Rehabiliter/Components/System/Scripts/Bat_Full.bat");
        }
        if(!stop.exists()){
            exportar.geraArquivoTxt(stopBat, "C:/Rehabiliter/Components/System/Scripts/Bat_Stop.bat");
        }
    }
    
    public void executeMaster(){
        try {
            Runtime.getRuntime().exec("cmd.exe /C \" C:/Rehabiliter/Components/System/Scripts/Bat_Master.bat");
        } catch (IOException ex) {
            gerarLog(ex);
        }
    }
    
    public void executeFullBackup(){
        try {
            Runtime.getRuntime().exec("cmd.exe /C \" C:/Rehabiliter/Components/System/Scripts/Bat_Full.bat");
        } catch (IOException ex) {
            gerarLog(ex);
        }
    }
    
    public void executeStopDatabase(){
        try {
            Runtime.getRuntime().exec("cmd.exe /C \" C:/Rehabiliter/Components/System/Scripts/Bat_Stop.bat");
        } catch (IOException ex) {
            gerarLog(ex);
        }
    }
    
    public void gerarBatsOnline(){
        String exporterAzure = "sqlpackage.exe /Action:Export "
                + "/ssn:tcp:rehabiliterserver.database.windows.net,1433 "
                + "/sdn: Rehabiliter_Database /su:adm61637659318 /sp:Rehab164819 "
                + "/tf:\""+System.getProperty("user.home")+"/documents/Rehabiliter/Backups/Nuvem/Backup_Nuvem.bacpac\" /p:Storage=File";
        
        String importerAzure = "sqlpackage.exe /Action:Import /tsn:tcp:rehabiliterserver.database.windows.net,1433 "
                + "/tdn:Rehabiliter_Database /tu:adm61637659318 /tp:Rehab164819 "
                + "/sf:\""+System.getProperty("user.home")+"/documents/Rehabiliter/Backups/Nuvem/Backup_Nuvem.bacpac\"  /p:Storage=File";
        
        File exporterBac = new File("C:/Rehabiliter/Components/System/Scripts/Bat_ExporterAzure.bat");
        File importerBac = new File("C:/Rehabiliter/Components/System/Scripts/Bat_ImporterAzure.bat");
        
        if(!exporterBac.exists()){
            exportar.geraArquivoTxt(exporterAzure, "C:/Rehabiliter/Components/System/Scripts/Bat_ExporterAzure.bat");
        }
        if(!importerBac.exists()){
            exportar.geraArquivoTxt(importerAzure, "C:/Rehabiliter/Components/System/Scripts/Bat_ImporterAzure.bat");
        }
    }
    
    public void executarExporterAzure(){
        try {
            Runtime.getRuntime().exec("cmd.exe /C \" C:/Rehabiliter/Components/System/Scripts/Bat_ExporterAzure.bat");
        } catch (IOException ex) {
            gerarLog(ex);
        }
    }
    
    public void executarImporterAzure(){
        try {
            Runtime.getRuntime().exec("C:/Rehabiliter/Components/System/Scripts/Bat_ImporterAzure.bat");
        } catch (IOException ex) {
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
