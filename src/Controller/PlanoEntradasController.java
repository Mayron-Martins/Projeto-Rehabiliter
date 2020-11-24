/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.auxiliar.ConversaoDeDinheiro;
import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Dao.AlunosDao;
import Dao.DetOrcamentarioDao;
import Dao.EntradasDao;
import Dao.ItensVendidosDao;
import Dao.PlanosDao;
import Dao.TurmasDao;
import Dao.VendasDao;
import Model.Aluno;
import Model.Vendas;
import Model.auxiliar.DetOrcamentario;
import Model.auxiliar.Entradas;
import Model.auxiliar.ItemVendido;
import Model.auxiliar.Planos;
import Model.auxiliar.Turmas;
import View.FinanceiroPlanodeEntradas;
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
public class PlanoEntradasController {
    private final FinanceiroPlanodeEntradas view;
    private final DefaultTableModel tabelaVendas;
    private final DefaultTableModel tabelaPlanos;
    private final DefaultTableModel tabelaEntradas;
    private final VendasDao vendasDao = new VendasDao();
    private final AlunosDao alunosDao = new AlunosDao();
    private final ItensVendidosDao itensVDao = new ItensVendidosDao();
    private final PlanosDao planosDao = new PlanosDao();
    private final TurmasDao turmasDao = new TurmasDao();
    private final EntradasDao entradasDao = new EntradasDao();
    private final DetOrcamentarioDao orcamentarioDao = new DetOrcamentarioDao();
    private final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();
    private final ConversaoDeDinheiro converterDinheiro = new ConversaoDeDinheiro();

    public PlanoEntradasController(FinanceiroPlanodeEntradas view) {
        this.view = view;
        this.tabelaVendas = (DefaultTableModel) view.getTabelaVendas().getModel();
        this.tabelaPlanos = (DefaultTableModel) view.getTabelasPlanos().getModel();
        this.tabelaEntradas = (DefaultTableModel) view.getTabelaEntradas().getModel();
    }
    
    //Limpar tabela
    public void limparTabelaVendas(){
        int quantLinhas = this.view.getTabelaVendas().getRowCount();
        for(int quant=0; quant<quantLinhas; quant++){
            this.tabelaVendas.removeRow(0);
        }
    }
    
    public void limparTabelaPlanos(){
        int quantLinhas = this.view.getTabelasPlanos().getRowCount();
        for(int quant=0; quant<quantLinhas; quant++){
            this.tabelaPlanos.removeRow(0);
        }
    }
    
    public void limparTabelaEntradas(){
        int quantLinhas = this.view.getTabelaEntradas().getRowCount();
        for(int quant=0; quant<quantLinhas; quant++){
            this.tabelaEntradas.removeRow(0);
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
    
    //Parte Detalhada___________________________________________________________
    //Setar as tabelas detalhadamente
    private void setarTabelasDetalhadas() throws SQLException, ParseException{
        int tipoSelecionado = view.getComboTipos().getSelectedIndex();
        ArrayList <Vendas> vendas = this.pegarVendasNoPeriodo();
        ArrayList<Entradas> entradas = this.pegarEntradasNoPeriodo();
        
        switch(tipoSelecionado){
            case 0:
                this.alternarPaineis(1);
                if(vendas!=null){
                   this.setarVendasDetalhadas(vendas); 
                }
                else{
                   view.exibeMensagem("Sem Dados de Vendas");
                   limparTabelaVendas();
                }

            break;

            case 1:
                this.alternarPaineis(2);
                if(vendas!= null){
                   this.setarPlanosDetalhados(vendas);
                }
                else{
                   view.exibeMensagem("Sem Dados de Planos");
                   limparTabelaPlanos(); 
                }
            break;

            case 2:
                this.alternarPaineis(3);
                if(entradas!=null){
                    this.setarEntradasDetalhadas(entradas);
                }
                else{
                   view.exibeMensagem("Sem Dados de Entradas");
                   limparTabelaEntradas();
                }
            break;  
        }
    }
    
    //Setar Vendas Detalhadas
    private void setarVendasDetalhadas(ArrayList <Vendas> vendas) throws ParseException, SQLException{
        limparTabelaVendas();
        for(int linhas=0; linhas<vendas.size(); linhas++){
            int codVenda = vendas.get(linhas).getCodVenda();

            //Dados Aluno
            String codAluno; 
            if(vendas.get(linhas).getCodAluno()==0){codAluno="Sem Cadastro";}
            else{codAluno = String.valueOf(vendas.get(linhas).getCodAluno());}
            
            //Dados Produtos
            ArrayList <ItemVendido> itensVendidos = itensVDao.pesquisarItensVendidos("SELECT * FROM tblItensVendidos WHERE chaveVenda = "+vendas.get(linhas).getChaveVenda());
            String produtos="";
            float quantidade=0;
            BigDecimal valorTotal= new BigDecimal(vendas.get(linhas).getValorVenda().toString());
            
            for(int repeticoes =0; repeticoes<itensVendidos.size(); repeticoes++){
                produtos = produtos.concat(itensVendidos.get(repeticoes).getCodProduto()+"; ");
                quantidade += itensVendidos.get(repeticoes).getQuantidade();
            }
            
            String formaPagamento = vendas.get(linhas).getFormaPagamento();
            String dataVenda = converterData.parseDate(vendas.get(linhas).getDataVenda());
            Object[] dadosDaTabela = {codVenda, codAluno, produtos, quantidade, valorTotal, formaPagamento, dataVenda};
            tabelaVendas.addRow(dadosDaTabela);
        }
    }
    
    //setar Planos Detalhados
    private void setarPlanosDetalhados(ArrayList <Vendas> vendas) throws SQLException, ParseException{
        limparTabelaPlanos();
        for(int linhas=0; linhas<vendas.size(); linhas++){
            //Dados Aluno
            int codAluno = vendas.get(linhas).getCodAluno();
            BigDecimal valorTotal = new BigDecimal(vendas.get(linhas).getValorVenda().toString());
            String dataVenda = converterData.parseDate(vendas.get(linhas).getDataVenda());
            
            Aluno aluno = alunosDao.pesquisarAlunos("SELECT * FROM tblAlunos WHERE codAluno = "+codAluno).get(0);
            Planos plano = planosDao.pesquisarPlanos("SELECT * FROM tblPlanos WHERE codAluno = "+codAluno).get(0);
            Turmas turmas = turmasDao.pesquisarTurmas("SELECT * FROM tblTurmas WHERE codTurma = "+aluno.getTurma()).get(0);

            String subgrupo="";
            if(turmas.getSubgrupo()!=null){subgrupo = "-"+turmas.getSubgrupo();}
            String turma = turmas.getCodBanco()+"."+turmas.getNomeTurma()+subgrupo;

            String situacao = plano.getSituacao();
            int chavePlano = plano.getChavePlano();
            
            Object[] dadosDaTabela = {chavePlano, aluno.getNome(), turma, valorTotal, situacao, dataVenda};
            tabelaPlanos.addRow(dadosDaTabela);
        }
    }
    
    //Setar Entradas Detalhadas
    private void setarEntradasDetalhadas(ArrayList <Entradas> entradas) throws ParseException{
        limparTabelaEntradas();
        for(int linhas=0; linhas<entradas.size(); linhas++){
            int codEntrada = entradas.get(linhas).getCodBanco();
            String referencia = entradas.get(linhas).getReferencia();
            float quantidade = entradas.get(linhas).getQuantidade();
            String formaPagamento = entradas.get(linhas).getFormaPagamento();
            BigDecimal valor = new BigDecimal(entradas.get(linhas).getValorEntrada().toString());
            String dataEntrada = converterData.parseDate(entradas.get(linhas).getDataCadastro());
            
            Object[] dadosDaTabela = {codEntrada, referencia, quantidade, formaPagamento, valor, dataEntrada};
            tabelaEntradas.addRow(dadosDaTabela);
            view.getComboPagamentoEntrada().setEnabled(true); 
            view.getComboPagamentoEntrada().setSelectedItem(formaPagamento);
        }
    }
    //__________________________________________________________________________
    
    //Parte Resumida
    //Setar Tabelas Resumidamente
    private void setarTabelasResumidas() throws SQLException, ParseException{
        int tipoSelecionado = view.getComboTipos().getSelectedIndex();
        ArrayList <Vendas> vendas = this.pegarVendasNoPeriodo();
        ArrayList<Entradas> entradas = this.pegarEntradasNoPeriodo();
        
        switch(tipoSelecionado){
            case 0:
                this.alternarPaineis(1);
                if(vendas!=null){
                   this.setarVendasResumidas(vendas); 
                }
                else{
                   view.exibeMensagem("Sem Dados de Vendas");
                   limparTabelaVendas();
                }

            break;

            case 1:
                this.alternarPaineis(2);
                if(vendas!= null){
                   this.setarPlanosResumidos(vendas);
                }
                else{
                   view.exibeMensagem("Sem Dados de Planos");
                   limparTabelaPlanos(); 
                }
            break;

            case 2:
                this.alternarPaineis(3);
                if(entradas!=null){
                    this.setarEntradasResumidas(entradas);
                }
                else{
                   view.exibeMensagem("Sem Dados de Entradas");
                   limparTabelaEntradas();
                }
            break;  
        }
    }
    
    //Setar Vendas de Forma Resumida
    private void setarVendasResumidas(ArrayList <Vendas> vendas) throws SQLException, ParseException{
        limparTabelaVendas();
        String produtos="Diversos";
        String codAluno = "Diversos";
        String codVenda = "Diversos";
        float quantidade=0;
        BigDecimal valorTotal = new BigDecimal("0");
        
        String formaPagamento=this.pegarFormaPagamento();
        if(view.getComboPagamento().getSelectedIndex()==0){
            formaPagamento = "Diversas";
        }
        
        
        for(int linhas=0; linhas<vendas.size(); linhas++){
            Date dataVenda = vendas.get(linhas).getDataVenda();
            //Datas anteriores e próximas
            Date dataVendaBanco = converterData.parseDate(converterData.parseDate(dataVenda));
            Date dataProxima;
            Date dataAnterior;
            if(linhas!=vendas.size()-1){dataProxima = vendas.get(linhas+1).getDataVenda();}
            else{
                LocalDate data = converterData.conversaoLocalforDate(dataVendaBanco).plusDays(1);
                dataProxima = converterData.conversaoLocalforDate(data);}
            
            if(linhas!=0){dataAnterior= vendas.get(linhas-1).getDataVenda();}
            else{
                LocalDate data = converterData.conversaoLocalforDate(dataVendaBanco).plusDays(-1);
                dataAnterior = converterData.conversaoLocalforDate(data);}
            
            //Dados Produtos
            ArrayList <ItemVendido> itensVendidos = itensVDao.pesquisarItensVendidos("SELECT * FROM tblItensVendidos WHERE chaveVenda = "+vendas.get(linhas).getChaveVenda());
            
            
            if(dataVenda.compareTo(dataAnterior)!=0){
                quantidade=0;
                valorTotal=new BigDecimal("0");
            }           
            valorTotal = valorTotal.add(vendas.get(linhas).getValorVenda());
            
            for(int repeticoes=0; repeticoes<itensVendidos.size(); repeticoes++){
                quantidade += itensVendidos.get(repeticoes).getQuantidade();
            }
            
            if(dataVenda.compareTo(dataProxima)!=0){
            String dataVendaFormatada = converterData.parseDate(dataVenda);
            Object[] dadosDaTabela = {codVenda, codAluno, produtos, quantidade, valorTotal, formaPagamento, dataVendaFormatada};
            tabelaVendas.addRow(dadosDaTabela);  
            }
        }
    }
    
    //Setar Planos Resumidamente
    private void setarPlanosResumidos(ArrayList <Vendas> vendas) throws SQLException, ParseException{
        limparTabelaPlanos();
        String chavePlano = "Diversas";
        String nomeAluno = "Diversos";
        String turma = "Diversas";
        String situacao = "Pago";
        BigDecimal valorTotal = new BigDecimal("0");
        for(int linhas=0; linhas<vendas.size(); linhas++){
            Date dataVenda = vendas.get(linhas).getDataVenda();
            
            //Datas anteriores e próximas
            Date dataVendaBanco = converterData.parseDate(converterData.parseDate(dataVenda));
            Date dataProxima;
            Date dataAnterior;
            if(linhas!=vendas.size()-1){dataProxima = vendas.get(linhas+1).getDataVenda();}
            else{
                LocalDate data = converterData.conversaoLocalforDate(dataVendaBanco).plusDays(1);
                dataProxima = converterData.conversaoLocalforDate(data);}
            
            if(linhas!=0){dataAnterior= vendas.get(linhas-1).getDataVenda();}
            else{
                LocalDate data = converterData.conversaoLocalforDate(dataVendaBanco).plusDays(-1);
                dataAnterior = converterData.conversaoLocalforDate(data);}
            //Dados Aluno
            int codAluno = vendas.get(linhas).getCodAluno();
            
            if(dataVenda.compareTo(dataAnterior)!=0){
               valorTotal = new BigDecimal("0"); 
            }
            
            valorTotal = valorTotal.add(vendas.get(linhas).getValorVenda());
           
            if(dataVenda.compareTo(dataProxima)!=0){
            String dataVendaFormatada = converterData.parseDate(dataVenda);
            Object[] dadosDaTabela = {chavePlano, nomeAluno, turma, valorTotal, situacao, dataVendaFormatada};
            tabelaPlanos.addRow(dadosDaTabela);
            }
        }
    }
    
    //Setar Entradas Resumidamente
    private void setarEntradasResumidas(ArrayList <Entradas> entradas) throws ParseException{
        limparTabelaEntradas();
        
        String codEntrada = "Diversos";
        String referencia = "Diversas";
        float quantidade = 0;
        BigDecimal valor = new BigDecimal("0");
        String formaPagamento = this.pegarFormaPagamento();
        if(view.getComboPagamento().getSelectedIndex()==0){
            formaPagamento = "Diversas";
        }
        
        for(int linhas=0; linhas<entradas.size(); linhas++){
            Date dataEntrada = entradas.get(linhas).getDataCadastro();
            
            //Datas anteriores e próximas
            Date dataEntradaBanco = converterData.parseDate(converterData.parseDate(dataEntrada));
            Date dataProxima;
            Date dataAnterior;
            if(linhas!=entradas.size()-1){dataProxima = entradas.get(linhas+1).getDataCadastro();}
            else{
                LocalDate data = converterData.conversaoLocalforDate(dataEntradaBanco).plusDays(1);
                dataProxima = converterData.conversaoLocalforDate(data);}
            
            if(linhas!=0){dataAnterior= entradas.get(linhas-1).getDataCadastro();}
            else{
                LocalDate data = converterData.conversaoLocalforDate(dataEntradaBanco).plusDays(-1);
                dataAnterior = converterData.conversaoLocalforDate(data);}
            
            if(dataEntrada.compareTo(dataAnterior)!=0){
                quantidade = 0;
                valor = new BigDecimal("0"); 
            }
            
            quantidade += entradas.get(linhas).getQuantidade();
            valor = valor.add(entradas.get(linhas).getValorEntrada());
            
            if(dataEntrada.compareTo(dataProxima)!=0){
            String dataEntradaFormatada = converterData.parseDate(dataEntrada);
            Object[] dadosDaTabela = {codEntrada, referencia, quantidade, formaPagamento, valor, dataEntradaFormatada};
            tabelaEntradas.addRow(dadosDaTabela);
            view.getComboPagamentoEntrada().setEnabled(false);  
            }
        }
    }
    
    //Pega As Vendas em determinado período
    private ArrayList <Vendas> pegarVendasNoPeriodo() throws SQLException{
        LocalDate dataAtual = LocalDate.now();
        Date dataBanco = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAtual));
        Date dataPassada;
        
        int periodo = view.getComboPeriodo().getSelectedIndex();
        String formaPagamento = this.pegarFormaPagamento();
        String tipoVenda = this.tipoVenda();
        
        switch(periodo){
            case 1:   
                if(formaPagamento.equals("nenhuma")){
                    return vendasDao.pesquisarVendas("SELECT * FROM tblVendas WHERE plano = '"+tipoVenda+"' AND dataVenda BETWEEN '"+dataAtual+"' AND '"+dataAtual+"';");
                }else{
                    return vendasDao.pesquisarVendas("SELECT * FROM tblVendas WHERE plano = '"+tipoVenda+"' AND formaPagamento = '"+formaPagamento+"' AND dataVenda BETWEEN '"+dataAtual+"' AND '"+dataAtual+"';");
                }                

            case 2:
            dataPassada = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAtual.minusWeeks(1)));    
                if(formaPagamento.equals("nenhuma")){
                    return vendasDao.pesquisarVendas("SELECT * FROM tblVendas WHERE plano = '"+tipoVenda+"' AND dataVenda BETWEEN '"+dataPassada+"' AND '"+dataAtual+"';");
                }else{
                return vendasDao.pesquisarVendas("SELECT * FROM tblVendas WHERE plano = '"+tipoVenda+"' AND formaPagamento = '"+formaPagamento+"' AND dataVenda BETWEEN '"+dataPassada+"' AND '"+dataAtual+"';");
                }      

            case 3:
                dataPassada = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAtual.minusMonths(1)));    
                if(formaPagamento.equals("nenhuma")){
                    return vendasDao.pesquisarVendas("SELECT * FROM tblVendas WHERE plano = '"+tipoVenda+"' AND dataVenda BETWEEN '"+dataPassada+"' AND '"+dataAtual+"';");
                }else{
                return vendasDao.pesquisarVendas("SELECT * FROM tblVendas WHERE plano = '"+tipoVenda+"' AND formaPagamento = '"+formaPagamento+"' AND dataVenda BETWEEN '"+dataPassada+"' AND '"+dataAtual+"';");
                }  

            case 4:
                dataPassada = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAtual.minusMonths(6)));    
                if(formaPagamento.equals("nenhuma")){
                    return vendasDao.pesquisarVendas("SELECT * FROM tblVendas WHERE plano = '"+tipoVenda+"' AND dataVenda BETWEEN '"+dataPassada+"' AND '"+dataAtual+"';");
                }else{
                return vendasDao.pesquisarVendas("SELECT * FROM tblVendas WHERE plano = '"+tipoVenda+"' AND formaPagamento = '"+formaPagamento+"' AND dataVenda BETWEEN '"+dataPassada+"' AND '"+dataAtual+"';");
                }  

            case 5:
                dataPassada = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAtual.minusYears(1)));    
                if(formaPagamento.equals("nenhuma")){
                    return vendasDao.pesquisarVendas("SELECT * FROM tblVendas WHERE plano = '"+tipoVenda+"' AND dataVenda BETWEEN '"+dataPassada+"' AND '"+dataAtual+"';");
                }else{
                return vendasDao.pesquisarVendas("SELECT * FROM tblVendas WHERE plano = '"+tipoVenda+"' AND formaPagamento = '"+formaPagamento+"' AND dataVenda BETWEEN '"+dataPassada+"' AND '"+dataAtual+"';");
                }  
        }
        
        return null;
    }
    
    //Pega as Entradas em determinado Período
    private ArrayList <Entradas> pegarEntradasNoPeriodo() throws SQLException{
        LocalDate dataAtual = LocalDate.now();
        Date dataBanco = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAtual));
        Date dataPassada;
        
        int periodo = view.getComboPeriodo().getSelectedIndex();
        String formaPagamento = this.pegarFormaPagamento();
        
        switch(periodo){
            case 1:   
                if(formaPagamento.equals("nenhuma")){
                    return entradasDao.pesquisarEntradas("SELECT * FROM tblEntradas WHERE dataCadastro BETWEEN '"+dataAtual+"' AND '"+dataAtual+"';");
                }else{
                    return entradasDao.pesquisarEntradas("SELECT * FROM tblEntradas WHERE  formaPagamento = '"+formaPagamento+"' AND dataCadastro BETWEEN '"+dataAtual+"' AND '"+dataAtual+"';");
                }                

            case 2:
            dataPassada = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAtual.minusWeeks(1)));    
                if(formaPagamento.equals("nenhuma")){
                    return entradasDao.pesquisarEntradas("SELECT * FROM tblEntradas WHERE dataCadastro BETWEEN '"+dataPassada+"' AND '"+dataAtual+"';");
                }else{
                return entradasDao.pesquisarEntradas("SELECT * FROM tblEntradas WHERE formaPagamento = '"+formaPagamento+"' AND dataCadastro BETWEEN '"+dataPassada+"' AND '"+dataAtual+"';");
                }      

            case 3:
                dataPassada = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAtual.minusMonths(1)));    
                if(formaPagamento.equals("nenhuma")){
                    return entradasDao.pesquisarEntradas("SELECT * FROM tblEntradas WHERE dataCadastro BETWEEN '"+dataPassada+"' AND '"+dataAtual+"';");
                }else{
                return entradasDao.pesquisarEntradas("SELECT * FROM tblEntradas WHERE formaPagamento = '"+formaPagamento+"' AND dataCadastro BETWEEN '"+dataPassada+"' AND '"+dataAtual+"';");
                }  

            case 4:
                dataPassada = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAtual.minusMonths(6)));    
                if(formaPagamento.equals("nenhuma")){
                    return entradasDao.pesquisarEntradas("SELECT * FROM tblEntradas WHERE dataCadastro BETWEEN '"+dataPassada+"' AND '"+dataAtual+"';");
                }else{
                return entradasDao.pesquisarEntradas("SELECT * FROM tblEntradas WHERE formaPagamento = '"+formaPagamento+"' AND dataCadastro BETWEEN '"+dataPassada+"' AND '"+dataAtual+"';");
                }  

            case 5:
                dataPassada = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAtual.minusYears(1)));    
                if(formaPagamento.equals("nenhuma")){
                    return entradasDao.pesquisarEntradas("SELECT * FROM tblEntradas WHERE dataCadastro BETWEEN '"+dataPassada+"' AND '"+dataAtual+"';");
                }else{
                return entradasDao.pesquisarEntradas("SELECT * FROM tblEntradas WHERE formaPagamento = '"+formaPagamento+"' AND dataCadastro BETWEEN '"+dataPassada+"' AND '"+dataAtual+"';");
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
            if(view.getComboTipos().getSelectedIndex()==2){
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
    
    //Verifica se é um plano ou não
    private String tipoVenda(){
        if(view.getComboTipos().getSelectedIndex()==0){
            return "N";
        }
        if(view.getComboTipos().getSelectedIndex()==1){
            return "S";
        }
        return null;
    }
    
    //Alterna as tabelas confome selecionado
    private void alternarPaineis(int opcao){
        switch(opcao){
            case 1:
                view.getPainelVendas().setVisible(true);
                view.getPainelPlanos().setVisible(false);
                view.getPainelEntradas().setVisible(false);
            break;
            
            case 2:
                view.getPainelPlanos().setVisible(true);
                view.getPainelVendas().setVisible(false);
                view.getPainelEntradas().setVisible(false);
            break;
            
            case 3:
                view.getPainelEntradas().setVisible(true);
                view.getPainelPlanos().setVisible(false);
                view.getPainelVendas().setVisible(false);
            break;
        }
    }
    //__________________________________________________________________________
    
    //Setar Tabela de Planos Pendentes
    public void setarTabelaPlanosPendentes() throws SQLException, ParseException{
        this.alternarPaineis(2);
        ArrayList <Planos> planos = planosDao.selecionarTodosPlanos();
        if(planos!=null){
            limparTabelaPlanos();
            for(int linhas=0; linhas<planos.size(); linhas++){
                if(!planos.get(linhas).getSituacao().equals("Pago")){
                        //Dados Aluno
                    int codAluno = planos.get(linhas).getCodAluno();
                    String dataPagamento = converterData.parseDate(planos.get(linhas).getDataPagamento());

                    Aluno aluno = alunosDao.pesquisarAlunos("SELECT * FROM tblAlunos WHERE codAluno = "+codAluno).get(0);
                    Turmas turmas = turmasDao.pesquisarTurmas("SELECT * FROM tblTurmas WHERE codTurma = "+aluno.getTurma()).get(0);

                    BigDecimal valorTotal = new BigDecimal(aluno.getValorContrato().toString());
                    String subgrupo="";
                    if(turmas.getSubgrupo()!=null){subgrupo = "-"+turmas.getSubgrupo();}
                    String turma = turmas.getCodBanco()+"."+turmas.getNomeTurma()+subgrupo;

                    String situacao = planos.get(linhas).getSituacao();
                    int chavePlano = planos.get(linhas).getChavePlano();

                    Object[] dadosDaTabela = {chavePlano, aluno.getNome(), turma, valorTotal, situacao, dataPagamento};
                    tabelaPlanos.addRow(dadosDaTabela);
                }
            }
        }else{
            view.exibeMensagem("Sem Planos Cadastrados");
            limparTabelaPlanos();
        }
    }
    
    public void setarTabelaPlanosPagos() throws SQLException, ParseException{
        this.alternarPaineis(2);
        ArrayList <Planos> planos = planosDao.selecionarTodosPlanos();
        if(planos!=null){
            limparTabelaPlanos();
            for(int linhas=0; linhas<planos.size(); linhas++){
                if(planos.get(linhas).getSituacao().equals("Pago")){
                        //Dados Aluno
                    int codAluno = planos.get(linhas).getCodAluno();
                    String dataPagamento = converterData.parseDate(planos.get(linhas).getDataPagamento());

                    Aluno aluno = alunosDao.pesquisarAlunos("SELECT * FROM tblAlunos WHERE codAluno = "+codAluno).get(0);
                    Turmas turmas = turmasDao.pesquisarTurmas("SELECT * FROM tblTurmas WHERE codTurma = "+aluno.getTurma()).get(0);

                    BigDecimal valorTotal = new BigDecimal(aluno.getValorContrato().toString());
                    String subgrupo="";
                    if(turmas.getSubgrupo()!=null){subgrupo = "-"+turmas.getSubgrupo();}
                    String turma = turmas.getCodBanco()+"."+turmas.getNomeTurma()+subgrupo;

                    String situacao = planos.get(linhas).getSituacao();
                    int chavePlano = planos.get(linhas).getChavePlano();

                    Object[] dadosDaTabela = {chavePlano, aluno.getNome(), turma, valorTotal, situacao, dataPagamento};
                    tabelaPlanos.addRow(dadosDaTabela);
                }
            }
        }else{
            view.exibeMensagem("Sem Planos Cadastrados");
            limparTabelaPlanos();
        }
    }
    //Fim das Funções de Setar Tabela
    
    //Editar Entradas
    public void editarEntradas() throws SQLException, ParseException{
        if(view.getBotaoVDetalhada().isEnabled()){
            if(view.getPainelEntradas().isVisible()){
                if(view.getTabelaEntradas().getSelectedRow()!=-1){
                    int linhaSelecionada = view.getTabelaEntradas().getSelectedRow();
                    
                    int codEntrada = Integer.parseInt(tabelaEntradas.getValueAt(linhaSelecionada, 0).toString());
                    String referencia = tabelaEntradas.getValueAt(linhaSelecionada, 1).toString();
                    BigDecimal quantGrande = converterDinheiro.converterParaBigDecimal(tabelaEntradas.getValueAt(linhaSelecionada, 2).toString());
                    float quantidade = quantGrande.floatValue();
                    String formaPagamento = this.retornarFormaPagamento();
                    BigDecimal valorEntrada = converterDinheiro.converterParaBigDecimal(view.getTabelaEntradas().getValueAt(linhaSelecionada, 4).toString());
                    
                    Entradas entrada = new Entradas(codEntrada, referencia, quantidade, formaPagamento, valorEntrada, null);
                    DetOrcamentario orcamentario = new DetOrcamentario(0, "Entradas", formaPagamento, valorEntrada, null, codEntrada);
                    if(referencia==null||quantidade==0||formaPagamento.equals("[Nenhum]")||valorEntrada.compareTo(BigDecimal.ZERO)==0){
                        view.exibeMensagem("Valores Inválidos!");
                    }
                    else{
                        entradasDao.atualizarDados(entrada);
                        orcamentarioDao.atualizarDados(orcamentario);
                        view.exibeMensagem("Sucesso!");
                        setarTabelas();
                    }                   
                }else{view.exibeMensagem("Selecione uma Linha na Tabela Entradas!");}
            }else{view.exibeMensagem("Permitida Edição Somente de Entradas!");}
        }else{view.exibeMensagem("Selecione a Exibição Detalhada");}
    }
    
    //Remover entradas
    public void removerEntradas() throws SQLException, ParseException{
        if(view.getBotaoVDetalhada().isEnabled()){
            if(view.getPainelEntradas().isVisible()){
                if(view.getTabelaEntradas().getSelectedRow()!=-1){
                    int linhaSelecionada = view.getTabelaEntradas().getSelectedRow();
                    
                    int codEntrada = Integer.parseInt(tabelaEntradas.getValueAt(linhaSelecionada, 0).toString());
                    
                    entradasDao.removerEntrada(codEntrada);
                    orcamentarioDao.removerOrcamentario("Entradas", codEntrada);
                    view.exibeMensagem("Sucesso!");
                    setarTabelas();
                                    
                }else{view.exibeMensagem("Selecione uma Linha na Tabela Entradas!");}
            }else{view.exibeMensagem("Permitida Remoção Somente de Entradas!");}
        }else{view.exibeMensagem("Selecione a Exibição Detalhada");}
    }
    
        private String retornarFormaPagamento(){
        int valorSelecionado = view.getComboPagamentoEntrada().getSelectedIndex();
        
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
}