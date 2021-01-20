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
import Model.auxiliar.Horarios;
import Model.auxiliar.LogAçoesFuncionario;
import Model.auxiliar.Planos;
import Model.auxiliar.Turmas;
import View.TurmasView;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mayro
 */
public class TurmasController {
    private final TurmasView view;
    private final TurmasDao turmasDao = new TurmasDao();
    private final HorariosDao horariosDao = new HorariosDao();
    private final FuncionarioDao funcionarioDao = new FuncionarioDao();
    private final LogAçoesFuncionarioDao logDao = new LogAçoesFuncionarioDao();
    private final AlunosDao alunoDao = new AlunosDao();
    private final PlanosDao planosDao = new PlanosDao();
    private final ConversaoDiasDaSemana converterDias = new ConversaoDiasDaSemana();
    private final DefaultTableModel tabelaDeTurmas;
    private final ConversaodeDataParaPadraoDesignado converterHora = new ConversaodeDataParaPadraoDesignado();

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
                int quantMaximaAlunos = turma.getQuantidadeMaximaAlunos();
                String quantidade = quantMaximaAlunos+"";
                if(quantMaximaAlunos==0){
                    quantidade="";
                }
                
                Object[] dadosDaTabela = {turma.getCodBanco(), 
                turma.getNomeTurma(), quantidade ,turma.getQuantidadeAlunos(),
                this.converterDias.converterDiasDaSemana(turma.getDiasDaSemana()),
                converterHora.parseHour(turma.getHorario()), turma.getSituacao()};
                tabelaDeTurmas.addRow(dadosDaTabela);
                  
            }
        }        
    }
    
    public void editarTurmas(){
        if(this.view.getTabelaTurmas().getSelectedRow()!= -1){
            int linhaSelecionada = this.view.getTabelaTurmas().getSelectedRow();
            int codTurma = Integer.parseInt(tabelaDeTurmas.getValueAt(linhaSelecionada, 0).toString());
            String nome = this.tabelaDeTurmas.getValueAt(linhaSelecionada, 1).toString();
                        
            int quantAlunos =0;
            
            int quantidadeMax=0;
            if(!this.tabelaDeTurmas.getValueAt(linhaSelecionada, 2).toString().equals("")){
                quantidadeMax = Integer.parseInt(tabelaDeTurmas.getValueAt(linhaSelecionada, 2).toString());
            }
            
            String diasDaSemana = this.view.getDiasDaSemana();
            ArrayList <String> diasDaSemanaUnitarios = view.getDiasDaSemanaUnitarios();
            
            String horario = this.view.getCampoHorario();
            String situacao = tabelaDeTurmas.getValueAt(linhaSelecionada, 6).toString();
            
            Turmas turma = null;
            ArrayList <Horarios> horarios = new ArrayList <>();
            if(!view.getCampoHorario().equals("")){
                
                turma = new Turmas(codTurma, nome, quantAlunos, quantidadeMax, diasDaSemana, horario, situacao);    


                int diferenca, contador = diasDaSemanaUnitarios.size();
                while(contador>0){
                diferenca = diasDaSemanaUnitarios.size()-contador;
                Horarios auxiliar = new Horarios(codTurma, diasDaSemanaUnitarios.get(diferenca), horario, 0, codTurma);
                horarios.add(auxiliar);
                contador--;
        }
        }
        
        //Inserindo Dados
        if(nome.equals("")|| horario.equals("") ||view.testeValidacaoHorario()==false){
         view.exibeMensagem("Campos Preenchidos Incorretamente");
        } else{
            TurmasDao turmaDao = new TurmasDao();
            turmaDao.atualizarDados(turma, horarios);
            
            ArrayList <Funcionario> funcionarios = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE status = 'Ativo'");
            if(funcionarios!=null){
                String acao = "Edição de Dados de Turma";
                String descricao = "Editou os dados da turma "+nome;
                this.setarLog(funcionarios, acao, descricao);
            }
            view.exibeMensagem("Sucesso!");
            //Limpando Campos
            listarTurmas();
        }
            //Turmas 
        }
        
        else{this.view.exibeMensagem("Erro, Nenhuma Turma Selecionada!");}
        
    
    }
    
    public void selecionarTabela(){
      if(this.view.getTabelaTurmas().getSelectedRow()!=-1){
          this.limparDias();
          //Número da linha selecionada
          int linhaSelecionada = this.view.getTabelaTurmas().getSelectedRow();
          //Habilita itens que ficam acima da tabela
          this.view.habilitarVisibilidadeComponente();
          //Pega o valor da coluna horario
          String horario = this.view.getTabelaTurmas().getValueAt(linhaSelecionada, 5).toString();
          String hora = String.valueOf(horario.charAt(0))+horario.charAt(1);
          String minutos = String.valueOf(horario.charAt(3))+horario.charAt(4);
          //Coloca os valores nos campos edithoras e editminutos
          view.getCampoEdicaoHoras().setText(hora);
          view.getCampoEdicaoMinutos().setText(minutos);
          //Pega o valor dos dias da semana
          ArrayList <Horarios> horarios;
          int codTurma = Integer.parseInt(this.view.getTabelaTurmas().getValueAt(linhaSelecionada, 0).toString());
          horarios = this.horariosDao.pesquisarHorarios("SELECT * FROM tblHorarios WHERE codHorario = "+codTurma);
          for(int cont=0; cont<horarios.size();cont++){
              String dia = horarios.get(cont).getDiaDaSemana();
              if(dia.equals("Seg")){this.view.getCaixaSegunda().setSelected(true);}
              if(dia.equals("Ter")){this.view.getCaixaTerca().setSelected(true);}
              if(dia.equals("Qua")){this.view.getCaixaQuarta().setSelected(true);}
              if(dia.equals("Qui")){this.view.getCaixaQuinta().setSelected(true);}
              if(dia.equals("Sex")){this.view.getCaixaSexta().setSelected(true);}
              if(dia.equals("Sab")){this.view.getCaixaSabado().setSelected(true);}
              if(dia.equals("Dom")){this.view.getCaixaDomingo().setSelected(true);}
          }
          
          
      }
    }
    
    public void removerTurma(){
        if(this.view.getTabelaTurmas().getSelectedRow()!= -1){
            int linhaSelecionada = this.view.getTabelaTurmas().getSelectedRow();
            int codTurma = Integer.parseInt(tabelaDeTurmas.getValueAt(linhaSelecionada, 0).toString());
            String nomeTurma = tabelaDeTurmas.getValueAt(linhaSelecionada, 1).toString();
            
            if(this.retornarAlunosUsando(codTurma)){
                turmasDao.removerTurma(codTurma);

                ArrayList <Funcionario> funcionarios = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE status = 'Ativo'");
                if(funcionarios!=null){
                    String acao = "Remoção de Turma";
                    String descricao = "Remoção da turma "+nomeTurma;
                    this.setarLog(funcionarios, acao, descricao);
                }
                this.view.exibeMensagem("Sucesso");
                this.view.desabilitarVisibilidadeComponente();
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
                int quantMaximaAlunos = turma.getQuantidadeMaximaAlunos();
                String quantidade = quantMaximaAlunos+"";
                if(quantMaximaAlunos==0){
                    quantidade="";
                }
                Object[] dadosDaTabela = {turma.getCodBanco(), 
                turma.getNomeTurma(), quantidade ,turma.getQuantidadeAlunos(),
                this.converterDias.converterDiasDaSemana(turma.getDiasDaSemana()),
                converterHora.parseHour(turma.getHorario()), turma.getSituacao()};
                tabelaDeTurmas.addRow(dadosDaTabela);
                  
            }
            }
        }
    }
    
    private LogAçoesFuncionario setarLog(ArrayList <Funcionario> funcionarios, String acao, String descricao){
        Funcionario funcionario = funcionarios.get(0);
        Date dataEvento = new Date();
        LogAçoesFuncionario logAcao = new LogAçoesFuncionario(funcionario.getCodBanco(), dataEvento, acao, descricao);
        return logAcao;
    }
    
    private boolean retornarAlunosUsando(int codTurma){
        ArrayList <Aluno> alunos = alunoDao.pesquisarAlunos("SELECT * FROM tblAlunos WHERE codTurma = "+codTurma);
        return alunos ==null;
    }
    
    public void encerrarReabrirServico(){
        int linhaSelecionada = view.getTabelaTurmas().getSelectedRow();
        
        if(linhaSelecionada>-1){
            int codTurma = Integer.parseInt(tabelaDeTurmas.getValueAt(linhaSelecionada, 0).toString());
            
            ArrayList <Planos> planos= planosDao.pesquisarPlanos("SELECT * FROM tblPlanos WHERE codTurma = "+codTurma+" AND situacao != 'Encerrado'");
            
            String situacao = tabelaDeTurmas.getValueAt(linhaSelecionada, 6).toString();
            if(situacao.equals("Aberta")){
                situacao = "Encerrada";
            }else{
                situacao = "Aberta";
            }
            
            if(situacao.equals("Encerrada")&&planos!=null){
                view.exibeMensagem("Não é possível encerrar a Turma, pois ainda existem Planos Não Encerrados!");
            }else{
                turmasDao.atualizarSituacao(codTurma, situacao);
                view.exibeMensagem("Sucesso!");
                listarTurmas();
            }
        }
    }

    private void limparDias() {
        view.getCaixaSegunda().setSelected(false);
        view.getCaixaTerca().setSelected(false);
        view.getCaixaQuarta().setSelected(false);
        view.getCaixaQuinta().setSelected(false);
        view.getCaixaSexta().setSelected(false);
        view.getCaixaSabado().setSelected(false);
        view.getCaixaDomingo().setSelected(false);
    }
}
