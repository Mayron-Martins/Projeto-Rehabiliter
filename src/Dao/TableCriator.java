/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Controller.auxiliar.LogsSystem;
import View.inicio;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Mayro
 */
public class TableCriator {
    private final Conexao conexao = new Conexao() {};
    private final inicio telaDeInicio;

    public TableCriator(inicio view) {
        this.telaDeInicio = view;
    }
    
    
    
    //Criação do statement para execução de comando no banco.
    private Statement gerarStatement() throws SQLException{
        Statement statement = conexao.getConnection().createStatement();
        return statement;
    }
    
    
    //Criação de todas as tabelas seguindo a ordem em que estão apresentadas abaixo.
    public void createTables(){
        try{
            this.tableAlunos();
            this.tableEndAlunoseClientes();
            this.tableClientes();
            this.tableFuncionarios();
            this.tableEmpresa();
            this.tableEndEmpresa();
            this.tableProdutos();
            this.tableLoteDeProdutos();
            this.tableFornProdutos();
            this.tableTurmas();
            this.tableHorarios();
            this.tableMatriculas();
            this.tableServicos();
            this.tableVendas();
            this.tableItensVendidos();
            this.tableEntradas();
            this.tableGastos();
            this.tableItensComprados();
            this.tableDetOrcamentario();
            this.tablePlanos();
            this.tableBackups();
            this.tableFreqFuncionarios();
            this.tableReposicaoAulas();
        } catch (SQLException ex) {
            gerarLog(ex);
        }
        
        
    }
    
    //Criação da tabela de alunos.
    private void tableAlunos() throws SQLException{
        telaDeInicio.mudartexto("Criando tabela de Alunos...");
        telaDeInicio.mudarPercentual();
        this.gerarStatement().execute("CREATE TABLE tblAlunos("
                + "codAluno INT IDENTITY(1,1) PRIMARY KEY,"   //É gerado um código único no banco para cada aluno.
                + "nome VARCHAR(MAX) NOT NULL,"
                + "cpf VARCHAR(15) NULL,"
                + "rg VARCHAR(20) NULL,"
                + "telefone VARCHAR(20) NULL,"
                + "celular VARCHAR(20) NULL,"
                + "email VARCHAR(50) NULL,"
                + "dataNascimento DATE NOT NULL,"
                + "nomeMae VARCHAR(MAX) NULL,"
                + "nomePai VARCHAR(MAX) NULL,"
                + "telefoneMae VARCHAR(20) NULL,"
                + "telefonePai VARCHAR(20) NULL,"
                + "cpfMae VARCHAR(15) NULL,"
                + "cpfPai VARCHAR(15) NULL,"
                + "codTurma INT NOT NULL,"   //Como cada aluno pode ter mais de uma matrícula, então elas são armazenadas sequencialmente num varhar.
                + "codServico INT NOT NULL,"  //Código do serviço e tempo para pagamento.
                + "descricao VARCHAR(MAX) NULL,"
                + "debito DECIMAL(16,2) NULL,"
                + "valorContrato DECIMAL(16,2) NOT NULL,"
                + "dataCadastro Date NULL,"
                + "valorMensal DECIMAL(16,2) NULL,"
                + "renovacaoAutomatica INT NULL"  //Valor incrementado ou decrementado conforme o aluno faz compras.
                + ") ON [AlunoseClientes];");
    }
    
    //Criação da tabela de endereço do aluno.
    private void tableEndAlunoseClientes() throws SQLException{
        telaDeInicio.mudartexto("Criando tabela de Endereços AC...");
        telaDeInicio.mudarPercentual();
        this.gerarStatement().execute("CREATE TABLE tblEndAlunoseClientes("
                + "codEndAlunoseClientes INT IDENTITY(1,1) PRIMARY KEY," //É gerado um endereço único para cada aluno.
                + "codAluno INT NOT NULL," //Abstrai o código de um aluno para que exista.
                + "logradouro VARCHAR(MAX) NULL,"
                + "bairro VARCHAR(MAX) NULL,"
                + "numero VARCHAR(10) NULL,"
                + "complemento VARCHAR(50) NULL,"
                + "referencia VARCHAR(50) NULL,"
                + "cidade VARCHAR(MAX) NULL,"
                + "estado CHAR(2) NULL,"
                + "CEP VARCHAR(10) NULL"
                + ") ON [AlunoseClientes];");
    }
     
    //Criação da tabela de Clientes (que não são alunos, mas que desejam pagar por algum plano, geralmente diária).
    private void tableClientes() throws SQLException{
        telaDeInicio.mudartexto("Criando tabela de Clientes...");
        telaDeInicio.mudarPercentual();
        this.gerarStatement().execute("CREATE TABLE tblClientes("
                + "codCliente INT IDENTITY(1,1) PRIMARY KEY,"  //Cada cliente tem um código único.
                + "nome VARCHAR(50) NOT NULL,"
                + "cpf VARCHAR(15) NOT NULL,"
                + "telefone VARCHAR(20) NOT NULL,"
                + "codEndereco INT NULL,"
                + "codServico INT NULL," //Código do tipo de serviço e período de pagamento (diária).
                + "diasAcordados INT NULL,"  //Dias que foram agendados para uso dos serviços.
                + "debito DECIMAL(16,2) NULL"
                + ") ON [AlunoseClientes];");
    }
    
    //Criação da tabela de funcionários.
    private void tableFuncionarios() throws SQLException{
        telaDeInicio.mudartexto("Criando tabela de Funcionários...");
        telaDeInicio.mudarPercentual();
        this.gerarStatement().execute("CREATE TABLE tblFuncionarios("
                + "codFuncionario INT IDENTITY(1,1) PRIMARY KEY," //Cada funcionário terá um código único.
                + "nome VARCHAR(MAX) NOT NULL,"
                + "cpf VARCHAR(15) NOT NULL,"
                + "rg VARCHAR(20) NULL,"
                + "telefone VARCHAR(20) NULL,"
                + "celular VARCHAR(20) NULL,"
                + "email VARCHAR(MAX) NULL,"
                + "dataNascimento DATE NOT NULL,"
                + "usuario VARCHAR(16) NOT NULL," // Cada funcionário terá um usuário único
                + "senha VARCHAR(10) NOT NULL,"
                + "cargo VARCHAR(25) NOT NULL,"
                + "salario DECIMAL(16,2) NOT NULL,"
                + "telasPermitidas VARCHAR(20) NULL,"
                + "status VARCHAR(10) NULL,"
                + "situacao VARCHAR(15) NULL"
                + ") ON [Funcionarios];");
    }
    
    //Criação da tabela da empresa, para cadastro no banco dos seus dados.
    private void tableEmpresa() throws SQLException{
        telaDeInicio.mudartexto("Criando tabela da Empresa...");
        telaDeInicio.mudarPercentual();
        this.gerarStatement().execute("CREATE TABLE tblEmpresa("
                + "razaoSocial VARCHAR(50) NOT NULL," //Nome da empresa.
                + "cnpj VARCHAR(20) NOT NULL,"
                + "nomeFantasia VARCHAR(50) NULL," //Outro nome cadastrado no governo.
                + "telefone VARCHAR(20) NULL,"
                + "email VARCHAR(50) NULL,"
                + "site VARCHAR(50) NULL,"
                + "codEndereco INT NULL" //Código do endereço da loja.
                + ");");
    }
   
   //Criação da tabela de endereço da empresa, pode ter uso futuro caso haja filiais
   private void tableEndEmpresa() throws SQLException{
       telaDeInicio.mudartexto("Criando tabela de Endereço E...");
       telaDeInicio.mudarPercentual();
       this.gerarStatement().execute("CREATE TABLE tblEndEmpresa("
                + "codEndEmpresa INT IDENTITY(1,1) PRIMARY KEY," //Código do endereço.
                + "logradouro VARCHAR(50) NULL,"
                + "bairro VARCHAR(25) NULL,"
                + "numero VARCHAR(10) NULL,"
                + "complemento VARCHAR(50) NULL,"
                + "referencia VARCHAR(50) NULL,"
                + "cidade VARCHAR(20) NULL,"
                + "estado CHAR(2) NULL,"
                + "CEP VARCHAR(10) NULL"
                + ");");
   }
    
    //Criação da tabela de produtos
    private void tableProdutos() throws SQLException{
        telaDeInicio.mudartexto("Criando tabela de Produtos...");
        telaDeInicio.mudarPercentual();
        this.gerarStatement().execute("CREATE TABLE tblProdutos("
                + "codProduto INT IDENTITY(1,1) PRIMARY KEY," //Cada produto terá um código único no banco
                + "nome VARCHAR(MAX) NOT NULL," 
                + "tipo VARCHAR(20) NULL," //Caso queira classificá-los para melhor filtragem (ex.: roupas, acessórios, etc.)
                + "unMedida VARCHAR(3) NULL," //Caso queira definir uma unidade de medida (ex.: UN, KG, PAC).
                + "quantidade FLOAT NOT NULL,"
                + "descricao VARCHAR(MAX) NULL,"
                + "valorDeCompra DECIMAL(16,2) NULL," //Caso queira definir por qual valor o produto foi comprado, e então definir o lucro.
                + "dataDeCompra DATE NULL," //data que o produto foi comprado do fornecedor.
                + "valorDeVenda DECIMAL(16,2) NOT NULL,"
                + "chaveDeLote INT NULL" //Utiliza a chave única de um lote cadastrado no banco e em uma tabela à parte.
                + ") ON [Produtos];");
    }
    
    //Criação da tabela de lote do produtos, caso queira cadastrar lotes de produtos (facilita para troca ou definição de validade)
    private void tableLoteDeProdutos() throws SQLException{
        telaDeInicio.mudartexto("Criando tabela de Lotes...");
        telaDeInicio.mudarPercentual();
        this.gerarStatement().execute("CREATE TABLE tblLoteDeProdutos("
                + "chaveDeLote BIGINT PRIMARY KEY," //Código único gerado a partir de uma função data-tipo-codProduto.
                + "codLote VARCHAR(50) NOT NULL," //Código físico do lote, apresentado no rótulo das caixas.
                + "dataAdicionamento DATE NOT NULL,"
                + "validadeMeses VARCHAR(8) NULL," //Caso o produto possua validade e essa seja em meses.
                + "validadeData DATE NULL,"
                + "codProduto INT NOT NULL" //Caso o produto apresente validade e essa seja em uma data fixada.
                + ") ON [Produtos];");
    }
    
    //Criação da tabela de fornecedores de produtos, caso queira cadastrá-los.
    private void tableFornProdutos() throws SQLException{
        telaDeInicio.mudartexto("Criando tabela de Fornecedores...");
        telaDeInicio.mudarPercentual();
        this.gerarStatement().execute("CREATE TABLE tblFornProdutos("
                + "codFornecedor INT IDENTITY(1,1) PRIMARY KEY," //Cada fornecedor tem um código único.
                + "nome VARCHAR(50) NOT NULL,"
                + "cnpj VARCHAR NULL,"
                + "codProduto VARCHAR(MAX) NOT NULL" //Produtos aos quais o fornecedor é associado, havendo a possibilidade de diversos fornecedores fornecerem um mesmo produto.
                + ") ON [Produtos];");
    }
    
    //Criação da tabela de turmas.
    private void tableTurmas() throws SQLException{
        telaDeInicio.mudartexto("Criando tabela de Turmas...");
        telaDeInicio.mudarPercentual();
        this.gerarStatement().execute("CREATE TABLE tblTurmas("
                + "codTurma INT IDENTITY(1,1) PRIMARY KEY," //Código único para cada turma.
                + "nome VARCHAR(MAX) NOT NULL,"
                + "quantAlunos INT NULL," //Caso queira colocar a quantidade presente de alunos.
                + "quantLimiteDeAlunos INT NULL," //Caso queira definir um valor máximo de alunos para uma turma.
                + "diasDaSemana VARCHAR(MAX) NOT NULL,"
                + "horario VARCHAR(10) NOT NULL,"
                + "situacao VARCHAR(15) NULL" //Dias da semana que a turma irá utilizar.
                + ") ON [AlunoseClientes];");
    }
    
    //Criação da tabela de horários, cada dia da semana que uma turma irá utilizar deverá ter um horário.
    private void tableHorarios() throws SQLException{
        telaDeInicio.mudartexto("Criando tabela de Horários...");
        telaDeInicio.mudarPercentual();
        this.gerarStatement().execute("CREATE TABLE tblHorarios("
                + "codHorario INT NOT NULL," //Código único para cada horário.
                + "diaDaSemana VARCHAR(15) NOT NULL,"
                + "indiceSemana INT NULL," //Dia da semana correspondente ao horário.
                + "horarioInicio VARCHAR(5) NOT NULL,"
                + "horarioFim VARCHAR(5) NULL," //hora.
                + "codCliente INT NULL," //Caso um cliente tenha agendado um horário.
                + "codTurma INT NULL" //Caso a turma utilize o horário.
                + ") ON [AlunoseClientes];");
    }
    
    //Criação da tabela de matrículas.
    private void tableMatriculas() throws SQLException{
        telaDeInicio.mudartexto("Criando tabela de Matrículas...");
        telaDeInicio.mudarPercentual();
        this.gerarStatement().execute("CREATE TABLE tblMatriculas("
                + "codMatricula INT PRIMARY KEY," //Código único para cada matrícula.
                + "codTurma INT NOT NULL," //A turma a qual a matrícula é relacionada.
                + "codAluno INT NOT NULL," //O aluno ao qual a matrícula é relacionada.
                + "anoMatricula INT NOT NULL," //O ano da matrícula.
                + "matricula VARCHAR(MAX) NOT NULL" //String gerada por função que utiliza código da turma, código do aluno e ano.
                + ") ON [AlunoseClientes];");
    }
    
    //Criação da tabela de serviços.
    private void tableServicos() throws SQLException{
        telaDeInicio.mudartexto("Criando tabela de Serviços...");
        telaDeInicio.mudarPercentual();
        this.gerarStatement().execute("CREATE TABLE tblServicos("
                + "codServico INT IDENTITY(1,1) PRIMARY KEY," //Cada serviço terá um código único no banco.
                + "nome VARCHAR(MAX) NOT NULL," //Nome do Serviço (natação, hidroginástica, etc.).
                + "periodo VARCHAR(10) NOT NULL,"
                + "formaPagamento VARCHAR(25) NOT NULL,"
                + "valor DECIMAL(16, 2) NULL," 
                + "valorBoleto DECIMAL(16,2) NULL,"
                + "valorCartaoDeCredito DECIMAL(16,2) NULL,"
                + "valorCartaoDeDebito DECIMAL(16,2) NULL,"
                + "periodDays INT NULL,"
                + "situacao VARCHAR(15) NULL"
                + ") ON [AlunoseClientes];");
    }
    
    //Criação da tabela de vendas.
    private void tableVendas() throws SQLException{
        telaDeInicio.mudartexto("Criando tabela de Vendas...");
        telaDeInicio.mudarPercentual();
        this.gerarStatement().execute("CREATE TABLE tblVendas("
                + "codVenda INT IDENTITY(1,1) NOT NULL," //Cada venda terá um código único.
                + "codCliente INT NULL," //Caso a venda ocorra com um cliente.
                + "codAluno INT NULL,"  //Caso a venda ocorra com um aluno.
                + "chavePlano BIGINT NULL," //Caso a venda seja pagamento de plano
                + "valorVenda DECIMAL(16,2) NOT NULL," //Valor total da venda.
                + "valorPagoCliente DECIMAL(16,2) NOT NULL," //Valor pago pelo cliente.
                + "valorTroco DECIMAL(16,2) NOT NULL," //Troco da venda.
                + "dataVenda DATE NOT NULL,"
                + "chaveVenda BIGINT PRIMARY KEY," //Cada venda gera uma chave única do tipo data-hora codificada, utilizada para identificação de produtos vendidos.
                + "formaPagamento VARCHAR(10) NOT NULL,"
                + "plano VARCHAR(2) NULL,"
                + "parcelas INT NULL" //Dinheiro ou cartão.
                + ") ON [Transacoes];");
    }
    
    //Criação da tabela de itens vendidos durante uma venda.
    private void tableItensVendidos() throws SQLException{
        telaDeInicio.mudartexto("Criando tabela de Itens V...");
        telaDeInicio.mudarPercentual();
        this.gerarStatement().execute("CREATE TABLE tblItensVendidos("
                + "chaveVenda BIGINT NOT NULL," //Associação do item à chave gerada.
                + "codProduto INT NOT NULL," //Código do produto vendido.
                + "quantidade FLOAT NOT NULL,"
                + "valor DECIMAL(16,2) NOT NULL,"
                + "subtotal DECIMAL(16,2) NOT NULL"
                + ") ON [Transacoes];");
    }
    
    //Criação da tabela de Entradas
    private void tableEntradas() throws SQLException{
        telaDeInicio.mudartexto("Criando tabela de Entradas...");
        telaDeInicio.mudarPercentual();
        this.gerarStatement().execute("CREATE TABLE tblEntradas("
                + "codEntrada INT IDENTITY(1,1) PRIMARY KEY," //Cada Entrada terá um código único.
                + "chavePlano BIGINT NULL,"
                + "referencia VARCHAR(MAX) NOT NULL,"
                + "quantidade FLOAT NOT NULL,"
                + "formaPagamento VARCHAR(10) NOT NULL,"
                + "valorEntrada DECIMAL(16,2) NOT NULL,"
                + "dataCadastro DATE NOT NULL,"
                + "status VARCHAR(10) NULL" 
                + ") ON [Transacoes];");
    }
    
    //Criação da tabela de gastos.
    private void tableGastos() throws SQLException{
        telaDeInicio.mudartexto("Criando tabela de Gastos...");
        telaDeInicio.mudarPercentual();
        this.gerarStatement().execute("CREATE TABLE tblGastos("
                + "codGasto INT IDENTITY(1,1) NOT NULL," //Cada gasto terá um código único.
                + "motivo VARCHAR(MAX) NOT NULL,"
                + "quantidade FLOAT NOT NULL,"
                + "formaPagamento VARCHAR(10) NOT NULL,"
                + "valorGasto DECIMAL(16,2) NOT NULL,"
                + "dataGasto DATE NOT NULL,"
                + "status VARCHAR(10) NULL,"
                + "chaveTransacao BIGINT PRIMARY KEY" //Chave gerada por meio de função data-hora codificada, utilizada para idenficação de itens comprados.
                + ") ON [Transacoes];");
    }
     
    //Criação da tabela de itens comprados (vide gastos).
    private void tableItensComprados() throws SQLException{
        telaDeInicio.mudartexto("Criando tabela de Itens C...");
        telaDeInicio.mudarPercentual();
        this.gerarStatement().execute("CREATE TABLE tblItensComprados("
                + "chaveTransacao BIGINT NOT NULL," //Associação do produto ou item à chave de transação.
                + "itemComprado VARCHAR(MAX) NOT NULL," //Item que foi comprado ou pago.
                + "quantidade FLOAT NOT NULL,"
                + "valor DECIMAL(16,2) NOT NULL,"
                + "subtotal DECIMAL(16,2) NOT NULL,"
                + "codProduto INT NULL," //O item comprado pode ser associado a um produto já cadastrado.
                + "codLote INT NULL" //Caso o produto esteja no banco e queira cadastrar o lote correspondente.
                + ") ON [Transacoes];");
    }
    
    //Criação da tabela Detalhamento Orçamentario
    private void tableDetOrcamentario() throws SQLException{
        telaDeInicio.mudartexto("Criando tabela de Orçamentário...");
        telaDeInicio.mudarPercentual();
        this.gerarStatement().execute("CREATE TABLE tblDetOrcamentario("
                + "codBanco INT IDENTITY(1,1) PRIMARY KEY,"
                + "tipo VARCHAR(10) NOT NULL," //Item que foi comprado ou pago.
                + "formaPagamento VARCHAR(10) NOT NULL,"
                + "valor DECIMAL(16,2) NOT NULL,"
                + "dataCadastro DATE NOT NULL,"
                + "chave BIGINT NOT NULL"
                + ") ON [Transacoes];");
    }
    
    //Criação da tabela de Planos (cada aluno terá um plano específico)
    private void tablePlanos() throws SQLException{
        telaDeInicio.mudartexto("Criando tabela de Planos...");
        telaDeInicio.mudarPercentual();
        this.gerarStatement().execute("CREATE TABLE tblPlanos("
                + "codAluno INT NOT NULL," //Código do aluno ao qual o plano será associado.
                + "codTurma INT NOT NULL," //Código da turma ao qual o plano será associado.
                + "codServico INT NOT NULL,"
                + "dataCadastro DATE NULL," //Código do serviço ao qual o plano será associado.
                + "dataPagamento DATE NULL," //Caso o plano tenha sido pago.
                + "diaVencimento INT NULL,"
                + "dataVencimento DATE NULL," //Data escolhida para vencimento do plano.
                + "dataCancelamento DATE NULL,"
                + "dataRenovacao DATE NULL," //Caso tenha sido cancelado, então a data em que ocorreu.
                + "situacao VARCHAR(10) NOT NULL," //Pago, pendente, vencido, cancelado.
                + "chavePlano BIGINT IDENTITY(1,1) PRIMARY KEY" //Cada plano terá uma chave única gerada por meio de função codificada aluno-turma-serviço.
                + ") ON [AlunoseClientes];");
    }
    
    //Criação da tabela de Backups, para armazenamento do histórico.
    private void tableBackups() throws SQLException{
        telaDeInicio.mudartexto("Criando tabela de Backups...");
        telaDeInicio.mudarPercentual();
        this.gerarStatement().execute("CREATE TABLE tblBackups("
                + "codBackup INT IDENTITY(1,1) PRIMARY KEY," //Cada backup terá um código único.
                + "nome VARCHAR(20) NOT NULL," //Data e horário como nome
                + "data DATETIME NOT NULL," //
                + "endArquivo VARCHAR(MAX) NOT NULL," //Localização no computador.
                + "tabelas VARCHAR(MAX) NOT NULL" //String com as tabelas presentes no backup, sequencialmente.
                + ");");
    }
    
    //Criação da tabela de frequÊncia dos funcionários
    private void tableFreqFuncionarios() throws SQLException{
        telaDeInicio.mudartexto("Criando tabela de Frequência F...");
        telaDeInicio.mudarPercentual();    
        this.gerarStatement().execute("CREATE TABLE tblFreqFuncionarios("
                + "data DATE NOT NULL," //Data em que ocorreu a entrada no sistema.
                + "codFuncionario INT NOT NULL," //Associa a frequência ao código do funcionário.
                + "situacao CHAR(1) NOT NULL" //P-presente (quando loga) ou A-ausente (quando deixa de logar o dia todo)
                + ")ON [Funcionarios];");
    }
    
    //Criação da tabela de Reposição de Aulas
    private void tableReposicaoAulas() throws SQLException{
        telaDeInicio.mudartexto("Criando tabela de Reposição de Aulas");
        telaDeInicio.mudarPercentual();    
        this.gerarStatement().execute("CREATE TABLE tblReposicaoAulas("
                + "codBanco INT IDENTITY(1,1) PRIMARY KEY,"
                + "data DATE NOT NULL," //Data em que ocorreu a entrada no sistema.
                + "codTurma INT NOT NULL,"
                + "codAluno INT NOT NULL," //Associa a frequência ao código do funcionário.
                + "situacao CHAR(2) NOT NULL" //Pr-presente, Au-ausente ou [P - Pendente.
                + ")ON [AlunoseClientes];");
    }
    
    //Gerar arquivo com o log de erro, caso haja
    private void gerarLog(Throwable erro){
        LogsSystem gerarLog = new LogsSystem();
        gerarLog.gravarErro(erro);
        gerarLog.close();
    }

}
