/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.auxiliar.ConversaoDiasDaSemana;
import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Dao.AlunosDao;
import Dao.FuncionarioDao;
import Dao.HorariosDao;
import Dao.LogAçoesFuncionarioDao;
import Dao.PlanosDao;
import Dao.TurmasDao;
import Model.Aluno;
import Model.Funcionario;
import Model.auxiliar.LogAçoesFuncionario;
import Model.auxiliar.Planos;
import Model.auxiliar.Turmas;
import View.LoginFuncionario;
import View.LoginGerente;
import View.TurmasView;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mayro
 */
public class TurmasController {
    protected final TurmasView view;
    protected final TurmasDao turmasDao = new TurmasDao();
    protected final HorariosDao horariosDao = new HorariosDao();
    protected final FuncionarioDao funcionarioDao = new FuncionarioDao();
    protected final LogAçoesFuncionarioDao logDao = new LogAçoesFuncionarioDao();
    protected final AlunosDao alunoDao = new AlunosDao();
    protected final PlanosDao planosDao = new PlanosDao();
    protected final ConversaoDiasDaSemana converterDias = new ConversaoDiasDaSemana();
    protected final DefaultTableModel tabelaDeTurmas;
    protected final ConversaodeDataParaPadraoDesignado converterHora = new ConversaodeDataParaPadraoDesignado();

    public TurmasController(TurmasView view) {
        this.view = view;
        this.tabelaDeTurmas = (DefaultTableModel) view.getTabelaTurmas().getModel();  
    }
    
    //Limpar tabela
    public void limparTabela(){
        int quantLinhas = this.view.getTabelaTurmas().getRowCount();    
        for(int quant=0; quant<quantLinhas; quant++){
            this.tabelaDeTurmas.removeRow(0);
        }
    }
    
    //Lista todas as turmas 
    public void listarTurmas(){
        String situacao = view.getComboSituacao().getSelectedItem().toString();
        situacao = situacao.substring(0,situacao.length()-1);
        ArrayList <Turmas> turmas = turmasDao.pesquisarTurmas("SELECT * FROM tblTurmas WHERE situacao = '"+situacao+"'");
        if(turmas==null){
            view.exibeMensagem("Sem Dados");
            limparTabela();
        } else{
            limparTabela();
            for(Turmas turma : turmas){
                Object[] dadosDaTabela = {turma.getCodBanco(), turma.getNomeTurma(), turma.getSituacao()};
                tabelaDeTurmas.addRow(dadosDaTabela);
                  
            }
        }        
    }
    
    public void editarTurma(){
        if(this.view.getTabelaTurmas().getSelectedRow()!= -1){
            int linhaSelecionada = this.view.getTabelaTurmas().getSelectedRow();
            int codTurma = Integer.parseInt(tabelaDeTurmas.getValueAt(linhaSelecionada, 0).toString());
            String nome = this.tabelaDeTurmas.getValueAt(linhaSelecionada, 1).toString();
            String situacao = tabelaDeTurmas.getValueAt(linhaSelecionada, 2).toString();
        
            //Inserindo Dados
            if(nome.equals("")){
             view.mensagemCritica("Campos Preenchidos Incorretamente", "Atenção");
            } else{
                TurmasDao turmaDao = new TurmasDao();
                turmaDao.atualizarSituacao(codTurma, nome, situacao);

                this.setarLog("Edição de Dados de Turma", "Editou os dados da turma "+nome);
                
                view.exibeMensagem("Sucesso!");
                //Limpando Campos
                listarTurmas();
                view.getTabelaTurmas().addRowSelectionInterval(linhaSelecionada, linhaSelecionada);
        }
            //Turmas 
        }
        
        else{view.mensagemCritica("Erro, Nenhuma Turma Selecionada!", "Erro");}
    }
    
        public void editarVariasTurmas(){
            int linhasTabela = tabelaDeTurmas.getRowCount();
            if(linhasTabela>0){
                boolean erros = false;
                String errosTurmas = "(";
                for(int linha=0; linha<linhasTabela; linha++){
                    int codTurma = Integer.parseInt(tabelaDeTurmas.getValueAt(linha, 0).toString());
                    String nome = this.tabelaDeTurmas.getValueAt(linha, 1).toString();
                    String situacao = tabelaDeTurmas.getValueAt(linha, 2).toString();

                    //Inserindo Dados
                    if(nome.equals("")){
                        erros = true;
                        errosTurmas+=nome+", ";
                    } else{
                        TurmasDao turmaDao = new TurmasDao();
                        turmaDao.atualizarSituacao(codTurma, nome, situacao);
                    }
                }
                
                this.setarLog("Edição de Dados de Turma", "Editou os dados de várias Turmas");
                
                
                if(erros){
                    view.mensagemCritica("As turmas "+errosTurmas.substring(0, errosTurmas.length()-2)+") não puderam ser editadas.", "Atenção");
                }
                view.exibeMensagem("Sucesso!");
                //Limpando Campos
                listarTurmas();
                
            }

            else{view.mensagemCritica("Sem dados na tabela", "Erro");}
    }
    
    public void removerTurma(){
        if(this.view.getTabelaTurmas().getSelectedRow()!= -1){
            int linhaSelecionada = this.view.getTabelaTurmas().getSelectedRow();
            int codTurma = Integer.parseInt(tabelaDeTurmas.getValueAt(linhaSelecionada, 0).toString());
            String nomeTurma = tabelaDeTurmas.getValueAt(linhaSelecionada, 1).toString();
            
            if(this.retornarAlunosUsando(codTurma)){
                turmasDao.removerTurma(codTurma);
                    this.setarLog("Remoção de Turma", "Remoção da turma "+nomeTurma);
                
                this.view.exibeMensagem("Sucesso");
                listarTurmas(); 
            }
            else{
                view.exibeMensagem("Não é possível remover, pois ainda existem alunos presentes nessa turma!");
            }
            
        }
        else{this.view.exibeMensagem("Erro, Nenhuma Turma Selecionada!");}
    }
    
    //Buscar turmas no campo de busca
    public void buscarTurmas(){
        String nomeTurma = view.getCampoDePesquisa().getText();
        
        if(nomeTurma.equals("")){listarTurmas();}
        else{
            ArrayList <Turmas> turmas = turmasDao.pesquisarPorNome(nomeTurma);
            if(turmas==null){view.exibeMensagem("Turma Não Encontrada!"); limparTabela();}
            else{
                limparTabela();
                for(Turmas turma : turmas){
                Object[] dadosDaTabela = {turma.getCodBanco(), turma.getNomeTurma(),  turma.getSituacao()};
                tabelaDeTurmas.addRow(dadosDaTabela);
                  
            }
            }
        }
    }
    
    protected Funcionario setarLog(String acao, String descricao){
        ArrayList <Funcionario> funcionarios = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE status = 'Ativo'");
            if(funcionarios!=null){
            Funcionario funcionario = funcionarios.get(0);
            Date dataEvento = new Date();
            LogAçoesFuncionario logAcao = new LogAçoesFuncionario(funcionario.getCodBanco(), dataEvento, acao, descricao);
            return funcionario;
        }
            return null;
    }
    
    protected boolean retornarAlunosUsando(int codTurma){
        ArrayList <Aluno> alunos = alunoDao.pesquisarAlunos("SELECT * FROM tblAlunos WHERE codTurma = "+codTurma);
        return alunos ==null;
    }
    
    public void encerrarReabrirTurma(){
        int linhaSelecionada = view.getTabelaTurmas().getSelectedRow();
        
        if(linhaSelecionada>-1){
            int codTurma = Integer.parseInt(tabelaDeTurmas.getValueAt(linhaSelecionada, 0).toString());
            String nomeTurma = tabelaDeTurmas.getValueAt(linhaSelecionada, 1).toString();
            ArrayList <Planos> planos= planosDao.pesquisarPlanos("SELECT * FROM tblPlanos WHERE codTurma = "+codTurma+" AND situacao != 'Encerrado'");
            
            String situacao = tabelaDeTurmas.getValueAt(linhaSelecionada, 2).toString();
            if(situacao.equals("Aberta")){
                situacao = "Encerrada";
            }else{
                situacao = "Aberta";
            }
            
            if(situacao.equals("Encerrada")&&planos!=null){
                view.exibeMensagem("Não é possível encerrar a Turma, pois ainda existem Planos Não Encerrados!");
            }else{
                turmasDao.atualizarSituacao(codTurma, nomeTurma, situacao);
                view.exibeMensagem("Sucesso!");
                listarTurmas();
            }
        }
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
    
    public void ajuda(){
        String atalhos = "\u07CBESC = Sair da Tela\n"
                + "\u07CBF5 = Atualizar Tabela\n"
                + "\u07CBDEL = Remover Turma\n"
                + "\u07CBCTRL + E = Editar Turma\n"
                + "\u07CBCTRL + F = Encerrar ou Reabrir Turma\n"
                + "\u07CBCTRL + N = Cadastrar Nova Turma\n";
        
        view.getPainelAjuda().setModal(true);
        view.getPainelAjuda().getCampoAtalhos().setText("");
        view.getPainelAjuda().getCampoAtalhos().append(atalhos);
        view.getPainelAjuda().setVisible(true);
    }
}
