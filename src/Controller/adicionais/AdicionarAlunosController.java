/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.adicionais;

import Controller.auxiliar.ConversaoDeDinheiro;
import Controller.auxiliar.ConversaoDiasDaSemana;
import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Controller.auxiliar.ExportarArquivos;
import Controller.auxiliar.ImpressaoComponentes;
import Controller.auxiliar.LogsSystem;
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
import View.LoginFuncionario;
import View.LoginGerente;
import static java.lang.Thread.sleep;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

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
    private final ImpressaoComponentes imprimirContrato = new ImpressaoComponentes();
    private final ExportarArquivos exportarContrato = new ExportarArquivos();
    
    public AdicionarAlunosController(AlunosCadastro view) {
        this.view = view;
    }
    
    public void verificacaoDeTurmaEServico(){
        ArrayList <Turmas> turmas = turmasDao.pesquisarTurmas("SELECT * FROM tblTurmas WHERE situacao != 'Encerrada'");
        ArrayList <Servicos> servicos = servicosDao.pesquisarServicos("SELECT * FROM tblServicos WHERE situacao != 'Encerrado'");
        limparCombos();
        
        if(turmas==null){
            view.exibeMensagem("Sem Turmas Cadastradas Ou Abertas! Adicione Alguma Para Entrar Nessa Tela!");
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
            view.exibeMensagem("Sem Serviços Cadastrados ou Abertos! Adicione Algum Para Entrar Nessa Tela!");
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
    
    public void adicionarAlunos() {
        try{
        Date dataCadastro = converterData.getSqlDate(new Date());
        if(view.getCampoDataCadastro().getDate()!=null){
            dataCadastro = view.getCampoDataCadastro().getDate();
        }
        Date dataUltimoPag = null;
        if(view.getCampoDataUltimoPag().getDate()!= null){
            dataUltimoPag = view.getCampoDataUltimoPag().getDate();
        }

        //Dados do Aluno
        int codAluno = (int) (verificar.verificarUltimo("tblAlunos", "codAluno")+1);
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
        String descricao = view.getCampoDescricao().getText(); //Nula por enquanto
        BigDecimal valorContrato = new BigDecimal(converterDinheiro.converterParaBigDecimal(view.getCampoValor().getText()).toString());
        BigDecimal valorMensal = new BigDecimal(converterDinheiro.converterParaBigDecimal(view.getCampoValorMensal().getText()).toString());
        BigDecimal debito = valorMensal;
        
        if(dataUltimoPag!=null){
            debito = BigDecimal.ZERO;
        }
        
        //Dados de Matrícula e Serviços
        int codServico = 0;
        int codTurma = 0;
        if(!plano.equals("[Nenhum]")||!turma.equals("[Nenhuma]")){
        codServico = Integer.parseInt(plano.split("\\.")[0]);
        codTurma = Integer.parseInt(turma.split("\\.")[0]);
        }
        
        
        //Dados de dias da semana e ano atual
        int anoAtual = LocalDate.now().getYear();
        
        //Matrícula em String
        String matriculaObtida = this.converterMatricula(anoAtual, codTurma, codAluno, codServico);
        
        //dados do Endereço
        String logradouro = view.getCampoRua().getText();
        String bairro = view.getCampoBairro().getText();
        String numero = view.getCampoNum().getText();
        String cidade = view.getCampoCidade().getText();
        String estado = view.getComboEstado().getSelectedItem().toString();
        String cep = view.getCampoCEP().getText();
        String complemento = "";
        String referencia = "";

        Servicos servicoContratado = this.buscarServico(codServico);
        
        //Dados do Plano
        long chavePlano = verificar.verificarUltimo("tblPlanos","chavePlano")+1;
        int diaVencimento = this.diaVencimento(servicoContratado.getPeriodDays());
        int renovacaoAutomatica =0;
        if(view.getRenovacaoAuto().isSelected()){
        renovacaoAutomatica = 1;
        }
        String situacao = "Pendente";
        if(view.getCampoDataUltimoPag().getDate()!= null){
            situacao = "Pago";
        }

        //Cria os tipos Aluno, Endereco e Matricula com os dados
        
        Aluno aluno = new Aluno(codAluno, nome, cpf, rg, telefone, celular, email, 
        dataNascimento, nomeMae, nomePai, contatoMae, contatoPai, cpfMae, cpfPai, codTurma, codServico, descricao, debito, valorContrato, dataCadastro, valorMensal, renovacaoAutomatica);
        EnderecoAlunos endereco = new EnderecoAlunos(logradouro, bairro, numero, complemento, referencia, cidade, estado, cep);
        Matriculas matricula = new Matriculas(codTurma, anoAtual, matriculaObtida);
        
        Date dataVencimento = this.dataVencimento(aluno, servicoContratado, dataUltimoPag, diaVencimento);
        Planos planoAluno = new Planos(chavePlano, codAluno, codTurma, codServico, diaVencimento, dataCadastro, dataVencimento, dataUltimoPag, null, null, situacao);
        
        
        //Obtem a quantidade de alunos presentes na turma
        int quantAlunosPresentes = (int) (verificar.verificarUltimo("tblTurmas", "quantAlunos")+1);
        
        //Verifica se não há dados irregulares antes de colocar na tabela
        if(nome.equals("")||plano.equals("[Nenhum]") || dataNascimento == null||turma.equals("[Nenhuma]")
               || logradouro.equals("")||bairro.equals("")||numero.equals("")||cidade.equals("")
                ||estado.equals("[Nenhum]")||cep.equals("  .   -   ") || view.getCampoDiaVencimento()==null){
        view.exibeMensagem("Valores Preenchidos Incorretamente!");
        }
        
        else{
            alunosDao.inserirDados(aluno, endereco, matricula, planoAluno);
            turmasDao.atualizarQuantAunos(codTurma, quantAlunosPresentes);
            
            this.setarLog(nome, turma);
            
            view.exibeMensagem("Sucesso!");
            
            int confirmacao = JOptionPane.showConfirmDialog(null, "Deseja realizar a impressão do contrato?", 
                    "Nota", JOptionPane.YES_NO_OPTION);
            //Imprimir se Ativada a impressão do contrato
            if(confirmacao == JOptionPane.YES_OPTION){
                boolean confirmaco = exportarContrato.exportarContratoWord(aluno, endereco, servicoContratado, matricula, servicoContratado.getPeriodDays());
                
                sleep(10);
                if(confirmaco){
                    exportarContrato.ConvertToPDF("C:/Rehabiliter/ContratoEditado.docx");
                    view.exibeMensagem("Exportando Arquivo para Impressão.");
                    imprimirContrato.impressao("C:/Rehabiliter/ContratoEditado.pdf");
                }
                
            }
            
            this.limparCampos();
        }
        } catch (SQLException | InvalidFormatException | InterruptedException ex) {
            this.gerarLog(ex);
            view.exibeMensagem("Não foi possível Salvar o Aluno corretamente.");
            this.limparCampos();
        }
        
    }
    
    

    //Pega o código da turma e retorna os dias que ela se aplica
    private String diasDaSemana(int codTurma){
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
    
    public void setarValorContrato() {
        if(view.getComboPlano().getSelectedIndex()>0){
            String nomeServico = view.getComboPlano().getSelectedItem().toString();
            int codServico = Integer.parseInt(nomeServico.split("\\.")[0]);
            Servicos servico = this.buscarServico(codServico);
            view.getCampoValor().setText(servico.getValor().toString());
            this.setarValorMensal(servico);
        }
    }
    
    public void limparCombos(){
        view.getComboTurma().removeAllItems();
        view.getComboTurma().addItem("[Nenhuma]");
        view.getComboPlano().removeAllItems();
        view.getComboPlano().addItem("[Nenhum]");
    }
    
    public void verificarQuantidadeLimiteAlunos() {
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
    
    
    
    private Servicos buscarServico(int codServico){
        return servicosDao.pesquisarServicos("SELECT * FROM tblServicos WHERE codServico = "+codServico).get(0);
    }
    
    private void setarValorMensal(Servicos servicoContratado){
        BigDecimal periodDays = new BigDecimal(servicoContratado.getPeriodDays());
        BigDecimal valorTotal = servicoContratado.getValor();
        
        BigDecimal valorMensal = new BigDecimal(0);
        String mensal = periodDays.divide(new BigDecimal(30), 2, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
        String anual = periodDays.divide(new BigDecimal(365), 3, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();

        
        boolean resultadoMensal = mensal.matches("[1-9]*");
        boolean resultadoAnual = anual.matches("[1-9]*");

        if(resultadoMensal||resultadoAnual){
            view.getCampoDiaVencimento().setEnabled(true);
            if(resultadoMensal){
               BigDecimal period = periodDays.divide(new BigDecimal(30), 2, RoundingMode.HALF_UP);
               valorMensal = valorTotal.divide(period, 2, RoundingMode.UP);
               valorMensal = valorMensal.setScale(2, RoundingMode.UP);
               view.getCampoValorMensal().setText(valorMensal.toString());
            }
            else{
                BigDecimal period = periodDays.divide(new BigDecimal(365), 2, RoundingMode.HALF_UP);
                period = period.multiply(new BigDecimal(12));
                valorMensal = valorTotal.divide(period, 2, RoundingMode.UP);
                valorMensal = valorMensal.setScale(2, RoundingMode.UP);
                view.getCampoValorMensal().setText(valorMensal.toString());
            }    
        }
        else{
            view.getCampoDiaVencimento().setEnabled(false);
            boolean renovacaoAutomatica = view.getRenovacaoAuto().isSelected();
            if(renovacaoAutomatica){
                BigDecimal period = (new BigDecimal(30)).divide(periodDays,4, RoundingMode.UP);
                valorMensal = valorTotal.multiply(period);
                valorMensal = valorMensal.setScale(2, RoundingMode.UP);

                view.getCampoValorMensal().setText(valorMensal.toString());
            }
            else{
                valorMensal = valorTotal;
                view.getCampoValorMensal().setText(valorMensal.toString());
            }
        }  
    }
    
    private int diaVencimento(int diasContrato){
        if(view.getCampoDiaVencimento().isEnabled()){
            return view.getCampoDiaVencimento().getDay();
        }
        else{
            LocalDate dataAtual = LocalDate.now();
            dataAtual = dataAtual.plusDays(diasContrato);
            return dataAtual.getDayOfMonth();
        }
    }
    
    private Date dataVencimento(Aluno aluno, Servicos servico, Date dataPagamento, int diaVencimento){
        BigDecimal periodDays = new BigDecimal(servico.getPeriodDays());
        LocalDate dataVencimento;
        
        int renovacaoAutomatica = aluno.getRenovacaoAutomatica();
        
        Date dataPag = converterData.parseDate(converterData.parseDate(dataPagamento));
        Date dataPrimaria = converterData.parseDate(converterData.parseDate(aluno.getDataCadastro()));
        LocalDate dataBanco = converterData.conversaoLocalforDate(dataPag);
        LocalDate dataCad = converterData.conversaoLocalforDate(dataPrimaria);
        
        BigDecimal valorMensal = aluno.getValorMensal();

        String mensal = periodDays.divide(new BigDecimal(30), 2, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
        String anual = periodDays.divide(new BigDecimal(365), 3, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();

        
        boolean resultadoMensal = mensal.matches("[1-9]*");
        boolean resultadoAnual = anual.matches("[1-9]*");
        
        
        if(resultadoMensal||resultadoAnual){
            if(dataCad.plusMonths(1).getMonth().compareTo(Month.FEBRUARY)==0&&diaVencimento>28){
                           diaVencimento = 28;
            }
           if(dataPagamento==null){
               dataVencimento = LocalDate.of(dataCad.plusMonths(1).getYear(), dataCad.plusMonths(1).getMonthValue(), diaVencimento);
               return converterData.conversaoLocalforDate(dataVencimento);
           }
           else{
               dataVencimento = LocalDate.of(dataBanco.plusMonths(1).getYear(), dataBanco.plusMonths(1).getMonthValue(), diaVencimento);
               return converterData.conversaoLocalforDate(dataVencimento);
           }
        }
        else{
            if(renovacaoAutomatica == 1){
                if(dataPagamento==null){
                    dataVencimento = dataCad.plusDays(servico.getPeriodDays());
                    return converterData.conversaoLocalforDate(dataVencimento);
                }
                else{
                    dataVencimento = dataBanco.plusDays(servico.getPeriodDays());
                    return converterData.conversaoLocalforDate(dataVencimento);
                }   
            }
            else{
               dataVencimento = dataCad.plusDays(servico.getPeriodDays());
               return converterData.conversaoLocalforDate(dataVencimento);
            }           
        } 
    }
    
    public void limparCampos(){
        //Limpando Campos
            view.getCampoNomeAluno().setText("");
            view.getCampoCPFAluno().setValue(null);
            view.getCampoTelefone().setValue(null);
            view.getCampoCelular().setValue(null);
            view.getCampoNomePai().setText("");
            view.getCampoNomeMae().setText("");
            view.getCampoContatoPai().setValue(null);
            view.getCampoContatoMae().setValue(null);
            view.getCampoCPFPai().setValue(null);
            view.getCampoCPFMae().setValue(null);
            view.getComboPlano().setSelectedIndex(0);
            view.getComboTurma().setSelectedIndex(0);
            view.getCampoValor().setText("");
            view.getCampoValorMensal().setText("");
            view.getCampoRua().setText("");
            view.getCampoNum().setText("");
            view.getCampoBairro().setText("");
            view.getCampoCidade().setText("Santa Inês");
            view.getCampoCEP().setValue(null);
            view.getComboEstado().setSelectedItem("MA");
            view.getjPanelCadastroFinal().setVisible(false);
            view.getJpanelDados().setVisible(true);
            view.getCampoNascimentoAluno().setDate(null);
            view.getCampoDataCadastro().setDate(null);
            view.getCampoDataUltimoPag().setDate(null);
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
    
    private void gerarLog(Throwable erro){
        LogsSystem gerarLog = new LogsSystem();
        gerarLog.gravarErro(erro);
        gerarLog.close();
    }
    
    private Funcionario setarLog(String nomeAluno, String nomeTurma){
        ArrayList <Funcionario> funcionarios = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE status = 'Ativo'");
            if(funcionarios!=null){
                Funcionario funcionario = funcionarios.get(0);
                Date dataEvento = new Date();
                LogAçoesFuncionario logAcao = new LogAçoesFuncionario(funcionario.getCodBanco(), dataEvento, "Cadastro de Aluno", "Cadastrou o aluno "+nomeAluno+"na Turma "+nomeTurma);
                return funcionario;
            }
        return null;
    }
}
