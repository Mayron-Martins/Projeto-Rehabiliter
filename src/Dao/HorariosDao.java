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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mayro
 */
public class HorariosDao extends Conexao{
    private final String inserir = "INSERT INTO ";
    private final String remover = "DELETE FROM ";
    private final String selecionarTudo = "SELECT * FROM ";
    private ConversaodeDataParaPadraoDesignado converterData;
    
    public void inserirDadosEmHorarios(PreparedStatement statement, ArrayList<Horarios>horario){
        try{
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

            int tamanhoHorario = resultset.getTime("horario").toString().length()-3;
            String horarioBanco = resultset.getTime("horario").toString().substring(0, tamanhoHorario);
            int codCliente = resultset.getInt("codCliente");
            int codTurma = resultset.getInt("codTurma");


            Horarios horario = new Horarios(codBanco, diaDaSemana, horarioBanco, codCliente, codTurma);

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
