/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Dao.FileCriator;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

/**
 *
 * @author 55989
 */
public class ImprimirExportar extends javax.swing.JDialog {
    private final java.awt.Frame parent;
    private final int numeroTela = 6;
    private final FileCriator criacaoDePasta = new FileCriator();
    private final turmasFrequencia telaTurmasFrequencia;
    private final FinanceiroAnaliseFinanceira telaAnaliseFinanceira;

    /**
     * Creates new form ImprimirExportar
     * @param parent
     * @param modal
     */
    public ImprimirExportar(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        this.parent = parent;
        telaTurmasFrequencia= new turmasFrequencia(parent, true);
        telaAnaliseFinanceira=new FinanceiroAnaliseFinanceira(parent, true);
        
        botaoFechar.setBackground(new Color(0,0,0,0));
        btnFrequencia.setBackground(new Color(0,0,0,0));
        btnRelatoriosOrca.setBackground(new Color(0,0,0,0));
        this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/imagens/rehabi.png")).getImage());
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
        btnFrequencia = new javax.swing.JButton();
        btnRelatoriosOrca = new javax.swing.JButton();
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

        btnFrequencia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/imprimir/btnfrequencia.png"))); // NOI18N
        btnFrequencia.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/imprimir/btnfrequenciaHover.png"))); // NOI18N
        btnFrequencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFrequenciaActionPerformed(evt);
            }
        });
        getContentPane().add(btnFrequencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 170, -1, -1));

        btnRelatoriosOrca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/imprimir/btnRelatoriosOrca.png"))); // NOI18N
        btnRelatoriosOrca.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/imprimir/btnRelatoriosOrcaHover.png"))); // NOI18N
        btnRelatoriosOrca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRelatoriosOrcaActionPerformed(evt);
            }
        });
        getContentPane().add(btnRelatoriosOrca, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 320, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/imprimir/fundo.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFecharActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_botaoFecharActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        criacaoDePasta.fileDocCriator();
    }//GEN-LAST:event_formWindowOpened

    private void btnFrequenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFrequenciaActionPerformed
        // TODO add your handling code here:
        telaTurmasFrequencia.alternarImprimir();
        telaTurmasFrequencia.setModal(true);
        telaTurmasFrequencia.setLocationRelativeTo(null);
        telaTurmasFrequencia.setVisible(true);
        this.dispose();
        
    }//GEN-LAST:event_btnFrequenciaActionPerformed

    private void btnRelatoriosOrcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRelatoriosOrcaActionPerformed
        // TODO add your handling code here:
        telaAnaliseFinanceira.alternarImprimir();
        telaAnaliseFinanceira.setModal(true);
        telaAnaliseFinanceira.setLocationRelativeTo(null);
        telaAnaliseFinanceira.setVisible(true);
        this.dispose();
        
    }//GEN-LAST:event_btnRelatoriosOrcaActionPerformed

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
            java.util.logging.Logger.getLogger(ImprimirExportar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ImprimirExportar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ImprimirExportar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ImprimirExportar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ImprimirExportar dialog = new ImprimirExportar(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnFrequencia;
    private javax.swing.JButton btnRelatoriosOrca;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

    public int getNumeroTela() {
        return numeroTela;
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
}
