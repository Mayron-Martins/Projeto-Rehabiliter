/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.adicionais;

import Controller.auxiliar.ConversaoDiasDaSemana;
import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Dao.TurmasDao;
import Model.auxiliar.Turmas;
import View.AlunosCadastroTurmasEHorarios;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mayro
 */
public class AlunosETurmasController {
    private final AlunosCadastroTurmasEHorarios view;
    private final TurmasDao turmasDao = new TurmasDao();
    private final DefaultTableModel tabelaTurmas;
    private final ConversaoDiasDaSemana converterDias = new ConversaoDiasDaSemana();
    private final ConversaodeDataParaPadraoDesignado converterHora = new ConversaodeDataParaPadraoDesignado();

    public AlunosETurmasController(AlunosCadastroTurmasEHorarios view) {
        this.view = view;
        tabelaTurmas = (DefaultTableModel)view.getTabelaTurmas().getModel();
    }
    
    public void listarTurmas() throws SQLException{
        ArrayList <Turmas> turmas = new ArrayList<>();
        turmas = this.turmasDao.selecionarTodasTurmas();
        for(int linhas=0; linhas<turmas.size(); linhas++){
            if(turmas.get(linhas).getQuantidadeMaximaAlunos()==0){
                Object[] dadosDaTabela = {turmas.get(linhas).getCodBanco(), 
                turmas.get(linhas).getNomeTurma(),
                converterHora.parseHour(turmas.get(linhas).getHorario()),
                this.converterDias.converterDiasDaSemana(turmas.get(linhas).getDiasDaSemana()),
                turmas.get(linhas).getQuantidadeAlunos(),"",
                };
                this.tabelaTurmas.addRow(dadosDaTabela);
            }
            else{
                Object[] dadosDaTabela = {turmas.get(linhas).getCodBanco(), 
                turmas.get(linhas).getNomeTurma(),
                converterHora.parseHour(turmas.get(linhas).getHorario()),
                this.converterDias.converterDiasDaSemana(turmas.get(linhas).getDiasDaSemana()),
                turmas.get(linhas).getQuantidadeAlunos(),turmas.get(linhas).getQuantidadeMaximaAlunos(),
                };
                this.tabelaTurmas.addRow(dadosDaTabela);
            }    
        }
    }
    
    public void limparTabela(){
        int quantLinhas = this.view.getTabelaTurmas().getRowCount();    
        for(int quant=0; quant<quantLinhas; quant++){
            this.tabelaTurmas.removeRow(quant);
        }
    }
    
    
}
