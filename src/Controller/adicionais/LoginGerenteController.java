/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.adicionais;

import Dao.FuncionarioDao;
import Model.Funcionario;
import View.LoginGerente;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author Mayro
 */
public class LoginGerenteController {
    private final LoginGerente view;
    private final FuncionarioDao funcionarioDao = new FuncionarioDao();

    public LoginGerenteController(LoginGerente view) {
        this.view = view;
    }
    
    public void entrarSistema() throws SQLException, ParseException{
        ArrayList<Funcionario> gerente = this.gerenteDoBanco();
        String usuario = view.getCampoCpfFuncionario().getText();
        String senha = new String(view.getCampoSenha().getPassword());
        
        if(gerente==null){
            view.exibeMensagem("Necessário Cadastro de Gerente Para Continuar!");
            view.getBotaoCadastrar().setVisible(true);
        }
        else{
            String usuarioBanco = gerente.get(0).getUsuario();
            String senhaBanco = gerente.get(0).getSenha();
            if(senha.equals("")||usuario.equals("")||!usuario.equals(usuarioBanco)||!senha.equals(senhaBanco)){
                view.senhaErrada();
            }
            else{
                view.getTelagerente().setVisible(true);
                view.dispose();
            }
        }
    }
    
    
    private ArrayList<Funcionario> gerenteDoBanco() throws SQLException, ParseException{
        String cargo = "Gerente"; 
        return funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE cargo = 'Gerente'");
    }
}
