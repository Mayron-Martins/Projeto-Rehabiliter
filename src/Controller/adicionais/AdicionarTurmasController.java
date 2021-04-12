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
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;

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
    private boolean testeHorario;


    public AdicionarTurmasController(TurmasAdicionar view) {
        this.testeHorario = false;
        this.view = view;
    }
    
    public void adicionarTurma(){
        try{
            //Pegando Dados Da tela
            int codBancoTurma = (int) (verificar.verificarUltimo("tblTurmas", "codTurma")+1);
            int codBancoHorario = (int) (verificar.verificarUltimo("tblHorarios", "codHorario")+1);
            String nomeTurma = view.getCampoNome().getText();
            String situacao = "Aberta";
            
            int quantidadeMax = 0;
            if(!view.getCampoCapMax().getText().equals("")){
                quantidadeMax = Integer.parseInt(view.getCampoCapMax().getText());;
            }
            
            String horario = "Único";
            if(!view.getRadioHorUnico().isSelected()){
                horario = "Diversos";
            }
            
            String diasDaSemana = this.diasDaSemana();


            Turmas turma = new Turmas(nomeTurma, 0, quantidadeMax, diasDaSemana, horario, situacao);
            
            ArrayList <Horarios> horarios = horario(codBancoHorario, codBancoTurma);
            
            
            //Inserindo Dados
            if(nomeTurma.equals("")||diasDaSemana.equals("")||testeHorario){
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
    
    private String diasDaSemana(){
        String dias="";
        for(JCheckBox caixa : view.getDiasSemana()){
            if(caixa.isSelected()){
                dias+=caixa.getText();
            }
        }  
        return dias;
    }
    
    private ArrayList<Horarios> horario(int codHorario, int codTurma){
        String horaI, horaF;
        int indiceSemana=1;
        ArrayList<Horarios> horarios = new ArrayList<>();
        if(view.getRadioHorUnico().isSelected()){
            horaI = view.getCampoHorariosI().get(0).getText();
            horaF = view.getCampoHorariosF().get(0).getText();
              
            for(JCheckBox caixa : view.getDiasSemana()){
                if(caixa.isSelected()){
                    horarios.add(new Horarios(codHorario, caixa.getText(), indiceSemana, horaI, horaF, 0, codTurma));
                }
                indiceSemana++;
            }
            
            if(testeHora(horaI)){
                testeHorario=true;
            }
            if(!horaF.trim().equals(":")&&testeHora(horaF)){
                testeHorario = true;
            }
            
            
        }
        
        else{
            int cont=1;
            for(JCheckBox caixa : view.getDiasSemana()){
                if(caixa.isSelected()){
                    horaI = view.getCampoHorariosI().get(cont).getText();
                    horaF = view.getCampoHorariosF().get(cont).getText();
                    horarios.add(new Horarios(codHorario, caixa.getText(), indiceSemana, horaI, horaF, 0, codTurma));
                    
                    if(testeHora(horaI)){
                        testeHorario=true;
                    }
                    if(!horaF.trim().equals(":")&&testeHora(horaF)){
                        testeHorario = true;
                    }
                }
                cont++;
                indiceSemana++;
            }
        }
        return horarios;
    }
    
    private boolean testeHora(String hora){
        if(hora.trim().equals(":")){
            return true;
        }
        
        int horas = Integer.parseInt(hora.split(":")[0]);
        int minutos = Integer.parseInt(hora.split(":")[1]);
        
        return horas>23||minutos>59;
    }
    
    public void limparCampos(){
        //Limpando Campos
        view.getCampoNome().setText("");
        view.getCampoCapMax().setText("");
        
        for(JCheckBox caixa : view.getDiasSemana()){
            caixa.setSelected(false);
        }
        
        view.getRadioHorUnico().setSelected(true);
        
        for(JFormattedTextField campo : view.getCampoHorariosI()){
            campo.setValue(null);
        }
        
        for(JFormattedTextField campo : view.getCampoHorariosF()){
            campo.setValue(null);
        }
        
        view.setarConfigsInciciais();
    }
    
    private void desmarcarCaixas(){
        
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
