/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Model.auxiliar.Planos;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Mayro
 */
public class PlanosDao extends Conexao{
        private final String inserir = "INSERT INTO ";
    private final String atualizar = "UPDATE ";
    private final String remover = "DELETE FROM ";
    private final String selecionarTudo = "SELECT * FROM ";
    private ConversaodeDataParaPadraoDesignado converterData;
    
    
    //Inserir dados na tabela Matriculas
    public void inserirDados (Planos plano) throws SQLException{
        //Adicionando Matrícula
        String inPlanos = inserir.concat("tblPlanos("
                + "codAluno, codTurma, codServico, dataPagamento, diaVencimento, dataCancelamento, dataRenovacao, situacao, chavePlano)"
                + "VALUES("
                + "?,?,?,?,?,?,?,?,?);");
        PreparedStatement statement = gerarStatement(inPlanos);
        statement.setInt(1, plano.getCodAluno());
        statement.setInt(2, plano.getCodTurma());
        statement.setInt(3, plano.getCodServico());
        statement.setDate(4, (Date) plano.getDataPagamento());
        statement.setInt(5, plano.getDiaVencimento());
        statement.setDate(6, (Date) plano.getDataCancelamento());
        statement.setDate(7, (Date) plano.getDataRenovacao());
        statement.setString(8, plano.getSituacao());
        statement.setInt(9, plano.getChavePlano());
        statement.execute();
        statement.close();
    }
    
    public void atualizarDados(Planos plano) throws SQLException{
        String inPlanos = atualizar.concat("tblPlanos "
                + "SET codTurma = ?, codServico = ?, diaVencimento = ?, chavePlano = ? where codAluno = ?");
        
        PreparedStatement statement = gerarStatement(inPlanos);
        statement.setInt(1, plano.getCodTurma());
        statement.setInt(2, plano.getCodServico());
        statement.setInt(3, plano.getDiaVencimento());
        statement.setInt(4, plano.getChavePlano());
        statement.setInt(5, plano.getCodAluno());
        
        statement.execute();
        statement.close();     
    }
    
    public void atualizarSituacao(Planos plano) throws SQLException{
        String inPlanos = atualizar.concat("tblPlanos "
                + "SET dataPagamento = ?, dataCancelamento = ?, dataRenovacao=?, situacao = ?  WHERE codAluno = ?");
        
        PreparedStatement statement = gerarStatement(inPlanos);
        statement.setDate(1, (Date) plano.getDataPagamento());
        statement.setDate(2, (Date) plano.getDataCancelamento());
        statement.setDate(3, (Date) plano.getDataRenovacao());
        statement.setString(4, plano.getSituacao());
        statement.setInt(5, plano.getCodAluno());
        
        statement.execute();
        statement.close();     
    }
    
    
    //Remover Dados
    public void removerPlano(int codAluno) throws SQLException{
        //Removendo Planos
        String inPlanos = remover.concat("tblPlanos WHERE codAluno = ?");
        
        PreparedStatement statement = gerarStatement(inPlanos);
        statement.setInt(1, codAluno);
        statement.execute();
        statement.close();
    }
    
    public ArrayList <Planos> selecionarTodosPlanos() throws SQLException{
        String inPlanos = selecionarTudo.concat("tblPlanos");
        return pesquisarPlanos(inPlanos);
    }
    
    public ArrayList <Planos> pesquisarPlanos(String comando) throws SQLException{
     ArrayList <Planos> planos = new ArrayList();
     PreparedStatement statement = gerarStatement(comando);
     statement.execute();
     ResultSet resultset = statement.getResultSet();
     boolean next = resultset.next();
     
     if(next==false){
         return null;
     }
     
    do{
    int codAluno = resultset.getInt("codAluno");
    int codTurma = resultset.getInt("codTurma");
    int codServico = resultset.getInt("codServico");
    int diaVencimeto = resultset.getInt("diaVencimento");
    Date dataPagamento = resultset.getDate("dataPagamento");
    Date dataCancelamento = resultset.getDate("dataCancelamento");
    Date dataRenovacao = resultset.getDate("dataRenovacao");
    String situacao = resultset.getString("situacao");
    int chavePlano = resultset.getInt("chavePlano");

    Planos plano = new Planos(codAluno, codTurma, codServico, diaVencimeto, dataPagamento, dataCancelamento,dataRenovacao,situacao);

    planos.add(plano);
     }while(resultset.next());
    
    statement.close();
    return planos;
    }
}
