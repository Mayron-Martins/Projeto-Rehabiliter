/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.auxiliar.ConversaoDeDinheiro;
import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Dao.AlunosDao;
import Dao.EnderecoAlunosDao;
import Dao.FuncionarioDao;
import Dao.LogAçoesFuncionarioDao;
import Dao.MatriculasDao;
import Dao.PlanosDao;
import Dao.ServicosDao;
import Dao.TurmasDao;
import Model.Aluno;
import Model.Funcionario;
import Model.auxiliar.EnderecoAlunos;
import Model.auxiliar.LogAçoesFuncionario;
import Model.auxiliar.Matriculas;
import Model.auxiliar.Planos;
import Model.auxiliar.Servicos;
import Model.auxiliar.Turmas;
import View.AlunosView;
import java.awt.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mayro
 */
public class AlunosController {
    private final AlunosView view;
    private final DefaultTableModel tabelaDeAlunos;
    private final DefaultTableModel tabelaDePais;
    private final DefaultTableModel tabelaDeEnderecos;
    private final DefaultTableModel tabelaDePlanos;
    private final AlunosDao alunosDao = new AlunosDao();
    private final MatriculasDao matriculasDao = new MatriculasDao();
    private final TurmasDao turmasDao = new TurmasDao();
    private final ServicosDao servicosDao = new ServicosDao();
    private final EnderecoAlunosDao enderecoDao = new EnderecoAlunosDao();
    private final PlanosDao planosDao = new PlanosDao();
    private final FuncionarioDao funcionarioDao = new FuncionarioDao();
    private final LogAçoesFuncionarioDao logDao = new LogAçoesFuncionarioDao();
    private final ConversaoDeDinheiro converterDinheiro = new ConversaoDeDinheiro();
    private final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();

    public AlunosController(AlunosView view) {
        this.view = view;
        this.tabelaDeAlunos = (DefaultTableModel)view.getTabelaAlunos().getModel();
        this.tabelaDePais = (DefaultTableModel)view.getTabelaPais().getModel();
        this.tabelaDeEnderecos = (DefaultTableModel)view.getTabelaEnderecos().getModel();
        this.tabelaDePlanos = (DefaultTableModel) view.getTabelaPlanos().getModel();
    }
    
    //Limpar tabela
    public void limparTabela(){
        int quantLinhas = this.view.getTabelaAlunos().getRowCount();
        for(int quant=0; quant<quantLinhas; quant++){
            this.tabelaDeAlunos.removeRow(0);
            this.tabelaDePlanos.removeRow(0);
            this.tabelaDePais.removeRow(0);
            this.tabelaDeEnderecos.removeRow(0);
        }
    }
    
    
    //Lista todas as turmas 
    public void listarAlunos() throws SQLException, ParseException, Exception {
        if(view.getComboTurmasExistentes().getSelectedIndex()>=0){
        String nomeTurmaAtual = view.getComboTurmasExistentes().getSelectedItem().toString();
        int codTurmaAtual = Integer.parseInt(nomeTurmaAtual.split("\\.")[0]);
        
        ArrayList <Aluno> alunos = this.alunosDao.pesquisarAlunos("SELECT * FROM tblAlunos WHERE codTurma = "+codTurmaAtual);
        this.buscas(alunos);   
        }
    }
    
    public void editarAlunos() throws SQLException, ParseException, Exception{
        if(this.view.getTabelaAlunos().getSelectedRow()!= -1){
            //Dados Alunos
            int linhaSelecionada = this.view.getTabelaAlunos().getSelectedRow();
            int codAluno = Integer.parseInt(tabelaDeAlunos.getValueAt(linhaSelecionada, 0).toString());
            String nome = this.tabelaDeAlunos.getValueAt(linhaSelecionada, 1).toString();
            String nomeTurmaAtual = view.getComboTurmas().getSelectedItem().toString();
            int codTurmaAtual = Integer.parseInt(nomeTurmaAtual.split("\\.")[0]);
            BigDecimal valorDebito = new BigDecimal(converterDinheiro.converterParaBigDecimal(this.tabelaDeAlunos.getValueAt(linhaSelecionada, 3).toString()).toString());
            int renovacaoAutomatica = 0;
            if(tabelaDeAlunos.getValueAt(linhaSelecionada, 4).toString().equals("true")){
                renovacaoAutomatica = 1;
            }
            
            //Dados Planos
            int chavePlano = Integer.parseInt(this.tabelaDePlanos.getValueAt(linhaSelecionada, 0).toString());
            String nomeServico = view.getComboServicos().getSelectedItem().toString();
            int codServico = Integer.parseInt(nomeServico.split("\\.")[0]);
            BigDecimal valorContrato = new BigDecimal(converterDinheiro.converterParaBigDecimal(this.tabelaDePlanos.getValueAt(linhaSelecionada, 2).toString()).toString());
            BigDecimal valorMensal = new BigDecimal(converterDinheiro.converterParaBigDecimal(this.tabelaDePlanos.getValueAt(linhaSelecionada, 3).toString()).toString());
            int diaVencimento = Integer.parseInt(this.tabelaDePlanos.getValueAt(linhaSelecionada, 4).toString());
            String situacao = this.tabelaDePlanos.getValueAt(linhaSelecionada, 5).toString();
            
            
            
            //Dados Pais
            String nomePai = this.tabelaDePais.getValueAt(linhaSelecionada, 0).toString();
            String cpfPai = this.tabelaDePais.getValueAt(linhaSelecionada, 1).toString();
            String telefonePai = this.tabelaDePais.getValueAt(linhaSelecionada, 2).toString();
            String nomeMae = this.tabelaDePais.getValueAt(linhaSelecionada, 3).toString();
            String cpfMae = this.tabelaDePais.getValueAt(linhaSelecionada, 4).toString();
            String telefoneMae = this.tabelaDePais.getValueAt(linhaSelecionada, 5).toString();
            
            //Dados Endereço
            String logradouro = this.tabelaDeEnderecos.getValueAt(linhaSelecionada, 0).toString();
            String numero = this.tabelaDeEnderecos.getValueAt(linhaSelecionada, 1).toString();
            String bairro = this.tabelaDeEnderecos.getValueAt(linhaSelecionada, 2).toString();
            String cidade = this.tabelaDeEnderecos.getValueAt(linhaSelecionada, 3).toString();
            String estado = view.getComboEstado().getSelectedItem().toString();
            String cep = this.tabelaDeEnderecos.getValueAt(linhaSelecionada, 5).toString();
            
            //Dados matrícula
            int anoAtual = converterData.obterAnoAtual();
            String nomeMatricula = this.converterMatricula(anoAtual, codTurmaAtual, codAluno, codServico);
            Aluno alunoAnterior = this.alunoAnterior(codAluno);
            Planos planoAnterior = this.planoAnterior(codAluno);
            
            Date dataValidade = planoAnterior.getDataVencimento();
            if(view.getCampoDataVencimento().getDate()!= null){
                dataValidade = view.getCampoDataVencimento().getDate();
            }
            
            Date dataPagamento = planoAnterior.getDataPagamento();
            if(view.getCampoDataPagamento().getDate() != null){
                dataPagamento = view.getCampoDataPagamento().getDate();
            }

            Date dataCadastro = alunoAnterior.getDataCadastro();
            if(view.getCampoDataCadastro().getDate() != null){
                dataCadastro = view.getCampoDataCadastro().getDate();

            }

            
            Aluno aluno = new Aluno(codAluno, nome, alunoAnterior.getCpf(), alunoAnterior.getRg(), alunoAnterior.getTelefone(), 
                    alunoAnterior.getCelular(), alunoAnterior.getEmail(), alunoAnterior.getDatadenascimento(), 
                    nomeMae, nomePai, telefoneMae, telefonePai, cpfMae, cpfPai, codTurmaAtual, codServico, alunoAnterior.getDescricao(), 
                    valorDebito, valorContrato, dataCadastro, valorMensal, renovacaoAutomatica);
            
            Matriculas matricula = new Matriculas(codAluno, codTurmaAtual, codAluno, anoAtual, nomeMatricula);
            EnderecoAlunos endereco = new EnderecoAlunos(codAluno, codAluno, logradouro, bairro, numero, nomeMae, telefoneMae, cidade, estado, cep);
            Planos planoAluno = new Planos(codAluno, codTurmaAtual, codServico, diaVencimento, 
                    dataValidade, dataPagamento, planoAnterior.getDataCancelamento(), 
                    planoAnterior.getDataRenovacao(), situacao);
            
            
            //Verificar se a turma atual possui vagas
            Turmas turmaAnterior = this.pegarTurma(alunoAnterior.getTurma());
            Turmas turmaAtual = this.pegarTurma(codTurmaAtual);
            boolean verificarVagasTurma = false;
            
            if(turmaAnterior.getCodBanco()!=turmaAtual.getCodBanco()){
                verificarVagasTurma = this.verificarQuantidadeLimiteAlunos(codTurmaAtual);
            }
            
        //Inserindo Dados
        if(verificarVagasTurma==true){
            view.exibeMensagem("Quantidade de Vagas Limite para a Turma Atingida!");
            listarAlunos();
        }
        else{
            if(nome.equals("")|| logradouro.equals("") || numero.equals("")|| bairro.equals("")|| cidade.equals("")||
        estado.equals("[Nenhum]")|| cep.equals("  .   -   ")){
         view.exibeMensagem("Campos Preenchidos Incorretamente");
        } else{
            alunosDao.atualizarDados(aluno, endereco, matricula, planoAluno, anoAtual);
            turmasDao.atualizarQuantAunos(turmaAnterior.getCodBanco(), (turmaAnterior.getQuantidadeAlunos()-1));
            turmasDao.atualizarQuantAunos(turmaAtual.getCodBanco(), (turmaAtual.getQuantidadeAlunos()+1));
            
            ArrayList <Funcionario> funcionarios = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE status = 'Ativo'");
            if(funcionarios!=null){
                String acao = "Edição de Dados de Aluno";
                String descricao = "Editou os dados do aluno "+nome;
                this.setarLog(funcionarios, acao, descricao);
            }
            view.exibeMensagem("Sucesso!");
            //Limpando Campos
            listarAlunos();
        }
        }
        }
        
        else{this.view.exibeMensagem("Erro, Nenhum Aluno Selecionado!");}
        
    
    }
    
    
    public void removerAluno() throws SQLException, ParseException, Exception{
        if(this.view.getTabelaAlunos().getSelectedRow()!= -1){
            int linhaSelecionada = this.view.getTabelaAlunos().getSelectedRow();
            int codAluno = Integer.parseInt(tabelaDeAlunos.getValueAt(linhaSelecionada, 0).toString());
            String nomeAluno = tabelaDeAlunos.getValueAt(linhaSelecionada, 1).toString();
            String nomeTurma = view.getComboTurmas().getSelectedItem().toString();
            int codTurma = Integer.parseInt(nomeTurma.split("\\.")[0]);
            Turmas turma = turmasDao.pesquisarTurmas("SELECT * FROM tblTurmas WHERE codTurma = "+codTurma).get(0);
            
            turmasDao.atualizarQuantAunos(codTurma, turma.getQuantidadeAlunos()-1);
            alunosDao.removerAluno(codAluno);
            ArrayList <Funcionario> funcionarios = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE status = 'Ativo'");
            if(funcionarios!=null){
                String acao = "Remoção de Aluno";
                String descricao = "Removeu o aluno "+nomeAluno;
                this.setarLog(funcionarios, acao, descricao);
            }
            this.view.exibeMensagem("Sucesso");
            listarAlunos();
        }
        else{this.view.exibeMensagem("Erro, Nenhum Aluno Selecionado!");}
    }
    
    //Buscar turmas no campo de busca
    public void buscarAlunos() throws Exception{
        String alunoPesquisa = view.getCampoPesquisa().getText();
        ArrayList <Aluno> alunos = alunosDao.pesquisarPorNome(alunoPesquisa);
        if(alunoPesquisa.equals("")){listarAlunos();}
        else{this.buscas(alunos);}        
    }
    
    //Buscar Aniversariantes
    private void buscarAniversariantes() throws SQLException, ParseException, Exception{
        ArrayList <Aluno> alunos = alunosDao.selecionarTodosAlunos();
        ArrayList <Aluno> alunosAniversariantes = new ArrayList<>();
        Date aniversario;
        for(int linhas = 0; linhas<alunos.size(); linhas++){
            aniversario = alunos.get(linhas).getDatadenascimento();
            
            if(converterData.aniversarianteDoDia(aniversario)){
                alunosAniversariantes.add(alunos.get(linhas));
            }
        }
        this.buscas(alunosAniversariantes);
    }
    
    //Buscar Débito existentes
    private void buscarDebitos(char opcao) throws SQLException, ParseException, Exception{
        ArrayList <Aluno> alunosComDebito = new ArrayList<>();
        ArrayList <Aluno> alunosSemDebito = new ArrayList<>();        

        switch(opcao){
            case 'C':
                ArrayList <Planos> planosPendentes = planosDao.pesquisarPlanos("SELECT * FROM tblPlanos WHERE situacao = 'Pendente' OR situacao = 'Vencido'");
                if(planosPendentes!=null){
                    for(int linhas=0; linhas<planosPendentes.size(); linhas++){
                        Aluno aluno = alunosDao.pesquisarAlunos("SELECT * FROM tblAlunos WHERE codAluno = "+planosPendentes.get(linhas).getCodAluno()).get(0);
                        alunosComDebito.add(aluno);
                    }
                    this.buscas(alunosComDebito);
                }else{
                    view.exibeMensagem("Sem Dados");
                }
                
            break;

            case 'S':
                ArrayList <Planos> planosPagos = planosDao.pesquisarPlanos("SELECT * FROM tblPlanos WHERE situacao = 'Pago'");
                if(planosPagos!=null){
                    for(int linhas=0; linhas<planosPagos.size(); linhas++){
                        Aluno aluno = alunosDao.pesquisarAlunos("SELECT * FROM tblAlunos WHERE codAluno = "+planosPagos.get(linhas).getCodAluno()).get(0);
                        alunosSemDebito.add(aluno);
                    }
                    this.buscas(alunosSemDebito);
                }else{
                    view.exibeMensagem("Sem Dados");
                }
            break;
        }
        
    }
    
    //Buscar alunos com contrato encerrado
    private void buscarEncerrados() throws SQLException, ParseException, Exception{
        ArrayList <Planos> planos = planosDao.pesquisarPlanos("SELECT * FROM tblPlanos WHERE situacao = 'Encerrado'");
        ArrayList <Aluno> alunosEncerrados = new ArrayList<>();
        if(planos !=null){
            for(int linhas=0; linhas< planos.size(); linhas++){
            Aluno aluno = alunosDao.pesquisarAlunos("SELECT * FROM tblAlunos WHERE codAluno = "+planos.get(linhas).getCodAluno()).get(0);
            alunosEncerrados.add(aluno);
            }
            this.buscas(alunosEncerrados);
        }else{
            view.exibeMensagem("Sem Dados.");
        }
    }
    
    private void buscarTodos() throws SQLException, ParseException, Exception{
        ArrayList <Aluno> alunos = alunosDao.selecionarTodosAlunos();
        this.buscas(alunos);
    }
    
    //Listar
    public void listar() throws ParseException, Exception{
        String comboListar = view.getComboListar().getSelectedItem().toString();
        switch(comboListar){
            case "Todos":
                buscarTodos();
            break;
            
            case "Aniversariantes":
                this.buscarAniversariantes();
                
            break;
            
            case "Pendentes":
                this.buscarDebitos('C');
            break;
            
            case "Pagos": 
                this.buscarDebitos('S');
            break;
            case "Encerrados":
                this.buscarEncerrados();
            break;
        }
    }

    public void verificacaoDeTurmaEServico() throws SQLException {
        ArrayList <Turmas> turmas = turmasDao.selecionarTodasTurmas();
        ArrayList <Servicos> servicos = servicosDao.selecionarTodosServicos();

        
        if(turmas==null){
            view.exibeMensagem("Sem Turmas Cadastradas! Adicione Alguma Para Entrar Nessa Tela!");
            if(view.isVisible()){
               view.dispose(); 
            }
        }
        else{
            view.getComboTurmas().removeAllItems();
            view.getComboTurmasExistentes().removeAllItems();
            for(int linhas=0; linhas<turmas.size(); linhas++){
            
            view.getComboTurmas().addItem(turmas.get(linhas).getCodBanco()+"."+turmas.get(linhas).getNomeTurma());
            view.getComboTurmasExistentes().addItem(turmas.get(linhas).getCodBanco()+"."+turmas.get(linhas).getNomeTurma());
            }
            view.getComboTurmasExistentes().setSelectedIndex(0);
        }
        
        if(servicos==null){
            view.exibeMensagem("Sem Serviços Cadastrados! Adicione Algum Para Entrar Nessa Tela!");
            if(view.isVisible()){
               view.dispose(); 
            }
        }
        else{
            view.getComboServicos().removeAllItems();
            for(int linhas=0; linhas<servicos.size(); linhas++){ 
            view.getComboServicos().addItem(servicos.get(linhas).getCodBanco()+"."+servicos.get(linhas).getNome()+"-"+servicos.get(linhas).getPeriodo());
            }
        }
    }
    
    public void setarValorContrato() throws SQLException{
        if(view.getComboServicos().getSelectedIndex()>0){
            int linhaSelecionada = view.getTabelaPlanos().getSelectedRow();
            String nomeServico = view.getComboServicos().getSelectedItem().toString();
            int diaVencimento = Integer.parseInt(tabelaDePlanos.getValueAt(linhaSelecionada, 4).toString());
            int codServico = Integer.parseInt(nomeServico.split("\\.")[0]);
            Servicos servico = servicosDao.pesquisarServicos("SELECT * FROM tblServicos WHERE codServico = "+codServico).get(0);
            
            String metodoDePagamento = servico.getFormaPagamento();
            
            BigDecimal valorContrato = null;
            if(metodoDePagamento.equals("[Nenhuma]")){valorContrato = new BigDecimal(servico.getValor().toString());}
            if(metodoDePagamento.equals("Dinheiro")){valorContrato = new BigDecimal(servico.getValorVista().toString());}
            if(metodoDePagamento.equals("Boleto")){valorContrato = new BigDecimal(servico.getValorBoleto().toString());}
            if(metodoDePagamento.equals("Cartão de Crédito")){valorContrato = new BigDecimal(servico.getValorPrazoCredito().toString());}
            if(metodoDePagamento.equals("Cartão de Débito")){valorContrato = new BigDecimal(servico.getValorPrazoDebito().toString());}   
            
            view.getTabelaPlanos().setValueAt(valorContrato.toString(), linhaSelecionada, 2);
            view.getTabelaPlanos().setValueAt(nomeServico, linhaSelecionada, 1);
            this.setarValorMensal(servico, linhaSelecionada, codServico);
            view.getTabelaPlanos().setValueAt(this.diaVencimento(servico.getPeriodDays(), linhaSelecionada,diaVencimento), linhaSelecionada, 4);
        }
    }
    
    private String converterMatricula(int anoAtual, int codTurma, int codAluno, int codPlano) {
        String matricula = anoAtual+"T"+codTurma+"A"+codAluno+"S"+codPlano;
        
                return  matricula; 
    }
    
    private Aluno alunoAnterior(int codAluno) throws SQLException, ParseException{
         return  alunosDao.pesquisarAlunos("SELECT * FROM tblAlunos WHERE codAluno = "+codAluno).get(0);
    }
    
    private Planos planoAnterior(int codAluno) throws SQLException{
         return  planosDao.pesquisarPlanos("SELECT * FROM tblPlanos WHERE codAluno = "+codAluno).get(0);
    }
    
    private void buscas(ArrayList <Aluno> listar) throws Exception{
        ArrayList<Turmas> turmas = new ArrayList<>();
        ArrayList<Servicos> servicos = new ArrayList<>();
        ArrayList <EnderecoAlunos> enderecos = new ArrayList<>();
        ArrayList <Planos> planos;
        ArrayList <Aluno> alunos = listar;
        
        removerSelecaoBox();
        if(alunos==null){view.exibeMensagem("Sem dados!"); limparTabela();}
        else{
            limparTabela();
            for(int linhas = 0; linhas<alunos.size(); linhas++){
            enderecos = this.enderecoDao.pesquisarEndereco("SELECT * FROM tblEndAlunoseClientes WHERE codAluno = "+
                    alunos.get(linhas).getCodBanco());
            turmas = this.turmasDao.pesquisarTurmas("SELECT * FROM tblTurmas WHERE codTurma = "+
                    alunos.get(linhas).getTurma());
            servicos = this.servicosDao.pesquisarServicos("SELECT * FROM tblServicos WHERE codServico = "+
                    alunos.get(linhas).getPlano());
            planos = this.planosDao.pesquisarPlanos("SELECT * FROM tblPlanos WHERE codAluno = "+alunos.get(linhas).getCodBanco());
            
            boolean renovacaoAutomatica = false;
            if(alunos.get(linhas).getRenovacaoAutomatica()==1){
                renovacaoAutomatica = true;
            }
            
            //Inserindo dados na tabela de alunos
            Object[] dadosDaTabelaAlunos = {alunos.get(linhas).getCodBanco(), 
            alunos.get(linhas).getNome(),turmas.get(0).getCodBanco()+"."+turmas.get(0).getNomeTurma(), 
            alunos.get(linhas).getDebito(), new Boolean(renovacaoAutomatica)};
            this.tabelaDeAlunos.addRow(dadosDaTabelaAlunos);
            this.view.getComboTurmas().setSelectedItem(turmas.get(0).getCodBanco()+"."+turmas.get(0).getNomeTurma());
            
            
            
            //Inserindo dados na tabela de Planos
            int chavePlano = planos.get(0).getChavePlano();
            int diaVencimento = planos.get(0).getDiaVencimento();
            String situacao = planos.get(0).getSituacao();
            
            Object[] dadosDaTabelaPlanos = {chavePlano, servicos.get(0).getCodBanco()+"."+servicos.get(0).getNome()+
                    "-"+servicos.get(0).getPeriodo(), alunos.get(linhas).getValorContrato(), alunos.get(linhas).getValorMensal(),
                    diaVencimento, situacao};
            this.tabelaDePlanos.addRow(dadosDaTabelaPlanos);
            Component tableCellEditorComponent = this.view.getTabelaPlanos().getColumnModel().getColumn(4).getCellEditor().getTableCellEditorComponent(view.getTabelaPlanos(), diaVencimento, true, linhas, 4);
            tableCellEditorComponent.setEnabled(false);
            this.view.getComboServicos().setSelectedItem(servicos.get(0).getCodBanco()+"."+servicos.get(0).getNome()+"-"+servicos.get(0).getPeriodo());
            
            
            
            //Inserino dados na tabela de Pais
            String telefonePai = alunos.get(linhas).getTelefonedopai();
            String telefoneMae = alunos.get(linhas).getTelefonedamae();
            String cpfPai = alunos.get(linhas).getCpfdopai();
            String cpfMae = alunos.get(linhas).getCpfdamae();

            Object[] dadosDaTabelaPais = {alunos.get(linhas).getNomedopai(), 
            cpfPai,telefonePai, 
            alunos.get(linhas).getNomedamae(),cpfMae,
            telefoneMae};
            this.tabelaDePais.addRow(dadosDaTabelaPais);

            //Inserindo dados na tabela Endereços
            String cep = enderecos.get(0).getCep();
            Object[] dadosDaTabelaEnderecos  = {enderecos.get(0).getLogradouro(), 
            enderecos.get(0).getNumero(),enderecos.get(0).getBairro(), 
            enderecos.get(0).getCidade(),enderecos.get(0).getEstado(), cep};
            this.tabelaDeEnderecos.addRow(dadosDaTabelaEnderecos);
            this.view.getComboEstado().setSelectedItem(enderecos.get(0).getEstado());

            }
        }
       ativarSelecaoBox();
    }
    
    public void removerSelecaoBox(){
        this.view.getComboTurmas().setEnabled(false);
        this.view.getComboServicos().setEnabled(false);
        this.view.getComboEstado().setEnabled(false);
        this.view.getComboListar().setEnabled(false);
    }
    public void ativarSelecaoBox(){
        this.view.getComboTurmas().setEnabled(true);
        this.view.getComboServicos().setEnabled(true);
        this.view.getComboEstado().setEnabled(true);
        this.view.getComboListar().setEnabled(true);
    }
    
    private boolean verificarQuantidadeLimiteAlunos(int codTurma) throws SQLException{
            Turmas turma = this.pegarTurma(codTurma);
            if(turma.getQuantidadeMaximaAlunos() != 0){
                return turma.getQuantidadeAlunos()==turma.getQuantidadeMaximaAlunos();
            }
            return false;
    }
    
    private Turmas pegarTurma(int codTurma) throws SQLException{
        return turmasDao.pesquisarTurmas("SELECT * FROM tblTurmas WHERE codTurma = "+codTurma).get(0);
    }
    
    private Servicos pegarServico(int codServico) throws SQLException{
        return servicosDao.pesquisarServicos("SELECT * FROM tblServicos WHERE codServico = "+codServico).get(0);
    }
    
    private LogAçoesFuncionario setarLog(ArrayList <Funcionario> funcionarios, String acao, String descricao){
        Funcionario funcionario = funcionarios.get(0);
        Date dataEvento = new Date();
        LogAçoesFuncionario logAcao = new LogAçoesFuncionario(funcionario.getCodBanco(), dataEvento, acao, descricao);
        return logAcao;
    }
    
    
    private void setarValorMensal(Servicos servicoContratado, int linhaSelecionada, int diaVencimento){
        BigDecimal periodDays = new BigDecimal(servicoContratado.getPeriodDays());
        BigDecimal valorTotal = new BigDecimal(0);
        
        if(servicoContratado.getValor().compareTo(BigDecimal.ZERO)!=0){
            valorTotal = servicoContratado.getValor();
        }
        if(servicoContratado.getValorBoleto().compareTo(BigDecimal.ZERO)!=0){
            valorTotal = servicoContratado.getValorBoleto();
        }
        if(servicoContratado.getValorPrazoCredito().compareTo(BigDecimal.ZERO)!=0){
            valorTotal = servicoContratado.getValorPrazoCredito();
        }
        if(servicoContratado.getValorPrazoDebito().compareTo(BigDecimal.ZERO)!=0){
            valorTotal = servicoContratado.getValorPrazoDebito();
        }
        if(servicoContratado.getValorVista().compareTo(BigDecimal.ZERO)!=0){
            valorTotal = servicoContratado.getValorVista();
        }

        
        BigDecimal valorMensal = new BigDecimal(0);
        String mensal = periodDays.divide(new BigDecimal(30), 2, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
        String anual = periodDays.divide(new BigDecimal(365), 3, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();

        
        boolean resultadoMensal = mensal.matches("[1-9]*");
        boolean resultadoAnual = anual.matches("[1-9]*");
        
        Component tableCellEditorComponent = this.view.getTabelaPlanos().getColumnModel().getColumn(4).getCellEditor().getTableCellEditorComponent(view.getTabelaPlanos(), diaVencimento, true, linhaSelecionada, 4);

        if(resultadoMensal||resultadoAnual){
            tableCellEditorComponent.setEnabled(true);
            if(resultadoMensal){
               BigDecimal period = periodDays.divide(new BigDecimal(30), 2, RoundingMode.HALF_UP);
               valorMensal = valorTotal.divide(period, 2, RoundingMode.UP);
               valorMensal = valorMensal.setScale(2, RoundingMode.UP);
               tabelaDePlanos.setValueAt(valorMensal, linhaSelecionada, 3);
            }
            else{
                BigDecimal period = periodDays.divide(new BigDecimal(365), 2, RoundingMode.HALF_UP);
                period = period.multiply(new BigDecimal(12));
                valorMensal = valorTotal.divide(period, 2, RoundingMode.UP);
                valorMensal = valorMensal.setScale(2, RoundingMode.UP);
                tabelaDePlanos.setValueAt(valorMensal, linhaSelecionada, 3);
            }    
        }
        else{
            tableCellEditorComponent.setEnabled(false);
            boolean renovacaoAutomatica = tabelaDeAlunos.getValueAt(linhaSelecionada, 4).toString().equals("true");

            if(renovacaoAutomatica){
                BigDecimal period = (new BigDecimal(30)).divide(periodDays,4, RoundingMode.UP);
                valorMensal = valorTotal.multiply(period);
                valorMensal = valorMensal.setScale(2, RoundingMode.UP);

                tabelaDePlanos.setValueAt(valorMensal, linhaSelecionada, 3);
            }
            else{
                valorMensal = valorTotal;
                tabelaDePlanos.setValueAt(valorMensal, linhaSelecionada, 3);
            }  
        }  
    }
    private int diaVencimento(int diasContrato, int linhaSelecionada, int diaVencimento){
        Component tableCellEditorComponent = this.view.getTabelaPlanos().getColumnModel().getColumn(4).getCellEditor().getTableCellEditorComponent(view.getTabelaPlanos(), diaVencimento, true, linhaSelecionada, 4);
        if(tableCellEditorComponent.isEnabled()){
            return Integer.parseInt(tabelaDePlanos.getValueAt(linhaSelecionada, 4).toString());
        }
        else{
            LocalDate dataAtual = LocalDate.now();
            dataAtual = dataAtual.plusDays(diasContrato);
            return dataAtual.getDayOfMonth();
        }
    }
    
    public void setarDatasConfiguracoesAd() throws SQLException, ParseException{
        int linhaSelecionada = view.getTabelaAlunos().getSelectedRow();
        if(linhaSelecionada>-1){
            int codAluno = Integer.parseInt(tabelaDeAlunos.getValueAt(linhaSelecionada, 0).toString());
            Aluno aluno = this.alunoAnterior(codAluno);
            Planos plano = this.planoAnterior(codAluno);
            view.getCampoDataCadastro().setDate(aluno.getDataCadastro());
            view.getCampoDataPagamento().setDate(plano.getDataPagamento());
            
            if(plano.getDataVencimento()!=null){
                view.getCampoDataVencimento().setDate(plano.getDataVencimento());
                view.getCampoAvisoDataVencimento().setVisible(false);
            }
            else{
                view.getCampoAvisoDataVencimento().setText("");
                view.getCampoAvisoDataVencimento().append("Não há uma Data de Validade Mensal no Sistema.\nPor favor, pressionar o botão Setar Vencimento.");
                view.getCampoAvisoDataVencimento().setVisible(true);
            }
        }
    }
    
    public void setarDataVencimento() throws SQLException, ParseException, Exception{
        int linhaSelecionada = view.getTabelaAlunos().getSelectedRow();
        if(linhaSelecionada>-1 && view.getCampoDataVencimento().getDate()==null){
            Date dataCadastro = view.getCampoDataCadastro().getDate();
            System.out.println(dataCadastro);
            Date dataPagamento = view.getCampoDataPagamento().getDate();
            System.out.println(dataPagamento);
            int codAluno = Integer.parseInt(tabelaDeAlunos.getValueAt(linhaSelecionada, 0).toString());
            
            editarAlunos();
            Aluno aluno = this.alunoAnterior(codAluno);
            Planos plano = this.planoAnterior(codAluno);
            Servicos servico = this.pegarServico(plano.getCodServico());
            BigDecimal periodDays = new BigDecimal(servico.getPeriodDays());
            LocalDate dataVencimento;
            

            int renovacaoAutomatica = aluno.getRenovacaoAutomatica();


            Date dataPag = converterData.parseDate(converterData.parseDate(dataPagamento));
            Date dataPrimaria = converterData.parseDate(converterData.parseDate(dataCadastro));
            LocalDate dataBanco = converterData.conversaoLocalforDate(dataPag);
            LocalDate dataCad = converterData.conversaoLocalforDate(dataPrimaria);

            BigDecimal valorMensal = aluno.getValorMensal();

            String mensal = periodDays.divide(new BigDecimal(30), 2, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
            String anual = periodDays.divide(new BigDecimal(365), 3, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();


            boolean resultadoMensal = mensal.matches("[1-9]*");
            boolean resultadoAnual = anual.matches("[1-9]*");

            
            Planos novoPlano;
            Date dataAVencer;
            if(resultadoMensal||resultadoAnual){
               if(dataPagamento==null){
                   dataVencimento = LocalDate.of(dataCad.getYear(), dataCad.plusMonths(1).getMonthValue(), plano.getDiaVencimento());
                   dataAVencer = converterData.conversaoLocalforDate(dataVencimento);
                   view.getCampoDataVencimento().setDate(dataAVencer);
                   novoPlano = new Planos(codAluno, plano.getCodTurma(), servico.getCodBanco(), plano.getDiaVencimento(), 
                           dataAVencer, plano.getDataPagamento(), plano.getDataCancelamento(), plano.getDataRenovacao(), plano.getSituacao());
                   planosDao.atualizarSituacao(plano);
               }
               else{
                   dataVencimento = LocalDate.of(dataBanco.getYear(), dataBanco.plusMonths(1).getMonthValue(), plano.getDiaVencimento());
                   dataAVencer = converterData.conversaoLocalforDate(dataVencimento);
                   view.getCampoDataVencimento().setDate(dataAVencer);
                   novoPlano = new Planos(codAluno, plano.getCodTurma(), servico.getCodBanco(), plano.getDiaVencimento(), 
                           dataAVencer, plano.getDataPagamento(), plano.getDataCancelamento(), plano.getDataRenovacao(), plano.getSituacao());
                   planosDao.atualizarSituacao(plano);
               }
            }
            else{
                if(renovacaoAutomatica == 1){
                    if(dataPagamento==null){
                        dataVencimento = dataCad.plusDays(servico.getPeriodDays());
                        dataAVencer = converterData.conversaoLocalforDate(dataVencimento);
                        view.getCampoDataVencimento().setDate(dataAVencer);
                        novoPlano = new Planos(codAluno, plano.getCodTurma(), servico.getCodBanco(), plano.getDiaVencimento(), 
                           dataAVencer, plano.getDataPagamento(), plano.getDataCancelamento(), plano.getDataRenovacao(), plano.getSituacao());
                        planosDao.atualizarSituacao(plano);
                    }
                    else{
                        dataVencimento = dataBanco.plusDays(servico.getPeriodDays());
                        dataAVencer = converterData.conversaoLocalforDate(dataVencimento);
                        view.getCampoDataVencimento().setDate(dataAVencer);
                        novoPlano = new Planos(codAluno, plano.getCodTurma(), servico.getCodBanco(), plano.getDiaVencimento(), 
                           dataAVencer, plano.getDataPagamento(), plano.getDataCancelamento(), plano.getDataRenovacao(), plano.getSituacao());
                        planosDao.atualizarSituacao(plano);
                    }   
                }
                else{
                   dataVencimento = dataCad.plusDays(servico.getPeriodDays());
                   dataAVencer = converterData.conversaoLocalforDate(dataVencimento);
                   view.getCampoDataVencimento().setDate(dataAVencer);
                   novoPlano = new Planos(codAluno, plano.getCodTurma(), servico.getCodBanco(), plano.getDiaVencimento(), 
                           dataAVencer, plano.getDataPagamento(), plano.getDataCancelamento(), plano.getDataRenovacao(), plano.getSituacao());
                   planosDao.atualizarSituacao(plano);
                }           
            }
            
            
        }
        
    }
    
    public void selecionarTabela(){
        int linhaSelecionada = view.getTabelaAlunos().getSelectedRow();
        if(linhaSelecionada!=-1){
            view.getBotaoConfigAdicionais().setVisible(true);
        }
        else{
            view.getBotaoConfigAdicionais().setVisible(false);
        }
    }
}
