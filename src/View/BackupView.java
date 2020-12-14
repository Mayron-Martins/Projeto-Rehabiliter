/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.adicionais.BackupController;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/**
 *
 * @author 55989
 */
public class BackupView extends javax.swing.JDialog {
    private final java.awt.Frame parent; 
    private final int numeroTela = 7;
    private final BackupController controller;

    /**
     * Creates new form Backup
     * @param parent
     * @param modal
     */
    public BackupView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        this.parent = parent;
        
        controller = new BackupController(this);
        botaoFechar.setBackground(new Color(0,0,0,0));
        painelLocal.setBackground(new Color(11,13,138));
        painelNuvem.setBackground(new Color(11,13,138));
        btnExportarLocal.setBackground(new Color(0,0,0,0));
        btnExportarNuvem.setBackground(new Color(0,0,0,0));
        btnImportarLocal.setBackground(new Color(0,0,0,0));
        btnImportarNuvem.setBackground(new Color(0,0,0,0));
        painelLocal.setEnabled(true);
        painelNuvem.setEnabled(false);
        
        fecharTelaESC();
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
        painelNuvem = new javax.swing.JPanel();
        btnImportarNuvem = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnExportarNuvem = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        painelLocal = new javax.swing.JPanel();
        campoLocalizacaoLocal = new javax.swing.JTextField();
        campoDataLocal = new javax.swing.JTextField();
        campoHoraLocal = new javax.swing.JTextField();
        btnImportarLocal = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnExportarLocal = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        botaoFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaofechar.png"))); // NOI18N
        botaoFechar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoHoverfechar.png"))); // NOI18N
        botaoFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoFecharActionPerformed(evt);
            }
        });
        getContentPane().add(botaoFechar, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 40, 220, 50));

        painelNuvem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                painelNuvemMouseClicked(evt);
            }
        });
        painelNuvem.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnImportarNuvem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/backup/btnImportar.png"))); // NOI18N
        btnImportarNuvem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportarNuvemActionPerformed(evt);
            }
        });
        painelNuvem.add(btnImportarNuvem, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, -1));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("BACKUP EM NUVEM");
        painelNuvem.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, -1, -1));

        btnExportarNuvem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/backup/btnExportar.png"))); // NOI18N
        painelNuvem.add(btnExportarNuvem, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, -1, -1));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/backup/quadro.png"))); // NOI18N
        painelNuvem.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, -1, -1));

        getContentPane().add(painelNuvem, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 130, 340, 450));

        painelLocal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                painelLocalMouseClicked(evt);
            }
        });
        painelLocal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        painelLocal.add(campoLocalizacaoLocal, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 388, 278, 30));
        painelLocal.add(campoDataLocal, new org.netbeans.lib.awtextra.AbsoluteConstraints(105, 280, 210, 30));
        painelLocal.add(campoHoraLocal, new org.netbeans.lib.awtextra.AbsoluteConstraints(105, 320, 210, 30));

        btnImportarLocal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/backup/btnImportar.png"))); // NOI18N
        btnImportarLocal.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/backup/btnImportar.png"))); // NOI18N
        btnImportarLocal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportarLocalActionPerformed(evt);
            }
        });
        painelLocal.add(btnImportarLocal, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, -1));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("BACKUP LOCAL");
        painelLocal.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, -1, -1));

        btnExportarLocal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/backup/btnExportar.png"))); // NOI18N
        btnExportarLocal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarLocalActionPerformed(evt);
            }
        });
        painelLocal.add(btnExportarLocal, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/backup/quadro.png"))); // NOI18N
        painelLocal.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, -1, -1));

        getContentPane().add(painelLocal, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 130, 340, 450));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/backup/fundo.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFecharActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_botaoFecharActionPerformed

    private void btnImportarLocalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportarLocalActionPerformed
        try {
            controller.importarBanco();
        } catch (SQLException ex) {
            Logger.getLogger(BackupView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnImportarLocalActionPerformed

    private void btnImportarNuvemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportarNuvemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnImportarNuvemActionPerformed

    private void painelLocalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_painelLocalMouseClicked
        painelLocal.setEnabled(true);
        painelNuvem.setEnabled(false);
    }//GEN-LAST:event_painelLocalMouseClicked

    private void painelNuvemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_painelNuvemMouseClicked
        painelNuvem.setEnabled(true);
        painelLocal.setEnabled(false);
    }//GEN-LAST:event_painelNuvemMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            controller.inserirUltimoBackup();
        } catch (SQLException ex) {
            Logger.getLogger(BackupView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(BackupView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowOpened

    private void btnExportarLocalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarLocalActionPerformed
        controller.adicionarDadosnoBanco();
    }//GEN-LAST:event_btnExportarLocalActionPerformed

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
            java.util.logging.Logger.getLogger(BackupView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BackupView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BackupView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BackupView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                BackupView dialog = new BackupView(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoFechar;
    private javax.swing.JButton btnExportarLocal;
    private javax.swing.JButton btnExportarNuvem;
    private javax.swing.JButton btnImportarLocal;
    private javax.swing.JButton btnImportarNuvem;
    private javax.swing.JTextField campoDataLocal;
    private javax.swing.JTextField campoHoraLocal;
    private javax.swing.JTextField campoLocalizacaoLocal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel painelLocal;
    private javax.swing.JPanel painelNuvem;
    // End of variables declaration//GEN-END:variables
    public void exibeMensagem(String mensagem) {
      JOptionPane.showMessageDialog(null, mensagem);
    }
    public JTextField getCampoHoraLocal() {
        return campoHoraLocal;
    }

    public JTextField getCampoLocalizacaoLocal() {
        return campoLocalizacaoLocal;
    }

    public JTextField getCampoDataLocal() {
        return campoDataLocal;
    }
    
    public void fecharTelaESC() {
        JRootPane meurootpane = getRootPane();
        meurootpane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "ESCAPE");
        meurootpane.getRootPane().getActionMap().put("ESCAPE", new AbstractAction("ESCAPE") {

            

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public int getNumeroTela() {
        return numeroTela;
    }
    
    

}
