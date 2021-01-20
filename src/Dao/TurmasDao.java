/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Controller.auxiliar.LogsSystem;
import Model.auxiliar.Horarios;
import Model.auxiliar.Turmas;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Mayro
 */
public class TurmasDao extends Conexao{
    private final String inserir = "INSERT INTO ";
    private final String atualizar = "UPDATE ";
    private final String remover = "DELETE FROM ";
    private final String selecionarTudo = "SELECT * FROM ";
    private final HorariosDao horariosDao = new HorariosDao();
    private ConversaodeDataParaPadraoDesignado converterData;
    
    
    //Inserir dados na tabela Turmas
    public void inserirDados (Turmas turma, ArrayList<Horarios> horario){
        try{
            //Adicionando Turma
            String inTurmas = inserir.concat("tblTurmas("
                    + "codTurma, nome, quantAlunos, quantLimiteDeAlunos, diasDaSemana, horario, situacao)"
                    + "VALUES("
                    + "?,?,?,?,?,?);");
            PreparedStatement statement = gerarStatement(inTurmas);
            statement.setInt(1, turma.getCodBanco());
            statement.setString(2, turma.getNomeTurma());
            statement.setInt(3, 0);
            statement.setInt(4, turma.getQuantidadeMaximaAlunos());
            statement.setString(5, turma.getDiasDaSemana());
            statement.setTime(6, turma.getHorario());
            statement.setString(7, turma.getSituacao());
            statement.execute();
            statement.close();

            //Adicionando Horário da Turma
            horariosDao.inserirDadosEmHorarios(statement, horario);
        } catch (SQLException ex) {
            gerarLog(ex);
        }
        
    }
    
    
    
    //Atualizar dados
    public void atualizarDados(Turmas turmas, ArrayList <Horarios> horario){
        try{
            //atualizando a tabela de turmas
            String inTurmas = atualizar.concat("tblTurmas "
                    + "SET nome = ?, quantLimiteDeAlunos = ?, diasDaSemana=?, horario=? where codTurma = ?");

            PreparedStatement statement = gerarStatement(inTurmas);
            statement.setString(1, turmas.getNomeTurma());
            statement.setInt(2, turmas.getQuantidadeMaximaAlunos());
            statement.setString(3, turmas.getDiasDaSemana());
            statement.setTime(4, turmas.getHorario());
            statement.setInt(5, turmas.getCodBanco());

            statement.execute();
            statement.close();

            //atualizando a tabela de horarios
            horariosDao.atualizarDados(horario);
        } catch (SQLException ex) {
            gerarLog(ex);
        }
        
    }
    
    //Atualizar quantidadeAlunos
    public void atualizarQuantAunos(int codBanco, int quantAlunos){
        try{
            //atualizando a tabela de turmas
            String inTurmas = atualizar.concat("tblTurmas "
                    + "SET quantAlunos = ? where codTurma = ?");

            PreparedStatement statement = gerarStatement(inTurmas);
            statement.setInt(1, quantAlunos);
            statement.setInt(2, codBanco);

            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            gerarLog(ex);
        }
    }
    
    public void atualizarSituacao(int codBanco, String situacao){
        try{
            //atualizando a tabela de turmas
            String inTurmas = atualizar.concat("tblTurmas "
                    + "SET situacao = ? where codTurma = ?");

            PreparedStatement statement = gerarStatement(inTurmas);
            statement.setString(1, situacao);
            statement.setInt(2, codBanco);

            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            gerarLog(ex);
        }
    }
    
    //Remover Dados
    public void removerTurma(int codTurma){
        try{
            //Removendo Turmas
            String inTurmas = remover.concat("tblTurmas WHERE codTurma = ?");

            PreparedStatement statement = gerarStatement(inTurmas);
            statement.setInt(1, codTurma);
            statement.execute();
            statement.close();

            //Removendo Horários da Tabela
            horariosDao.removerHorarios(statement, codTurma);
        } catch (SQLException ex) {
            gerarLog(ex);
        }
        
    }
    
    public ArrayList <Turmas> selecionarTodasTurmas(){
        String inTurmas = selecionarTudo.concat("tblTurmas");
        return pesquisarTurmas(inTurmas);
    }
    
    public ArrayList <Turmas> pesquisarTurmas(String comando){
        try{
            ArrayList <Turmas> turmas = new ArrayList();
            PreparedStatement statement = gerarStatement(comando);
            statement.execute();
            ResultSet resultset = statement.getResultSet();
            boolean next = resultset.next();

            if(next==false){
                return null;
            }

            do{
            int codBanco = resultset.getInt("codTurma");
            String nome = resultset.getString("nome");
            int quantAlunos = resultset.getInt("quantAlunos");
            int quantLimiteDeAlunos = resultset.getInt("quantLimiteDeAlunos");
            String diasDaSemana = resultset.getString("diasDaSemana");

            int tamanhoHora = resultset.getTime("horario").toString().length()-3;
            String horario = resultset.getTime("horario").toString().substring(0, tamanhoHora);
            String situacao = resultset.getString("situacao");

            Turmas turma = new Turmas(codBanco, nome, quantAlunos, quantLimiteDeAlunos, diasDaSemana, horario, situacao);

            turmas.add(turma);
             }while(resultset.next());

            statement.close();
            return turmas;
        } catch (SQLException ex) {
            gerarLog(ex);
            return null;
        }
    
    }
    
    public ArrayList<Turmas> pesquisarPorNome(String nomeTurma){
       ArrayList <Turmas> turmas = selecionarTodasTurmas();
       ArrayList<Turmas> turmasBuscadas = new ArrayList<>();
       for(int repeticoes = 0; repeticoes<turmas.size(); repeticoes++){
           if(turmas.get(repeticoes).getNomeTurma().toLowerCase().contains(nomeTurma.toLowerCase())== true){
               turmasBuscadas.add(turmas.get(repeticoes));
           }
       }
       if(turmasBuscadas.size()<1){
           return null;
       }
       return turmasBuscadas;
    }
    
    //Gerar arquivo com o log de erro, caso haja
    private void gerarLog(Throwable erro){
        LogsSystem gerarLog = new LogsSystem();
        gerarLog.gravarErro(erro);
        gerarLog.close();
    }
}
