/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.auxiliar.ConversaoDiasDaSemana;
import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Dao.HorariosDao;
import Dao.TurmasDao;
import Model.auxiliar.Horarios;
import Model.auxiliar.Turmas;
import View.TurmasView;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mayro
 */
public class TurmasController {
    private final TurmasView view;
    private final TurmasDao turmasDao = new TurmasDao();
    private final HorariosDao horariosDao = new HorariosDao();
    private final ConversaoDiasDaSemana converterDias = new ConversaoDiasDaSemana();
    private final DefaultTableModel tabelaDeTurmas;
    private final ConversaodeDataParaPadraoDesignado converterHora = new ConversaodeDataParaPadraoDesignado();

    public TurmasController(TurmasView view) {
        this.view = view;
        this.tabelaDeTurmas = (DefaultTableModel) view.getTabelaAlunos().getModel();  
    }
    
    //Limpar tabela
    public void limparTabela(){
        int quantLinhas = this.view.getTabelaAlunos().getRowCount();    
        for(int quant=0; quant<quantLinhas; quant++){
            this.tabelaDeTurmas.removeRow(quant);
        }
    }
    
    //Lista todas as turmas 
    public void listarTurmas() throws SQLException{
        ArrayList <Turmas> turmas = new ArrayList<>();
        ArrayList <Horarios> horarios = new ArrayList<>();
        turmas = this.turmasDao.selecionarTodasTurmas();
        horarios = this.horariosDao.selecionarTodosHorarios();
        if(turmas==null){
            view.exibeMensagem("Sem Turmas Cadastradas");
        } else{
            for(int linhas=0; linhas<turmas.size(); linhas++){
                if(turmas.get(linhas).getQuantidadeMaximaAlunos()==0){
                    Object[] dadosDaTabela = {turmas.get(linhas).getCodBanco(), 
                    turmas.get(linhas).getNomeTurma(),turmas.get(linhas).getSubgrupo(),
                    "",turmas.get(linhas).getQuantidadeAlunos(),
                    this.converterDias.converterDiasDaSemana(turmas.get(linhas).getDiasDaSemana()),
                    converterHora.parseHour(turmas.get(linhas).getHorario())};
                    this.tabelaDeTurmas.addRow(dadosDaTabela);
                }
                else{
                    Object[] dadosDaTabela = {turmas.get(linhas).getCodBanco(), 
                    turmas.get(linhas).getNomeTurma(),turmas.get(linhas).getSubgrupo(),
                    turmas.get(linhas).getQuantidadeMaximaAlunos(),turmas.get(linhas).getQuantidadeAlunos(),
                    this.converterDias.converterDiasDaSemana(turmas.get(linhas).getDiasDaSemana()),
                    converterHora.parseHour(turmas.get(linhas).getHorario())};
                    this.tabelaDeTurmas.addRow(dadosDaTabela);
                }    
            }
        }        
    }
    
    public void editarTurmas() throws SQLException{
        if(this.view.getTabelaAlunos().getSelectedRow()!= -1){
            int linhaSelecionada = this.view.getTabelaAlunos().getSelectedRow();
            int codTurma = Integer.parseInt(tabelaDeTurmas.getValueAt(linhaSelecionada, 0).toString());
            String nome = this.tabelaDeTurmas.getValueAt(linhaSelecionada, 1).toString();
            
            String subgrupo;
            if(this.tabelaDeTurmas.getValueAt(linhaSelecionada, 2) == null){subgrupo=null;}
            else{subgrupo = this.view.getTabelaAlunos().getValueAt(linhaSelecionada, 2).toString();}
            
            
            int quantAlunos =0;
            
            int quantidadeMax;
            if(this.tabelaDeTurmas.getValueAt(linhaSelecionada, 3)==null){quantidadeMax = 0;}
            else{quantidadeMax = Integer.parseInt(this.tabelaDeTurmas.getValueAt(linhaSelecionada, 3).toString());}
            
            String diasDaSemana = this.view.getDiasDaSemana();
            ArrayList <String> diasDaSemanaUnitarios = new ArrayList<>();
            diasDaSemanaUnitarios = this.view.getDiasDaSemanaUnitarios();
            
            String horario = this.view.getCampoHorario();
            
            Turmas turma = null;
            ArrayList <Horarios> horarios = new ArrayList <>();
            if(!view.getCampoHorario().equals("")){
            turma = new Turmas(codTurma, nome, subgrupo, quantAlunos, quantidadeMax, diasDaSemana, horario);    

        
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
            view.exibeMensagem("Sucesso!");
            //Limpando Campos
            limparTabela();
            listarTurmas();
        }
            //Turmas 
        }
        
        else{this.view.exibeMensagem("Erro, Nenhuma Turma Selecionada!");}
        
    
    }
    
    public void selecionarTabela() throws SQLException{
      if(this.view.getTabelaAlunos().getSelectedRow()!=-1){
          //Número da linha selecionada
          int linhaSelecionada = this.view.getTabelaAlunos().getSelectedRow();
          //Habilita itens que ficam acima da tabela
          this.view.habilitarVisibilidadeComponente();
          //Pega o valor da coluna horario
          String horario = this.view.getTabelaAlunos().getValueAt(linhaSelecionada, 6).toString();
          String hora = String.valueOf(horario.charAt(0))+horario.charAt(1);
          String minutos = String.valueOf(horario.charAt(3))+horario.charAt(4);
          //Coloca os valores nos campos edithoras e editminutos
          view.getCampoEdicaoHoras().setText(horario);
          view.getCampoEdicaoMinutos().setText(minutos);
          //Pega o valor dos dias da semana
          ArrayList <Horarios> horarios;
          int codTurma = Integer.parseInt(this.view.getTabelaAlunos().getValueAt(linhaSelecionada, 0).toString());
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
    
    public void removerTurma() throws SQLException{
        if(this.view.getTabelaAlunos().getSelectedRow()!= -1){
            int linhaSelecionada = this.view.getTabelaAlunos().getSelectedRow();
            int codTurma = Integer.parseInt(tabelaDeTurmas.getValueAt(linhaSelecionada, 0).toString());
            
            turmasDao.removerTurma(codTurma);
            this.view.exibeMensagem("Sucesso");
            limparTabela();
            this.view.desabilitarVisibilidadeComponente();
            listarTurmas();
        }
        else{this.view.exibeMensagem("Erro, Nenhuma Turma Selecionada!");}
    }
    
    //Buscar turmas no campo de busca
    public void buscarTurmas() throws Exception{
        String nomeTurma = view.getCampoDePesquisa().getText();
        ArrayList <Turmas> turmas = turmasDao.pesquisarPorNome(nomeTurma);
        
        if(nomeTurma.equals("")){limparTabela();listarTurmas();}
        else{
            if(turmas==null){view.exibeMensagem("Turma Não Encontrada!"); limparTabela();}
            else{
                limparTabela();
                for(int linhas = 0; linhas<turmas.size(); linhas++){
                    if(turmas.get(linhas).getQuantidadeMaximaAlunos()==0){
                        Object[] dadosDaTabela = {turmas.get(linhas).getCodBanco(), 
                        turmas.get(linhas).getNomeTurma(),turmas.get(linhas).getSubgrupo(),
                        "",turmas.get(linhas).getQuantidadeAlunos(),
                        this.converterDias.converterDiasDaSemana(turmas.get(linhas).getDiasDaSemana()),
                        converterHora.parseHour(turmas.get(linhas).getHorario())};
                        this.tabelaDeTurmas.addRow(dadosDaTabela);
                    }
                    else{
                        Object[] dadosDaTabela = {turmas.get(linhas).getCodBanco(), 
                        turmas.get(linhas).getNomeTurma(),turmas.get(linhas).getSubgrupo(),
                        turmas.get(linhas).getQuantidadeMaximaAlunos(),turmas.get(linhas).getQuantidadeAlunos(),
                        this.converterDias.converterDiasDaSemana(turmas.get(linhas).getDiasDaSemana()),
                        converterHora.parseHour(turmas.get(linhas).getHorario())};
                        this.tabelaDeTurmas.addRow(dadosDaTabela);
                    }
                }
            }
        }
    }
}
