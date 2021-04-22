/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Controller.auxiliar.LogsSystem;
import Model.auxiliar.DetOrcamentario;
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
public class DetOrcamentarioDao extends Conexao{
    private final String inserir = "INSERT INTO ";
    private final String atualizar = "UPDATE ";
    private final String remover = "DELETE FROM ";
    private final String selecionarTudo = "SELECT * FROM ";
    private ConversaodeDataParaPadraoDesignado converterData;
    
    //Inserir dados na tabela DetOrcamentario
    public void inserirDados (DetOrcamentario orcamentario){
        try{
            String inOrcamentario = inserir.concat("tblDetOrcamentario("
                    + "tipo, formaPagamento, valor, dataCadastro, chave)"
                    + "VALUES("
                    + "?,?,?,?,?);");
            PreparedStatement statement = gerarStatement(inOrcamentario);
            statement.setString(1, orcamentario.getTipo());
            statement.setString(2, orcamentario.getFormaPagamento());
            statement.setBigDecimal(3, new BigDecimal(orcamentario.getValor().toString()));
            statement.setDate(4, (Date) orcamentario.getDataCadastro());
            statement.setLong(5, orcamentario.getChave());
            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            gerarLog(ex);
        }
        
    }
    
    //Atualizar dados
    public void atualizarDados(DetOrcamentario orcamentario){
        try{
            String inOrcamentarios = atualizar.concat("tblDetOrcamentario "
                    + "SET formaPagamento = ?, valor=?, dataCadastro=? where tipo = ? AND chave = ?");

            PreparedStatement statement = gerarStatement(inOrcamentarios);
            statement.setString(1, orcamentario.getFormaPagamento());
            statement.setBigDecimal(2, new BigDecimal(orcamentario.getValor().toString()));
            statement.setDate(3, (Date) orcamentario.getDataCadastro());
            statement.setString(4, orcamentario.getTipo());
            statement.setLong(5, orcamentario.getChave());

            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            gerarLog(ex);
        }
        
    }
    
    //Remover Dados
    public void removerOrcamentario(String tipo, long chave){
        try{
            String inOrcamentarios = remover.concat("tblDetOrcamentario WHERE tipo = ? AND chave = ?");
            PreparedStatement statement = gerarStatement(inOrcamentarios);
            statement.setString(1, tipo);
            statement.setLong(2, chave);
            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            gerarLog(ex);
        }
        
    }
    
    public ArrayList <DetOrcamentario> selecionarTodosOrcamentarios(){
        String inOrcamentarios = selecionarTudo.concat("tblDetOrcamentario");
        return pesquisarOrcamentarios(inOrcamentarios);
    }
    
    public ArrayList <DetOrcamentario> pesquisarOrcamentarios(String comando){
        try{
            ArrayList <DetOrcamentario> orcamentarios = new ArrayList();
            PreparedStatement statement = gerarStatement(comando);
            statement.execute();
            ResultSet resultset = statement.getResultSet();
            boolean next = resultset.next();

            if(next==false){
                return null;
            }

            do{
            int codBanco = resultset.getInt("codBanco");
            String tipo = resultset.getString("tipo");
            String formaPagamento = resultset.getString("formaPagamento");
            BigDecimal valor = new BigDecimal(resultset.getBigDecimal("valor").toString());
            Date dataCadastro = resultset.getDate("dataCadastro");
            long chave = resultset.getLong("chave");

            DetOrcamentario orcamentario = new DetOrcamentario(codBanco, tipo, formaPagamento, valor, dataCadastro, chave);

            orcamentarios.add(orcamentario);
             }while(resultset.next());

            statement.close();
            return orcamentarios;
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
