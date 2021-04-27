/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Paineis;

import Controller.AlunosController;
import View.AlunosView;
import View.Paineis.AlunosDetalhes;

/**
 *
 * @author Mayro
 */
public class AlunosDetalhesController extends AlunosController{
    private final AlunosDetalhes viewSecundaria;
    
    public AlunosDetalhesController(AlunosView view, AlunosDetalhes viewSecundaria) {
        super(view);
        this.viewSecundaria = viewSecundaria;
    }
    
}
