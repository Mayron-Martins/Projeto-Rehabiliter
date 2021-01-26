/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Controller.auxiliar.LogsSystem;
import Model.Funcionario;
import Model.auxiliar.EnderecoFuncionario;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public void inserirDados (Funcionario funcionario, EnderecoFuncionario endereco){
        try{
            //Adicionando aluno
            String inFuncionario = inserir.concat("tblFuncionarios("
                    + "codFuncionario, nome, cpf, rg, telefone, celular, email, dataNascimento, "
                    + "codEndereco, usuario, senha, cargo, salario, telasPermitidas, status, situacao)"
                    + "VALUES("
                    + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
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
            statement.setString(16, funcionario.getSituacao());
            statement.execute();
            statement.close();

            //Adicionando endereco e matrícula do aluno
            enderecoDao.inserirDadosEmEnderecoFuncionario(endereco);
        } catch (SQLException ex) {
            gerarLog(ex);
        }
    }
    
    
    
    //Atualizar dados
    public void atualizarDados(Funcionario funcionario){
        try{
            String inFuncionario = atualizar.concat("tblFuncionarios "
                    + "SET nome = ?, cpf = ?, rg = ?, telefone=?, celular=?, salario=?, telasPermitidas=? where codFuncionario = ?");

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
        } catch (SQLException ex) {
            gerarLog(ex);
        }
        

    }
    
    public void atualizarTelasPermitidas(Funcionario funcionario){
        try{
            String inFuncionario = atualizar.concat("tblFuncionarios "
                    + "SET telasPermitidas=? WHERE codFuncionario = ?");

            PreparedStatement statement = gerarStatement(inFuncionario);
            statement.setString(1, funcionario.getTelasPermitidas());
            statement.setInt(2, funcionario.getCodBanco());
            statement.execute();
            statement.close();  
        } catch (SQLException ex) {
            gerarLog(ex);
        }
    }
    
    public void atualizarStatusAll(){
        try{
            String inFuncionario = atualizar.concat("tblFuncionarios "
                    + "SET status=?");

            PreparedStatement statement = gerarStatement(inFuncionario);
            statement.setString(1, "Inativo");
            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            gerarLog(ex);
        }
        
    }
    
    public void atualizarStatus(String usuario){
        try{
            String inFuncionario = atualizar.concat("tblFuncionarios "
                    + "SET status=? WHERE usuario = ?");

            PreparedStatement statement = gerarStatement(inFuncionario);
            statement.setString(1, "Ativo");
            statement.setString(2, usuario);
            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            gerarLog(ex);
        }
    }
    
    public void atualizarSenha(Funcionario funcionario){
        try{
            String inFuncionario = atualizar.concat("tblFuncionarios "
                    + "SET senha=? WHERE usuario = ?");

            PreparedStatement statement = gerarStatement(inFuncionario);
            statement.setString(1, funcionario.getSenha());
            statement.setString(2, funcionario.getUsuario());
            statement.execute();
            statement.close();    
        } catch (SQLException ex) {
            gerarLog(ex);
        }
    }
    
    public void atualizarSituacao(int codFuncionario, String situacao){
        try{
            String inFuncionario = atualizar.concat("tblFuncionarios "
                    + "SET situacao=? WHERE codFuncionario = ?");

            PreparedStatement statement = gerarStatement(inFuncionario);
            statement.setString(1, situacao);
            statement.setInt(2, codFuncionario);
            statement.execute();
            statement.close();    
        } catch (SQLException ex) {
            gerarLog(ex);
        }
    }
    
    //Remover Dados
    public void removerFuncionario(int codFuncionario){
        try{
            //Removendo endereço e matricula do aluno
            enderecoDao.removerEnderecoFuncionario(codFuncionario);


            //Removendo Alunos
            String inAlunos = remover.concat("tblFuncionarios WHERE codFuncionario = ?");

            PreparedStatement statement = gerarStatement(inAlunos);
            statement.setInt(1, codFuncionario);
            statement.execute();
            statement.close(); 
        } catch (SQLException ex) {
            gerarLog(ex);
        }
         
    }
    
    public ArrayList <Funcionario> selecionarTodosFuncionarios(){
        String inFuncionario = selecionarTudo.concat("tblFuncionarios");
        return pesquisarFuncionario(inFuncionario);
    }
    
    public ArrayList <Funcionario> pesquisarFuncionario(String comando){
        try{
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
            String situacao = resultset.getString("situacao");

            Funcionario funcionario = new Funcionario(codBanco, nome, cpf, "", telefone,celular , "", dataNascimento, usuario, senha, salario, cargo, telasPermitidas, status, situacao);

            funcionarios.add(funcionario);
             }while(resultset.next());

            statement.close();
            return funcionarios;
        } catch (SQLException ex) {
            gerarLog(ex);
            return null;
        }
        
    }
    
    public ArrayList<Funcionario> pesquisarPorNome(String nomeFuncionario){
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
    
    //Gerar arquivo com o log de erro, caso haja
    private void gerarLog(Throwable erro){
        LogsSystem gerarLog = new LogsSystem();
        gerarLog.gravarErro(erro);
        gerarLog.close();
    }

}
