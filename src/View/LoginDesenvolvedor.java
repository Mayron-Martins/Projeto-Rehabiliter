/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.Color;
import java.awt.Point;

/**
 *
 * @author 55989
 */
public class LoginDesenvolvedor extends javax.swing.JFrame {

    /**
     * Creates new form LoginDesenvolvedor
     */
    public LoginDesenvolvedor() {
        initComponents();
        botaoSair.setBackground(new Color(0,0,0,0));
        botaoentrarDesenvolvedor.setBackground(new Color(0,0,0,0));
        botaoFuncionario.setBackground(new Color(0,0,0,0));
        botaoGerente.setBackground(new Color(0,0,0,0));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        inputCpfDesenvolvedor = new javax.swing.JTextField();
        inputSenhaDesenvolvedor = new javax.swing.JPasswordField();
        botaoSair = new javax.swing.JButton();
        botaoentrarDesenvolvedor = new javax.swing.JButton();
        botaoFuncionario = new javax.swing.JButton();
        botaoGerente = new javax.swing.JButton();
        planodefundo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        inputCpfDesenvolvedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputCpfDesenvolvedorActionPerformed(evt);
            }
        });
        getContentPane().add(inputCpfDesenvolvedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(178, 206, 300, 26));
        getContentPane().add(inputSenhaDesenvolvedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(178, 248, 300, 26));

        botaoSair.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSairActionPerformed(evt);
            }
        });
        getContentPane().add(botaoSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, -1, 20));

        botaoentrarDesenvolvedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoentrarDesenvolvedor.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/loginfuncionário/botaoHoverEntrar.png"))); // NOI18N
        botaoentrarDesenvolvedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoentrarDesenvolvedorActionPerformed(evt);
            }
        });
        getContentPane().add(botaoentrarDesenvolvedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(252, 313, 150, 32));

        botaoFuncionario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoFuncionario.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/logingerente/botaoHoverFuncionario.png"))); // NOI18N
        botaoFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoFuncionarioActionPerformed(evt);
            }
        });
        getContentPane().add(botaoFuncionario, new org.netbeans.lib.awtextra.AbsoluteConstraints(196, 362, 255, 31));

        botaoGerente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoGerente.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/loginfuncionário/botaoHoverGerenteescuroo.png"))); // NOI18N
        botaoGerente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoGerenteActionPerformed(evt);
            }
        });
        getContentPane().add(botaoGerente, new org.netbeans.lib.awtextra.AbsoluteConstraints(196, 403, 255, 31));

        planodefundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/logindesenvolvedor/planodefundo.jpg"))); // NOI18N
        getContentPane().add(planodefundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void inputCpfDesenvolvedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputCpfDesenvolvedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputCpfDesenvolvedorActionPerformed

    private void botaoSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSairActionPerformed
        System.exit(0);
    }//GEN-LAST:event_botaoSairActionPerformed

    private void botaoentrarDesenvolvedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoentrarDesenvolvedorActionPerformed

        //!-->TELA TREMER-->
        Point p= this.getLocation();
        LoginDesenvolvedor login=this;
        new Thread(){
            @Override
            public void run(){
                try {
                    for(int i=0; i<3; i++){
                        login.setLocation(p.x-10, p.y);
                        sleep(20);
                        login.setLocation(p.x+10, p.y);
                        sleep(20);
                    }
                }catch (Exception e) {}
            }}.start();
            //!-->FINAL TELA TREMER-->
    }//GEN-LAST:event_botaoentrarDesenvolvedorActionPerformed

    private void botaoFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFuncionarioActionPerformed
        // TODO add your handling code here:

        LoginFuncionario login = new LoginFuncionario();
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_botaoFuncionarioActionPerformed

    private void botaoGerenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoGerenteActionPerformed
        // TODO add your handling code here:

        LoginGerente login= new LoginGerente();
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_botaoGerenteActionPerformed

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
            java.util.logging.Logger.getLogger(LoginDesenvolvedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginDesenvolvedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginDesenvolvedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginDesenvolvedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginDesenvolvedor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoFuncionario;
    private javax.swing.JButton botaoGerente;
    private javax.swing.JButton botaoSair;
    private javax.swing.JButton botaoentrarDesenvolvedor;
    private javax.swing.JTextField inputCpfDesenvolvedor;
    private javax.swing.JPasswordField inputSenhaDesenvolvedor;
    private javax.swing.JLabel planodefundo;
    // End of variables declaration//GEN-END:variables
}
