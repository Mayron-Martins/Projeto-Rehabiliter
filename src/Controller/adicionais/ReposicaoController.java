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
import Dao.FuncionarioDao;
import Dao.HorariosDao;
import Dao.LogAçoesFuncionarioDao;
import Dao.ReposicaoDao;
import Dao.TurmasDao;
import Model.Aluno;
import Model.Funcionario;
import Model.auxiliar.Horarios;
import Model.auxiliar.LogAçoesFuncionario;
import Model.auxiliar.ReposicaoAulas;
import Model.auxiliar.Turmas;
import View.LoginFuncionario;
import View.LoginGerente;
import View.ReposicaoView;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
    private final FuncionarioDao funcionarioDao = new FuncionarioDao();
    private final LogAçoesFuncionarioDao logDao = new LogAçoesFuncionarioDao();
    //private final VerificarCodigoNoBanco verificar = new VerificarCodigoNoBanco();
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
    }
    
    public void listarHorarios(){
        if(view.getComboTurma().getSelectedIndex()>0){
            String nomeTurma = view.getComboTurma().getSelectedItem().toString().split("\\.")[0];
            int codTurma = Integer.parseInt(nomeTurma);
            ArrayList <Horarios> horarios = horariosDao.pesquisarHorarios("SELECT * FROM tblHorarios WHERE codHorario = "+codTurma);
            view.getComboDiaDaSemana().removeAllItems();
            view.getComboDiaDaSemana().addItem("[Dia]");
            for(Horarios horario: horarios){
                view.getComboDiaDaSemana().addItem(horario.getDiaDaSemana());
            }
            view.getComboDiaDaSemana().setSelectedIndex(0); 
        }
    }
    
    //Pesquisar Alunos
    public void pesquisarAluno(){
        String nomePesquisado = view.getCampoPesquisa().getText();
        
        view.ativarDesabilitarComponentes(false);
        if(!nomePesquisado.trim().equals("")){
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
        Date dataInicio = view.getCampoDataInicio().getDate();
        Date dataFim = view.getCampoDataFim().getDate();
        if(dataInicio!=null&&dataFim!=null){
            LocalDate dataInicioConv = converterData.conversaoLocalforDate(dataInicio);
            LocalDate dataFimConv = converterData.conversaoLocalforDate(dataFim);
            if(dataFimConv.isAfter(dataInicioConv)){
                this.setarAgendamento();
            }else{
                view.exibeMensagem("Data final menor que inicial");
                view.getComboTurmasExistentes().setSelectedIndex(0);
                view.getCampoDataInicio().setDate(null);
                view.getCampoDataFim().setDate(null);
            }
            
        }else{
            this.setarAgendamento();
        }
    }
    
    private void setarAgendamento(){
        int linhaSelecionada = view.getTabelaAgendamento().getSelectedRow();
        if(linhaSelecionada!=-1){
            int codAluno = Integer.parseInt(tabelaDeAgendamento.getValueAt(linhaSelecionada, 0).toString());
            String nomeTurma = view.getComboTurmasExistentes().getSelectedItem().toString().split("\\.")[0];
            int codTurma = Integer.parseInt(nomeTurma);

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

                ReposicaoAulas reposicao = new ReposicaoAulas(dataAgendamento, codTurma, codAluno, "Au");
                reposicaoDao.inserirDados(reposicao);
                this.setarLog("Cadastro de Reposição", "Cadastrou o aluno "+codAluno+" em "+codTurma+"para o dia "+dataAgendamento);
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

                if(dataFimConv.isBefore(dataInicioConv)){

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
                            ReposicaoAulas reposicao = new ReposicaoAulas(dataAgendamento, codTurma, codAluno, "Au");
                            reposicaoDao.inserirDados(reposicao);
                            this.setarLog("Cadastro de Reposição", "Cadastrou o aluno "+codAluno+" em "+codTurma+"para o dia "+dataAgendamento);
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
            view.getTabelaAgendados().setSelectionMode(0);
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
        }
        else{
            limparTabelaAgendados();
        }
    }
    
    public void removerReposicao(){
        int[] linhas = view.getTabelaAgendados().getSelectedRows();
        if(linhas!=null){
            for(int linhaSelecionada:linhas){
                int codBanco = Integer.parseInt(tabelaAgendados.getValueAt(linhaSelecionada, 0).toString());
                reposicaoDao.removerReposicao(codBanco);
                this.setarLog("Remoção de Reposição de Aula", "Removeu a reposição "+codBanco);
            }
            view.exibeMensagem("Removido com sucesso!");
            
            int modoSelecao = view.getTabelaAgendados().getSelectionModel().getSelectionMode();
            if(modoSelecao==0){
                setarTabelaAgendados();
            }
            else{
                exibirFuturasReposicoes();
            }
        }
    }
    
    public void editarReposicao(){
        int linhaSelecionada = view.getTabelaAgendados().getSelectedRow();
        if(linhaSelecionada!=-1){
            int codBanco = Integer.parseInt(tabelaAgendados.getValueAt(linhaSelecionada, 0).toString());
            String situacao = "Au";
            if(tabelaAgendados.getValueAt(linhaSelecionada, 3).equals(true)){
                situacao = "Pr";
            }
            ReposicaoAulas reposicao = new ReposicaoAulas(codBanco, situacao);
            reposicaoDao.atualizarDados(reposicao);
            this.setarLog("Edição de Reposição de Aula", "Modificou a presença para "+situacao+" em Reposição "+codBanco);
            view.exibeMensagem("Sucesso!");
            setarTabelaAgendados();
            
        }
    }
    
    public void editarVariasReposicoes(){
        int totalLinhas = tabelaAgendados.getRowCount();
        if(totalLinhas>0){
            for(int linhas =0; linhas<totalLinhas; linhas++){
                int codBanco = Integer.parseInt(tabelaAgendados.getValueAt(linhas, 0).toString());
                String situacao = "Au";
                if(tabelaAgendados.getValueAt(linhas, 3).equals(true)){
                    situacao = "Pr";
                }
                
                ReposicaoAulas reposicao = new ReposicaoAulas(codBanco, situacao);
                reposicaoDao.atualizarDados(reposicao);
                this.setarLog("Edição de Reposição de Aula", "Modificou a presença para "+situacao);
            }
            view.exibeMensagem("Sucesso!");
            setarTabelaAgendados();
            
        }
    }
    
    public void exibirFuturasReposicoes(){
        limparTabelaAgendados();
        view.getTabelaAgendados().setSelectionMode(1);
        if(view.getComboTurma().getSelectedIndex()>0){
            LocalDate dataAtualConv = LocalDate.now();
            String nomeTurma = view.getComboTurma().getSelectedItem().toString().split("\\.")[0];
            int codTurma = Integer.parseInt(nomeTurma);
            Date dataAtual = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAtualConv));
            ArrayList <ReposicaoAulas> reposicoes = reposicaoDao.pesquisarReposicao("SELECT * FROM tblReposicaoAulas WHERE codTurma = "+codTurma+" AND data >= '"+dataAtual+"'");
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
                view.exibeMensagem("Sem Dados.");
            }
        }
        
    }
    
    public void ajuda(){
        String atalhos = "\u07CBESC = Sair da Tela\n"
                + "\u07CBF5 = Atualizar Tabela\n"
                + "\u07CBDEL = Remover Reposição\n"
                + "\u07CBCTRL + E = Editar Reposição\n"
                + "\u07CBCTRL + Q = Editar Várias Reposições\n";
        
        view.getPainelAjuda().setModal(true);
        view.getPainelAjuda().getCampoAtalhos().setText("");
        view.getPainelAjuda().getCampoAtalhos().append(atalhos);
        view.getPainelAjuda().setVisible(true);
    }
    
    //Gerar arquivo com o log de erro, caso haja
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
