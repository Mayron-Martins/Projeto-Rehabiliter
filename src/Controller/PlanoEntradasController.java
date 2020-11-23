/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Dao.AlunosDao;
import Dao.ItensVendidosDao;
import Dao.PlanosDao;
import Dao.TurmasDao;
import Dao.VendasDao;
import Model.Aluno;
import Model.Vendas;
import Model.auxiliar.ItemVendido;
import Model.auxiliar.Planos;
import Model.auxiliar.Turmas;
import View.FinanceiroPlanodeEntradas;
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
    private final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();

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
    
    public void setarTabelasVendasePlanos() throws SQLException, ParseException{
        int tipoSelecionado = view.getComboTipos().getSelectedIndex();
        ArrayList <Vendas> vendas = this.pegarVendasNoPeriodo();
        System.out.println(vendas.size());
        for(int linhas=0; linhas<vendas.size(); linhas++){
            int codVenda = vendas.get(linhas).getCodVenda();

            //Dados Aluno
            int codAluno = vendas.get(linhas).getCodAluno();
            

            
            //Dados Produtos
            ArrayList <ItemVendido> itensVendidos = itensVDao.pesquisarItensVendidos("SELECT * FROM tblItensVendidos WHERE chaveVenda = "+vendas.get(linhas).getChaveVenda());
            String produtos="";
            float quantidade=0;
            BigDecimal valorTotal=new BigDecimal("0");
            
            for(int repeticoes =0; repeticoes<itensVendidos.size(); repeticoes++){
                produtos = produtos.concat(itensVendidos.get(repeticoes).getCodProduto()+"; ");
                quantidade += itensVendidos.get(repeticoes).getQuantidade();
                valorTotal = new BigDecimal((valorTotal.add(itensVendidos.get(repeticoes).getSubtotal())).toString());
            }
            
            valorTotal = valorTotal.setScale(2, RoundingMode.UP);
            String formaPagamento = vendas.get(linhas).getFormaPagamento();
            String dataVenda = converterData.parseDate(vendas.get(linhas).getDataVenda());
            
            if(tipoSelecionado==0){
                limparTabelaVendas();
                this.alternarPaineis(1);
                Object[] dadosDaTabela = {codVenda, codAluno, produtos, quantidade, valorTotal, formaPagamento, dataVenda};
                tabelaVendas.addRow(dadosDaTabela);
            }

            if(tipoSelecionado==1){
                Aluno aluno = alunosDao.pesquisarAlunos("SELECT * FROM tblAlunos WHERE codAluno = "+codAluno).get(0);
                Planos plano = planosDao.pesquisarPlanos("SELECT * FROM tblPlanos WHERE codAluno = "+codAluno).get(0);
                Turmas turmas = turmasDao.pesquisarTurmas("SELECT * FROM tblTurmas WHERE codTurma = "+aluno.getTurma()).get(0);
                
                String subgrupo="";
                if(turmas.getSubgrupo()!=null){subgrupo = "-"+turmas.getSubgrupo();}
                String turma = turmas.getCodBanco()+"."+turmas.getNomeTurma()+subgrupo;
                
                String situacao = plano.getSituacao();
                int chavePlano = plano.getChavePlano();
                
                limparTabelaPlanos();
                Object[] dadosDaTabela = {chavePlano, aluno.getNome(), turma, valorTotal, situacao, dataVenda};
                tabelaPlanos.addRow(dadosDaTabela);
            }
        }
    }
    
    private ArrayList <Vendas> pegarVendasNoPeriodo() throws SQLException{
        LocalDate dataAtual = LocalDate.now();
        Date dataBanco = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAtual));
        Date dataPassada;
        
        int periodo = view.getComboPeriodo().getSelectedIndex();
        String formaPagamento = this.pegarFormaPagamento();
        String tipoVenda = this.pegarFormaPagamento();
        
        switch(periodo){
            case 1:   
                if(formaPagamento.equals("nenhuma")){
                    return vendasDao.pesquisarVendas("SELECT * FROM tblVendas WHERE plano = '"+tipoVenda+"' AND dataVenda BETWEEN '"+dataAtual+"' AND '"+dataAtual+"';");
                }else{
                    return vendasDao.pesquisarVendas("SELECT * FROM tblVendas WHERE plano = '"+tipoVenda+"' AND formaPagamento = "+formaPagamento+" AND dataVenda BETWEEN '"+dataAtual+"' AND '"+dataAtual+"';");
                }                

            case 2:
            dataPassada = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAtual.minusWeeks(1)));    
                if(formaPagamento.equals("nenhuma")){
                    return vendasDao.pesquisarVendas("SELECT * FROM tblVendas WHERE plano = '"+tipoVenda+"' AND dataVenda BETWEEN '"+dataPassada+"' AND '"+dataAtual+"';");
                }else{
                return vendasDao.pesquisarVendas("SELECT * FROM tblVendas WHERE plano = '"+tipoVenda+"' AND formaPagamento = "+formaPagamento+" AND dataVenda BETWEEN '"+dataPassada+"' AND '"+dataAtual+"';");
                }      

            case 3:
                dataPassada = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAtual.minusMonths(1)));    
                if(formaPagamento.equals("nenhuma")){
                    return vendasDao.pesquisarVendas("SELECT * FROM tblVendas WHERE plano = '"+tipoVenda+"' AND dataVenda BETWEEN '"+dataPassada+"' AND '"+dataAtual+"';");
                }else{
                return vendasDao.pesquisarVendas("SELECT * FROM tblVendas WHERE plano = '"+tipoVenda+"' AND formaPagamento = "+formaPagamento+" AND dataVenda BETWEEN '"+dataPassada+"' AND '"+dataAtual+"';");
                }  

            case 4:
                dataPassada = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAtual.minusMonths(6)));    
                if(formaPagamento.equals("nenhuma")){
                    return vendasDao.pesquisarVendas("SELECT * FROM tblVendas WHERE plano = '"+tipoVenda+"' AND dataVenda BETWEEN '"+dataPassada+"' AND '"+dataAtual+"';");
                }else{
                return vendasDao.pesquisarVendas("SELECT * FROM tblVendas WHERE plano = '"+tipoVenda+"' AND formaPagamento = "+formaPagamento+" AND dataVenda BETWEEN '"+dataPassada+"' AND '"+dataAtual+"';");
                }  

            case 5:
                dataPassada = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAtual.minusYears(1)));    
                if(formaPagamento.equals("nenhuma")){
                    return vendasDao.pesquisarVendas("SELECT * FROM tblVendas WHERE plano = '"+tipoVenda+"' AND dataVenda BETWEEN '"+dataPassada+"' AND '"+dataAtual+"';");
                }else{
                return vendasDao.pesquisarVendas("SELECT * FROM tblVendas WHERE plano = '"+tipoVenda+"' AND formaPagamento = "+formaPagamento+" AND dataVenda BETWEEN '"+dataPassada+"' AND '"+dataAtual+"';");
                }  
        }
        
        return null;
    }
    
    private String pegarFormaPagamento(){
        int comboPagamento = view.getComboPagamento().getSelectedIndex();
        
        switch(comboPagamento){
            case 1:
            return "Dinheiro";
            
            case 2:
            return "Dinheiro";
            
            case 3:
            return "Crédito";
            
            case 4:
            return "Débito";
            
        }
        return "nenhuma";
    }
    
    private String tipoVenda(){
        if(view.getComboTipos().getSelectedIndex()==0){
            return "N";
        }
        if(view.getComboTipos().getSelectedIndex()==1){
            return "S";
        }
        return null;
    }
    
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
}