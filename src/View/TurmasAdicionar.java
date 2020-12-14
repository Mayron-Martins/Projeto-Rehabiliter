/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.adicionais.AdicionarTurmasController;
import Controller.auxiliar.FormatacaodeCamposRestritos;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
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
public class TurmasAdicionar extends javax.swing.JDialog {
    private final java.awt.Frame parent;
    private final AdicionarTurmasController controller;
    private String diasDaSemana=null;
    private ArrayList <String> diasDaSemanaUnitarios;
    private String campoHorario="";
    private int aberto=0;


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
        campoHoras = new javax.swing.JFormattedTextField();
        campoMinutos = new javax.swing.JFormattedTextField();
        campoCapMax = new FormatacaodeCamposRestritos();
        campoNome = new javax.swing.JTextField();
        botaoConfirmar = new javax.swing.JButton();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        barraDeRolagemDiasdaSemana = new javax.swing.JScrollBar();
        caixaSegunda = new javax.swing.JCheckBox();
        caixaTerca = new javax.swing.JCheckBox();
        caixaQuarta = new javax.swing.JCheckBox();
        caixaQuinta = new javax.swing.JCheckBox();
        caixaSexta = new javax.swing.JCheckBox();
        caixaSabado = new javax.swing.JCheckBox();
        caixaDomingo = new javax.swing.JCheckBox();
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
        botaoFechar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoHoverfechar.png"))); // NOI18N
        botaoFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoFecharActionPerformed(evt);
            }
        });
        getContentPane().add(botaoFechar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 210, 50));

        try {
            campoHoras.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("## h")));
        } catch (java.text.ParseException erroHora) {
            erroHora.printStackTrace();}
        campoHoras.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(campoHoras, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 195, 60, 30));

        try {
            campoMinutos.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("## min")));
        } catch (java.text.ParseException erroMinutos) {
            erroMinutos.printStackTrace();}
        campoMinutos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(campoMinutos, new org.netbeans.lib.awtextra.AbsoluteConstraints(655, 195, 90, 30));

        campoCapMax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoCapMaxActionPerformed(evt);
            }
        });
        getContentPane().add(campoCapMax, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 270, 70, 30));

        campoNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoNomeActionPerformed(evt);
            }
        });
        getContentPane().add(campoNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 195, 240, 30));

        botaoConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/turmas/adicionarturma/confirmar.png"))); // NOI18N
        botaoConfirmar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/turmas/adicionarturma/confirmarhover.png"))); // NOI18N
        botaoConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoConfirmarActionPerformed(evt);
            }
        });
        getContentPane().add(botaoConfirmar, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 360, 330, 50));

        jLayeredPane1.setAutoscrolls(true);
        jLayeredPane1.setInheritsPopupMenu(true);
        jLayeredPane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        barraDeRolagemDiasdaSemana.setOrientation(javax.swing.JScrollBar.HORIZONTAL);
        barraDeRolagemDiasdaSemana.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                barraDeRolagemDiasdaSemanaMouseDragged(evt);
            }
        });
        barraDeRolagemDiasdaSemana.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                barraDeRolagemDiasdaSemanaMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                barraDeRolagemDiasdaSemanaMousePressed(evt);
            }
        });
        jLayeredPane1.add(barraDeRolagemDiasdaSemana, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 27, 190, 10));

        caixaSegunda.setText("Seg");
        jLayeredPane1.add(caixaSegunda, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        caixaTerca.setText("Ter");
        jLayeredPane1.add(caixaTerca, new org.netbeans.lib.awtextra.AbsoluteConstraints(46, 0, -1, -1));

        caixaQuarta.setText("Qua");
        jLayeredPane1.add(caixaQuarta, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 0, -1, -1));

        caixaQuinta.setText("Qui");
        jLayeredPane1.add(caixaQuinta, new org.netbeans.lib.awtextra.AbsoluteConstraints(138, 0, -1, -1));

        caixaSexta.setText("Sex");
        jLayeredPane1.add(caixaSexta, new org.netbeans.lib.awtextra.AbsoluteConstraints(182, 0, -1, -1));

        caixaSabado.setText("Sáb");
        jLayeredPane1.add(caixaSabado, new org.netbeans.lib.awtextra.AbsoluteConstraints(228, 0, -1, -1));

        caixaDomingo.setText("Dom");
        jLayeredPane1.add(caixaDomingo, new org.netbeans.lib.awtextra.AbsoluteConstraints(274, 0, -1, -1));

        getContentPane().add(jLayeredPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 195, 190, 40));

        fundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/backup/fundoTurma.png"))); // NOI18N
        getContentPane().add(fundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFecharActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_botaoFecharActionPerformed

    private void campoNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoNomeActionPerformed

    private void campoCapMaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoCapMaxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoCapMaxActionPerformed

    private void barraDeRolagemDiasdaSemanaMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barraDeRolagemDiasdaSemanaMouseDragged

      if(this.getMousePosition()!=null){
          int posicaoX = this.getMousePosition().x-this.jLayeredPane1.getX();
          if(posicaoX>=30&&posicaoX<=170){
             this.moverCaixas(); 
          }
      } 
      
        
    }//GEN-LAST:event_barraDeRolagemDiasdaSemanaMouseDragged

    private void barraDeRolagemDiasdaSemanaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barraDeRolagemDiasdaSemanaMouseClicked
        this.moverCaixasClique();
    }//GEN-LAST:event_barraDeRolagemDiasdaSemanaMouseClicked

    private void barraDeRolagemDiasdaSemanaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barraDeRolagemDiasdaSemanaMousePressed
        this.moverCaixasClique();
    }//GEN-LAST:event_barraDeRolagemDiasdaSemanaMousePressed

    private void botaoConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoConfirmarActionPerformed
        this.diasDaSemana();
        this.edicaoDeHorario();
        try {
            controller.adicionarTurma();
        } catch (SQLException ex) {
            Logger.getLogger(TurmasAdicionar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(TurmasAdicionar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botaoConfirmarActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

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
    private javax.swing.JScrollBar barraDeRolagemDiasdaSemana;
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
    private javax.swing.JFormattedTextField campoHoras;
    private javax.swing.JFormattedTextField campoMinutos;
    private javax.swing.JTextField campoNome;
    private javax.swing.JLabel fundo;
    private javax.swing.JLayeredPane jLayeredPane1;
    // End of variables declaration//GEN-END:variables

private void moverCaixas(){
    int positionX = this.getMousePosition().x-this.jLayeredPane1.getX();
    this.caixaSegunda.setLocation(30-positionX, 0);
    this.caixaTerca.setLocation(76-positionX, 0);
    this.caixaQuarta.setLocation(120-positionX, 0);
    this.caixaQuinta.setLocation(168-positionX, 0);
    this.caixaSexta.setLocation(212-positionX, 0);
    this.caixaSabado.setLocation(258-positionX, 0);
    this.caixaDomingo.setLocation(304-positionX, 0);
}

private void moverCaixasClique(){
    int positionX = this.barraDeRolagemDiasdaSemana.getValue()*2;
    this.caixaSegunda.setLocation(10-positionX, 0);
    this.caixaTerca.setLocation(56-positionX, 0);
    this.caixaQuarta.setLocation(100-positionX, 0);
    this.caixaQuinta.setLocation(148-positionX, 0);
    this.caixaSexta.setLocation(192-positionX, 0);
    this.caixaSabado.setLocation(238-positionX, 0);
    this.caixaDomingo.setLocation(284-positionX, 0);  
}
    
    private void diasDaSemana(){
        String seg = null;
        String ter =null;
        String qua =null;
        String qui =null;
        String sex =null;
        String sab =null;
        String dom =null;
        
        this.diasDaSemanaUnitarios = new ArrayList<>();
        
        if(this.caixaSegunda.isSelected()){seg ="Seg"; 
        this.diasDaSemanaUnitarios.add(seg);}
        if(this.caixaTerca.isSelected()){ter = "Ter";
        this.diasDaSemanaUnitarios.add(ter);}
        if(this.caixaQuarta.isSelected()){qua = "Qua";
        this.diasDaSemanaUnitarios.add(qua);}
        if(this.caixaQuinta.isSelected()){qui="Qui";
        this.diasDaSemanaUnitarios.add(qui);}
        if(this.caixaSexta.isSelected()){sex="Sex";
        this.diasDaSemanaUnitarios.add(sex);}
        if(this.caixaSabado.isSelected()){sab="Sab";
        this.diasDaSemanaUnitarios.add(sab);}
        if(this.caixaDomingo.isSelected()){dom="Dom";
        this.diasDaSemanaUnitarios.add(dom);}
        this.diasDaSemana = seg+", "+ter+", "+qua+", "+qui+", "+sex+", "+sab+", "+dom;
    }
    
    private void edicaoDeHorario(){
        
        String hora= null;
        String minutos = null;
        
        if(!this.campoHoras.getText().replace("  h", "").equals("")){hora = this.campoHoras.getText().replace(" h", "");}
        if(!this.campoMinutos.getText().replace(" min", "").equals("")){minutos = this.campoMinutos.getText().replace(" min", "");}
        if(!hora.equals("")&&!minutos.equals("")){this.campoHorario = hora+":"+minutos;}
        
    }
    
    public boolean testeValidacaoHorario(){
        boolean verificar = true;
        int horas = Integer.parseInt(this.campoHoras.getText().replace(" h", ""));
        int minutos = Integer.parseInt(this.campoMinutos.getText().replace(" min", ""));
        
        if((horas<0||horas>23)||(minutos<0||minutos>59)){
            verificar=false;
        }
        return verificar;
    }
    
    public void exibeMensagem(String mensagem) {
      JOptionPane.showMessageDialog(null, mensagem);
    }

    public JTextField getCampoCapMax() {
        return campoCapMax;
    }

    public String getCampoHorario() {
        return campoHorario;
    }


    public JTextField getCampoNome() {
        return campoNome;
    }


    public String getDiasDaSemana() {
        return diasDaSemana;
    }

    public ArrayList<String> getDiasDaSemanaUnitarios() {        
        return diasDaSemanaUnitarios;
    }
    
    public void desmarcarCaixas(){
        this.caixaSegunda.setSelected(false);
        this.caixaTerca.setSelected(false);
        this.caixaQuarta.setSelected(false);
        this.caixaQuinta.setSelected(false);
        this.caixaSexta.setSelected(false);
        this.caixaSabado.setSelected(false);
        this.caixaDomingo.setSelected(false);
    }

    public JFormattedTextField getCampoHoras() {
        return campoHoras;
    }

    public JFormattedTextField getCampoMinutos() {
        return campoMinutos;
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
