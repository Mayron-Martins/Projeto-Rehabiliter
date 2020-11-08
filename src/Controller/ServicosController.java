/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.auxiliar.ConversaoDeDinheiro;
import Dao.ServicosDao;
import Model.auxiliar.Servicos;
import View.ServicosView;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mayro
 */
public class ServicosController {
    private final ServicosView view;
    private final DefaultTableModel tabelaDeServicos;
    private final ServicosDao servicosDao = new ServicosDao();
    private final ConversaoDeDinheiro converterDinheiro = new ConversaoDeDinheiro();

    public ServicosController(ServicosView view) {
        this.view = view;
        this.tabelaDeServicos = (DefaultTableModel) view.getTabelaServicos().getModel();
    }
    
    //Limpar tabela
    public void limparTabela(){
        int quantLinhas = this.view.getTabelaServicos().getRowCount();    
        for(int quant=0; quant<quantLinhas; quant++){
            this.tabelaDeServicos.removeRow(quant);
        }
    }
    
    //Lista todas as turmas 
    public void listarServicos() throws SQLException{
        ArrayList <Servicos> servicos = new ArrayList<>();
        servicos = this.servicosDao.selecionarTodosServicos();
        if(servicos==null){
            view.exibeMensagem("Sem Serviços Cadastradas");
        } else{
            for(int linhas=0; linhas<servicos.size(); linhas++){
                    String formaPagamento = servicos.get(linhas).getFormaPagamento();
                    if(formaPagamento.equals("Dinheiro")){
                        Object[] dadosDaTabela = {servicos.get(linhas).getCodBanco(), 
                        servicos.get(linhas).getNome(),servicos.get(linhas).getPeriodo(),
                        servicos.get(linhas).getFormaPagamento(),servicos.get(linhas).getValorVista()};
                        this.tabelaDeServicos.addRow(dadosDaTabela);
                    }
                    
                    if(formaPagamento.equals("Boleto")){
                        Object[] dadosDaTabela = {servicos.get(linhas).getCodBanco(), 
                        servicos.get(linhas).getNome(),servicos.get(linhas).getPeriodo(),
                        servicos.get(linhas).getFormaPagamento(),servicos.get(linhas).getValorBoleto()};
                        this.tabelaDeServicos.addRow(dadosDaTabela);
                    }
                    
                    if(formaPagamento.equals("Cartão de Crédito")){
                        Object[] dadosDaTabela = {servicos.get(linhas).getCodBanco(), 
                        servicos.get(linhas).getNome(),servicos.get(linhas).getPeriodo(),
                        servicos.get(linhas).getFormaPagamento(),servicos.get(linhas).getValorPrazoCredito()};
                        this.tabelaDeServicos.addRow(dadosDaTabela);
                    }
                    if(formaPagamento.equals("Cartão de Débito")){
                        Object[] dadosDaTabela = {servicos.get(linhas).getCodBanco(), 
                        servicos.get(linhas).getNome(),servicos.get(linhas).getPeriodo(),
                        servicos.get(linhas).getFormaPagamento(),servicos.get(linhas).getValorPrazoDebito()};
                        this.tabelaDeServicos.addRow(dadosDaTabela);
                    }

            }
        }        
    }
    
    public void editarServicos() throws SQLException{
        if(this.view.getTabelaServicos().getSelectedRow()!= -1){
            int linhaSelecionada = this.view.getTabelaServicos().getSelectedRow();
            int codTurma = Integer.parseInt(tabelaDeServicos.getValueAt(linhaSelecionada, 0).toString());
            String nome = this.tabelaDeServicos.getValueAt(linhaSelecionada, 1).toString();
            
            String periodo = view.getComboPeriodo().getSelectedItem().toString();
            String metodoDePagamento = view.getMetodoPagamento().getSelectedItem().toString();
            
            BigDecimal valorAVista = new BigDecimal("0");
            BigDecimal valorBoleto = new BigDecimal("0");
            BigDecimal valorAPrazoCredito = new BigDecimal("0");
            BigDecimal valorAPrazoDebito = new BigDecimal("0");
            
            String valorDinheiro = converterDinheiro.converterParaBigDecimal(view.getTabelaServicos().getValueAt(linhaSelecionada, 4).toString()).toString();
            if(metodoDePagamento.equals("Dinheiro")){valorAVista = new BigDecimal(valorDinheiro);}
            if(metodoDePagamento.equals("Boleto")){valorBoleto = new BigDecimal(valorDinheiro);}
            if(metodoDePagamento.equals("Cartão de Crédito")){valorAPrazoCredito = new BigDecimal(valorDinheiro);}
            if(metodoDePagamento.equals("Cartão de Débito")){valorAPrazoDebito = new BigDecimal(valorDinheiro);}
            
            Servicos servico = new Servicos(codTurma, nome, periodo, metodoDePagamento, valorAVista, valorBoleto, valorAPrazoCredito, valorAPrazoDebito);
        
        //Inserindo Dados
        if(nome.equals("")){
         view.exibeMensagem("Campos Preenchidos Incorretamente");
        } else{
            servicosDao.atualizarDados(servico);
            view.exibeMensagem("Sucesso!");
            //Limpando Campos
            limparTabela();
            listarServicos();
        }
            //Turmas 
        }
        
        else{this.view.exibeMensagem("Erro, Nenhum Servio Selecionado!");}
    }
    
    public void selecionarTabela() throws SQLException{
      if(this.view.getTabelaServicos().getSelectedRow()!=-1){
          //Número da linha selecionada
          int linhaSelecionada = this.view.getTabelaServicos().getSelectedRow();
          //Habilita itens que ficam acima da tabela
          this.view.habilitarComponentes();
          //Pega o valor da coluna Tipo
          String periodo = tabelaDeServicos.getValueAt(linhaSelecionada, 2).toString();
          //Pega o valor da coluna Forma de Pagamento
          String metodoDePagamento = tabelaDeServicos.getValueAt(linhaSelecionada, 3).toString();
          //Joga o valor das colunas no combobox
          view.getComboPeriodo().setSelectedItem(periodo);
          view.getComboPeriodo().setSelectedItem(metodoDePagamento);  
      }
    }
    
    public void removerServico() throws SQLException{
        if(this.view.getTabelaServicos().getSelectedRow()!= -1){
            int linhaSelecionada = this.view.getTabelaServicos().getSelectedRow();
            int codTurma = Integer.parseInt(tabelaDeServicos.getValueAt(linhaSelecionada, 0).toString());
            
            servicosDao.removerServico(codTurma);
            this.view.exibeMensagem("Sucesso");
            limparTabela();
            this.view.desabilitarComponentes();
            listarServicos();
        }
        else{this.view.exibeMensagem("Erro, Nenhum Serviço Selecionado!");}
    }
    
    
    
}
