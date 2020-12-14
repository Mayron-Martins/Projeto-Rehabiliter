/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.adicionais;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Controller.auxiliar.ExportarArquivos;
import Controller.auxiliar.ImpressaoComponentes;
import Dao.AlunosDao;
import Dao.FrequenciaTurmasDao;
import Dao.FuncionarioDao;
import Dao.HorariosDao;
import Dao.LogAçoesFuncionarioDao;
import Dao.TurmasDao;
import Model.Aluno;
import Model.Funcionario;
import Model.auxiliar.FrequenciaTurmas;
import Model.auxiliar.Horarios;
import Model.auxiliar.LogAçoesFuncionario;
import Model.auxiliar.Turmas;
import View.turmasFrequencia;
import java.awt.Color;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mayro
 */
public class FrequenciaTurmasController {
    private final turmasFrequencia view;
    private final DefaultTableModel tabelaDeAlunos;
    private final DefaultTableModel tabelaDeAlunosBanco;
    private final TurmasDao turmasDao = new TurmasDao();
    private final AlunosDao alunosDao = new AlunosDao();
    private final HorariosDao horariosDao = new HorariosDao();  
    private final FuncionarioDao funcionarioDao = new FuncionarioDao();
    private final LogAçoesFuncionarioDao logDao = new LogAçoesFuncionarioDao();
    private final FrequenciaTurmasDao frequencia = new FrequenciaTurmasDao();
    private final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();
    private final ExportarArquivos exportarTabela = new ExportarArquivos();
    private final ImpressaoComponentes imprimirTabela = new ImpressaoComponentes();

    public FrequenciaTurmasController(turmasFrequencia view) {
        this.view = view;
        this.tabelaDeAlunos = (DefaultTableModel) view.getTabelaAlunos().getModel();
        this.tabelaDeAlunosBanco = (DefaultTableModel) view.getTabelaAlunosBanco().getModel();
    }
    
    public void limparTabela(){
        int quantLinhas = this.view.getTabelaAlunos().getRowCount();
        for(int quant=0; quant<quantLinhas; quant++){
            this.tabelaDeAlunos.removeRow(0);
        }
    }
    public void limparTabelaBanco(){
        int quantLinhas = this.view.getTabelaAlunosBanco().getRowCount();
        for(int quant=0; quant<quantLinhas; quant++){
            this.tabelaDeAlunosBanco.removeRow(0);
        }
    }
    
    //Adicionar linhas à tabela se for a data correta
    public void adicionarLinhasATabela() throws SQLException, ParseException{
        view.getPainelderolagem().setVisible(true);
        view.getPainelderolagem1().setVisible(false);
        limparTabela();
        if(view.getComboTurmas().getSelectedIndex()>0){
            if(this.verificarSeHaDadosNoSistema()){
                    if(this.verificarNovoDado()){
                        this.setarTabela();
                    }
                    else{
                        String turma = view.getComboTurmas().getSelectedItem().toString();
                        view.getCampoAviso().setForeground(Color.RED);
                        view.getCampoAviso().removeAll();
                        view.getCampoAviso().append("A turma "+turma.split("\\.")[1]+" não possui horários para o dia de hoje.");
                        view.getScrollPaneAviso().setVisible(true);
                    }
            }
            else{
                view.exibeMensagem("Dados Já preenchidos Hoje!");
            }
        }
        else{
            view.exibeMensagem("Selecione Uma Turma Nas Opções Para Continuar!");
        }
    }
    
    //Seta os novos dados conforme correspondente no Banco
    public void setarNovosDadosTabela() throws ParseException, SQLException{
    view.getPainelderolagem().setVisible(false);
    view.getPainelderolagem1().setVisible(true);
    limparTabelaBanco();
        if(view.getComboPeriodo().isEnabled()){
            if(view.getComboIntervalo().getSelectedIndex()>0){
                this.setarDadosDoBanco();
            }
        }
    }
    
    //Adiciona a frequencia salva ao banco
    public void adicionarFrequenciaoBanco() throws ParseException, SQLException{
        for(int linhas=0; linhas<this.tabelaDeAlunos.getRowCount(); linhas++){
            int codTurma = this.retornarCodTurma();
            int codAluno = Integer.parseInt(tabelaDeAlunos.getValueAt(linhas, 0).toString());
            String nomeTurma = view.getComboTurmas().getSelectedItem().toString();
            LocalDate dataAtual = LocalDate.now();
            String dataInput = converterData.parseDate(converterData.conversaoLocalforDate(dataAtual));
            String situacao = tabelaDeAlunos.getValueAt(linhas, 3).toString().substring(0, 2);

            FrequenciaTurmas frequenciaAtual = new FrequenciaTurmas(codTurma, codAluno, dataInput, situacao);

            frequencia.inserirDados(frequenciaAtual);
            
            ArrayList <Funcionario> funcionarios = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE status = 'Ativo'");
            if(funcionarios!=null){
                String acao = "Cadastro de Frequência";
                String descricao = "Cadastrou a frequência para a turma "+nomeTurma;
                this.setarLog(funcionarios, acao, descricao);
            }
        }
        view.exibeMensagem("Dados Acicionados Com Sucesso!");
        limparTabela();
    }
    
    //Atualiza a frequência no banco de dados
    public void atualizarFrequenciaoBanco() throws ParseException, SQLException{
        for(int linhas=0; linhas<this.tabelaDeAlunosBanco.getRowCount(); linhas++){
            int codTurma = this.retornarCodTurma();
            int codAluno = Integer.parseInt(tabelaDeAlunosBanco.getValueAt(linhas, 0).toString());
            String nomeTurma = view.getComboTurmas().getSelectedItem().toString();
            String dataInput = converterData.parseDate(BuscarFrequenciasNaData().get(0).getDataInsert());
            String situacao = tabelaDeAlunosBanco.getValueAt(linhas, 3).toString().substring(0, 2);

            FrequenciaTurmas frequenciaAtual = new FrequenciaTurmas(codTurma, codAluno, dataInput, situacao);

            frequencia.atualizarDados(frequenciaAtual);
            
            ArrayList <Funcionario> funcionarios = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE status = 'Ativo'");
            if(funcionarios!=null){
                String acao = "Edição de Frequência";
                String descricao = "Editou os dados da frequência para a turma "+nomeTurma;
                this.setarLog(funcionarios, acao, descricao);
            }
        }
        view.exibeMensagem("Dados Atualizados Com Sucesso!");
        limparTabelaBanco();
    }
    
    //Seta as turmas no combobox de turmas
    public void setarTurmas() throws SQLException{
        ArrayList <Turmas> turmas = turmasDao.selecionarTodasTurmas();
        
        if(turmas == null){
            view.exibeMensagem("Sem Turmas Cadastradas");
            view.dispose();
        }
        else{
            int codBanco;
            String nomeTurma;
            
            for(int linhas=0; linhas<turmas.size();linhas++){
                codBanco = turmas.get(linhas).getCodBanco();
                nomeTurma = turmas.get(linhas).getNomeTurma();
                view.getComboTurmas().addItem(codBanco+"."+nomeTurma);
            }
        }
    }
    
    //seta a tabela para data de hoje
    private void setarTabela() throws SQLException, ParseException{
            limparTabela();
            int codTurma = this.retornarCodTurma();
           ArrayList <Aluno> alunos = alunosDao.pesquisarAlunos("SELECT * FROM tblAlunos WHERE codTurma = "+codTurma);
           if(alunos == null){view.exibeMensagem("Sem Alunos Cadastrados Nessa Turma!");}
           else{
               for(int linhas=0; linhas<alunos.size(); linhas++){
                  Object[] dadosDaTabela = {alunos.get(linhas).getCodBanco(), alunos.get(linhas).getNome(), "", view.getComboPresenca().getSelectedItem()};
                  this.tabelaDeAlunos.addRow(dadosDaTabela); 
               }
           }
    }
    
    
    //Setar o combo presença na tabela
    public void setarComboPresenca(){

      if(view.getTabelaAlunos().getSelectedRow()>-1){
      int linhaSelecionada = view.getTabelaAlunos().getSelectedRow();
      String situacao = view.getComboPresenca().getSelectedItem().toString();

      if(situacao.equals("[Pendente]")){this.tabelaDeAlunos.setValueAt("[Pendente]", linhaSelecionada, 3);}
      if(situacao.equals("Presente")){this.tabelaDeAlunos.setValueAt("Presente", linhaSelecionada, 3);}
      if(situacao.equals("Ausente")){this.tabelaDeAlunos.setValueAt("Ausente", linhaSelecionada, 3);}
    }
        
    }
    
    public void setarComboPresencaBanco(){

      if(view.getTabelaAlunosBanco().getSelectedRow()>-1){
      int linhaSelecionada = view.getTabelaAlunosBanco().getSelectedRow();
      String situacao = view.getComboPresencaBanco().getSelectedItem().toString();

      if(situacao.equals("[Pendente]")){this.tabelaDeAlunosBanco.setValueAt("[Pendente]", linhaSelecionada, 3);}
      if(situacao.equals("Presente")){this.tabelaDeAlunosBanco.setValueAt("Presente", linhaSelecionada, 3);}
      if(situacao.equals("Ausente")){this.tabelaDeAlunosBanco.setValueAt("Ausente", linhaSelecionada, 3);}
    }
        
    }
    
    //Setar campo Intervalo
    public void setarCampoIntervalo() throws SQLException, ParseException{
        if(view.getComboPeriodo().getSelectedIndex()>0){
            int numPeriodo = view.getComboPeriodo().getSelectedIndex();
            LocalDate data = LocalDate.now();
            switch(numPeriodo){
                case 1:
                this.pesquisarPeriodo(data.minusWeeks(1));
                break;
                
                case 2:
                this.pesquisarPeriodo(data.minusMonths(1));
                break;
                
                case 3:
                this.pesquisarPeriodo(data.minusMonths(6));
                break;
                
                case 4:
                this.pesquisarPeriodo(data.minusYears(1));
                break;
            }
            
        }
    }
    
    //Verificar se o dia da semana é o mesmo da turma
    private boolean verificarNovoDado() throws SQLException{
        ArrayList <Horarios> horarios = horariosDao.pesquisarHorarios("SELECT * FROM tblHorarios WHERE codTurma = "+this.retornarCodTurma());
        ArrayList <DayOfWeek> diasDaSemana = new ArrayList<>();
        
        for(int linhas=0; linhas<horarios.size(); linhas++){
            switch(horarios.get(linhas).getDiaDaSemana()){
                case "Seg":
                    diasDaSemana.add(DayOfWeek.MONDAY);
                break;
                
                case "Ter":
                    diasDaSemana.add(DayOfWeek.TUESDAY);
                break;
                
                case "Qua":
                    diasDaSemana.add(DayOfWeek.WEDNESDAY);
                break;
                
                case "Qui":
                    diasDaSemana.add(DayOfWeek.THURSDAY);
                break;
                
                case "Sex":
                    diasDaSemana.add(DayOfWeek.FRIDAY);
                break;
                
                case "Sáb":
                    diasDaSemana.add(DayOfWeek.SATURDAY);
                break;
                
                case "Dom":
                    diasDaSemana.add(DayOfWeek.SUNDAY);
                break;       
            }
        }
        //Verifica se um dos dias da semana pegos no banco é igual a hoje
        DayOfWeek dataAtual = LocalDate.now().getDayOfWeek();
        int verificador = 0;
        for(int linhas = 0; linhas < diasDaSemana.size(); linhas++){
            if(diasDaSemana.get(linhas).equals(dataAtual)){
                verificador++;
            }
        }
        
        return verificador>0;
    }
    
    public boolean verificarPresencaDeDados() throws SQLException, ParseException{
        ArrayList <FrequenciaTurmas> frequencias = frequencia.pesquisarFrequencias("SELECT * FROM tblFreqTurma"+this.retornarCodTurma());
        return frequencias == null;
    }
    private int retornarCodTurma(){
        String turma = view.getComboTurmas().getSelectedItem().toString();
        return Integer.parseInt(turma.split("\\.")[0]);
    }
    
    private boolean verificarSeHaDadosNoSistema() throws SQLException, ParseException{
        LocalDate dataAtual = LocalDate.now();
        Date dataInpult = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAtual));
        ArrayList <FrequenciaTurmas> frequencias = frequencia.pesquisarFrequencias("SELECT * FROM tblFreqTurma"+this.retornarCodTurma() +" WHERE data = '"+dataInpult+"' AND codTurma = "+this.retornarCodTurma());
        return frequencias==null;
    }
    
    public void ativarComboPeriodo(){
        if(view.getComboTurmas().getSelectedIndex()>0){
            view.getComboPeriodo().setEnabled(true);
            view.getComboPeriodo().setSelectedIndex(0);
            view.getComboIntervalo().setSelectedIndex(0);
        }
        else{
            view.getComboPeriodo().setEnabled(false);
            view.getComboIntervalo().setSelectedIndex(0);
            view.getComboIntervalo().setEnabled(false);
        }
    }
    public void ativarComboIntervalo(){
        if(view.getComboPeriodo().getSelectedIndex()>0){
            view.getComboIntervalo().setEnabled(true);
        }
        else{
            view.getComboIntervalo().setEnabled(false);
        }
    }
    
    private void pesquisarPeriodo(LocalDate dataPassada) throws SQLException, ParseException{
        LocalDate dataAtual = LocalDate.now();
        Date dataPassadaSql = converterData.getSqlDate(converterData.conversaoLocalforDate(dataPassada));
        Date dataSql = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAtual));
        
        ArrayList <FrequenciaTurmas> frequencias = frequencia.pesquisarFrequencias("SELECT * FROM tblFreqTurma"+this.retornarCodTurma()+" WHERE data BETWEEN '"+dataPassadaSql+"' AND '"+dataSql+"';");
        
        String dataAnterior="", dataBanco;
        if(frequencias!=null){
            view.getComboIntervalo().removeAllItems();
            view.getComboIntervalo().addItem("[Nenhum]");
            view.getComboIntervalo().setSelectedIndex(0);
            for(int linhas =0; linhas<frequencias.size(); linhas++){
                dataBanco = converterData.parseDate(frequencias.get(linhas).getDataInsert());
                if(!dataBanco.equals(dataAnterior)){
                    view.getComboIntervalo().addItem("Dia "+dataBanco);
                }
                dataAnterior = dataBanco;
            }
        }
        else{
             view.getComboIntervalo().removeAllItems();
            view.getComboIntervalo().addItem("[Nenhum]");
            view.getComboIntervalo().setSelectedIndex(0);
        }
        
    }
    
    private void setarDadosDoBanco() throws ParseException, SQLException{
            limparTabelaBanco();
           ArrayList <FrequenciaTurmas> frequencias = this.BuscarFrequenciasNaData();
           
           for(int linhas=0; linhas<frequencias.size(); linhas++){
           Aluno aluno = alunosDao.pesquisarAlunos("SELECT * FROM tblAlunos WHERE codAluno = "+frequencias.get(linhas).getCodAluno()).get(0);
           this.selecionarComboPresencaBanco(frequencias.get(linhas).getSituacao());
           Object[] dadosDaTabela = {aluno.getCodBanco(), aluno.getNome(), "", view.getComboPresencaBanco().getSelectedItem()};
                  this.tabelaDeAlunosBanco.addRow(dadosDaTabela); 
               }
    }
    
    private ArrayList <FrequenciaTurmas> BuscarFrequenciasNaData() throws ParseException, SQLException{
        int codTurma = this.retornarCodTurma();
            String dataCombo = view.getComboIntervalo().getSelectedItem().toString();
            Date data = converterData.getSqlDate(converterData.parseDate(dataCombo.split("Dia ")[1]));
           ArrayList <FrequenciaTurmas> frequencias = this.frequencia.pesquisarFrequencias("SELECT * FROM tblFreqTurma"+this.retornarCodTurma()+" WHERE data = '"+data+"'");
    return frequencias;
    }
    
    private void selecionarComboPresencaBanco(String situacao){
        switch(situacao){
            case "[P":
                view.getComboPresencaBanco().setSelectedIndex(0);
            break;
            
            case "Pr":
                view.getComboPresencaBanco().setSelectedIndex(1);
            break;
            
            case "Au":
                view.getComboPresencaBanco().setSelectedIndex(2);
            break;
        }
    }
    
    private LogAçoesFuncionario setarLog(ArrayList <Funcionario> funcionarios, String acao, String descricao){
        Funcionario funcionario = funcionarios.get(0);
        Date dataEvento = new Date();
        LogAçoesFuncionario logAcao = new LogAçoesFuncionario(funcionario.getCodBanco(), dataEvento, acao, descricao);
        return logAcao;
    }
    
    public void salvarDadosEmPlanilha(){
        int numLinhas = tabelaDeAlunosBanco.getRowCount();
        if(numLinhas>0){
            exportarTabela.exportarExcel(view.getTabelaAlunosBanco().getModel(), "/documents/Rehabiliter/Exportações/Frequencia Turmas");
        }
        else{
            view.exibeMensagem("Inicie uma tabela primeiro!");
        }
    }
    
    public void imprimirDados(){
        int numLinhas = tabelaDeAlunosBanco.getRowCount();
        if(numLinhas>0){
            String nomeTurma = view.getComboTurmas().getSelectedItem().toString().split("\\.")[1];
            String titulo = "Frequência da Turma "+nomeTurma;
            String rodape = view.getComboIntervalo().getSelectedItem().toString().split("Dia ")[1];
            imprimirTabela.imprimirTabelas(titulo, rodape, view.getTabelaAlunosBanco());
        }
        else{
            view.exibeMensagem("Inicie uma tabela primeiro!");
        }
    }
}
