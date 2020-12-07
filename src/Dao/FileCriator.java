/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import java.io.File;

/**
 *
 * @author Mayro
 */
public class FileCriator {
    
    //Função para criar pastas
    //OBS.: adicionar pastas filhas conforme necessário
    public void fileCriator(){
        File pastapai = new File("C:/Rehabiliter");
        File pastaDatabase = new File("C:/Rehabiliter/Databases");
        
        if(!pastapai.exists()){
            pastapai.mkdirs();
            pastaDatabase.mkdirs();
        }
    }
    
    public void fileDocCriator(){
        File pastaPai = new File(System.getProperty("user.home")+"/documents/Rehabiliter");
        File pastaBackup = new File(System.getProperty("user.home")+"/documents/Rehabiliter/Backups");
        File pastaBackupInterno = new File(System.getProperty("user.home")+"/documents/Rehabiliter/Backups/Local");
        File PastaBackupNuvem = new File(System.getProperty("user.home")+"/documents/Rehabiliter/Backups/Nuvem");
        File pastaExportacoes = new File(System.getProperty("user.home")+"/documents/Rehabiliter/Exportações");
        File pastaExportacoesFrequenciasTurmas = new File(System.getProperty("user.home")+"/documents/Rehabiliter/Exportações/Frequencia Turmas");
        File pastaExportacoesOrcamentario = new File(System.getProperty("user.home")+"/documents/Rehabiliter/Exportações/Relatórios Orçamentais");
        File pastaExportacoesFuncionarios = new File(System.getProperty("user.home")+"/documents/Rehabiliter/Exportações/Relatórios Funcionários");
        
        
        if(!pastaPai.exists()){pastaPai.mkdirs();}
        if(!pastaBackup.exists()){pastaBackup.mkdirs();}
        if(!pastaBackupInterno.exists()){pastaBackupInterno.mkdirs();}
        if(!PastaBackupNuvem.exists()){PastaBackupNuvem.mkdirs();}
        if(!pastaExportacoes.exists()){pastaExportacoes.mkdirs();}
        if(!pastaExportacoesFrequenciasTurmas.exists()){pastaExportacoesFrequenciasTurmas.mkdirs();}
        if(!pastaExportacoesOrcamentario.exists()){pastaExportacoesOrcamentario.mkdirs();}
        if(!pastaExportacoesFuncionarios.exists()){pastaExportacoesFuncionarios.mkdirs();}
    }
}

