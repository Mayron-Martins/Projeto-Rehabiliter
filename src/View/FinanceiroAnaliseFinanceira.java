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
public class FinanceiroAnaliseFinanceira extends javax.swing.JFrame {

    /**
     * Creates new form FinanceiroAnaliseFinanceira
     */
    public FinanceiroAnaliseFinanceira() {
        initComponents();
        btnDetalhada.setBackground(new Color(0,0,0,0));
        btnResumida.setBackground(new Color(0,0,0,0));
        botaoFechar.setBackground(new Color(0,0,0,0));
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
        btnDetalhada = new javax.swing.JButton();
        btnResumida = new javax.swing.JButton();
        painelderolagem = new javax.swing.JScrollPane();
        tabelaServicos = new javax.swing.JTable();
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

        btnDetalhada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/financeiro/btnDetalhada.png"))); // NOI18N
        btnDetalhada.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/financeiro/btnDetalhadaHover.png"))); // NOI18N
        getContentPane().add(btnDetalhada, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 175, 146, 34));

        btnResumida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/financeiro/btnResumido.png"))); // NOI18N
        btnResumida.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/financeiro/btnResumidoHover.png"))); // NOI18N
        getContentPane().add(btnResumida, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 175, 146, 34));

        tabelaServicos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tabelaServicos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"adsa", "adsa", "adsa", null}
            },
            new String [] {
                "asdasdas", "asdasd", "dfdafsd", "asdas"
            }
        ));
        tabelaServicos.setFocusable(false);
        tabelaServicos.setGridColor(new java.awt.Color(255, 255, 255));
        tabelaServicos.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tabelaServicos.setRowHeight(25);
        tabelaServicos.setShowVerticalLines(false);
        tabelaServicos.getTableHeader().setReorderingAllowed(false);
        tabelaServicos.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                tabelaServicosComponentHidden(evt);
            }
        });
        painelderolagem.setViewportView(tabelaServicos);

        getContentPane().add(painelderolagem, new org.netbeans.lib.awtextra.AbsoluteConstraints(261, 350, 580, 320));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/financeiro/analiseFinanceira.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFecharActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_botaoFecharActionPerformed

    private void tabelaServicosComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tabelaServicosComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelaServicosComponentHidden

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
            java.util.logging.Logger.getLogger(FinanceiroAnaliseFinanceira.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FinanceiroAnaliseFinanceira.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FinanceiroAnaliseFinanceira.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FinanceiroAnaliseFinanceira.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FinanceiroAnaliseFinanceira().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoFechar;
    private javax.swing.JButton btnDetalhada;
    private javax.swing.JButton btnResumida;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane painelderolagem;
    private javax.swing.JTable tabelaServicos;
    // End of variables declaration//GEN-END:variables
}