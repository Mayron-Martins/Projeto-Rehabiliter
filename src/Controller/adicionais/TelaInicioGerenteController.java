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
import Dao.TurmasDao;
import Model.Aluno;
import Model.Funcionario;
import Model.auxiliar.LogAçoesFuncionario;
import Model.auxiliar.Planos;
import Model.auxiliar.Turmas;
import View.TelaInicialGerenteView;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mayro
 */
public class TelaInicioGerenteController {
    private final TelaInicialGerenteView view;
    private final DefaultTableModel tabelaDeAlunos;
    private final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();
    private final AlunosDao alunosDao = new AlunosDao();
    private final TurmasDao turmasDao = new TurmasDao();
    private final PlanosDao planosDao = new PlanosDao();
    private final FuncionarioDao funcionarioDao = new FuncionarioDao();
    private final LogAçoesFuncionarioDao logDao = new LogAçoesFuncionarioDao();

    public TelaInicioGerenteController(TelaInicialGerenteView view) {
        this.view = view;
        this.tabelaDeAlunos = (DefaultTableModel) view.getTabelaAniversariantes().getModel();
    }

    
    
    public void limparTabela(){
        int quantLinhas = this.view.getTabelaAniversariantes().getRowCount();
        for(int quant=0; quant<quantLinhas; quant++){
            this.tabelaDeAlunos.removeRow(0);
        }
    }

    public void inicializarTabela() throws SQLException, ParseException{
        ArrayList <Aluno> alunos = alunosDao.selecionarTodosAlunos();
        ArrayList <Aluno> alunosAniversariantes = new ArrayList<>();
        Date aniversario;
        if(alunos!=null){
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
    
        public void sairTela() throws SQLException, ParseException{
        funcionarioDao.atualizarStatusAll();
        
        ArrayList <Funcionario> funcionarios = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE status = 'Ativo'");
        if(funcionarios!=null){
            this.setarLog(funcionarios);
        }
        view.dispose();
    }
        
        
    
    private void buscas(ArrayList <Aluno> listar) throws SQLException, ParseException{
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
    
        public void setarPlanos() throws SQLException, ParseException{
        if(alunosDao.selecionarTodosAlunos()!=null){
            this.setarVencimentoPlanos();
            this.setarPendenciaPlanos();
        }
    }
    
    private void setarPendenciaPlanos() throws SQLException, ParseException{
        LocalDate dataAtual = LocalDate.now();
        
        if(dataAtual.getDayOfMonth()==1){
            ArrayList <Planos> planos = new ArrayList<>();
            
            planos = planosDao.selecionarTodosPlanos();
            
            for(int linhas=0; linhas<planos.size(); linhas++){
                Planos planoAtual;
                if(planos.get(linhas).getSituacao().equals("Pago")){
                    planoAtual = new Planos(planos.get(linhas).getCodAluno(), 0, 0, 0, planos.get(linhas).getDataPagamento(), planos.get(linhas).getDataCancelamento(), "Pendente");
                    planosDao.atualizarSituacao(planoAtual);
                    this.setarDebitos(planos.get(linhas).getCodAluno());
                 }
         }
     }
  }
    
    private void setarVencimentoPlanos() throws SQLException, ParseException{
        LocalDate dataAtual = LocalDate.now();
        LocalDate dataVencimento;
        Period periodo;
        ArrayList <Planos> planos = planosDao.selecionarTodosPlanos();
        ArrayList <Aluno> alunos = alunosDao.selecionarTodosAlunos();
        Planos planoAtual;
            
        
            for(int linhas=0; linhas<planos.size(); linhas++){
                if(planos.get(linhas).getDataPagamento()==null){
                    Date dataCadastro = alunos.get(linhas).getDataCadastro();
                    Date dataAux = converterData.parseDate(converterData.parseDate(dataCadastro));
                    dataVencimento = converterData.conversaoLocalforDate(dataAux).plusMonths(1);
                    periodo = Period.between(dataVencimento, dataAtual);
                    if(periodo.getDays()>=1){
                    planoAtual = new Planos(planos.get(linhas).getCodAluno(), 0, 0, 0, planos.get(linhas).getDataPagamento(), planos.get(linhas).getDataCancelamento(), "Vencido");
                    planosDao.atualizarSituacao(planoAtual);
                    }  
                }
                
                else{
                    Date dataAux = converterData.parseDate(converterData.parseDate(planos.get(linhas).getDataPagamento()));
                    dataVencimento = converterData.conversaoLocalforDate(dataAux).plusMonths(1);
                    periodo = Period.between(dataVencimento, dataAtual);
                   if(periodo.getDays()>=1){
                    planoAtual = new Planos(planos.get(linhas).getCodAluno(), 0, 0, 0, planos.get(linhas).getDataPagamento(), planos.get(linhas).getDataCancelamento(), "Vencido");
                    planosDao.atualizarSituacao(planoAtual);
                   } 
                 }
         }
    }
    
    private void setarDebitos(int codAluno) throws SQLException, ParseException{
        Aluno aluno = alunosDao.pesquisarAlunos("SELECT * FROM tblAlunos WHERE codAluno = "+codAluno).get(0);
        
        BigDecimal debitos = new BigDecimal(aluno.getDebito().toString());
        BigDecimal contrato = new BigDecimal(aluno.getValorContrato().toString());
        alunosDao.atualizarDebitos(codAluno, debitos.add(contrato));
        
    }
    
    public boolean permissaoDeAcessoATela() throws SQLException, ParseException{
        ArrayList<Funcionario> funcionarios = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE status = 'Ativo'");
        
        boolean permissao = false;
        if(funcionarios!=null){
            Funcionario funcionarioLogado = funcionarios.get(0);
            String[] telas = funcionarioLogado.getTelasPermitidas().split(",");
            for(int linhas=0; linhas<telas.length; linhas++){
                telas[linhas]=telas[linhas].replace(",", "");

                switch(telas[linhas]){
                    case "1":
                        permissao = true;
                    break;
                    case "2":
                        permissao = true;
                    break;
                    case "3":
                        permissao = true;
                    break;
                    case "4":
                        permissao = true;
                    break;
                    case "5":
                        permissao = true;
                    break;
                    case "6":
                        permissao = true;
                    break;
                    case "7":
                        permissao = true;
                    break;
                    case "8":
                        permissao = true;
                    break;
                    case "9":
                        permissao = true;
                    break;
                }
            }
            return permissao;
        }
        return permissao;
    }
    
    private LogAçoesFuncionario setarLog(ArrayList <Funcionario> funcionarios){
        Funcionario funcionario = funcionarios.get(0);
        Date dataEvento = new Date();
        
        LogAçoesFuncionario logAcao = new LogAçoesFuncionario(funcionario.getCodBanco(), dataEvento, "Saída do Sistema", null);
        return logAcao;
    }
}
