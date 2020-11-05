/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.adicionais;

import Controller.auxiliar.VerificarCodigoNoBanco;
import View.TurmasAdicionar;
import java.sql.SQLException;

/**
 *
 * @author Mayro
 */
public class AdicionarTurmasController {
    private final TurmasAdicionar view;
    private final VerificarCodigoNoBanco verificar = new VerificarCodigoNoBanco();

    public AdicionarTurmasController(TurmasAdicionar view) {
        this.view = view;
    }
    
    public void adicionarTurma() throws SQLException{
        int codBanco = verificar.verificarUltimo("tblTurmas", "codTurma")+1;
        String nomeTurma = view.getCampoNome().getText();
        //String subgrupo = view.ge
    }
    
    
    
}
