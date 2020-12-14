/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.ServicosController;
import Controller.auxiliar.JMoneyField;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/**
 *
 * @author 55989
 */
public class ServicosView extends javax.swing.JDialog {
    private final java.awt.Frame parent;
    private final int numeroTela = 4;
    private final ServicosController controller;
    //private final JComboBox combo = new JComboBox();
    private final JFormattedTextField valorFormatado = new JMoneyField();
    private final ServicosAdicionar telaServicosAdicionar;

    /**
     * Creates new form Servicos
     * @param parent
     * @param modal
     */
    public ServicosView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        this.parent = parent;
        telaServicosAdicionar=new ServicosAdicionar(parent, false);
        
        controller = new ServicosController(this);
        btnAdicionar.setBackground(new Color(0,0,0,0));
        btnEditar.setBackground(new Color(0,0,0,0));
        btnRemover.setBackground(new Color(0,0,0,0));
        btnFechar.setBackground(new Color(0,0,0,0));
        desabilitarComponentes();
        fecharTelaESC();
        this.setarValores();
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

        campoOutroPeriodo = new javax.swing.JTextField();
        comboPeriodo = new javax.swing.JComboBox<>();
        metodoPagamento = new javax.swing.JComboBox<>();
        btnRemover = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnAdicionar = new javax.swing.JButton();
        btnFechar = new javax.swing.JButton();
        painelderolagem = new javax.swing.JScrollPane();
        tabelaServicos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        campoOutroPeriodo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                campoOutroPeriodoMouseClicked(evt);
            }
        });
        getContentPane().add(campoOutroPeriodo, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 295, 130, 30));

        comboPeriodo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Diária", "Semanal", "Mensal", "Trimestral", "Semestral", "Anual" }));
        comboPeriodo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comboPeriodoMouseClicked(evt);
            }
        });
        getContentPane().add(comboPeriodo, new org.netbeans.lib.awtextra.AbsoluteConstraints(354, 295, 160, 30));

        metodoPagamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[Nenhuma]", "Dinheiro", "Boleto", "Cartão de Crédito", "Cartão de Débito" }));
        metodoPagamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                metodoPagamentoActionPerformed(evt);
            }
        });
        getContentPane().add(metodoPagamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(678, 295, 160, 30));

        btnRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/servicos/btnRemover.png"))); // NOI18N
        btnRemover.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/servicos/btnRemoverHover.png"))); // NOI18N
        btnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverActionPerformed(evt);
            }
        });
        getContentPane().add(btnRemover, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 210, 210, 60));

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/servicos/btnEditar.png"))); // NOI18N
        btnEditar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/servicos/btnEditarHover.png"))); // NOI18N
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        getContentPane().add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 210, 210, 60));

        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/servicos/btnAdicionar.png"))); // NOI18N
        btnAdicionar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/servicos/btnAdicionarHover.png"))); // NOI18N
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });
        getContentPane().add(btnAdicionar, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 210, 210, 60));

        btnFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaofechar.png"))); // NOI18N
        btnFechar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoHoverfechar.png"))); // NOI18N
        btnFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharActionPerformed(evt);
            }
        });
        getContentPane().add(btnFechar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 230, 60));

        tabelaServicos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tabelaServicos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CodBanco", "Categoria", "Tipo", "Forma de Pagamento", "Valor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaServicos.getTableHeader().setResizingAllowed(false);
        tabelaServicos.getTableHeader().setReorderingAllowed(false);
        //tabelaServicos.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(combo));
        tabelaServicos.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(valorFormatado));
        tabelaServicos.setFocusable(false);
        tabelaServicos.setGridColor(new java.awt.Color(255, 255, 255));
        tabelaServicos.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tabelaServicos.setRowHeight(25);
        tabelaServicos.setShowVerticalLines(false);
        tabelaServicos.getTableHeader().setReorderingAllowed(false);
        tabelaServicos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaServicosMouseClicked(evt);
            }
        });
        tabelaServicos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tabelaServicosKeyReleased(evt);
            }
        });
        painelderolagem.setViewportView(tabelaServicos);

        getContentPane().add(painelderolagem, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, 811, 340));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/servicos/telafundo.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnFecharActionPerformed

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        // TODO add your handling code here:
        telaServicosAdicionar.setModal(true);
        telaServicosAdicionar.setLocationRelativeTo(null);
        telaServicosAdicionar.setVisible(true);
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void metodoPagamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_metodoPagamentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_metodoPagamentoActionPerformed

    private void tabelaServicosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaServicosMouseClicked
        try {
            controller.selecionarTabela();
        } catch (SQLException ex) {
            Logger.getLogger(ServicosView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tabelaServicosMouseClicked

    private void tabelaServicosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaServicosKeyReleased
        try {
            controller.selecionarTabela();
        } catch (SQLException ex) {
            Logger.getLogger(ServicosView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tabelaServicosKeyReleased

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        try {
            controller.removerServico();
        } catch (SQLException ex) {
            Logger.getLogger(ServicosView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ServicosView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnRemoverActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        try {
            controller.editarServicos();
        } catch (SQLException ex) {
            Logger.getLogger(ServicosView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ServicosView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            controller.listarServicos();
        } catch (SQLException ex) {
            Logger.getLogger(ServicosView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowOpened

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        desabilitarComponentes();
    }//GEN-LAST:event_formMouseClicked

    private void comboPeriodoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboPeriodoMouseClicked
        comboPeriodo.setEnabled(true);
        campoOutroPeriodo.setText("Outro");
        campoOutroPeriodo.setEnabled(false);
    }//GEN-LAST:event_comboPeriodoMouseClicked

    private void campoOutroPeriodoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_campoOutroPeriodoMouseClicked
        campoOutroPeriodo.setEnabled(true);
        this.esvaziarOutroTipo();
        comboPeriodo.setEnabled(false);        
    }//GEN-LAST:event_campoOutroPeriodoMouseClicked

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
            java.util.logging.Logger.getLogger(ServicosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ServicosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ServicosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServicosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ServicosView dialog = new ServicosView(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnFechar;
    private javax.swing.JButton btnRemover;
    private javax.swing.JTextField campoOutroPeriodo;
    private javax.swing.JComboBox<String> comboPeriodo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JComboBox<String> metodoPagamento;
    private javax.swing.JScrollPane painelderolagem;
    private javax.swing.JTable tabelaServicos;
    // End of variables declaration//GEN-END:variables

    public void exibeMensagem(String mensagem) {
      JOptionPane.showMessageDialog(null, mensagem);
    }
    
    public JComboBox<String> getComboPeriodo() {
        return comboPeriodo;
    }

    public JComboBox<String> getMetodoPagamento() {
        return metodoPagamento;
    }

    public JTable getTabelaServicos() {
        return tabelaServicos;
    }
    
    public void desabilitarComponentes(){
        this.comboPeriodo.setVisible(false);
        this.campoOutroPeriodo.setVisible(false);
        this.metodoPagamento.setVisible(false);
    }
    
    public void habilitarComponentes(){
        this.comboPeriodo.setVisible(true);
        this.campoOutroPeriodo.setVisible(true);
        this.metodoPagamento.setVisible(true);
    }

    public JTextField getCampoOutroPeriodo() {
        return campoOutroPeriodo;
    }
    
    private void setarValores(){
        comboPeriodo.setEnabled(true);
        campoOutroPeriodo.setText("Outro");
        campoOutroPeriodo.setEnabled(false);
    }
    
    private void esvaziarOutroTipo(){
        if(campoOutroPeriodo.getText().equals("Outro")){
            campoOutroPeriodo.setText("");
        }
    }

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
