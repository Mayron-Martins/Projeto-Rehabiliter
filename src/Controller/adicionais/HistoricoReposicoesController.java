/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.adicionais;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Dao.AlunosDao;
import Dao.FuncionarioDao;
import Dao.LogAçoesFuncionarioDao;
import Dao.ReposicaoDao;
import Dao.TurmasDao;
import Model.Aluno;
import Model.Funcionario;
import Model.auxiliar.LogAçoesFuncionario;
import Model.auxiliar.ReposicaoAulas;
import Model.auxiliar.Turmas;
import View.HistoricoRehab;
import View.LoginFuncionario;
import View.LoginGerente;
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
    private final FuncionarioDao funcionarioDao = new FuncionarioDao();
    private final LogAçoesFuncionarioDao logDao = new LogAçoesFuncionarioDao();
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
        view.getComboIntervalo().removeAllItems();
        view.getComboIntervalo().addItem("[Nenhum]");
        LocalDate dataAtual = LocalDate.now();
        Date dataAnteriorSQL = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAnterior));
        Date dataAtualSQL = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAtual));

        ArrayList <ReposicaoAulas> reposicoes = reposicaoDao.pesquisarReposicao("SELECT * FROM tblReposicaoAulas WHERE data BETWEEN '"+dataAnteriorSQL+"' AND '"+dataAtualSQL+"'");
        if(reposicoes!=null)
        view.getComboIntervalo().setSelectedIndex(0);
        
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
            String data = converterData.parseDate(dataCampo);
            this.setarTabela(data);
            view.getCampoDataEspecífica().setDate(null);
            
        }
    }
    
    private void setarTabela(String data){
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
