/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Controller.auxiliar.LogsSystem;
import Model.auxiliar.Horarios;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Mayro
 */
public class HorariosDao extends Conexao{
    private final String inserir = "INSERT INTO ";
    private final String remover = "DELETE FROM ";
    private final String selecionarTudo = "SELECT * FROM ";
    private ConversaodeDataParaPadraoDesignado converterData;
    
    public void inserirDadosEmHorarios(PreparedStatement statement, ArrayList<Horarios>horarios){
        try{
            String inHorarios = inserir.concat("tblHorarios("
                    + "codHorario, diaDaSemana, indiceSemana, horarioInicio, horarioFim, codCliente, codTurma)"
                    + "VALUES("
                    + "?,?,?,?,?,?,?);");
            statement = gerarStatement(inHorarios);
            
            for(Horarios horario:horarios){
                statement.setInt(1, horario.getCodBanco());
                statement.setString(2,horario.getDiaDaSemana());
                statement.setInt(3, horario.getIndiceSemana());
                statement.setString(4, horario.getHorarioInicio());
                statement.setString(5, horario.getHorarioFim());
                statement.setInt(6, horario.getCodCliente());
                statement.setInt(7, horario.getCodTurma());
                statement.execute(); 
            }
            statement.close(); 
        } catch (SQLException ex) {
            gerarLog(ex);
        }
        
    }
    
    public void removerHorarios(PreparedStatement statement, int codHorario){
        try{
            String inHorarios = remover.concat("tblHorarios WHERE codHorario = ?");
        
            statement = gerarStatement(inHorarios);
            statement.setInt(1, codHorario);
            statement.execute();
            statement.close(); 
        } catch (SQLException ex) {
            gerarLog(ex);
        }
    }
    
    //Editar os Horários
    public void atualizarDados(ArrayList <Horarios> horarios){
        PreparedStatement statement = null;
        removerHorarios(statement, horarios.get(0).getCodBanco());
        inserirDadosEmHorarios(statement, horarios);
    }
    
    //Selecionar Todos os Horários
    public ArrayList <Horarios> selecionarTodosHorarios(){
        String inHorarios = selecionarTudo.concat("tblHorarios");
        return pesquisarHorarios(inHorarios);
    }
    
    //Procurar por Horários no Banco
    public ArrayList <Horarios> pesquisarHorarios(String comando){
        try{
            ArrayList <Horarios> horarios = new ArrayList();
            PreparedStatement statement = gerarStatement(comando);
            statement.execute();
            ResultSet resultset = statement.getResultSet();
            boolean next = resultset.next();

            if(next==false){
                return null;
            }

            do{
            int codBanco = resultset.getInt("codHorario");
            String diaDaSemana = resultset.getString("diaDaSemana");
            int indiceSemana = resultset.getInt("indiceSemana");
            String horarioInicio = resultset.getString("horarioInicio");
            String horarioFim = resultset.getString("horarioFim");
            int codCliente = resultset.getInt("codCliente");
            int codTurma = resultset.getInt("codTurma");


            Horarios horario = new Horarios(codBanco, diaDaSemana, indiceSemana, horarioInicio, horarioFim, codCliente, codTurma);

            horarios.add(horario);
             }while(resultset.next());
            statement.close();
            return horarios;
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
