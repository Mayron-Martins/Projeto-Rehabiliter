/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.auxiliar;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

/**
 *
 * @author Mayro
 */
public class GerenciamentoDeJCheckBox {
    private final javax.swing.JFrame view;

    public GerenciamentoDeJCheckBox(JFrame view) {
        this.view = view;
    }
/*    
    public void desmarcarCaixas(){
        int contador;
        for(contador=0; contador<view.getComponents().length; contador++){
            if(view.getComponent(contador) instanceof JLayeredPane){
               desmarcarCaixas();
            }
            if(view.getComponent(contador) instanceof JCheckBox){
                ((JCheckBox)view.getComponent(contador)).setSelected(false);
            }
        }
    }
*/
}
