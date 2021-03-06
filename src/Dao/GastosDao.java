/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Controller.auxiliar.LogsSystem;
import Model.auxiliar.DetOrcamentario;
import Model.auxiliar.Gastos;
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
public class GastosDao extends Conexao{
    private final String inserir = "INSERT INTO ";
    private final String atualizar = "UPDATE ";
    private final String remover = "DELETE FROM ";
    private final String selecionarTudo = "SELECT * FROM ";
    private ConversaodeDataParaPadraoDesignado converterData;
    
    
    //Inserir dados na tabela Gastos
    public void inserirDados (Gastos gasto){
        try{
            //Adicionando Turma
            String inGastos = inserir.concat("tblGastos("
                    + "motivo, quantidade, formaPagamento, valorGasto, dataGasto, status, chaveTransacao)"
                    + "VALUES("
                    + "?,?,?,?,?,?,?);");
            PreparedStatement statement = gerarStatement(inGastos);
            statement.setString(1, gasto.getMotivo());
            statement.setFloat(2, gasto.getQuantidade());
            statement.setString(3, gasto.getFormaPagamento());
            statement.setBigDecimal(4, new BigDecimal(gasto.getValorGasto().toString()));
            statement.setDate(5, (Date) gasto.getDataCadastro());
            statement.setString(6, gasto.getStatus());
            statement.setLong(7, gasto.getChaveTransacao());
            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            gerarLog(ex);
        }
        
    }
    
    //Atualizar dados
    public void atualizarDados(Gastos gasto){
        try{
            //atualizando a tabela de turmas
            String inGastos = atualizar.concat("tblGastos "
                    + "SET motivo = ?, quantidade = ?, formaPagamento = ?, valorGasto=?, dataGasto=? where codGasto = ?");

            PreparedStatement statement = gerarStatement(inGastos);
            statement.setString(1, gasto.getMotivo());
            statement.setFloat(2, gasto.getQuantidade());
            statement.setString(3, gasto.getFormaPagamento());
            statement.setBigDecimal(4, new BigDecimal(gasto.getValorGasto().toString()));
            statement.setDate(5, (Date) gasto.getDataCadastro());
            statement.setInt(6, gasto.getCodBanco());

            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            gerarLog(ex);
        }
    }
    
    public void atualizarStatus(int codBanco, String status){
        try{
            //atualizando a tabela de turmas
            String inGastos = atualizar.concat("tblGastos "
                    + "SET status=? where codGasto = ?");

            PreparedStatement statement = gerarStatement(inGastos);
            statement.setString(1, status);
            statement.setInt(2, codBanco);

            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            gerarLog(ex);
        }
    }
    
    public void atualizarMotivo(int codBanco, String motivo){
        try{
            //atualizando a tabela de turmas
            String inGastos = atualizar.concat("tblGastos "
                    + "SET motivo = ? where codGasto = ?");

            PreparedStatement statement = gerarStatement(inGastos);
            statement.setString(1, motivo);
            statement.setInt(2, codBanco);

            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            gerarLog(ex);
        }
    }
    
    //Remover Dados
    public void removerGastos(int codGasto){
        try{
            String inGastos = remover.concat("tblGastos WHERE codGasto = ?");
        
            PreparedStatement statement = gerarStatement(inGastos);
            statement.setInt(1, codGasto);
            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            gerarLog(ex);
        }
    }
    
    public ArrayList <Gastos> selecionarTodasGastos(){
        String inGastos = selecionarTudo.concat("tblGastos");
        return pesquisarGastos(inGastos);
    }
    
    public ArrayList <Gastos> pesquisarGastos(String comando){
        try{
            ArrayList <Gastos> gastos = new ArrayList();
            PreparedStatement statement = gerarStatement(comando);
            statement.execute();
            ResultSet resultset = statement.getResultSet();
            boolean next = resultset.next();

            if(next==false){
                return null;
            }

            do{
            int codGasto = resultset.getInt("codGasto");
            String motivo = resultset.getString("motivo");
            float quantidade = resultset.getFloat("quantidade");
            String formaPagamento = resultset.getString("formaPagamento");
            BigDecimal valorGasto = new BigDecimal(resultset.getBigDecimal("valorGasto").toString());
            Date dataCadastro = resultset.getDate("dataGasto");
            String status = resultset.getString("status");
            long chaveTransacao = resultset.getLong("chaveTransacao");

            Gastos gasto = new Gastos(codGasto, motivo, quantidade, formaPagamento, valorGasto, dataCadastro, status, chaveTransacao);

            gastos.add(gasto);
             }while(resultset.next());

            statement.close();
            return gastos;
        } catch (SQLException ex) {
            gerarLog(ex);
            return null;
        }
        
    }
    
    public ArrayList<Gastos> pesquisarPorNome(String motivo){
       ArrayList <Gastos> gastos = selecionarTodasGastos();
       ArrayList<Gastos> gastosBuscados = new ArrayList<>();
       for(Gastos gasto : gastos){
           if(gasto.getMotivo().toLowerCase().contains(motivo.toLowerCase())== true){
               gastosBuscados.add(gasto);
           }
       }
       if(gastosBuscados.size()<1){
           return null;
       }
       return gastosBuscados;
    }
    
    //Gerar arquivo com o log de erro, caso haja
    private void gerarLog(Throwable erro){
        LogsSystem gerarLog = new LogsSystem();
        gerarLog.gravarErro(erro);
        gerarLog.close();
    }
}
