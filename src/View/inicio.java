/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.InicioController;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author 55989
 */

public class inicio extends javax.swing.JFrame {
    private final InicioController controller;
    private int percentual=0;
    /**
     * Creates new form inicio
     */
    public inicio() {
        initComponents();
        controller = new InicioController(this);
          Dimension tamTela = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension tamJan = getSize();
            setLocation(new Point((tamTela.width - tamJan.width)/2, (tamTela.height- tamJan.height)/2));
            setResizable(false);
        
        //controller.iniciarCriacaoBanco();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblcarrega = new javax.swing.JLabel();
        funcoes = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblcarrega.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        lblcarrega.setForeground(new java.awt.Color(255, 255, 255));
        lblcarrega.setText("0%");
        getContentPane().add(lblcarrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 230, -1, -1));

        funcoes.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        funcoes.setForeground(new java.awt.Color(254, 254, 254));
        funcoes.setText("Iniciando Programa");
        getContentPane().add(funcoes, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/rehab.gif"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 590, 320));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // Tela de splash
        new Thread(){
                public void run(){
                    try {
                        controller.iniciarCriacaoBanco();
                    } catch (SQLException ex) {
                        //exibeMensagem("Impossível Continuar!");
                    }
                while(percentual<100){
                percentual++;
                
                mudartexto("Aguarde mais um pouco...");
                lblcarrega.setText(String.valueOf(percentual)+"%");   
                try {sleep(30);} catch (Exception e) {}
                
                }//!-->FECHA O WHILE-->
                
                new LoginFuncionario().setVisible(true);
                inicio.this.dispose();
                }//!-->Fecha o run-->
        }.start();//"-->Fecha o Thread-->
        
    }//GEN-LAST:event_formWindowActivated

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new inicio().setVisible(true);

            }
        });
    }
    
public void exibeMensagem(String mensagem) {
      JOptionPane.showMessageDialog(null, mensagem);
    }
    
public void mudartexto(String texto){
    funcoes.setText(texto);
}

public void mudarPercentual (){
    this.percentual++;
    lblcarrega.setText(String.valueOf(this.percentual)+"%");
}
   
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel funcoes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblcarrega;
    // End of variables declaration//GEN-END:variables
}



