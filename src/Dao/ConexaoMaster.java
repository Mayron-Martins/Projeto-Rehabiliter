/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Mayro
 */
public abstract class ConexaoMaster {
    private final String user = "sa";
    private final String pass = "164829";
    private final String url = "jdbc:sqlserver://localhost:1433; databaseName = master;";
    
    //Inicia a conexão com o banco de dados
    public Connection getConnection() throws SQLException{
        Connection conexao = DriverManager.getConnection(url, user, pass);
        return conexao;
    }
    
    public PreparedStatement gerarStatement(String comando) throws SQLException{
        PreparedStatement statement = this.getConnection().prepareStatement(comando);
        return statement;
    }
}
