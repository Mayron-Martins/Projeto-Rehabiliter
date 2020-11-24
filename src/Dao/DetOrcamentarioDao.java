/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Model.auxiliar.DetOrcamentario;
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
public class DetOrcamentarioDao extends Conexao{
    private final String inserir = "INSERT INTO ";
    private final String atualizar = "UPDATE ";
    private final String remover = "DELETE FROM ";
    private final String selecionarTudo = "SELECT * FROM ";
    private ConversaodeDataParaPadraoDesignado converterData;
    
    //Inserir dados na tabela DetOrcamentario
    public void inserirDados (DetOrcamentario orcamentario) throws SQLException{
        String inOrcamentario = inserir.concat("tblDetOrcamentario("
                + "codBanco, tipo, formaPagamento, valor, dataCadastro, chave)"
                + "VALUES("
                + "?,?,?,?,?,?);");
        PreparedStatement statement = gerarStatement(inOrcamentario);
        statement.setInt(1, orcamentario.getCodBanco());
        statement.setString(2, orcamentario.getTipo());
        statement.setString(3, orcamentario.getFormaPagamento());
        statement.setBigDecimal(4, new BigDecimal(orcamentario.getValor().toString()));
        statement.setDate(5, (Date) orcamentario.getDataCadastro());
        statement.setLong(6, orcamentario.getChave());
        statement.execute();
        statement.close();
    }
    
    //Atualizar dados
    public void atualizarDados(DetOrcamentario orcamentario) throws SQLException{
        String inOrcamentarios = atualizar.concat("tblDetOrcamentario "
                + "SET formaPagamento = ?, valor=? where tipo = ? AND chave = ?");
        
        PreparedStatement statement = gerarStatement(inOrcamentarios);
        statement.setString(1, orcamentario.getFormaPagamento());
        statement.setBigDecimal(2, new BigDecimal(orcamentario.getValor().toString()));
        statement.setString(3, orcamentario.getTipo());
        statement.setLong(4, orcamentario.getChave());
        
        statement.execute();
        statement.close();
    }
    
    //Remover Dados
    public void removerOrcamentario(String tipo, long chave) throws SQLException{
        String inOrcamentarios = remover.concat("tblDetOrcamentario WHERE tipo = ? AND chave = ?");
        PreparedStatement statement = gerarStatement(inOrcamentarios);
        statement.setString(1, tipo);
        statement.setLong(2, chave);
        statement.execute();
        statement.close();
    }
    
    public ArrayList <DetOrcamentario> selecionarTodosOrcamentarios() throws SQLException{
        String inOrcamentarios = selecionarTudo.concat("tblDetOrcamentario");
        return pesquisarOrcamentarios(inOrcamentarios);
    }
    
    public ArrayList <DetOrcamentario> pesquisarOrcamentarios(String comando) throws SQLException{
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
    }
}
