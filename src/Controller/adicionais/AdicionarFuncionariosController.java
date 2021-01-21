/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.adicionais;

import Controller.auxiliar.ConversaoDeDinheiro;
import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Controller.auxiliar.LogsSystem;
import Controller.auxiliar.VerificarCodigoNoBanco;
import Dao.EnderecoFuncionarioDao;
import Dao.FuncionarioDao;
import Dao.LogAçoesFuncionarioDao;
import Dao.TableCriatorPosInput;
import Model.Funcionario;
import Model.auxiliar.EnderecoFuncionario;
import Model.auxiliar.LogAçoesFuncionario;
import View.FuncionariosAdicionar;
import View.LoginFuncionario;
import View.LoginGerente;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Mayro
 */
public class AdicionarFuncionariosController {
    private final FuncionariosAdicionar view;
    private final FuncionarioDao funcionarioDao = new FuncionarioDao();
    private final EnderecoFuncionarioDao endereco = new EnderecoFuncionarioDao();
    private final LogAçoesFuncionarioDao logDao = new LogAçoesFuncionarioDao();
    private final ConversaoDeDinheiro converterDinheiro = new ConversaoDeDinheiro();
    private final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();
    private final VerificarCodigoNoBanco verificar = new VerificarCodigoNoBanco();
    private final TableCriatorPosInput criarTabelas = new TableCriatorPosInput();

    public AdicionarFuncionariosController(FuncionariosAdicionar view) {
        this.view = view;
    }
    
    public void adicionarFuncionario(){
        try{
            //Dados do Gerente
            int codFuncionario = (int) (verificar.verificarUltimo("tblFuncionarios", "codFuncionario")+1);
            String nome = view.getCampoNome().getText();
            String cpf = view.getCampoCPF().getText();
            Date dataNascimento = view.getCampoNascimento().getDate();

            String celular = view.getCampoCelular().getText();
            String telefone = view.getCampoTelefone().getText();
            String usuario = cpf;
            String senha = new String(view.getCampoSenha().getPassword());
            BigDecimal salario = new BigDecimal(converterDinheiro.converterParaBigDecimal(view.getCampoSalario().getText()).toString()); //Seta setado em 0 reais
            String cargo = view.getCampoCargo().getText();
            String email = view.getCampoEmail().getText();

            //dados do Endereço
            int codEndereco = (int) (verificar.verificarUltimo("tblEndFuncionarios", "codEndFuncionarios")+1);
            String logradouro = "";
            String bairro = "";
            String numero = "";
            String cidade = "";
            String estado = "";
            String cep = "";
            String complemento = "";
            String referencia = "";

            String telasPermitidas = "1,2,3,4,5,6,7,8,9";
            String status = "Inativo";
            String situacao = "Contratado";

            //Cria os tipos Aluno, Endereco e Matricula com os dados
            Funcionario funcionario = new Funcionario(codFuncionario, nome, cpf, "", telefone, celular, email, dataNascimento, usuario, senha, salario, cargo, telasPermitidas, status, situacao);
            EnderecoFuncionario endereco = new EnderecoFuncionario(codEndereco, codFuncionario, logradouro, bairro, numero, complemento, referencia, cidade, estado, cep);

            //Verifica se não há dados irregulares antes de colocar na tabela
            if(nome==null||dataNascimento==null|| verificarSenha() ||cpf.equals("   .   .   -  ")){
            view.exibeMensagem("Valores Preenchidos Incorretamente!");
            }

            else{
                funcionarioDao.inserirDados(funcionario, endereco);
                criarTabelas.tableLogdeAcoesdoFunc();

                
                this.setarLog("Cadastro de Funcionário", "Cadastrou o funcionário "+nome+" aos cargo de "+cargo);
                
                view.exibeMensagem("Sucesso!");
                view.getCampoNome().setText("");
                view.getCampoCPF().setValue(null);
                view.getCampoTelefone().setValue(null);
                view.getCampoCelular().setValue(null);
                view.getCampoCargo().setText("");
                view.getCampoSalario().setText("");
                view.getCampoSenha().setText("");
                view.getCampoNascimento().setDate(null);

            }
        } catch (SQLException ex) {
            gerarLog(ex);
            view.exibeMensagem("Não foi possível salvar o Funcionário corretamente!");
                view.getCampoNome().setText("");
                view.getCampoCPF().setValue(null);
                view.getCampoTelefone().setValue(null);
                view.getCampoCelular().setValue(null);
                view.getCampoCargo().setText("");
                view.getCampoSalario().setText("");
                view.getCampoSenha().setText("");
                view.getCampoNascimento().setDate(null);
        }
        
    }

    private boolean verificarSenha() {      
    return view.getCampoSenha().getPassword()==null;
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
    
    private Funcionario setarLog(String acao, String referencia){
        ArrayList <Funcionario> funcionarios = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE status = 'Ativo'");
        if(funcionarios!=null){
            Funcionario funcionario = funcionarios.get(0);
            Date dataEvento = new Date();
            LogAçoesFuncionario logAcao = new LogAçoesFuncionario(funcionario.getCodBanco(), dataEvento, acao, referencia);
            return funcionario;
        }
        return null;
    }
}
