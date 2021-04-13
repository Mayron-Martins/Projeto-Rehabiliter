/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.adicionais.AdicionarTurmasController;
import Controller.auxiliar.FormatacaodeCamposRestritos;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;

/**
 *
 * @author 55989
 */
public class TurmasAdicionar extends javax.swing.JDialog {
    private final java.awt.Frame parent;
    private final AdicionarTurmasController controller;
    private Vector <JCheckBox> diasSemana = new Vector <JCheckBox>();
    private Vector <JFormattedTextField> campoHorariosI = new Vector <JFormattedTextField>();
    private Vector <JFormattedTextField> campoHorariosF = new Vector <JFormattedTextField>();


    /**
     * Creates new form TurmasAdicionar
     * @param parent
     * @param modal
     */
    public TurmasAdicionar(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.parent = parent;
        this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/imagens/rehabi.png")).getImage());
        controller = new AdicionarTurmasController(this);
        botaoFechar.setBackground(new Color(0,0,0,0));
        botaoConfirmar.setBackground(new Color(0,0,0,0));
        
         //Fechar a tela quando pressionar ESC
        this.teclasDeAtalho();
        this.grupoBotoes();
        setarConfigsInciciais();
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
        campoCapMax = new FormatacaodeCamposRestritos();
        campoNome = new javax.swing.JTextField();
        botaoConfirmar = new javax.swing.JButton();
        caixaSegunda = new javax.swing.JCheckBox();
        caixaTerca = new javax.swing.JCheckBox();
        caixaQuarta = new javax.swing.JCheckBox();
        caixaQuinta = new javax.swing.JCheckBox();
        caixaSexta = new javax.swing.JCheckBox();
        caixaSabado = new javax.swing.JCheckBox();
        caixaDomingo = new javax.swing.JCheckBox();
        radioHorDiversos = new javax.swing.JRadioButton();
        radioHorUnico = new javax.swing.JRadioButton();
        painelHorDiversos = new javax.swing.JPanel();
        campoHoraSegI = new javax.swing.JFormattedTextField();
        campoHoraTerI = new javax.swing.JFormattedTextField();
        campoHoraQuaI = new javax.swing.JFormattedTextField();
        campoHoraQuiI = new javax.swing.JFormattedTextField();
        campoHoraSexI = new javax.swing.JFormattedTextField();
        campoHoraSabI = new javax.swing.JFormattedTextField();
        campoHoraDomI = new javax.swing.JFormattedTextField();
        campoHoraSegF = new javax.swing.JFormattedTextField();
        campoHoraTerF = new javax.swing.JFormattedTextField();
        campoHoraQuaF = new javax.swing.JFormattedTextField();
        campoHoraQuiF = new javax.swing.JFormattedTextField();
        campoHoraSexF = new javax.swing.JFormattedTextField();
        campoHoraSabF = new javax.swing.JFormattedTextField();
        campoHoraDomF = new javax.swing.JFormattedTextField();
        painelHorUnico = new javax.swing.JPanel();
        campoHoraUnicoI = new javax.swing.JFormattedTextField();
        campoHoraUnicoF = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        fundo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        botaoFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaofechar.png"))); // NOI18N
        botaoFechar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoFechar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoHoverfechar.png"))); // NOI18N
        botaoFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoFecharActionPerformed(evt);
            }
        });
        getContentPane().add(botaoFechar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 210, 50));

        campoCapMax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoCapMaxActionPerformed(evt);
            }
        });
        getContentPane().add(campoCapMax, new org.netbeans.lib.awtextra.AbsoluteConstraints(198, 240, 70, 30));

        campoNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoNomeActionPerformed(evt);
            }
        });
        getContentPane().add(campoNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 160, 240, 30));

        botaoConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/turmas/adicionarturma/confirmar.png"))); // NOI18N
        botaoConfirmar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoConfirmar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/turmas/adicionarturma/confirmarhover.png"))); // NOI18N
        botaoConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoConfirmarActionPerformed(evt);
            }
        });
        getContentPane().add(botaoConfirmar, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 400, 330, 50));

        caixaSegunda.setForeground(new java.awt.Color(11, 13, 138));
        caixaSegunda.setText("Segunda");
        caixaSegunda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                caixaSegundaActionPerformed(evt);
            }
        });
        getContentPane().add(caixaSegunda, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 164, -1, -1));

        caixaTerca.setForeground(new java.awt.Color(11, 13, 138));
        caixaTerca.setText("Terça");
        caixaTerca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                caixaTercaActionPerformed(evt);
            }
        });
        getContentPane().add(caixaTerca, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 194, -1, -1));

        caixaQuarta.setForeground(new java.awt.Color(11, 13, 138));
        caixaQuarta.setText("Quarta");
        caixaQuarta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                caixaQuartaActionPerformed(evt);
            }
        });
        getContentPane().add(caixaQuarta, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 224, -1, -1));

        caixaQuinta.setForeground(new java.awt.Color(11, 13, 138));
        caixaQuinta.setText("Quinta");
        caixaQuinta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                caixaQuintaActionPerformed(evt);
            }
        });
        getContentPane().add(caixaQuinta, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 254, -1, -1));

        caixaSexta.setForeground(new java.awt.Color(11, 13, 138));
        caixaSexta.setText("Sexta");
        caixaSexta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                caixaSextaActionPerformed(evt);
            }
        });
        getContentPane().add(caixaSexta, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 284, -1, -1));

        caixaSabado.setForeground(new java.awt.Color(11, 13, 138));
        caixaSabado.setText("Sábado");
        caixaSabado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                caixaSabadoActionPerformed(evt);
            }
        });
        getContentPane().add(caixaSabado, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 314, -1, -1));

        caixaDomingo.setForeground(new java.awt.Color(11, 13, 138));
        caixaDomingo.setText("Domingo");
        caixaDomingo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                caixaDomingoActionPerformed(evt);
            }
        });
        getContentPane().add(caixaDomingo, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 344, -1, -1));

        radioHorDiversos.setText("Diversos");
        radioHorDiversos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioHorDiversosActionPerformed(evt);
            }
        });
        getContentPane().add(radioHorDiversos, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 110, -1, -1));

        radioHorUnico.setSelected(true);
        radioHorUnico.setText("Único");
        radioHorUnico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioHorUnicoActionPerformed(evt);
            }
        });
        getContentPane().add(radioHorUnico, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 110, -1, -1));

        painelHorDiversos.setBackground(new java.awt.Color(157, 197, 187));
        painelHorDiversos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        try {
            campoHoraSegI.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        campoHoraSegI.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        painelHorDiversos.add(campoHoraSegI, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 60, 30));

        try {
            campoHoraTerI.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        campoHoraTerI.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        painelHorDiversos.add(campoHoraTerI, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 60, 30));

        try {
            campoHoraQuaI.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        campoHoraQuaI.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        painelHorDiversos.add(campoHoraQuaI, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 60, 30));

        try {
            campoHoraQuiI.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        campoHoraQuiI.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        painelHorDiversos.add(campoHoraQuiI, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 60, 30));

        try {
            campoHoraSexI.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        campoHoraSexI.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        painelHorDiversos.add(campoHoraSexI, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 60, 30));

        try {
            campoHoraSabI.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        campoHoraSabI.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        painelHorDiversos.add(campoHoraSabI, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 60, 30));

        try {
            campoHoraDomI.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        campoHoraDomI.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        painelHorDiversos.add(campoHoraDomI, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 60, 30));

        try {
            campoHoraSegF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        campoHoraSegF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        painelHorDiversos.add(campoHoraSegF, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 0, 60, 30));

        try {
            campoHoraTerF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        campoHoraTerF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        painelHorDiversos.add(campoHoraTerF, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, 60, 30));

        try {
            campoHoraQuaF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        campoHoraQuaF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        painelHorDiversos.add(campoHoraQuaF, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, 60, 30));

        try {
            campoHoraQuiF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        campoHoraQuiF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        painelHorDiversos.add(campoHoraQuiF, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 60, 30));

        try {
            campoHoraSexF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        campoHoraSexF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        painelHorDiversos.add(campoHoraSexF, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 60, 30));

        try {
            campoHoraSabF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        campoHoraSabF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        painelHorDiversos.add(campoHoraSabF, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, 60, 30));

        try {
            campoHoraDomF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        campoHoraDomF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        painelHorDiversos.add(campoHoraDomF, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 180, 60, 30));

        getContentPane().add(painelHorDiversos, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 160, 210, 270));

        painelHorUnico.setBackground(new java.awt.Color(157, 197, 187));
        painelHorUnico.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        try {
            campoHoraUnicoI.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        campoHoraUnicoI.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        painelHorUnico.add(campoHoraUnicoI, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 60, 30));

        try {
            campoHoraUnicoF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        campoHoraUnicoF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        painelHorUnico.add(campoHoraUnicoF, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 0, 60, 30));

        getContentPane().add(painelHorUnico, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 160, 210, 270));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(11, 13, 138));
        jLabel1.setText("Dias da Semana");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 140, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(11, 13, 138));
        jLabel2.setText("Horário Fim");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 140, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(11, 13, 138));
        jLabel3.setText("Capacidade Máxima");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 220, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(11, 13, 138));
        jLabel4.setText("Nome");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 140, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(11, 13, 138));
        jLabel5.setText("Horário Início");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 140, -1, -1));

        fundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/imagensparaseremtrocadas/adicionarNova.png"))); // NOI18N
        getContentPane().add(fundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 480));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFecharActionPerformed
        controller.limparCampos();
        this.dispose();
    }//GEN-LAST:event_botaoFecharActionPerformed

    private void campoNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoNomeActionPerformed

    private void campoCapMaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoCapMaxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoCapMaxActionPerformed

    private void botaoConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoConfirmarActionPerformed
        controller.adicionarTurma();
    }//GEN-LAST:event_botaoConfirmarActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

    private void radioHorUnicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioHorUnicoActionPerformed
        paineisHorarios(true);
    }//GEN-LAST:event_radioHorUnicoActionPerformed

    private void radioHorDiversosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioHorDiversosActionPerformed
        paineisHorarios(false);
    }//GEN-LAST:event_radioHorDiversosActionPerformed

    private void caixaSegundaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_caixaSegundaActionPerformed
        visibleHorario(caixaSegunda, campoHoraSegI, campoHoraSegF);
    }//GEN-LAST:event_caixaSegundaActionPerformed

    private void caixaTercaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_caixaTercaActionPerformed
        visibleHorario(caixaTerca, campoHoraTerI, campoHoraTerF);
    }//GEN-LAST:event_caixaTercaActionPerformed

    private void caixaQuartaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_caixaQuartaActionPerformed
        visibleHorario(caixaQuarta, campoHoraQuaI, campoHoraQuaF);
    }//GEN-LAST:event_caixaQuartaActionPerformed

    private void caixaQuintaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_caixaQuintaActionPerformed
        visibleHorario(caixaQuinta, campoHoraQuiI, campoHoraQuiF);
    }//GEN-LAST:event_caixaQuintaActionPerformed

    private void caixaSextaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_caixaSextaActionPerformed
        visibleHorario(caixaSexta, campoHoraSexI, campoHoraSexF);
    }//GEN-LAST:event_caixaSextaActionPerformed

    private void caixaSabadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_caixaSabadoActionPerformed
        visibleHorario(caixaSabado, campoHoraSabI, campoHoraSabF);
    }//GEN-LAST:event_caixaSabadoActionPerformed

    private void caixaDomingoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_caixaDomingoActionPerformed
        visibleHorario(caixaDomingo, campoHoraDomI, campoHoraDomF);
    }//GEN-LAST:event_caixaDomingoActionPerformed

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
            java.util.logging.Logger.getLogger(TurmasAdicionar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TurmasAdicionar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TurmasAdicionar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TurmasAdicionar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TurmasAdicionar dialog = new TurmasAdicionar(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton botaoFechar;
    private javax.swing.JCheckBox caixaDomingo;
    private javax.swing.JCheckBox caixaQuarta;
    private javax.swing.JCheckBox caixaQuinta;
    private javax.swing.JCheckBox caixaSabado;
    private javax.swing.JCheckBox caixaSegunda;
    private javax.swing.JCheckBox caixaSexta;
    private javax.swing.JCheckBox caixaTerca;
    private javax.swing.JTextField campoCapMax;
    private javax.swing.JFormattedTextField campoHoraDomF;
    private javax.swing.JFormattedTextField campoHoraDomI;
    private javax.swing.JFormattedTextField campoHoraQuaF;
    private javax.swing.JFormattedTextField campoHoraQuaI;
    private javax.swing.JFormattedTextField campoHoraQuiF;
    private javax.swing.JFormattedTextField campoHoraQuiI;
    private javax.swing.JFormattedTextField campoHoraSabF;
    private javax.swing.JFormattedTextField campoHoraSabI;
    private javax.swing.JFormattedTextField campoHoraSegF;
    private javax.swing.JFormattedTextField campoHoraSegI;
    private javax.swing.JFormattedTextField campoHoraSexF;
    private javax.swing.JFormattedTextField campoHoraSexI;
    private javax.swing.JFormattedTextField campoHoraTerF;
    private javax.swing.JFormattedTextField campoHoraTerI;
    private javax.swing.JFormattedTextField campoHoraUnicoF;
    private javax.swing.JFormattedTextField campoHoraUnicoI;
    private javax.swing.JTextField campoNome;
    private javax.swing.JLabel fundo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel painelHorDiversos;
    private javax.swing.JPanel painelHorUnico;
    private javax.swing.JRadioButton radioHorDiversos;
    private javax.swing.JRadioButton radioHorUnico;
    // End of variables declaration//GEN-END:variables
        
    public void exibeMensagem(String mensagem) {
      JOptionPane.showMessageDialog(null, mensagem);
    }

    public JTextField getCampoCapMax() {
        return campoCapMax;
    }

    public JTextField getCampoNome() {
        return campoNome;
    }

    public Vector<JCheckBox> getDiasSemana() {
        return diasSemana;
    }

    public Vector <JFormattedTextField> getCampoHorariosI() {
        return campoHorariosI;
    }

    public Vector<JFormattedTextField> getCampoHorariosF() {
        return campoHorariosF;
    }

    public JRadioButton getRadioHorUnico() {
        return radioHorUnico;
    }
    
    public Frame getParent() {
        return parent;
    }
    
    private void grupoBotoes(){
        ButtonGroup grupoHorario = new ButtonGroup();
        grupoHorario.add(radioHorUnico);
        grupoHorario.add(radioHorDiversos);
        
        Vector<JCheckBox> caixas = new Vector<JCheckBox>();
        
        diasSemana.addElement(caixaSegunda);
        diasSemana.addElement(caixaTerca);
        diasSemana.addElement(caixaQuarta);
        diasSemana.addElement(caixaQuinta);
        diasSemana.addElement(caixaSexta);
        diasSemana.addElement(caixaSabado);
        diasSemana.addElement(caixaDomingo);
        
        
        campoHorariosI.addElement(campoHoraUnicoI);
        campoHorariosI.addElement(campoHoraSegI);
        campoHorariosI.addElement(campoHoraTerI);
        campoHorariosI.addElement(campoHoraQuaI);
        campoHorariosI.addElement(campoHoraQuiI);
        campoHorariosI.addElement(campoHoraSexI);
        campoHorariosI.addElement(campoHoraSabI);
        campoHorariosI.addElement(campoHoraDomI);
        
        
        
        campoHorariosF.addElement(campoHoraUnicoF);
        campoHorariosF.addElement(campoHoraSegF);
        campoHorariosF.addElement(campoHoraTerF);
        campoHorariosF.addElement(campoHoraQuaF);
        campoHorariosF.addElement(campoHoraQuiF);
        campoHorariosF.addElement(campoHoraSexF);
        campoHorariosF.addElement(campoHoraSabF);
        campoHorariosF.addElement(campoHoraDomF);
    }
    
    public void setarConfigsInciciais(){
        painelHorUnico.setVisible(true);
        painelHorDiversos.setVisible(false);
        campoHoraSegI.setVisible(false);
        campoHoraTerI.setVisible(false);
        campoHoraQuaI.setVisible(false);
        campoHoraQuiI.setVisible(false);
        campoHoraSexI.setVisible(false);
        campoHoraSabI.setVisible(false);
        campoHoraDomI.setVisible(false);
        
        campoHoraSegF.setVisible(false);
        campoHoraTerF.setVisible(false);
        campoHoraQuaF.setVisible(false);
        campoHoraQuiF.setVisible(false);
        campoHoraSexF.setVisible(false);
        campoHoraSabF.setVisible(false);
        campoHoraDomF.setVisible(false);
    }
    
    private void paineisHorarios(boolean unico){
        painelHorUnico.setVisible(unico);
        painelHorDiversos.setVisible(!unico);
        if(radioHorDiversos.isSelected()){
            visibilidadeHorariosPosTroca();
        }
    }
    
    private void visibilidadeHorariosPosTroca(){
        int cont=1;
        for(JCheckBox caixa : diasSemana){
            if(caixa.isSelected()){
                visibleHorario(caixa, campoHorariosI.get(cont), campoHorariosF.get(cont));
            }
            cont++;
        }
    }
    
    private void visibleHorario(JCheckBox caixaSelecao, JFormattedTextField campo, JFormattedTextField campo2){
        if(radioHorDiversos.isSelected()&&caixaSelecao.isSelected()){
            campo.setVisible(true);
            campo2.setVisible(true);
        }
        else{
            campo.setVisible(false);
            campo2.setVisible(false);
        }
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
