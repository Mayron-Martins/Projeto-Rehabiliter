/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.adicionais;

import Dao.ServicosDao;
import Dao.TurmasDao;
import Model.auxiliar.Servicos;
import Model.auxiliar.Turmas;
import View.AlunosCadastro;
import View.ServicosView;
import View.TurmasView;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Mayro
 */
public class AdicionarAlunosController {
    private final AlunosCadastro view;
    private final TurmasDao turmasDao = new TurmasDao();
    private final ServicosDao servicosDao = new ServicosDao();

    public AdicionarAlunosController(AlunosCadastro view) {
        this.view = view;
    }
    
    public void verificacaoDeTurmaEServico() throws SQLException{
        ArrayList <Turmas> turmas = turmasDao.selecionarTodasTurmas();
        ArrayList <Servicos> servicos = servicosDao.selecionarTodosServicos();
        ServicosView telaServicos = new ServicosView();
        TurmasView telaTurmas = new TurmasView();
        
        if(turmas==null){
            view.exibeMensagem("Sem Turmas Cadastradas! Adicione Alguma Para Entrar Nessa Tela!");
            telaTurmas.setVisible(true);
            view.dispose();
        }
        else{
            for(int linhas=0; linhas<turmas.size(); linhas++){
            String subgrupo = "";    
            if(turmas.get(linhas).getSubgrupo()!=null){subgrupo = "-"+turmas.get(linhas).getSubgrupo();}
            view.getComboTurma().addItem(turmas.get(linhas).getNomeTurma()+subgrupo);
            }
        }
        
        if(servicos==null){
            view.exibeMensagem("Sem Serviços Cadastradas! Adicione Alguma Para Entrar Nessa Tela!");
            telaServicos.setVisible(true);
            view.dispose();
        }
        else{
            for(int linhas=0; linhas<servicos.size(); linhas++){ 
            view.getComboPlano().addItem(servicos.get(linhas).getNome()+"-"+servicos.get(linhas).getFormaPagamento());
            }
        }
    }
    
    public void adicionarTurmas(){
        
    }
    
}
