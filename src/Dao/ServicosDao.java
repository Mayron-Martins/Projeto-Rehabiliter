/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Controller.auxiliar.LogsSystem;
import Model.auxiliar.Servicos;
import java.math.BigDecimal;
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
public class ServicosDao extends Conexao{
    private final String inserir = "INSERT INTO ";
    private final String atualizar = "UPDATE ";
    private final String remover = "DELETE FROM ";
    private final String selecionarTudo = "SELECT * FROM ";
    private final HorariosDao horariosDao = new HorariosDao();
    private ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();
    
    
    //Inserir dados na tabela Servicos
    public void inserirDados (Servicos servico){
        try{
            //Adicionando Turma
            String inServicos = inserir.concat("tblServicos("
                    + "codServico, nome, periodo, formaPagamento, valor, valorAVista, valorBoleto, valorCartaoDeCredito, valorCartaoDeDebito, periodDays)"
                    + "VALUES("
                    + "?,?,?,?,?,?,?,?,?, ?);");
            PreparedStatement statement = gerarStatement(inServicos);
            statement.setInt(1, servico.getCodBanco());
            statement.setString(2, servico.getNome());
            statement.setString(3, servico.getPeriodo());
            statement.setString(4, servico.getFormaPagamento());
            statement.setBigDecimal(5, servico.getValor());
            statement.setBigDecimal(6, servico.getValorVista());
            statement.setBigDecimal(7, servico.getValorBoleto());
            statement.setBigDecimal(8, servico.getValorPrazoCredito());
            statement.setBigDecimal(9, servico.getValorPrazoDebito());
            statement.setInt(10, servico.getPeriodDays());
            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            gerarLog(ex);
        }
        
    }
    
    
    
    //Atualizar dados
    public void atualizarDados(Servicos servico){
        try{
            //atualizando a tabela de turmas
            String inServicos = atualizar.concat("tblServicos "
                    + "SET nome = ?, periodo = ?, formaPagamento = ?, valor =?, valorAVista = ?, valorBoleto=?, valorCartaoDeCredito=?, valorCartaoDeDebito=?, periodDays = ? where codServico = ?");

            PreparedStatement statement = gerarStatement(inServicos);
            statement.setString(1, servico.getNome());
            statement.setString(2, servico.getPeriodo());
            statement.setString(3, servico.getFormaPagamento());
            statement.setBigDecimal(4, servico.getValor());
            statement.setBigDecimal(5, servico.getValorVista());
            statement.setBigDecimal(6, servico.getValorBoleto());
            statement.setBigDecimal(7, servico.getValorPrazoCredito());
            statement.setBigDecimal(8, servico.getValorPrazoDebito());
            statement.setInt(9, servico.getPeriodDays());
            statement.setInt(10, servico.getCodBanco());

            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            gerarLog(ex);
        }
        
    }
    
    //Remover Dados
    public void removerServico(int codServico){
        try{
            //Removendo Turmas
            String inServicos = remover.concat("tblServicos WHERE codServico = ?");

            PreparedStatement statement = gerarStatement(inServicos);
            statement.setInt(1, codServico);
            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            gerarLog(ex);
        }
        
        
    }
    
    public ArrayList <Servicos> selecionarTodosServicos(){
        String inServicos = selecionarTudo.concat("tblServicos");
        return pesquisarServicos(inServicos);
    }
    
    public ArrayList <Servicos> pesquisarServicos(String comando){
        try{
            ArrayList <Servicos> servicos = new ArrayList();
            PreparedStatement statement = gerarStatement(comando);
            statement.execute();
            ResultSet resultset = statement.getResultSet();
            boolean next = resultset.next();

            if(next==false){
                return null;
            }

            do{
            int codBanco = resultset.getInt("codServico");
            String nome = resultset.getString("nome");
            String periodo = resultset.getString("periodo");
            String formaPagamento = resultset.getString("formaPagamento");
            BigDecimal valor = new BigDecimal(resultset.getBigDecimal("valor").toString());
            BigDecimal valorAVista = new BigDecimal(resultset.getBigDecimal("valorAVista").toString());
            BigDecimal valorBoleto = new BigDecimal(resultset.getBigDecimal("valorBoleto").toString());
            BigDecimal valorAPrazoCredito = new BigDecimal(resultset.getBigDecimal("valorCartaoDeCredito").toString());
            BigDecimal valorAPrazoDebito = new BigDecimal (resultset.getBigDecimal("valorCartaoDeDebito").toString());
            int periodDays = resultset.getInt("periodDays");

            Servicos servico = new Servicos(codBanco, nome, periodo, formaPagamento, valor, valorAVista, valorBoleto, valorAPrazoCredito, valorAPrazoDebito, periodDays);

            servicos.add(servico);
             }while(resultset.next());

            statement.close();
            return servicos;
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
