/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Paineis;

import Controller.AlunosController;
import Controller.auxiliar.JMoneyField;
import View.AlunosView;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JDayChooser;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author Mayro
 */
public class AlunosConfigAdicionais extends javax.swing.JDialog {
    private final AlunosView view;
    private final AlunosController controller;
    /**
     * Creates new form NewJDialog
     * @param parent
     * @param modal
     * @param view
     */
    public AlunosConfigAdicionais(java.awt.Frame parent, boolean modal, AlunosView view) {
        super(parent, modal);
        initComponents();
        
        this.view = view;
        this.controller = new AlunosController(view, this, view.getPainelDescricao());
        botaoFecharConf.setBackground(new Color(0,0,0,0));
        botaoSetarVencimento.setBackground(new Color(0,0,0,0));
        this.setLocation(view.getLocation().x-240, view.getLocation().y);
        this.desativarComponentes();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        botaoFecharConf = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        campoDataCadastro = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        campoDataPagamento = new com.toedter.calendar.JDateChooser();
        campoDataVencimento = new com.toedter.calendar.JDateChooser();
        botaoSetarVencimento = new javax.swing.JButton();
        campoDataFimPlano = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaPlanos = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelaPagamentos = new javax.swing.JTable();
        painelCadTurma = new javax.swing.JPanel();
        comboTurmas = new javax.swing.JComboBox<>();
        comboServicos = new javax.swing.JComboBox<>();
        campoRenovAutomatica = new javax.swing.JCheckBox();
        campoValorMensal = new JMoneyField();
        campoValorTotal = new JMoneyField();
        jLabel7 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        botaoCancelar = new javax.swing.JButton();
        botaoAplicar = new javax.swing.JButton();
        campoNovoPlano = new javax.swing.JCheckBox();
        comboDiaVencimento = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        botaoReativarPlano = new javax.swing.JButton();
        botaoCadNvo = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        campoDataRenovacao = new com.toedter.calendar.JDateChooser();
        jLabel15 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setLocation(new java.awt.Point(0, 0));
        setMinimumSize(new java.awt.Dimension(237, 701));
        setUndecorated(true);
        setSize(new java.awt.Dimension(237, 701));

        jScrollPane3.setBackground(new java.awt.Color(116, 174, 159));
        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setHorizontalScrollBar(null);
        jScrollPane3.setMinimumSize(new java.awt.Dimension(237, 701));
        jScrollPane3.setPreferredSize(new java.awt.Dimension(237, 701));

        jPanel1.setBackground(new java.awt.Color(116, 174, 159));
        jPanel1.setMinimumSize(new java.awt.Dimension(237, 701));
        jPanel1.setPreferredSize(new java.awt.Dimension(237, 1060));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        botaoFecharConf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/imagensparaseremtrocadas/btnFecharPeq.png"))); // NOI18N
        botaoFecharConf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoFecharConfActionPerformed(evt);
            }
        });
        jPanel1.add(botaoFecharConf, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 30));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("HISTÓRICO DE PLANOS");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 430, -1, -1));
        jPanel1.add(campoDataCadastro, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 140, 30));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Data Renovação");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 196, -1, -1));
        jPanel1.add(campoDataPagamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 140, 30));

        campoDataVencimento.setEnabled(false);
        jPanel1.add(campoDataVencimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 140, 30));

        botaoSetarVencimento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/imagensparaseremtrocadas/btnSet.png"))); // NOI18N
        botaoSetarVencimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSetarVencimentoActionPerformed(evt);
            }
        });
        jPanel1.add(botaoSetarVencimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 140, 30));

        campoDataFimPlano.setEnabled(false);
        jPanel1.add(campoDataFimPlano, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 140, 30));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Data de Fim do Plano");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 256, -1, -1));

        tabelaPlanos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Chave", "Turma", "Serviço", "Valor", "Situação"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaPlanos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tabelaPlanos.setMinimumSize(new java.awt.Dimension(300, 64));
        tabelaPlanos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelaPlanos.getTableHeader().setReorderingAllowed(false);
        tabelaPlanos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaPlanosMouseClicked(evt);
            }
        });
        tabelaPlanos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tabelaPlanosKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaPlanos);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 450, 210, 110));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Data Cadastro");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 76, -1, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("CONFIGURAÇÕES ADICIONAIS");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("HISTÓRICO DE PAGAMENTOS");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 570, -1, -1));

        tabelaPagamentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CodEntrada", "Data", "Valor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        tabelaPagamentos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tabelaPagamentos.setMinimumSize(new java.awt.Dimension(300, 64));
        tabelaPagamentos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelaPagamentos.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tabelaPagamentos);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 590, 210, 110));

        painelCadTurma.setBackground(new java.awt.Color(0, 102, 102));
        painelCadTurma.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        comboTurmas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[Nenhuma]" }));
        painelCadTurma.add(comboTurmas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 190, 40));

        comboServicos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[Nenhum]" }));
        comboServicos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboServicosActionPerformed(evt);
            }
        });
        painelCadTurma.add(comboServicos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 190, 40));

        campoRenovAutomatica.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        campoRenovAutomatica.setForeground(new java.awt.Color(255, 255, 255));
        campoRenovAutomatica.setText("Renovação Automática");
        campoRenovAutomatica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoRenovAutomaticaActionPerformed(evt);
            }
        });
        painelCadTurma.add(campoRenovAutomatica, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));
        painelCadTurma.add(campoValorMensal, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 180, 90, 30));
        painelCadTurma.add(campoValorTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 90, 30));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Valor Mensal");
        painelCadTurma.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 166, -1, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Dia Vencimento");
        painelCadTurma.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(66, 224, -1, -1));

        botaoCancelar.setText("Cancelar");
        botaoCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoCancelarActionPerformed(evt);
            }
        });
        painelCadTurma.add(botaoCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, -1, -1));

        botaoAplicar.setText("Aplicar");
        botaoAplicar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoAplicarActionPerformed(evt);
            }
        });
        painelCadTurma.add(botaoAplicar, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 290, 70, -1));

        campoNovoPlano.setText("Novo Plano");
        campoNovoPlano.setEnabled(false);
        painelCadTurma.add(campoNovoPlano, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        painelCadTurma.add(comboDiaVencimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 240, 60, 30));
        comboDiaVencimento.removeAllItems();
        for(int dia=1; dia<=30;dia++){
            comboDiaVencimento.addItem(dia+"");
        }

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Valor Total");
        painelCadTurma.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 166, -1, -1));

        jPanel1.add(painelCadTurma, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 710, 210, 330));

        botaoReativarPlano.setText("Reativar Plano");
        botaoReativarPlano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoReativarPlanoActionPerformed(evt);
            }
        });
        jPanel1.add(botaoReativarPlano, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 710, 110, -1));

        botaoCadNvo.setText("Cadastrar Novo");
        botaoCadNvo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoCadNvoActionPerformed(evt);
            }
        });
        jPanel1.add(botaoCadNvo, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 750, 110, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Data Vencimento");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 316, -1, -1));

        campoDataRenovacao.setEnabled(false);
        jPanel1.add(campoDataRenovacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 140, 30));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Data Último Pagamento");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 136, -1, -1));

        jScrollPane3.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 701, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoSetarVencimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSetarVencimentoActionPerformed
        controller.setarDataVencimento();
    }//GEN-LAST:event_botaoSetarVencimentoActionPerformed

    private void botaoFecharConfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFecharConfActionPerformed
        controller.setarDataPagCadastro();
        this.setVisible(false);
    }//GEN-LAST:event_botaoFecharConfActionPerformed

    private void botaoCadNvoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoCadNvoActionPerformed
        controller.novoPlano();
    }//GEN-LAST:event_botaoCadNvoActionPerformed

    private void tabelaPlanosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaPlanosMouseClicked
        controller.selecionarTabelaPlanosAdicionais();
    }//GEN-LAST:event_tabelaPlanosMouseClicked

    private void tabelaPlanosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaPlanosKeyReleased
        controller.selecionarTabelaPlanosAdicionais();
    }//GEN-LAST:event_tabelaPlanosKeyReleased

    private void botaoCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoCancelarActionPerformed
        painelCadTurma.setVisible(false);
        controller.cancelarReativacaoPlano();
    }//GEN-LAST:event_botaoCancelarActionPerformed

    private void botaoReativarPlanoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoReativarPlanoActionPerformed
        controller.reativarPlano();
    }//GEN-LAST:event_botaoReativarPlanoActionPerformed

    private void comboServicosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboServicosActionPerformed
        controller.selecionarComboServicosPlanosAdicional();
    }//GEN-LAST:event_comboServicosActionPerformed

    private void campoRenovAutomaticaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoRenovAutomaticaActionPerformed
        controller.selecionarComboServicosPlanosAdicional();
    }//GEN-LAST:event_campoRenovAutomaticaActionPerformed

    private void botaoAplicarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoAplicarActionPerformed
        controller.reativarAluno();
    }//GEN-LAST:event_botaoAplicarActionPerformed

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
            java.util.logging.Logger.getLogger(AlunosConfigAdicionais.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AlunosConfigAdicionais.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AlunosConfigAdicionais.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AlunosConfigAdicionais.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AlunosConfigAdicionais dialog = new AlunosConfigAdicionais(new javax.swing.JFrame(), true, new AlunosView(new javax.swing.JFrame(), true));
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
    private javax.swing.JButton botaoAplicar;
    private javax.swing.JButton botaoCadNvo;
    private javax.swing.JButton botaoCancelar;
    private javax.swing.JButton botaoFecharConf;
    private javax.swing.JButton botaoReativarPlano;
    private javax.swing.JButton botaoSetarVencimento;
    private com.toedter.calendar.JDateChooser campoDataCadastro;
    private com.toedter.calendar.JDateChooser campoDataFimPlano;
    private com.toedter.calendar.JDateChooser campoDataPagamento;
    private com.toedter.calendar.JDateChooser campoDataRenovacao;
    private com.toedter.calendar.JDateChooser campoDataVencimento;
    private javax.swing.JCheckBox campoNovoPlano;
    private javax.swing.JCheckBox campoRenovAutomatica;
    private javax.swing.JTextField campoValorMensal;
    private javax.swing.JTextField campoValorTotal;
    private javax.swing.JComboBox<String> comboDiaVencimento;
    private javax.swing.JComboBox<String> comboServicos;
    private javax.swing.JComboBox<String> comboTurmas;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel painelCadTurma;
    private javax.swing.JTable tabelaPagamentos;
    private javax.swing.JTable tabelaPlanos;
    // End of variables declaration//GEN-END:variables

    private void desativarComponentes(){
        botaoReativarPlano.setVisible(false);
        botaoCadNvo.setVisible(false);
        painelCadTurma.setVisible(false);
        
    }

    public void limparCampos(){
        campoDataCadastro.setDate(null);
        campoDataPagamento.setDate(null);
        campoDataVencimento.setDate(null);
        campoDataFimPlano.setDate(null);
        comboServicos.setSelectedIndex(0);
        comboTurmas.setSelectedIndex(0);
        campoValorTotal.setText("");
        campoValorMensal.setText("");
        campoRenovAutomatica.setSelected(false);
    }
    
    public JDateChooser getCampoDataCadastro() {
        return campoDataCadastro;
    }

    public JDateChooser getCampoDataFimPlano() {
        return campoDataFimPlano;
    }

    public JDateChooser getCampoDataPagamento() {
        return campoDataPagamento;
    }

    public JDateChooser getCampoDataVencimento() {
        return campoDataVencimento;
    }

    public JCheckBox getCampoRenovAutomatica() {
        return campoRenovAutomatica;
    }

    public JTextField getCampoValorMensal() {
        return campoValorMensal;
    }

    public JTextField getCampoValorTotal() {
        return campoValorTotal;
    }

    public JComboBox<String> getComboServicos() {
        return comboServicos;
    }

    public JComboBox<String> getComboTurmas() {
        return comboTurmas;
    }

    public JPanel getPainelCadTurma() {
        return painelCadTurma;
    }

    public JTable getTabelaPagamentos() {
        return tabelaPagamentos;
    }

    public JTable getTabelaPlanos() {
        return tabelaPlanos;
    }

    public JButton getBotaoCadNvo() {
        return botaoCadNvo;
    }

    public JButton getBotaoReativarPlano() {
        return botaoReativarPlano;
    }

    public JCheckBox getCampoNovoPlano() {
        return campoNovoPlano;
    }

    public JComboBox<String> getComboDiaVencimento() {
        return comboDiaVencimento;
    }

    public JDateChooser getCampoDataRenovacao() {
        return campoDataRenovacao;
    }
}
