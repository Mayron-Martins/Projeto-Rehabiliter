/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.adicionais.AdicionarServicosController;
import Controller.auxiliar.FormatacaodeCamposRestritos;
import Controller.auxiliar.JMoneyField;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
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
public class ServicosAdicionar extends javax.swing.JDialog {
    private final java.awt.Frame parent;
    private final AdicionarServicosController controller;

    /**
     * Creates new form ServicosAdicionar
     * @param parent
     * @param modal
     */
    public ServicosAdicionar(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        this.parent = parent;
        
        controller = new AdicionarServicosController(this);
        btnFechar.setBackground(new Color(0,0,0,0));
        botaoConfirmar.setBackground(new Color(0,0,0,0));
        this.setarValores();
        controller.preencherComboPeriodo();
        
        this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/imagens/rehabi.png")).getImage());
        this.teclasDeAtalho();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        campoOutroTipo = new javax.swing.JTextField();
        comboPeriodo = new javax.swing.JComboBox<>();
        campoDias = new FormatacaodeCamposRestritos();
        btnFechar = new javax.swing.JButton();
        comboDias = new javax.swing.JComboBox<>();
        nomeServico = new javax.swing.JTextField();
        valorDinheiro = new JMoneyField();
        metodoPagamento = new javax.swing.JComboBox<>();
        botaoConfirmar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        campoOutroTipo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                campoOutroTipoMouseClicked(evt);
            }
        });
        getContentPane().add(campoOutroTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 202, 170, 30));

        comboPeriodo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[Nenhum]", "Diária", "Semanal", "Mensal", "Trimestral", "Quadrimestral", "Semestral", "Anual" }));
        comboPeriodo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comboPeriodoMouseClicked(evt);
            }
        });
        getContentPane().add(comboPeriodo, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 202, 170, 30));
        getContentPane().add(campoDias, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 240, 110, 30));

        btnFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaofechar.png"))); // NOI18N
        btnFechar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFechar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoHoverfechar.png"))); // NOI18N
        btnFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharActionPerformed(evt);
            }
        });
        getContentPane().add(btnFechar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 230, 60));

        comboDias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "D", "M", "A" }));
        getContentPane().add(comboDias, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 240, 50, 30));

        nomeServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nomeServicoActionPerformed(evt);
            }
        });
        getContentPane().add(nomeServico, new org.netbeans.lib.awtextra.AbsoluteConstraints(213, 202, 170, 30));

        valorDinheiro.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(valorDinheiro, new org.netbeans.lib.awtextra.AbsoluteConstraints(592, 320, 110, 40));

        metodoPagamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[Nenhuma]", "Dinheiro", "Boleto", "Cartão de Crédito", "Cartão de Débito" }));
        metodoPagamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                metodoPagamentoActionPerformed(evt);
            }
        });
        getContentPane().add(metodoPagamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 320, 250, 40));

        botaoConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/turmas/adicionarturma/confirmar.png"))); // NOI18N
        botaoConfirmar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoConfirmar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/turmas/adicionarturma/confirmarhover.png"))); // NOI18N
        botaoConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoConfirmarActionPerformed(evt);
            }
        });
        getContentPane().add(botaoConfirmar, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 380, 330, 50));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 102));
        jLabel2.setText("R$");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(568, 330, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/servicos/telafundoadc.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed
        controller.limparCampos();
        this.dispose();
    }//GEN-LAST:event_btnFecharActionPerformed

    private void nomeServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nomeServicoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nomeServicoActionPerformed

    private void metodoPagamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_metodoPagamentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_metodoPagamentoActionPerformed

    private void botaoConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoConfirmarActionPerformed
        controller.adicionarServico();
    }//GEN-LAST:event_botaoConfirmarActionPerformed

    private void comboPeriodoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboPeriodoMouseClicked
        comboPeriodo.setEnabled(true);
        campoOutroTipo.setText("Outro");
        campoOutroTipo.setEnabled(false);
        
        campoDias.setEnabled(false);
        comboDias.setEnabled(false);
    }//GEN-LAST:event_comboPeriodoMouseClicked

    private void campoOutroTipoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_campoOutroTipoMouseClicked
        campoOutroTipo.setEnabled(true);
        this.esvaziarOutroTipo();
        campoDias.setEnabled(true);
        comboDias.setEnabled(true);
        comboPeriodo.setEnabled(false);
    }//GEN-LAST:event_campoOutroTipoMouseClicked

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
            java.util.logging.Logger.getLogger(ServicosAdicionar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ServicosAdicionar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ServicosAdicionar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServicosAdicionar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ServicosAdicionar dialog = new ServicosAdicionar(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton botaoConfirmar;
    private javax.swing.JButton btnFechar;
    private javax.swing.JTextField campoDias;
    private javax.swing.JTextField campoOutroTipo;
    private javax.swing.JComboBox<String> comboDias;
    private javax.swing.JComboBox<String> comboPeriodo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JComboBox<String> metodoPagamento;
    private javax.swing.JTextField nomeServico;
    private javax.swing.JFormattedTextField valorDinheiro;
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

    public JTextField getNomeServico() {
        return nomeServico;
    }

    public JTextField getCampoDias() {
        return campoDias;
    }

    public JComboBox<String> getComboDias() {
        return comboDias;
    }

    public JFormattedTextField getValorDinheiro() {
        return valorDinheiro;
    }

    private void setarValores(){
        comboPeriodo.setEnabled(true);
        campoOutroTipo.setText("Outro");
        campoOutroTipo.setEnabled(false);
    }
    
    private void esvaziarOutroTipo(){
        if(campoOutroTipo.getText().equals("Outro")){
            campoOutroTipo.setText("");
        }
    }

    public JTextField getCampoOutroTipo() {
        return campoOutroTipo;
    }

    public Frame getParent() {
        return parent;
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
