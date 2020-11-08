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
public class MenuGerente extends javax.swing.JFrame {

    /**
     * Creates new form Menu
     */
    public MenuGerente() {
        initComponents();
        setBackground(new Color(0,0,0,0));
        botaoFechar.setBackground(new Color(0,0,0,0));
        botaoSair.setBackground(new Color(0,0,0,0));
        botaoAlunos.setBackground(new Color(0,0,0,0));
        botaoTurmas.setBackground(new Color(0,0,0,0));
        botaoProdutos.setBackground(new Color(0,0,0,0));
        botaoServicos.setBackground(new Color(0,0,0,0));
        botaoFinanceiro.setBackground(new Color(0,0,0,0));
        botaoBackup.setBackground(new Color(0,0,0,0));
        botaoImprimirExportar.setBackground(new Color(0,0,0,0));
        botaoCaixa.setBackground(new Color(0,0,0,0));
        this.setLocation(0, 1);
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
        botaoBackup = new javax.swing.JButton();
        botaoImprimirExportar = new javax.swing.JButton();
        botaoFinanceiro = new javax.swing.JButton();
        botaoServicos = new javax.swing.JButton();
        botaoTurmas = new javax.swing.JButton();
        botaoSair = new javax.swing.JButton();
        botaoAlunos = new javax.swing.JButton();
        botaoProdutos = new javax.swing.JButton();
        botaoCaixa = new javax.swing.JButton();
        planodefundo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        botaoFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoFecharActionPerformed(evt);
            }
        });
        getContentPane().add(botaoFechar, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 0, 40, 40));

        botaoBackup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/menufuncionario/botaoBackup.png"))); // NOI18N
        botaoBackup.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/menufuncionario/botaoHoverBackup.png"))); // NOI18N
        getContentPane().add(botaoBackup, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 500, 360, 50));

        botaoImprimirExportar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/menufuncionario/botaoImprimir.png"))); // NOI18N
        botaoImprimirExportar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/menufuncionario/botaoHoverImprimir.png"))); // NOI18N
        botaoImprimirExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoImprimirExportarActionPerformed(evt);
            }
        });
        getContentPane().add(botaoImprimirExportar, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 437, 360, 50));

        botaoFinanceiro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/menufuncionario/botaoFinanceiro.png"))); // NOI18N
        botaoFinanceiro.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/menufuncionario/botaoHoverFinanceiro.png"))); // NOI18N
        botaoFinanceiro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoFinanceiroActionPerformed(evt);
            }
        });
        getContentPane().add(botaoFinanceiro, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 373, 360, 50));

        botaoServicos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/menufuncionario/botaoServicos.png"))); // NOI18N
        botaoServicos.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/menufuncionario/botaoHoverServicos.png"))); // NOI18N
        botaoServicos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoServicosActionPerformed(evt);
            }
        });
        getContentPane().add(botaoServicos, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 305, 360, 50));

        botaoTurmas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/menufuncionario/botaoTurmas.png"))); // NOI18N
        botaoTurmas.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/menufuncionario/botaoHoverTurmas.png"))); // NOI18N
        botaoTurmas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoTurmasActionPerformed(evt);
            }
        });
        getContentPane().add(botaoTurmas, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 179, 360, 50));

        botaoSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/menufuncionario/botaoSair.png"))); // NOI18N
        botaoSair.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/menufuncionario/botaoHoverSair.png"))); // NOI18N
        botaoSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSairActionPerformed(evt);
            }
        });
        getContentPane().add(botaoSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 690, 270, 52));

        botaoAlunos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/menufuncionario/botaoAlunos.png"))); // NOI18N
        botaoAlunos.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/menufuncionario/botaoHoverAlunos.png"))); // NOI18N
        botaoAlunos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoAlunosActionPerformed(evt);
            }
        });
        getContentPane().add(botaoAlunos, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 113, 360, 50));

        botaoProdutos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/menufuncionario/botaoProdutos.png"))); // NOI18N
        botaoProdutos.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/menufuncionario/botaoHoverProdutos.png"))); // NOI18N
        botaoProdutos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoProdutosActionPerformed(evt);
            }
        });
        getContentPane().add(botaoProdutos, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 242, 360, 50));

        botaoCaixa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/menufuncionario/botaoCaixa.png"))); // NOI18N
        botaoCaixa.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/menufuncionario/botaoHoverCaixa.png"))); // NOI18N
        botaoCaixa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoCaixaActionPerformed(evt);
            }
        });
        getContentPane().add(botaoCaixa, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 564, 360, 50));

        planodefundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/menufuncionario/menu-principal-funcionário.png"))); // NOI18N
        getContentPane().add(planodefundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -60, -1, 880));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botaoFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFecharActionPerformed
        this.dispose();
        
    }//GEN-LAST:event_botaoFecharActionPerformed

    private void botaoSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSairActionPerformed
        // TODO add your handling code here:
        System.exit(0);
        
    }//GEN-LAST:event_botaoSairActionPerformed

    private void botaoCaixaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoCaixaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botaoCaixaActionPerformed

    private void botaoAlunosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoAlunosActionPerformed
        // TODO add your handling code here:
        this.dispose();
        Alunos jump=new Alunos();
        jump.setVisible(true);
    }//GEN-LAST:event_botaoAlunosActionPerformed

    private void botaoTurmasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoTurmasActionPerformed
        // TODO add your handling code here:
        this.dispose();
        TurmasView abrir=new TurmasView();
        abrir.setVisible(true);
    }//GEN-LAST:event_botaoTurmasActionPerformed

    private void botaoProdutosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoProdutosActionPerformed
        // TODO add your handling code here:
        this.dispose();
        produtos abrir=new produtos();
        abrir.setVisible(true);
        
    }//GEN-LAST:event_botaoProdutosActionPerformed

    private void botaoServicosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoServicosActionPerformed
        // TODO add your handling code here:
        this.dispose();
        ServicosView abrir=new ServicosView();
        abrir.setVisible(true);
    }//GEN-LAST:event_botaoServicosActionPerformed

    private void botaoFinanceiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFinanceiroActionPerformed
        // TODO add your handling code here:
        this.dispose();
        Financeiro abrir = new Financeiro();
        abrir.setVisible(true);
    }//GEN-LAST:event_botaoFinanceiroActionPerformed

    private void botaoImprimirExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoImprimirExportarActionPerformed
        // TODO add your handling code here:
        ImprimirExportarGerente abrir= new ImprimirExportarGerente();
        abrir.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_botaoImprimirExportarActionPerformed

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
            java.util.logging.Logger.getLogger(MenuGerente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuGerente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuGerente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuGerente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuGerente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoAlunos;
    private javax.swing.JButton botaoBackup;
    private javax.swing.JButton botaoCaixa;
    private javax.swing.JButton botaoFechar;
    private javax.swing.JButton botaoFinanceiro;
    private javax.swing.JButton botaoImprimirExportar;
    private javax.swing.JButton botaoProdutos;
    private javax.swing.JButton botaoSair;
    private javax.swing.JButton botaoServicos;
    private javax.swing.JButton botaoTurmas;
    private javax.swing.JLabel planodefundo;
    // End of variables declaration//GEN-END:variables
}