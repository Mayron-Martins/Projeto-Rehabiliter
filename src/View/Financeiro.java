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
public class Financeiro extends javax.swing.JFrame {

    /**
     * Creates new form Financeiro
     */
    public Financeiro() {
        initComponents();
        botaoFechar.setBackground(new Color(0,0,0,0));
        btnPlanodeContraServicos.setBackground(new Color(0,0,0,0));
        btnAnaliseFinanceira.setBackground(new Color(0,0,0,0));
        btnPlanodeEntradas.setBackground(new Color(0,0,0,0));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        botaoFechar = new javax.swing.JButton();
        btnPlanodeContraServicos = new javax.swing.JButton();
        btnAnaliseFinanceira = new javax.swing.JButton();
        btnPlanodeEntradas = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        botaoFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaofechar.png"))); // NOI18N
        botaoFechar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoHoverfechar.png"))); // NOI18N
        botaoFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoFecharActionPerformed(evt);
            }
        });
        getContentPane().add(botaoFechar, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 40, 220, 50));

        btnPlanodeContraServicos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/financeiro/btnplano.png"))); // NOI18N
        btnPlanodeContraServicos.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/financeiro/btnplanoHover.png"))); // NOI18N
        btnPlanodeContraServicos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlanodeContraServicosActionPerformed(evt);
            }
        });
        getContentPane().add(btnPlanodeContraServicos, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 240, 382, 47));

        btnAnaliseFinanceira.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/financeiro/btnanalisefinaceira.png"))); // NOI18N
        btnAnaliseFinanceira.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/financeiro/btnanalisefinaceirahover.png"))); // NOI18N
        btnAnaliseFinanceira.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnaliseFinanceiraActionPerformed(evt);
            }
        });
        getContentPane().add(btnAnaliseFinanceira, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 360, 382, 47));

        btnPlanodeEntradas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/financeiro/btnplanodeentradas.png"))); // NOI18N
        btnPlanodeEntradas.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/financeiro/btnplanodeentradashover.png"))); // NOI18N
        btnPlanodeEntradas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlanodeEntradasActionPerformed(evt);
            }
        });
        getContentPane().add(btnPlanodeEntradas, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 300, 382, 47));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/financeiro/telafundo.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFecharActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_botaoFecharActionPerformed

    private void btnPlanodeContraServicosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlanodeContraServicosActionPerformed
        // TODO add your handling code here:
       FinanceiroPlanodeContra abrir =new FinanceiroPlanodeContra();
       abrir.setVisible(true);
       
    }//GEN-LAST:event_btnPlanodeContraServicosActionPerformed

    private void btnAnaliseFinanceiraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnaliseFinanceiraActionPerformed
        // TODO add your handling code here:
        FinanceiroAnaliseFinanceira abrir = new FinanceiroAnaliseFinanceira();
        abrir.setVisible(true);
    }//GEN-LAST:event_btnAnaliseFinanceiraActionPerformed

    private void btnPlanodeEntradasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlanodeEntradasActionPerformed
        // TODO add your handling code here:
        FinanceiroPlanodeEntradas abrir= new FinanceiroPlanodeEntradas();
        abrir.setVisible(true);
    }//GEN-LAST:event_btnPlanodeEntradasActionPerformed

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
            java.util.logging.Logger.getLogger(Financeiro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Financeiro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Financeiro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Financeiro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Financeiro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoFechar;
    private javax.swing.JButton btnAnaliseFinanceira;
    private javax.swing.JButton btnPlanodeContraServicos;
    private javax.swing.JButton btnPlanodeEntradas;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
