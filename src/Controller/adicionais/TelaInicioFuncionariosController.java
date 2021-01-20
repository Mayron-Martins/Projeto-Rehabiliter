/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.adicionais;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Dao.AlunosDao;
import Dao.FuncionarioDao;
import Dao.LogAçoesFuncionarioDao;
import Dao.PlanosDao;
import Dao.ServicosDao;
import Dao.TurmasDao;
import Model.Aluno;
import Model.Funcionario;
import Model.auxiliar.LogAçoesFuncionario;
import Model.auxiliar.Planos;
import Model.auxiliar.Servicos;
import Model.auxiliar.Turmas;
import View.telainicialFuncionario;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mayro
 */
public class TelaInicioFuncionariosController {
    private final telainicialFuncionario view;
    private final DefaultTableModel tabelaDeAlunos;
    private final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();
    private final AlunosDao alunosDao = new AlunosDao();
    private final TurmasDao turmasDao = new TurmasDao();
    private final PlanosDao planosDao = new PlanosDao();
    private final ServicosDao servicosDao = new ServicosDao();
    private final FuncionarioDao funcionarioDao = new FuncionarioDao();
    private final LogAçoesFuncionarioDao logDao = new LogAçoesFuncionarioDao();

    public TelaInicioFuncionariosController(telainicialFuncionario view) {
        this.view = view;
        this.tabelaDeAlunos = (DefaultTableModel) view.getTabelaAniversariantes().getModel();
    }
    
    public void limparTabela(){
        int quantLinhas = this.view.getTabelaAniversariantes().getRowCount();
        for(int quant=0; quant<quantLinhas; quant++){
            this.tabelaDeAlunos.removeRow(0);
        }
    }

    public void inicializarTabela(){
        ArrayList <Aluno> alunos = alunosDao.selecionarTodosAlunos();
        
        ArrayList <Aluno> alunosAniversariantes = new ArrayList<>();
        Date aniversario;
        if(alunos != null){
            for(int linhas = 0; linhas<alunos.size(); linhas++){
            aniversario = alunos.get(linhas).getDatadenascimento();
            if(converterData.anviversarianteMes(aniversario)){
                alunosAniversariantes.add(alunos.get(linhas));
            }
        }
        if(alunosAniversariantes!=null){
            this.buscas(alunosAniversariantes);
        }
        }
    }
    
    private void buscas(ArrayList <Aluno> listar){
        ArrayList<Turmas> turmas = new ArrayList<>();
        ArrayList <Aluno> alunos = listar;

        if(alunos.size()==0){view.getjScrollPane1().setVisible(false);}
        else{
            view.getjScrollPane1().setVisible(true);
            limparTabela();
            for(int linhas = 0; linhas<alunos.size(); linhas++){
            turmas = this.turmasDao.pesquisarTurmas("SELECT * FROM tblTurmas WHERE codTurma = "+
                    alunos.get(linhas).getTurma());

            //Inserindo dados na tabela de alunos
            String dataAniversario = converterData.parseDate(alunos.get(linhas).getDatadenascimento());
            Object[] dadosDaTabelaAlunos = {alunos.get(linhas).getNome(),turmas.get(0).getCodBanco()+"."+turmas.get(0).getNomeTurma(), dataAniversario};
            this.tabelaDeAlunos.addRow(dadosDaTabelaAlunos);
            }
        }
    }
    
    public void setarPlanos(){
        if(alunosDao.selecionarTodosAlunos()!=null){
            this.setarVencimentoPlanos();
            this.setarPendenciaPlanos();
        }
    }
    
    public void sairTela(){
        funcionarioDao.atualizarStatusAll();
        ArrayList <Funcionario> funcionarios = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE status = 'Ativo'");
        if(funcionarios!=null){
            this.setarLog(funcionarios);
        }
        view.dispose();
    }
    
    private void setarPendenciaPlanos(){
        LocalDate dataAtual = LocalDate.now();
        
        if(dataAtual.getDayOfMonth()==1){
            ArrayList <Planos> planos = planosDao.selecionarTodosPlanos();
            if(planos!=null){
                for(Planos plano : planos){
                    Planos planoAtual;
                    if(plano.getSituacao().equals("Pago")){
                        Aluno aluno = alunosDao.pesquisarAlunos("SELECT * FROM tblAlunos WHERE codAluno = "+plano.getCodAluno()).get(0);
                        Servicos servico = servicosDao.pesquisarServicos("SELECT * FROM tblServicos WHERE codServico = "+plano.getCodServico()).get(0);

                        Date dataUsual = converterData.parseDate(converterData.parseDate(aluno.getDataCadastro()));
                        if(plano.getDataRenovacao()!=null){
                            dataUsual = converterData.parseDate(converterData.parseDate(plano.getDataRenovacao()));
                        }
                        LocalDate dataVencimentoFinal = converterData.conversaoLocalforDate(dataUsual).plusDays(servico.getPeriodDays());



                        if(aluno.getRenovacaoAutomatica()==1){
                            planoAtual = new Planos(plano.getChavePlano(), plano.getCodAluno(), 0, 0, 0,plano.getDataVencimento(),
                                plano.getDataPagamento(), plano.getDataCancelamento(), 
                                plano.getDataRenovacao(),"Pendente");
                        }
                        else{
                            if(dataVencimentoFinal.isEqual(dataAtual)){
                                planoAtual = new Planos(plano.getChavePlano(), plano.getCodAluno(), 0, 0, 0, plano.getDataVencimento(),
                                plano.getDataPagamento(), plano.getDataCancelamento(), 
                                plano.getDataRenovacao(),"Encerrado"); 
                            }
                            else{
                                planoAtual = new Planos(plano.getChavePlano(), plano.getCodAluno(), 0, 0, 0, plano.getDataVencimento(),
                                plano.getDataPagamento(), plano.getDataCancelamento(), 
                                plano.getDataRenovacao(),"Pendente");
                            }

                        }
                        planosDao.atualizarSituacao(planoAtual);
                        if(!planoAtual.getSituacao().equals("Encerrado")){
                            this.setarDebitos(aluno);
                        }
                     }
             }
            }

     }
  }
    
    private void setarVencimentoPlanos(){
        LocalDate dataAtual = LocalDate.now();
        LocalDate dataVencimento;
        Period periodo;
        ArrayList <Planos> planos = planosDao.selecionarTodosPlanos();
        Planos planoAtual;
            
        int verificadorExistencia =0;
        if(planos!=null){
            for(Planos plano : planos){
                if(plano.getSituacao().equals("Pendente")){
                    if(plano.getDataVencimento()!=null){
                        Aluno aluno = alunosDao.pesquisarAlunos("SELECT * FROM tblAlunos WHERE codAluno = "+plano.getCodAluno()).get(0);
                        Servicos servico = servicosDao.pesquisarServicos("SELECT * FROM tblServicos WHERE codServico = "+plano.getCodServico()).get(0);
                        Date dataUsual = plano.getDataVencimento();
                        Date dataAux = converterData.parseDate(converterData.parseDate(dataUsual));
                        dataVencimento = converterData.conversaoLocalforDate(dataAux);

                        periodo = Period.between(dataVencimento, dataAtual);
                        if(periodo.getDays()>=1){
                        planoAtual = new Planos(plano.getChavePlano(), plano.getCodAluno(), 0, 0, 0, plano.getDataVencimento(),
                                plano.getDataPagamento(), plano.getDataCancelamento(), 
                                plano.getDataRenovacao(),"Vencido");
                        planosDao.atualizarSituacao(planoAtual);
                        verificadorExistencia++;
                            }
                    }
                }        
            }
            }
        if(verificadorExistencia>0){
            view.exibeMensagem("A mensalidade de alguns alunos vence hoje. Verifique a tela de Alunos!");
        }
    }
    
    private void setarDebitos(Aluno alunoBanco){
        Servicos servico = servicosDao.pesquisarServicos("SELECT * FROM tblServicos WHERE codServico = "+alunoBanco.getServico()).get(0);
        
        BigDecimal debitos = new BigDecimal(alunoBanco.getDebito().toString());
        BigDecimal valor = new BigDecimal(alunoBanco.getValorMensal().toString());
        if(servico.getPeriodDays()<30){
            if(alunoBanco.getRenovacaoAutomatica()==1){
                BigDecimal periodDays = new BigDecimal(servico.getPeriodDays());
                BigDecimal period = (new BigDecimal(30)).divide(periodDays,4, RoundingMode.UP);
                valor = valor.divide(period, 4, RoundingMode.UP);
                valor = valor.setScale(2, RoundingMode.UP);
            }
        }
        
        alunosDao.atualizarDebitos(alunoBanco.getCodBanco(), debitos.add(valor));
        
    }
    
    public boolean permissaoDeAcessoATela(String tela){
        ArrayList<Funcionario> funcionarios = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE status = 'Ativo'");
        
        if(funcionarios!=null){
            Funcionario funcionarioLogado = funcionarios.get(0);
            String[] telas = funcionarioLogado.getTelasPermitidas().split(",");
            for(int linhas=0; linhas<telas.length; linhas++){
                telas[linhas]=telas[linhas].replace(",", "");
                
                if(telas[linhas].equals(tela)){
                    return true;
                }
            }
            return false;
        }
        return true;
    }
    
    private LogAçoesFuncionario setarLog(ArrayList <Funcionario> funcionarios){
        Funcionario funcionario = funcionarios.get(0);
        Date dataEvento = new Date();
        
        LogAçoesFuncionario logAcao = new LogAçoesFuncionario(funcionario.getCodBanco(), dataEvento, "Saída do Sistema", null);
        return logAcao;
    } 
    
}


    

