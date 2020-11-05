/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import View.inicio;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Mayro
 */
public class ReferencesTable {
    private final String alterarTabela = "ALTER TABLE";
    private final String regra = "ADD CONSTRAINT";
    private final String fk = "FOREIGN KEY";
    private final inicio telaDeInicio;

    public ReferencesTable(inicio view) {
        this.telaDeInicio = view;
    }
    
    
    
    //Faz a referência das colunas com as tabelas correspondentes, evitando que um item inexistente entre na tabela.
    public void referencesTables(Statement statement) throws SQLException{
        this.fkTblAlunos_TblEndAlunos(statement);
        this.fkTblAlunos_TblServicos(statement);
        this.fkTblLoteDeProdutos_TblProdutos(statement);
        this.fkTblMatriculas_TblTurmas(statement);
        this.fkTblMatriculas_TblAlunos(statement);
        this.fkTblItensVendidos_TblVendas(statement);
        this.fkTblItensVendidos_TblProdutos(statement);
        this.fkTblItensComprados_TblGastos(statement);
        this.fkTblPlanos_TblAlunos(statement);
        this.fkTblPlanos_TblTurmas(statement);
        this.fkTblPlanos_TblServicos(statement);
        this.fkTblFreqFuncionarios_TblFuncionarios(statement);
    }
    
    
    //Referência da coluna codEndereco da tabela Alunos em relação a coluna codEndAlunoseClientes na tabela de Endereços de Alunos e Clientes.
    private void fkTblAlunos_TblEndAlunos(Statement statement) throws SQLException{
        telaDeInicio.mudartexto("Referenciando tabela Alunos com End...");
        telaDeInicio.mudarPercentual();
        statement.execute(this.alterarTabela+" tblAlunos "
                +this.regra+" FK_Alunos_Endereco " //Nome da Regra
                +this.fk+"(codEndereco) "
                +"REFERENCES tblEndAlunoseClientes(codEndAlunoseClientes);");
    }
    
    //Referência da coluna codServico da tabela de Alunos em relação a coluna codServico na tabela de Serviços.
    private void fkTblAlunos_TblServicos(Statement statement) throws SQLException{
        telaDeInicio.mudartexto("Referenciando tabela Alunos com Serv...");
        telaDeInicio.mudarPercentual();
        statement.execute(this.alterarTabela+" tblAlunos "
                +this.regra+" FK_Alunos_Servicos "
                +this.fk+"(codServico) "
                +"REFERENCES tblServicos(codServico);");
    }
    
    //Referência da coluna codProduto da tabela de Lote de Produtos em relação a coluna codProduto na tabela de Produtos.
    private void fkTblLoteDeProdutos_TblProdutos(Statement statement) throws SQLException{
        telaDeInicio.mudartexto("Referenciando tabela Lotes com Pro...");
        telaDeInicio.mudarPercentual();
        statement.execute(this.alterarTabela+" tblLoteDeProdutos "
                +this.regra+" FK_LoteDeProdutos_Produtos "
                +this.fk+"(codProduto) "
                +"REFERENCES tblProdutos(codProduto);");
    }
    
    //Referência da coluna codTurma da tabela de Matrículas em relação a coluna codTurma na tabela de Turmas.
    private void fkTblMatriculas_TblTurmas(Statement statement) throws SQLException{
        telaDeInicio.mudartexto("Referenciando tabela Matrículas com Tu...");
        telaDeInicio.mudarPercentual();
        statement.execute(this.alterarTabela+" tblMatriculas "
                +this.regra+" FK_Matriculas_Turmas "
                +this.fk+"(codTurma) "
                +"REFERENCES tblTurmas(codTurma);");
    }
    
    //Referência da coluna codAluno da tabela de Matrículas em relação a coluna codAluno na tabela de Alunos.
    private void fkTblMatriculas_TblAlunos(Statement statement) throws SQLException{
        telaDeInicio.mudartexto("Referenciando tabela Matrículas com Al...");
        telaDeInicio.mudarPercentual();
        statement.execute(this.alterarTabela+" tblMatriculas "
                +this.regra+" FK_Matriculas_Alunos "
                +this.fk+"(codAluno) "
                +"REFERENCES tblAlunos(codAluno);");
    }
    
    //Referência da coluna chaveVenda da tabela de Itens Vendidos em relação a coluna chaveVenda na tabela de Vendas.
    private void fkTblItensVendidos_TblVendas(Statement statement) throws SQLException{
        telaDeInicio.mudartexto("Referenciando tabela Itens V com Ven...");
        telaDeInicio.mudarPercentual();
        System.out.println("Início");
        statement.execute(this.alterarTabela+" tblItensVendidos "
                +this.regra+" FK_ItensVendidos_Vendas "
                +this.fk+"(chaveVenda) "
                +"REFERENCES tblVendas(chaveVenda);");
    }
    
    //Referência da coluna codProduto da tabela de Itens Vendidos em relação a coluna codProduto na tabela de Produtos.
    private void fkTblItensVendidos_TblProdutos(Statement statement) throws SQLException{
        telaDeInicio.mudartexto("Referenciando tabela Itens V com Pro...");
        telaDeInicio.mudarPercentual();
        statement.execute(this.alterarTabela+" tblItensVendidos "
                +this.regra+" FK_ItensVendidos_Produtos "
                +this.fk+"(codProduto) "
                +"REFERENCES tblProdutos(codProduto);");
    }
    
    //Referência da coluna chaveTransacao da tabela de Itens Comprados em relação a coluna chaveTransacao na tabela de Gastos.
    private void fkTblItensComprados_TblGastos(Statement statement) throws SQLException{
        telaDeInicio.mudartexto("Referenciando tabela Itens C com Gas...");
        telaDeInicio.mudarPercentual();
        statement.execute(this.alterarTabela+" tblItensComprados "
                +this.regra+" FK_ItensComprados_Gastos "
                +this.fk+"(chaveTransacao) "
                +"REFERENCES tblGastos(chaveTransacao);");
    }
    
    //Referência da coluna codAluno da tabela de Planos em relação a coluna codAluno na tabela de Alunos.
    private void fkTblPlanos_TblAlunos(Statement statement) throws SQLException{
        telaDeInicio.mudartexto("Referenciando tabela Planos com Al...");
        telaDeInicio.mudarPercentual();
        statement.execute(this.alterarTabela+" tblPlanos "
                +this.regra+" FK_Planos_Alunos "
                +this.fk+"(codAluno) "
                +"REFERENCES tblAlunos(codAluno);");
    }
    
    //Referência da coluna codTurma da tabela de Planos em relação a coluna codTurma na tabela de Turmas.
    private void fkTblPlanos_TblTurmas(Statement statement) throws SQLException{
        telaDeInicio.mudartexto("Referenciando tabela Planos com Tur...");
        telaDeInicio.mudarPercentual();
        statement.execute(this.alterarTabela+" tblPlanos "
                +this.regra+" FK_Planos_Turmas "
                +this.fk+"(codTurma) "
                +"REFERENCES tblTurmas(codTurma);");
    }
    
    //Referência da coluna codServico da tabela de Planos em relação a coluna codServico na tabela de Serviços.
    private void fkTblPlanos_TblServicos(Statement statement) throws SQLException{
        telaDeInicio.mudartexto("Referenciando tabela Planos com Ser...");
        telaDeInicio.mudarPercentual();
        statement.execute(this.alterarTabela+" tblPlanos "
                +this.regra+" FK_Planos_Servicos "
                +this.fk+"(codServico) "
                +"REFERENCES tblServicos(codServico);");
    }
    
    //Referência da coluna codFuncionario da tabela de Frequência dos Funcionários em relação a coluna codFuncionario na tabela de Funcionários.
    private void fkTblFreqFuncionarios_TblFuncionarios(Statement statement) throws SQLException{
        telaDeInicio.mudartexto("Referenciando tabela Frequência F com Fun...");
        telaDeInicio.mudarPercentual();
        statement.execute(this.alterarTabela+" tblFreqFuncionarios "
                +this.regra+" FK_FreqFuncionarios_Funcionarios "
                +this.fk+"(codFuncionario) "
                +"REFERENCES tblFuncionarios(codFuncionario);");
    }
    
    
    //Usadas quando se cria um funcionário ou uma turma
    //_____________________________________________________________________________________________________________
    //Referência da coluna codTurma da tabela de Frequência de Turmas em relação a coluna codTurma na tabela de Turmas.
    private void fkTblFreqTurmas_TblTurmas(Statement statement, int numeroDaTurma) throws SQLException{
        telaDeInicio.mudartexto("Referenciando tabela Frequência T com Tur...");
        telaDeInicio.mudarPercentual();
        statement.execute(this.alterarTabela+" tblFreqTurma"+numeroDaTurma+" "
                +this.regra+" FK_FreqTurmas_Turmas"
                +this.fk+"(codTurma)"
                +"REFERENCES tblTurmas(codTurma);");
    }
    
    //Referência da coluna codAluno da tabela de Frequência de Turmas em relação a coluna codAuno na tabela de Alunos.
    private void fkTblFreqTurmas_TblAlunos(Statement statement, int numeroDaTurma) throws SQLException{
        telaDeInicio.mudartexto("Referenciando tabela Frequência T com Al...");
        telaDeInicio.mudarPercentual();
        statement.execute(this.alterarTabela+" tblFreqTurma"+numeroDaTurma+" "
                +this.regra+" FK_FreqTurmas_Alunos"
                +this.fk+"(codAluno)"
                +"REFERENCES tblAlunos(codAluno);");
    }
    
    //Faz referência com os itens correspondentes na tabela de Frequência de Turmas, deve ser executada quando for criada uma turma.
    public void referencesFreqTurmas(Statement statement) throws SQLException{
        telaDeInicio.mudartexto("Referenciando tabela Frequência T");
        telaDeInicio.mudarPercentual();
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
    
}
