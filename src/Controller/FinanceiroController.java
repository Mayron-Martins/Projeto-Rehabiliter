/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Dao.FuncionarioDao;
import Dao.LogAçoesFuncionarioDao;
import Model.Funcionario;
import Model.auxiliar.LogAçoesFuncionario;
import View.Financeiro;
import View.LoginFuncionario;
import View.LoginGerente;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Mayro
 */
public class FinanceiroController {
    private final Financeiro view;
    private final FuncionarioDao funcionarioDao = new FuncionarioDao();
    private final LogAçoesFuncionarioDao logDao = new LogAçoesFuncionarioDao();

    public FinanceiroController(Financeiro view) {
        this.view = view;
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
