/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.adicionais.LoginGerenteController;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import static java.lang.Thread.sleep;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

/**
 *
 * @author 55989
 */
public class LoginGerente extends javax.swing.JFrame {
    private final TelaInicialGerenteView telagerente = new TelaInicialGerenteView();
    private final LoginGerenteController controller;

    /**
     * Creates new form LoginGerente
     */
    public LoginGerente() {
        initComponents();
        controller = new LoginGerenteController(this);
        botaoentrarGerente.setBackground(new Color(0,0,0,0));
        botaoFuncionario.setBackground(new Color(0,0,0,0));
        botaoDesenvolvedor.setBackground(new Color(0,0,0,0));
        botaoSair.setBackground(new Color(0,0,0,0));
        botaoEsqueceuSenha.setBackground(new Color(0,0,0,0));
        botaoCadastrar.setBackground(new Color(0,0,0,0));
        botaoCadastrar.setVisible(false);
        this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/imagens/rehabi.png")).getImage());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        botaoCadastrar = new javax.swing.JButton();
        campoCpfFuncionario = new javax.swing.JFormattedTextField();
        campoSenha = new javax.swing.JPasswordField();
        botaoentrarGerente = new javax.swing.JButton();
        botaoFuncionario = new javax.swing.JButton();
        botaoDesenvolvedor = new javax.swing.JButton();
        botaoSair = new javax.swing.JButton();
        botaoEsqueceuSenha = new javax.swing.JButton();
        plano_de_fundo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        botaoCadastrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/logingerente/btnCadastro.png"))); // NOI18N
        botaoCadastrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoCadastrarActionPerformed(evt);
            }
        });
        getContentPane().add(botaoCadastrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 77, 180, 40));

        try {
            campoCpfFuncionario.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        campoCpfFuncionario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                campoCpfFuncionarioKeyPressed(evt);
            }
        });
        getContentPane().add(campoCpfFuncionario, new org.netbeans.lib.awtextra.AbsoluteConstraints(178, 206, 300, 26));

        campoSenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                campoSenhaKeyPressed(evt);
            }
        });
        getContentPane().add(campoSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(178, 248, 300, 26));

        botaoentrarGerente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoentrarGerente.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/loginfuncionário/botaoHoverEntrar.png"))); // NOI18N
        botaoentrarGerente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoentrarGerenteActionPerformed(evt);
            }
        });
        getContentPane().add(botaoentrarGerente, new org.netbeans.lib.awtextra.AbsoluteConstraints(253, 312, 150, 32));

        botaoFuncionario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoFuncionario.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/logingerente/botaoHoverFuncionario.png"))); // NOI18N
        botaoFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoFuncionarioActionPerformed(evt);
            }
        });
        getContentPane().add(botaoFuncionario, new org.netbeans.lib.awtextra.AbsoluteConstraints(196, 361, 255, 31));

        botaoDesenvolvedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoDesenvolvedor.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/loginfuncionário/botaoHoverDesenvolvedor.png"))); // NOI18N
        botaoDesenvolvedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoDesenvolvedorActionPerformed(evt);
            }
        });
        getContentPane().add(botaoDesenvolvedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(196, 402, 255, 31));

        botaoSair.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSairActionPerformed(evt);
            }
        });
        getContentPane().add(botaoSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 0, 50, 30));

        botaoEsqueceuSenha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/imagensparaseremtrocadas/btnEsqueceuSenha.png"))); // NOI18N
        botaoEsqueceuSenha.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoEsqueceuSenha.setPreferredSize(new java.awt.Dimension(111, 21));
        botaoEsqueceuSenha.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/imagensparaseremtrocadas/btnEsqueceuSenhaHover.png"))); // NOI18N
        botaoEsqueceuSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoEsqueceuSenhaActionPerformed(evt);
            }
        });
        getContentPane().add(botaoEsqueceuSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 280, 130, 20));

        plano_de_fundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/logingerente/login-gerente2.jpg"))); // NOI18N
        getContentPane().add(plano_de_fundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 650, 520));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoentrarGerenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoentrarGerenteActionPerformed
        controller.entrarSistema();
    }//GEN-LAST:event_botaoentrarGerenteActionPerformed

    private void botaoFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFuncionarioActionPerformed
        // TODO add your handling code here:
        
        LoginFuncionario login = new LoginFuncionario();
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_botaoFuncionarioActionPerformed

    private void botaoSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSairActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_botaoSairActionPerformed

    private void botaoDesenvolvedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoDesenvolvedorActionPerformed
        // TODO add your handling code here:
        LoginDesenvolvedor login=new LoginDesenvolvedor();
        login.setVisible(true);
        this.dispose();
        
        
    }//GEN-LAST:event_botaoDesenvolvedorActionPerformed

    private void botaoCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoCadastrarActionPerformed
        // TODO add your handling code here:
        RegistrodeGerente abrir= new RegistrodeGerente();
        abrir.setVisible(true);
    }//GEN-LAST:event_botaoCadastrarActionPerformed

    private void campoCpfFuncionarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoCpfFuncionarioKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            getRootPane().setDefaultButton(botaoentrarGerente);
        }
    }//GEN-LAST:event_campoCpfFuncionarioKeyPressed

    private void campoSenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoSenhaKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            getRootPane().setDefaultButton(botaoentrarGerente);
        }
    }//GEN-LAST:event_campoSenhaKeyPressed

    private void botaoEsqueceuSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoEsqueceuSenhaActionPerformed
        controller.recuperacaoSenha();
    }//GEN-LAST:event_botaoEsqueceuSenhaActionPerformed

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
            java.util.logging.Logger.getLogger(LoginGerente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginGerente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginGerente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginGerente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginGerente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoCadastrar;
    private javax.swing.JButton botaoDesenvolvedor;
    private javax.swing.JButton botaoEsqueceuSenha;
    private javax.swing.JButton botaoFuncionario;
    private javax.swing.JButton botaoSair;
    private javax.swing.JButton botaoentrarGerente;
    private javax.swing.JFormattedTextField campoCpfFuncionario;
    private javax.swing.JPasswordField campoSenha;
    private javax.swing.JLabel plano_de_fundo;
    // End of variables declaration//GEN-END:variables

    public JButton getBotaoCadastrar() {
        return botaoCadastrar;
    }

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
        LoginGerente login=this;
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

    public TelaInicialGerenteView getTelagerente() {
        return telagerente;
    }
    
    
}
