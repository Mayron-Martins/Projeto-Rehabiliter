/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.adicionais.RelatoriosFuncionariosController;
import java.awt.Color;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

/**
 *
 * @author 55989
 */
public class Relatoriosfun extends javax.swing.JFrame {
    private final RelatoriosFuncionariosController controller;

    /**
     * Creates new form Relatoriosfun
     */
    public Relatoriosfun() {
        initComponents();
        controller = new RelatoriosFuncionariosController(this);
        botaoFechar.setBackground(new Color(0,0,0,0));
        btExportar.setBackground(new Color(0,0,0,0));
        btImprimir.setBackground(new Color(0,0,0,0));
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
        painelderolagem = new javax.swing.JScrollPane();
        tabelaLogs = new javax.swing.JTable();
        comboPeriodo = new javax.swing.JComboBox<>();
        comboIntervalo = new javax.swing.JComboBox<>();
        comboFuncionarios = new javax.swing.JComboBox<>();
        scrollPaneAviso = new javax.swing.JScrollPane();
        campoDescricao = new javax.swing.JTextArea();
        btExportar = new javax.swing.JButton();
        btImprimir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
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

        tabelaLogs.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tabelaLogs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Data", "Ação", "Descrição"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class
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
        tabelaLogs.setFocusable(false);
        tabelaLogs.setGridColor(new java.awt.Color(255, 255, 255));
        tabelaLogs.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tabelaLogs.setRowHeight(25);
        tabelaLogs.setShowVerticalLines(false);
        tabelaLogs.getTableHeader().setReorderingAllowed(false);
        tabelaLogs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaLogsMouseClicked(evt);
            }
        });
        tabelaLogs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tabelaLogsKeyReleased(evt);
            }
        });
        painelderolagem.setViewportView(tabelaLogs);

        getContentPane().add(painelderolagem, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, 811, 340));

        comboPeriodo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[Nenhum]", "Última Semana", "Último Mês", "Último Semestre", "Último Ano" }));
        comboPeriodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboPeriodoActionPerformed(evt);
            }
        });
        getContentPane().add(comboPeriodo, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 170, 200, 40));

        comboIntervalo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[Nenhum]" }));
        comboIntervalo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboIntervaloActionPerformed(evt);
            }
        });
        getContentPane().add(comboIntervalo, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 170, 200, 40));

        comboFuncionarios.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[Nenhuma]" }));
        comboFuncionarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboFuncionariosActionPerformed(evt);
            }
        });
        getContentPane().add(comboFuncionarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 170, 200, 40));

        campoDescricao.setEditable(false);
        campoDescricao.setColumns(20);
        campoDescricao.setRows(5);
        campoDescricao.setAutoscrolls(false);
        scrollPaneAviso.setViewportView(campoDescricao);

        getContentPane().add(scrollPaneAviso, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 250, 190, 70));
        campoDescricao.setLineWrap(true);
        campoDescricao.setWrapStyleWord(true);

        btExportar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/imprimir/exportar.png"))); // NOI18N
        btExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExportarActionPerformed(evt);
            }
        });
        getContentPane().add(btExportar, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 280, 230, 40));

        btImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/imprimir/imprimir.png"))); // NOI18N
        btImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btImprimirActionPerformed(evt);
            }
        });
        getContentPane().add(btImprimir, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 230, 40));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/imprimir/relatoriosF.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFecharActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_botaoFecharActionPerformed

    private void comboPeriodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPeriodoActionPerformed
       controller.ativarComboIntervalo();
       
        try {
            controller.setarCampoIntervalo();
        } catch (SQLException | ParseException ex) {
            Logger.getLogger(Relatoriosfun.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_comboPeriodoActionPerformed

    private void comboIntervaloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboIntervaloActionPerformed
        try {
            controller.setarDadosTabela();
        } catch (ParseException ex) {
            Logger.getLogger(Relatoriosfun.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Relatoriosfun.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_comboIntervaloActionPerformed

    private void comboFuncionariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboFuncionariosActionPerformed
        controller.ativarComboPeriodo();
    }//GEN-LAST:event_comboFuncionariosActionPerformed

    private void btExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExportarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btExportarActionPerformed

    private void btImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btImprimirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btImprimirActionPerformed

    private void tabelaLogsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaLogsMouseClicked
        controller.selecionarTabela();
    }//GEN-LAST:event_tabelaLogsMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            controller.setarLogs();
        } catch (SQLException ex) {
            Logger.getLogger(Relatoriosfun.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Relatoriosfun.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowOpened

    private void tabelaLogsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaLogsKeyReleased
        controller.selecionarTabela();
    }//GEN-LAST:event_tabelaLogsKeyReleased

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
            java.util.logging.Logger.getLogger(Relatoriosfun.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Relatoriosfun.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Relatoriosfun.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Relatoriosfun.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Relatoriosfun().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoFechar;
    private javax.swing.JButton btExportar;
    private javax.swing.JButton btImprimir;
    private javax.swing.JTextArea campoDescricao;
    private javax.swing.JComboBox<String> comboFuncionarios;
    private javax.swing.JComboBox<String> comboIntervalo;
    private javax.swing.JComboBox<String> comboPeriodo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane painelderolagem;
    private javax.swing.JScrollPane scrollPaneAviso;
    private javax.swing.JTable tabelaLogs;
    // End of variables declaration//GEN-END:variables

    public void exibeMensagem(String mensagem) {
      JOptionPane.showMessageDialog(null, mensagem);
    }

    public JTextArea getCampoDescricao() {
        return campoDescricao;
    }

    public JComboBox<String> getComboFuncionarios() {
        return comboFuncionarios;
    }

    public JComboBox<String> getComboIntervalo() {
        return comboIntervalo;
    }

    public JComboBox<String> getComboPeriodo() {
        return comboPeriodo;
    }

    public JTable getTabelaLogs() {
        return tabelaLogs;
    }
    
    
}
