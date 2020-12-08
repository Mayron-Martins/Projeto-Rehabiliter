/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Model.Funcionario;
import Model.auxiliar.EnderecoFuncionario;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author Mayro
 */
public class FuncionarioDao extends Conexao{
    private final String inserir = "INSERT INTO ";
    private final String atualizar = "UPDATE ";
    private final String remover = "DELETE FROM ";
    private final String selecionarTudo = "SELECT * FROM ";
    private final EnderecoFuncionarioDao enderecoDao = new EnderecoFuncionarioDao();
    private final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();
    
    
    //Inserir dados na tabela Alunos
    public void inserirDados (Funcionario funcionario, EnderecoFuncionario endereco) throws SQLException{

        //Adicionando aluno
        String inFuncionario = inserir.concat("tblFuncionarios("
                + "codFuncionario, nome, cpf, rg, telefone, celular, email, dataNascimento, "
                + "codEndereco, usuario, senha, cargo, salario, telasPermitidas, status)"
                + "VALUES("
                + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
        PreparedStatement statement = gerarStatement(inFuncionario);
        statement.setInt(1, funcionario.getCodBanco());
        statement.setString(2, funcionario.getNome());
        statement.setString(3, funcionario.getCpf());
        statement.setString(4, funcionario.getRg());
        statement.setString(5, funcionario.getTelefone());
        statement.setString(6, funcionario.getCelular());
        statement.setString(7, "");
        statement.setDate(8, (Date) funcionario.getDatadenascimento());
        statement.setInt(9, endereco.getCodBanco());
        statement.setString(10, funcionario.getUsuario());
        statement.setString(11, funcionario.getSenha());
        statement.setString(12, funcionario.getCargo());
        statement.setBigDecimal(13, new BigDecimal(funcionario.getSalario().toString()));
        statement.setString(14, funcionario.getTelasPermitidas());
        statement.setString(15, funcionario.getStatus());
        statement.execute();
        statement.close();
        
        //Adicionando endereco e matrícula do aluno
        enderecoDao.inserirDadosEmEnderecoFuncionario(endereco);
    }
    
    
    
    //Atualizar dados
    public void atualizarDados(Funcionario funcionario) throws SQLException{
        //atualizando a tabela de alunos
        String inFuncionario = atualizar.concat("tblFuncionarios "
                + "SET nome = ?, cpf = ?, rg = ?, telefone=?, celular=?, salario=?, status=? where codFuncionario = ?");
        
        PreparedStatement statement = gerarStatement(inFuncionario);
        statement.setString(1, funcionario.getNome());
        statement.setString(2, funcionario.getCpf());
        statement.setString(3, funcionario.getRg());
        statement.setString(4, funcionario.getTelefone());
        statement.setString(5, funcionario.getCelular());
        statement.setBigDecimal(6, new BigDecimal(funcionario.getSalario().toString()));
        statement.setString(7, funcionario.getTelasPermitidas());
        statement.setInt(8, funcionario.getCodBanco());
        
        statement.execute();
        statement.close();
        
        //atualizando a tabela de horarios
    }
    
    public void atualizarStatus(Funcionario funcionario) throws SQLException{
        String inFuncionario = atualizar.concat("tblFuncionarios "
                + "SET status=? where usuario = ?");
        
        PreparedStatement statement = gerarStatement(inFuncionario);
        statement.setString(1, funcionario.getStatus());
        statement.setString(2, funcionario.getUsuario());
        statement.execute();
        statement.close();
    }
    
    public void atualizarSenha(Funcionario funcionario) throws SQLException{
        String inFuncionario = atualizar.concat("tblFuncionarios "
                + "SET senha=? where usuario = ?");
        
        PreparedStatement statement = gerarStatement(inFuncionario);
        statement.setString(1, funcionario.getSenha());
        statement.setString(2, funcionario.getUsuario());
        statement.execute();
        statement.close();
    }
    
    //Remover Dados
    public void removerFuncionario(int codFuncionario) throws SQLException{
        //Removendo endereço e matricula do aluno
        enderecoDao.removerEnderecoFuncionario(codFuncionario);
        
        
        //Removendo Alunos
        String inAlunos = remover.concat("tblFuncionarios WHERE codFuncionario = ?");
        
        PreparedStatement statement = gerarStatement(inAlunos);
        statement.setInt(1, codFuncionario);
        statement.execute();
        statement.close();  
    }
    
    public ArrayList <Funcionario> selecionarTodosFuncionarios() throws SQLException, ParseException{
        String inFuncionario = selecionarTudo.concat("tblFuncionarios");
        return pesquisarFuncionario(inFuncionario);
    }
    
    public ArrayList <Funcionario> pesquisarFuncionario(String comando) throws SQLException, ParseException{
     ArrayList <Funcionario> funcionarios = new ArrayList();
     PreparedStatement statement = gerarStatement(comando);
     statement.execute();
     ResultSet resultset = statement.getResultSet();
     boolean next = resultset.next();
     
     if(next==false){
         return null;
     }
     
    do{
    int codBanco = resultset.getInt("codFuncionario");
    String nome = resultset.getString("nome");
    String cpf = resultset.getString("cpf");
    String telefone = resultset.getString("telefone");
    String celular = resultset.getString("celular");
    Date dataNascimento = resultset.getDate("dataNascimento");
    String usuario = resultset.getString("usuario");
    String senha = resultset.getString("senha");
    BigDecimal salario= new BigDecimal(resultset.getBigDecimal("salario").toString());
    String cargo = resultset.getString("cargo");
    String telasPermitidas = resultset.getString("telasPermitidas");
    String status = resultset.getString("status");
    
    Funcionario funcionario = new Funcionario(codBanco, nome, cpf, "", telefone,celular , "", dataNascimento, usuario, senha, salario, cargo, telasPermitidas, status);

    funcionarios.add(funcionario);
     }while(resultset.next());
    
    statement.close();
    return funcionarios;
    }
    
    public ArrayList<Funcionario> pesquisarPorNome(String nomeFuncionario) throws SQLException, Exception
    {
           ArrayList <Funcionario> funcionarios = selecionarTodosFuncionarios();
           ArrayList<Funcionario> funcionariosBuscados = new ArrayList<>();
           for(int repeticoes = 0; repeticoes<funcionarios.size(); repeticoes++){
               if(funcionarios.get(repeticoes).getNome().toLowerCase().contains(nomeFuncionario.toLowerCase())== true){
                   funcionariosBuscados.add(funcionarios.get(repeticoes));
               }
           }
           if(funcionariosBuscados.size()<1){
               return null;
           }
           return funcionariosBuscados;
    }
    
    
    
}
