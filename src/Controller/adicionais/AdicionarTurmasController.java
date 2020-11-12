/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.adicionais;

import Controller.auxiliar.VerificarCodigoNoBanco;
import Dao.TableCriatorPosInput;
import Dao.TurmasDao;
import Model.auxiliar.Horarios;
import Model.auxiliar.Turmas;
import View.TurmasAdicionar;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Mayro
 */
public class AdicionarTurmasController {
    private final TurmasAdicionar view;
    private final VerificarCodigoNoBanco verificar = new VerificarCodigoNoBanco();
    private final TableCriatorPosInput criarTabela = new TableCriatorPosInput();


    public AdicionarTurmasController(TurmasAdicionar view) {
        this.view = view;
    }
    
    public void adicionarTurma() throws SQLException{
        //Pegando Dados Da tela
        int codBancoTurma = verificar.verificarUltimo("tblTurmas", "codTurma")+1;
        int codBancoHorario = verificar.verificarUltimo("tblHorarios", "codHorario")+1;
        String nomeTurma = view.getCampoNome().getText();
        String subgrupo;
        if(view.getCampoSubgrupos().getText().equals("")){subgrupo = null;}
        else{ subgrupo = view.getCampoSubgrupos().getText();}
        
        int quantidadeMax;
        if(view.getCampoCapMax().getText().equals("")){quantidadeMax = 0;}
        else{quantidadeMax = Integer.parseInt(view.getCampoCapMax().getText());}
        String diasDaSemana = view.getDiasDaSemana();
        ArrayList <String> diasDaSemanaUnitario = new ArrayList<>();
        diasDaSemanaUnitario = view.getDiasDaSemanaUnitarios();
        
        String horario = view.getCampoHorario();
        
        Turmas turma = null;
        ArrayList <Horarios> horarios = new ArrayList <>();
        if(!view.getCampoHorario().equals("")){
        turma = new Turmas(codBancoTurma, nomeTurma, subgrupo, 0, quantidadeMax, diasDaSemana, horario);    

        
        int diferenca, contador = diasDaSemanaUnitario.size();
        while(contador>0){
        diferenca = diasDaSemanaUnitario.size()-contador;
        Horarios auxiliar = new Horarios(codBancoHorario, diasDaSemanaUnitario.get(diferenca), horario, 0, codBancoTurma);
        horarios.add(auxiliar);
        contador--;
        }
        }
        
        //Inserindo Dados
        if(nomeTurma.equals("")|| horario.equals("") ||view.testeValidacaoHorario()==false){
         view.exibeMensagem("Campos Preenchidos Incorretamente");
        } else{
            TurmasDao turmaDao = new TurmasDao();
            turmaDao.inserirDados(turma, horarios);
            criarTabela.tableFreqTurmas();
            view.exibeMensagem("Sucesso!");
            //Limpando Campos
            view.getCampoNome().setText("");
            view.getCampoHoras().setText("");
            view.getCampoMinutos().setText("");
            view.getCampoSubgrupos().setText("");
            view.getCampoCapMax().setText("");
            view.desmarcarCaixas();
        }
        
    }
    
    
}
