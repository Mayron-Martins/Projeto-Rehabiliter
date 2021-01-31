/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.auxiliar.ConversaoDeDinheiro;
import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Controller.auxiliar.LogsSystem;
import Controller.auxiliar.VerificarCodigoNoBanco;
import Dao.AlunosDao;
import Dao.EnderecoAlunosDao;
import Dao.FuncionarioDao;
import Dao.LogAçoesFuncionarioDao;
import Dao.MatriculasDao;
import Dao.PlanosDao;
import Dao.ServicosDao;
import Dao.TurmasDao;
import Dao.VendasDao;
import Model.Aluno;
import Model.Funcionario;
import Model.Vendas;
import Model.auxiliar.EnderecoAlunos;
import Model.auxiliar.LogAçoesFuncionario;
import Model.auxiliar.Matriculas;
import Model.auxiliar.Planos;
import Model.auxiliar.Servicos;
import Model.auxiliar.Turmas;
import View.AlunosView;
import View.LoginFuncionario;
import View.LoginGerente;
import View.Paineis.AlunosConfigAdicionais;
import View.Paineis.AlunosDescricao;
import java.awt.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mayro
 */
public class AlunosController {
    private final AlunosView view;
    private final AlunosConfigAdicionais view2;
    private final AlunosDescricao view3;
    private final DefaultTableModel tabelaDeAlunos;
    private final DefaultTableModel tabelaDePais;
    private final DefaultTableModel tabelaDeEnderecos;
    private final DefaultTableModel tabelaDePlanos;
    private final DefaultTableModel tabelaDePlanosAdicionais;
    private final DefaultTableModel tabelaDePagamentos;
    private final AlunosDao alunosDao = new AlunosDao();
    private final MatriculasDao matriculasDao = new MatriculasDao();
    private final TurmasDao turmasDao = new TurmasDao();
    private final ServicosDao servicosDao = new ServicosDao();
    private final EnderecoAlunosDao enderecoDao = new EnderecoAlunosDao();
    private final PlanosDao planosDao = new PlanosDao();
    private final FuncionarioDao funcionarioDao = new FuncionarioDao();
    private final LogAçoesFuncionarioDao logDao = new LogAçoesFuncionarioDao();
    private final VendasDao vendasDao = new VendasDao();
    private final ConversaoDeDinheiro converterDinheiro = new ConversaoDeDinheiro();
    private final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();
    private final VerificarCodigoNoBanco verificar = new VerificarCodigoNoBanco();

    public AlunosController(AlunosView view, AlunosConfigAdicionais view2, AlunosDescricao view3) {
        this.view = view;
        this.view2 = view2;
        this.view3 = view3;
        this.tabelaDeAlunos = (DefaultTableModel)view.getTabelaAlunos().getModel();
        this.tabelaDePais = (DefaultTableModel)view.getTabelaPais().getModel();
        this.tabelaDeEnderecos = (DefaultTableModel)view.getTabelaEnderecos().getModel();
        this.tabelaDePlanos = (DefaultTableModel) view.getTabelaPlanos().getModel();
        this.tabelaDePlanosAdicionais = (DefaultTableModel) view2.getTabelaPlanos().getModel();
        this.tabelaDePagamentos = (DefaultTableModel) view2.getTabelaPagamentos().getModel();
    }

    
    
    //TELA PRINCIPAL
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
    public void listarAlunos() {
        if(view.getComboTurmasExistentes().getSelectedIndex()>=0){
        String nomeTurmaAtual = view.getComboTurmasExistentes().getSelectedItem().toString();
        int codTurmaAtual = Integer.parseInt(nomeTurmaAtual.split("\\.")[0]);
        
        ArrayList<Planos> planos = planosDao.pesquisarPlanos("SELECT * FROM tblPlanos WHERE codTurma = "+codTurmaAtual+" AND situacao != 'Encerrado'");
        this.buscas(planos);   
        }
    }
    
    //Editar todos os alunos
    public void editarAlunos(){
        if(this.view.getTabelaAlunos().getRowCount()>0){
            //Dados Alunos
            int totalLinhas = this.view.getTabelaAlunos().getRowCount();
            for(int linhaSelecionada=0; linhaSelecionada<totalLinhas; linhaSelecionada++){
                int chavePlano = Integer.parseInt(this.tabelaDePlanos.getValueAt(linhaSelecionada, 0).toString());
                Planos planoAnterior = this.planoAnterior(chavePlano);
                if(!planoAnterior.getSituacao().equals("Encerrado")){
                    int codAluno = Integer.parseInt(tabelaDeAlunos.getValueAt(linhaSelecionada, 0).toString());
                    String nome = this.tabelaDeAlunos.getValueAt(linhaSelecionada, 1).toString();
                    String nomeTurmaAtual = this.tabelaDeAlunos.getValueAt(linhaSelecionada, 2).toString();
                    int codTurmaAtual = Integer.parseInt(nomeTurmaAtual.split("\\.")[0]);
                    BigDecimal valorDebito = new BigDecimal(converterDinheiro.converterParaBigDecimal(this.tabelaDeAlunos.getValueAt(linhaSelecionada, 3).toString()).toString());
                    int renovacaoAutomatica = 0;
                    if(tabelaDeAlunos.getValueAt(linhaSelecionada, 4).toString().equals("true")){
                        renovacaoAutomatica = 1;
                    }

                    //Dados Planos
                    String nomeServico = this.tabelaDePlanos.getValueAt(linhaSelecionada, 1).toString();
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
                    int anoAtual = LocalDate.now().getYear();
                    String nomeMatricula = this.converterMatricula(anoAtual, codTurmaAtual, codAluno, codServico);
                    Aluno alunoAnterior = this.alunoAnterior(codAluno);


                    Date dataValidade = planoAnterior.getDataVencimento();
                    Date dataPagamento = planoAnterior.getDataPagamento();
                    Date dataCadastro = alunoAnterior.getDataCadastro();

                    Aluno aluno = new Aluno(codAluno, nome, alunoAnterior.getCpf(), alunoAnterior.getRg(), alunoAnterior.getTelefone(), 
                            alunoAnterior.getCelular(), alunoAnterior.getEmail(), alunoAnterior.getDatadenascimento(), 
                            nomeMae, nomePai, telefoneMae, telefonePai, cpfMae, cpfPai, codTurmaAtual, codServico, alunoAnterior.getDescricao(), 
                            valorDebito, valorContrato, dataCadastro, valorMensal, renovacaoAutomatica);

                    Matriculas matricula = new Matriculas(codAluno, codTurmaAtual, codAluno, anoAtual, nomeMatricula);
                    EnderecoAlunos endereco = new EnderecoAlunos(codAluno, codAluno, logradouro, bairro, numero, nomeMae, telefoneMae, cidade, estado, cep);
                    Planos planoAluno = new Planos(chavePlano, codAluno, codTurmaAtual, codServico, diaVencimento, 
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
                    view.exibeMensagem("Quantidade de Vagas Limite para a Turma Atingida!\nVerifique a linha "+linhaSelecionada+1);
                }
                else{
                    if(nome.equals("")|| logradouro.equals("") || numero.equals("")|| bairro.equals("")|| cidade.equals("")||
                estado.equals("[Nenhum]")|| cep.equals("  .   -   ")){
                 view.exibeMensagem("Campos Preenchidos Incorretamente na linha "+linhaSelecionada+1);
                } else{
                    alunosDao.atualizarDados(aluno, endereco, matricula, planoAluno);
                    turmasDao.atualizarQuantAunos(turmaAnterior.getCodBanco(), (turmaAnterior.getQuantidadeAlunos()-1));
                    turmasDao.atualizarQuantAunos(turmaAtual.getCodBanco(), (turmaAtual.getQuantidadeAlunos()+1));
                    
                    this.setarLog("Edição de Dados de Aluno", "Editou os dados do aluno "+nome);
                    
                    }
                }
                }
            }
                
                
            view.exibeMensagem("Sucesso!");
            //Limpando Campos
            listarAlunos();
            }

        else{this.view.exibeMensagem("Erro, Sem dados na tabela!");}
    }
    
    
    //Editar apenas um Aluno
    public void editarAluno(){
        if(this.view.getTabelaAlunos().getSelectedRow()>=0){
            int linhaSelecionada = this.view.getTabelaAlunos().getSelectedRow();
            int chavePlano = Integer.parseInt(this.tabelaDePlanos.getValueAt(linhaSelecionada, 0).toString());
            Planos planoAnterior = this.planoAnterior(chavePlano);
            if(!planoAnterior.getSituacao().equals("Encerrado")){
                //Dados Alunos
                int codAluno = Integer.parseInt(tabelaDeAlunos.getValueAt(linhaSelecionada, 0).toString());
                String nome = this.tabelaDeAlunos.getValueAt(linhaSelecionada, 1).toString();
                String nomeTurmaAtual = this.tabelaDeAlunos.getValueAt(linhaSelecionada, 2).toString();
                int codTurmaAtual = Integer.parseInt(nomeTurmaAtual.split("\\.")[0]);
                BigDecimal valorDebito = new BigDecimal(converterDinheiro.converterParaBigDecimal(this.tabelaDeAlunos.getValueAt(linhaSelecionada, 3).toString()).toString());
                int renovacaoAutomatica = 0;
                if(tabelaDeAlunos.getValueAt(linhaSelecionada, 4).toString().equals("true")){
                    renovacaoAutomatica = 1;
                }

                //Dados Planos
                String nomeServico = this.tabelaDePlanos.getValueAt(linhaSelecionada, 1).toString();
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
                int anoAtual = LocalDate.now().getYear();
                String nomeMatricula = this.converterMatricula(anoAtual, codTurmaAtual, codAluno, codServico);
                Aluno alunoAnterior = this.alunoAnterior(codAluno);

                Date dataValidade = planoAnterior.getDataVencimento();
                if(view2.getCampoDataVencimento().getDate()!= null){
                    dataValidade = view2.getCampoDataVencimento().getDate();
                }

                Date dataPagamento = planoAnterior.getDataPagamento();
                if(view2.getCampoDataPagamento().getDate() != null){
                    dataPagamento = view2.getCampoDataPagamento().getDate();
                }

                Date dataCadastro = alunoAnterior.getDataCadastro();
                if(view2.getCampoDataCadastro().getDate() != null){
                    dataCadastro = view2.getCampoDataCadastro().getDate();

                }

                String descricao = view3.getCampoObservacao().getText();

                Aluno aluno = new Aluno(codAluno, nome, alunoAnterior.getCpf(), alunoAnterior.getRg(), alunoAnterior.getTelefone(), 
                        alunoAnterior.getCelular(), alunoAnterior.getEmail(), alunoAnterior.getDatadenascimento(), 
                        nomeMae, nomePai, telefoneMae, telefonePai, cpfMae, cpfPai, codTurmaAtual, codServico, descricao, 
                        valorDebito, valorContrato, dataCadastro, valorMensal, renovacaoAutomatica);

                Matriculas matricula = new Matriculas(codAluno, codTurmaAtual, codAluno, anoAtual, nomeMatricula);
                EnderecoAlunos endereco = new EnderecoAlunos(codAluno, codAluno, logradouro, bairro, numero, nomeMae, telefoneMae, cidade, estado, cep);
                Planos planoAluno = new Planos(chavePlano, codAluno, codTurmaAtual, codServico, diaVencimento, 
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
                    view.exibeMensagem("Quantidade de Vagas Limite para a Turma Atingida!\nVerifique a linha "+linhaSelecionada+1);
                }
                else{
                    if(nome.equals("")|| logradouro.equals("") || numero.equals("")|| bairro.equals("")|| cidade.equals("")||
                estado.equals("[Nenhum]")|| cep.equals("  .   -   ")){
                 view.exibeMensagem("Campos Preenchidos Incorretamente na linha "+linhaSelecionada+1);
                } else{
                    alunosDao.atualizarDados(aluno, endereco, matricula, planoAluno);
                    turmasDao.atualizarQuantAunos(turmaAnterior.getCodBanco(), (turmaAnterior.getQuantidadeAlunos()-1));
                    turmasDao.atualizarQuantAunos(turmaAtual.getCodBanco(), (turmaAtual.getQuantidadeAlunos()+1));

                    this.setarLog("Edição de Dados de Aluno", "Editou os dados do aluno "+nome);
                    
                        view.exibeMensagem("Sucesso!"); 
                    }

                }

            }
            else{
                view.exibeMensagem("Não é possível editar, aluno com Plano Encerrado!");
            }
                 
        }
    }
    
    public void removerAluno(){
        if(this.view.getTabelaAlunos().getSelectedRow()!= -1){
            int linhaSelecionada = this.view.getTabelaAlunos().getSelectedRow();
            int codAluno = Integer.parseInt(tabelaDeAlunos.getValueAt(linhaSelecionada, 0).toString());
            String nomeAluno = tabelaDeAlunos.getValueAt(linhaSelecionada, 1).toString();
            String nomeTurma = view.getComboTurmas().getSelectedItem().toString();
            int codTurma = Integer.parseInt(nomeTurma.split("\\.")[0]);
            Turmas turma = turmasDao.pesquisarTurmas("SELECT * FROM tblTurmas WHERE codTurma = "+codTurma).get(0);

            turmasDao.atualizarQuantAunos(codTurma, turma.getQuantidadeAlunos()-1);
            alunosDao.removerAluno(codAluno);

            this.setarLog("Remoção de Aluno", "Removeu o aluno "+nomeAluno);
            
            this.view.exibeMensagem("Sucesso");
            listarAlunos();
        }
        else{this.view.exibeMensagem("Erro, Nenhum Aluno Selecionado!");}  
    }
    
    //Buscar turmas no campo de busca
    public void buscarAlunos(){
        String alunoPesquisa = view.getCampoPesquisa().getText();
        if(alunoPesquisa.equals("")){listarAlunos();}
        else{
            ArrayList <Aluno> alunos = alunosDao.pesquisarPorNome(alunoPesquisa);
            ArrayList <Planos> allPlanos;
            ArrayList <Planos> planos = new ArrayList<>();
            
            for(Aluno aluno : alunos){
                allPlanos = planosDao.pesquisarPlanos("SELECT * FROM tblPlanos WHERE codAluno = "+aluno.getCodBanco());
                if(allPlanos.size()>1){
                    planos.add(allPlanos.get(allPlanos.size()-1));
                }
                else{
                    planos.add(allPlanos.get(0));
                }
            }
            this.buscas(planos);
        }      
    }
    
    //Buscar Aniversariantes
    private void buscarAniversariantes(){
        ArrayList <Aluno> alunos = alunosDao.selecionarTodosAlunos();
        ArrayList <Planos> planos;
        ArrayList <Planos> planosAniversariantes = new ArrayList<>();
        Date aniversario;
        for(Aluno aluno : alunos){
            aniversario = aluno.getDatadenascimento();

            if(converterData.aniversarianteDoDia(aniversario)){
                planos = planosDao.pesquisarPlanos("SELECT * FROM tblPlanos WHERE codAluno = "+aluno.getCodBanco()+" AND situacao != 'Encerrado'");
                if(planos!=null){
                    planosAniversariantes.add(planos.get(0));
                }
            }
        }
        if(planosAniversariantes.size()==0){
            view.exibeMensagem("Sem Dados.");
            limparTabela();
        }else{
            this.buscas(planosAniversariantes);
        }
        
    }
    
    //Buscar Débito existentes
    private void buscarDebitos(char opcao){       
        switch(opcao){
            case 'C':
                ArrayList <Planos> planosPendentes = planosDao.pesquisarPlanos("SELECT * FROM tblPlanos WHERE situacao = 'Pendente' OR situacao = 'Vencido'");
                if(planosPendentes!=null){
                    this.buscas(planosPendentes);
                }else{
                    view.exibeMensagem("Sem Dados");
                    limparTabela();
                }

            break;

            case 'S':
                ArrayList <Planos> planosPagos = planosDao.pesquisarPlanos("SELECT * FROM tblPlanos WHERE situacao = 'Pago'");
                if(planosPagos!=null){
                    this.buscas(planosPagos);
                }else{
                    view.exibeMensagem("Sem Dados");
                    limparTabela();
                }
            break;
        }
    }
    
    //Buscar alunos com contrato encerrado
    private void buscarEncerrados() {
        int quantItens = view.getComboTurmasExistentes().getItemCount();
        if(quantItens>0){
            String nomeTurma = view.getComboTurmasExistentes().getSelectedItem().toString().split("\\.")[0];
            int codTurma = Integer.parseInt(nomeTurma);
            ArrayList <Planos> planos = planosDao.pesquisarPlanos("SELECT * FROM tblPlanos WHERE situacao = 'Encerrado' AND codTurma = "+codTurma);
            this.buscas(planos);
        }
        
    }
    
    private void buscarTodos(){
        ArrayList <Planos> planos = planosDao.pesquisarPlanos("SELECT * FROM tblPlanos WHERE situacao != 'Encerrado'");
        this.buscas(planos);
    }
    
    //Listar
    public void listar(){
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
    
    public void trocarModoTurma(){
        String modoTurma = view.getModoTurma().getText();
        
        if(modoTurma.equals("Modo: Turmas Abertas")){
            view.getModoTurma().setText("Modo: Todas as Turmas");
            this.setarTodasAsTurmas();
        }
        else{
            view.getModoTurma().setText("Modo: Turmas Abertas");
            this.setarTurmasAbertas();
        }
    }

    //Seta as turmas não encerradas para os combos na área de alunosView e Painel de COnfigurações Adicionais
    public void verificacaoDeTurmaEServico(){
        ArrayList <Turmas> turmas = turmasDao.selecionarTodasTurmas();
        ArrayList <Servicos> servicos = servicosDao.selecionarTodosServicos();

        if(turmas==null){
            view.exibeMensagem("Sem Turmas Cadastradas! Adicione Alguma Para Entrar Nessa Tela!");
            if(view.isVisible()){
               view.dispose(); 
            }
        }
        else{
            view.getModoTurma().setText("Modo: Turmas Abertas");
            this.setarTurmasAbertas();
        }

        if(servicos==null){
            view.exibeMensagem("Sem Serviços Cadastrados! Adicione Algum Para Entrar Nessa Tela!");
            if(view.isVisible()){
               view.dispose(); 
            }
        }
        else{
            this.setarServicosAbertos();
        }
    }
    
    private void setarTurmasAbertas(){
        ArrayList <Turmas> turmas = turmasDao.pesquisarTurmas("SELECT * FROM tblTurmas WHERE situacao != 'Encerrada'");
        view.getComboTurmas().removeAllItems();
        view.getComboTurmasExistentes().removeAllItems();
        view2.getComboTurmas().removeAllItems();
        view2.getComboTurmas().addItem("[Nenhuma]");
        if(turmas!=null){
            for(int linhas=0; linhas<turmas.size(); linhas++){
                view.getComboTurmas().addItem(turmas.get(linhas).getCodBanco()+"."+turmas.get(linhas).getNomeTurma());
                view.getComboTurmasExistentes().addItem(turmas.get(linhas).getCodBanco()+"."+turmas.get(linhas).getNomeTurma());
                view2.getComboTurmas().addItem(turmas.get(linhas).getCodBanco()+"."+turmas.get(linhas).getNomeTurma());
            }
        view.getComboTurmasExistentes().setSelectedIndex(0);
        }  
    }
    
    private void setarServicosAbertos(){
        ArrayList <Servicos> servicos = servicosDao.pesquisarServicos("SELECT * FROM tblServicos WHERE situacao != 'Encerrado'");
        view.getComboServicos().removeAllItems();
        view2.getComboServicos().removeAllItems();
        view2.getComboServicos().addItem("[Nenhum]");
        if(servicos!=null){
            for(int linhas=0; linhas<servicos.size(); linhas++){ 
                view.getComboServicos().addItem(servicos.get(linhas).getCodBanco()+"."+servicos.get(linhas).getNome()+"-"+servicos.get(linhas).getPeriodo());
                view2.getComboServicos().addItem(servicos.get(linhas).getCodBanco()+"."+servicos.get(linhas).getNome()+"-"+servicos.get(linhas).getPeriodo());
            }
        } 
    }
    
    public void setarTodasAsTurmas(){
        String comboTurmas=null;
        if(view.getComboTurmasExistentes().getItemCount()>0){
            comboTurmas = view.getComboTurmasExistentes().getSelectedItem().toString();
        }
        
        ArrayList <Turmas> turmas = turmasDao.selecionarTodasTurmas();
        if(turmas!=null){
            view.getComboTurmas().removeAllItems();
            view.getComboTurmasExistentes().removeAllItems();
            for(Turmas turma : turmas){
                view.getComboTurmasExistentes().addItem(turma.getCodBanco()+"."+turma.getNomeTurma());
            }

            if(comboTurmas!=null){
                view.getComboTurmasExistentes().setSelectedItem(comboTurmas);
            }
        }
    }
    
    public void setarValorContrato(){
        if(view.getComboServicos().getSelectedIndex()>=0){
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
    
    private Aluno alunoAnterior(int codAluno){
         return  alunosDao.pesquisarAlunos("SELECT * FROM tblAlunos WHERE codAluno = "+codAluno).get(0);
    }
    
    private Planos planoAnterior(long chavePlano){
         return  planosDao.pesquisarPlanos("SELECT * FROM tblPlanos WHERE chavePlano = "+chavePlano).get(0);
    }
    
    private void buscas(ArrayList <Planos> listar){
        ArrayList <Planos> planos = listar;
        removerSelecaoBox();
        if(planos==null){view.exibeMensagem("Sem dados!"); limparTabela();}
        else{
            limparTabela();
            int linhas=0;
            String servicoAdicionado = null;
            String turmaAdicionada = null;
            for(Planos plano : planos){
                if(servicoAdicionado!=null){
                    view.getComboServicos().removeItem(servicoAdicionado);
                    servicoAdicionado = null;
                }
                
                if(turmaAdicionada!=null){
                    view.getComboTurmas().removeItem(turmaAdicionada);
                    turmaAdicionada = null;
                }
                
                Aluno aluno = alunosDao.pesquisarAlunos("SELECT * FROM tblAlunos WHERE codAluno = "+plano.getCodAluno()).get(0);
                EnderecoAlunos endereco = enderecoDao.pesquisarEndereco("SELECT * FROM tblEndAlunoseClientes WHERE codAluno = "+plano.getCodAluno()).get(0);
                Turmas turma = turmasDao.pesquisarTurmas("SELECT * FROM tblTurmas WHERE codTurma = "+plano.getCodTurma()).get(0);
                Servicos servico = servicosDao.pesquisarServicos("SELECT * FROM tblServicos WHERE codServico = "+plano.getCodServico()).get(0);
                
                

                boolean renovacaoAutomatica = false;
                if(aluno.getRenovacaoAutomatica()==1){
                    renovacaoAutomatica = true;
                }

                //Inserindo dados na tabela de alunos
                Object[] dadosDaTabelaAlunos = {aluno.getCodBanco(), aluno.getNome(),turma.getCodBanco()+"."+turma.getNomeTurma(), 
                aluno.getDebito(), renovacaoAutomatica};
                this.tabelaDeAlunos.addRow(dadosDaTabelaAlunos);
                
                if(turma.getSituacao().equals("Encerrada")){
                    turmaAdicionada = turma.getCodBanco()+"."+turma.getNomeTurma();
                    view.getComboTurmas().addItem(turmaAdicionada);
                }
                this.view.getComboTurmas().setSelectedItem(turma.getCodBanco()+"."+turma.getNomeTurma());



                //Inserindo dados na tabela de Planos
                long chavePlano = plano.getChavePlano();
                int diaVencimento = plano.getDiaVencimento();
                String situacao = plano.getSituacao();

                Object[] dadosDaTabelaPlanos = {chavePlano, servico.getCodBanco()+"."+servico.getNome()+"-"+servico.getPeriodo(), 
                    aluno.getValorContrato(), aluno.getValorMensal(),
                        diaVencimento, situacao};
                this.tabelaDePlanos.addRow(dadosDaTabelaPlanos);
                Component tableCellEditorComponent = this.view.getTabelaPlanos().getColumnModel().getColumn(4).getCellEditor().getTableCellEditorComponent(view.getTabelaPlanos(), diaVencimento, true, linhas, 4);
                tableCellEditorComponent.setEnabled(false);
                
                if(servico.getSituacao().equals("Encerrado")){
                    servicoAdicionado = servico.getCodBanco()+"."+servico.getNome()+"-"+servico.getPeriodo();
                    view.getComboServicos().addItem(servicoAdicionado);
                }
                this.view.getComboServicos().setSelectedItem(servico.getCodBanco()+"."+servico.getNome()+"-"+servico.getPeriodo());



                //Inserino dados na tabela de Pais
                String telefonePai = aluno.getTelefonedopai();
                String telefoneMae = aluno.getTelefonedamae();
                String cpfPai = aluno.getCpfdopai();
                String cpfMae = aluno.getCpfdamae();

                Object[] dadosDaTabelaPais = {aluno.getNomedopai(), 
                cpfPai,telefonePai, 
                aluno.getNomedamae(),cpfMae,
                telefoneMae};
                this.tabelaDePais.addRow(dadosDaTabelaPais);

                //Inserindo dados na tabela Endereços
                String cep = endereco.getCep();
                Object[] dadosDaTabelaEnderecos  = {endereco.getLogradouro(), 
                endereco.getNumero(),endereco.getBairro(), 
                endereco.getCidade(),endereco.getEstado(), cep};
                this.tabelaDeEnderecos.addRow(dadosDaTabelaEnderecos);
                this.view.getComboEstado().setSelectedItem(endereco.getEstado());
                
                if(plano.getSituacao().equals("Encerrado")){
                    this.bloquearLinhaTabela(linhas);
                }
                
                linhas++;
            }
        }
       ativarSelecaoBox();
    }
    
    private void bloquearLinhaTabela(int linhaTabela){
        int colunasAluno = tabelaDeAlunos.getColumnCount();
        int colunasPlanos = tabelaDePlanos.getColumnCount();
        int colunasEnderecos = tabelaDeEnderecos.getColumnCount();
        int colunasPais = tabelaDePais.getColumnCount();
        
        Component tableCellEditorComponent;
        JTable tabela = view.getTabelaAlunos();
        Object dadoTabela;
        
        //Tabela de Alunos
        for(int colunas=0; colunas<colunasAluno; colunas++){
            dadoTabela = tabelaDeAlunos.getValueAt(linhaTabela, colunas);
            tableCellEditorComponent =  view.getTabelaAlunos().getCellEditor(linhaTabela, colunas).getTableCellEditorComponent(tabela, dadoTabela, true, linhaTabela, colunas);
            tableCellEditorComponent.setEnabled(false);
        }
        
        //Tabela de Planos
        tabela = view.getTabelaPlanos();
        for(int colunas=0; colunas<colunasPlanos; colunas++){
            dadoTabela = tabelaDePlanos.getValueAt(linhaTabela, colunas);
            tableCellEditorComponent = view.getTabelaPlanos().getCellEditor(linhaTabela, colunas).getTableCellEditorComponent(tabela, dadoTabela, true, linhaTabela, colunas);
            tableCellEditorComponent.setEnabled(false);
        }
        
        //Tabela de Pais
        tabela = view.getTabelaPais();
        for(int colunas=0; colunas<colunasPais; colunas++){
            dadoTabela = tabelaDePais.getValueAt(linhaTabela, colunas);
            tableCellEditorComponent = view.getTabelaPais().getCellEditor(linhaTabela, colunas).getTableCellEditorComponent(tabela, dadoTabela, true, linhaTabela, colunas);
            tableCellEditorComponent.setEnabled(false);
        }
        
        //Tabela de Endereços
        tabela = view.getTabelaEnderecos();
        for(int colunas=0; colunas<colunasEnderecos; colunas++){
            dadoTabela = tabelaDeEnderecos.getValueAt(linhaTabela, colunas);
            tableCellEditorComponent = view.getTabelaEnderecos().getCellEditor(linhaTabela, colunas).getTableCellEditorComponent(tabela, dadoTabela, true, linhaTabela, colunas);
            tableCellEditorComponent.setEnabled(false);
        }
    }
    
    public void removerSelecaoBox(){
        this.view.getComboTurmas().setEnabled(false);
        this.view.getComboServicos().setEnabled(false);
        this.view.getComboEstado().setEnabled(false);
    }
    public void ativarSelecaoBox(){
        this.view.getComboTurmas().setEnabled(true);
        this.view.getComboServicos().setEnabled(true);
        this.view.getComboEstado().setEnabled(true);
    }
    
    private boolean verificarQuantidadeLimiteAlunos(int codTurma){
            Turmas turma = this.pegarTurma(codTurma);
            if(turma.getQuantidadeMaximaAlunos() != 0){
                return turma.getQuantidadeAlunos()==turma.getQuantidadeMaximaAlunos();
            }
            return false;
    }
    
    private Turmas pegarTurma(int codTurma){
        return turmasDao.pesquisarTurmas("SELECT * FROM tblTurmas WHERE codTurma = "+codTurma).get(0);
    }
    
    private Servicos pegarServico(int codServico){
        return servicosDao.pesquisarServicos("SELECT * FROM tblServicos WHERE codServico = "+codServico).get(0);
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
    
    public void encerrarPlano(){
        int linhaSelecionada = view.getTabelaAlunos().getSelectedRow();
        
        if(linhaSelecionada>-1){
            long chavePlano = Integer.parseInt(tabelaDePlanos.getValueAt(linhaSelecionada, 0).toString());
            Planos plano = this.planoAnterior(chavePlano);
            Aluno aluno = this.alunoAnterior(plano.getCodAluno());
            
            BigDecimal debito = aluno.getDebito();
            
            Planos novoPlano;
            
            if(!plano.getSituacao().equals("Encerrado")){
                int confirmacao = -1;
                if(debito.compareTo(BigDecimal.ZERO)!=0){
                    confirmacao = JOptionPane.showConfirmDialog(null, "O Aluno ainda possui débitos não pagos, deseja Encerrar o Plano assim mesmo?", 
                        "Atenção", JOptionPane.YES_NO_OPTION);
                }
                else{
                    confirmacao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja encerrar o Plano deste Aluno?", 
                        "Atenção", JOptionPane.YES_NO_OPTION);
                }

                if(confirmacao == JOptionPane.YES_OPTION){
                    Date dataAtual = new Date();
                    String situacao = "Encerrado";

                    novoPlano = new Planos(chavePlano, plano.getCodAluno(), plano.getCodTurma(), plano.getCodServico(), 
                            plano.getDiaVencimento(), plano.getDataVencimento(), plano.getDataPagamento(), dataAtual, plano.getDataRenovacao(), situacao);

                    planosDao.atualizarSituacao(novoPlano);
                    alunosDao.atualizarDebitos(aluno.getCodBanco(), BigDecimal.ZERO);
                    view.exibeMensagem("Sucesso!");
                    listarAlunos();
                }
            }
            else{
                view.exibeMensagem("O Plano já está encerrado!");
            }
        }
    }
    
    
    //CONFIGURAÇÕES ADICIONAIS
    public void selecionarTabela(){
        int linhaSelecionada = view.getTabelaAlunos().getSelectedRow();
        if(linhaSelecionada!=-1){
            view.getBotaoConfigAdicionais().setVisible(true);
            view.getBotaoObservacao().setVisible(true);
            setarDatasConfiguracoesAd();
            setarTabelaPlanosAdicionais();
            liberarBotoesAdicionais();
            setarObservacoes();
        }
        else{
            view.getBotaoConfigAdicionais().setVisible(false);
            view.getBotaoObservacao().setVisible(false);
        }
    }
    
    public void limparTabelaPlanosAdicionais(){
        int quantLinhas = view2.getTabelaPlanos().getRowCount();
        for(int quant=0; quant<quantLinhas; quant++){
            this.tabelaDePlanosAdicionais.removeRow(0);
        }
    }
    
    public void limparTabelaPagamentos(){
        int quantLinhas = view2.getTabelaPagamentos().getRowCount();
        for(int quant=0; quant<quantLinhas; quant++){
            this.tabelaDePagamentos.removeRow(0);
        }
    }
    
    public void setarDatasConfiguracoesAd(){
        view2.limparCampos();
        limparTabelaPlanosAdicionais();
        limparTabelaPagamentos();
        int linhaSelecionada = view.getTabelaAlunos().getSelectedRow();
        if(linhaSelecionada>-1){
            int codAluno = Integer.parseInt(tabelaDeAlunos.getValueAt(linhaSelecionada, 0).toString());
            long chavePlano = Long.parseLong(tabelaDePlanos.getValueAt(linhaSelecionada, 0).toString());
            Aluno aluno = this.alunoAnterior(codAluno);
            Planos plano = this.planoAnterior(chavePlano);
            Servicos servico = this.pegarServico(plano.getCodServico());

            Date dataConvertida = converterData.parseDate(converterData.parseDate(aluno.getDataCadastro()));
            if(plano.getDataRenovacao()!=null){
                dataConvertida = converterData.parseDate(converterData.parseDate(plano.getDataRenovacao()));
            }
            LocalDate dataFimPlano = converterData.conversaoLocalforDate(dataConvertida);
            

            view2.getCampoDataCadastro().setDate(aluno.getDataCadastro());
            view2.getCampoDataPagamento().setDate(plano.getDataPagamento());
            view2.getCampoDataRenovacao().setDate(plano.getDataRenovacao());
            view2.getCampoDataFimPlano().setDate(converterData.conversaoLocalforDate(dataFimPlano.plusDays(servico.getPeriodDays())));

            if(plano.getDataVencimento()!=null){
                view2.getCampoDataVencimento().setDate(plano.getDataVencimento());
            }
            if(!plano.getSituacao().equals("Encerrado")){
                view2.getCampoDataCadastro().setEnabled(false);
                view2.getCampoDataPagamento().setEnabled(false);
            }
            else{
                view2.getCampoDataCadastro().setEnabled(true);
                view2.getCampoDataPagamento().setEnabled(true);
            }
        }
        setarTabelaPlanosAdicionais();
        liberarBotoesAdicionais();
    }
    
    public void setarDataVencimento(){
        int linhaSelecionada = view.getTabelaAlunos().getSelectedRow();
        if(linhaSelecionada>-1){
            long chavePlano = Long.parseLong(tabelaDePlanos.getValueAt(linhaSelecionada, 1).toString());
            Date dataCadastro = view2.getCampoDataCadastro().getDate();
            Date dataPagamento = view2.getCampoDataPagamento().getDate();
            Date dataRenovacao = view2.getCampoDataRenovacao().getDate();
            int codAluno = Integer.parseInt(tabelaDeAlunos.getValueAt(linhaSelecionada, 0).toString());

            String situacao = "Pendente";
            if(dataPagamento!=null){
                situacao = "Pago";
            }
            editarAluno();
            Aluno aluno = this.alunoAnterior(codAluno);
            Planos plano = this.planoAnterior(chavePlano);
            Servicos servico = this.pegarServico(plano.getCodServico());
            BigDecimal periodDays = new BigDecimal(servico.getPeriodDays());
            LocalDate dataVencimento;


            int renovacaoAutomatica = aluno.getRenovacaoAutomatica();


            Date dataPag = converterData.parseDate(converterData.parseDate(dataPagamento));
            Date dataPrimaria = converterData.parseDate(converterData.parseDate(dataCadastro));
            LocalDate dataUsual = converterData.conversaoLocalforDate(dataPag);
            LocalDate dataCad = converterData.conversaoLocalforDate(dataPrimaria);
            if(dataRenovacao!=null){
                LocalDate dataRenov = converterData.conversaoLocalforDate(dataRenovacao);
                if(dataRenov.isAfter(dataUsual)){
                    dataUsual = dataRenov;
                    situacao = "Pendente";
                }
            }
            

            BigDecimal valorMensal = aluno.getValorMensal();

            String mensal = periodDays.divide(new BigDecimal(30), 2, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
            String anual = periodDays.divide(new BigDecimal(365), 3, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();


            boolean resultadoMensal = mensal.matches("[1-9]*");
            boolean resultadoAnual = anual.matches("[1-9]*");

            int diaVencimento = plano.getDiaVencimento();


            Planos novoPlano;
            Date dataAVencer=null;
            if(resultadoMensal||resultadoAnual){
               if(dataCad.plusMonths(1).getMonth().compareTo(Month.FEBRUARY)==0&&diaVencimento>28){
                       diaVencimento = 28;
                }

               if(dataPagamento==null){
                   dataVencimento = LocalDate.of(dataCad.plusMonths(1).getYear(), dataCad.plusMonths(1).getMonthValue(), diaVencimento);
                   dataAVencer = converterData.conversaoLocalforDate(dataVencimento);
                   view2.getCampoDataVencimento().setDate(dataAVencer);
                   novoPlano = new Planos(chavePlano, codAluno, plano.getCodTurma(), servico.getCodBanco(), plano.getDiaVencimento(), 
                           dataAVencer, plano.getDataPagamento(), plano.getDataCancelamento(), plano.getDataRenovacao(), situacao);
                   planosDao.atualizarSituacao(plano);
               }
               else{
                   dataVencimento = LocalDate.of(dataUsual.plusMonths(1).getYear(), dataUsual.plusMonths(1).getMonthValue(), diaVencimento);
                   dataAVencer = converterData.conversaoLocalforDate(dataVencimento);
                   view2.getCampoDataVencimento().setDate(dataAVencer);
                   novoPlano = new Planos(chavePlano, codAluno, plano.getCodTurma(), servico.getCodBanco(), plano.getDiaVencimento(), 
                           dataAVencer, plano.getDataPagamento(), plano.getDataCancelamento(), plano.getDataRenovacao(), situacao);
                   planosDao.atualizarSituacao(plano);
               }
            }
            else{
                if(renovacaoAutomatica == 1){
                    if(dataPagamento==null){
                        dataVencimento = dataCad.plusDays(servico.getPeriodDays());
                        dataAVencer = converterData.conversaoLocalforDate(dataVencimento);
                        view2.getCampoDataVencimento().setDate(dataAVencer);
                        novoPlano = new Planos(chavePlano, codAluno, plano.getCodTurma(), servico.getCodBanco(), plano.getDiaVencimento(), 
                           dataAVencer, plano.getDataPagamento(), plano.getDataCancelamento(), plano.getDataRenovacao(), situacao);
                        planosDao.atualizarSituacao(plano);
                    }
                    else{
                        dataVencimento = dataUsual.plusDays(servico.getPeriodDays());
                        dataAVencer = converterData.conversaoLocalforDate(dataVencimento);
                        view2.getCampoDataVencimento().setDate(dataAVencer);
                        novoPlano = new Planos(chavePlano, codAluno, plano.getCodTurma(), servico.getCodBanco(), plano.getDiaVencimento(), 
                           dataAVencer, plano.getDataPagamento(), plano.getDataCancelamento(), plano.getDataRenovacao(), situacao);
                        planosDao.atualizarSituacao(plano);
                    }   
                }
                else{
                   dataVencimento = dataCad.plusDays(servico.getPeriodDays());
                   dataAVencer = converterData.conversaoLocalforDate(dataVencimento);
                   view2.getCampoDataVencimento().setDate(dataAVencer);
                   novoPlano = new Planos(chavePlano, codAluno, plano.getCodTurma(), servico.getCodBanco(), plano.getDiaVencimento(), 
                           dataAVencer, plano.getDataPagamento(), plano.getDataCancelamento(), plano.getDataRenovacao(), situacao);
                   planosDao.atualizarSituacao(plano);
                }           
            }
        }
    }
    
    public void setarDataPagCadastro(){
        int linhaSelecionada = view.getTabelaAlunos().getSelectedRow();
        if(linhaSelecionada>-1){
            Date dataCadastro = view2.getCampoDataCadastro().getDate();
            Date dataPagamento = view2.getCampoDataPagamento().getDate();
            int codAluno = Integer.parseInt(tabelaDeAlunos.getValueAt(linhaSelecionada, 0).toString());
            long chavePlano = Long.parseLong(tabelaDePlanos.getValueAt(linhaSelecionada, 0).toString());
            Aluno aluno = this.alunoAnterior(codAluno);
            Planos plano = this.planoAnterior(chavePlano);

            //Date dataPag = converterData.parseDate(converterData.parseDate(dataPagamento));
            //Date dataPrimaria = converterData.parseDate(converterData.parseDate(dataCadastro));
            LocalDate dataPagConv = converterData.conversaoLocalforDate(dataPagamento);
            LocalDate dataCad = converterData.conversaoLocalforDate(dataCadastro);

            Date dataPagamentoBanco = converterData.parseDate(converterData.parseDate(plano.getDataPagamento()));
            Date dataCadastroBanco = converterData.parseDate(converterData.parseDate(aluno.getDataCadastro()));
            LocalDate dataPagBanco = converterData.conversaoLocalforDate(dataPagamentoBanco);
            LocalDate dataCadBanco = converterData.conversaoLocalforDate(dataCadastroBanco);

            if(!dataPagConv.isEqual(dataPagBanco)||!dataCad.isEqual(dataCadBanco)){
                editarAluno();
            }

        }
    }
    
    private void setarTabelaPlanosAdicionais(){
        limparTabelaPlanosAdicionais();
        int linhaSelecionada = view.getTabelaAlunos().getSelectedRow();
        if(linhaSelecionada>-1){
            int codAluno = Integer.parseInt(tabelaDeAlunos.getValueAt(linhaSelecionada, 0).toString());
            ArrayList <Planos> planos = planosDao.pesquisarPlanos("SELECT * FROM tblPlanos WHERE codAluno = "+codAluno);
            if(planos!=null){
                String situacao;
                for(int linhas = planos.size()-1; linhas>=0;linhas--){
                    situacao = planos.get(linhas).getSituacao();
                    if(!situacao.equals("Encerrado")){
                        situacao = "Em Aberto";
                    }
                    Turmas turma = turmasDao.pesquisarTurmas("SELECT * FROM tblTurmas WHERE codTurma = "+planos.get(linhas).getCodTurma()).get(0);
                    Servicos servico = servicosDao.pesquisarServicos("SELECT * FROM tblServicos WHERE codServico = "+planos.get(linhas).getCodServico()).get(0);
                    Object[] dadosDaTabela = {planos.get(linhas).getChavePlano(), turma.getCodBanco()+"."+turma.getNomeTurma(), servico.getCodBanco()+"."+servico.getNome()+"-"+servico.getPeriodo(),
                    servico.getValor(), situacao};

                    tabelaDePlanosAdicionais.addRow(dadosDaTabela);
                }

            }
        }
    }
    
    public void selecionarTabelaPlanosAdicionais(){
        limparTabelaPagamentos();
        int linhaSelecionada = view2.getTabelaPlanos().getSelectedRow();
        if(linhaSelecionada>-1){
            int chavePlano = Integer.parseInt(tabelaDePlanosAdicionais.getValueAt(linhaSelecionada, 0).toString());
            ArrayList<Vendas> vendas = vendasDao.pesquisarVendas("SELECT * FROM tblVendas WHERE chavePlano = "+chavePlano);
            
            if(vendas!=null){
                for(Vendas venda : vendas){
                    String dataVenda = converterData.parseDate(venda.getDataVenda());                    
                    Object[] dadosTabela = {venda.getChaveVenda(),dataVenda, venda.getValorVenda()};
                    tabelaDePagamentos.addRow(dadosTabela);
                }
                
            }
        }
    }
    
    private void liberarBotoesAdicionais(){
        int quantLinhas = view2.getTabelaPlanos().getRowCount();
        boolean visibilidade = false;
        if(quantLinhas>0){
            for(int linhas=0; linhas<quantLinhas; linhas++){
                if(!tabelaDePlanosAdicionais.getValueAt(linhas, 4).toString().equals("Encerrado")){
                    visibilidade = true;
                }
            }
        }
        else{
            visibilidade = false;
        }
        view2.getBotaoCadNvo().setVisible(visibilidade);
        view2.getBotaoReativarPlano().setVisible(visibilidade);
        
    }
    
    public void reativarPlano(){
        int quantLinhas = tabelaDePlanosAdicionais.getRowCount();
        if(quantLinhas>0){
            boolean situacaoAberta = false;
            for(int linhas=0; linhas<quantLinhas; linhas++){
                if(tabelaDePlanosAdicionais.getValueAt(linhas, 4).toString().equals("Em Aberto")){
                    situacaoAberta = true;
                }
            }
            
            if(!situacaoAberta){
                int linhaSelecionadaAlunos = view.getTabelaAlunos().getSelectedRow();
                int linhaSelecionadaPlanos = view2.getTabelaPlanos().getSelectedRow();
                if(linhaSelecionadaPlanos>-1){
                    view2.getCampoNovoPlano().setSelected(false);

                    long chavePlano = Long.parseLong(tabelaDePlanos.getValueAt(linhaSelecionadaPlanos, 0).toString());
                    String nomeTurma = tabelaDePlanosAdicionais.getValueAt(linhaSelecionadaPlanos, 1).toString().split("\\.")[0];
                    int codTurma = Integer.parseInt(nomeTurma);

                    String nomeServico = tabelaDePlanosAdicionais.getValueAt(linhaSelecionadaPlanos, 2).toString().split("\\.")[0];
                    int codServico = Integer.parseInt(nomeServico);

                    Turmas turma = turmasDao.pesquisarTurmas("SELECT * FROM tblTurmas WHERE codTurma = "+codTurma).get(0);
                    Servicos servico = servicosDao.pesquisarServicos("SELECT * FROM tblServicos WHERE codServico = "+codServico).get(0);
                    view2.getComboTurmas().setSelectedIndex(0);
                    view2.getComboServicos().setSelectedIndex(0);
                    if(!turma.getSituacao().equals("Encerrada")){
                        view2.getComboTurmas().setSelectedItem(turma.getCodBanco()+"."+turma.getNomeTurma());
                    }
                    
                    if(!servico.getSituacao().equals("Encerrado")){
                        view2.getComboServicos().setSelectedItem(servico.getCodBanco()+"."+servico.getNome()+"-"+servico.getPeriodo());
                    }
                    view2.getPainelCadTurma().setVisible(true);
                }
            }
            else{
                view.exibeMensagem("Necessário que todos os planos estejam encerrados!");
            }
        }
    }
    
    public void cancelarReativacaoPlano(){
        view2.getComboServicos().setSelectedIndex(0);
        view2.getComboTurmas().setSelectedIndex(0);
        view2.getCampoValorMensal().setText("");
        view2.getCampoValorTotal().setText("");
        view2.getComboDiaVencimento().setSelectedItem("30");
        view2.getCampoRenovAutomatica().setSelected(false);
    }
    
    public void novoPlano(){
        int quantLinhas = tabelaDePlanosAdicionais.getRowCount();
        if(quantLinhas>0){
            boolean situacaoAberta = false;
            for(int linhas=0; linhas<quantLinhas; linhas++){
                if(tabelaDePlanosAdicionais.getValueAt(linhas, 4).toString().equals("Em Aberto")){
                    situacaoAberta = true;
                }
            }
            
            if(!situacaoAberta){
                cancelarReativacaoPlano();
                view2.getCampoNovoPlano().setSelected(true);
            }
            else{
                view.exibeMensagem("Necessário que todos os planos estejam encerrados!");
            }
        }
    }
    
    public void selecionarComboServicosPlanosAdicional(){
        int linhaSelecionada = view2.getComboServicos().getSelectedIndex();
        if(linhaSelecionada>0){
            String nomeServico = view2.getComboServicos().getSelectedItem().toString().split("\\.")[0];
            int codServico = Integer.parseInt(nomeServico);
            Servicos servico = servicosDao.pesquisarServicos("SELECT * FROM tblServicos WHERE codServico = "+codServico).get(0);
            setarValorContratoPlanoAdicional(servico);
        }
    }
    
    private void setarValorContratoPlanoAdicional(Servicos servico){
        if(view.getComboServicos().getSelectedIndex()>=0){

            String metodoDePagamento = servico.getFormaPagamento();

            BigDecimal valorContrato = null;
            if(metodoDePagamento.equals("[Nenhuma]")){valorContrato = new BigDecimal(servico.getValor().toString());}
            if(metodoDePagamento.equals("Dinheiro")){valorContrato = new BigDecimal(servico.getValorVista().toString());}
            if(metodoDePagamento.equals("Boleto")){valorContrato = new BigDecimal(servico.getValorBoleto().toString());}
            if(metodoDePagamento.equals("Cartão de Crédito")){valorContrato = new BigDecimal(servico.getValorPrazoCredito().toString());}
            if(metodoDePagamento.equals("Cartão de Débito")){valorContrato = new BigDecimal(servico.getValorPrazoDebito().toString());}   

            this.setarValorMensalPlanoAdicional(servico);
            view2.getCampoValorTotal().setText(valorContrato.toString());
        }
    }
    
    private void setarValorMensalPlanoAdicional(Servicos servicoContratado){
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

        if(resultadoMensal||resultadoAnual){
            view2.getComboDiaVencimento().setEnabled(true);
            if(resultadoMensal){
               BigDecimal period = periodDays.divide(new BigDecimal(30), 2, RoundingMode.HALF_UP);
               valorMensal = valorTotal.divide(period, 2, RoundingMode.UP);
               valorMensal = valorMensal.setScale(2, RoundingMode.UP);
               view2.getCampoValorMensal().setText(valorMensal.toString());
            }
            else{
                BigDecimal period = periodDays.divide(new BigDecimal(365), 2, RoundingMode.HALF_UP);
                period = period.multiply(new BigDecimal(12));
                valorMensal = valorTotal.divide(period, 2, RoundingMode.UP);
                valorMensal = valorMensal.setScale(2, RoundingMode.UP);
                view2.getCampoValorMensal().setText(valorMensal.toString());
            }    
        }
        else{
            LocalDate dataAtual = LocalDate.now().plusDays(servicoContratado.getPeriodDays());
            int dia = dataAtual.getDayOfMonth();
            view2.getComboDiaVencimento().setEnabled(false);
            if(dia>30){
                dia = 1;
            }
            view2.getComboDiaVencimento().setSelectedIndex(dia);
            
            boolean renovacaoAutomatica = view2.getCampoRenovAutomatica().isSelected();
            
            if(renovacaoAutomatica){
                BigDecimal period = (new BigDecimal(30)).divide(periodDays,4, RoundingMode.UP);
                valorMensal = valorTotal.multiply(period);
                valorMensal = valorMensal.setScale(2, RoundingMode.UP);

                view2.getCampoValorMensal().setText(valorMensal.toString());
            }
            else{
                valorMensal = valorTotal;
                view2.getCampoValorMensal().setText(valorMensal.toString());
            }  
        }  
    }
    
    
    
    private int diaVencimentoPlanoAdicional(int diasContrato){
        if(view2.getComboDiaVencimento().isEnabled()){
            return Integer.parseInt(view2.getComboDiaVencimento().getSelectedItem().toString());
        }
        else{
            LocalDate dataAtual = LocalDate.now();
            dataAtual = dataAtual.plusDays(diasContrato);
            return dataAtual.getDayOfMonth();
        }
    }
    
    //Setar Novo Plano ou Atualizar Selecionado
    public void reativarAluno(){
        boolean novoPlano = view2.getCampoNovoPlano().isSelected();
        
        if(novoPlano){
            this.cadastrarNovoPlano();
        }
        else{
            this.atualizaçãoDePlanos();
        }
    }
    
    private void atualizaçãoDePlanos(){
        int linhaSelecionadaAlunos = view.getTabelaAlunos().getSelectedRow();
        int linhaSelecionadaPlanos = view2.getTabelaPlanos().getSelectedRow();
        
        if(view2.getComboTurmas().getSelectedIndex()==0||view2.getComboServicos().getSelectedIndex()==0){
            int codAluno = Integer.parseInt(tabelaDeAlunos.getValueAt(linhaSelecionadaAlunos, 0).toString());
            //Dados da Turma
            String nomeTurma = view2.getComboTurmas().getSelectedItem().toString();
            int codTurma = Integer.parseInt(nomeTurma);
            
            //Dados do Serviço
            String nomeServico = view2.getComboServicos().getSelectedItem().toString();
            int codServico = Integer.parseInt(nomeTurma);
            
            //Dados Plano
            long chavePlano = Long.parseLong(tabelaDePlanosAdicionais.getValueAt(linhaSelecionadaPlanos, 0).toString());
            Planos planoAnterior = planosDao.pesquisarPlanos("SELECT * FROM tblPlanos WHERE chavePlano = "+chavePlano).get(0);
            int renovacaoAutomatica = 0;
            if(view2.getCampoRenovAutomatica().isSelected()){
                renovacaoAutomatica = 1;
            }
            
            int diaVencimento = Integer.parseInt(view2.getComboDiaVencimento().getSelectedItem().toString());
            
            BigDecimal valorTotal = converterDinheiro.converterParaBigDecimal(view2.getCampoValorTotal().getText());
            BigDecimal valorMensal = converterDinheiro.converterParaBigDecimal(view2.getCampoValorMensal().getText());
            
            Date dataRenovacao = converterData.getSqlDate(converterData.conversaoLocalforDate(LocalDate.now()));
            Date dataVencimento = dataVencimentoAdicional(codServico, diaVencimento);
            
            String situacao = "Pendente";
            
            Planos planoAtual = new Planos(chavePlano, codAluno, codTurma, codServico, diaVencimento, dataVencimento, planoAnterior.getDataPagamento(), planoAnterior.getDataCancelamento(), dataRenovacao, situacao);
            Aluno aluno = new Aluno(codTurma, codServico, valorMensal, valorTotal, valorMensal, renovacaoAutomatica, codAluno);
            
            alunosDao.atualizarPlanoAdicional(aluno);
            planosDao.atualizarDados(planoAtual);
            planosDao.atualizarSituacao(planoAtual);
        } 
    }
    
    private void cadastrarNovoPlano(){
        try{
            int linhaSelecionadaAlunos = view.getTabelaAlunos().getSelectedRow();

            if(view2.getComboTurmas().getSelectedIndex()==0||view2.getComboServicos().getSelectedIndex()==0){
                int codAluno = Integer.parseInt(tabelaDeAlunos.getValueAt(linhaSelecionadaAlunos, 0).toString());
                //Dados da Turma
                String nomeTurma = view2.getComboTurmas().getSelectedItem().toString();
                int codTurma = Integer.parseInt(nomeTurma);

                //Dados do Serviço
                String nomeServico = view2.getComboServicos().getSelectedItem().toString();
                int codServico = Integer.parseInt(nomeTurma);

                //Dados Plano
                long chavePlano = verificar.verificarUltimo("tblPlanos", "chavePlano")+1;
                int renovacaoAutomatica = 0;
                if(view2.getCampoRenovAutomatica().isSelected()){
                    renovacaoAutomatica = 1;
                }

                int diaVencimento = Integer.parseInt(view2.getComboDiaVencimento().getSelectedItem().toString());

                BigDecimal valorTotal = converterDinheiro.converterParaBigDecimal(view2.getCampoValorTotal().getText());
                BigDecimal valorMensal = converterDinheiro.converterParaBigDecimal(view2.getCampoValorMensal().getText());

                Date dataRenovacao = converterData.getSqlDate(converterData.conversaoLocalforDate(LocalDate.now()));
                Date dataVencimento = dataVencimentoAdicional(codServico, diaVencimento);

                String situacao = "Pendente";

                Planos planoAtual = new Planos(chavePlano, codAluno, codTurma, codServico, diaVencimento, dataVencimento, null, null, dataRenovacao, situacao);
                Aluno aluno = new Aluno(codTurma, codServico, valorMensal, valorTotal, valorMensal, renovacaoAutomatica, codAluno);

                alunosDao.atualizarPlanoAdicional(aluno);
                planosDao.inserirDados(planoAtual);
            } 
        } catch (SQLException ex) {
            gerarLog(ex);
        }
        
    }
    
    private Date dataVencimentoAdicional(int codServico, int diaVencimento){
        Servicos servico = servicosDao.pesquisarServicos("SELECT * FROM tblServicos WHERE codServico = "+codServico).get(0);
        BigDecimal periodDays = new BigDecimal(servico.getPeriodDays());
        LocalDate dataUsual = LocalDate.now();
        
        
        BigDecimal periodoMensal = periodDays.divide(new BigDecimal(30), 4, RoundingMode.UP);
        periodoMensal = periodoMensal.stripTrailingZeros();
        BigDecimal periodoAnual = periodDays.divide(new BigDecimal(365), 4, RoundingMode.UP);
        periodoAnual = periodoAnual.stripTrailingZeros();
        
        boolean mensal = periodoMensal.toPlainString().matches("[1-9]*");
        boolean anual = periodoAnual.toPlainString().matches("[1-9]*");
        
        if(mensal||anual){
            if(dataUsual.plusMonths(1).getMonth().compareTo(Month.FEBRUARY)==0&&diaVencimento>28){
                diaVencimento = 28;
            }
            dataUsual = LocalDate.of(dataUsual.plusMonths(1).getYear(), dataUsual.plusMonths(1).getMonthValue(), diaVencimento);
            return converterData.conversaoLocalforDate(dataUsual);
        }
        else{
            dataUsual = dataUsual.plusDays(servico.getPeriodDays());
            return converterData.conversaoLocalforDate(dataUsual);
        }
    }
    
    //PAINEL OBSERVAÇÕES
    private void setarObservacoes() {
        int linhaSelecionada = view.getTabelaAlunos().getSelectedRow();
        if(linhaSelecionada>-1){
            int codAluno = Integer.parseInt(tabelaDeAlunos.getValueAt(linhaSelecionada, 0).toString());
            Aluno aluno = this.alunoAnterior(codAluno);
            
            view3.getCampoObservacao().setText("");
            view3.getCampoObservacao().append(aluno.getDescricao());
        }
    }
    
    public void adicionarDescricao(){
        int linhaSelecionada = view.getTabelaAlunos().getSelectedRow();
        if(linhaSelecionada>-1){
            int codAluno = Integer.parseInt(tabelaDeAlunos.getValueAt(linhaSelecionada, 0).toString());
            String observacao = view3.getCampoObservacao().getText();
            Aluno aluno = this.alunoAnterior(codAluno);
            
            if(!aluno.getDescricao().equals(observacao)){
                editarAluno();
            }
        }
    }
    
    public void ajuda(){
        String atalhos = "\u07CBESC = Sair da Tela\n"
                + "\u07CBF5 = Atualizar Tabela\n"
                + "\u07CBDEL = Remover Aluno\n"
                + "\u07CBESQUERDA = Movimentar Painéis\n"
                + "\u07CBDIREITA = Movimentar Painéis\n"
                + "\u07CBCTRL + E = Editar Aluno\n"
                + "\u07CBCTRL + N = Cadastrar Novo Funcionário\n"
                + "\u07CBCTRL + T = Trocar Modo de Turma\n"
                + "\u07CBCTRL + END = Finalizar Plano Ativo\n";
        
        view.getPainelAjuda().setModal(true);
        view.getPainelAjuda().getCampoAtalhos().setText("");
        view.getPainelAjuda().getCampoAtalhos().append(atalhos);
        view.getPainelAjuda().setVisible(true);
    }
    
    
    //Gerar arquivo com o log de erro, caso haja
    private void gerarLog(Throwable erro){
        LogsSystem gerarLog = new LogsSystem();
        gerarLog.gravarErro(erro);
        gerarLog.close();
    }

    public void sairTela(){
        funcionarioDao.atualizarStatusAll();
        Funcionario funcionario = this.setarLog("Saída do Sistema", null);
        view.getParent().dispose();
        if(funcionario==null||!funcionario.getCargo().equals("Gerente")){
            LoginFuncionario jump = new LoginFuncionario();
            jump.setVisible(true);
        }
        else{
            LoginGerente jump = new LoginGerente();
            jump.setVisible(true);
        }
    }
    
    private Funcionario setarLog(String acao, String descricao){
        ArrayList <Funcionario> funcionarios = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE status = 'Ativo'");
        if(funcionarios!=null){
            Funcionario funcionario = funcionarios.get(0);
            Date dataEvento = new Date();
            LogAçoesFuncionario logAcao = new LogAçoesFuncionario(funcionario.getCodBanco(), dataEvento, acao, descricao);
            logDao.inserirDados(logAcao);
            return funcionario;
        }
        return null;
    }
    
}
