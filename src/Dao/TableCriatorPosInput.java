/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Mayro
 */
public class TableCriatorPosInput extends Conexao{
    private final Conexao conexao = new Conexao() {};
    private final String alterarTabela = "ALTER TABLE";
    private final String regra = "ADD CONSTRAINT";
    private final String fk = "FOREIGN KEY";
    
    //Criação do statement para execução de comando no banco.
    private Statement gerarStatement() throws SQLException{
        Statement statement = conexao.getConnection().createStatement();
        return statement;
    }
    
        //Usadas quando se cria uma turma ou um funcionário
    //_________________________________________________________________________________________
    //Criação da tabela de frequencia turmas.
    public void tableFreqTurmas() throws SQLException{
        gerarStatement().execute("CREATE TABLE tblFreqTurma"+this.quantTurmas()+"("
                + "codTurma INT NOT NULL," //Associa a turma à frequência.
                + "codAluno INT NOT NULL," //Associa o código do aluno à frequência.
                + "data DATE NOT NULL," //Data em que ocorre a chamada.
                + "situacao CHAR(2) NOT NULL" //P-presente ou A-ausente.
                + ")ON [AlunoseClientes];");

    }
    
    
    //Criação da tabela de Log das ações do funcionário.
    public void tableLogdeAcoesdoFunc() throws SQLException{
            this.gerarStatement().execute("CREATE TABLE tblLogdeAcoesdoFun"+this.quantFuncionarios()+"("
                + "codFuncionario INT NOT NULL," //Associa o log ao código do funcionário
                + "data DATETIME NOT NULL," //data e hora do acontecimento.
                + "acao VARCHAR(MAX) NOT NULL,"
                + "descricao VARCHAR(MAX) NULL"
                + ")ON [Funcionarios];");

    }
    
    //Função para identificar a quantidade de turmas dentro do banco.
    public int quantTurmas() throws SQLException{
        ResultSet count = this.gerarStatement().executeQuery("SELECT COUNT(codTurma) AS quant FROM tblTurmas;"); //Conta a quantidade de itens na coluna quant do count.
        int contador = 0;
        while(count.next()){
            contador = count.getInt("quant"); //Como o count possui apenas uma linha então é igualado apenas uma vez.
        }
        
        return contador;
    }
    
    //Função para identificar a quantidade de funcionários dentro do banco.
    public int quantFuncionarios() throws SQLException{
        ResultSet count = this.gerarStatement().executeQuery("SELECT COUNT(codFuncionario) AS quant FROM tblFuncionarios;"); //Conta a quantidade de itens na coluna quant do count.
        int contador = 0;
        while(count.next()){
            contador = count.getInt("quant"); //Como o count possui apenas uma linha então é igualado apenas uma vez.
        }
        
        return contador;
    }
    /*
    
        //Usadas quando se cria um funcionário ou uma turma
    //_____________________________________________________________________________________________________________
    //Referência da coluna codTurma da tabela de Frequência de Turmas em relação a coluna codTurma na tabela de Turmas.
    private void fkTblFreqTurmas_TblTurmas(Statement statement, int numeroDaTurma) throws SQLException{
        statement.execute(this.alterarTabela+" tblFreqTurma"+numeroDaTurma+" "
                +this.regra+" FK_FreqTurmas_Turmas"
                +this.fk+"(codTurma)"
                +"REFERENCES tblTurmas(codTurma);");
    }
    
    //Referência da coluna codAluno da tabela de Frequência de Turmas em relação a coluna codAuno na tabela de Alunos.
    private void fkTblFreqTurmas_TblAlunos(Statement statement, int numeroDaTurma) throws SQLException{
        statement.execute(this.alterarTabela+" tblFreqTurma"+numeroDaTurma+" "
                +this.regra+" FK_FreqTurmas_Alunos"
                +this.fk+"(codAluno)"
                +"REFERENCES tblAlunos(codAluno);");
    }
    
    //Faz referência com os itens correspondentes na tabela de Frequência de Turmas, deve ser executada quando for criada uma turma.
    public void referencesFreqTurmas(Statement statement) throws SQLException{
        TableCriator turmas = new TableCriator(this.telaDeInicio);
        this.fkTblFreqTurmas_TblAlunos(statement, turmas.quantTurmas());
        this.fkTblFreqTurmas_TblTurmas(statement, turmas.quantTurmas());
    }
    
    //Referência da coluna codFuncionario da tabela de Log de Ações do Funcionário em relação a coluna codFuncionario na tabela de Funcionários, deve ser executada quando for criado um novo funcionário.
    public void fkTblLogDeAcoesDoFunc_TblFuncionarios(Statement statement) throws SQLException{
        telaDeInicio.mudartexto("Referenciando tabela Log F com Fun...");
        telaDeInicio.mudarPercentual();
        TableCriator funcionarios = new TableCriator(this.telaDeInicio);
        statement.execute(this.alterarTabela+" tblLogdeAcoesdoFun"+funcionarios.quantFuncionarios()+" "
                +this.regra+" FK_LogDeAcoesDoFunc_Funcionarios"
                +this.fk+"(codFuncionario)"
                +"REFERENCES tblFuncionarios(codFuncionario);");
    }
    */
}
