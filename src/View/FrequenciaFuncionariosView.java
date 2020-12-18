/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.adicionais.FrequenciaFuncionariosController;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;

/**
 *
 * @author 55989
 */
public class FrequenciaFuncionariosView extends javax.swing.JDialog {
    private final java.awt.Frame parent; 
    private final FrequenciaFuncionariosController controller;

    /**
     * Creates new form Frequencia
     * @param parent
     * @param modal
     */
    public FrequenciaFuncionariosView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        this.parent = parent;
        
        controller = new FrequenciaFuncionariosController(this);
        botaoFechar.setBackground(new Color(0,0,0,0));
        this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/imagens/rehabi.png")).getImage());
        comboIntervalo.setEnabled(false);
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
        painelderolagem1 = new javax.swing.JScrollPane();
        tabelaFuncionarios = new javax.swing.JTable();
        comboPeriodo = new javax.swing.JComboBox<>();
        comboIntervalo = new javax.swing.JComboBox<>();
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

        tabelaFuncionarios.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tabelaFuncionarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CodFuncionário", "Funcionário", "Situação"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaFuncionarios.getTableHeader().setResizingAllowed(false);
        tabelaFuncionarios.getTableHeader().setReorderingAllowed(false);
        tabelaFuncionarios.setFocusable(false);
        tabelaFuncionarios.setGridColor(new java.awt.Color(255, 255, 255));
        tabelaFuncionarios.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tabelaFuncionarios.setRowHeight(25);
        tabelaFuncionarios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelaFuncionarios.setShowVerticalLines(false);
        tabelaFuncionarios.getTableHeader().setReorderingAllowed(false);
        tabelaFuncionarios.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                tabelaFuncionariosComponentHidden(evt);
            }
        });
        painelderolagem1.setViewportView(tabelaFuncionarios);

        getContentPane().add(painelderolagem1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, 811, 340));

        comboPeriodo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[Nenhum]", "Última Semana", "Último Mês", "Último Semestre", "Último Ano" }));
        comboPeriodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboPeriodoActionPerformed(evt);
            }
        });
        getContentPane().add(comboPeriodo, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 210, 200, 40));

        comboIntervalo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[Nenhum]" }));
        comboIntervalo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboIntervaloActionPerformed(evt);
            }
        });
        getContentPane().add(comboIntervalo, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 210, 200, 40));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/frequencia/fundo.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFecharActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_botaoFecharActionPerformed

    private void tabelaFuncionariosComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tabelaFuncionariosComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelaFuncionariosComponentHidden

    private void comboPeriodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPeriodoActionPerformed
        controller.ativarComboIntervalo();
        try {
            controller.setarCampoIntervalo();
        } catch (SQLException ex) {
            Logger.getLogger(FrequenciaFuncionariosView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FrequenciaFuncionariosView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_comboPeriodoActionPerformed

    private void comboIntervaloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboIntervaloActionPerformed
        try {
            controller.adicionarDadosNaTabela();
        } catch (SQLException ex) {
            Logger.getLogger(FrequenciaFuncionariosView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FrequenciaFuncionariosView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_comboIntervaloActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            controller.notificacaoInicial();
        } catch (SQLException ex) {
            Logger.getLogger(FrequenciaFuncionariosView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FrequenciaFuncionariosView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowOpened

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
            java.util.logging.Logger.getLogger(FrequenciaFuncionariosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrequenciaFuncionariosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrequenciaFuncionariosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrequenciaFuncionariosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrequenciaFuncionariosView dialog = new FrequenciaFuncionariosView(new javax.swing.JFrame(), true);
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
    private javax.swing.JComboBox<String> comboIntervalo;
    private javax.swing.JComboBox<String> comboPeriodo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane painelderolagem1;
    private javax.swing.JTable tabelaFuncionarios;
    // End of variables declaration//GEN-END:variables

    public void exibeMensagem(String mensagem) {
      JOptionPane.showMessageDialog(null, mensagem);
    }
    
    public JComboBox<String> getComboIntervalo() {
        return comboIntervalo;
    }

    public JComboBox<String> getComboPeriodo() {
        return comboPeriodo;
    }

    public JTable getTabelaFuncionarios() {
        return tabelaFuncionarios;
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
