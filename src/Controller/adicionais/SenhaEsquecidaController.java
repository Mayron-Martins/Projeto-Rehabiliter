/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.adicionais;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Controller.auxiliar.ExportarArquivos;
import Dao.FuncionarioDao;
import Model.Funcionario;
import View.Paineis.LoginSenhaEsquecida;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Mayro
 */
public class SenhaEsquecidaController {
    private final LoginSenhaEsquecida view;
    private final FuncionarioDao funcionarioDao = new FuncionarioDao();
    private final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();
    private final ExportarArquivos exportar = new ExportarArquivos();

    public SenhaEsquecidaController(LoginSenhaEsquecida view) {
        this.view = view;
    }
    
    public void recuperarSenha(){
        String cpf = view.getCampoCPF().getText();
        
        Date dataNacimento = converterData.getSqlDate(view.getCampoDataNascimento().getDate());
        String celular = view.getCampoCelular().getText();
        String email = view.getCampoEmail().getText();
        String senha;
        String informacoes;
        
        if(view.getCampoCelular().isEnabled()){
            if(!cpf.replace(".", "").replace("-", "").trim().equals("")&&dataNacimento!=null&&
                    !celular.replace(".", "").replace("-", "").trim().equals("")){
                ArrayList <Funcionario> funcionarios = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE usuario = '"+cpf+"' AND "
                        + "dataNascimento = '"+dataNacimento+"' AND celular = '"+celular+"'");
                if(funcionarios!=null){
                    Funcionario funcionario = funcionarios.get(0);
                    senha = funcionario.getSenha();
                    informacoes = "CPF: "+cpf+"\n"+
                            "Data de Nascimento: "+converterData.parseDate(dataNacimento)+"\n"+
                            "Celular: "+celular+"\n"+
                            "Sua senha é "+senha;
                    
                    exportar.geraArquivoTxt(informacoes,"C:/Rehabiliter/Senha.txt");
                    view.exibeMensagem("Arquivo Senha.txt gerado em C:/Rehabiliter");
                    this.limparCampos();
                    view.dispose();
                }
                else{
                    view.exibeMensagem("Funcionário Não Encontrado!");
                }
                
            }else{
                view.exibeMensagem("Valores Preenchidos Incorretamente");
            }
        }
        else{
            if(!email.trim().equals("")&&dataNacimento!=null&&
                    !celular.replace(".", "").replace("-", "").trim().equals("")){
                ArrayList <Funcionario> funcionarios = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE usuario = '"+cpf+"' AND "
                        + "dataNascimento = '"+dataNacimento+"' AND email = '"+email+"'");
                if(funcionarios!=null){
                    Funcionario funcionario = funcionarios.get(0);
                    senha = funcionario.getSenha();
                    informacoes = "CPF: "+cpf+"\n"+
                            "Data de Nascimento: "+converterData.parseDate(dataNacimento)+"\n"+
                            "E-mail: "+email+"\n"+
                            "Sua senha é "+senha;
                    
                    exportar.geraArquivoTxt(informacoes, "C:/Rehabiliter/Senha.txt");
                    view.exibeMensagem("Arquivo Senha.txt gerado em C:/Rehabiliter");
                    this.limparCampos();
                    view.dispose();
                }
                else{
                    view.exibeMensagem("Funcionário Não Encontrado!");
                }
                
            }else{
                view.exibeMensagem("Valores Preenchidos Incorretamente");
            }
        }
    }
    
    private void limparCampos(){
        view.getCampoCPF().setValue(null);
        view.getCampoDataNascimento().setDate(null);
        view.getCampoCelular().setValue(null);
        view.getCampoEmail().setText("");
    }
}
