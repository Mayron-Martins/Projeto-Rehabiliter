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
import View.LoginGerente;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Mayro
 */
public class LoginGerenteController {
    private final LoginGerente view;
    private final FuncionarioDao funcionarioDao = new FuncionarioDao();
    private final FrequenciaFuncionariosDao frequencia = new FrequenciaFuncionariosDao();
    private final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();

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
            if(senha.equals("")||usuario.equals("   .   .   -  ")||!usuario.equals(usuarioBanco)||!senha.equals(senhaBanco)){
                view.senhaErrada();
            }
            else{
                view.getTelagerente().setVisible(true);
                this.setarPresencaFuncionario(usuario);
                view.dispose();
            }
        }
    }
    
    
    private ArrayList<Funcionario> gerenteDoBanco() throws SQLException, ParseException{
        String cargo = "Gerente"; 
        return funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE cargo = 'Gerente'");
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
