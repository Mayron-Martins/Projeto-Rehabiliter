/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Paineis;

import Controller.PlanoGastosController;
import View.FinanceiroPlanodeContra;
import View.Paineis.GastosDetalhes;

/**
 *
 * @author Mayro
 */
public class GastosDetalhesController extends PlanoGastosController{
    private final GastosDetalhes viewSecundaria;
    
    public GastosDetalhesController(FinanceiroPlanodeContra view, GastosDetalhes viewSecundaria) {
        super(view);
        this.viewSecundaria = viewSecundaria;
    }
    
}
