/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Model.auxiliar.LogAçoesFuncionario;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author Mayro
 */
public class LogAçoesFuncionarioDao extends Conexao{
    private final String inserir = "INSERT INTO ";
    private final String atualizar = "UPDATE ";
    private final String remover = "DELETE FROM ";
    private final String selecionarTudo = "SELECT * FROM ";
    private final HorariosDao horariosDao = new HorariosDao();
    private final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();
    
    //Inserir dados na tabela Log
    public void inserirDados (LogAçoesFuncionario acoes) throws SQLException{
        //Adicionando Log
        String inLogs = inserir.concat("tblLogdeAcoesdoFun"+acoes.getCodFuncionario()+"("
                + "codFuncionario, data, acao, descricao)"
                + "VALUES("
                + "?,?,?,?);");
        PreparedStatement statement = gerarStatement(inLogs);
        statement.setInt(1, acoes.getCodFuncionario());
        statement.setTimestamp(2, (Timestamp) acoes.getDataEvento());
        statement.setString(3, acoes.getAcao());
        statement.setString(4, acoes.getDescricao());
        statement.execute();
        statement.close();
    }
    
    public ArrayList <LogAçoesFuncionario> selecionarTodosLogs(int codFuncionario) throws SQLException, ParseException{
        String inLogs = selecionarTudo.concat("tblLogdeAcoesdoFun"+codFuncionario);
        return pesquisarLogs(inLogs);
    }
    
    public ArrayList <LogAçoesFuncionario> pesquisarLogs(String comando) throws SQLException, ParseException{
     ArrayList <LogAçoesFuncionario> logs = new ArrayList();
     PreparedStatement statement = gerarStatement(comando);
     statement.execute();
     ResultSet resultset = statement.getResultSet();
     boolean next = resultset.next();
     
     if(next==false){
         return null;
     }
     
    do{
    int codFuncionario = resultset.getInt("codFuncionario");
    Date dataEvento = converterData.parseDateAndTime(resultset.getTimestamp("data"));
    String acao = resultset.getString("acao");
    String descricao = resultset.getString("descricao");

    LogAçoesFuncionario log = new LogAçoesFuncionario(codFuncionario, dataEvento, acao, descricao);

    logs.add(log);
     }while(resultset.next());
    
    statement.close();
    return logs;
    }
 
}
