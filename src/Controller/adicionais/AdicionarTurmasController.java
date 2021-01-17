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
            int codBancoTurma = verificar.verificarUltimo("tblTurmas", "codTurma")+1;
            int codBancoHorario = verificar.verificarUltimo("tblHorarios", "codHorario")+1;
            String nomeTurma = view.getCampoNome().getText();

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
            turma = new Turmas(codBancoTurma, nomeTurma, 0, quantidadeMax, diasDaSemana, horario);    


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

                ArrayList <Funcionario> funcionarios = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE status = 'Ativo'");
                if(funcionarios!=null){
                    this.setarLog(funcionarios, nomeTurma);
                }
                view.exibeMensagem("Sucesso!");
                //Limpando Campos
                view.getCampoNome().setText("");
                view.getCampoHoras().setText("");
                view.getCampoMinutos().setText("");
                view.getCampoCapMax().setText("");
                view.desmarcarCaixas();
            }
        } catch (SQLException ex) {
            gerarLog(ex);
        }
         
    }
    
   private LogAçoesFuncionario setarLog(ArrayList <Funcionario> funcionarios, String nomeTurma){
        Funcionario funcionario = funcionarios.get(0);
        Date dataEvento = new Date();
        LogAçoesFuncionario logAcao = new LogAçoesFuncionario(funcionario.getCodBanco(), dataEvento, "Cadastro de Turmas", "Cadastrou a turma "+nomeTurma);
        return logAcao;
    }
   
   private void gerarLog(Throwable erro){
        LogsSystem gerarLog = new LogsSystem();
        gerarLog.gravarErro(erro);
        gerarLog.close();
    }
}
