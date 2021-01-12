/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.auxiliar.ReposicaoAulas;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Mayro
 */
public class ReposicaoDao extends Conexao{
    private final String inserir = "INSERT INTO ";
    private final String atualizar = "UPDATE ";
    private final String remover = "DELETE FROM ";
    private final String selecionarTudo = "SELECT * FROM ";
    
    //Inserir dados na tabela Entradas
    public void inserirDados (ReposicaoAulas reposicao) throws SQLException{
        //Adicionando Reposicao
        String inReposicao = inserir.concat("tblReposicaoAulas("
                + "codBanco, data, codTurma, codAluno, situacao)"
                + "VALUES("
                + "?,?,?,?,?);");
        PreparedStatement statement = gerarStatement(inReposicao);
        statement.setInt(1, reposicao.getCodBanco());
        statement.setDate(2, (Date) reposicao.getData());
        statement.setInt(3, reposicao.getCodTurma());
        statement.setInt(4, reposicao.getCodAluno());
        statement.setString(5, reposicao.getSituacao());
        statement.execute();
        statement.close();
    }
    
    //Atualizar dados
    public void atualizarDados(ReposicaoAulas reposicao) throws SQLException{
        //atualizando a tabela de turmas
        String inReposicao = atualizar.concat("tblReposicaoAulas "
                + "SET situacao = ? WHERE codBanco = ?");
        
        PreparedStatement statement = gerarStatement(inReposicao);
        statement.setString(1, reposicao.getSituacao());
        statement.setInt(2, reposicao.getCodBanco());
        
        statement.execute();
        statement.close();
    }
    
    //Remover Dados
    public void removerReposicao(int codReposicao) throws SQLException{
        String inReposicao = remover.concat("tblReposicaoAulas WHERE codBanco = ?");
        
        PreparedStatement statement = gerarStatement(inReposicao);
        statement.setInt(1, codReposicao);
        statement.execute();
        statement.close();
    }
    
    public ArrayList <ReposicaoAulas> selecionarTodasReposicoes() throws SQLException{
        String inReposicao = selecionarTudo.concat("tblReposicaoAulas");
        return pesquisarReposicao(inReposicao);
    }
    
    public ArrayList <ReposicaoAulas> pesquisarReposicao(String comando) throws SQLException{
     ArrayList <ReposicaoAulas> reposicoes = new ArrayList();
     PreparedStatement statement = gerarStatement(comando);
     statement.execute();
     ResultSet resultset = statement.getResultSet();
     boolean next = resultset.next();
     
     if(next==false){
         return null;
     }
     
    do{
    int codBanco = resultset.getInt("codBanco");
    Date data = resultset.getDate("data");
    int codTurma = resultset.getInt("codTurma");
    int codAluno = resultset.getInt("codAluno");
    String situacao = resultset.getString("situacao");

    ReposicaoAulas reposicao = new ReposicaoAulas(codBanco, data, codTurma, codAluno, situacao);

    reposicoes.add(reposicao);
     }while(resultset.next());
    
    statement.close();
    return reposicoes;
    }
}
