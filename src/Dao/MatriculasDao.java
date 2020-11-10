/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Model.auxiliar.Matriculas;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Mayro
 */
public class MatriculasDao extends Conexao{
    private final String inserir = "INSERT INTO ";
    private final String atualizar = "UPDATE ";
    private final String remover = "DELETE FROM ";
    private final String selecionarTudo = "SELECT * FROM ";
    private ConversaodeDataParaPadraoDesignado converterData;
    
    
    //Inserir dados na tabela Matriculas
    public void inserirDados (Matriculas matricula) throws SQLException{
        //Adicionando Matrícula
        String inMatriculas = inserir.concat("tblMatriculas("
                + "codMatricula, codTurma, codAluno, anoMatricula, matricula)"
                + "VALUES("
                + "?,?,?,?,?);");
        PreparedStatement statement = gerarStatement(inMatriculas);
        statement.setInt(1, matricula.getCodBanco());
        statement.setInt(2, matricula.getCodTurma());
        statement.setInt(3, matricula.getCodAluno());
        statement.setInt(4, matricula.getAnoMatricula());
        statement.setString(5, matricula.getMatricula());
        statement.execute();
        statement.close();
    }
    
    public void atualizarDados(Matriculas matricula) throws SQLException{
        String inEnderecos = atualizar.concat("tblMatriculas "
                + "SET codTurma = ?, matricula = ? where codMatricula = ?");
        
        PreparedStatement statement = gerarStatement(inEnderecos);
        statement.setInt(1, matricula.getCodTurma());
        statement.setString(2, matricula.getMatricula());
        statement.setInt(3, matricula.getCodBanco());
        
        statement.execute();
        statement.close();     
    }
    
    //Remover Dados
    public void removerMatricula(int codMatricula) throws SQLException{
        //Removendo Matriculas
        String inMatriculas = remover.concat("tblMatriculas WHERE codMatricula = ?");
        
        PreparedStatement statement = gerarStatement(inMatriculas);
        statement.setInt(1, codMatricula);
        statement.execute();
        statement.close();
    }
    
    public ArrayList <Matriculas> selecionarTodasMatriculas() throws SQLException{
        String inMatriculas = selecionarTudo.concat("tblMatriculas");
        return pesquisarMatriculas(inMatriculas);
    }
    
    public ArrayList <Matriculas> pesquisarMatriculas(String comando) throws SQLException{
     ArrayList <Matriculas> matriculas = new ArrayList();
     PreparedStatement statement = gerarStatement(comando);
     statement.execute();
     ResultSet resultset = statement.getResultSet();
     boolean next = resultset.next();
     
     if(next==false){
         return null;
     }
     
    do{
    int codBanco = resultset.getInt("codMatricula");
    int codTurma = resultset.getInt("codTurma");
    int codAluno = resultset.getInt("codAluno");
    int anoMatricula = resultset.getInt("anoMatricula");
    String matriculaBanco = resultset.getString("matricula");

    Matriculas matricula = new Matriculas(codBanco, codTurma, codAluno, anoMatricula, matriculaBanco);

    matriculas.add(matricula);
     }while(resultset.next());
    
    statement.close();
    return matriculas;
    }
}
