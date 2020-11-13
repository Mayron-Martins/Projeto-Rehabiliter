/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.adicionais;

import Dao.AlunosDao;
import Dao.HorariosDao;
import Dao.TurmasDao;
import Model.Aluno;
import Model.auxiliar.Horarios;
import Model.auxiliar.Turmas;
import View.TurmasView;
import View.turmasFrequencia;
import java.awt.Color;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mayro
 */
public class FrequenciaTurmasController {
    private final turmasFrequencia view;
    private final DefaultTableModel tabelaDeAlunos;
    private final TurmasDao turmasDao = new TurmasDao();
    private final TurmasView telaDeTurmas = new TurmasView();
    private final AlunosDao alunosDao = new AlunosDao();
    private final HorariosDao horariosDao = new HorariosDao();
    

    public FrequenciaTurmasController(turmasFrequencia view) {
        this.view = view;
        this.tabelaDeAlunos = (DefaultTableModel) view.getTabelaAlunos().getModel();
    }
    
    public void limparTabela(){
        int quantLinhas = this.view.getTabelaAlunos().getRowCount();
        for(int quant=0; quant<quantLinhas; quant++){
            this.tabelaDeAlunos.removeRow(0);
        }
    }
    
    //Adicionar linhas à tabela se for a data correta
    public void adicionarLinhasATabela() throws SQLException, ParseException{
    if(view.getComboTurmas().getSelectedIndex()>0){
    
        if(this.verificarNovoDado()){
            this.setarTabela();
        }
        else{
            String turma = view.getComboTurmas().getSelectedItem().toString();
            view.getCampoAviso().setForeground(Color.RED);
            view.getCampoAviso().append("A turma "+turma.split("\\.")[1]+" não possui horários para o dia de hoje.");
            view.getScrollPaneAviso().setVisible(true);
        }
    }
    else{
        view.exibeMensagem("Selecione Uma Turma Nas Opções Para Continuar!");
    }
    }
    
    //Seta as turmas no combobox de turmas
    public void setarTurmas() throws SQLException{
        ArrayList <Turmas> turmas = turmasDao.selecionarTodasTurmas();
        
        if(turmas == null){
            view.exibeMensagem("Sem Turmas Cadastradas");
            telaDeTurmas.setVisible(true);
            view.dispose();
        }
        else{
            int codBanco;
            String nomeTurma;
            String subgrupo;
            
            for(int linhas=0; linhas<turmas.size();linhas++){
                subgrupo = "";
                codBanco = turmas.get(linhas).getCodBanco();
                nomeTurma = turmas.get(linhas).getNomeTurma();
                if(turmas.get(linhas).getSubgrupo()!=null){subgrupo = " - "+turmas.get(linhas).getSubgrupo();}
                view.getComboTurmas().addItem(codBanco+"."+nomeTurma+subgrupo);
            }
        }
    }
    
    //seta a tabela para data de hoje
    private void setarTabela() throws SQLException, ParseException{
            limparTabela();
            int codTurma = this.retornarCodTurma();
           ArrayList <Aluno> alunos = alunosDao.pesquisarAlunos("SELECT * FROM tblAlunos WHERE codTurma = "+codTurma);
           if(alunos == null){view.exibeMensagem("Sem Alunos Cadastrados Nessa Turma!");}
           else{
               for(int linhas=0; linhas<alunos.size(); linhas++){
                  Object[] dadosDaTabela = {alunos.get(linhas).getCodBanco(), alunos.get(linhas).getNome(), "", view.getComboPresenca().getSelectedItem()};
                  this.tabelaDeAlunos.addRow(dadosDaTabela); 
               }
           }
    }
    
    
    //Setar o combo presença na tabela
    public void setarComboPresenca(){
        if(view.getTabelaAlunos().getSelectedRow()>-1){
          int linhaSelecionada = view.getTabelaAlunos().getSelectedRow();
          String situacao = view.getComboPresenca().getSelectedItem().toString();
          
          if(situacao.equals("[Pendente]")){this.tabelaDeAlunos.setValueAt("[Pendente]", linhaSelecionada, 3);}
          if(situacao.equals("Presente")){this.tabelaDeAlunos.setValueAt("Presente", linhaSelecionada, 3);}
          if(situacao.equals("Ausente")){this.tabelaDeAlunos.setValueAt("Ausente", linhaSelecionada, 3);}
        }
    }
    
    
    //Verificar se o dia da semana é o mesmo da turma
    private boolean verificarNovoDado() throws SQLException{
        ArrayList <Horarios> horarios = horariosDao.pesquisarHorarios("SELECT * FROM tblHorarios WHERE codTurma = "+this.retornarCodTurma());
        ArrayList <DayOfWeek> diasDaSemana = new ArrayList<>();
        
        for(int linhas=0; linhas<horarios.size(); linhas++){
            switch(horarios.get(linhas).getDiaDaSemana()){
                case "Seg":
                    diasDaSemana.add(DayOfWeek.MONDAY);
                break;
                
                case "Ter":
                    diasDaSemana.add(DayOfWeek.TUESDAY);
                break;
                
                case "Qua":
                    diasDaSemana.add(DayOfWeek.WEDNESDAY);
                break;
                
                case "Qui":
                    diasDaSemana.add(DayOfWeek.THURSDAY);
                break;
                
                case "Sex":
                    diasDaSemana.add(DayOfWeek.FRIDAY);
                break;
                
                case "Sáb":
                    diasDaSemana.add(DayOfWeek.SATURDAY);
                break;
                
                case "Dom":
                    diasDaSemana.add(DayOfWeek.SUNDAY);
                break;       
            }
        }
        //Verifica se um dos dias da semana pegos no banco é igual a hoje
        DayOfWeek dataAtual = LocalDate.now().getDayOfWeek();
        int verificador = 0;
        for(int linhas = 0; linhas < diasDaSemana.size(); linhas++){
            if(diasDaSemana.get(linhas).equals(dataAtual)){
                verificador++;
            }
        }
        
        return verificador>0;
    }
    
    private int retornarCodTurma(){
        String turma = view.getComboTurmas().getSelectedItem().toString();
        return Integer.parseInt(turma.split("\\.")[0]);
    }
}
