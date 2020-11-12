/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.adicionais;

import Dao.FuncionarioDao;
import Model.Funcionario;
import View.LoginFuncionario;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author Mayro
 */
public class LoginFuncionarioController {
    private final LoginFuncionario view;
    private final FuncionarioDao funcionarioDao = new FuncionarioDao();

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
                view.dispose();
            }}
        } 
        }
    }
    
    
    private ArrayList<Funcionario> gerenteDoBanco(String usuario) throws SQLException, ParseException{
        return funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE usuario = '"+usuario+"'");
    }
    
}
