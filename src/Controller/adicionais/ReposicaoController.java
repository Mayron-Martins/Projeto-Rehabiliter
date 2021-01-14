/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.adicionais;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Controller.auxiliar.LogsSystem;
import Controller.auxiliar.VerificarCodigoNoBanco;
import Dao.AlunosDao;
import Dao.HorariosDao;
import Dao.ReposicaoDao;
import Dao.TurmasDao;
import Model.Aluno;
import Model.auxiliar.Horarios;
import Model.auxiliar.ReposicaoAulas;
import Model.auxiliar.Turmas;
import View.ReposicaoView;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mayro
 */
public class ReposicaoController {
    private final ReposicaoView view;
    private final DefaultTableModel tabelaAgendados;
    private final DefaultTableModel tabelaDeAgendamento;
    private final AlunosDao alunosDao = new AlunosDao();
    private final TurmasDao turmasDao = new TurmasDao();
    private final ReposicaoDao reposicaoDao = new ReposicaoDao();
    private final HorariosDao horariosDao = new HorariosDao();
    private final VerificarCodigoNoBanco verificar = new VerificarCodigoNoBanco();
    private final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();

    public ReposicaoController(ReposicaoView view) {
        this.view = view;
        this.tabelaAgendados = (DefaultTableModel) view.getTabelaAgendados().getModel();
        this.tabelaDeAgendamento = (DefaultTableModel) view.getTabelaAgendamento().getModel();
    }
    
    public void limparTabelaAgendados(){
        int quantLinhas = this.view.getTabelaAgendados().getRowCount();
        for(int quant=0; quant<quantLinhas; quant++){
            this.tabelaAgendados.removeRow(0);
        }
    }
    
    public void limparTabelaDeAgendamento(){
        int quantLinhas = this.view.getTabelaAgendamento().getRowCount();
        for(int quant=0; quant<quantLinhas; quant++){
            this.tabelaDeAgendamento.removeRow(0);
        }
    }
    
    //Inserir as turmas nos combos ao iniciar a tela
    public void listarTurmas(){
        try{
            ArrayList <Turmas> turmas = turmasDao.selecionarTodasTurmas();
            view.getComboTurma().removeAllItems();
            view.getComboTurmasExistentes().removeAllItems();
            view.getComboTurma().addItem("[Nenhuma]");
            if(turmas!=null){
                for (Turmas turma : turmas) {
                    view.getComboTurma().addItem(turma.getCodBanco()+"."+turma.getNomeTurma());
                    view.getComboTurmasExistentes().addItem(turma.getCodBanco()+"."+turma.getNomeTurma());
                }
            view.getComboTurma().setSelectedIndex(0);
            view.getComboTurmasExistentes().setSelectedIndex(0);
            listarHorarios();
            }
            else{
                view.exibeMensagem("Não há turmas disponíveis, adicione pelo menos uma para continuar!");
                view.dispose();
                view.setModal(false);
            }
        } catch (SQLException ex) {
            gerarLog(ex);
        } 
    }
    
    public void listarHorarios(){
        if(view.getComboTurma().getSelectedIndex()>0){
            String nomeTurma = view.getComboTurma().getSelectedItem().toString().split("\\.")[0];
            int codTurma = Integer.parseInt(nomeTurma);
            try{
                 ArrayList <Horarios> horarios = horariosDao.pesquisarHorarios("SELECT * FROM tblHorarios WHERE codHorario = "+codTurma);
                 view.getComboDiaDaSemana().removeAllItems();
                 view.getComboDiaDaSemana().addItem("[Dia]");
                 for(Horarios horario: horarios){
                     view.getComboDiaDaSemana().addItem(horario.getDiaDaSemana());
                 }
                 view.getComboDiaDaSemana().setSelectedIndex(0);
            }catch (SQLException ex) {
                gerarLog(ex);
            } 
        }
    }
    
    //Pesquisar Alunos
    public void pesquisarAluno(){
        String nomePesquisado = view.getCampoPesquisa().getText();
        
        view.ativarDesabilitarComponentes(false);
        if(!nomePesquisado.trim().equals("")){
            try{
                ArrayList <Aluno> alunos = alunosDao.pesquisarPorNome(nomePesquisado);
                
                if(alunos!=null){
                    limparTabelaDeAgendamento();
                    for (Aluno aluno : alunos) {
                        Turmas turma = turmasDao.pesquisarTurmas("SELECT * FROM tblTurmas WHERE codTurma = "+aluno.getTurma()).get(0);
                        Object[] dadosTabela = {aluno.getCodBanco(), turma.getCodBanco()+"."+turma.getNomeTurma(), aluno.getNome()};
                        tabelaDeAgendamento.addRow(dadosTabela);
                    }
                }else{
                    view.exibeMensagem("Aluno não encontrado!");
                    limparTabelaDeAgendamento();
                }
            } catch (Exception ex) {
                gerarLog(ex);
            }
            
        }
        else{
            view.exibeMensagem("Insira um nome válido!");
            limparTabelaDeAgendamento();
        }
    }
    
    //Habilita os campos de agendamento
    public void selecionarTabelaAgendamento(){
        int linhaSelecionada = view.getTabelaAgendamento().getSelectedRow();
        
        if(linhaSelecionada!=-1){
            view.ativarDesabilitarComponentes(true);
        }else{
            view.ativarDesabilitarComponentes(false);
        }
    }
    
    public void adicionarAgendamento(){
        int linhaSelecionada = view.getTabelaAgendamento().getSelectedRow();
        if(linhaSelecionada!=-1){
            try{
                int codAluno = Integer.parseInt(tabelaDeAgendamento.getValueAt(linhaSelecionada, 0).toString());
                String nomeTurma = view.getComboTurmasExistentes().getSelectedItem().toString().split("\\.")[0];
                int codTurma = Integer.parseInt(nomeTurma);

                int codAgendamento = verificar.verificarUltimo("tblReposicaoAulas", "codBanco")+1;
                
                Date dataInicio = view.getCampoDataInicio().getDate();
                Date dataFim = view.getCampoDataFim().getDate();
                
                ArrayList <Horarios> horarios = horariosDao.pesquisarHorarios("SELECT * FROM tblHorarios WHERE codHorario = "+codTurma);
                
                //Quando não se escolhe um período, utiliza-se o próximo dia útil da turma
                if(dataInicio==null){
                    LocalDate dataAtual = LocalDate.now();
                    boolean diaAgendado = false;
                    while(diaAgendado==false){
                        for (Horarios horario : horarios) {
                            if(retornarDiaDaSemana(dataAtual.getDayOfWeek()).equals(horario.getDiaDaSemana())){
                                diaAgendado = true;
                                break;
                            }
                        }
                        if(diaAgendado==true){
                            break;
                        }
                        dataAtual = dataAtual.plusDays(1);
                    }
                    Date dataAgendamento = converterData.conversaoLocalforDate(dataAtual);
                    
                    ReposicaoAulas reposicao = new ReposicaoAulas(codAgendamento, dataAgendamento, codTurma, codAluno, "Au");
                    reposicaoDao.inserirDados(reposicao);
                }else{
                    LocalDate dataInicioConv = converterData.conversaoLocalforDate(dataInicio);
                    LocalDate dataFimConv;
                    //Quando não se define um período de fim, defini-se automaticamente o período de 6 meses
                    if(dataFim==null){
                        dataFimConv = dataInicioConv.plusMonths(6);
                    }else{
                        //Quando define-se o período de fim
                        dataFimConv = converterData.conversaoLocalforDate(dataFim);
                    }
                    
                    ArrayList <LocalDate> datasIniciais = new ArrayList<>();
                    int quantDiasAgendados = 0;
                    while(quantDiasAgendados!=horarios.size()){
                        for (Horarios horario : horarios) {
                            if(retornarDiaDaSemana(dataInicioConv.getDayOfWeek()).equals(horario.getDiaDaSemana())){
                                datasIniciais.add(dataInicioConv);
                                quantDiasAgendados++;
                                break;
                            }
                        }
                        dataInicioConv = dataInicioConv.plusDays(1);
                    }
                   
                    LocalDate dataAtual = datasIniciais.get(0);
                    while(!dataAtual.isAfter(dataFimConv)){
                        int indice =0;
                        for (LocalDate datasIniciai : datasIniciais) {
                            if(!datasIniciai.isAfter(dataFimConv)){
                                Date dataAgendamento = converterData.conversaoLocalforDate(datasIniciai);
                                ReposicaoAulas reposicao = new ReposicaoAulas(codAgendamento, dataAgendamento, codTurma, codAluno, "Au");
                                codAgendamento++;
                                reposicaoDao.inserirDados(reposicao);
                                datasIniciais.set(indice, datasIniciai.plusWeeks(1));
                            }
                            dataAtual = datasIniciai;
                            indice++;
                        }
                    }
                    
                }
                
                //Limpando Campos
                view.exibeMensagem("Sucesso");
                view.getComboTurma().setSelectedIndex(view.getComboTurmasExistentes().getSelectedIndex()+1);
                view.getComboTurmasExistentes().setSelectedIndex(0);
                view.getCampoDataInicio().setDate(null);
                view.getCampoDataFim().setDate(null);
                
            } catch (SQLException ex) {
                gerarLog(ex);
                view.exibeMensagem("Não foi possível o agendamento!");
                view.getComboTurma().setSelectedIndex(view.getComboTurmasExistentes().getSelectedIndex()+1);
                view.getComboTurmasExistentes().setSelectedIndex(0);
                view.getCampoDataInicio().setDate(null);
                view.getCampoDataFim().setDate(null);
            }
            
            
        }else{
            view.exibeMensagem("Selecione um aluno!");
        }
    }
    
    private String retornarDiaDaSemana(DayOfWeek dia){
        switch(dia.name()){
            case "SUNDAY":
                return "Dom";
            case "MONDAY":
                return "Seg";
            case "TUESDAY":
                return "Ter";
            case "WEDNESDAY":
                return "Qua";
            case "THURSDAY":
                return "Qui";
            case "FRIDAY":
                return "Sex";
        }
        return "Sab";
    }
    
    public void setarTabelaAgendados(){
        if(view.getComboDiaDaSemana().getSelectedIndex()>0){
            limparTabelaAgendados();
            String nomeTurma = view.getComboTurma().getSelectedItem().toString().split("\\.")[0];
            int codTurma = Integer.parseInt(nomeTurma);

            String diaSemana = view.getComboDiaDaSemana().getSelectedItem().toString();

            LocalDate dataAtual = LocalDate.now();
            boolean diaAgendado = false;
            while(diaAgendado==false){
                if(retornarDiaDaSemana(dataAtual.getDayOfWeek()).equals(diaSemana)){
                    diaAgendado = true;
                    break;
                }
                dataAtual = dataAtual.plusDays(1);
            }
            try{
                Date data = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAtual));
                ArrayList <ReposicaoAulas> reposicoes = reposicaoDao.pesquisarReposicao("SELECT * FROM tblReposicaoAulas WHERE data = '"+data+"'");
                if(reposicoes!=null){
                    for(ReposicaoAulas reposicao : reposicoes){
                        String dataConvertida = converterData.parseDate(reposicao.getData());

                        Aluno aluno = alunosDao.pesquisarAlunos("SELECT * FROM tblAlunos WHERE codAluno = "+reposicao.getCodAluno()).get(0);
                        boolean situacao = false;
                        if(reposicao.getSituacao().equals("Pr")){
                            situacao = true;
                        }
                        Object[] dadosDaTabela = {reposicao.getCodBanco(), dataConvertida, aluno.getNome(), situacao};
                        tabelaAgendados.addRow(dadosDaTabela);
                    }
                }else{
                    view.exibeMensagem("Sem dados.");
                }
            } catch (SQLException | ParseException ex) {
                gerarLog(ex);
            }   
        }
        else{
            limparTabelaAgendados();
        }
    }
    
    //Gerar arquivo com o log de erro, caso haja
    private void gerarLog(Throwable erro){
        LogsSystem gerarLog = new LogsSystem();
        gerarLog.gravarErro(erro);
        gerarLog.close();
    }
    
}
