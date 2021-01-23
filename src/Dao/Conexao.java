/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Controller.auxiliar.Utilitarios;
import Scripts.ScriptsGenerate;
import Scripts.Sqlcmd;
import View.inicio;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Mayro
 */
public abstract class Conexao {
    private final String user = "sa";
    private final String pass = "164829";
    private final String url = "jdbc:sqlserver://localhost:1433; databaseName = Rehabiliter_Database;";
    private final FileCriator criarPastas = new FileCriator();
    private final ScriptsGenerate scripts = new ScriptsGenerate();
    private final Sqlcmd sqlcmd = new Sqlcmd();
    private final Utilitarios utilitarios = new Utilitarios();

    
    
    
    
    //Testa a conexão com o banco de dados, se ele não existir o cria, se der erro então há problema com
    //o servidor, de modo a ter alguma informação errada ou a sua inexistência.
    //NOTA: Verificar se o conversor mssql-jbdc-8.2.2.jre8.jar está adicionado no computador na pasta lib direto no projeto
    public void testConnection(inicio telaDeInicio) throws SQLException, SQLException{
         DatabaseCriator existenciaDeBase = new DatabaseCriator(telaDeInicio);
         TableCriator criarTabelas = new TableCriator(telaDeInicio);
        try{
            criarPastas.fileCriator();
            scripts.gerarScripts();
            sqlcmd.gerarBats();
            sqlcmd.gerarBatsOnline();
            utilitarios.inicializacao();
            if(existenciaDeBase.databaseConfirmed(user, pass) == false){
                existenciaDeBase.databaseCriation(user, pass);
                criarTabelas.createTables();
            }
            if(utilitarios.testeMaster()){
                sqlcmd.executeMaster();
            }
        } catch (java.sql.SQLException erro){
            JOptionPane.showMessageDialog(null, "Falha na Conexão com o Banco de Dados");
            System.exit(1);
        }
    }
    
    
    
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
