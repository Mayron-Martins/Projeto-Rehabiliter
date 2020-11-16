/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Model.Aluno;
import Model.auxiliar.EnderecoAlunos;
import Model.auxiliar.Matriculas;
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
public class AlunosDao extends Conexao{
    private final String inserir = "INSERT INTO ";
    private final String atualizar = "UPDATE ";
    private final String remover = "DELETE FROM ";
    private final String selecionarTudo = "SELECT * FROM ";
    private final EnderecoAlunosDao enderecoDao = new EnderecoAlunosDao();
    private final MatriculasDao matriculaDao = new MatriculasDao();
    private final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();
    
    
    //Inserir dados na tabela Alunos
    public void inserirDados (Aluno aluno, EnderecoAlunos endereco, Matriculas matricula, int diasDaSemana, int codServico) throws SQLException{

        //Adicionando aluno
        String inAluno = inserir.concat("tblAlunos("
                + "codAluno, nome, cpf, rg, telefone, celular, email, dataNascimento, "
                + "codEndereco, nomeMae, nomePai, telefoneMae, telefonePai, cpfMae, cpfPai, "
                + "matricula, codTurma, codDiasDaSemana, codServico, descricao, debito, valorContrato)"
                + "VALUES("
                + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
        PreparedStatement statement = gerarStatement(inAluno);
        statement.setInt(1, aluno.getCodBanco());
        statement.setString(2, aluno.getNome());
        statement.setString(3, aluno.getCpf());
        statement.setString(4, aluno.getRg());
        statement.setString(5, aluno.getTelefone());
        statement.setString(6, aluno.getCelular());
        statement.setString(7, "");
        statement.setDate(8, (Date) aluno.getDatadenascimento());
        statement.setInt(9, endereco.getCodBanco());
        statement.setString(10, aluno.getNomedamae());
        statement.setString(11, aluno.getNomedopai());
        statement.setString(12, aluno.getTelefonedamae());
        statement.setString(13, aluno.getTelefonedopai());
        statement.setString(14, aluno.getCpfdamae());
        statement.setString(15, aluno.getCpfdopai());
        statement.setString(16, matricula.getMatricula());
        statement.setInt(17, aluno.getTurma());
        statement.setInt(18, diasDaSemana);
        statement.setInt(19, codServico);
        statement.setString(20, aluno.getDescricao());
        statement.setBigDecimal(21, new BigDecimal("0"));
        statement.setBigDecimal(22, new BigDecimal(aluno.getValorContrato().toString()));
        statement.execute();
        statement.close();
        
        //Adicionando endereco e matrícula do aluno
        enderecoDao.inserirDadosEmEnderecoAluno(endereco);
        matriculaDao.inserirDados(matricula);
    }
    
    
    
    //Atualizar dados
    public void atualizarDados(Aluno aluno, EnderecoAlunos endereco, Matriculas matricula, int diasDaSemana) throws SQLException{
        //atualizando a tabela de alunos
        String inAlunos = atualizar.concat("tblAlunos "
                + "SET nome = ?, cpf = ?, rg = ?, telefone=?, celular=?, telefoneMae=?, telefonePai=?, matricula=?, codTurma=?, codDiasDaSemana=?, codServico=?, descricao=?, debito=?, valorContrato=? where codAluno = ?");
        
        PreparedStatement statement = gerarStatement(inAlunos);
        statement.setString(1, aluno.getNome());
        statement.setString(2, aluno.getCpf());
        statement.setString(3, aluno.getRg());
        statement.setString(4, aluno.getTelefone());
        statement.setString(5, aluno.getCelular());
        statement.setString(6, aluno.getTelefonedamae());
        statement.setString(7, aluno.getTelefonedopai());
        statement.setString(8, matricula.getMatricula());
        statement.setInt(9, aluno.getTurma());
        statement.setInt(10, diasDaSemana);
        statement.setInt(11, aluno.getPlano());
        statement.setString(12, aluno.getDescricao());
        statement.setBigDecimal(13, new BigDecimal(aluno.getDebito().toString()));
        statement.setBigDecimal(14, new BigDecimal(aluno.getValorContrato().toString()));
        statement.setInt(15, aluno.getCodBanco());
        
        statement.execute();
        statement.close();
        
        //atualizando a tabela de horarios
        enderecoDao.atualizarDados(endereco);
        matriculaDao.atualizarDados(matricula);
    }
    
    //Remover Dados
    public void removerAluno(int codAluno) throws SQLException{
        //Removendo endereço e matricula do aluno
        enderecoDao.removerEnderecoAluno(codAluno);
        matriculaDao.removerMatricula(codAluno);
        
        
        //Removendo Alunos
        String inAlunos = remover.concat("tblAlunos WHERE codAluno = ?");
        
        PreparedStatement statement = gerarStatement(inAlunos);
        statement.setInt(1, codAluno);
        statement.execute();
        statement.close();  
    }
    
    public ArrayList <Aluno> selecionarTodosAlunos() throws SQLException, ParseException{
        String inAlunos = selecionarTudo.concat("tblAlunos");
        return pesquisarAlunos(inAlunos);
    }
    
    public ArrayList <Aluno> pesquisarAlunos(String comando) throws SQLException, ParseException{
     ArrayList <Aluno> alunos = new ArrayList();
     PreparedStatement statement = gerarStatement(comando);
     statement.execute();
     ResultSet resultset = statement.getResultSet();
     boolean next = resultset.next();
     
     if(next==false){
         return null;
     }
     
    do{
    int codBanco = resultset.getInt("codAluno");
    String nome = resultset.getString("nome");
    String cpf = resultset.getString("cpf");
    String rg = resultset.getString("rg");
    String telefone = resultset.getString("telefone");
    String celular = resultset.getString("celular");
    String email = "";
    String dataNascimento = converterData.parseDate(resultset.getDate("dataNascimento"));
    String nomeMae = resultset.getString("nomeMae");
    String nomePai = resultset.getString("nomePai");
    String telefoneMae = resultset.getString("telefoneMae");
    String telefonePai = resultset.getString("telefonePai");
    String cpfMae = resultset.getString("cpfMae");
    String cpfPai = resultset.getString("cpfPai");
    int codTurma = resultset.getInt("codTurma");
    int codPlano = resultset.getInt("codServico");
    String descricao = resultset.getString("descricao");
    BigDecimal debito= new BigDecimal(resultset.getBigDecimal("debito").toString());
    BigDecimal valorContrato= new BigDecimal(resultset.getBigDecimal("valorContrato").toString());
    
    Aluno aluno = new Aluno(codBanco, nome, cpf, rg, telefone, celular, email, dataNascimento, nomeMae, nomePai, telefoneMae, telefonePai, cpfMae, cpfPai, codTurma, codPlano, descricao, debito, valorContrato);

    alunos.add(aluno);
     }while(resultset.next());
    
    statement.close();
    return alunos;
    }
    
    public ArrayList<Aluno> pesquisarPorNome(String nomeAluno) throws SQLException, Exception
    {
           ArrayList <Aluno> alunos = selecionarTodosAlunos();
           ArrayList<Aluno> alunosBuscados = new ArrayList<>();
           for(int repeticoes = 0; repeticoes<alunos.size(); repeticoes++){
               if(alunos.get(repeticoes).getNome().toLowerCase().contains(nomeAluno.toLowerCase())== true){
                   alunosBuscados.add(alunos.get(repeticoes));
               }
           }
           if(alunosBuscados.size()<1){
               return null;
           }
           return alunosBuscados;
    }
    
    
    
}
