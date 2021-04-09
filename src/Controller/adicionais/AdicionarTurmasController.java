/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.adicionais;

import Controller.auxiliar.LogsSystem;
import Controller.auxiliar.VerificarCodigoNoBanco;
import Dao.FuncionarioDao;
import Dao.LogAçoesFuncionarioDao;
import Dao.TableCriatorPosInput;
import Dao.TurmasDao;
import Model.Funcionario;
import Model.auxiliar.Horarios;
import Model.auxiliar.LogAçoesFuncionario;
import Model.auxiliar.Turmas;
import View.LoginFuncionario;
import View.LoginGerente;
import View.TurmasAdicionar;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mayro
 */
public class AdicionarTurmasController {
    private final TurmasAdicionar view;
    private final TurmasDao turmaDao = new TurmasDao();
    private final FuncionarioDao funcionarioDao = new FuncionarioDao();
    private final LogAçoesFuncionarioDao logDao = new LogAçoesFuncionarioDao();
    private final VerificarCodigoNoBanco verificar = new VerificarCodigoNoBanco();
    private final TableCriatorPosInput criarTabela = new TableCriatorPosInput();


    public AdicionarTurmasController(TurmasAdicionar view) {
        this.view = view;
    }
    
    public void adicionarTurma(){
        try{
            //Pegando Dados Da tela
            int codBancoTurma = (int) (verificar.verificarUltimo("tblTurmas", "codTurma")+1);
            int codBancoHorario = (int) (verificar.verificarUltimo("tblHorarios", "codHorario")+1);
            String nomeTurma = view.getCampoNome().getText();
            String situacao = "Aberta";

            int quantidadeMax;
            if(view.getCampoCapMax().getText().equals("")){quantidadeMax = 0;}
            else{quantidadeMax = Integer.parseInt(view.getCampoCapMax().getText());}
            String diasDaSemana = view.getDiasDaSemana();
            ArrayList <String> diasDaSemanaUnitario = new ArrayList<>();
            diasDaSemanaUnitario = view.getDiasDaSemanaUnitarios();

            String horario = view.getCampoHorario();

            Turmas turma = null;
            ArrayList <Horarios> horarios = new ArrayList <>();
            if(!view.getCampoHorario().equals("")){
                turma = new Turmas(nomeTurma, 0, quantidadeMax, diasDaSemana, horario, situacao);    

                int diferenca, contador = diasDaSemanaUnitario.size();
                while(contador>0){
                    diferenca = diasDaSemanaUnitario.size()-contador;
                    Horarios auxiliar = new Horarios(codBancoHorario, diasDaSemanaUnitario.get(diferenca), horario, 0, codBancoTurma);
                    horarios.add(auxiliar);
                    contador--;
                }
            }

            //Inserindo Dados
            if(nomeTurma.equals("")|| horario.equals("") ||view.testeValidacaoHorario()==false){
             view.exibeMensagem("Campos Preenchidos Incorretamente");
            } else{
                turmaDao.inserirDados(turma, horarios);
                criarTabela.tableFreqTurmas();

                
                this.setarLog("Cadastro de Turmas", "Cadastrou a turma "+nomeTurma);
                
                view.exibeMensagem("Sucesso!");
                limparCampos();
            }
        } catch (SQLException ex) {
            gerarLog(ex);
            view.exibeMensagem("Não foi possível salvar a Turma corretamente!");
            limparCampos();
        }
         
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
    
    public void limparCampos(){
        //Limpando Campos
        view.getCampoNome().setText("");
        view.getCampoHoras().setValue(null);
        view.getCampoMinutos().setValue(null);
        view.getCampoCapMax().setText("");
        view.desmarcarCaixas();
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
   
   private void gerarLog(Throwable erro){
        LogsSystem gerarLog = new LogsSystem();
        gerarLog.gravarErro(erro);
        gerarLog.close();
    }
}
