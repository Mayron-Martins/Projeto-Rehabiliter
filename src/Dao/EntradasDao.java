/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
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
    public void inserirDados (Entradas entrada) throws SQLException{
        //Adicionando Turma
        String inEntradas = inserir.concat("tblEntradas("
                + "codEntrada, referencia, quantidade, formaPagamento, valorEntrada, dataCadastro)"
                + "VALUES("
                + "?,?,?,?,?,?);");
        PreparedStatement statement = gerarStatement(inEntradas);
        statement.setInt(1, entrada.getCodBanco());
        statement.setString(2, entrada.getReferencia());
        statement.setFloat(3, entrada.getQuantidade());
        statement.setString(4, entrada.getFormaPagamento());
        statement.setBigDecimal(5, new BigDecimal(entrada.getValorEntrada().toString()));
        statement.setDate(6, (Date) entrada.getDataCadastro());
        statement.execute();
        statement.close();
    }
    
    //Atualizar dados
    public void atualizarDados(Entradas entrada) throws SQLException{
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
    }
    
    //Remover Dados
    public void removerEntrada(int codEntrada) throws SQLException{
        String inEntradas = remover.concat("tblEntradas WHERE codEntrada = ?");
        
        PreparedStatement statement = gerarStatement(inEntradas);
        statement.setInt(1, codEntrada);
        statement.execute();
        statement.close();
    }
    
    public ArrayList <Entradas> selecionarTodasEntradas() throws SQLException{
        String inEntradas = selecionarTudo.concat("tblEntradas");
        return pesquisarEntradas(inEntradas);
    }
    
    public ArrayList <Entradas> pesquisarEntradas(String comando) throws SQLException{
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
    String referencia = resultset.getString("referencia");
    float quantidade = resultset.getFloat("quantidade");
    String formaPagamento = resultset.getString("formaPagamento");
    BigDecimal valorEntrada = new BigDecimal(resultset.getBigDecimal("valorEntrada").toString());
    Date dataCadastro = resultset.getDate("dataCadastro");

    Entradas entrada = new Entradas(codEntrada, referencia, quantidade, formaPagamento, valorEntrada, dataCadastro);

    entradas.add(entrada);
     }while(resultset.next());
    
    statement.close();
    return entradas;
    }
}
