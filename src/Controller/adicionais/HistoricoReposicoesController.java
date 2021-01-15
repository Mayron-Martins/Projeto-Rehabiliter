/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.adicionais;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Controller.auxiliar.LogsSystem;
import Dao.AlunosDao;
import Dao.ReposicaoDao;
import Dao.TurmasDao;
import Model.Aluno;
import Model.auxiliar.ReposicaoAulas;
import Model.auxiliar.Turmas;
import View.HistoricoRehab;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mayro
 */
public class HistoricoReposicoesController {
    private final HistoricoRehab view;
    private final DefaultTableModel tabelaReposicoes;
    private final ReposicaoDao reposicaoDao = new ReposicaoDao();
    private final AlunosDao alunosDao = new AlunosDao();
    private final TurmasDao turmasDao = new TurmasDao();
    private final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();

    public HistoricoReposicoesController(HistoricoRehab view) {
        this.view = view;
        this.tabelaReposicoes = (DefaultTableModel) view.getTabelaReposicoes().getModel();
    }
    
    public void limparTabela(){
        int quantLinhas = this.view.getTabelaReposicoes().getRowCount();
        for(int quant=0; quant<quantLinhas; quant++){
            this.tabelaReposicoes.removeRow(0);
        }
    }
    
    public void setarTurmas(){
        try{
            ArrayList <Turmas> turmas = turmasDao.selecionarTodasTurmas();
            view.getComboTurmas().removeAllItems();
            view.getComboTurmas().addItem("[Nenhuma]");
            if(turmas!=null){
                for(Turmas turma : turmas){
                    view.getComboTurmas().addItem(turma.getCodBanco()+"."+turma.getNomeTurma());
                }
            }else{
                view.exibeMensagem("Sem Turmas Cadastradas!");
            }
        } catch (SQLException ex) {
            gerarLog(ex);
        }
        
    }
    
    public void ativarComboPeriodo(){
        int opcaoSelecionada = view.getComboTurmas().getSelectedIndex();
        view.getComboPeriodo().setSelectedIndex(0);
        if(opcaoSelecionada>0){  
            view.getComboPeriodo().setEnabled(true);
            view.getCampoDataEspecífica().setEnabled(true);
            view.getBotaoAplicar().setEnabled(true);
        }
        else{
            view.getComboPeriodo().setEnabled(false);
            view.getCampoDataEspecífica().setEnabled(false);
            view.getBotaoAplicar().setEnabled(false);
        }
    }
    
    public void ativarComboIntervalo(){
        int opcaoSelecionada = view.getComboPeriodo().getSelectedIndex();
        view.getComboIntervalo().setSelectedIndex(0);
        if(opcaoSelecionada>0){
            view.getComboIntervalo().setEnabled(true);
            this.setarComboIntervalo();
        }
        else{
            view.getComboIntervalo().setEnabled(false);
        }
    }
    
    private void setarComboIntervalo(){
        int opcaoSelecionada = view.getComboPeriodo().getSelectedIndex();
        if(opcaoSelecionada>0){
            LocalDate dataRepassada = LocalDate.now();
            switch(opcaoSelecionada){
                case 1:
                    this.pesquisarIntervalo(dataRepassada.minusWeeks(1));
                break;
                case 2:
                    this.pesquisarIntervalo(dataRepassada.minusMonths(1));
                break;
                case 3:
                    this.pesquisarIntervalo(dataRepassada.minusMonths(6));
                break;
                case 4:
                    this.pesquisarIntervalo(dataRepassada.minusYears(1));
                break;
            }
        }
        
    }
    
    private void pesquisarIntervalo(LocalDate dataAnterior){
        try{
            view.getComboIntervalo().removeAllItems();
            view.getComboIntervalo().addItem("[Nenhum]");
            LocalDate dataAtual = LocalDate.now();
            Date dataAnteriorSQL = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAnterior));
            Date dataAtualSQL = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAtual));

            ArrayList <ReposicaoAulas> reposicoes = reposicaoDao.pesquisarReposicao("SELECT * FROM tblReposicaoAulas WHERE data BETWEEN '"+dataAnteriorSQL+"' AND '"+dataAtualSQL+"'");
            if(reposicoes!=null){
                String dataAntiga = "", dataBanco;
                for(ReposicaoAulas reposicao : reposicoes){
                    dataBanco = converterData.parseDate(reposicao.getData());
                    if(!dataBanco.equals(dataAntiga)){
                        view.getComboIntervalo().addItem(dataBanco);
                    }
                    dataAntiga = dataBanco;
                }
            }
            view.getComboIntervalo().setSelectedIndex(0);
        } catch (SQLException | ParseException ex) {
            gerarLog(ex);
        }
        
    }
    
    public void setarTabelaCombo(){
        int opcaoSelecionada = view.getComboIntervalo().getSelectedIndex();
        if(opcaoSelecionada>0){
            String data = view.getComboIntervalo().getSelectedItem().toString();
            this.setarTabela(data);
        }
    }
    public void setarTabelaCampoData(){
        Date dataCampo = view.getCampoDataEspecífica().getDate();
        if(dataCampo!=null){
            try{
                String data = converterData.parseDate(dataCampo);
                this.setarTabela(data);
                view.getCampoDataEspecífica().setDate(null);
            }catch (ParseException ex) {
            gerarLog(ex);
        }
            
        }
    }
    
    private void setarTabela(String data){
        try{
            Date dataSQL = converterData.getSqlDate(converterData.parseDate(data));
            ArrayList <ReposicaoAulas> reposicoes = reposicaoDao.pesquisarReposicao("SELECT * FROM tblReposicaoAulas WHERE data = '"+dataSQL+"'");
            if(reposicoes!=null){
                for(ReposicaoAulas reposicao : reposicoes){
                    Aluno aluno = alunosDao.pesquisarAlunos("SELECT * FROM tblAlunos WHERE codAluno = "+reposicao.getCodAluno()).get(0);
                    String situacao = "Ausente";
                    if(reposicao.getSituacao().equals("Pr")){
                        situacao = "Presente";
                    }
                    Object[] dadosDaTabela = {reposicao.getCodBanco(), aluno.getNome(), situacao};
                    tabelaReposicoes.addRow(dadosDaTabela);
                }

            }else{
                view.exibeMensagem("Sem Dados.");
            }
        } catch (ParseException | SQLException ex) {
            gerarLog(ex);
        }
    }
    
    //Gerar arquivo com o log de erro, caso haja
    private void gerarLog(Throwable erro){
        LogsSystem gerarLog = new LogsSystem();
        gerarLog.gravarErro(erro);
        gerarLog.close();
    }
}
