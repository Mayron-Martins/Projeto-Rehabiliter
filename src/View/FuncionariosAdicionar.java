/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.adicionais.AdicionarFuncionariosController;
import Controller.auxiliar.JMoneyField;
import java.awt.Color;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author 55989
 */
public class FuncionariosAdicionar extends javax.swing.JFrame {
    private final AdicionarFuncionariosController controller;

    /**
     * Creates new form FuncionariosAdicionar
     */
    public FuncionariosAdicionar() {
        initComponents();
        controller = new AdicionarFuncionariosController(this);
        botaoConfirmar.setBackground(new Color(0,0,0,0));
        botaofechar.setBackground(new Color(0,0,0,0));
        comboFuncionarios.setVisible(false);
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

        botaoConfirmar = new javax.swing.JButton();
        botaofechar = new javax.swing.JButton();
        campoTelefone = new javax.swing.JFormattedTextField();
        campoCelular = new javax.swing.JFormattedTextField();
        campoSalario = new JMoneyField();
        campoNascimento = new javax.swing.JFormattedTextField();
        campoCPF = new javax.swing.JFormattedTextField();
        campoSenha = new javax.swing.JPasswordField();
        campoNome = new javax.swing.JTextField();
        campoEmail = new javax.swing.JTextField();
        comboFuncionarios = new javax.swing.JComboBox<>();
        campoCargo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        botaoConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/cadastroalunos/botaoConfirmar.png"))); // NOI18N
        botaoConfirmar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/cadastroalunos/botaoConfirmarHover.png"))); // NOI18N
        botaoConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoConfirmarActionPerformed(evt);
            }
        });
        getContentPane().add(botaoConfirmar, new org.netbeans.lib.awtextra.AbsoluteConstraints(273, 470, 330, 60));

        botaofechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaofechar.png"))); // NOI18N
        botaofechar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoHoverfechar.png"))); // NOI18N
        botaofechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaofecharActionPerformed(evt);
            }
        });
        getContentPane().add(botaofechar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 210, 50));

        try {
            campoTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) ####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        getContentPane().add(campoTelefone, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 280, 180, 30));

        try {
            campoCelular.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) # ####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        getContentPane().add(campoCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 280, 160, 30));
        getContentPane().add(campoSalario, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 360, 160, 30));

        try {
            campoNascimento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        getContentPane().add(campoNascimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 200, 160, 30));

        try {
            campoCPF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        getContentPane().add(campoCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 200, 180, 30));
        getContentPane().add(campoSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 360, 220, 30));
        getContentPane().add(campoNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 200, 230, 30));
        getContentPane().add(campoEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 360, 140, 30));

        comboFuncionarios.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[Nenhum]" }));
        getContentPane().add(comboFuncionarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 280, 160, 30));
        getContentPane().add(campoCargo, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 280, 160, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/funcionarios/cadastro/fundo.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaofecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaofecharActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_botaofecharActionPerformed

    private void botaoConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoConfirmarActionPerformed
        try {
            controller.adicionarFuncionario();
        } catch (SQLException ex) {
            Logger.getLogger(FuncionariosAdicionar.class.getName()).log(Level.SEVERE, null, ex);
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
            java.util.logging.Logger.getLogger(FuncionariosAdicionar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FuncionariosAdicionar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FuncionariosAdicionar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FuncionariosAdicionar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FuncionariosAdicionar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoConfirmar;
    private javax.swing.JButton botaofechar;
    private javax.swing.JFormattedTextField campoCPF;
    private javax.swing.JTextField campoCargo;
    private javax.swing.JFormattedTextField campoCelular;
    private javax.swing.JTextField campoEmail;
    private javax.swing.JFormattedTextField campoNascimento;
    private javax.swing.JTextField campoNome;
    private javax.swing.JFormattedTextField campoSalario;
    private javax.swing.JPasswordField campoSenha;
    private javax.swing.JFormattedTextField campoTelefone;
    private javax.swing.JComboBox<String> comboFuncionarios;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

    public JFormattedTextField getCampoCPF() {
        return campoCPF;
    }

    public JTextField getCampoCargo() {
        return campoCargo;
    }

    public JFormattedTextField getCampoCelular() {
        return campoCelular;
    }

    public JTextField getCampoEmail() {
        return campoEmail;
    }

    public JFormattedTextField getCampoNascimento() {
        return campoNascimento;
    }

    public JTextField getCampoNome() {
        return campoNome;
    }

    public JFormattedTextField getCampoSalario() {
        return campoSalario;
    }

    public JPasswordField getCampoSenha() {
        return campoSenha;
    }

    public JFormattedTextField getCampoTelefone() {
        return campoTelefone;
    }

    public void exibeMensagem(String mensagem) {
      JOptionPane.showMessageDialog(null, mensagem);
    }
}
