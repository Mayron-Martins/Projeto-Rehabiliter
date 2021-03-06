/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Paineis;

import Controller.Paineis.GastosDetalhesController;
import Controller.auxiliar.JMoneyField;
import View.FinanceiroPlanodeContra;
import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

/**
 *
 * @author Mayro
 */
public class GastosDetalhes extends javax.swing.JDialog {
    private final GastosDetalhesController controller;
    /**
     * Creates new form GastosDetalhes
     */
    public GastosDetalhes(FinanceiroPlanodeContra planoContra, java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        controller = new GastosDetalhesController(planoContra, this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        campoChaveTransacao = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        campoReferencia = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        comboFormaPagamento = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        comboStatus = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        campoQuantidade = new JMoneyField();
        jLabel8 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        campoValor = new JMoneyField();
        jLabel9 = new javax.swing.JLabel();
        campoData = new com.toedter.calendar.JDateChooser();
        botaoEditar = new javax.swing.JButton();
        botaoConcluir = new javax.swing.JButton();
        botaoSair = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(157, 197, 188));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        campoChaveTransacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoChaveTransacaoActionPerformed(evt);
            }
        });
        jPanel1.add(campoChaveTransacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 380, 130, 30));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(11, 13, 138));
        jLabel10.setText("Chave Transação");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(136, 360, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(11, 13, 138));
        jLabel3.setText("Valor");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(286, 220, -1, -1));

        campoReferencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoReferenciaActionPerformed(evt);
            }
        });
        jPanel1.add(campoReferencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 360, 30));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(11, 13, 138));
        jLabel4.setText("Detalhes");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(11, 13, 138));
        jLabel5.setText("Data Pagamento");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 290, -1, -1));

        comboFormaPagamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dinheiro", "Boleto", "Cartão de Crédito", "Cartão de Débito" }));
        jPanel1.add(comboFormaPagamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 150, 30));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(11, 13, 138));
        jLabel6.setText("Status");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 150, -1, -1));

        comboStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pendente", "Pago" }));
        jPanel1.add(comboStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 170, 150, 30));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(11, 13, 138));
        jLabel7.setText("Referência");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));
        jPanel1.add(campoQuantidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 240, 80, 30));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(11, 13, 138));
        jLabel8.setText("Quantidade");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 220, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 102));
        jLabel2.setText("R$");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(235, 244, -1, -1));
        jPanel1.add(campoValor, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 240, 90, 30));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(11, 13, 138));
        jLabel9.setText("Forma de Pagamento");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, -1));
        jPanel1.add(campoData, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 310, 130, 30));

        botaoEditar.setBackground(new java.awt.Color(0, 204, 204));
        botaoEditar.setText("Editar");
        botaoEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoEditarActionPerformed(evt);
            }
        });
        jPanel1.add(botaoEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 580, 140, 50));

        botaoConcluir.setBackground(new java.awt.Color(0, 204, 0));
        botaoConcluir.setText("Concluir");
        botaoConcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoConcluirActionPerformed(evt);
            }
        });
        jPanel1.add(botaoConcluir, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 580, 140, 50));

        botaoSair.setBackground(new java.awt.Color(255, 51, 51));
        botaoSair.setText("X");
        botaoSair.setMaximumSize(new java.awt.Dimension(40, 25));
        botaoSair.setMinimumSize(new java.awt.Dimension(40, 25));
        botaoSair.setPreferredSize(new java.awt.Dimension(40, 25));
        botaoSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSairActionPerformed(evt);
            }
        });
        jPanel1.add(botaoSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 0, 40, 25));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 700));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void campoReferenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoReferenciaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoReferenciaActionPerformed

    private void botaoEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoEditarActionPerformed
        trocaBotoes(true);
    }//GEN-LAST:event_botaoEditarActionPerformed

    private void botaoConcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoConcluirActionPerformed
        controller.editarGasto();
    }//GEN-LAST:event_botaoConcluirActionPerformed

    private void botaoSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSairActionPerformed
        controller.sairTela();
    }//GEN-LAST:event_botaoSairActionPerformed

    private void campoChaveTransacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoChaveTransacaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoChaveTransacaoActionPerformed

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
            java.util.logging.Logger.getLogger(GastosDetalhes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GastosDetalhes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GastosDetalhes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GastosDetalhes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                javax.swing.JFrame frame = new javax.swing.JFrame();
                GastosDetalhes dialog = new GastosDetalhes(new FinanceiroPlanodeContra(frame, true), frame, true);
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
    private javax.swing.JButton botaoConcluir;
    private javax.swing.JButton botaoEditar;
    private javax.swing.JButton botaoSair;
    private javax.swing.JTextField campoChaveTransacao;
    private com.toedter.calendar.JDateChooser campoData;
    private javax.swing.JFormattedTextField campoQuantidade;
    private javax.swing.JTextField campoReferencia;
    private javax.swing.JFormattedTextField campoValor;
    private javax.swing.JComboBox<String> comboFormaPagamento;
    private javax.swing.JComboBox<String> comboStatus;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
    
    public JDateChooser getCampoData() {
        return campoData;
    }

    public JFormattedTextField getCampoQuantidade() {
        return campoQuantidade;
    }

    public JTextField getCampoReferencia() {
        return campoReferencia;
    }

    public JFormattedTextField getCampoValor() {
        return campoValor;
    }

    public JComboBox<String> getComboFormaPagamento() {
        return comboFormaPagamento;
    }

    public JComboBox<String> getComboStatus() {
        return comboStatus;
    }

    public JTextField getCampoChaveTransacao() {
        return campoChaveTransacao;
    }
    
    
    public void trocaBotoes(boolean editar){
        botaoEditar.setVisible(!editar);
        botaoConcluir.setVisible(editar);
        enableCampos(editar);
    }
    
    private void enableCampos(boolean enable){
        campoReferencia.setEditable(enable);
        comboFormaPagamento.setEnabled(enable);
        comboStatus.setEnabled(enable);
        campoQuantidade.setEditable(enable);
        campoValor.setEditable(enable);
        campoData.setEnabled(enable);
        campoChaveTransacao.setEnabled(false);
    }
    
}
