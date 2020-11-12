/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.adicionais.AdicionarAlunosController;
import Controller.auxiliar.FormatacaoCamposRestritosLetras;
import Controller.auxiliar.FormatacaodeCamposRestritos;
import Controller.auxiliar.JMoneyField;
import java.awt.Button;
import java.awt.Color;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author 55989
 */
public class AlunosCadastro extends javax.swing.JFrame {
    private final AdicionarAlunosController controller;

    /**
     * Creates new form AlunosCadastro
     */
    public AlunosCadastro() {
        initComponents();
        controller = new AdicionarAlunosController(this);
        botaofechar.setBackground(new Color (0,0,0,0));
        botaoDescricao.setBackground(new Color (0,0,0,0));
        botaoDescricao.setVisible(false);
       turmasehorarios.setBackground(new Color (0,0,0,0));
       botaoConfirmar.setBackground(new Color (0,0,0,0));
       
       
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        botaofechar = new javax.swing.JButton();
        comboTurma = new javax.swing.JComboBox<>();
        botaoDescricao = new javax.swing.JButton();
        botaoConfirmar = new javax.swing.JButton();
        campoNomeAluno = new FormatacaoCamposRestritosLetras(50);
        campoNascimentoAluno = new javax.swing.JFormattedTextField();
        campoNomePai = new FormatacaoCamposRestritosLetras(50);
        campoNomeMae = new FormatacaoCamposRestritosLetras(50);
        turmasehorarios = new javax.swing.JButton();
        campoValor = new JMoneyField();
        campoRua = new FormatacaodeCamposRestritos(50, 0);
        campoCidade = new FormatacaodeCamposRestritos(20);
        campoBairro = new FormatacaodeCamposRestritos(25, 0);
        campoNum = new FormatacaodeCamposRestritos(10, 0);
        campoTelefone = new javax.swing.JFormattedTextField();
        campoCPFAluno = new javax.swing.JFormattedTextField();
        campoCelular = new javax.swing.JFormattedTextField();
        campoContatoPai = new javax.swing.JFormattedTextField();
        campoContatoMae = new javax.swing.JFormattedTextField();
        campoCPFPai = new javax.swing.JFormattedTextField();
        campoCPFMae = new javax.swing.JFormattedTextField();
        comboPlano = new javax.swing.JComboBox<>();
        comboEstado = new javax.swing.JComboBox<>();
        campoCEP = new javax.swing.JFormattedTextField();
        planodefundo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        botaofechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaofechar.png"))); // NOI18N
        botaofechar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoHoverfechar.png"))); // NOI18N
        botaofechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaofecharActionPerformed(evt);
            }
        });
        getContentPane().add(botaofechar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 210, 50));

        comboTurma.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[Nenhuma]" }));
        getContentPane().add(comboTurma, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 468, 190, 32));

        botaoDescricao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/cadastroalunos/btnDescricao.png"))); // NOI18N
        botaoDescricao.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/cadastroalunos/btnDescricaoHover.png"))); // NOI18N
        botaoDescricao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoDescricaoActionPerformed(evt);
            }
        });
        getContentPane().add(botaoDescricao, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 720, 150, 30));

        botaoConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/cadastroalunos/botaoConfirmar.png"))); // NOI18N
        botaoConfirmar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/cadastroalunos/botaoConfirmarHover.png"))); // NOI18N
        botaoConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoConfirmarActionPerformed(evt);
            }
        });
        getContentPane().add(botaoConfirmar, new org.netbeans.lib.awtextra.AbsoluteConstraints(273, 690, 330, 60));

        campoNomeAluno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoNomeAlunoActionPerformed(evt);
            }
        });
        getContentPane().add(campoNomeAluno, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 192, 240, 32));

        try {
            campoNascimentoAluno.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        getContentPane().add(campoNascimentoAluno, new org.netbeans.lib.awtextra.AbsoluteConstraints(587, 192, 165, 32));
        getContentPane().add(campoNomePai, new org.netbeans.lib.awtextra.AbsoluteConstraints(492, 267, 240, 32));

        campoNomeMae.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoNomeMaeActionPerformed(evt);
            }
        });
        getContentPane().add(campoNomeMae, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 336, 240, 32));

        turmasehorarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/cadastroalunos/botaoTurmaseHora.png"))); // NOI18N
        turmasehorarios.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/cadastroalunos/botaoTurmaseHoraHover.png"))); // NOI18N
        turmasehorarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                turmasehorariosActionPerformed(evt);
            }
        });
        getContentPane().add(turmasehorarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(343, 468, 230, 32));
        getContentPane().add(campoValor, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 468, 170, 32));

        campoRua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoRuaActionPerformed(evt);
            }
        });
        getContentPane().add(campoRua, new org.netbeans.lib.awtextra.AbsoluteConstraints(86, 581, 240, 32));

        campoCidade.setText("Santa Inês");
        getContentPane().add(campoCidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 582, 140, 30));

        campoBairro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoBairroActionPerformed(evt);
            }
        });
        getContentPane().add(campoBairro, new org.netbeans.lib.awtextra.AbsoluteConstraints(496, 582, 140, 30));
        getContentPane().add(campoNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(246, 650, 140, 32));

        try {
            campoTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) ####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        getContentPane().add(campoTelefone, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 267, 160, 32));

        try {
            campoCPFAluno.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        campoCPFAluno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoCPFAlunoActionPerformed(evt);
            }
        });
        getContentPane().add(campoCPFAluno, new org.netbeans.lib.awtextra.AbsoluteConstraints(379, 192, 190, 32));

        try {
            campoCelular.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) # ####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        getContentPane().add(campoCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(314, 267, 160, 32));

        try {
            campoContatoPai.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) # ####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        getContentPane().add(campoContatoPai, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 336, 160, 32));

        try {
            campoContatoMae.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) # ####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        getContentPane().add(campoContatoMae, new org.netbeans.lib.awtextra.AbsoluteConstraints(567, 336, 160, 32));

        try {
            campoCPFPai.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        getContentPane().add(campoCPFPai, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 405, 190, 32));

        try {
            campoCPFMae.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        getContentPane().add(campoCPFMae, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 405, 190, 32));

        comboPlano.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[Nenhum]" }));
        comboPlano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboPlanoActionPerformed(evt);
            }
        });
        getContentPane().add(comboPlano, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 405, 190, 32));

        comboEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[Nenhum]", "AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RO", "RN", "RR", "RS", "SC", "SE", "SP", "TO" }));
        getContentPane().add(comboEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 580, 140, 30));

        try {
            campoCEP.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.###-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        getContentPane().add(campoCEP, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 650, 130, 32));

        planodefundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/cadastroalunos/fundo jframecadastroalunos.png"))); // NOI18N
        getContentPane().add(planodefundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void campoNomeAlunoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoNomeAlunoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoNomeAlunoActionPerformed

    private void campoNomeMaeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoNomeMaeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoNomeMaeActionPerformed

    private void campoCPFAlunoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoCPFAlunoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoCPFAlunoActionPerformed

    private void campoRuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoRuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoRuaActionPerformed

    private void campoBairroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoBairroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoBairroActionPerformed

    private void botaofecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaofecharActionPerformed

        this.dispose();
    }//GEN-LAST:event_botaofecharActionPerformed

    private void turmasehorariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_turmasehorariosActionPerformed
        // TODO add your handling code here:
        AlunosCadastroTurmasEHorarios abrir = new AlunosCadastroTurmasEHorarios();
        abrir.setVisible(true);
    }//GEN-LAST:event_turmasehorariosActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            controller.verificacaoDeTurmaEServico();
        } catch (SQLException ex) {
            Logger.getLogger(AlunosCadastro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowOpened

    private void botaoDescricaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoDescricaoActionPerformed
        // TODO add your handling code here:
        AlunosCadastroDescricao abrir = new AlunosCadastroDescricao();
        abrir.setVisible(true);
    }//GEN-LAST:event_botaoDescricaoActionPerformed

    private void botaoConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoConfirmarActionPerformed
        try {
            controller.adicionarAlunos();
        } catch (SQLException ex) {
            Logger.getLogger(AlunosCadastro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botaoConfirmarActionPerformed

    private void comboPlanoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPlanoActionPerformed
        try {
            controller.setarValorContrato();
        } catch (SQLException ex) {
            Logger.getLogger(AlunosCadastro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_comboPlanoActionPerformed

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
            java.util.logging.Logger.getLogger(AlunosCadastro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AlunosCadastro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AlunosCadastro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AlunosCadastro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AlunosCadastro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoConfirmar;
    private javax.swing.JButton botaoDescricao;
    private javax.swing.JButton botaofechar;
    private javax.swing.JTextField campoBairro;
    private javax.swing.JFormattedTextField campoCEP;
    private javax.swing.JFormattedTextField campoCPFAluno;
    private javax.swing.JFormattedTextField campoCPFMae;
    private javax.swing.JFormattedTextField campoCPFPai;
    private javax.swing.JFormattedTextField campoCelular;
    private javax.swing.JTextField campoCidade;
    private javax.swing.JFormattedTextField campoContatoMae;
    private javax.swing.JFormattedTextField campoContatoPai;
    private javax.swing.JFormattedTextField campoNascimentoAluno;
    private javax.swing.JTextField campoNomeAluno;
    private javax.swing.JTextField campoNomeMae;
    private javax.swing.JTextField campoNomePai;
    private javax.swing.JTextField campoNum;
    private javax.swing.JTextField campoRua;
    private javax.swing.JFormattedTextField campoTelefone;
    private javax.swing.JFormattedTextField campoValor;
    private javax.swing.JComboBox<String> comboEstado;
    private javax.swing.JComboBox<String> comboPlano;
    private javax.swing.JComboBox<String> comboTurma;
    private javax.swing.JLabel planodefundo;
    private javax.swing.JButton turmasehorarios;
    // End of variables declaration//GEN-END:variables

    public void exibeMensagem(String mensagem) {
      JOptionPane.showMessageDialog(null, mensagem);
    }

    public JTextField getCampoBairro() {
        return campoBairro;
    }

    public JFormattedTextField getCampoCEP() {
        return campoCEP;
    }

    public JFormattedTextField getCampoCPFAluno() {
        return campoCPFAluno;
    }

    public JFormattedTextField getCampoCPFMae() {
        return campoCPFMae;
    }

    public JFormattedTextField getCampoCPFPai() {
        return campoCPFPai;
    }

    public JFormattedTextField getCampoCelular() {
        return campoCelular;
    }

    public JTextField getCampoCidade() {
        return campoCidade;
    }

    public JFormattedTextField getCampoContatoMae() {
        return campoContatoMae;
    }

    public JFormattedTextField getCampoContatoPai() {
        return campoContatoPai;
    }

    public JFormattedTextField getCampoNascimentoAluno() {
        return campoNascimentoAluno;
    }

    public JTextField getCampoNomeAluno() {
        return campoNomeAluno;
    }

    public JTextField getCampoNomeMae() {
        return campoNomeMae;
    }

    public JTextField getCampoNomePai() {
        return campoNomePai;
    }

    public JTextField getCampoNum() {
        return campoNum;
    }

    public JTextField getCampoRua() {
        return campoRua;
    }

    public JFormattedTextField getCampoTelefone() {
        return campoTelefone;
    }

    public JFormattedTextField getCampoValor() {
        return campoValor;
    }

    public JComboBox<String> getComboEstado() {
        return comboEstado;
    }

    public JComboBox<String> getComboPlano() {
        return comboPlano;
    }

    public JComboBox<String> getComboTurma() {
        return comboTurma;
    }
    
}
