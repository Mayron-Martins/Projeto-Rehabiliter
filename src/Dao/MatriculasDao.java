/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Controller.auxiliar.LogsSystem;
import Model.auxiliar.Matriculas;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public void inserirDados (Matriculas matricula){
        try{
            //Adicionando Matrícula
            String inMatriculas = inserir.concat("tblMatriculas("
                    + "codTurma, codAluno, anoMatricula, matricula)"
                    + "VALUES("
                    + "?,(SELECT IDENT_CURRENT('tblAlunos')),?,?);");
            PreparedStatement statement = gerarStatement(inMatriculas);
            statement.setInt(1, matricula.getCodTurma());
            statement.setInt(2, matricula.getAnoMatricula());
            statement.setString(3, matricula.getMatricula());
            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            gerarLog(ex);
        }
        
    }
    
    public void atualizarDados(Matriculas matricula){
        try{
            String inEnderecos = atualizar.concat("tblMatriculas "
                    + "SET codTurma = ?, matricula = ? where codMatricula = ?");

            PreparedStatement statement = gerarStatement(inEnderecos);
            statement.setInt(1, matricula.getCodTurma());
            statement.setString(2, matricula.getMatricula());
            statement.setInt(3, matricula.getCodBanco());

            statement.execute();
            statement.close(); 
        } catch (SQLException ex) {
            gerarLog(ex);
        }
            
    }
    
    //Remover Dados
    public void removerMatricula(int codAluno){
        try{
            //Removendo Matriculas
            String inMatriculas = remover.concat("tblMatriculas WHERE codAluno = ?");

            PreparedStatement statement = gerarStatement(inMatriculas);
            statement.setInt(1, codAluno);
            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            gerarLog(ex);
        }
        
    }
    
    public ArrayList <Matriculas> selecionarTodasMatriculas(){
        String inMatriculas = selecionarTudo.concat("tblMatriculas");
        return pesquisarMatriculas(inMatriculas);
    }
    
    public ArrayList <Matriculas> pesquisarMatriculas(String comando){
        try{
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
        } catch (SQLException ex) {
            gerarLog(ex);
            return null;
        }
        
    }
    
    //Gerar arquivo com o log de erro, caso haja
    private void gerarLog(Throwable erro){
        LogsSystem gerarLog = new LogsSystem();
        gerarLog.gravarErro(erro);
        gerarLog.close();
    }
}
