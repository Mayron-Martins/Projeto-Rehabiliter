/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.adicionais.FrequenciaTurmasController;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;

/**
 *
 * @author 55989
 */
public class turmasFrequencia extends javax.swing.JFrame {
    private final FrequenciaTurmasController controller;
    private final JComboBox comboPresenca = new JComboBox();

    /**
     * Creates new form turmasFrequencia
     */
    public turmasFrequencia() {
        initComponents();
        this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/imagens/rehabi.png")).getImage());
        controller = new FrequenciaTurmasController(this);
        botaoFechar.setBackground(new Color(0,0,0,0));
        botaoSalvar.setBackground(new Color(0,0,0,0));
        comboPresenca.addItem("[Pendente]");
        comboPresenca.addItem("Presente");
        comboPresenca.addItem("Ausente");
        scrollPaneAviso.setVisible(false);
        comboPeriodo.setEnabled(false);
        comboIntervalo.setEnabled(false);
        
        
        comboPresenca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboServicosActionPerformed(evt);
            }
            private void comboServicosActionPerformed(ActionEvent evt) {
            int linhaSelecionada = tabelaAlunos.getSelectedRow();
                if(tabelaAlunos.isRowSelected(linhaSelecionada)){
                    controller.setarComboPresenca();
                }
        
    }
        });
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
        tabelaAlunos = new javax.swing.JTable();
        comboPeriodo = new javax.swing.JComboBox<>();
        comboIntervalo = new javax.swing.JComboBox<>();
        comboTurmas = new javax.swing.JComboBox<>();
        botaoAdicionar = new javax.swing.JButton();
        botaoSalvar = new javax.swing.JButton();
        scrollPaneAviso = new javax.swing.JScrollPane();
        campoAviso = new javax.swing.JTextArea();
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

        tabelaAlunos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tabelaAlunos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CodAluno", "Nome", "Confirmação", "Situação"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaAlunos.getTableHeader().setResizingAllowed(false);
        tabelaAlunos.getTableHeader().setReorderingAllowed(false);
        tabelaAlunos.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(comboPresenca));
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

        comboTurmas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[Nenhuma]" }));
        comboTurmas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTurmasActionPerformed(evt);
            }
        });
        getContentPane().add(comboTurmas, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 170, 200, 40));

        botaoAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoAdicionar.png"))); // NOI18N
        botaoAdicionar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoHoverAdicionar.png"))); // NOI18N
        botaoAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoAdicionarActionPerformed(evt);
            }
        });
        getContentPane().add(botaoAdicionar, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 230, 40));

        botaoSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/turmas/frequencia/salvar.png"))); // NOI18N
        botaoSalvar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/turmas/frequencia/salvarHover.png"))); // NOI18N
        botaoSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSalvarActionPerformed(evt);
            }
        });
        getContentPane().add(botaoSalvar, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 280, 230, 40));

        campoAviso.setEditable(false);
        campoAviso.setColumns(20);
        campoAviso.setRows(5);
        campoAviso.setAutoscrolls(false);
        scrollPaneAviso.setViewportView(campoAviso);

        getContentPane().add(scrollPaneAviso, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 250, 190, 70));
        campoAviso.setLineWrap(true);
        campoAviso.setWrapStyleWord(true);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/turmas/frequencia/fundo.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFecharActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_botaoFecharActionPerformed

    private void tabelaAlunosComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tabelaAlunosComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelaAlunosComponentHidden

    private void comboPeriodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPeriodoActionPerformed
        controller.ativarComboIntervalo();
        try {
            controller.setarCampoIntervalo();
        } catch (SQLException ex) {
            Logger.getLogger(turmasFrequencia.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(turmasFrequencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_comboPeriodoActionPerformed

    private void botaoSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSalvarActionPerformed
        try {
            controller.adicionarFrequenciaoBanco();
        } catch (ParseException ex) {
            Logger.getLogger(turmasFrequencia.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(turmasFrequencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botaoSalvarActionPerformed

    private void comboTurmasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTurmasActionPerformed
    controller.ativarComboPeriodo();
    }//GEN-LAST:event_comboTurmasActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            controller.setarTurmas();
        } catch (SQLException ex) {
            Logger.getLogger(turmasFrequencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowOpened

    private void botaoAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoAdicionarActionPerformed
        try {
            controller.adicionarLinhasATabela();
        } catch (SQLException | ParseException ex) {
            Logger.getLogger(turmasFrequencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botaoAdicionarActionPerformed

    private void comboIntervaloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboIntervaloActionPerformed
        try {
            controller.setarNovosDadosTabela();
        } catch (ParseException ex) {
            Logger.getLogger(turmasFrequencia.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(turmasFrequencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_comboIntervaloActionPerformed

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
            java.util.logging.Logger.getLogger(turmasFrequencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(turmasFrequencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(turmasFrequencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(turmasFrequencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new turmasFrequencia().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoAdicionar;
    private javax.swing.JButton botaoFechar;
    private javax.swing.JButton botaoSalvar;
    private javax.swing.JTextArea campoAviso;
    private javax.swing.JComboBox<String> comboIntervalo;
    private javax.swing.JComboBox<String> comboPeriodo;
    private javax.swing.JComboBox<String> comboTurmas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane painelderolagem;
    private javax.swing.JScrollPane scrollPaneAviso;
    private javax.swing.JTable tabelaAlunos;
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

    public JComboBox<String> getComboTurmas() {
        return comboTurmas;
    }

    public JTable getTabelaAlunos() {
        return tabelaAlunos;
    }

    public JComboBox getComboPresenca() {
        return comboPresenca;
    }

    public JTextArea getCampoAviso() {
        return campoAviso;
    }

    public JScrollPane getScrollPaneAviso() {
        return scrollPaneAviso;
    }

    public JButton getBotaoAdicionar() {
        return botaoAdicionar;
    }

    public JButton getBotaoSalvar() {
        return botaoSalvar;
    }

    
}
