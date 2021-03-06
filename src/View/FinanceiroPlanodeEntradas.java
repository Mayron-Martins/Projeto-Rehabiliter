/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.PlanoEntradasController;
import Controller.auxiliar.JMoneyField;
import View.Paineis.PainelAjuda;
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
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;

/**
 *
 * @author 55989
 */
public class FinanceiroPlanodeEntradas extends javax.swing.JDialog {
    private final java.awt.Frame parent;
    private final PlanoEntradasController controller;
    private final FinanceiroPlanodeEntradasAdc telaAdAEntradas;
    private final JComboBox comboPagamentoEntrada = new JComboBox();
    private final PainelAjuda painelAjuda;

    /**
     * Creates new form FinanceiroPlanodeEntradas
     * @param parent
     * @param modal
     */
    public FinanceiroPlanodeEntradas(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        this.parent = parent;
        telaAdAEntradas= new FinanceiroPlanodeEntradasAdc(parent, false);
         painelAjuda = new PainelAjuda(parent, false, this.getLocation().x+this.getSize().width+4, this.getLocation().y);
        
        controller = new PlanoEntradasController(this);
        btnAdicionar.setBackground(new Color(0,0,0,0));
        btnAplicar.setBackground(new Color(0,0,0,0));
        btnContasRecebidas.setBackground(new Color(0,0,0,0));
        botaoVDetalhada.setBackground(new Color(0,0,0,0));
        btnMensalidadesAReceber.setBackground(new Color(0,0,0,0));
        btnEditar.setBackground(new Color(0,0,0,0));
        btnRemover.setBackground(new Color(0,0,0,0));
        botaoVResumida.setBackground(new Color(0,0,0,0));
        botaoFechar.setBackground(new Color(0,0,0,0));
        
        this.setarComponentes();
        this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/imagens/rehabi.png")).getImage());
        this.teclasDeAtalho();
        this.setarComponentesTabela();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        campoDataEspecífica = new com.toedter.calendar.JDateChooser();
        botaoFechar = new javax.swing.JButton();
        btnAdicionar = new javax.swing.JButton();
        btnRemover = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        botaoVResumida = new javax.swing.JButton();
        botaoVDetalhada = new javax.swing.JButton();
        btnAplicar = new javax.swing.JButton();
        comboPagamento = new javax.swing.JComboBox<>();
        comboTipos = new javax.swing.JComboBox<>();
        comboPeriodo = new javax.swing.JComboBox<>();
        painelVendas = new javax.swing.JScrollPane();
        tabelaVendas = new javax.swing.JTable();
        painelPlanos = new javax.swing.JScrollPane();
        tabelasPlanos = new javax.swing.JTable();
        painelEntradas = new javax.swing.JScrollPane();
        tabelaEntradas = new javax.swing.JTable();
        btnMensalidadesAReceber = new javax.swing.JButton();
        btnContasRecebidas = new javax.swing.JButton();
        botaoAjuda = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(campoDataEspecífica, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 120, -1));

        botaoFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaofechar.png"))); // NOI18N
        botaoFechar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoFechar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoHoverfechar.png"))); // NOI18N
        botaoFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoFecharActionPerformed(evt);
            }
        });
        getContentPane().add(botaoFechar, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 40, 220, 50));

        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/financeiro/btnAdicionar.png"))); // NOI18N
        btnAdicionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdicionar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/financeiro/btnAdicionarHover.png"))); // NOI18N
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });
        getContentPane().add(btnAdicionar, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 180, 146, 34));

        btnRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/financeiro/btnRemover.png"))); // NOI18N
        btnRemover.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRemover.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/financeiro/btnRemoverHover.png"))); // NOI18N
        btnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverActionPerformed(evt);
            }
        });
        getContentPane().add(btnRemover, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 180, 146, 34));

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/financeiro/btnEditar.png"))); // NOI18N
        btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/financeiro/btnEditarHover.png"))); // NOI18N
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        getContentPane().add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 180, 146, 34));

        botaoVResumida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/financeiro/btnResumido.png"))); // NOI18N
        botaoVResumida.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoVResumida.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/financeiro/btnResumidoHover.png"))); // NOI18N
        botaoVResumida.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botaoVResumidaMouseClicked(evt);
            }
        });
        getContentPane().add(botaoVResumida, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 180, 146, 34));

        botaoVDetalhada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/financeiro/btnDetalhada.png"))); // NOI18N
        botaoVDetalhada.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoVDetalhada.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/financeiro/btnDetalhadaHover.png"))); // NOI18N
        botaoVDetalhada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botaoVDetalhadaMouseClicked(evt);
            }
        });
        getContentPane().add(botaoVDetalhada, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 180, 146, 34));

        btnAplicar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/financeiro/btnAplicar.png"))); // NOI18N
        btnAplicar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAplicar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/financeiro/btnAplicarHover.png"))); // NOI18N
        btnAplicar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAplicarActionPerformed(evt);
            }
        });
        getContentPane().add(btnAplicar, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 240, 146, 35));

        comboPagamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[Nenhum]", "Dinheiro", "Boleto", "Cartão de Crédito", "Cartão de Débito" }));
        getContentPane().add(comboPagamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 250, 130, -1));

        comboTipos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vendas", "Planos", "Entradas" }));
        comboTipos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTiposActionPerformed(evt);
            }
        });
        getContentPane().add(comboTipos, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 250, 120, -1));

        comboPeriodo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[Nenhum]", "Diário", "Semanal", "Mensal", "Semestral", "Anual" }));
        comboPeriodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboPeriodoActionPerformed(evt);
            }
        });
        getContentPane().add(comboPeriodo, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 250, 140, -1));

        tabelaVendas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tabelaVendas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CodBanco", "CodCliente", "Produtos", "Quantidade", "Total", "Forma Pagamento", "Parc.", "Data"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaVendas.setFocusable(false);
        tabelaVendas.setGridColor(new java.awt.Color(255, 255, 255));
        tabelaVendas.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tabelaVendas.setRowHeight(25);
        tabelaVendas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelaVendas.setShowVerticalLines(false);
        tabelaVendas.getTableHeader().setReorderingAllowed(false);
        tabelaVendas.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                tabelaVendasComponentHidden(evt);
            }
        });
        painelVendas.setViewportView(tabelaVendas);

        getContentPane().add(painelVendas, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, 811, 340));

        tabelasPlanos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tabelasPlanos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "chavePlano", "Aluno", "Turma", "Valor", "Parc.", "Situação", "Último Pagamento"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelasPlanos.setFocusable(false);
        tabelasPlanos.setGridColor(new java.awt.Color(255, 255, 255));
        tabelasPlanos.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tabelasPlanos.setRowHeight(25);
        tabelasPlanos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelasPlanos.setShowVerticalLines(false);
        tabelasPlanos.getTableHeader().setReorderingAllowed(false);
        tabelasPlanos.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                tabelasPlanosComponentHidden(evt);
            }
        });
        painelPlanos.setViewportView(tabelasPlanos);

        getContentPane().add(painelPlanos, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, 811, 340));

        tabelaEntradas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tabelaEntradas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CodBanco", "Referência", "Quantidade", "Forma de Pagamento", "Valor", "Data"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaEntradas.setFocusable(false);
        tabelaEntradas.setGridColor(new java.awt.Color(255, 255, 255));
        tabelaEntradas.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tabelaEntradas.setRowHeight(25);
        tabelaEntradas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelaEntradas.setShowVerticalLines(false);
        tabelaEntradas.getTableHeader().setReorderingAllowed(false);
        tabelaEntradas.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                tabelaEntradasComponentHidden(evt);
            }
        });
        painelEntradas.setViewportView(tabelaEntradas);

        getContentPane().add(painelEntradas, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, 811, 340));

        btnMensalidadesAReceber.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/financeiro/btnMensalidadesAReceber.png"))); // NOI18N
        btnMensalidadesAReceber.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMensalidadesAReceber.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/financeiro/btnMensalidadesAReceberHover.png"))); // NOI18N
        btnMensalidadesAReceber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMensalidadesAReceberActionPerformed(evt);
            }
        });
        getContentPane().add(btnMensalidadesAReceber, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 290, 287, 35));

        btnContasRecebidas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/financeiro/BTNcontasrecebidas.png"))); // NOI18N
        btnContasRecebidas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnContasRecebidas.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/financeiro/BTNcontasrecebidasHover.png"))); // NOI18N
        btnContasRecebidas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContasRecebidasActionPerformed(evt);
            }
        });
        getContentPane().add(btnContasRecebidas, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 290, 211, 35));

        botaoAjuda.setBackground(new java.awt.Color(255, 255, 255));
        botaoAjuda.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        botaoAjuda.setForeground(new java.awt.Color(255, 255, 255));
        botaoAjuda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/imagensparaseremtrocadas/btnDuvidas.png"))); // NOI18N
        botaoAjuda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoAjuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoAjudaActionPerformed(evt);
            }
        });
        getContentPane().add(botaoAjuda, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 30, 30));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(11, 13, 138));
        jLabel3.setText("Pagamento");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 230, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(11, 13, 138));
        jLabel4.setText("VERSÕES");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 160, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(11, 13, 138));
        jLabel5.setText("Período");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 230, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(11, 13, 138));
        jLabel6.setText("Tipos");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 230, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/imagensparaseremtrocadas/planodeentradasFinal.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFecharActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_botaoFecharActionPerformed

    private void tabelaVendasComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tabelaVendasComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelaVendasComponentHidden

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        // TODO add your handling code here:
        telaAdAEntradas.setModal(true);
        telaAdAEntradas.setLocationRelativeTo(null);
        telaAdAEntradas.setVisible(true);
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void comboPeriodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPeriodoActionPerformed
        if(comboPeriodo.getSelectedIndex()>0){
            comboTipos.setSelectedIndex(0);
            comboTipos.setEnabled(true);
            campoDataEspecífica.setDate(null);
            campoDataEspecífica.setEnabled(false);
        }
        else{
            campoDataEspecífica.setDate(null);
            campoDataEspecífica.setEnabled(true);
            comboTipos.setSelectedIndex(0);
            comboTipos.setEnabled(true);
            comboPagamento.setSelectedIndex(0);
            comboPagamento.setEnabled(true);
        }
    }//GEN-LAST:event_comboPeriodoActionPerformed

    private void comboTiposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTiposActionPerformed
        comboPagamento.setSelectedIndex(0);
        comboPagamento.setEnabled(true);
    }//GEN-LAST:event_comboTiposActionPerformed

    private void botaoVResumidaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botaoVResumidaMouseClicked
        botaoVResumida.setEnabled(true);
        botaoVDetalhada.setEnabled(false);
    }//GEN-LAST:event_botaoVResumidaMouseClicked

    private void botaoVDetalhadaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botaoVDetalhadaMouseClicked
        botaoVDetalhada.setEnabled(true);
        botaoVResumida.setEnabled(false);
    }//GEN-LAST:event_botaoVDetalhadaMouseClicked

    private void tabelasPlanosComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tabelasPlanosComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelasPlanosComponentHidden

    private void tabelaEntradasComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tabelaEntradasComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelaEntradasComponentHidden

    private void btnAplicarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAplicarActionPerformed
        controller.setarTabelas();
    }//GEN-LAST:event_btnAplicarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        controller.editarEntradas();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        controller.removerEntradas();
    }//GEN-LAST:event_btnRemoverActionPerformed

    private void btnContasRecebidasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContasRecebidasActionPerformed
        controller.setarTabelaPlanosPagos();
    }//GEN-LAST:event_btnContasRecebidasActionPerformed

    private void btnMensalidadesAReceberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMensalidadesAReceberActionPerformed
        controller.setarTabelaPlanosPendentes();
    }//GEN-LAST:event_btnMensalidadesAReceberActionPerformed

    private void botaoAjudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoAjudaActionPerformed
        controller.ajuda();
    }//GEN-LAST:event_botaoAjudaActionPerformed

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
            java.util.logging.Logger.getLogger(FinanceiroPlanodeEntradas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FinanceiroPlanodeEntradas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FinanceiroPlanodeEntradas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FinanceiroPlanodeEntradas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FinanceiroPlanodeEntradas dialog = new FinanceiroPlanodeEntradas(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton botaoAjuda;
    private javax.swing.JButton botaoFechar;
    private javax.swing.JButton botaoVDetalhada;
    private javax.swing.JButton botaoVResumida;
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnAplicar;
    private javax.swing.JButton btnContasRecebidas;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnMensalidadesAReceber;
    private javax.swing.JButton btnRemover;
    private com.toedter.calendar.JDateChooser campoDataEspecífica;
    private javax.swing.JComboBox<String> comboPagamento;
    private javax.swing.JComboBox<String> comboPeriodo;
    private javax.swing.JComboBox<String> comboTipos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane painelEntradas;
    private javax.swing.JScrollPane painelPlanos;
    private javax.swing.JScrollPane painelVendas;
    private javax.swing.JTable tabelaEntradas;
    private javax.swing.JTable tabelaVendas;
    private javax.swing.JTable tabelasPlanos;
    // End of variables declaration//GEN-END:variables
    
    public void exibeMensagem(String mensagem) {
      JOptionPane.showMessageDialog(null, mensagem);
    }
    
    private void setarComponentes(){
        //combos
        comboTipos.setSelectedIndex(0);
        comboTipos.setEnabled(false);
        comboPagamento.setSelectedIndex(0);
        comboPagamento.setEnabled(false);
        
        //botões
        botaoVResumida.setEnabled(true);
        botaoVDetalhada.setEnabled(false);
        
        //tabelas
        painelVendas.setVisible(true);
        painelPlanos.setVisible(false);
        painelEntradas.setVisible(false);
        
        campoDataEspecífica.setEnabled(false);
    }

    public JScrollPane getPainelEntradas() {
        return painelEntradas;
    }

    public JScrollPane getPainelPlanos() {
        return painelPlanos;
    }

    public JScrollPane getPainelVendas() {
        return painelVendas;
    }

    public JTable getTabelaEntradas() {
        return tabelaEntradas;
    }

    public JTable getTabelaVendas() {
        return tabelaVendas;
    }

    public JTable getTabelasPlanos() {
        return tabelasPlanos;
    }

    public JComboBox<String> getComboPagamento() {
        return comboPagamento;
    }

    public JComboBox<String> getComboPeriodo() {
        return comboPeriodo;
    }

    public JComboBox<String> getComboTipos() {
        return comboTipos;
    }

    public JComboBox getComboPagamentoEntrada() {
        return comboPagamentoEntrada;
    }

    public JButton getBotaoVDetalhada() {
        return botaoVDetalhada;
    }

    public JButton getBotaoVResumida() {
        return botaoVResumida;
    }

    public JDateChooser getCampoDataEspecífica() {
        return campoDataEspecífica;
    }

    public Frame getParent() {
        return parent;
    }

    public PainelAjuda getPainelAjuda() {
        return painelAjuda;
    }
    
    
    
    private void setarComponentesTabela(){
        
        comboPagamentoEntrada.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[Nenhum]", "Dinheiro", "Boleto", "Cartão de Crédito", "Cartão de Débito" }));
        
        tabelaEntradas.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(new JMoneyField()));
        tabelaEntradas.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(comboPagamentoEntrada));
        tabelaEntradas.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(new JMoneyField()));
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
        
        //Adicionar Novo
        meurootpane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ctrl N"), "NOVO");
        meurootpane.getRootPane().getActionMap().put("NOVO", new AbstractAction("NOVO") {
            @Override
            public void actionPerformed(ActionEvent e) {
                telaAdAEntradas.setModal(true);
                telaAdAEntradas.setLocationRelativeTo(null);
                telaAdAEntradas.setVisible(true);
            }
        });
    }
}
