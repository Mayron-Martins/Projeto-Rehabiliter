/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.adicionais;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Dao.FrequenciaFuncionariosDao;
import Dao.FuncionarioDao;
import Model.Funcionario;
import Model.auxiliar.FrequenciaFuncionarios;
import View.FrequenciaFuncionariosView;
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
public class FrequenciaFuncionariosController {
    private final FrequenciaFuncionariosView view;
    private final DefaultTableModel tabelaDeFuncionariosBanco;
    private final FuncionarioDao funcionarioDao = new FuncionarioDao();
    private final FrequenciaFuncionariosDao frequencia = new FrequenciaFuncionariosDao();
    private final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();

    public FrequenciaFuncionariosController(FrequenciaFuncionariosView view) {
        this.view = view;
        this.tabelaDeFuncionariosBanco = (DefaultTableModel) view.getTabelaFuncionarios().getModel();
    }
    

    public void limparTabela(){
        int quantLinhas = this.view.getTabelaFuncionarios().getRowCount();
        for(int quant=0; quant<quantLinhas; quant++){
            this.tabelaDeFuncionariosBanco.removeRow(0);
        }
    }


    //Adicionar Frequencia na Tabela
    public void adicionarDadosNaTabela() throws SQLException, ParseException{
        limparTabela();
        if(view.getComboPeriodo().isEnabled()){
          if(view.getComboIntervalo().getSelectedIndex()>0){
              this.setarDadosDoBanco();
          }  
        }
    }
    
    public void notificacaoInicial() throws SQLException, ParseException{
        if(this.verificarPresencaDeDados()){
            view.exibeMensagem("Sem Dados Disponíveis");
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
    
    
    private boolean verificarPresencaDeDados() throws SQLException, ParseException{
        ArrayList <FrequenciaFuncionarios> frequencias = frequencia.pesquisarFrequencias("SELECT * FROM tblFreqFuncionarios");
        return frequencias == null;
    }


    public void ativarComboIntervalo(){
        if(view.getComboPeriodo().getSelectedIndex()>0){
            view.getComboIntervalo().setEnabled(true);
            view.getComboIntervalo().setSelectedIndex(0);
        }
        else{
            view.getComboIntervalo().setEnabled(false);
            view.getComboIntervalo().setSelectedIndex(0);
        }
    }
    
    private void pesquisarPeriodo(LocalDate dataPassada) throws SQLException, ParseException{
        LocalDate dataAtual = LocalDate.now();
        Date dataPassadaSql = converterData.getSqlDate(converterData.conversaoLocalforDate(dataPassada));
        Date dataSql = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAtual));
        
        ArrayList <FrequenciaFuncionarios> frequencias = frequencia.pesquisarFrequencias("SELECT * FROM tblFreqFuncionarios WHERE data BETWEEN '"+dataPassadaSql+"' AND '"+dataSql+"';");
        
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
           limparTabela();
           ArrayList <FrequenciaFuncionarios> frequencias = this.BuscarFrequenciasNaData();
           String situacao;
           for(int linhas=0; linhas<frequencias.size(); linhas++){
           if(frequencias.get(linhas).getSituacao().equals("A")){situacao="Ausente";}
           else{situacao = "Presente";}
           Funcionario funcionario = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE codFuncionario = "+frequencias.get(linhas).getCodFuncinario()).get(0);
           Object[] dadosDaTabela = {funcionario.getCodBanco(), funcionario.getNome(), situacao};
                  this.tabelaDeFuncionariosBanco.addRow(dadosDaTabela); 
           }
    }
    
    private ArrayList <FrequenciaFuncionarios> BuscarFrequenciasNaData() throws ParseException, SQLException{
    String dataCombo = view.getComboIntervalo().getSelectedItem().toString();
    Date data = converterData.getSqlDate(converterData.parseDate(dataCombo.split("Dia ")[1]));
    ArrayList <FrequenciaFuncionarios> frequencias = this.frequencia.pesquisarFrequencias("SELECT * FROM tblFreqFuncionarios WHERE data = '"+data+"'");
    return frequencias;
    }
}
