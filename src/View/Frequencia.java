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
public class Frequencia extends javax.swing.JFrame {

    /**
     * Creates new form Frequencia
     */
    public Frequencia() {
        initComponents();
        btnImprimir.setBackground(new Color(0,0,0,0));
        botaoFechar.setBackground(new Color(0,0,0,0));
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

        botaoFechar = new javax.swing.JButton();
        painelderolagem1 = new javax.swing.JScrollPane();
        tabelaAlunos1 = new javax.swing.JTable();
        jComboBox1 = new javax.swing.JComboBox<>();
        btnImprimir = new javax.swing.JButton();
        jComboBox4 = new javax.swing.JComboBox<>();
        jComboBox5 = new javax.swing.JComboBox<>();
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

        tabelaAlunos1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tabelaAlunos1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"adsa", "adsa", "adsa", null}
            },
            new String [] {
                "asdasdas", "asdasd", "dfdafsd", "asdas"
            }
        ));
        tabelaAlunos1.setFocusable(false);
        tabelaAlunos1.setGridColor(new java.awt.Color(255, 255, 255));
        tabelaAlunos1.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tabelaAlunos1.setRowHeight(25);
        tabelaAlunos1.setShowVerticalLines(false);
        tabelaAlunos1.getTableHeader().setReorderingAllowed(false);
        tabelaAlunos1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                tabelaAlunos1ComponentHidden(evt);
            }
        });
        painelderolagem1.setViewportView(tabelaAlunos1);

        getContentPane().add(painelderolagem1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, 811, 340));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 180, 200, 40));

        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/frequencia/btnimprimir.png"))); // NOI18N
        getContentPane().add(btnImprimir, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 240, -1, -1));

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(jComboBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 180, 200, 40));

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(jComboBox5, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 180, 200, 40));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/frequencia/fundo.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFecharActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_botaoFecharActionPerformed

    private void tabelaAlunos1ComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tabelaAlunos1ComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelaAlunos1ComponentHidden

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
            java.util.logging.Logger.getLogger(Frequencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frequencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frequencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frequencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frequencia().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoFechar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane painelderolagem1;
    private javax.swing.JTable tabelaAlunos1;
    // End of variables declaration//GEN-END:variables
}
