/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Model.auxiliar.Servicos;
import java.math.BigDecimal;
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
        String inServicos = inserir.concat("tblServicos("
                + "codServico, nome, periodo, formaPagamento, valor, valorAVista, valorBoleto, valorCartaoDeCredito, valorCartaoDeDebito)"
                + "VALUES("
                + "?,?,?,?,?,?,?,?,?);");
        PreparedStatement statement = gerarStatement(inServicos);
        statement.setInt(1, servico.getCodBanco());
        statement.setString(2, servico.getNome());
        statement.setString(3, servico.getPeriodo());
        statement.setString(4, servico.getFormaPagamento());
        statement.setBigDecimal(5, servico.getValor());
        statement.setBigDecimal(6, servico.getValorVista());
        statement.setBigDecimal(7, servico.getValorBoleto());
        statement.setBigDecimal(8, servico.getValorPrazoCredito());
        statement.setBigDecimal(9, servico.getValorPrazoDebito());
        statement.execute();
        statement.close();
    }
    
    
    
    //Atualizar dados
    public void atualizarDados(Servicos servico) throws SQLException{
        //atualizando a tabela de turmas
        String inServicos = atualizar.concat("tblServicos "
                + "SET nome = ?, periodo = ?, formaPagamento = ?, valor =?, valorAVista = ?, valorBoleto=?, valorCartaoDeCredito=?, valorCartaoDeDebito=? where codServico = ?");
        
        PreparedStatement statement = gerarStatement(inServicos);
        statement.setString(1, servico.getNome());
        statement.setString(2, servico.getPeriodo());
        statement.setString(3, servico.getFormaPagamento());
        statement.setBigDecimal(4, servico.getValor());
        statement.setBigDecimal(5, servico.getValorVista());
        statement.setBigDecimal(6, servico.getValorBoleto());
        statement.setBigDecimal(7, servico.getValorPrazoCredito());
        statement.setBigDecimal(8, servico.getValorPrazoDebito());
        statement.setInt(9, servico.getCodBanco());
        
        statement.execute();
        statement.close();
    }
    
    //Remover Dados
    public void removerServico(int codServico) throws SQLException{
        //Removendo Turmas
        String inServicos = remover.concat("tblServicos WHERE codServico = ?");
        
        PreparedStatement statement = gerarStatement(inServicos);
        statement.setInt(1, codServico);
        statement.execute();
        statement.close();
        
    }
    
    public ArrayList <Servicos> selecionarTodosServicos() throws SQLException{
        String inServicos = selecionarTudo.concat("tblServicos");
        return pesquisarServicos(inServicos);
    }
    
    public ArrayList <Servicos> pesquisarServicos(String comando) throws SQLException{
     ArrayList <Servicos> servicos = new ArrayList();
     PreparedStatement statement = gerarStatement(comando);
     statement.execute();
     ResultSet resultset = statement.getResultSet();
     boolean next = resultset.next();
     
     if(next==false){
         return null;
     }
     
    do{
    int codBanco = resultset.getInt("codServico");
    String nome = resultset.getString("nome");
    String periodo = resultset.getString("periodo");
    String formaPagamento = resultset.getString("formaPagamento");
    BigDecimal valor = new BigDecimal(resultset.getBigDecimal("valor").toString());
    BigDecimal valorAVista = new BigDecimal(resultset.getBigDecimal("valorAVista").toString());
    BigDecimal valorBoleto = new BigDecimal(resultset.getBigDecimal("valorBoleto").toString());
    BigDecimal valorAPrazoCredito = new BigDecimal(resultset.getBigDecimal("valorCartaoDeCredito").toString());
    BigDecimal valorAPrazoDebito = new BigDecimal (resultset.getBigDecimal("valorCartaoDeDebito").toString());

    Servicos servico = new Servicos(codBanco, nome, periodo, formaPagamento, valor, valorAVista, valorBoleto, valorAPrazoCredito, valorAPrazoDebito);

    servicos.add(servico);
     }while(resultset.next());
    
    statement.close();
    return servicos;
    }
 
}
