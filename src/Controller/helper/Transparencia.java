/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.helper;

import java.awt.Color;
import javax.swing.JFrame;

/**
 *
 * @author Mayro
 */
public class Transparencia {
    public void AplicarTransparencia(JFrame frame){
        frame.setUndecorated(true);
        frame.setBackground(new Color(0,0,0,70));
    }
    
    
    
}
