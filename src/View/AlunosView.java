/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;

/**
 *
 * @author 55989
 */
public class AlunosView extends javax.swing.JFrame {
private Point point = new Point();
    /**
     * Creates new form Alunos
     */
    public AlunosView() {
        initComponents();
        botaobuscar.setBackground(new Color(0,0,0,0));
        botaoFechar.setBackground(new Color(0,0,0,0));
        botaoAdicionar.setBackground(new Color(0,0,0,0));
        botaoRemover.setBackground(new Color(0,0,0,0));
        botaoEditar.setBackground(new Color(0,0,0,0));
        botaoListar.setBackground(new Color(0,0,0,0));
        tabelaAlunos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabelaAlunos.getTableHeader().setOpaque(false);
        tabelaAlunos.getTableHeader().setBackground(new Color(0,0,0));
        tabelaAlunos.getTableHeader().setForeground(new Color(0,0,0));
        tabelaAlunos.setRowHeight(25);
        botaoAlternar.setBackground(new Color(0,0,0,0));
        botaoAlternar1.setBackground(new Color(0,0,0,0));
        botaoAlternar2.setBackground(new Color(0,0,0,0));              
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelderolagem = new javax.swing.JScrollPane();
        tabelaAlunos = new javax.swing.JTable();
        botaoRemover = new javax.swing.JButton();
        botaoEditar = new javax.swing.JButton();
        botaoListar = new javax.swing.JButton();
        botaoFechar = new javax.swing.JButton();
        botaobuscar = new javax.swing.JButton();
        botaoAdicionar = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        botaoAlternar2 = new javax.swing.JButton();
        botaoAlternar1 = new javax.swing.JButton();
        botaoAlternar = new javax.swing.JButton();
        planodefundo = new javax.swing.JLabel();

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

        tabelaAlunos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tabelaAlunos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"adsa", "adsa", "adsa", null}
            },
            new String [] {
                "asdasdas", "asdasd", "dfdafsd", "asdas"
            }
        ));
        tabelaAlunos.setFocusable(false);
        tabelaAlunos.setGridColor(new java.awt.Color(255, 255, 255));
        tabelaAlunos.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tabelaAlunos.setRowHeight(25);
        tabelaAlunos.setShowVerticalLines(false);
        tabelaAlunos.getTableHeader().setReorderingAllowed(false);
        tabelaAlunos.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                tabelaAlunosComponentHidden(evt);
            }
        });
        painelderolagem.setViewportView(tabelaAlunos);

        getContentPane().add(painelderolagem, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, 811, 340));

        botaoRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoRemover.png"))); // NOI18N
        botaoRemover.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoHoverremover.png"))); // NOI18N
        botaoRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoRemoverActionPerformed(evt);
            }
        });
        getContentPane().add(botaoRemover, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 168, 205, 50));

        botaoEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoEditar.png"))); // NOI18N
        botaoEditar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoEditarHover.png"))); // NOI18N
        botaoEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoEditarActionPerformed(evt);
            }
        });
        getContentPane().add(botaoEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 168, 205, 50));

        botaoListar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoListar.png"))); // NOI18N
        botaoListar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoHoverListar.png"))); // NOI18N
        getContentPane().add(botaoListar, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 170, 205, 50));

        botaoFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaofechar.png"))); // NOI18N
        botaoFechar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoHoverfechar.png"))); // NOI18N
        botaoFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoFecharActionPerformed(evt);
            }
        });
        getContentPane().add(botaoFechar, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 40, 220, 50));

        botaobuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoBuscar.png"))); // NOI18N
        botaobuscar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoBuscarHover.png"))); // NOI18N
        getContentPane().add(botaobuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 230, 50, 40));

        botaoAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoAdicionar.png"))); // NOI18N
        botaoAdicionar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoHoverAdicionar.png"))); // NOI18N
        botaoAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoAdicionarActionPerformed(evt);
            }
        });
        getContentPane().add(botaoAdicionar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 168, 205, 50));
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 230, 320, 40));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 230, 170, 30));

        botaoAlternar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/btnAlunosh.png"))); // NOI18N
        botaoAlternar2.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/btnAlunos.png"))); // NOI18N
        getContentPane().add(botaoAlternar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 310, 120, -1));

        botaoAlternar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/btnPais.png"))); // NOI18N
        botaoAlternar1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/btnPaishover.png"))); // NOI18N
        getContentPane().add(botaoAlternar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 310, 120, -1));

        botaoAlternar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/btnEndereco.png"))); // NOI18N
        botaoAlternar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/btnEnderecohover.png"))); // NOI18N
        getContentPane().add(botaoAlternar, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 310, 120, -1));

        planodefundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/Alunos.jpg"))); // NOI18N
        getContentPane().add(planodefundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_formMouseClicked

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        // TODO add your handling code here:
        point.x = evt.getX();
        point.y = evt.getY();
        
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        // TODO add your handling code here:
        Point p = this.getLocation();
        this.setLocation(p.x+ evt.getX()-point.x,p.y+ evt.getY()-point.y);
    }//GEN-LAST:event_formMouseDragged

    private void botaoEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoEditarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botaoEditarActionPerformed

    private void tabelaAlunosComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tabelaAlunosComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelaAlunosComponentHidden

    private void botaoFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFecharActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_botaoFecharActionPerformed

    private void botaoAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoAdicionarActionPerformed
        // TODO add your handling code here:
        AlunosCadastro abrir= new AlunosCadastro();
        abrir.setVisible(true);
    }//GEN-LAST:event_botaoAdicionarActionPerformed

    private void botaoRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoRemoverActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botaoRemoverActionPerformed

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
            java.util.logging.Logger.getLogger(AlunosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AlunosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AlunosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AlunosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AlunosView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoAdicionar;
    private javax.swing.JButton botaoAlternar;
    private javax.swing.JButton botaoAlternar1;
    private javax.swing.JButton botaoAlternar2;
    private javax.swing.JButton botaoEditar;
    private javax.swing.JButton botaoFechar;
    private javax.swing.JButton botaoListar;
    private javax.swing.JButton botaoRemover;
    private javax.swing.JButton botaobuscar;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JScrollPane painelderolagem;
    private javax.swing.JLabel planodefundo;
    private javax.swing.JTable tabelaAlunos;
    // End of variables declaration//GEN-END:variables
}
