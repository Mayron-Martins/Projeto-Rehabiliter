/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.adicionais;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Controller.auxiliar.LogsSystem;
import Controller.auxiliar.VerificarCodigoNoBanco;
import Dao.BackupBancoDao;
import Dao.BackupDao;
import View.BackupView;
import Model.auxiliar.Backup;
import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mayro
 */
public class BackupController {
    private final BackupView view;
    private final BackupDao backupDao = new BackupDao();
    private final BackupBancoDao backupBancoDao= new BackupBancoDao();
    private final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();
    private final VerificarCodigoNoBanco verificar = new VerificarCodigoNoBanco();

    public BackupController(BackupView view) {
        this.view = view;
    }
    
    public void inserirUltimoBackup(){
        try{
            ArrayList <Backup> backups = backupBancoDao.selecionarTodosBackups();
            view.getCampoDataLocal().setText("");
            view.getCampoHoraLocal().setText("");
            view.getCampoLocalizacaoLocal().setText("");
            if(backups!=null){
                String data = converterData.parseDateAndTime(converterData.parseDateAndTime(backups.get(backups.size()-1).getData()));
                String[] split = data.split(" ");
                view.getCampoDataLocal().setText(split[0]);
                view.getCampoHoraLocal().setText(split[1]);
                view.getCampoLocalizacaoLocal().setText(backups.get(backups.size()-1).getEnderecoBackup());
            }
            else{
                view.getCampoDataLocal().setText("Sem Dados");
                view.getCampoHoraLocal().setText("Sem Dados");
                view.getCampoLocalizacaoLocal().setText("Sem Dados");
            }
        } catch (SQLException | ParseException ex) {
            this.gerarLog(ex);
        }
        
    }
    
    public void adicionarDadosnoBanco(){
            try {
            Date dataAtual = new Date();
            String nome = "LocalBackup";
            int codBackup = verificar.verificarUltimo("tblBackups", "codBackup")+1;
            String endereco = System.getProperty("user.home")+"/documents/Rehabiliter/Backups/Local/LocalBackup.bak";
            String tabelas = "Todas";

            Backup backup = new Backup(codBackup, nome, dataAtual, endereco, tabelas);
            backupDao.exportarBanco();
            backupBancoDao.inserirDados(backup);
            view.exibeMensagem("Sucesso!");
            inserirUltimoBackup();
        } catch (SQLException ex) {
            this.gerarLog(ex);
        }
        
    }
    
    public void importarBanco() throws SQLException{
        File file = new File(System.getProperty("user.home")+"/documents/Rehabiliter/Backups/Local/LocalBackup.bak");
        if(file.exists()){
            backupDao.importarBanco();
            view.exibeMensagem("Sucesso!");
            inserirUltimoBackup();
        }
        else{
            view.exibeMensagem("Realize um Backup Primeiro");
        }
    }
    
    private void gerarLog(Throwable erro){
        LogsSystem gerarLog = new LogsSystem();
        gerarLog.gravarErro(erro);
        gerarLog.close();
    }
}
