/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Paineis;

import Controller.adicionais.SenhaEsquecidaController;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import javax.swing.ButtonGroup;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Mayro
 */
public class LoginSenhaEsquecida extends javax.swing.JDialog {
    private final SenhaEsquecidaController controller;

    /**
     * Creates new form LoginSenhaEsquecida
     */
    public LoginSenhaEsquecida(java.awt.Frame parent, boolean modal, String cpf) {
        super(parent, modal);
        initComponents();
        jButton2.setBackground(new Color(0,0,0,0));
        btnCancelar.setBackground(new Color(0,0,0,0));
        
        controller = new SenhaEsquecidaController(this);
        
        campoCPF.setValue(null);
        campoCPF.setText(cpf);
        this.agruparJRadios();
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
        campoCelular = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        campoCPF = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        campoDataNascimento = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        campoEmail = new javax.swing.JTextField();
        JRadioEmail = new javax.swing.JRadioButton();
        JRadioCelular = new javax.swing.JRadioButton();
        btnCancelar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        setSize(new java.awt.Dimension(400, 400));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 134, 191));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        try {
            campoCelular.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) # ####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        campoCelular.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                campoCelularMouseClicked(evt);
            }
        });
        jPanel1.add(campoCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 210, 210, 30));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Celular");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 190, -1, -1));

        try {
            campoCPF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jPanel1.add(campoCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 210, 30));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("RECUPERAÇÃO DE SENHA");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, -1, -1));
        jPanel1.add(campoDataNascimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 150, 210, 30));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Data Nascimento");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 130, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("CPF");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 70, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("E-mail");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 250, -1, -1));

        campoEmail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                campoEmailMouseClicked(evt);
            }
        });
        jPanel1.add(campoEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 270, 210, 30));

        JRadioEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JRadioEmailActionPerformed(evt);
            }
        });
        jPanel1.add(JRadioEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 273, -1, -1));

        JRadioCelular.setSelected(true);
        JRadioCelular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JRadioCelularActionPerformed(evt);
            }
        });
        jPanel1.add(JRadioCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 215, -1, -1));

        btnCancelar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/imagensparaseremtrocadas/btnCancelar2.png"))); // NOI18N
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/imagensparaseremtrocadas/btnCancelar2.png"))); // NOI18N
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 340, 120, 40));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/imagensparaseremtrocadas/btnConcluir.png"))); // NOI18N
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 340, 110, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 400));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void JRadioCelularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JRadioCelularActionPerformed
        this.habilitarDesabilitarCampos(true);
    }//GEN-LAST:event_JRadioCelularActionPerformed

    private void JRadioEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JRadioEmailActionPerformed
        this.habilitarDesabilitarCampos(false);
    }//GEN-LAST:event_JRadioEmailActionPerformed

    private void campoCelularMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_campoCelularMouseClicked
        this.habilitarDesabilitarCampos(true);
    }//GEN-LAST:event_campoCelularMouseClicked

    private void campoEmailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_campoEmailMouseClicked
        this.habilitarDesabilitarCampos(false);
    }//GEN-LAST:event_campoEmailMouseClicked

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        controller.recuperarSenha();
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(LoginSenhaEsquecida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginSenhaEsquecida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginSenhaEsquecida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginSenhaEsquecida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                LoginSenhaEsquecida dialog = new LoginSenhaEsquecida(new javax.swing.JFrame(), true, null);
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
    private javax.swing.JRadioButton JRadioCelular;
    private javax.swing.JRadioButton JRadioEmail;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JFormattedTextField campoCPF;
    private javax.swing.JFormattedTextField campoCelular;
    private com.toedter.calendar.JDateChooser campoDataNascimento;
    private javax.swing.JTextField campoEmail;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
   
    public JFormattedTextField getCampoCPF() {
        return campoCPF;
    }

    public JFormattedTextField getCampoCelular() {
        return campoCelular;
    }

    public JDateChooser getCampoDataNascimento() {
        return campoDataNascimento;
    }

    public JTextField getCampoEmail() {
        return campoEmail;
    }
    
    public void exibeMensagem(String mensagem) {
      JOptionPane.showMessageDialog(null, mensagem);
    }
    
    private void agruparJRadios(){
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(JRadioCelular);
        grupo.add(JRadioEmail);
        this.habilitarDesabilitarCampos(true);
    }
    
    private void habilitarDesabilitarCampos(boolean opcao){
        campoCelular.setEnabled(opcao);
        campoEmail.setEnabled(!opcao);
        
        JRadioCelular.setSelected(opcao);
        JRadioEmail.setSelected(!opcao);
    }  
}
