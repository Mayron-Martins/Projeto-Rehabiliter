/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.adicionais;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Controller.auxiliar.ExportExcelFiles;
import Dao.FuncionarioDao;
import Dao.LogAçoesFuncionarioDao;
import Model.Funcionario;
import Model.auxiliar.LogAçoesFuncionario;
import View.Relatoriosfun;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import java.util.Date;

/**
 *
 * @author Mayro
 */
public class RelatoriosFuncionariosController {
    private final Relatoriosfun view;
    private final DefaultTableModel tabelaDeLogs;
    private final FuncionarioDao funcionarioDao = new FuncionarioDao();
    private final LogAçoesFuncionarioDao logDao = new LogAçoesFuncionarioDao();
    private final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();
    private final ExportExcelFiles exportarTabela;

    public RelatoriosFuncionariosController(Relatoriosfun view) {
        this.view = view;
        this.tabelaDeLogs = (DefaultTableModel) view.getTabelaLogs().getModel();
        this.exportarTabela = new ExportExcelFiles(this.tabelaDeLogs, "/documents/Rehabiliter/Exportações/Relatórios Funcionários");
    }
    
    public void limparTabela(){
        int quantLinhas = this.view.getTabelaLogs().getRowCount();
        for(int quant=0; quant<quantLinhas; quant++){
            this.tabelaDeLogs.removeRow(0);
        }
    }
    
    
    //Seta os novos dados conforme correspondente no Banco
    public void setarDadosTabela() throws ParseException, SQLException{
    limparTabela();
        if(view.getComboPeriodo().isEnabled()){
            if(view.getComboIntervalo().getSelectedIndex()>0){
                this.setarDados();
            }
        }
    }
    
    //Seta os funcionários no combobox de funcionários
    public void setarLogs() throws SQLException, ParseException{
        ArrayList <Funcionario> funcionarios = funcionarioDao.selecionarTodosFuncionarios();
        
        if(funcionarios == null){
            view.exibeMensagem("Sem Funcionários Cadastrados");
            view.dispose();
        }
        else{
            int codFuncionario;
            String nomeFuncionario;
            
            for(int linhas=0; linhas<funcionarios.size();linhas++){
                codFuncionario = funcionarios.get(linhas).getCodBanco();
                nomeFuncionario = funcionarios.get(linhas).getNome();
                view.getComboFuncionarios().addItem(codFuncionario+"."+nomeFuncionario);
            }
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
    
    
    public boolean verificarPresencaDeDados() throws SQLException, ParseException{
        ArrayList <LogAçoesFuncionario> logs = logDao.pesquisarLogs("SELECT * FROM tblLogdeAcoesdoFun"+this.retornarCodFuncionario());
        return logs == null;
    }
    private int retornarCodFuncionario(){
        String turma = view.getComboFuncionarios().getSelectedItem().toString();
        return Integer.parseInt(turma.split("\\.")[0]);
    }
    
    
    public void ativarComboPeriodo(){
        if(view.getComboFuncionarios().getSelectedIndex()>0){
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
        String dataPassadaSql = converterData.getSqlDate(converterData.conversaoLocalforDate(dataPassada)).toString().replace("-", "");
        String dataSql = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAtual)).toString().replace("-", "");
        ArrayList <LogAçoesFuncionario> logs = logDao.pesquisarLogs("SELECT * FROM tblLogdeAcoesdoFun"+this.retornarCodFuncionario()+" WHERE data BETWEEN '"+dataPassadaSql+"' AND '"+dataSql+"';");
        
        String dataAnterior="", dataBanco;
        if(logs!=null){
            view.getComboIntervalo().removeAllItems();
            view.getComboIntervalo().addItem("[Nenhum]");
            view.getComboIntervalo().setSelectedIndex(0);
            for(int linhas =0; linhas<logs.size(); linhas++){
                dataBanco = converterData.parseDate(converterData.parseDateAndTime(logs.get(linhas).getDataEvento()));
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
    
    private void setarDados() throws ParseException, SQLException{
           limparTabela();
           ArrayList <LogAçoesFuncionario> logs = this.BuscarLogsNaData();
           
           for(int linhas=0; linhas<logs.size(); linhas++){
               String dataEvento = converterData.parseDateAndTime(converterData.parseDateAndTime(logs.get(linhas).getDataEvento()));
           Object[] dadosDaTabela = {dataEvento, logs.get(linhas).getAcao(),logs.get(linhas).getDescricao()};
                  this.tabelaDeLogs.addRow(dadosDaTabela); 
               }
    }
    
    private ArrayList <LogAçoesFuncionario> BuscarLogsNaData() throws ParseException, SQLException{
        int codFuncionario = this.retornarCodFuncionario();
            String dataCombo = view.getComboIntervalo().getSelectedItem().toString();
            Date data;
            LocalDate dataAnterior = converterData.conversaoLocalforDate(converterData.parseDate(dataCombo.split("Dia ")[1]));
            String dataSeguinteSQL = dataAnterior.plusDays(1).toString().replace("-", "");
            String dataAnteriorSQL = dataAnterior.toString().replace("-", "");
            
            ArrayList <LogAçoesFuncionario> logs = logDao.pesquisarLogs("SELECT * FROM tblLogdeAcoesdoFun"+this.retornarCodFuncionario()+" WHERE data BETWEEN '"+dataAnteriorSQL+"' AND '"+dataSeguinteSQL+"';");
    return logs;
    }
    
    
    private LogAçoesFuncionario setarLog(ArrayList <Funcionario> funcionarios, String acao, String descricao){
        Funcionario funcionario = funcionarios.get(0);
        Date dataEvento = new Date();
        LogAçoesFuncionario logAcao = new LogAçoesFuncionario(funcionario.getCodBanco(), dataEvento, acao, descricao);
        return logAcao;
    }
    
    public void selecionarTabela(){
        int linhaSelecionada = view.getTabelaLogs().getSelectedRow();
        
        if(linhaSelecionada>-1){
            view.getCampoDescricao().setVisible(true);
            view.getCampoDescricao().removeAll(); 
            Object descricao = tabelaDeLogs.getValueAt(linhaSelecionada, 2);
            if(descricao!=null){
               view.getCampoDescricao().append(tabelaDeLogs.getValueAt(linhaSelecionada, 2).toString()); 
            }
            
        }
    }
    
    public void salvarDadosEmPlanilha(){
        int numLinhas = tabelaDeLogs.getRowCount();
        if(numLinhas>0){
            exportarTabela.exportarArquivoExcel();
        }
    }
}
