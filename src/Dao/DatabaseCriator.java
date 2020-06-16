/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Mayro
 */
public class DatabaseCriator {
    private final String url = "jdbc:sqlserver://localhost:1433";
    
    //Verifica se a base de dados já existe no sistema
    public boolean databaseConfirmed(String user, String pass) throws SQLException{
            Connection conexao = DriverManager.getConnection(url, user, pass);
            Statement statement = conexao.createStatement();
            return statement.execute("IF NOT EXISTS (SELECT * FROM sys.databases WHERE NAME = 'Rehabiliter_Database')");
    }
    
    //Cria a base de dados e as pastas subjacentes, assim como os grupos de arquivos e delimita um tamanho incial
    public void databaseCriation(String user, String pass){
        FileCriator criarPastas = new FileCriator();
        criarPastas.fileCriator();
        try{
            Connection conexao = DriverManager.getConnection(url, user, pass);
            Statement statement = conexao.createStatement();
            statement.execute("CREATE DATABASE Rehabiliter_Database ON PRIMARY ("
               + "NAME = 'Rehabiliter_Database', FILENAME = 'c:\\Rehabiliter\\Databases\\Rehabiliter_Database.mdf'"
               + ",SIZE = 10MB, FILEGROWTH = 20MB"
               + "), FILEGROUP [Alunos]("
               + "NAME = 'Alunos', FILENAME = 'c:\\Rehabiliter\\Databases\\Alunos.ndf'"
               + "), FILEGROUP [Funcionarios]("
               + "NAME = 'Funcionarios', FILENAME = 'c:\\Rehabiliter\\Databases\\Funcionarios.ndf'"
               + "), FILEGROUP [Produtos]("
               + "NAME = 'Produtos', FILENAME = 'c:\\Rehabiliter\\Databases\\Produtos.ndf'"
               + "), FILEGROUP [Orcamento]("
               + "NAME = 'Orcamento', FILENAME = 'c:\\Rehabiliter\\Databases\\Orcamento.ndf'"
               + "), FILEGROUP [Transacoes]("
               + "NAME = 'Transacoes', FILENAME = 'c:\\Rehabiliter\\Databases\\Transacoes.ndf'"
               + ") LOG ON("
               + "NAME = 'Teste_Log', FILENAME = 'c:\\Rehabiliter\\Databases\\Teste_Log.ldf'"
               + ");");
        } catch(java.sql.SQLException erro){
            mensagemdeErro();
                }
    }
    
    //Caso o programa não esteja intalado ou se houver algo de errado com o servidor a mensagem aparecerá
    public void mensagemdeErro(){
        System.out.println("Não foi possível criar a base"); //Adicionar mensagem personalizada na tela
    }
    
}
