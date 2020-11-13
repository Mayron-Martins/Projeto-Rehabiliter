/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.Color;

/**
 *
 * @author 55989
 */
public class telainicialFuncionario extends javax.swing.JFrame {

    /**
     * Creates new form telainicialFuncionario
     */
    public telainicialFuncionario() {
        initComponents();
        botaoSair.setBackground(new Color(0,0,0,0));
        botaoCaixa.setBackground(new Color(0,0,0,0));
        botaoAlunos.setBackground(new Color(0,0,0,0));
        botaoFinanceiro.setBackground(new Color(0,0,0,0));
        botaoFrequencia.setBackground(new Color(0,0,0,0));
        botaoMenu.setBackground(new Color(0,0,0,0));
        setExtendedState(MAXIMIZED_BOTH);
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

        botaoSair = new javax.swing.JButton();
        botaoCaixa = new javax.swing.JButton();
        animaçãoRehabiliter = new javax.swing.JLabel();
        botaoAlunos = new javax.swing.JButton();
        botaoFrequencia = new javax.swing.JButton();
        botaoFinanceiro = new javax.swing.JButton();
        botaoMenu = new javax.swing.JButton();
        planodefundo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        botaoSair.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSairActionPerformed(evt);
            }
        });
        getContentPane().add(botaoSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 688, 90, 25));

        botaoCaixa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/telainicialfuncionario/caixaprincipal.png"))); // NOI18N
        botaoCaixa.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/telainicialfuncionario/caixaHoverPrincipal.png"))); // NOI18N
        botaoCaixa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoCaixaActionPerformed(evt);
            }
        });
        getContentPane().add(botaoCaixa, new org.netbeans.lib.awtextra.AbsoluteConstraints(544, 90, 168, 203));

        animaçãoRehabiliter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/rehab.gif"))); // NOI18N
        getContentPane().add(animaçãoRehabiliter, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 570, 420, 190));

        botaoAlunos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/telainicialfuncionario/alunosprincipal.png"))); // NOI18N
        botaoAlunos.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/telainicialfuncionario/alunosHoverprincipal.png"))); // NOI18N
        botaoAlunos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoAlunosActionPerformed(evt);
            }
        });
        getContentPane().add(botaoAlunos, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 168, 203));

        botaoFrequencia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/telainicialfuncionario/frequenciaprincipal.png"))); // NOI18N
        botaoFrequencia.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/telainicialfuncionario/frequenciaHoverprincipal.png"))); // NOI18N
        botaoFrequencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoFrequenciaActionPerformed(evt);
            }
        });
        getContentPane().add(botaoFrequencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(208, 90, 168, 203));

        botaoFinanceiro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/telainicialfuncionario/financeiroprincipal.png"))); // NOI18N
        botaoFinanceiro.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/telainicialfuncionario/financeiroHoverprincipal.png"))); // NOI18N
        botaoFinanceiro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoFinanceiroActionPerformed(evt);
            }
        });
        getContentPane().add(botaoFinanceiro, new org.netbeans.lib.awtextra.AbsoluteConstraints(376, 90, 168, 203));

        botaoMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/telainicialfuncionario/menuprincipal.png"))); // NOI18N
        botaoMenu.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/telainicialfuncionario/menuHoverPrincipal.png"))); // NOI18N
        botaoMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoMenuActionPerformed(evt);
            }
        });
        getContentPane().add(botaoMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 40, 110, 70));

        planodefundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/telainicialfuncionario/tela-inicial-funcionário.jpg"))); // NOI18N
        getContentPane().add(planodefundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSairActionPerformed
        // TODO add your handling code here:
        this.dispose();
        LoginFuncionario jump = new LoginFuncionario();
        jump.setVisible(true);
    }//GEN-LAST:event_botaoSairActionPerformed

    private void botaoMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoMenuActionPerformed
        // TODO add your handling code here:
        Menu jump=new Menu();
        jump.setVisible(true);
    }//GEN-LAST:event_botaoMenuActionPerformed

    private void botaoAlunosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoAlunosActionPerformed
        // TODO add your handling code here:
        AlunosView jump=new AlunosView();
        jump.setVisible(true);
    }//GEN-LAST:event_botaoAlunosActionPerformed

    private void botaoFrequenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFrequenciaActionPerformed
        // TODO add your handling code here:
        turmasFrequencia abrir= new turmasFrequencia();
        abrir.setVisible(true);
    }//GEN-LAST:event_botaoFrequenciaActionPerformed

    private void botaoFinanceiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFinanceiroActionPerformed
        // TODO add your handling code here:
        Financeiro abrir=new Financeiro();
        abrir.setVisible(true);
    }//GEN-LAST:event_botaoFinanceiroActionPerformed

    private void botaoCaixaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoCaixaActionPerformed
        // TODO add your handling code here:
        Caixa abrir= new Caixa();
        abrir.setVisible(true);
    }//GEN-LAST:event_botaoCaixaActionPerformed

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
            java.util.logging.Logger.getLogger(telainicialFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(telainicialFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(telainicialFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(telainicialFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new telainicialFuncionario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel animaçãoRehabiliter;
    private javax.swing.JButton botaoAlunos;
    private javax.swing.JButton botaoCaixa;
    private javax.swing.JButton botaoFinanceiro;
    private javax.swing.JButton botaoFrequencia;
    private javax.swing.JButton botaoMenu;
    private javax.swing.JButton botaoSair;
    private javax.swing.JLabel planodefundo;
    // End of variables declaration//GEN-END:variables
}
