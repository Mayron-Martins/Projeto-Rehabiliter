/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.adicionais;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Dao.FrequenciaFuncionariosDao;
import Dao.FuncionarioDao;
import Dao.LogAçoesFuncionarioDao;
import Model.Funcionario;
import Model.auxiliar.FrequenciaFuncionarios;
import Model.auxiliar.LogAçoesFuncionario;
import View.LoginFuncionario;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Mayro
 */
public class LoginFuncionarioController {
    private final LoginFuncionario view;
    private final FuncionarioDao funcionarioDao = new FuncionarioDao();
    private final FrequenciaFuncionariosDao frequencia = new FrequenciaFuncionariosDao();
    private final LogAçoesFuncionarioDao logDao = new LogAçoesFuncionarioDao();
    private final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();

    public LoginFuncionarioController(LoginFuncionario view) {
        this.view = view;
    }
    
    public void entrarSistema() throws SQLException, ParseException{
        String usuario = view.getCampoCpfFuncionario().getText();
        String senha = new String(view.getCampoSenha().getPassword());
        ArrayList <Funcionario> funcionario = this.gerenteDoBanco(usuario);
        if(senha.equals("")||usuario.equals("   .   .   -  ")){
            view.senhaErrada();
        }
        else{
           if(funcionario==null){
            view.exibeMensagem("Usuário Não Encontrado!");
        }
        else{
            if(funcionario.get(0).getCargo().equals("Gerente")){view.exibeMensagem("Por favor, realize o login como Gerente!");}   
            else{String senhaBanco = funcionario.get(0).getSenha();
            if(!senha.equals(senhaBanco)){
                view.senhaErrada();
            }
            else{
                view.getTelaInicioFuncionario().setVisible(true);
                this.setarPresencaFuncionario(usuario);
                funcionarioDao.atualizarStatusAll();
                funcionarioDao.atualizarStatus(usuario);
                logDao.inserirDados(this.setarLog(usuario));
                view.dispose();
            }}
        } 
        }
    }
    
    
    private ArrayList<Funcionario> gerenteDoBanco(String usuario) throws SQLException, ParseException{
        return funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE usuario = '"+usuario+"'");
    }
    
    private void setarPresencaFuncionario(String usuario) throws SQLException, ParseException{
        Funcionario funcionario = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE usuario = '"+usuario+"'").get(0);
        
        LocalDate dataAtual = LocalDate.now();
        String dataInput = converterData.parseDate(converterData.conversaoLocalforDate(dataAtual));
            FrequenciaFuncionarios frequenciaFuncionarios = new FrequenciaFuncionarios(funcionario.getCodBanco(), dataInput, "P");
            frequencia.atualizarDados(frequenciaFuncionarios);
    }
    
    private LogAçoesFuncionario setarLog(String usuario) throws SQLException, ParseException{
        Funcionario funcionario = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE usuario = '"+usuario+"'").get(0);
        Date dataEvento = new Date();
        
        LogAçoesFuncionario logAcao = new LogAçoesFuncionario(funcionario.getCodBanco(), dataEvento, "Entrada no Sistema", null);
        return logAcao;
    } 
}
