/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.adicionais;

import Controller.auxiliar.ConversaoDeDinheiro;
import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Controller.auxiliar.VerificarCodigoNoBanco;
import Dao.EnderecoFuncionarioDao;
import Dao.FuncionarioDao;
import Model.Funcionario;
import Model.auxiliar.EnderecoFuncionario;
import View.FuncionariosAdicionar;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Mayro
 */
public class AdicionarFuncionariosController {
    private final FuncionariosAdicionar view;
    private final FuncionarioDao funcionarioDao = new FuncionarioDao();
    private final EnderecoFuncionarioDao endereco = new EnderecoFuncionarioDao();
    private final ConversaoDeDinheiro converterDinheiro = new ConversaoDeDinheiro();
    private final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();
    private final VerificarCodigoNoBanco verificar = new VerificarCodigoNoBanco();

    public AdicionarFuncionariosController(FuncionariosAdicionar view) {
        this.view = view;
    }
    
    public void adicionarFuncionario() throws SQLException{
        //Dados do Gerente
        int codFuncionario = verificar.verificarUltimo("tblFuncionarios", "codFuncionario")+1;
        String nome = view.getCampoNome().getText();
        String cpf = view.getCampoCPF().getText();
        String dataNascimento;
        if(view.getCampoNascimento().getText().equals("  /  /    ")){dataNascimento=null;}
        else{dataNascimento = view.getCampoNascimento().getText();}
        
        String celular = view.getCampoCelular().getText();
        String telefone = view.getCampoTelefone().getText();
        String usuario = cpf;
        String senha = new String(view.getCampoSenha().getPassword());
        BigDecimal salario = new BigDecimal(converterDinheiro.converterParaBigDecimal(view.getCampoSalario().getText()).toString()); //Seta setado em 0 reais
        String cargo = view.getCampoCargo().getText();
        String email = view.getCampoEmail().getText();
        
        //dados do Endereço
        int codEndereco = verificar.verificarUltimo("tblEndFuncionarios", "codEndFuncionarios")+1;
        String logradouro = "";
        String bairro = "";
        String numero = "";
        String cidade = "";
        String estado = "";
        String cep = "";
        String complemento = "";
        String referencia = "";
        
        //Cria os tipos Aluno, Endereco e Matricula com os dados
        Funcionario funcionario = new Funcionario(codFuncionario, nome, cpf, "", telefone, celular, email, dataNascimento, usuario, senha, salario, cargo);
        EnderecoFuncionario endereco = new EnderecoFuncionario(codEndereco, codFuncionario, logradouro, bairro, numero, complemento, referencia, cidade, estado, cep);
        
        //Verifica se não há dados irregulares antes de colocar na tabela
        if(nome==null||dataNascimento==null|| verificarSenha() ||cpf.equals("   .   .   -  ")){
        view.exibeMensagem("Valores Preenchidos Incorretamente!");
        }
        
        else{
            funcionarioDao.inserirDados(funcionario, endereco);
            view.exibeMensagem("Sucesso!");
            view.getCampoNome().setText("");
            view.getCampoCPF().setText("");
            view.getCampoCelular().setText("");
            view.getCampoCelular().setText("");
            view.getCampoNascimento().setText("");
            view.getCampoCargo().setText("");
            view.getCampoSalario().setText("");
            view.getCampoSenha().setText("");
            
        }
        
    }

    private boolean verificarSenha() {      
    return view.getCampoSenha().getPassword()==null;
    }
    
}