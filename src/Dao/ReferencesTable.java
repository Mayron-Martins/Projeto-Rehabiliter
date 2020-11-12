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
    
}
