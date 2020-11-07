/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Model.auxiliar.Horarios;
import Model.auxiliar.Turmas;
import View.Servicos;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Mayro
 */
public class ServicosDao extends Conexao{
    private final String inserir = "INSERT INTO ";
    private final String atualizar = "UPDATE ";
    private final String remover = "DELETE FROM ";
    private final String selecionarTudo = "SELECT * FROM ";
    private final HorariosDao horariosDao = new HorariosDao();
    private ConversaodeDataParaPadraoDesignado converterData;
    
    
    //Inserir dados na tabela Servicos
    public void inserirDados (Servicos servico) throws SQLException{
        //Adicionando Turma
        String inTurmas = inserir.concat("tblServicos("
                + "codServico, nome, periodo, valorAVista, valorBoleto, valorCartao)"
                + "VALUES("
                + "?,?,?,?,?,?,?);");
        PreparedStatement statement = gerarStatement(inTurmas);
        statement.setInt(1, servico.get);
        statement.setString(2, turma.getNomeTurma());
        statement.setString(3, turma.getSubgrupo());
        statement.setInt(4, 0);
        statement.setInt(5, turma.getQuantidadeMaximaAlunos());
        statement.setString(6, turma.getDiasDaSemana());
        statement.execute();
        statement.close();
    }
    
    
    
    //Atualizar dados
    public void atualizarDados(Turmas turmas, ArrayList <Horarios> horario) throws SQLException{
        //atualizando a tabela de turmas
        String inTurmas = atualizar.concat("tblTurmas "
                + "SET nome = ?, subgrupo = ?, quantLimiteDeAlunos = ?, diasDaSemana=?, horario=? where codTurma = ?");
        
        PreparedStatement statement = gerarStatement(inTurmas);
        statement.setString(1, turmas.getNomeTurma());
        statement.setString(2, turmas.getSubgrupo());
        statement.setInt(3, turmas.getQuantidadeMaximaAlunos());
        statement.setString(4, turmas.getDiasDaSemana());
        statement.setTime(5, turmas.getHorario());
        statement.setInt(6, turmas.getCodBanco());
        
        statement.execute();
        statement.close();
        
        //atualizando a tabela de horarios
        horariosDao.atualizarDados(horario);
    }
    
    //Remover Dados
    public void removerTurma(int codTurma) throws SQLException{
        //Removendo Turmas
        String inTurmas = remover.concat("tblTurmas WHERE codTurma = ?");
        
        PreparedStatement statement = gerarStatement(inTurmas);
        statement.setInt(1, codTurma);
        statement.execute();
        statement.close();
        
        //Removendo Horários da Tabela
        horariosDao.removerHorarios(statement, codTurma);
    }
    
    public ArrayList <Turmas> selecionarTodasTurmas() throws SQLException{
        String inTurmas = selecionarTudo.concat("tblTurmas");
        return pesquisarTurmas(inTurmas);
    }
    
    public ArrayList <Turmas> pesquisarTurmas(String comando) throws SQLException{
     ArrayList <Turmas> turmas = new ArrayList();
     PreparedStatement statement = gerarStatement(comando);
     statement.execute();
     ResultSet resultset = statement.getResultSet();
     boolean next = resultset.next();
     
     if(next==false){
         return null;
     }
     
    do{
    int codBanco = resultset.getInt("codTurma");
    String nome = resultset.getString("nome");
    String subgrupo = resultset.getString("subgrupo");
    int quantAlunos = resultset.getInt("quantAlunos");
    int quantLimiteDeAlunos = resultset.getInt("quantLimiteDeAlunos");
    String diasDaSemana = resultset.getString("diasDaSemana");
    
    int tamanhoHora = resultset.getTime("horario").toString().length()-3;
    String horario = resultset.getTime("horario").toString().substring(0, tamanhoHora);

    Turmas turma = new Turmas(codBanco, nome, subgrupo, quantAlunos, quantLimiteDeAlunos, diasDaSemana, horario);

    turmas.add(turma);
     }while(resultset.next());
    
    statement.close();
    return turmas;
    }
    
    
}
