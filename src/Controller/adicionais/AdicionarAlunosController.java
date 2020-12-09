/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.adicionais;

import Controller.auxiliar.ConversaoDeDinheiro;
import Controller.auxiliar.ConversaoDiasDaSemana;
import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Controller.auxiliar.VerificarCodigoNoBanco;
import Dao.AlunosDao;
import Dao.EnderecoAlunosDao;
import Dao.FuncionarioDao;
import Dao.LogAçoesFuncionarioDao;
import Dao.MatriculasDao;
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
import View.AlunosCadastro;
import View.ServicosView;
import View.TurmasView;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Mayro
 */
public class AdicionarAlunosController {
    private final AlunosCadastro view;
    private final TurmasDao turmasDao = new TurmasDao();
    private final ServicosDao servicosDao = new ServicosDao();
    private final AlunosDao alunosDao = new AlunosDao();
    private final EnderecoAlunosDao enderecoDao = new EnderecoAlunosDao();
    private final MatriculasDao matriculaDao = new MatriculasDao();
    private final FuncionarioDao funcionarioDao = new FuncionarioDao();
    private final LogAçoesFuncionarioDao logDao = new LogAçoesFuncionarioDao();
    private final ConversaoDeDinheiro converterDinheiro = new ConversaoDeDinheiro();
    private final ConversaoDiasDaSemana converterDias = new ConversaoDiasDaSemana();
    private final VerificarCodigoNoBanco verificar = new VerificarCodigoNoBanco();
    private final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();
    
    public AdicionarAlunosController(AlunosCadastro view) {
        this.view = view;
    }
    
    public void verificacaoDeTurmaEServico() throws SQLException{
        ArrayList <Turmas> turmas = turmasDao.selecionarTodasTurmas();
        ArrayList <Servicos> servicos = servicosDao.selecionarTodosServicos();
        ServicosView telaServicos = new ServicosView();
        TurmasView telaTurmas = new TurmasView();
        limparCombos();
        
        if(turmas==null){
            view.exibeMensagem("Sem Turmas Cadastradas! Adicione Alguma Para Entrar Nessa Tela!");
            telaTurmas.setVisible(true);
            if(view.isVisible()){
               view.dispose(); 
            }
        }
        else{
            for(int linhas=0; linhas<turmas.size(); linhas++){
            view.getComboTurma().addItem(turmas.get(linhas).getCodBanco()+"."+turmas.get(linhas).getNomeTurma());
            }
        }
        
        if(servicos==null){
            view.exibeMensagem("Sem Serviços Cadastrados! Adicione Algum Para Entrar Nessa Tela!");
            telaServicos.setVisible(true);
            if(view.isVisible()){
               view.dispose(); 
            }
        }
        else{
            for(int linhas=0; linhas<servicos.size(); linhas++){ 
            String periodo = servicos.get(linhas).getPeriodo();
            String abreviatura = periodo.substring(0, 3);
            if(periodo.equals("Semestral")){abreviatura = abreviatura.toUpperCase();}
            view.getComboPlano().addItem(servicos.get(linhas).getCodBanco()+"."+servicos.get(linhas).getNome()+" |"+abreviatura+"| "+servicos.get(linhas).getFormaPagamento());
            }
        }
    }
    
    public void adicionarAlunos() throws SQLException, ParseException{
        Date dataCadastro = converterData.getSqlDate(new Date());

        //Dados do Aluno
        int codAluno = verificar.verificarUltimo("tblAlunos", "codAluno")+1;
        String nome = view.getCampoNomeAluno().getText();
        String cpf = view.getCampoCPFAluno().getText();
        String rg = ""; //Como não está na tabela é nula
        
        Date dataNascimento = view.getCampoNascimentoAluno().getDate();
        
        String telefone = view.getCampoTelefone().getText();
        String celular = view.getCampoCelular().getText();
        String email=""; //Nula por enquanto
        String nomePai = view.getCampoNomePai().getText();
        String nomeMae = view.getCampoNomeMae().getText();
        String contatoPai = view.getCampoContatoPai().getText();
        String contatoMae = view.getCampoContatoMae().getText();
        String cpfMae = view.getCampoCPFMae().getText();
        String cpfPai = view.getCampoCPFPai().getText();
        String plano = view.getComboPlano().getSelectedItem().toString();
        String turma = view.getComboTurma().getSelectedItem().toString();
        String descricao = ""; //Nula por enquanto
        BigDecimal valorContrato = new BigDecimal(converterDinheiro.converterParaBigDecimal(view.getCampoValor().getText()).toString());
        BigDecimal debito = valorContrato;
        
        //Dados de Matrícula e Serviços
        int codMatricula = verificar.verificarUltimo("tblMatriculas", "codMatricula")+1;
        int codPlano = 0;
        int codTurma = 0;
        if(!plano.equals("[Nenhum]")||!turma.equals("[Nenhuma]")){
        codPlano = Integer.parseInt(plano.split("\\.")[0]);
        codTurma = Integer.parseInt(plano.split("\\.")[0]);
        }
        
        
        //Dados de dias da semana e ano atual
        int anoAtual = converterData.obterAnoAtual();
        
        //Matrícula em String
        String matriculaObtida = this.converterMatricula(anoAtual, codTurma, codAluno, codPlano);
        
        //dados do Endereço
        int codEndereco = verificar.verificarUltimo("tblEndAlunoseClientes","codEndAlunoseClientes ")+1;
        String logradouro = view.getCampoRua().getText();
        String bairro = view.getCampoBairro().getText();
        String numero = view.getCampoNum().getText();
        String cidade = view.getCampoCidade().getText();
        String estado = view.getComboEstado().getSelectedItem().toString();
        String cep = view.getCampoCEP().getText();
        String complemento = "";
        String referencia = "";

        
        //Dados do Plano
        int diaVencimento = view.getCampoDiaVencimento().getDay();
        
        //Cria os tipos Aluno, Endereco e Matricula com os dados
        Aluno aluno = new Aluno(codAluno, nome, cpf, rg, telefone, celular, email, dataNascimento, nomeMae, nomePai, contatoMae, contatoPai, cpfMae, cpfPai, codTurma, codPlano, descricao, debito, valorContrato, dataCadastro);
        EnderecoAlunos endereco = new EnderecoAlunos(codEndereco, codAluno, logradouro, bairro, numero, complemento, referencia, cidade, estado, cep);
        Matriculas matricula = new Matriculas(codMatricula, codTurma, codAluno, anoAtual, matriculaObtida);
        Planos planoAluno = new Planos(codAluno, codTurma, codPlano, diaVencimento, null, null, "Pendente");
        
        //Obtem a quantidade de alunos presentes na turma
        int quantAlunosPresentes = verificar.verificarUltimo("tblTurmas", "quantAlunos")+1;
        
        //Verifica se não há dados irregulares antes de colocar na tabela
        if(nome.equals("")||plano.equals("[Nenhum]") || dataNascimento == null||turma.equals("[Nenhuma]")
               || logradouro.equals("")||bairro.equals("")||numero.equals("")||cidade.equals("")
                ||estado.equals("[Nenhum]")||cep.equals("  .   -   ") || view.getCampoDiaVencimento()==null){
        view.exibeMensagem("Valores Preenchidos Incorretamente!");
        }
        
        else{
            alunosDao.inserirDados(aluno, endereco, matricula, planoAluno, codTurma, codTurma);
            turmasDao.atualizarQuantAunos(codTurma, quantAlunosPresentes);
            ArrayList <Funcionario> funcionarios = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE status = 'Ativo'");
            if(funcionarios!=null){
            this.setarLog(funcionarios, nome, turma);
            }
            view.exibeMensagem("Sucesso!");
            //Limpando Campos
            view.getCampoNomeAluno().setText("");
            view.getCampoCPFAluno().setText("");
            view.getCampoTelefone().setText("");
            view.getCampoCelular().setText("");
            view.getCampoNomePai().setText("");
            view.getCampoNomeMae().setText("");
            view.getCampoContatoPai().setText("");
            view.getCampoContatoMae().setText("");
            view.getCampoCPFPai().setText("");
            view.getCampoCPFMae().setText("");
            view.getComboPlano().setSelectedIndex(0);
            view.getComboTurma().setSelectedIndex(0);
            view.getCampoValor().setText("");
            view.getCampoRua().setText("");
            view.getCampoNum().setText("");
            view.getCampoBairro().setText("");
            view.getCampoCidade().setText("Santa Inês");
            view.getCampoCEP().setText("");
            view.getComboEstado().setSelectedIndex(0);
            view.getjPanelCadastroFinal().setVisible(false);
            view.getJpanelDados().setVisible(true);
        }
        
    }
    
    

    //Pega o código da turma e retorna os dias que ela se aplica
    private String diasDaSemana(int codTurma) throws SQLException{
        String diasDaSemana;
        ArrayList<Turmas> turmas = turmasDao.pesquisarTurmas("SELECT diasDaSemana FROM tblTurmas WHERE codTurma = "+codTurma);
        diasDaSemana = converterDias.converterDiasDaSemana(turmas.get(0).getDiasDaSemana());
        return diasDaSemana;
    }

    //Junta os dados do ano atual, código do aluno, turma e serviço escolhidos
    private String converterMatricula(int anoAtual, int codTurma, int codAluno, int codPlano) {
        String matricula = anoAtual+"T"+codTurma+"A"+codAluno+"S"+codPlano;
        
                return  matricula; 
    }
    
    public void setarValorContrato() throws SQLException{
        if(view.getComboPlano().getSelectedIndex()>0){
            String nomeServico = view.getComboPlano().getSelectedItem().toString();
            int codServico = Integer.parseInt(String.valueOf(nomeServico.charAt(0)));
            ArrayList<Servicos> servicos = servicosDao.pesquisarServicos("SELECT * FROM tblServicos WHERE codServico = "+codServico);
            
            String metodoDePagamento = servicos.get(0).getFormaPagamento();
            
            BigDecimal valorContrato = null;
            if(metodoDePagamento.equals("[Nenhuma]")){valorContrato = new BigDecimal(servicos.get(0).getValor().toString());}
            if(metodoDePagamento.equals("Dinheiro")){valorContrato = new BigDecimal(servicos.get(0).getValorVista().toString());}
            if(metodoDePagamento.equals("Boleto")){valorContrato = new BigDecimal(servicos.get(0).getValorBoleto().toString());}
            if(metodoDePagamento.equals("Cartão de Crédito")){valorContrato = new BigDecimal(servicos.get(0).getValorPrazoCredito().toString());}
            if(metodoDePagamento.equals("Cartão de Débito")){valorContrato = new BigDecimal(servicos.get(0).getValorPrazoDebito().toString());}   
            
            view.getCampoValor().setText(valorContrato.toString());
        }
    }
    
    public void limparCombos(){
        view.getComboTurma().removeAllItems();
        view.getComboTurma().addItem("[Nenhuma]");
        view.getComboPlano().removeAllItems();
        view.getComboPlano().addItem("[Nenhum]");
    }
    
    public void verificarQuantidadeLimiteAlunos() throws SQLException{
        int linhaSelecionada = view.getComboTurma().getSelectedIndex();
        if(linhaSelecionada>0){
            String nomeTurma = view.getComboTurma().getSelectedItem().toString();
            int codTurma = Integer.parseInt(nomeTurma.split("\\.")[0]);
            Turmas turma = turmasDao.pesquisarTurmas("SELECT * FROM tblTurmas WHERE codTurma = "+codTurma).get(0);
            if(turma.getQuantidadeMaximaAlunos() != 0){
                if(turma.getQuantidadeAlunos()==turma.getQuantidadeMaximaAlunos()){
                    view.exibeMensagem("Turma Completa! Por Favor Aumente o Limite em Turmas.");
                    view.getComboTurma().setSelectedIndex(0);
                }
            }
            
        }
    }
    
    private LogAçoesFuncionario setarLog(ArrayList <Funcionario> funcionarios, String nomeAluno, String nomeTurma){
        Funcionario funcionario = funcionarios.get(0);
        Date dataEvento = new Date();
        LogAçoesFuncionario logAcao = new LogAçoesFuncionario(funcionario.getCodBanco(), dataEvento, "Cadastro de Aluno", "Cadastrou o aluno "+nomeAluno+"na Turma "+nomeTurma);
        return logAcao;
    } 
}
