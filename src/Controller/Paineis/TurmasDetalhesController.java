/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Paineis;

import Controller.TurmasController;
import Model.auxiliar.Horarios;
import Model.auxiliar.Turmas;
import View.Paineis.TurmasDetalhes;
import View.TurmasView;
import java.awt.Dialog;
import java.awt.Dialog.ModalExclusionType;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;

/**
 *
 * @author Mayro
 */
public class TurmasDetalhesController extends TurmasController{
    private final TurmasDetalhes viewSecundaria;
    private boolean testeHorario;
    
    public TurmasDetalhesController(TurmasView view, TurmasDetalhes viewSecundaria) {
        super(view);
        this.viewSecundaria = viewSecundaria;
        testeHorario=false;
    }
    
    public void selecionarTabela(){
      if(this.view.getTabelaTurmas().getSelectedRow()!=-1){
          //Número da linha selecionada
          int linhaSelecionada = this.view.getTabelaTurmas().getSelectedRow();
          
          int codTurma = Integer.parseInt(tabelaDeTurmas.getValueAt(linhaSelecionada, 0).toString());
          
          Turmas turma = turmasDao.pesquisarTurmas("SELECT * FROM tblTurmas WHERE codTurma = "+codTurma).get(0);
          ArrayList <Horarios> horarios = horariosDao.pesquisarHorarios("SELECT * FROM tblHorarios WHERE codTurma = "+codTurma);
          
          //Inserir Dados na tela detalhes
          viewSecundaria.getCampoNome().setText(turma.getNomeTurma());
          viewSecundaria.getCampoCapMax().setText(turma.getQuantidadeMaximaAlunos()+"");
          viewSecundaria.getCampoQuantPresente().setText(turma.getQuantidadeAlunos()+"");
          
          inserirHorarios(horarios, turma.getHorario());
          
          viewSecundaria.trocaBotoes(false);
          if(!viewSecundaria.isVisible()){
              alterarLocalizacaoViews(true);
          }
      }
    }
    
    public void releasedTable(){
        if(viewSecundaria.isVisible()){
            limparCampos();
            selecionarTabela();
        }
    }
    
    @Override
    public void editarTurma(){
        int linhaSelecionada = view.getTabelaTurmas().getSelectedRow();
        int codTurma = Integer.parseInt(tabelaDeTurmas.getValueAt(linhaSelecionada, 0).toString());
        String situacao = tabelaDeTurmas.getValueAt(linhaSelecionada, 2).toString();
        
        String nome = viewSecundaria.getCampoNome().getText();
        int quantAlunos = Integer.parseInt(viewSecundaria.getCampoQuantPresente().getText());
        
        int quantMaxima = 0;
        if(viewSecundaria.getCampoQuantPresente().getText().trim().equals("")){
            quantMaxima = Integer.parseInt(viewSecundaria.getCampoQuantPresente().getText());
        }
        
        String horario = "Único";
        if(!viewSecundaria.getRadioHorUnico().isSelected()){
            horario = "Diversos";
        }
        
        String diasDaSemana = diasDaSemana();
        
        Turmas turma = new Turmas(codTurma, nome, quantAlunos, quantMaxima, diasDaSemana, horario, situacao);
        
        
        int codHorarios = Integer.parseInt(viewSecundaria.getCampoCodHorarios().getText());
        ArrayList <Horarios> horarios = horario(codHorarios, codTurma);
        
        if(nome.equals("")||testeHorario||testeQuantidades(quantAlunos, quantMaxima)){
            view.mensagemCritica("Campos Preenchidos Incorretamente", "Atenção");
        }
        else{
            turmasDao.atualizarDados(turma, horarios);
            setarLog("Edição de Dados de Turma", "Editou os dados da turma "+nome);
            listarTurmas();
            view.getTabelaTurmas().addRowSelectionInterval(linhaSelecionada, linhaSelecionada);
            view.exibeMensagem("Sucesso!");
            viewSecundaria.trocaBotoes(false);
        }
    }
    
    private void inserirHorarios(ArrayList <Horarios> horarios, String tipoHorario){
        String horaI, horaF;
        viewSecundaria.getCampoCodHorarios().setText(horarios.get(0).getCodBanco()+"");
        if(tipoHorario.equals("Único")){
            horaI = horarios.get(0).getHorarioInicio();
            horaF = horarios.get(0).getHorarioFim();
            viewSecundaria.getRadioHorUnico().setSelected(true);
            for(Horarios horario : horarios){
                viewSecundaria.getDiasSemana().get(horario.getIndiceSemana()-1).setSelected(true);
            }
            //Inserir no Painel de Horário único
            viewSecundaria.getCampoHorariosI().get(0).setText(horaI);
            viewSecundaria.getCampoHorariosF().get(0).setText(horaF);
        }else{
            viewSecundaria.paineisHorarios(false);
            for(Horarios horario : horarios){
                viewSecundaria.getDiasSemana().get(horario.getIndiceSemana()-1).setSelected(true);
                
                horaI = horario.getHorarioInicio();
                horaF = horario.getHorarioFim();
                
                //Adicionando no painel com horários diversos
                viewSecundaria.getCampoHorariosI().get(horario.getIndiceSemana()).setText(horaI);
                viewSecundaria.getCampoHorariosF().get(horario.getIndiceSemana()).setText(horaF);
                
                viewSecundaria.getCampoHorariosI().get(horario.getIndiceSemana()).setVisible(true);
                viewSecundaria.getCampoHorariosF().get(horario.getIndiceSemana()).setVisible(true);
                
                
            }
        }
    }
    
    private String diasDaSemana(){
        String dias="";
        for(JCheckBox caixa : viewSecundaria.getDiasSemana()){
            if(caixa.isSelected()){
                dias+=caixa.getText();
            }
        }  
        return dias;
    }
    
    private ArrayList<Horarios> horario(int codHorario, int codTurma){
        String horaI, horaF;
        int indiceSemana=1;
        ArrayList<Horarios> horarios = new ArrayList<>();
        if(viewSecundaria.getRadioHorUnico().isSelected()){
            horaI = viewSecundaria.getCampoHorariosI().get(0).getText();
            horaF = viewSecundaria.getCampoHorariosF().get(0).getText();
              
            for(JCheckBox caixa : viewSecundaria.getDiasSemana()){
                if(caixa.isSelected()){
                    horarios.add(new Horarios(codHorario, caixa.getText(), indiceSemana, horaI, horaF, 0, codTurma));
                }
                indiceSemana++;
            }
            
            if(testeHora(horaI)){
                testeHorario=true;
            }
            if(!horaF.trim().equals(":")&&testeHora(horaF)){
                testeHorario = true;
            }
            
            
        }
        
        else{
            int cont=1;
            for(JCheckBox caixa : viewSecundaria.getDiasSemana()){
                if(caixa.isSelected()){
                    horaI = viewSecundaria.getCampoHorariosI().get(cont).getText();
                    horaF = viewSecundaria.getCampoHorariosF().get(cont).getText();
                    horarios.add(new Horarios(codHorario, caixa.getText(), indiceSemana, horaI, horaF, 0, codTurma));
                    
                    if(testeHora(horaI)){
                        testeHorario=true;
                    }
                    if(!horaF.trim().equals(":")&&testeHora(horaF)){
                        testeHorario = true;
                    }
                }
                cont++;
                indiceSemana++;
            }
        }
        return horarios;
    }
    
    private boolean testeHora(String hora){
        if(hora.trim().equals(":")){
            return true;
        }
        
        int horas = Integer.parseInt(hora.split(":")[0]);
        int minutos = Integer.parseInt(hora.split(":")[1]);
        
        return horas>23||minutos>59;
    }
    
    private boolean testeQuantidades(int quantPresente, int quantMaxima){
        return quantMaxima>0&&quantMaxima<quantPresente;
    }
    
    private void alterarLocalizacaoViews(boolean ativarDetalhes){
        int view1X = view.getX();
        int view1Y = view.getY();
        int largView1 = view.getWidth();
        int largView2 = viewSecundaria.getWidth();
        int deslocamentoX = largView2/2;
        int espacoEntreViews = 1;
        
        if(ativarDetalhes){
            view.setLocation(view1X-deslocamentoX, view1Y);
            viewSecundaria.setLocation(view1X+largView1+espacoEntreViews-deslocamentoX, view1Y);
            viewSecundaria.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
            viewSecundaria.setVisible(true);
        }
        else{
            view.setLocation(view1X+deslocamentoX, view1Y);
            viewSecundaria.setLocation(view1X+largView1+espacoEntreViews+deslocamentoX, view1Y);
            limparCampos();
            viewSecundaria.dispose();
        }
        
    }
    
    @Override
    public void sairTela(){
        alterarLocalizacaoViews(false);
    }
    
    private void limparCampos(){
        viewSecundaria.getCampoNome().setText("");
        viewSecundaria.getCampoCapMax().setText("");
        viewSecundaria.getCampoQuantPresente().setText("");
        viewSecundaria.getCampoCodHorarios().setText("");
        
        for(JCheckBox caixa : viewSecundaria.getDiasSemana()){
            caixa.setSelected(false);
        }
        for(JFormattedTextField campo : viewSecundaria.getCampoHorariosI()){
            campo.setValue(null);
        }
        
        for(JFormattedTextField campo : viewSecundaria.getCampoHorariosF()){
            campo.setValue(null);
        }
        viewSecundaria.setarConfigsInciciais();
    }
    
}
