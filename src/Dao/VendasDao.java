/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Controller.auxiliar.LogsSystem;
import Model.Vendas;
import Model.auxiliar.ItemVendido;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mayro
 */
public class VendasDao extends Conexao{
    private final String inserir = "INSERT INTO ";
    private final String atualizar = "UPDATE ";
    private final String remover = "DELETE FROM ";
    private final String selecionarTudo = "SELECT * FROM ";
    private final ItensVendidosDao itensVendidosDao = new ItensVendidosDao();
    private ConversaodeDataParaPadraoDesignado converterData;
    
    
    //Inserir dados na tabela Vendas
    public void inserirDados (Vendas venda, ArrayList<ItemVendido> item){
        try{
            //Adicionando Turma
            String inVendas = inserir.concat("tblVendas("
                    + "codVenda, codCliente, codAluno, chavePlano, valorVenda, valorPagoCliente, valorTroco, dataVenda, chaveVenda, formaPagamento, plano, parcelas)"
                    + "VALUES("
                    + "?,?,?,?,?,?,?,?,?,?,?,?);");
            PreparedStatement statement = gerarStatement(inVendas);
            statement.setInt(1, venda.getCodVenda());
            statement.setInt(2, venda.getCodCliente());
            statement.setInt(3, venda.getCodAluno());
            statement.setInt(4, venda.getChavePlano());
            statement.setBigDecimal(5, new BigDecimal(venda.getValorVenda().toString()));
            statement.setBigDecimal(6, new BigDecimal(venda.getValorPago().toString()));
            statement.setBigDecimal(7, new BigDecimal(venda.getValorTroco().toString()));
            statement.setDate(8, (Date) venda.getDataVenda());
            statement.setLong(9, venda.getChaveVenda());
            statement.setString(10, venda.getFormaPagamento());
            statement.setString(11, venda.getPlano());
            statement.setInt(12, venda.getParcelas());
            statement.execute();
            statement.close();

            //Adicionando Horário da Turma
            itensVendidosDao.inserirDadosEmItensVendidos(statement, item);
        } catch (SQLException ex) {
            gerarLog(ex);
        }
        
    }
    
    
    public ArrayList <Vendas> selecionarTodasVendas(){
        String inVendas = selecionarTudo.concat("tblVendas");
        return pesquisarVendas(inVendas);
    }
    
    public ArrayList <Vendas> pesquisarVendas(String comando){
        try{
            ArrayList <Vendas> vendas = new ArrayList();
            PreparedStatement statement = gerarStatement(comando);
            statement.execute();
            ResultSet resultset = statement.getResultSet();
            boolean next = resultset.next();

            if(next==false){
                return null;
            }

            do{
            int codVenda = resultset.getInt("codVenda");
            int codCliente = resultset.getInt("codCliente");
            int codAluno = resultset.getInt("codAluno");
            int chavePlano = resultset.getInt("chavePlano");
            BigDecimal valorVenda = new BigDecimal(resultset.getBigDecimal("valorVenda").toString());
            BigDecimal valorPago = new BigDecimal(resultset.getBigDecimal("valorPagoCliente").toString());
            BigDecimal valorTroco = new BigDecimal(resultset.getBigDecimal("valorTroco").toString());
            Date dataVenda = resultset.getDate("dataVenda");
            String formaPagamento = resultset.getString("formaPagamento");
            long chaveVenda = resultset.getLong("chaveVenda");
            String plano = resultset.getString("plano");
            int parcelas = resultset.getInt("parcelas");

            Vendas venda = new Vendas(codVenda, codCliente, codAluno, chavePlano, valorVenda, valorPago, valorTroco, dataVenda, formaPagamento, plano, chaveVenda, parcelas);

            vendas.add(venda);
             }while(resultset.next());

            statement.close();
            return vendas;
        } catch (SQLException ex) {
            gerarLog(ex);
            return null;
        }
        
    }
    
    //Gerar arquivo com o log de erro, caso haja
    private void gerarLog(Throwable erro){
        LogsSystem gerarLog = new LogsSystem();
        gerarLog.gravarErro(erro);
        gerarLog.close();
    }
    
}
