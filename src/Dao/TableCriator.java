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
public class TableCriator {
    private final Conexao conexao = new Conexao();
    private final String criarSeNaoExistir = "CREATE TABLE IF NOT EXISTS";
    
    private Statement gerarStatement() throws SQLException{
        Statement statement = conexao.getConnection().createStatement();
        return statement;
    }
    
    public void createTables(){
        
    }
    
    public void tableAlunos() throws SQLException{
        this.gerarStatement().execute(this.criarSeNaoExistir+" tblAlunos("
                + "codAluno INT PRIMARY KEY,"
                + "nome VARCHAR(50) NOT NULL,"
                + "cpf VARCHAR(15) NULL,"
                + "rg VARCHAR(20) NULL,"
                + "sexo CHAR(1) NOT NULL,"
                + "telefone VARCHAR(20) NULL,"
                + "email VARCHAR(50) NULL,"
                + "dataNascimento DATE NOT NULL,"
                + "codEndereco INT NOT NULL,"
                + "nomeMae VARCHAR(50) NULL,"
                + "nomePai VARCHAR(50) NULL,"
                + "telefoneMae VARCHAR(20) NULL,"
                + "telefonePai VARCHAR(20) NULL,"
                + "cpfMae VARCHAR(15) NULL,"
                + "cpfPai VARCHAR(15) NULL,"
                + "matriculas VARCHAR(MAX) NOT NULL,"
                + "codDiasDaSemana INT NOT NULL,"
                + "codServico INT NOT NULL,"
                + "descricao VARCHAR(MAX) NULL,"
                + "debito DECIMAL NULL"
                + ") ON [AlunoseClientes];");
    }
    
    public void tableEndAlunoseClientes() throws SQLException{
        this.gerarStatement().execute(this.criarSeNaoExistir+" tblEndAlunoseClientes("
                + "codEndAlunoseClientes INT PRIMARY KEY,"
                + "codAluno INT NOT NULL,"
                + "logradouro VARCHAR(50) NOT NULL,"
                + "bairro VARCHAR(25) NOT NULL,"
                + "numero VARCHAR(10) NOT NULL,"
                + "complemento VARCHAR(50) NULL,"
                + "referencia VARCHAR(50) NULL,"
                + "cidade VARCHAR(20) NULL,"
                + "estado CHAR(2) NULL,"
                + "CEP VARCHAR(10) NULL"
                + ") ON [AlunoseClientes];");
    }
        
    public void tableClientes() throws SQLException{
        this.gerarStatement().execute(this.criarSeNaoExistir+" tblClientes("
                + "codCliente INT PRIMARY KEY,"
                + "nome VARCHAR(50) NOT NULL,"
                + "cpf VARCHAR(15) NOT NULL,"
                + "telefone VARCHAR(20) NOT NULL,"
                + "codEndereco INT NULL,"
                + "codServico INT NULL,"
                + "diasAcordados INT NULL,"
                + "debito DECIMAL NULL"
                + ") ON [AlunoseClientes];");
    }
    
    public void tableFuncionarios() throws SQLException{
        this.gerarStatement().execute(this.criarSeNaoExistir+" tblFuncionarios("
                + "codFuncionario INT PRIMARY KEY,"
                + "nome VARCHAR(50) NOT NULL,"
                + "cpf VARCHAR(15) NOT NULL,"
                + "rg VARCHAR(20) NOT NULL,"
                + "sexo CHAR(1) NOT NULL,"
                + "telefone VARCHAR(20) NOT NULL,"
                + "email VARCHAR(50) NULL,"
                + "dataNascimento DATE NOT NULL,"
                + "codEndereco INT NOT NULL,"
                + "usuario VARCHAR(16) NOT NULL,"
                + "senha VARCHAR(10) NOT NULL,"
                + "cargo VARCHAR(25) NOT NULL,"
                + "salario DECIMAL NOT NULL"
                + ") ON [Funcionarios];");
    }
        
    public void tableEndFuncionarios() throws SQLException{
        this.gerarStatement().execute(this.criarSeNaoExistir+" tblEndFuncionarios("
                + "codEndFuncionarios INT PRIMARY KEY,"
                + "codFuncionario INT NOT NULL,"
                + "logradouro VARCHAR(50) NOT NULL,"
                + "bairro VARCHAR(25) NOT NULL,"
                + "numero VARCHAR(10) NOT NULL,"
                + "complemento VARCHAR(50) NULL,"
                + "referencia VARCHAR(50) NULL,"
                + "cidade VARCHAR(20) NULL,"
                + "estado CHAR(2) NULL,"
                + "CEP VARCHAR(10) NULL"
                + ") ON [Funcionarios];");
    }
    
    public void tableEmpresa() throws SQLException{
        this.gerarStatement().execute(this.criarSeNaoExistir+" tblEmpresa("
                + "razaoSocial VARCHAR(50) NOT NULL,"
                + "cnpj VARCHAR(20) NOT NULL,"
                + "nomeFantasia VARCHAR(50) NULL,"
                + "telefone VARCHAR(20) NULL,"
                + "email VARCHAR(50) NULL,"
                + "site VARCHAR(50) NULL,"
                + "codEndereco INT NULL"
                + ");");
    }
    
   public void tableEndEmpresa() throws SQLException{
       this.gerarStatement().execute(this.criarSeNaoExistir+" tblEndEmpresa("
                + "codEndEmpresa INT PRIMARY KEY,"
                + "logradouro VARCHAR(50) NOT NULL,"
                + "bairro VARCHAR(25) NOT NULL,"
                + "numero VARCHAR(10) NOT NULL,"
                + "complemento VARCHAR(50) NULL,"
                + "referencia VARCHAR(50) NULL,"
                + "cidade VARCHAR(20) NULL,"
                + "estado CHAR(2) NULL,"
                + "CEP VARCHAR(10) NULL"
                + ");");
   }
    
    public void tableProdutos() throws SQLException{
        this.gerarStatement().execute(this.criarSeNaoExistir+" tblProdutos("
                + "codProduto INT PRIMARY KEY,"
                + "nome VARCHAR(50) NOT NULL,"
                + "tipo VARCHAR(20) NOT NULL,"
                + "unMedida CHAR(2) NOT NULL,"
                + "quantidade FLOAT NOT NULL,"
                + "descricao VARCHAR(MAX) NULL,"
                + "valorDeCompra DECIMAL NULL,"
                + "dataDeCompra DATE NULL,"
                + "valorDeVenda DECIMAL NOT NULL,"
                + "chaveDeLote INT NULL"
                + ") ON [Produtos];");
    }
        
    public void tableLoteDeProdutos() throws SQLException{
        this.gerarStatement().execute(this.criarSeNaoExistir+" tblLoteDeProdutos("
                + "chaveDeLote INT PRIMARY KEY,"
                + "codLote VARCHAR(50) PRIMARY KEY,"
                + "dataAdicionamento DATE NOT NULL,"
                + "validadeMeses VARCHAR(8) NULL,"
                + "validadeData DATE NULL"
                + ") ON [Produtos];");
    }
        
    public void tableFornProdutos() throws SQLException{
        this.gerarStatement().execute(this.criarSeNaoExistir+" tblFornProdutos("
                + "codFornecedor INT PRIMARY KEY,"
                + "nome VARCHAR(50) NOT KEY,"
                + "cnpj VARCHAR NULL,"
                + "codProduto VARCHAR(MAX) NOT NULL"
                + ") ON [Produtos];");
    }
    
    public void tableTurmas() throws SQLException{
        this.gerarStatement().execute(this.criarSeNaoExistir+" tblTurmas("
                + "codTurma INT PRIMARY KEY,"
                + "nome VARCHAR(15) NOT NULL,"
                + "subgrupo CHAR(2) NULL,"
                + "quantAlunos INT NULL,"
                + "quantLimiteDeAlunos INT NULL,"
                + "diasDaSemana VARCHAR(MAX) NOT NULL"
                + ") ON [AlunoseClientes];");
    }
    
    public void tableHorarios() throws SQLException{
        this.gerarStatement().execute(this.criarSeNaoExistir+" tblHorarios("
                + "codHorario INT PRIMARY KEY,"
                + "diaDaSemana VARCHAR(15) NOT NULL,"
                + "horario DATE NOT NULL,"
                + "codCliente INT NULL,"
                + "codTurma INT NULL"
                + ") ON [AlunoseClientes];");
    }

    public void tableMatriculas() throws SQLException{
        this.gerarStatement().execute(this.criarSeNaoExistir+" tblMatriculas("
                + "codMatricula INT PRIMARY KEY,"
                + "codTurma INT NOT NULL,"
                + "codAluno INT NOT NULL,"
                + "anoMatricula INT NOT NULL,"
                + "matricula VARCHAR(12) NOT NULL"
                + ") ON [AlunoseClientes];");
    }
    
    public void tableServicos() throws SQLException{
        this.gerarStatement().execute(this.criarSeNaoExistir+" tblServicos("
                + "codServico INT PRIMARY KEY,"
                + "nome VARCHAR(15) NOT NULL,"
                + "periodo VARCHAR(10) NOT NULL,"
                + "valorAVista DECIMAL NULL,"
                + "valorBoleto DECIMAL NULL,"
                + "valorCartao DECIMAL NULL"
                + ") ON [AlunoseClientes];");
    }
    
    public void tableVendas() throws SQLException{
        this.gerarStatement().execute(this.criarSeNaoExistir+" tblVendas("
                + "codVenda INT PRIMARY KEY,"
                + "codCliente INT NULL,"
                + "codAluno INT NULL,"
                + "valorVenda DECIMAL NOT NULL,"
                + "valorPagoCliente DECIMAL NOT NULL,"
                + "valorTroco DECIMAL NOT NULL,"
                + "dataVenda DATE NOT NULL,"
                + "chaveVenda INT PRIMARY KEY,"
                + "formaPagamento VARCHAR(10) NOT NULL"
                + ") ON [Transacoes];");
    }
    
    public void tableItensVendidos() throws SQLException{
        this.gerarStatement().execute(this.criarSeNaoExistir+" tblItensVendidos("
                + "chaveVenda INT NOT NULL,"
                + "codProduto INT NOT NULL,"
                + "quantidade FLOAT NOT NULL,"
                + "valor DECIMAL NOT NULL,"
                + "subtotal DECIMAL NOT NULL"
                + ") ON [Transacoes];");
    }
    public void tableGastos() throws SQLException{
        this.gerarStatement().execute(this.criarSeNaoExistir+" tblGastos("
                + "codGasto INT PRIMARY KEY,"
                + "motivo VARCHAR(MAX) NOT NULL,"
                + "valorGasto DECIMAL NOT NULL,"
                + "dataGasto DATE NOT NULL,"
                + "chaveTransacao INT NOT NULL,"
                + "situacao VARCHAR(10) NOT NULL"
                + ") ON [Transacoes];");
    }
        
    public void tableItensComprados() throws SQLException{
        this.gerarStatement().execute(this.criarSeNaoExistir+" tblItensComprados("
                + "chaveCompra INT NOT NULL,"
                + "itemComprado VARCHAR(MAX) NOT NULL"
                + "quantidade FLOAT NOT NULL,"
                + "valor DECIMAL NOT NULL,"
                + "subtotal DECIMAL NOT NULL,"
                + "codProduto INT NULL,"
                + "codLote INT NULL"
                + ") ON [Transacoes];");
    }

    public void tableDetOrcamentario() throws SQLException{
        this.gerarStatement().execute(this.criarSeNaoExistir+" tblDetOrcamentario("
                + "chaveVenda INT NULL,"
                + "chaveCompra INT NULL,"
                + "chavePlano INT NULL,"
                + "data DATE NOT NULL,"
                + "valorRecebidoPago DECIMAL NULL,"
                + "valorRecebidoPendente DECIMAL NULL"
                + "valorGastoPago DECIMAL NULL,"
                + "valorGastoPendente DECIMAL NULL,"
                + "chaveOrcamentaria INT PRIMARY KEY"
                + ") ON [Orcamento];");
    }
    
    public void tablePlanos() throws SQLException{
        this.gerarStatement().execute(this.criarSeNaoExistir+" tblPlanos("
                + "codAluno INT NOT NULL,"
                + "codTurma INT NOT NULL,"
                + "codServico INT NOT NULL,"
                + "dataPagamento DATE NULL,"
                + "dataVencimento DATE NULL,"
                + "dataCancelamento DATE NULL,"
                + "situacao VARCHAR(10) NOT NULL,"
                + "chavePlano INT PRIMARY KEY"
                + ") ON [AlunoseClientes];");
    }

    public void tableBackups() throws SQLException{
        this.gerarStatement().execute(this.criarSeNaoExistir+" tblBackups("
                + "codBackup INT PRIMARY KEY,"
                + "nome VARCHAR(20) NOT NULL,"
                + "data DATE NOT NULL,"
                + "endArquivo VARCHAR(MAX) NOT NULL,"
                + "tabelas VARCHAR(MAX) NOT NULL"
                + ");");
    }
    
    public void tableLogdeAcoesdoFunc() throws SQLException{
        int quantidadeFuncionarios = this.quantFuncionarios();
        while(quantidadeFuncionarios>0){
            this.gerarStatement().execute(this.criarSeNaoExistir+" tblLogdeAcoesdoFun"+(this.quantFuncionarios() - quantidadeFuncionarios+1)+"("
                + "codFuncionario INT NOT NULL,"
                + "data DATE NOT NULL,"
                + "acao VARCHAR(MAX) NOT NULL,"
                + "descricao VARCHAR(MAX) NULL"
                + ")ON [Funcionarios];");
            
            quantidadeFuncionarios--;
        }
    }
    
    public void tableFreqTurmas() throws SQLException{
        int quantidadeTurmas = this.quantTurmas();
        while(quantidadeTurmas>0){
            this.gerarStatement().execute(this.criarSeNaoExistir+" tblFreqTurma"+(this.quantTurmas() - quantidadeTurmas+1)+"("
                + "codTurma INT NOT NULL,"
                + "codAluno INT NOT NULL,"
                + "data DATE NOT NULL,"
                + "situacao CHAR(1) NOT NULL"
                + ")ON [AlunoseClientes];");
            
            quantidadeTurmas--;
        }
    }
    
    public void tableFreqFuncionarios() throws SQLException{
            this.gerarStatement().execute(this.criarSeNaoExistir+" tblFreqFuncionarios("
                + "data DATE NOT NULL,"
                + "codFuncionario INT NOT NULL,"
                + "situacao CHAR(1) NOT NULL"
                + ")ON [Funcionarios];");

    }
    
    
    
    private int quantTurmas() throws SQLException{
        ResultSet count = this.gerarStatement().executeQuery("SELECT COUNT(codTurma) AS quant FROM tblTurmas;");
        int contador = 0;
        while(count.next()){
            contador = count.getInt("quant");
        }
        
        return contador;
    }
    
    private int quantFuncionarios() throws SQLException{
        ResultSet count = this.gerarStatement().executeQuery("SELECT COUNT(codFuncionario) AS quant FROM tblFuncionarios;");
        int contador = 0;
        while(count.next()){
            contador = count.getInt("quant");
        }
        
        return contador;
    }

}
