/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Mayro
 */
public class Conexao {
    private final String user = "sa";
    private final String pass = "164829";
    private final String url = "jdbc:sqlserver://localhost:1433; databaseName = Rehabiliter_Database;";
    
    
    //Testa a conexão com o banco de dados, se ele não existir o cria, se der erro então há problema com
    //o servidor, de modo a ter alguma informação errada ou a sua inexistência.
    //NOTA: Verificar se o conversor mssql-jbdc-8.2.2.jre8.jar está adicionado no computador na pasta lib direto no projeto
    public void testConnection(){
         DatabaseCriator existenciaDeBase = new DatabaseCriator();
        try{
            if(existenciaDeBase.databaseConfirmed(user, pass)){
                existenciaDeBase.databaseCriation(user, pass);
            }
             Connection conexao = DriverManager.getConnection(url, user, pass);
             System.out.println("Connection Successful");
        } catch (java.sql.SQLException erro){
             System.out.println("Connection Failed");
        }
    }
    
    
    
    //Inicia a conexão com o banco de dados
    public Connection getConnection() throws SQLException{
        Connection conexao = DriverManager.getConnection(url, user, pass);
        return conexao;
    }
    
}