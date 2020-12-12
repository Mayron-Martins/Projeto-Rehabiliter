/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.auxiliar;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

/**
 *
 * @author Mayro
 */
public class teclaESC extends JFrame implements KeyListener{
    private final JFrame tela;
    public teclaESC(JFrame tela) {
        this.tela = tela;
        this.addKeyListener(this);
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
       this.sair(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.sair(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.sair(e);
    }
    
    private void sair(KeyEvent evt){
        if(evt.getKeyCode()==KeyEvent.VK_ESCAPE){
                tela.dispose();
            }
    }
}
