/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Controller.auxiliar.LogsSystem;
import Model.auxiliar.Entradas;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Mayro
 */
public class EntradasDao extends Conexao{
    private final String inserir = "INSERT INTO ";
    private final String atualizar = "UPDATE ";
    private final String remover = "DELETE FROM ";
    private final String selecionarTudo = "SELECT * FROM ";
    private ConversaodeDataParaPadraoDesignado converterData;
    
    
    //Inserir dados na tabela Entradas
    public void inserirDados (Entradas entrada){
        try{
            //Adicionando Turma
            String inEntradas = inserir.concat("tblEntradas("
                    + "chavePlano, referencia, quantidade, formaPagamento, valorEntrada, dataCadastro, status)"
                    + "VALUES("
                    + "?,?,?,?,?,?,?);");
            PreparedStatement statement = gerarStatement(inEntradas);
            statement.setLong(1, entrada.getChavePlano());
            statement.setString(2, entrada.getReferencia());
            statement.setFloat(3, entrada.getQuantidade());
            statement.setString(4, entrada.getFormaPagamento());
            statement.setBigDecimal(5, new BigDecimal(entrada.getValorEntrada().toString()));
            statement.setDate(6, (Date) entrada.getDataCadastro());
            statement.setString(7, entrada.getStatus());
            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            gerarLog(ex);
        }
        
    }
    
    //Atualizar dados
    public void atualizarDados(Entradas entrada){
        try{
            //atualizando a tabela de turmas
            String inEntradas = atualizar.concat("tblEntradas "
                    + "SET referencia = ?, quantidade = ?, formaPagamento = ?, valorEntrada=? where codEntrada = ?");

            PreparedStatement statement = gerarStatement(inEntradas);
            statement.setString(1, entrada.getReferencia());
            statement.setFloat(2, entrada.getQuantidade());
            statement.setString(3, entrada.getFormaPagamento());
            statement.setBigDecimal(4, new BigDecimal(entrada.getValorEntrada().toString()));
            statement.setInt(5, entrada.getCodBanco());

            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            gerarLog(ex);
        }
    }
    
    public void atualizarStatus(int codEntrada, String status){
        try{
            //atualizando a tabela de turmas
            String inEntradas = atualizar.concat("tblEntradas "
                    + "SET status=? where codEntrada = ?");

            PreparedStatement statement = gerarStatement(inEntradas);
            statement.setString(1, status);
            statement.setInt(2, codEntrada);

            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            gerarLog(ex);
        }
    }
    
    //Remover Dados
    public void removerEntrada(int codEntrada){
        try{
            String inEntradas = remover.concat("tblEntradas WHERE codEntrada = ?");
        
            PreparedStatement statement = gerarStatement(inEntradas);
            statement.setInt(1, codEntrada);
            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            gerarLog(ex);
        }
        
    }
    
    public ArrayList <Entradas> selecionarTodasEntradas(){
        String inEntradas = selecionarTudo.concat("tblEntradas");
        return pesquisarEntradas(inEntradas);
    }
    
    public ArrayList <Entradas> pesquisarEntradas(String comando){
        try{
            ArrayList <Entradas> entradas = new ArrayList();
            PreparedStatement statement = gerarStatement(comando);
            statement.execute();
            ResultSet resultset = statement.getResultSet();
            boolean next = resultset.next();

            if(next==false){
                return null;
            }

            do{
            int codEntrada = resultset.getInt("codEntrada");
            long chavePlano = resultset.getLong("chavePlano");
            String referencia = resultset.getString("referencia");
            float quantidade = resultset.getFloat("quantidade");
            String formaPagamento = resultset.getString("formaPagamento");
            BigDecimal valorEntrada = new BigDecimal(resultset.getBigDecimal("valorEntrada").toString());
            Date dataCadastro = resultset.getDate("dataCadastro");
            String status = resultset.getString("status");

            Entradas entrada = new Entradas(codEntrada, chavePlano, referencia, quantidade, formaPagamento, valorEntrada, dataCadastro, status);

            entradas.add(entrada);
             }while(resultset.next());

            statement.close();
            return entradas;
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
