/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.auxiliar.ConversaoDeDinheiro;
import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Dao.DetOrcamentarioDao;
import Dao.FuncionarioDao;
import Dao.GastosDao;
import Dao.LogAçoesFuncionarioDao;
import Model.Funcionario;
import Model.auxiliar.DetOrcamentario;
import Model.auxiliar.Gastos;
import Model.auxiliar.LogAçoesFuncionario;
import View.FinanceiroPlanodeContra;
import java.math.BigDecimal;
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
public class PlanoGastosController {
    private final FinanceiroPlanodeContra view;
    private final DefaultTableModel tabelaGastos;
    private final GastosDao gastosDao = new GastosDao();
    private final DetOrcamentarioDao orcamentarioDao = new DetOrcamentarioDao();
    private final FuncionarioDao funcionarioDao = new FuncionarioDao();
    private final LogAçoesFuncionarioDao logDao = new LogAçoesFuncionarioDao();
    private final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();
    private final ConversaoDeDinheiro converterDinheiro = new ConversaoDeDinheiro();

    public PlanoGastosController(FinanceiroPlanodeContra view) {
        this.view = view;
        this.tabelaGastos = (DefaultTableModel) view.getTabelaGastos().getModel();
    }

        //Limpar tabela
    public void limparTabelaGastos(){
        int quantLinhas = this.view.getTabelaGastos().getRowCount();
        for(int quant=0; quant<quantLinhas; quant++){
            this.tabelaGastos.removeRow(0);
        }
    }
    
    //Setar Tabelas conforme forma Detalha ou Resumida
    public void setarTabelas() throws SQLException, ParseException{
        if(view.getBotaoVResumida().isEnabled()){
            this.setarTabelasResumidas();
        }
        else{
            this.setarTabelasDetalhadas();
        }
    }
    
    //Parte Detalhada
    private void setarTabelasDetalhadas() throws SQLException, ParseException{
        int tipoSelecionado = view.getComboTipos().getSelectedIndex();
        ArrayList <Gastos> gastos = this.pegarGastosNoPeriodo();
        
        switch(tipoSelecionado){
            case 0:
                if(gastos!=null){
                   this.setarGastosDetalhados(gastos);
                }
                else{
                   view.exibeMensagem("Sem Dados de Gastos");
                   limparTabelaGastos();
                }

            break; 
        }
    }
    
    //Setar Entradas Detalhadas
    private void setarGastosDetalhados(ArrayList <Gastos> gastos) throws ParseException{
        limparTabelaGastos();
        for(int linhas=0; linhas<gastos.size(); linhas++){
            int codEntrada = gastos.get(linhas).getCodBanco();
            String motivo = gastos.get(linhas).getMotivo();
            float quantidade = gastos.get(linhas).getQuantidade();
            String formaPagamento = gastos.get(linhas).getFormaPagamento();
            BigDecimal valor = new BigDecimal(gastos.get(linhas).getValorGasto().toString());
            String dataGasto = converterData.parseDate(gastos.get(linhas).getDataCadastro());
            
            Object[] dadosDaTabela = {codEntrada, motivo, quantidade, formaPagamento, valor, dataGasto};
            tabelaGastos.addRow(dadosDaTabela);
            view.getComboPagamentoGasto().setEnabled(true);
            view.getComboPagamentoGasto().setSelectedItem(formaPagamento);
        }
    }
    //__________________________________________________________________________
    
    //Parte Resumida
    //Setar Tabelas Resumidamente
    private void setarTabelasResumidas() throws SQLException, ParseException{
        int tipoSelecionado = view.getComboTipos().getSelectedIndex();
        ArrayList <Gastos> gastos = this.pegarGastosNoPeriodo();
        
        switch(tipoSelecionado){
            case 0:
                if(gastos!=null){
                   this.setarGastosResumidos(gastos);
                }
                else{
                   view.exibeMensagem("Sem Dados de Gastos");
                   limparTabelaGastos();
                }

            break;
        }
    }
    
    
    //Setar Entradas Resumidamente
    private void setarGastosResumidos(ArrayList <Gastos> gastos) throws ParseException{
        limparTabelaGastos();
        
        String codGasto = "Diversos";
        String motivo = "Diversas";
        float quantidade = 0;
        BigDecimal valor = new BigDecimal("0");
        String formaPagamento = this.pegarFormaPagamento();
        if(view.getComboPagamento().getSelectedIndex()==0){
            formaPagamento = "Diversas";
        }
        
        for(int linhas=0; linhas<gastos.size(); linhas++){
            Date dataGasto = gastos.get(linhas).getDataCadastro();
            
            //Datas anteriores e próximas
            Date dataEntradaBanco = converterData.parseDate(converterData.parseDate(dataGasto));
            Date dataProxima;
            Date dataAnterior;
            if(linhas!=gastos.size()-1){dataProxima = gastos.get(linhas+1).getDataCadastro();}
            else{
                LocalDate data = converterData.conversaoLocalforDate(dataEntradaBanco).plusDays(1);
                dataProxima = converterData.conversaoLocalforDate(data);}
            
            if(linhas!=0){dataAnterior= gastos.get(linhas-1).getDataCadastro();}
            else{
                LocalDate data = converterData.conversaoLocalforDate(dataEntradaBanco).plusDays(-1);
                dataAnterior = converterData.conversaoLocalforDate(data);}
            
            if(dataGasto.compareTo(dataAnterior)!=0){
                quantidade = 0;
                valor = new BigDecimal("0"); 
            }
            
            quantidade += gastos.get(linhas).getQuantidade();
            valor = valor.add(gastos.get(linhas).getValorGasto());
            
            if(dataGasto.compareTo(dataProxima)!=0){
            String dataGastoFormatada = converterData.parseDate(dataGasto);
            Object[] dadosDaTabela = {codGasto, motivo, quantidade, formaPagamento, valor, dataGastoFormatada};
            tabelaGastos.addRow(dadosDaTabela);
            view.getComboPagamentoGasto().setEnabled(false);
            }
        }
    }
    
    //Pega os Gastos em determinado Período
    private ArrayList <Gastos> pegarGastosNoPeriodo() throws SQLException{
        LocalDate dataAtual = LocalDate.now();
        Date dataBanco = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAtual));
        Date dataPassada;
        
        int periodo = view.getComboPeriodo().getSelectedIndex();
        if(view.getCampoDataEspecífica().isEnabled()&&view.getCampoDataEspecífica().getDate()!=null){
            periodo = 0;
            Date dataCampo = view.getCampoDataEspecífica().getDate();
            dataBanco = converterData.getSqlDate(dataCampo);
        }
        
        String formaPagamento = this.pegarFormaPagamento();
        
        switch(periodo){
            case 0:
                if(formaPagamento.equals("nenhuma")){
                    return gastosDao.pesquisarGastos("SELECT * FROM tblGastos WHERE dataGasto BETWEEN '"+dataBanco+"' AND '"+dataBanco+"';");
                }else{
                    return gastosDao.pesquisarGastos("SELECT * FROM tblGastos WHERE  formaPagamento = '"+formaPagamento+"' AND dataGasto BETWEEN '"+dataBanco+"' AND '"+dataBanco+"';");
                } 
                
            case 1:   
                if(formaPagamento.equals("nenhuma")){
                    return gastosDao.pesquisarGastos("SELECT * FROM tblGastos WHERE dataGasto BETWEEN '"+dataAtual+"' AND '"+dataAtual+"';");
                }else{
                    return gastosDao.pesquisarGastos("SELECT * FROM tblGastos WHERE  formaPagamento = '"+formaPagamento+"' AND dataGasto BETWEEN '"+dataAtual+"' AND '"+dataAtual+"';");
                }                

            case 2:
            dataPassada = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAtual.minusWeeks(1)));    
                if(formaPagamento.equals("nenhuma")){
                    return gastosDao.pesquisarGastos("SELECT * FROM tblGastos WHERE dataGasto BETWEEN '"+dataPassada+"' AND '"+dataAtual+"';");
                }else{
                return gastosDao.pesquisarGastos("SELECT * FROM tblGastos WHERE formaPagamento = '"+formaPagamento+"' AND dataGasto BETWEEN '"+dataPassada+"' AND '"+dataAtual+"';");
                }      

            case 3:
                dataPassada = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAtual.minusMonths(1)));    
                if(formaPagamento.equals("nenhuma")){
                    return gastosDao.pesquisarGastos("SELECT * FROM tblGastos WHERE dataGasto BETWEEN '"+dataPassada+"' AND '"+dataAtual+"';");
                }else{
                return gastosDao.pesquisarGastos("SELECT * FROM tblGastos WHERE formaPagamento = '"+formaPagamento+"' AND dataGasto BETWEEN '"+dataPassada+"' AND '"+dataAtual+"';");
                }  

            case 4:
                dataPassada = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAtual.minusMonths(6)));    
                if(formaPagamento.equals("nenhuma")){
                    return gastosDao.pesquisarGastos("SELECT * FROM tblGastos WHERE dataGasto BETWEEN '"+dataPassada+"' AND '"+dataAtual+"';");
                }else{
                return gastosDao.pesquisarGastos("SELECT * FROM tblGastos WHERE formaPagamento = '"+formaPagamento+"' AND dataGasto BETWEEN '"+dataPassada+"' AND '"+dataAtual+"';");
                }  

            case 5:
                dataPassada = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAtual.minusYears(1)));    
                if(formaPagamento.equals("nenhuma")){
                    return gastosDao.pesquisarGastos("SELECT * FROM tblGastos WHERE dataGasto BETWEEN '"+dataPassada+"' AND '"+dataAtual+"';");
                }else{
                return gastosDao.pesquisarGastos("SELECT * FROM tblGastos WHERE formaPagamento = '"+formaPagamento+"' AND dataGasto BETWEEN '"+dataPassada+"' AND '"+dataAtual+"';");
                }  
        }
        
        return null;
    }
    
    //Pega a forma de pagamento do combo Forma de Pagamento e retorna um valor
    private String pegarFormaPagamento(){
        int comboPagamento = view.getComboPagamento().getSelectedIndex();
        
        switch(comboPagamento){
            case 1:
            return "Dinheiro";
            
            case 2:
            if(view.getComboTipos().getSelectedIndex()==0){
                return "Boleto";
            }
            return "Dinheiro";
            
            case 3:
            return "Crédito";

            case 4:
            return "Débito";
        }
        return "nenhuma";
    }
    
   //Fim das Funções de Setar Tabela
    //Editar Entradas
    public void editarEntradas() throws SQLException, ParseException{
        if(view.getBotaoVDetalhada().isEnabled()){
            if(view.getPainelGastos().isVisible()){
                if(view.getTabelaGastos().getSelectedRow()!=-1){
                    int linhaSelecionada = view.getTabelaGastos().getSelectedRow();
                    
                    int codGasto = Integer.parseInt(tabelaGastos.getValueAt(linhaSelecionada, 0).toString());
                    String motivo = tabelaGastos.getValueAt(linhaSelecionada, 1).toString();
                    BigDecimal quantGrande = converterDinheiro.converterParaBigDecimal(tabelaGastos.getValueAt(linhaSelecionada, 2).toString());
                    float quantidade = quantGrande.floatValue();
                    String formaPagamento = this.retornarFormaPagamento();
                    BigDecimal valorGasto = converterDinheiro.converterParaBigDecimal(view.getTabelaGastos().getValueAt(linhaSelecionada, 4).toString());
                    
                    Gastos gasto = new Gastos(codGasto, motivo, quantidade, formaPagamento, valorGasto, null);
                    DetOrcamentario orcamentario = new DetOrcamentario(0, "Gastos", formaPagamento, valorGasto, null, this.chaveGasto(codGasto));
                    if(motivo==null||quantidade==0||formaPagamento.equals("[Nenhum]")||valorGasto.compareTo(BigDecimal.ZERO)==0){
                        view.exibeMensagem("Valores Inválidos!");
                    }
                    else{
                        gastosDao.atualizarDados(gasto);
                        orcamentarioDao.atualizarDados(orcamentario);
                        
                        ArrayList <Funcionario> funcionarios = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE status = 'Ativo'");
                        if(funcionarios!=null){
                            String acao = "Edição de Gasto";
                            String descricao = "Editou o gasto "+motivo;
                            this.setarLog(funcionarios, acao, descricao);
                        }
                        view.exibeMensagem("Sucesso!");
                        setarTabelas();
                    }                   
                }else{view.exibeMensagem("Selecione uma Linha na Tabela Gastos!");}
            }else{view.exibeMensagem("Permitida Edição Somente de Gastos!");}
        }else{view.exibeMensagem("Selecione a Exibição Detalhada");}
    }
    
    //Remover entradas
    public void removerEntradas() throws SQLException, ParseException{
        if(view.getBotaoVDetalhada().isEnabled()){
            if(view.getPainelGastos().isVisible()){
                if(view.getTabelaGastos().getSelectedRow()!=-1){
                    int linhaSelecionada = view.getTabelaGastos().getSelectedRow();
                    
                    int codGasto = Integer.parseInt(tabelaGastos.getValueAt(linhaSelecionada, 0).toString());
                    String motivo = tabelaGastos.getValueAt(linhaSelecionada, 1).toString();
                    
                    gastosDao.removerGastos(codGasto);
                    orcamentarioDao.removerOrcamentario("Gastos", this.chaveGasto(codGasto));
                    
                    ArrayList <Funcionario> funcionarios = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE status = 'Ativo'");
                        if(funcionarios!=null){
                            String acao = "Remoção de Gasto";
                            String descricao = "Removeu o gasto "+motivo;
                            this.setarLog(funcionarios, acao, descricao);
                        }
                    view.exibeMensagem("Sucesso!");
                    setarTabelas();
                                    
                }else{view.exibeMensagem("Selecione uma Linha na Tabela Gastos!");}
            }else{view.exibeMensagem("Permitida Remoção Somente de Gastos!");}
        }else{view.exibeMensagem("Selecione a Exibição Detalhada");}
    }
    
        private String retornarFormaPagamento(){
        int valorSelecionado = view.getComboPagamentoGasto().getSelectedIndex();
        
        switch(valorSelecionado){
            case 1:
                return "Dinheiro";
            case 2:
                return "Boleto";
            case 3:
                return "Crédito";
            case 4:
                return "Débito";
        }
        return "[Nenhum]";
    }
        
        private long chaveGasto(int codGasto) throws SQLException{
            Gastos gasto = gastosDao.pesquisarGastos("SELECT * FROM tblGastos WHERE codGasto = "+codGasto).get(0);
            return gasto.getChaveTransacao();
        }
        
    private LogAçoesFuncionario setarLog(ArrayList <Funcionario> funcionarios, String acao, String descricao){
        Funcionario funcionario = funcionarios.get(0);
        Date dataEvento = new Date();
        LogAçoesFuncionario logAcao = new LogAçoesFuncionario(funcionario.getCodBanco(), dataEvento, acao, descricao);
        return logAcao;
    }
}
