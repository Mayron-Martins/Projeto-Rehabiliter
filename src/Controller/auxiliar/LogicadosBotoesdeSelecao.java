/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.auxiliar;


import javax.swing.JCheckBox;

/**
 *
 * @author Mayro
 */
public class LogicadosBotoesdeSelecao {
    public void TwoButons(JCheckBox a, JCheckBox b, int escolha){
        switch(escolha){
            case 1:
            if(a.getSelectedObjects() != null){
            b.setSelected(true);
            a.setSelected(false);
            }
            break;
                
            case 2:
            if(b.getSelectedObjects() != null){
            a.setSelected(true);
            b.setSelected(false);
            }
            break;
            }
    }
    /*public void RestrincaodeCampodePesquisaAlternados(JCheckBox a, JCheckBox b, int escolha, Caixa caixa){
        switch(escolha){
            case 1:
            if(a.getSelectedObjects() != null){
            b.setSelected(true);
            a.setSelected(false);
            caixa.getCampodePesquisaProdutosporID().setEnabled(true);
            caixa.getCampodePesquisaProdutosporNome().setEnabled(false); 
            caixa.getCampodePesquisaProdutosporNome().setVisible(false);
            caixa.getCampodePesquisaProdutosporID().setVisible(true);
            }
            break;
                
            case 2:
            if(b.getSelectedObjects() != null){
            a.setSelected(true);
            b.setSelected(false);
            caixa.getCampodePesquisaProdutosporID().setEnabled(false);
            caixa.getCampodePesquisaProdutosporNome().setEnabled(true); 
            caixa.getCampodePesquisaProdutosporID().setVisible(false);
            caixa.getCampodePesquisaProdutosporNome().setVisible(true);
            }
            break;
            }

    }*/
    
 
}
