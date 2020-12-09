/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.adicionais;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Dao.FrequenciaFuncionariosDao;
import Dao.FuncionarioDao;
import Model.Funcionario;
import Model.auxiliar.FrequenciaFuncionarios;
import View.LoginFuncionario;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Mayro
 */
public class LoginFuncionarioController {
    private final LoginFuncionario view;
    private final FuncionarioDao funcionarioDao = new FuncionarioDao();
    private final FrequenciaFuncionariosDao frequencia = new FrequenciaFuncionariosDao();
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
                view.dispose();
            }}
        } 
        }
    }
    
    
    private ArrayList<Funcionario> gerenteDoBanco(String usuario) throws SQLException, ParseException{
        return funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE usuario = '"+usuario+"'");
    }
    
    private void setarPresencaFuncionario(String usuario) throws SQLException, ParseException{
        ArrayList <Funcionario> funcionarios = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE usuario = '"+usuario+"'");
        
        LocalDate dataAtual = LocalDate.now();
        String dataInput = converterData.parseDate(converterData.conversaoLocalforDate(dataAtual));
        for(int linhas=0; linhas <funcionarios.size(); linhas++){
            FrequenciaFuncionarios frequenciaFuncionarios = new FrequenciaFuncionarios(funcionarios.get(linhas).getCodBanco(), dataInput, "P");
            frequencia.atualizarDados(frequenciaFuncionarios);
        }
    }
}
