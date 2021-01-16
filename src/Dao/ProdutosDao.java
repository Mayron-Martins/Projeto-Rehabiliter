/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Controller.auxiliar.LogsSystem;
import Model.Produtos;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mayro
 */
public class ProdutosDao extends Conexao{
    private final String inserir = "INSERT INTO ";
    private final String atualizar = "UPDATE ";
    private final String remover = "DELETE FROM ";
    private final String selecionarTudo = "SELECT * FROM ";
    private ConversaodeDataParaPadraoDesignado converterData;
    
    
    //Inserir dados na tabela Produtos
    public void inserirDados (Produtos produto){
        try{
            //Adicionando Turma
            String inProdutos = inserir.concat("tblProdutos("
                    + "codProduto, nome, tipo, unMedida, quantidade, descricao, valorDeCompra, dataDeCompra, valorDeVenda)"
                    + "VALUES("
                    + "?,?,?,?,?,?,?,?,?);");
            PreparedStatement statement = gerarStatement(inProdutos);
            statement.setInt(1, produto.getCodBanco());
            statement.setString(2, produto.getNomeProduto());
            statement.setString(3, produto.getTipo());
            statement.setString(4, produto.getUnMedida());
            statement.setFloat(5, produto.getQuantidade());
            statement.setString(6, produto.getDescricao());
            statement.setBigDecimal(7, new BigDecimal(produto.getValorDeCompra().toString()));
            statement.setDate(8, (Date) produto.getDataDeCompra());
            statement.setBigDecimal(9, new BigDecimal(produto.getValorDeVenda().toString()));
            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            gerarLog(ex);
        }
        
    }
    
    
    
    //Atualizar dados
    public void atualizarDados(Produtos produto){
        try{
            //atualizando a tabela de Produtos
            String inSProdutos = atualizar.concat("tblProdutos "
                    + "SET nome = ?, quantidade = ?, descricao = ?, valorDeVenda = ? where codProduto = ?");

            PreparedStatement statement = gerarStatement(inSProdutos);
            statement.setString(1, produto.getNomeProduto());
            statement.setFloat(2, produto.getQuantidade());
            statement.setString(3, produto.getDescricao());
            statement.setBigDecimal(4, new BigDecimal(produto.getValorDeVenda().toString()));
            statement.setInt(5, produto.getCodBanco());

            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            gerarLog(ex);
        }
        
    }
    
    public void atualizarEstoque(int codProduto, float estoque){
        try{
            //atualizando a tabela de Produtos
            String inProdutos = atualizar.concat("tblProdutos "
                    + "SET quantidade = ? where codProduto = ?");

            PreparedStatement statement = gerarStatement(inProdutos);
            statement.setFloat(1, estoque);
            statement.setInt(2, codProduto);

            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            gerarLog(ex);
        }
        
    }
    
    //Remover Dados
    public void removerProduto(int codProduto){
        try{
            //Removendo Turmas
            String inProdutos = remover.concat("tblProdutos WHERE codProduto = ?");

            PreparedStatement statement = gerarStatement(inProdutos);
            statement.setInt(1, codProduto);
            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            gerarLog(ex);
        }
        
        
    }
    
    public ArrayList <Produtos> selecionarTodosProdutos(){
        String inProdutos = selecionarTudo.concat("tblProdutos");
        return pesquisarProdutos(inProdutos);
    }
    
    public ArrayList <Produtos> pesquisarProdutos(String comando){
        try{
            ArrayList <Produtos> produtos = new ArrayList();
            PreparedStatement statement = gerarStatement(comando);
            statement.execute();
            ResultSet resultset = statement.getResultSet();
            boolean next = resultset.next();

            if(next==false){
                return null;
            }

            do{
            int codBanco = resultset.getInt("codProduto");
            String nome = resultset.getString("nome");
            String tipo = resultset.getString("tipo");
            String unMedida = resultset.getString("unMedida");
            float quantidade = resultset.getFloat("quantidade");
            String descricao = resultset.getString("descricao");
            BigDecimal valorDeCompra = new BigDecimal(resultset.getBigDecimal("valorDeCompra").toString());
            String dataCompra = null;
                    //converterData.parseDate(resultset.getDate("dataDeCompra"));
            BigDecimal valorDeVenda = new BigDecimal(resultset.getBigDecimal("valorDeVenda").toString());

            Produtos produto = new Produtos(codBanco, nome, tipo, unMedida, quantidade, descricao, valorDeCompra, dataCompra, valorDeVenda, 0);

            produtos.add(produto);
             }while(resultset.next());

            statement.close();
            return produtos;
        } catch (SQLException ex) {
            gerarLog(ex);
            return null;
        }
    
    }
 
    public ArrayList<Produtos> pesquisarPorNome(String nomeProduto){
               ArrayList <Produtos> produtos = selecionarTodosProdutos();
               ArrayList<Produtos> produtosBuscados = new ArrayList<>();
               for(int repeticoes = 0; repeticoes<produtos.size(); repeticoes++){
                   if(produtos.get(repeticoes).getNomeProduto().toLowerCase().contains(nomeProduto.toLowerCase())== true){
                       produtosBuscados.add(produtos.get(repeticoes));
                   }
               }
               if(produtosBuscados.size()<1){
                   return null;
               }
               return produtosBuscados;
    }

    public ArrayList<Produtos> pesquisarPorID(String codBuscado){
               ArrayList <Produtos> produtos = selecionarTodosProdutos();
               ArrayList<Produtos> produtosBuscados = new ArrayList<>();
               for(int repeticoes = 0; repeticoes<produtos.size(); repeticoes++){
                   String codBanco = String.valueOf(produtos.get(repeticoes).getCodBanco());
                   if(codBanco.contains(codBuscado)== true){
                       produtosBuscados.add(produtos.get(repeticoes));
                   }
               }
               if(produtosBuscados.size()<1){
                   return null;
               }
               return produtosBuscados;
    }

    //Gerar arquivo com o log de erro, caso haja
    private void gerarLog(Throwable erro){
        LogsSystem gerarLog = new LogsSystem();
        gerarLog.gravarErro(erro);
        gerarLog.close();
    }
}
