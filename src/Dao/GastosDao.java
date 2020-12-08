/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Model.auxiliar.DetOrcamentario;
import Model.auxiliar.Gastos;
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
public class GastosDao extends Conexao{
    private final String inserir = "INSERT INTO ";
    private final String atualizar = "UPDATE ";
    private final String remover = "DELETE FROM ";
    private final String selecionarTudo = "SELECT * FROM ";
    private ConversaodeDataParaPadraoDesignado converterData;
    
    
    //Inserir dados na tabela Gastos
    public void inserirDados (Gastos gasto) throws SQLException{
        //Adicionando Turma
        String inGastos = inserir.concat("tblGastos("
                + "codGasto, motivo, quantidade, formaPagamento, valorGasto, dataGasto, chaveTransacao)"
                + "VALUES("
                + "?,?,?,?,?,?,?);");
        PreparedStatement statement = gerarStatement(inGastos);
        statement.setInt(1, gasto.getCodBanco());
        statement.setString(2, gasto.getMotivo());
        statement.setFloat(3, gasto.getQuantidade());
        statement.setString(4, gasto.getFormaPagamento());
        statement.setBigDecimal(5, new BigDecimal(gasto.getValorGasto().toString()));
        statement.setDate(6, (Date) gasto.getDataCadastro());
        statement.setLong(7, gasto.getChaveTransacao());
        statement.execute();
        statement.close();
    }
    
    //Atualizar dados
    public void atualizarDados(Gastos gasto) throws SQLException{
        //atualizando a tabela de turmas
        String inGastos = atualizar.concat("tblGastos "
                + "SET motivo = ?, quantidade = ?, formaPagamento = ?, valorGasto=? where codGasto = ?");
        
        PreparedStatement statement = gerarStatement(inGastos);
        statement.setString(1, gasto.getMotivo());
        statement.setFloat(2, gasto.getQuantidade());
        statement.setString(3, gasto.getFormaPagamento());
        statement.setBigDecimal(4, new BigDecimal(gasto.getValorGasto().toString()));
        statement.setInt(5, gasto.getCodBanco());
        
        statement.execute();
        statement.close();
        
    }
    
    //Remover Dados
    public void removerGastos(int codGasto) throws SQLException{
        String inGastos = remover.concat("tblGastos WHERE codGasto = ?");
        
        PreparedStatement statement = gerarStatement(inGastos);
        statement.setInt(1, codGasto);
        statement.execute();
        statement.close();
    }
    
    public ArrayList <Gastos> selecionarTodasGastos() throws SQLException{
        String inGastos = selecionarTudo.concat("tblGastos");
        return pesquisarGastos(inGastos);
    }
    
    public ArrayList <Gastos> pesquisarGastos(String comando) throws SQLException{
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
    long chaveTransacao = resultset.getLong("chaveTransacao");

    Gastos gasto = new Gastos(codGasto, motivo, quantidade, formaPagamento, valorGasto, dataCadastro, chaveTransacao);

    gastos.add(gasto);
     }while(resultset.next());
    
    statement.close();
    return gastos;
    }
}