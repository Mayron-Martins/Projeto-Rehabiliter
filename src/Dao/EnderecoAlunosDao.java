/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Controller.auxiliar.LogsSystem;
import Model.auxiliar.EnderecoAlunos;
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
public class EnderecoAlunosDao extends Conexao{
    private final String inserir = "INSERT INTO ";
    private final String atualizar = "UPDATE ";
    private final String remover = "DELETE FROM ";
    private final String selecionarTudo = "SELECT * FROM ";
    private ConversaodeDataParaPadraoDesignado converterData;
    
    //Inserir dados na tabela Endereco Alunos
    public void inserirDadosEmEnderecoAluno(EnderecoAlunos endereco){
        try{
            String inEndereco = inserir.concat("tblEndAlunoseClientes("
                    + "codEndAlunoseClientes, codAluno, logradouro, bairro, numero, complemento, referencia, cidade, estado, CEP)"
                    + "VALUES("
                    + "?,?,?,?,?,?,?,?,?,?);");
            PreparedStatement statement = gerarStatement(inEndereco);
            statement.setInt(1, endereco.getCodBanco());
            statement.setInt(2, endereco.getCodAluno());
            statement.setString(3, endereco.getLogradouro());
            statement.setString(4, endereco.getBairro());
            statement.setString(5, endereco.getNumero());
            statement.setString(6, endereco.getComplemento());
            statement.setString(7, endereco.getReferencia());
            statement.setString(8, endereco.getCidade());
            statement.setString(9, endereco.getEstado());
            statement.setString(10, endereco.getCep());
            statement.execute(); 
            statement.close(); 
        } catch (SQLException ex) {
            gerarLog(ex);
        }
        
    }
    
    //Remove o endereço
    public void removerEnderecoAluno(int codAluno){
        try{
            String inEndereco= remover.concat("tblEndAlunoseClientes WHERE codAluno = ?");
            PreparedStatement statement = gerarStatement(inEndereco);
            statement.setInt(1, codAluno);
            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            gerarLog(ex);
        }
        
    }
    
    //Editar o Endereço
    public void atualizarDados(EnderecoAlunos endereco){
        try{
            String inEnderecos = atualizar.concat("tblEndAlunoseClientes "
                    + "SET logradouro = ?, bairro = ?, numero = ?, complemento=?, referencia=?, cidade=?, estado=?, CEP=? where codEndAlunoseClientes = ?");

            PreparedStatement statement = gerarStatement(inEnderecos);
            statement.setString(1, endereco.getLogradouro());
            statement.setString(2, endereco.getBairro());
            statement.setString(3, endereco.getNumero());
            statement.setString(4, endereco.getComplemento());
            statement.setString(5, endereco.getReferencia());
            statement.setString(6, endereco.getCidade());
            statement.setString(7, endereco.getEstado());
            statement.setString(8, endereco.getCep());
            statement.setInt(9, endereco.getCodBanco());

            statement.execute();
            statement.close();   
        } catch (SQLException ex) {
            gerarLog(ex);
        }
          
    }
    
    //Selecionar Todos os Endereços dos Alunos
    public ArrayList <EnderecoAlunos> selecionarTodosEnderecosAlunos(){
        String inEndereco = selecionarTudo.concat("tblEndAlunoseClientes");
        return pesquisarEndereco(inEndereco);
    }
    
    //Procurar por Endereco no Banco
    public ArrayList <EnderecoAlunos> pesquisarEndereco(String comando){
        try{
            ArrayList <EnderecoAlunos> enderecos = new ArrayList<>();
            PreparedStatement statement = gerarStatement(comando);
            statement.execute();
            ResultSet resultset = statement.getResultSet();
            boolean next = resultset.next();

            if(next==false){
                return null;
            }

            do{
                int codBanco = resultset.getInt("codEndAlunoseClientes");
                int codAluno = resultset.getInt("codAluno");
                String logradouro = resultset.getString("logradouro");
                String bairro = resultset.getString("bairro");
                String numero = resultset.getString("numero");
                String complemento = resultset.getString("complemento");
                String referencia = resultset.getString("referencia");
                String cidade = resultset.getString("cidade");
                String estado = resultset.getString("estado");
                String cep = resultset.getString("CEP");

                EnderecoAlunos endereco = new EnderecoAlunos(codBanco, codAluno, logradouro, bairro, numero, complemento, referencia, cidade, estado, cep);
                enderecos.add(endereco);
            }while(resultset.next());


            statement.close();
            return enderecos;
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
