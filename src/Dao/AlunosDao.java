/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Controller.auxiliar.LogsSystem;
import Model.Aluno;
import Model.auxiliar.EnderecoAlunos;
import Model.auxiliar.Matriculas;
import Model.auxiliar.Planos;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
    private final PlanosDao planosDao = new PlanosDao();
    private final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();
    
    
    //Inserir dados na tabela Alunos
    public void inserirDados (Aluno aluno, EnderecoAlunos endereco, Matriculas matricula, Planos plano){
        try{
            LocalDate dataAtual = LocalDate.now();
            //Adicionando aluno
            String inAluno = inserir.concat("tblAlunos("
                    + "nome, cpf, rg, telefone, celular, email, dataNascimento, "
                    + "nomeMae, nomePai, telefoneMae, telefonePai, cpfMae, cpfPai, "
                    + "codTurma, codServico, descricao, debito, valorContrato, dataCadastro,"
                    + "valorMensal, renovacaoAutomatica)"
                    + "VALUES("
                    + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
            PreparedStatement statement = gerarStatement(inAluno);
            statement.setString(1, aluno.getNome());
            statement.setString(2, aluno.getCpf());
            statement.setString(3, aluno.getRg());
            statement.setString(4, aluno.getTelefone());
            statement.setString(5, aluno.getCelular());
            statement.setString(6, aluno.getEmail());
            statement.setDate(7, (Date) aluno.getDatadenascimento());
            statement.setString(8, aluno.getNomedamae());
            statement.setString(9, aluno.getNomedopai());
            statement.setString(10, aluno.getTelefonedamae());
            statement.setString(11, aluno.getTelefonedopai());
            statement.setString(12, aluno.getCpfdamae());
            statement.setString(13, aluno.getCpfdopai());
            statement.setInt(14, aluno.getTurma());
            statement.setInt(15, aluno.getServico());
            statement.setString(16, aluno.getDescricao());
            statement.setBigDecimal(17, new BigDecimal(aluno.getDebito().toString()));
            statement.setBigDecimal(18, new BigDecimal(aluno.getValorContrato().toString()));
            statement.setDate(19, (Date) aluno.getDataCadastro());
            statement.setBigDecimal(20, new BigDecimal(aluno.getValorMensal().toString()));
            statement.setInt(21, aluno.getRenovacaoAutomatica());
            statement.execute();
            statement.close();

            //Adicionando endereco e matrícula do aluno
            enderecoDao.inserirDadosEmEnderecoAluno(endereco);
            matriculaDao.inserirDados(matricula);
            planosDao.inserirDados(plano);
        } catch (SQLException ex) {
            gerarLog(ex);
        }
        
    }
    
    
    
    //Atualizar dados
    public void atualizarDados(Aluno aluno, EnderecoAlunos endereco, Matriculas matricula, Planos plano){
        try{
            //atualizando a tabela de alunos
            String inAlunos = atualizar.concat("tblAlunos "
                    + "SET nome = ?, cpf = ?, rg = ?, telefone=?, celular=?, telefoneMae=?, "
                    + "telefonePai=?, codTurma=?, codServico=?, "
                    + "debito=?, valorContrato=?, dataCadastro=?, valorMensal=?, renovacaoAutomatica=? where codAluno = ?");

            PreparedStatement statement = gerarStatement(inAlunos);
            statement.setString(1, aluno.getNome());
            statement.setString(2, aluno.getCpf());
            statement.setString(3, aluno.getRg());
            statement.setString(4, aluno.getTelefone());
            statement.setString(5, aluno.getCelular());
            statement.setString(6, aluno.getTelefonedamae());
            statement.setString(7, aluno.getTelefonedopai());
            statement.setInt(8, aluno.getTurma());
            statement.setInt(9, aluno.getServico());
            statement.setBigDecimal(10, new BigDecimal(aluno.getDebito().toString()));
            statement.setBigDecimal(11, new BigDecimal(aluno.getValorContrato().toString()));
            statement.setDate(12, (Date) aluno.getDataCadastro());
            statement.setBigDecimal(13, new BigDecimal(aluno.getValorMensal().toString()));
            statement.setInt(14, aluno.getRenovacaoAutomatica());
            statement.setInt(15, aluno.getCodBanco());

            statement.execute();
            statement.close();

            //atualizando a tabela de horarios
            enderecoDao.atualizarDados(endereco);
            matriculaDao.atualizarDados(matricula);
            planosDao.atualizarDados(plano);
            planosDao.atualizarSituacao(plano);
        } catch (SQLException ex) {
            gerarLog(ex);
        }
    }
    
        //Atualizar débitos
    public void atualizarDebitos(int codAluno, BigDecimal debito){
        try{
            //atualizando a tabela de alunos
            String inAlunos = atualizar.concat("tblAlunos "
                    + "SET debito=? WHERE codAluno = ?");

            PreparedStatement statement = gerarStatement(inAlunos);
            statement.setBigDecimal(1, new BigDecimal(debito.toString()));
            statement.setInt(2,codAluno);

            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            gerarLog(ex);
        }
        
    }
    
    public void atualizarPlanoAdicional(Aluno aluno){
        try{
            //atualizando a tabela de alunos
            String inAlunos = atualizar.concat("tblAlunos "
                    + "SET codTurma=?, codServico=?, debito=?, valorContrato=?, valorMensal=?, renovacaoAutomatica=? WHERE codAluno = ?");

            PreparedStatement statement = gerarStatement(inAlunos);
            statement.setInt(1,aluno.getTurma());
            statement.setInt(2,aluno.getServico());
            statement.setBigDecimal(3, aluno.getDebito());
            statement.setBigDecimal(4, aluno.getValorContrato());
            statement.setBigDecimal(5, aluno.getValorMensal());
            statement.setInt(6,aluno.getRenovacaoAutomatica());
            statement.setInt(7,aluno.getCodBanco());

            statement.execute();
            statement.close();
            
        } catch (SQLException ex) {
            gerarLog(ex);
        }
    }
    
    public void atualizarDescricao(int codAluno, String descricao){
        try{
            //atualizando a tabela de alunos
            String inAlunos = atualizar.concat("tblAlunos "
                    + "SET descricao=? WHERE codAluno = ?");

            PreparedStatement statement = gerarStatement(inAlunos);
            statement.setString(1,descricao);
            statement.setInt(2,codAluno);

            statement.execute();
            statement.close();
            
        } catch (SQLException ex) {
            gerarLog(ex);
        }
    }
    
    
    //Remover Dados
    public void removerAluno(int codAluno){
        try{
            //Removendo endereço e matricula do aluno
            enderecoDao.removerEnderecoAluno(codAluno);
            matriculaDao.removerMatricula(codAluno);
            planosDao.removerPlano(codAluno);


            //Removendo Alunos
            String inAlunos = remover.concat("tblAlunos WHERE codAluno = ?");

            PreparedStatement statement = gerarStatement(inAlunos);
            statement.setInt(1, codAluno);
            statement.execute();
            statement.close();  
        } catch (SQLException ex) {
            gerarLog(ex);
        }
        
    }
    
    public ArrayList <Aluno> selecionarTodosAlunos(){
        String inAlunos = selecionarTudo.concat("tblAlunos");
        return pesquisarAlunos(inAlunos);
    }
    
    public ArrayList <Aluno> pesquisarAlunos(String comando){
        try{
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
            String email = resultset.getString("email");
            Date dataNascimento = resultset.getDate("dataNascimento");
            String nomeMae = resultset.getString("nomeMae");
            String nomePai = resultset.getString("nomePai");
            String telefoneMae = resultset.getString("telefoneMae");
            String telefonePai = resultset.getString("telefonePai");
            String cpfMae = resultset.getString("cpfMae");
            String cpfPai = resultset.getString("cpfPai");
            int codTurma = resultset.getInt("codTurma");
            int codServico = resultset.getInt("codServico");
            String descricao = resultset.getString("descricao");
            BigDecimal debito= new BigDecimal(resultset.getBigDecimal("debito").toString());
            BigDecimal valorContrato= new BigDecimal(resultset.getBigDecimal("valorContrato").toString());
            Date dataCadastro = resultset.getDate("dataCadastro");
            BigDecimal valorMensal= new BigDecimal(resultset.getBigDecimal("valorMensal").toString());
            int renovacaoAutomatica = resultset.getInt("renovacaoAutomatica");

            Aluno aluno = new Aluno(codBanco, nome, cpf, rg, telefone, celular, email, dataNascimento, nomeMae, nomePai, telefoneMae, telefonePai, cpfMae, cpfPai, codTurma, codServico, descricao, debito, valorContrato, dataCadastro,
            valorMensal, renovacaoAutomatica);

            alunos.add(aluno);
             }while(resultset.next());

            statement.close();
            return alunos;
        } catch (SQLException ex) {
            gerarLog(ex);
            return null;
        }
    }
    
    public ArrayList<Aluno> pesquisarPorNome(String nomeAluno){
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
    
    //Gerar arquivo com o log de erro, caso haja
    private void gerarLog(Throwable erro){
        LogsSystem gerarLog = new LogsSystem();
        gerarLog.gravarErro(erro);
        gerarLog.close();
    }
}
