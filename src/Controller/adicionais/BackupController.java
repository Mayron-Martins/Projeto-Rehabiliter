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
import Dao.FuncionarioDao;
import Dao.LogAçoesFuncionarioDao;
import Model.Funcionario;
import View.BackupView;
import Model.auxiliar.Backup;
import Model.auxiliar.LogAçoesFuncionario;
import View.LoginFuncionario;
import View.LoginGerente;
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
    private final FuncionarioDao funcionarioDao = new FuncionarioDao();
    private final LogAçoesFuncionarioDao logDao = new LogAçoesFuncionarioDao();
    private final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();
    private final VerificarCodigoNoBanco verificar = new VerificarCodigoNoBanco();

    public BackupController(BackupView view) {
        this.view = view;
    }
    
    public void inserirUltimoBackup(){
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
        
    }
    
    public void adicionarDadosnoBanco(){
            try {
            Date dataAtual = new Date();
            String nome = "LocalBackup";
            int codBackup = (int) (verificar.verificarUltimo("tblBackups", "codBackup")+1);
            String endereco = System.getProperty("user.home")+"/documents/Rehabiliter/Backups/Local/";
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
    
    public void importarBanco(){
        String endereco = backupDao.buscarBackup();
        if(endereco!=null){
            backupDao.importarBanco(endereco);
            view.exibeMensagem("Sucesso!");
            inserirUltimoBackup();
        }
    }
    
    private void gerarLog(Throwable erro){
        LogsSystem gerarLog = new LogsSystem();
        gerarLog.gravarErro(erro);
        gerarLog.close();
    }
    
    public void sairTela(){
        funcionarioDao.atualizarStatusAll();
        Funcionario funcionario = this.setarLog("Saída do Sistema", null);
        view.getParent().dispose();
        if(funcionario==null||!funcionario.getCargo().equals("Gerente")){
            LoginFuncionario jump = new LoginFuncionario();
            jump.setVisible(true);
        }
        else{
            LoginGerente jump = new LoginGerente();
            jump.setVisible(true);
        }
    }
    
    private Funcionario setarLog(String acao, String descricao){
        ArrayList <Funcionario> funcionarios = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE status = 'Ativo'");
        if(funcionarios!=null){
            Funcionario funcionario = funcionarios.get(0);
            Date dataEvento = new Date();
            LogAçoesFuncionario logAcao = new LogAçoesFuncionario(funcionario.getCodBanco(), dataEvento, acao, descricao);
            logDao.inserirDados(logAcao);
            return funcionario;
        }
        return null;
    }
}
