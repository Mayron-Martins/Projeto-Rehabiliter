/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.adicionais.AdicionarGastosController;
import Controller.auxiliar.JMoneyField;
import com.toedter.calendar.JDateChooser;
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
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/**
 *
 * @author 55989
 */
public class FinanceiroPlanodeContraAdc extends javax.swing.JFrame {
    private final AdicionarGastosController controller;

    /**
     * Creates new form FinanceiroPlanodeContraAdc
     */
    public FinanceiroPlanodeContraAdc() {
        initComponents();
        controller = new AdicionarGastosController(this);
        botaoConfirmar.setBackground(new Color(0,0,0,0));
        botaoFechar.setBackground(new Color(0,0,0,0));
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
        botaoConfirmar = new javax.swing.JButton();
        campoReferencia = new javax.swing.JTextField();
        campoQuantidade = new JMoneyField();
        campoFormaPagamento = new javax.swing.JComboBox<>();
        campoValor = new JMoneyField();
        campoData = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        botaoFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaofechar.png"))); // NOI18N
        botaoFechar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoHoverfechar.png"))); // NOI18N
        botaoFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoFecharActionPerformed(evt);
            }
        });
        getContentPane().add(botaoFechar, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 40, 220, 50));

        botaoConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/turmas/adicionarturma/confirmar.png"))); // NOI18N
        botaoConfirmar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/turmas/adicionarturma/confirmarhover.png"))); // NOI18N
        botaoConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoConfirmarActionPerformed(evt);
            }
        });
        getContentPane().add(botaoConfirmar, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 360, 330, 50));
        getContentPane().add(campoReferencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(215, 200, 150, 30));
        getContentPane().add(campoQuantidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(473, 200, 60, 30));

        campoFormaPagamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[Nenhum]", "Dinheiro", "Boleto", "Cartão de Crédito", "Cartão de Débito" }));
        getContentPane().add(campoFormaPagamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 280, 130, 30));
        getContentPane().add(campoValor, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 280, 90, 30));
        getContentPane().add(campoData, new org.netbeans.lib.awtextra.AbsoluteConstraints(555, 280, 130, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/financeiro/planodecontradc.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFecharActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_botaoFecharActionPerformed

    private void botaoConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoConfirmarActionPerformed
        try {
            controller.adicionarEntrada();
        } catch (SQLException ex) {
            Logger.getLogger(FinanceiroPlanodeContraAdc.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FinanceiroPlanodeContraAdc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botaoConfirmarActionPerformed

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
            java.util.logging.Logger.getLogger(FinanceiroPlanodeContraAdc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FinanceiroPlanodeContraAdc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FinanceiroPlanodeContraAdc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FinanceiroPlanodeContraAdc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FinanceiroPlanodeContraAdc().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoConfirmar;
    private javax.swing.JButton botaoFechar;
    private com.toedter.calendar.JDateChooser campoData;
    private javax.swing.JComboBox<String> campoFormaPagamento;
    private javax.swing.JFormattedTextField campoQuantidade;
    private javax.swing.JTextField campoReferencia;
    private javax.swing.JFormattedTextField campoValor;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
    public void exibeMensagem(String mensagem) {
      JOptionPane.showMessageDialog(null, mensagem);
    }

    public JDateChooser getCampoData() {
        return campoData;
    }

    public JComboBox<String> getCampoFormaPagamento() {
        return campoFormaPagamento;
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
