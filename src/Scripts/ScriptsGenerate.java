/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scripts;

import Controller.auxiliar.ExportarArquivos;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 *
 * @author Mayro
 */
public class ScriptsGenerate {
    private final BackupMaster backupMaster = new BackupMaster();
    private final ExportarArquivos exportar = new ExportarArquivos();
    private final FullBackup fullBackup = new FullBackup();
    private final StopDatabase stopDatabase = new StopDatabase();
    
    public void gerarScripts(){
        File master = new File("C:/Rehabiliter/Components/System/Scripts/Script_Master.sql");
        File full = new File("C:/Rehabiliter/Components/System/Scripts/Full_Backup.sql");
        File stop = new File("C:/Rehabiliter/Components/System/Scripts/Stop_Database.sql");
        if(!master.exists()){
            try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("C:/Rehabiliter/Components/System/Scripts/Script_Master.sql", true));  
			writer.write(backupMaster.part1());
			writer.newLine();
                        writer.write(backupMaster.part2());
			writer.newLine();
                        writer.write(backupMaster.part3());
			writer.newLine();
                        writer.write(backupMaster.part4());
			writer.newLine();
                        writer.write(backupMaster.part5());
			writer.newLine();
                        writer.write(backupMaster.part6());
			writer.newLine();
                        writer.write(backupMaster.part7());
			writer.newLine();
                        writer.write(backupMaster.part8());
			writer.newLine();
                        writer.write(backupMaster.part9());
			writer.newLine();
                        writer.write(backupMaster.part10());
			writer.newLine();
                        writer.write(backupMaster.part11());
			writer.newLine();
                        writer.write(backupMaster.part12());
			writer.newLine();
                        writer.write(backupMaster.part13());
			writer.newLine();
                        writer.flush();
                        writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
        }
        
        if(!full.exists()){
            exportar.geraArquivoTxt(this.fullBackup.script(), "C:/Rehabiliter/Components/System/Scripts/Full_Backup.sql");
        }
        
        if(!stop.exists()){
            exportar.geraArquivoTxt(stopDatabase.script(), "C:/Rehabiliter/Components/System/Scripts/Stop_Database.sql");
        }
    }
    
}
