/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.adicionais;

import Controller.auxiliar.ConversaoDeDinheiro;
import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Dao.FuncionarioDao;
import Dao.TableCriatorPosInput;
import Model.Funcionario;
import View.LoginGerente;
import View.RegistrodeGerente;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author Mayro
 */
public class AdicionarGerenteController {
    private final RegistrodeGerente view;
    private final FuncionarioDao funcionarioDao = new FuncionarioDao();
    private final ConversaoDeDinheiro converterDinheiro = new ConversaoDeDinheiro();
    private final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();
    private final LoginGerente loginView = new LoginGerente();
    private final TableCriatorPosInput criarTabelas = new TableCriatorPosInput();

    public AdicionarGerenteController(RegistrodeGerente view) {
        this.view = view;
    }
    
    public void adicionarGerente(){
        //Dados do Gerente
        String nome = view.getCampoNome().getText();
        String cpf = view.getCampoCPF().getText();
        Date dataNascimento = view.getCampoNascimento().getDate();
        
        String celular = view.getCampoCelular().getText();
        String usuario = cpf;
        String senha = new String(view.getCampoSenha().getPassword());
        BigDecimal salario = new BigDecimal("0"); //Seta setado em 0 reais
        String cargo = "Gerente";
        
        
        String telasPermitidas = "1,2,3,4,5,6,7,8,9,10";
        String status = "Inativo";
        String situacao = "Contratado";
        
        //Cria os tipos Aluno, Endereco e Matricula com os dados
        Funcionario funcionario = new Funcionario(nome, cpf, "", "", celular, "", dataNascimento, usuario, senha, salario, cargo, telasPermitidas, status, situacao);
        
        //Verifica se não há dados irregulares antes de colocar na tabela
        if(nome==null||dataNascimento==null|| verificarSenha() ||cpf.equals("   .   .   -  ")){
        view.exibeMensagem("Valores Preenchidos Incorretamente!");
        }
        
        else{
            funcionarioDao.inserirDados(funcionario);
            criarTabelas.tableLogdeAcoesdoFunc();
            view.exibeMensagem("Sucesso!");
            this.loginView.setVisible(true);
            view.dispose();
        }
        
    }

    private boolean verificarSenha() {      
    if(view.getCampoSenha().getPassword()==null||view.getCampoConfirmarSenha().getPassword()==null){return true;}
    else{
        String senha = new String(view.getCampoSenha().getPassword());
        String confirmSenha = new String(view.getCampoConfirmarSenha().getPassword());
    if(!confirmSenha.equals(senha)){return true;}
    return false;
    }
    }
    
}
