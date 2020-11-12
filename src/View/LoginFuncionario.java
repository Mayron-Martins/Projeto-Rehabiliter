/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.adicionais.LoginFuncionarioController;
import java.awt.Color;
import java.awt.Point;
import static java.lang.Thread.sleep;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

/**
 *
 * @author 55989
 */
public class LoginFuncionario extends javax.swing.JFrame {
    private Point point=new Point();
    private final LoginFuncionarioController controller;
    private final telainicialFuncionario telaInicioFuncionario = new telainicialFuncionario();
    
    /**
     * Creates new form LoginFuncionário
     */
    public LoginFuncionario() {
        initComponents();
        controller = new LoginFuncionarioController(this);
        botaosair.setBackground(new Color(0,0,0,0));
        botaoentrarFuncionario.setBackground(new Color(0,0,0,0));
        botaoDesenvolvedor.setBackground(new Color(0,0,0,0));
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

        botaosair = new javax.swing.JButton();
        campoCpfFuncionario = new javax.swing.JFormattedTextField();
        campoSenha = new javax.swing.JPasswordField();
        botaoDesenvolvedor = new javax.swing.JButton();
        botaoentrarFuncionario = new javax.swing.JButton();
        botaoGerente = new javax.swing.JButton();
        plano_de_fundo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        botaosair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/loginfuncionário/botãosair.png"))); // NOI18N
        botaosair.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaosair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaosairActionPerformed(evt);
            }
        });
        getContentPane().add(botaosair, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 10, 40, -1));

        try {
            campoCpfFuncionario.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        getContentPane().add(campoCpfFuncionario, new org.netbeans.lib.awtextra.AbsoluteConstraints(178, 206, 300, 26));
        getContentPane().add(campoSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(178, 248, 300, 26));

        botaoDesenvolvedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoDesenvolvedor.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/loginfuncionário/botaoHoverDesenvolvedor.png"))); // NOI18N
        botaoDesenvolvedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoDesenvolvedorActionPerformed(evt);
            }
        });
        getContentPane().add(botaoDesenvolvedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(197, 403, 255, 31));

        botaoentrarFuncionario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoentrarFuncionario.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/loginfuncionário/botaoHoverEntrar.png"))); // NOI18N
        botaoentrarFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoentrarFuncionarioActionPerformed(evt);
            }
        });
        getContentPane().add(botaoentrarFuncionario, new org.netbeans.lib.awtextra.AbsoluteConstraints(252, 313, 150, 32));

        botaoGerente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoGerente.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/loginfuncionário/botaoHoverGerenteescuroo.png"))); // NOI18N
        botaoGerente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoGerenteActionPerformed(evt);
            }
        });
        getContentPane().add(botaoGerente, new org.netbeans.lib.awtextra.AbsoluteConstraints(196, 362, 255, 31));

        plano_de_fundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/loginfuncionário/background.jpg"))); // NOI18N
        getContentPane().add(plano_de_fundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaosairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaosairActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_botaosairActionPerformed

    private void botaoentrarFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoentrarFuncionarioActionPerformed
        try {
            controller.entrarSistema();
        } catch (SQLException ex) {
            Logger.getLogger(LoginFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(LoginFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botaoentrarFuncionarioActionPerformed

    private void botaoDesenvolvedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoDesenvolvedorActionPerformed
        // TODO add your handling code here:
        LoginDesenvolvedor login=new LoginDesenvolvedor();
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_botaoDesenvolvedorActionPerformed

    private void botaoGerenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoGerenteActionPerformed
        // TODO add your handling code here:
    
        LoginGerente login= new LoginGerente();
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_botaoGerenteActionPerformed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        // TODO add your handling code here:
       // Point p = this.getLocation();
        //this.setLocation(p.x + evt.getX()-point.x, p.y + evt.getY()-point.y);
    }//GEN-LAST:event_formMouseDragged

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked


    }//GEN-LAST:event_formMouseClicked

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        // TODO add your handling code here
        point.x=evt.getX();
        point.y=evt.getY();
    }//GEN-LAST:event_formMousePressed

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
            java.util.logging.Logger.getLogger(LoginFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginFuncionario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoDesenvolvedor;
    private javax.swing.JButton botaoGerente;
    private javax.swing.JButton botaoentrarFuncionario;
    private javax.swing.JButton botaosair;
    private javax.swing.JFormattedTextField campoCpfFuncionario;
    private javax.swing.JPasswordField campoSenha;
    private javax.swing.JLabel plano_de_fundo;
    // End of variables declaration//GEN-END:variables

public void exibeMensagem(String mensagem) {
      JOptionPane.showMessageDialog(null, mensagem);
    }

    public JFormattedTextField getCampoCpfFuncionario() {
        return campoCpfFuncionario;
    }

    public JPasswordField getCampoSenha() {
        return campoSenha;
    }
    
    public void senhaErrada(){
        Point p= this.getLocation();
        LoginFuncionario login=this;
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
    }

    public telainicialFuncionario getTelaInicioFuncionario() {
        return telaInicioFuncionario;
    }
    
    

}