/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.auxiliar.ConversaoDeDinheiro;
import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Controller.auxiliar.ExportarArquivos;
import Dao.AlunosDao;
import Dao.DetOrcamentarioDao;
import Dao.PlanosDao;
import Model.Aluno;
import Model.auxiliar.DetOrcamentario;
import Model.auxiliar.Planos;
import View.FinanceiroAnaliseFinanceira;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
public class OrcamentarioController {
    private final FinanceiroAnaliseFinanceira view;
    private final DefaultTableModel tabelaOrcamentaria;
    private final DetOrcamentarioDao orcamentarioDao = new DetOrcamentarioDao();
    private final PlanosDao planosDao = new PlanosDao();
    private final AlunosDao alunosDao = new AlunosDao();
    private final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();
    private final ConversaoDeDinheiro converterDinheiro = new ConversaoDeDinheiro();
    private final ExportarArquivos exportarTabela;

    public OrcamentarioController(FinanceiroAnaliseFinanceira view) {
        this.view = view;
        this.tabelaOrcamentaria = (DefaultTableModel) view.getTabelaOrcamentaria().getModel();
        this.exportarTabela = new ExportarArquivos(view.getTabelaOrcamentaria().getModel(), "/documents/Rehabiliter/Exportações/Relatórios Orçamentais");
    }
    
    //Limpar tabela
    public void limparTabelaOrcamentaria(){
        int quantLinhas = this.view.getTabelaOrcamentaria().getRowCount();
        for(int quant=0; quant<quantLinhas; quant++){
            this.tabelaOrcamentaria.removeRow(0);
        }
    }
    
    public void setarTabelas() throws SQLException, ParseException{
        ArrayList <DetOrcamentario> orcamentarios = this.pegarOrcamentarioNoPeriodo();
        
        if(orcamentarios!=null){
            if(view.getBotaoVResumida().isEnabled()){
                this.setarTabelaResumida(orcamentarios);
                this.setarCampoPendentes();
                this.setarCamposTotais();
             }
            else{
                this.setarTabelaDetalhada(orcamentarios);
                this.setarCampoPendentes();
                this.setarCamposTotais();
            }
        }
        else{
            view.exibeMensagem("Sem Dados Orçamentários");
            limparCampos();
            limparTabelaOrcamentaria();
        }
        
    }
    
    private void setarTabelaDetalhada(ArrayList<DetOrcamentario> orcamentarios) throws SQLException, ParseException{
        limparTabelaOrcamentaria();
        limparCampos();
        
        BigDecimal ganhoTotal = new BigDecimal("0");
        BigDecimal gastoTotal = new BigDecimal("0");
        
        for(int linhas=0; linhas<orcamentarios.size(); linhas++){
            int codBanco = orcamentarios.get(linhas).getCodBanco();
            String tipo = orcamentarios.get(linhas).getTipo();
            String formaPagamento = orcamentarios.get(linhas).getFormaPagamento();
            BigDecimal valor = new BigDecimal(orcamentarios.get(linhas).getValor().toString());
            String dataCadastro = converterData.parseDate(orcamentarios.get(linhas).getDataCadastro());
            long chave = orcamentarios.get(linhas).getChave();
            
            if(tipo.equals("Gastos")){
                gastoTotal = gastoTotal.add(valor);
            }
            else{
                ganhoTotal = ganhoTotal.add(valor);
            }
            
            Object[] dadosDaTabela = {codBanco, tipo, formaPagamento, valor, dataCadastro, chave};
            tabelaOrcamentaria.addRow(dadosDaTabela);
        }
        view.getCampoGanhoTotal().setText(ganhoTotal.toString());
        view.getCampoDespesaTotal().setText(gastoTotal.toString());
        
    }
    
    private void setarTabelaResumida(ArrayList<DetOrcamentario> orcamentarios) throws SQLException, ParseException{
        limparTabelaOrcamentaria();
        limparCampos();
        
        BigDecimal ganhoTotal = new BigDecimal("0");
        BigDecimal gastoTotal = new BigDecimal("0");
        BigDecimal ganhoTotalDia = new BigDecimal("0");
        BigDecimal gastoTotalDia = new BigDecimal("0");
        
        String codBanco = "-";
        String tipo = "Balanço";
        String formaPagamento = "Diversas";
        String chave = "-";
        
        for(int linhas=0; linhas<orcamentarios.size(); linhas++){
            BigDecimal valor = new BigDecimal(orcamentarios.get(linhas).getValor().toString());
            Date dataCadastro = orcamentarios.get(linhas).getDataCadastro();
            String tipoDado = orcamentarios.get(linhas).getTipo();
            
            
            //Datas anteriores e próximas
            Date dataEntradaBanco = converterData.parseDate(converterData.parseDate(dataCadastro));
            Date dataProxima;
            Date dataAnterior;
            if(linhas!=orcamentarios.size()-1){dataProxima = orcamentarios.get(linhas+1).getDataCadastro();}
            else{
                LocalDate data = converterData.conversaoLocalforDate(dataEntradaBanco).plusDays(1);
                dataProxima = converterData.conversaoLocalforDate(data);}
            
            if(linhas!=0){dataAnterior= orcamentarios.get(linhas-1).getDataCadastro();}
            else{
                LocalDate data = converterData.conversaoLocalforDate(dataEntradaBanco).plusDays(-1);
                dataAnterior = converterData.conversaoLocalforDate(data);}
            
            //Adicionar ao balanço do dia
            if(dataCadastro.compareTo(dataAnterior)!=0){
                ganhoTotalDia = new BigDecimal("0");
                gastoTotalDia = new BigDecimal("0");
            }
            
            //Adicionar aos campos gastos
            if(tipoDado.equals("Gastos")){
                gastoTotal = gastoTotal.add(valor);
                gastoTotalDia = gastoTotalDia.add(valor);
            }
            else{
                ganhoTotal = ganhoTotal.add(valor);
                ganhoTotalDia = ganhoTotalDia.add(valor);
            }
            
            if(dataCadastro.compareTo(dataProxima)!=0){
                String dataFormatada = converterData.parseDate(dataCadastro);
                Object[] dadosDaTabela = {codBanco, tipo, formaPagamento, ganhoTotalDia.subtract(gastoTotalDia), dataFormatada, chave};
                tabelaOrcamentaria.addRow(dadosDaTabela); 
            } 
        }
        view.getCampoGanhoTotal().setText(ganhoTotal.toString());
        view.getCampoDespesaTotal().setText(gastoTotal.toString());
    }
    //Pega as Entradas em determinado Período
    private ArrayList <DetOrcamentario> pegarOrcamentarioNoPeriodo() throws SQLException{
        LocalDate dataAtual = LocalDate.now();
        Date dataBanco = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAtual));
        Date dataPassada;
        
        int periodo = view.getComboPeriodo().getSelectedIndex();
        
        switch(periodo){
            case 1:   
            return orcamentarioDao.pesquisarOrcamentarios("SELECT * FROM tblDetOrcamentario WHERE dataCadastro BETWEEN '"+dataAtual+"' AND '"+dataAtual+"';");                
            
            case 2:
            dataPassada = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAtual.minusWeeks(1)));    
            return orcamentarioDao.pesquisarOrcamentarios("SELECT * FROM tblDetOrcamentario WHERE dataCadastro BETWEEN '"+dataPassada+"' AND '"+dataAtual+"';");

            case 3:
                dataPassada = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAtual.minusMonths(1)));    
                return orcamentarioDao.pesquisarOrcamentarios("SELECT * FROM tblDetOrcamentario WHERE dataCadastro BETWEEN '"+dataPassada+"' AND '"+dataAtual+"';");

            case 4:
                dataPassada = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAtual.minusMonths(6)));    
                return orcamentarioDao.pesquisarOrcamentarios("SELECT * FROM tblDetOrcamentario WHERE dataCadastro BETWEEN '"+dataPassada+"' AND '"+dataAtual+"';"); 

            case 5:
                dataPassada = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAtual.minusYears(1)));    
                return orcamentarioDao.pesquisarOrcamentarios("SELECT * FROM tblDetOrcamentario WHERE dataCadastro BETWEEN '"+dataPassada+"' AND '"+dataAtual+"';");
        }        
        return orcamentarioDao.selecionarTodosOrcamentarios();
    }
    
    private void setarCampoPendentes() throws SQLException, ParseException{
        ArrayList <Planos> planos = planosDao.pesquisarPlanos("SELECT * FROM tblPlanos WHERE situacao != 'Pago'");
        BigDecimal valorPendente = new BigDecimal("0");
        
        if(planos !=null){
            for(int linhas=0; linhas<planos.size(); linhas++){
                Aluno aluno = alunosDao.pesquisarAlunos("SELECT * FROM tblAlunos WHERE codAluno = "+planos.get(linhas).getCodAluno()).get(0);
                valorPendente = valorPendente.add(aluno.getValorContrato());
            }
            valorPendente = valorPendente.setScale(2, RoundingMode.UP);
            view.getCampoPendente().setText(valorPendente.toString());
        }
        else{
            view.getCampoPendente().setText(valorPendente.toString());
        }
    }
    
    private void setarCamposTotais(){
        BigDecimal totalParcial = new BigDecimal("0");
        BigDecimal totalRelativo = new BigDecimal("0");
        BigDecimal ganhoTotal = new BigDecimal(converterDinheiro.converterParaBigDecimal(view.getCampoGanhoTotal().getText()).toString());
        BigDecimal gastoTotal = new BigDecimal(converterDinheiro.converterParaBigDecimal(view.getCampoDespesaTotal().getText()).toString());
        BigDecimal pendentes = new BigDecimal(converterDinheiro.converterParaBigDecimal(view.getCampoPendente().getText()).toString());
        
        totalParcial = ganhoTotal.add(pendentes).subtract(gastoTotal);
        totalRelativo = ganhoTotal.subtract(gastoTotal);
        
        totalParcial = totalParcial.setScale(2, RoundingMode.UP);
        totalRelativo = totalRelativo.setScale(2, RoundingMode.UP);
        
        view.getCampoTotalParcial().setText(totalParcial.toString());
        view.getCampoGanhoRelativoTotal().setText(totalRelativo.toString());
    }
    
    private void limparCampos(){
        view.getCampoGanhoTotal().setText("");
        view.getCampoDespesaTotal().setText("");
        view.getCampoGanhoRelativoTotal().setText("");
        view.getCampoTotalParcial().setText("");
        view.getCampoPendente().setText("");
    }
    
    public void salvarDadosEmPlanilha(){
        int numLinhas = tabelaOrcamentaria.getRowCount();
        if(numLinhas>0){
            String ganhoTotal = view.getCampoGanhoTotal().getText();
            String pendente = view.getCampoPendente().getText();
            String despesaTotal = view.getCampoDespesaTotal().getText();
            String ganhoRelativo = view.getCampoGanhoRelativoTotal().getText();
            String totalParcial = view.getCampoTotalParcial().getText();
            exportarTabela.exportarExcel(ganhoTotal, pendente, despesaTotal, ganhoRelativo, totalParcial);
        }
        else{
            view.exibeMensagem("Inicie uma tabela primeiro!");
        }
    }
}
