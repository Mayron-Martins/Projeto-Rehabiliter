/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Model.auxiliar.ItemVendido;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Mayro
 */
public class ItensVendidosDao extends Conexao{
    private final String inserir = "INSERT INTO ";
    private final String remover = "DELETE FROM ";
    private final String selecionarTudo = "SELECT * FROM ";
    private ConversaodeDataParaPadraoDesignado converterData;
    
    public void inserirDadosEmItensVendidos(PreparedStatement statement, ArrayList<ItemVendido> item) throws SQLException{
        int quantidadeItens = item.size();
        
        String inItensVendidos = inserir.concat("tblItensVendidos("
                + "chaveVenda, codProduto, quantidade, valor, subtotal)"
                + "VALUES("
                + "?,?,?,?,?);");
        statement = gerarStatement(inItensVendidos);
        for(int linhas=0; linhas<quantidadeItens; linhas++){
            statement.setLong(1, item.get(linhas).getChaveVenda());
            statement.setInt(2, item.get(linhas).getCodProduto());
            statement.setFloat(3, item.get(linhas).getQuantidade());
            statement.setBigDecimal(4, new BigDecimal(item.get(linhas).getValor().toString()));
            statement.setBigDecimal(5,  new BigDecimal(item.get(linhas).getSubtotal().toString()));
            statement.execute(); 
        }
        statement.close(); 
    }
    
    //Selecionar Todos os Itens Vendidos
    public ArrayList <ItemVendido> selecionarTodosItensVendidos() throws SQLException{
        String inItensVendidos = selecionarTudo.concat("tblItensVendidos");
        return pesquisarItensVendidos(inItensVendidos);
    }
    
    //Procurar por Itens Vendidos no Banco
    public ArrayList <ItemVendido> pesquisarItensVendidos(String comando) throws SQLException{
     ArrayList <ItemVendido> item = new ArrayList();
     PreparedStatement statement = gerarStatement(comando);
     statement.execute();
     ResultSet resultset = statement.getResultSet();
     boolean next = resultset.next();
     
     if(next==false){
         return null;
     }

    do{
    int chaveVenda = resultset.getInt("chaveVenda");
    int codProduto = resultset.getInt("codProduto");
    float quantidade = resultset.getFloat("quantidade");
    BigDecimal valor = new BigDecimal(resultset.getBigDecimal("valor").toString());
    BigDecimal subtotal = new BigDecimal(resultset.getBigDecimal("subtotal").toString());


    ItemVendido itemBanco = new ItemVendido(chaveVenda, codProduto, quantidade, valor, subtotal);

    item.add(itemBanco);
     }while(resultset.next());
    statement.close();
    return item;
    }
    
}
