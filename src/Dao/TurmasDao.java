/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.auxiliar.Horarios;
import Model.auxiliar.Turmas;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Mayro
 */
public class TurmasDao extends Conexao{
    private final String inserir = "INSERT INTO ";
    
    private PreparedStatement gerarStatement(String comando) throws SQLException{
        PreparedStatement statement = this.getConnection().prepareStatement(comando);
        return statement;
    }
    
    //Inserir dados na tabela Turmas
    public void inserirDados (Turmas turma, ArrayList<Horarios> horario) throws SQLException{
        //Adicionando Turma
        String inTurmas = inserir.concat("tblTurmas("
                + "codTurma, nome, subgrupo, quantAlunos, quantLimiteDeAlunos, diasDaSemana)"
                + "VALUES("
                + "?,?,?,?,?,?);");
        PreparedStatement statement = gerarStatement(inTurmas);
        statement.setInt(1, turma.getCodBanco());
        statement.setString(2, turma.getNomeTurma());
        statement.setString(3, turma.getSubgrupo());
        statement.setInt(4, 0);
        statement.setInt(5, turma.getQuantidadeMaximaAlunos());
        statement.setString(6, turma.getDiasDaSemana());
        statement.execute();
        statement.close();
        
        //Adicionando Horário da Turma
        int quantidadeDias = horario.size();
        int diferenca, contador = quantidadeDias;
        
        String inHorarios = inserir.concat("tblHorarios("
                + "codHorario, diaDaSemana, horario, codCliente, codTurma)"
                + "VALUES("
                + "?,?,?,?,?);");
        statement = gerarStatement(inHorarios);
        while(quantidadeDias>0){
            diferenca = contador-quantidadeDias;
            statement.setInt(1, horario.get(diferenca).getCodBanco());
            statement.setString(2,horario.get(diferenca).getDiaDaSemana());
            statement.setTime(3, horario.get(diferenca).getHorario());
            statement.setInt(4, horario.get(diferenca).getCodCliente());
            statement.setInt(5, horario.get(diferenca).getCodTurma());
            statement.execute(); 
            quantidadeDias--;
        }
        statement.close(); 
    }
}
