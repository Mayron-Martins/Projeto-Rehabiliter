/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.adicionais;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Dao.AlunosDao;
import Dao.TurmasDao;
import Model.Aluno;
import Model.auxiliar.Turmas;
import View.TelaInicialGerenteView;
import java.sql.SQLException;
import java.text.ParseException;
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
            String subgrupo="";
            if(turmas.get(0).getSubgrupo()!=null){subgrupo = "-"+turmas.get(0).getSubgrupo();}
            String dataAniversario = converterData.parseDate(alunos.get(linhas).getDatadenascimento());
            Object[] dadosDaTabelaAlunos = {alunos.get(linhas).getNome(),turmas.get(0).getCodBanco()+"."+turmas.get(0).getNomeTurma()+subgrupo, dataAniversario};
            this.tabelaDeAlunos.addRow(dadosDaTabelaAlunos);
            }
        }
    }
}
