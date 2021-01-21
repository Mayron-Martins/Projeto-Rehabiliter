/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.OrcamentarioController;
import Controller.auxiliar.JMoneyField;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;

/**
 *
 * @author 55989
 */
public class FinanceiroAnaliseFinanceira extends javax.swing.JDialog {
    private final java.awt.Frame parent;
    private final OrcamentarioController controller;

    /**
     * Creates new form FinanceiroAnaliseFinanceira
     * @param parent
     * @param modal
     */
    public FinanceiroAnaliseFinanceira(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        this.parent = parent;
        
        controller = new OrcamentarioController(this);
        botaoVDetalhada.setBackground(new Color(0,0,0,0));
        botaoVResumida.setBackground(new Color(0,0,0,0));
        btnExportar.setBackground(new Color(0,0,0,0));
        btnImprimir.setBackground(new Color(0,0,0,0));
        botaoFechar.setBackground(new Color(0,0,0,0));
        btnAplicar.setBackground(new Color(0,0,0,0));
        this.setarComponentes();
        this.teclasDeAtalho();
        
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

        btnAplicar = new javax.swing.JButton();
        campoDataEspecífica = new com.toedter.calendar.JDateChooser();
        botaoFechar = new javax.swing.JButton();
        botaoVDetalhada = new javax.swing.JButton();
        botaoVResumida = new javax.swing.JButton();
        comboPeriodo = new javax.swing.JComboBox<>();
        painelderolagem = new javax.swing.JScrollPane();
        tabelaOrcamentaria = new javax.swing.JTable();
        campoGanhoRelativoTotal = new JMoneyField();
        R4 = new javax.swing.JLabel();
        campoPendente = new JMoneyField();
        R3 = new javax.swing.JLabel();
        campoDespesaTotal = new JMoneyField();
        R2 = new javax.swing.JLabel();
        campoGanhoTotal = new JMoneyField();
        R1 = new javax.swing.JLabel();
        R0 = new javax.swing.JLabel();
        campoTotalParcial = new JMoneyField();
        painelBotoesImprimirExportar = new javax.swing.JPanel();
        btnImprimir = new javax.swing.JButton();
        btnExportar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnAplicar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/imagensparaseremtrocadas/btnAplicar.png"))); // NOI18N
        btnAplicar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/financeiro/btnAplicarHover.png"))); // NOI18N
        btnAplicar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAplicarActionPerformed(evt);
            }
        });
        getContentPane().add(btnAplicar, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 150, 60, 20));
        getContentPane().add(campoDataEspecífica, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 150, 120, -1));

        botaoFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaofechar.png"))); // NOI18N
        botaoFechar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoHoverfechar.png"))); // NOI18N
        botaoFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoFecharActionPerformed(evt);
            }
        });
        getContentPane().add(botaoFechar, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 40, 220, 50));

        botaoVDetalhada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/financeiro/btnDetalhada.png"))); // NOI18N
        botaoVDetalhada.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/financeiro/btnDetalhadaHover.png"))); // NOI18N
        botaoVDetalhada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botaoVDetalhadaMouseClicked(evt);
            }
        });
        getContentPane().add(botaoVDetalhada, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 175, 146, 34));

        botaoVResumida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/financeiro/btnResumido.png"))); // NOI18N
        botaoVResumida.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/financeiro/btnResumidoHover.png"))); // NOI18N
        botaoVResumida.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botaoVResumidaMouseClicked(evt);
            }
        });
        getContentPane().add(botaoVResumida, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 175, 146, 34));

        comboPeriodo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[Nenhum]", "Sempre", "Diário", "Semanal", "Mensal", "Semestral", "Anual" }));
        comboPeriodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboPeriodoActionPerformed(evt);
            }
        });
        getContentPane().add(comboPeriodo, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 180, 196, -1));

        tabelaOrcamentaria.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tabelaOrcamentaria.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "codBanco", "Tipo", "Forma de Pagamento", "Valor", "Data", "Chave"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaOrcamentaria.setFocusable(false);
        tabelaOrcamentaria.setGridColor(new java.awt.Color(255, 255, 255));
        tabelaOrcamentaria.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tabelaOrcamentaria.setRowHeight(25);
        tabelaOrcamentaria.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelaOrcamentaria.setShowVerticalLines(false);
        tabelaOrcamentaria.getTableHeader().setReorderingAllowed(false);
        tabelaOrcamentaria.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                tabelaOrcamentariaComponentHidden(evt);
            }
        });
        painelderolagem.setViewportView(tabelaOrcamentaria);

        getContentPane().add(painelderolagem, new org.netbeans.lib.awtextra.AbsoluteConstraints(241, 350, 590, 320));

        campoGanhoRelativoTotal.setEditable(false);
        campoGanhoRelativoTotal.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        getContentPane().add(campoGanhoRelativoTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(88, 400, 142, 50));

        R4.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        R4.setText("R$");
        getContentPane().add(R4, new org.netbeans.lib.awtextra.AbsoluteConstraints(44, 400, -1, 50));

        campoPendente.setEditable(false);
        campoPendente.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        getContentPane().add(campoPendente, new org.netbeans.lib.awtextra.AbsoluteConstraints(688, 242, 142, 70));

        R3.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        R3.setText("R$");
        getContentPane().add(R3, new org.netbeans.lib.awtextra.AbsoluteConstraints(644, 242, -1, 70));

        campoDespesaTotal.setEditable(false);
        campoDespesaTotal.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        getContentPane().add(campoDespesaTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(488, 242, 142, 70));

        R2.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        R2.setText("R$");
        getContentPane().add(R2, new org.netbeans.lib.awtextra.AbsoluteConstraints(444, 242, -1, 70));

        campoGanhoTotal.setEditable(false);
        campoGanhoTotal.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        getContentPane().add(campoGanhoTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(288, 242, 142, 70));

        R1.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        R1.setText("R$");
        getContentPane().add(R1, new org.netbeans.lib.awtextra.AbsoluteConstraints(244, 242, -1, 70));

        R0.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        R0.setText("R$");
        getContentPane().add(R0, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 242, -1, 70));

        campoTotalParcial.setEditable(false);
        campoTotalParcial.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        getContentPane().add(campoTotalParcial, new org.netbeans.lib.awtextra.AbsoluteConstraints(88, 242, 142, 70));

        painelBotoesImprimirExportar.setBackground(new java.awt.Color(157, 197, 187));
        painelBotoesImprimirExportar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/imprimir/imprimir.png"))); // NOI18N
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });
        painelBotoesImprimirExportar.add(btnImprimir, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        btnExportar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/imprimir/exportar.png"))); // NOI18N
        btnExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarActionPerformed(evt);
            }
        });
        painelBotoesImprimirExportar.add(btnExportar, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, 230, 40));

        getContentPane().add(painelBotoesImprimirExportar, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 140, 540, 70));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/financeiro/analiseFinanceira.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFecharActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_botaoFecharActionPerformed

    private void tabelaOrcamentariaComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tabelaOrcamentariaComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelaOrcamentariaComponentHidden

    private void botaoVResumidaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botaoVResumidaMouseClicked
        botaoVResumida.setEnabled(true);
        botaoVDetalhada.setEnabled(false);
    }//GEN-LAST:event_botaoVResumidaMouseClicked

    private void botaoVDetalhadaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botaoVDetalhadaMouseClicked
        botaoVDetalhada.setEnabled(true);
        botaoVResumida.setEnabled(false);
    }//GEN-LAST:event_botaoVDetalhadaMouseClicked

    private void comboPeriodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPeriodoActionPerformed
        if(comboPeriodo.getSelectedIndex()>0){
            campoDataEspecífica.setDate(null);
            campoDataEspecífica.setEnabled(false);
            controller.setarTabelas();
        }
        else{
            campoDataEspecífica.setDate(null);
            campoDataEspecífica.setEnabled(true);
        }
    }//GEN-LAST:event_comboPeriodoActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        controller.imprimirDados();
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarActionPerformed
        controller.salvarDadosEmPlanilha();        
    }//GEN-LAST:event_btnExportarActionPerformed

    private void btnAplicarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAplicarActionPerformed
        controller.setarTabelas();
    }//GEN-LAST:event_btnAplicarActionPerformed

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
                FinanceiroAnaliseFinanceira dialog = new FinanceiroAnaliseFinanceira(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel R0;
    private javax.swing.JLabel R1;
    private javax.swing.JLabel R2;
    private javax.swing.JLabel R3;
    private javax.swing.JLabel R4;
    private javax.swing.JButton botaoFechar;
    private javax.swing.JButton botaoVDetalhada;
    private javax.swing.JButton botaoVResumida;
    private javax.swing.JButton btnAplicar;
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btnImprimir;
    private com.toedter.calendar.JDateChooser campoDataEspecífica;
    private javax.swing.JFormattedTextField campoDespesaTotal;
    private javax.swing.JFormattedTextField campoGanhoRelativoTotal;
    private javax.swing.JFormattedTextField campoGanhoTotal;
    private javax.swing.JFormattedTextField campoPendente;
    private javax.swing.JFormattedTextField campoTotalParcial;
    private javax.swing.JComboBox<String> comboPeriodo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel painelBotoesImprimirExportar;
    private javax.swing.JScrollPane painelderolagem;
    private javax.swing.JTable tabelaOrcamentaria;
    // End of variables declaration//GEN-END:variables

    public void exibeMensagem(String mensagem) {
      JOptionPane.showMessageDialog(null, mensagem);
    }

    public JButton getBotaoVDetalhada() {
        return botaoVDetalhada;
    }

    public JButton getBotaoVResumida() {
        return botaoVResumida;
    }

    public JFormattedTextField getCampoDespesaTotal() {
        return campoDespesaTotal;
    }

    public JFormattedTextField getCampoGanhoRelativoTotal() {
        return campoGanhoRelativoTotal;
    }

    public JFormattedTextField getCampoGanhoTotal() {
        return campoGanhoTotal;
    }

    public JFormattedTextField getCampoPendente() {
        return campoPendente;
    }

    public JFormattedTextField getCampoTotalParcial() {
        return campoTotalParcial;
    }

    public JComboBox<String> getComboPeriodo() {
        return comboPeriodo;
    }

    public JTable getTabelaOrcamentaria() {
        return tabelaOrcamentaria;
    }

    public JDateChooser getCampoDataEspecífica() {
        return campoDataEspecífica;
    }

    public Frame getParent() {
        return parent;
    }
    
    
    private void setarComponentes(){
        botaoVResumida.setEnabled(true);
        botaoVDetalhada.setEnabled(false);
        
        comboPeriodo.setSelectedIndex(0);
        painelBotoesImprimirExportar.setVisible(false);
        
        campoDataEspecífica.setEnabled(false);
    }
    
    public void alternarImprimir(){
        botaoVDetalhada.setEnabled(true);
        botaoVDetalhada.setVisible(false);
        botaoVResumida.setEnabled(false);
        botaoVResumida.setVisible(false);
        comboPeriodo.setSelectedIndex(0);
        painelBotoesImprimirExportar.setVisible(true);
        tabelaOrcamentaria.setEnabled(false);
    }
    private void teclasDeAtalho() {
        JRootPane meurootpane = getRootPane();
        meurootpane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "ESCAPE");
        meurootpane.getRootPane().getActionMap().put("ESCAPE", new AbstractAction("ESCAPE") {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        this.conjuntoTeclasAtalho(meurootpane);
    }
    
    private void conjuntoTeclasAtalho(JRootPane meurootpane){
        //Fechar Sistema
        meurootpane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("alt F4"), "FECHAR");
        meurootpane.getRootPane().getActionMap().put("FECHAR", new AbstractAction("FECHAR") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int showConfirmDialog = JOptionPane.showConfirmDialog(null, "Deseja Realmente encerrar esta sessão", "Nota", JOptionPane.YES_NO_OPTION);
                if(showConfirmDialog == JOptionPane.YES_OPTION){
                    controller.sairTela();
                    
                }
            }
        });
    }
}
