/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;


import Controller.TurmasController;
import Controller.auxiliar.FormatacaodeCamposRestritos;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author 55989
 */
public class TurmasView extends javax.swing.JFrame {
    private final int numeroTela = 2;
    private final TurmasController controller;
    private String diasDaSemana=null;
    private ArrayList <String> diasDaSemanaUnitarios;
    private String campoHorario="";
    private final JTextField campoMaximoAlunos = new FormatacaodeCamposRestritos();

    /**
     * Creates new form Turmas
     */
    public TurmasView()  {
        initComponents();
        controller = new TurmasController(this);
        botaoAdicionarTurmas.setBackground(new Color(0,0,0,0));
        botaoEditarTurmas.setBackground(new Color(0,0,0,0));
        botaoFechar.setBackground(new Color(0,0,0,0));
        botaoListarTurmas.setBackground(new Color(0,0,0,0));
        botaoRemoverTurmas.setBackground(new Color(0,0,0,0));
        botaobuscar.setBackground(new Color(0,0,0,0));
        this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/imagens/rehabi.png")).getImage());
        
        
        //formatarTabelas.formatar(tabelaAlunos, 'C');
        desabilitarVisibilidadeComponente();
        

        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        blocoDiasdaSemana = new javax.swing.JLayeredPane();
        barraDeRolagemDiasdaSemana = new javax.swing.JScrollBar();
        caixaSegunda = new javax.swing.JCheckBox();
        caixaTerca = new javax.swing.JCheckBox();
        caixaQuarta = new javax.swing.JCheckBox();
        caixaQuinta = new javax.swing.JCheckBox();
        caixaSexta = new javax.swing.JCheckBox();
        caixaSabado = new javax.swing.JCheckBox();
        caixaDomingo = new javax.swing.JCheckBox();
        botaoRemoverTurmas = new javax.swing.JButton();
        botaoEditarTurmas = new javax.swing.JButton();
        botaoListarTurmas = new javax.swing.JButton();
        botaoAdicionarTurmas = new javax.swing.JButton();
        botaoFechar = new javax.swing.JButton();
        botaobuscar = new javax.swing.JButton();
        campoDePesquisa = new javax.swing.JTextField();
        campoEdicaoHoras = new javax.swing.JFormattedTextField();
        campoEdicaoMinutos = new javax.swing.JFormattedTextField();
        painelderolagem = new javax.swing.JScrollPane();
        tabelaAlunos = new javax.swing.JTable();
        planodefundo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        blocoDiasdaSemana.setAutoscrolls(true);
        blocoDiasdaSemana.setInheritsPopupMenu(true);
        blocoDiasdaSemana.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        blocoDiasdaSemana.add(barraDeRolagemDiasdaSemana, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 27, 120, 10));

        caixaSegunda.setText("Seg");
        blocoDiasdaSemana.add(caixaSegunda, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        caixaTerca.setText("Ter");
        blocoDiasdaSemana.add(caixaTerca, new org.netbeans.lib.awtextra.AbsoluteConstraints(46, 0, -1, -1));

        caixaQuarta.setText("Qua");
        blocoDiasdaSemana.add(caixaQuarta, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 0, -1, -1));

        caixaQuinta.setText("Qui");
        blocoDiasdaSemana.add(caixaQuinta, new org.netbeans.lib.awtextra.AbsoluteConstraints(138, 0, -1, -1));

        caixaSexta.setText("Sex");
        blocoDiasdaSemana.add(caixaSexta, new org.netbeans.lib.awtextra.AbsoluteConstraints(182, 0, -1, -1));

        caixaSabado.setText("Sáb");
        blocoDiasdaSemana.add(caixaSabado, new org.netbeans.lib.awtextra.AbsoluteConstraints(228, 0, -1, -1));

        caixaDomingo.setText("Dom");
        blocoDiasdaSemana.add(caixaDomingo, new org.netbeans.lib.awtextra.AbsoluteConstraints(274, 0, -1, -1));

        getContentPane().add(blocoDiasdaSemana, new org.netbeans.lib.awtextra.AbsoluteConstraints(606, 290, 120, 40));

        botaoRemoverTurmas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoRemover.png"))); // NOI18N
        botaoRemoverTurmas.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoHoverremover.png"))); // NOI18N
        botaoRemoverTurmas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoRemoverTurmasActionPerformed(evt);
            }
        });
        getContentPane().add(botaoRemoverTurmas, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 168, 205, 50));

        botaoEditarTurmas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoEditar.png"))); // NOI18N
        botaoEditarTurmas.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoEditarHover.png"))); // NOI18N
        botaoEditarTurmas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoEditarTurmasActionPerformed(evt);
            }
        });
        getContentPane().add(botaoEditarTurmas, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 168, 205, 50));

        botaoListarTurmas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/turmas/frequencia.png"))); // NOI18N
        botaoListarTurmas.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/turmas/frequenciaHover.png"))); // NOI18N
        botaoListarTurmas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoListarTurmasActionPerformed(evt);
            }
        });
        getContentPane().add(botaoListarTurmas, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 170, 205, 50));

        botaoAdicionarTurmas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoAdicionar.png"))); // NOI18N
        botaoAdicionarTurmas.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoHoverAdicionar.png"))); // NOI18N
        botaoAdicionarTurmas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoAdicionarTurmasActionPerformed(evt);
            }
        });
        getContentPane().add(botaoAdicionarTurmas, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 168, 205, 50));

        botaoFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaofechar.png"))); // NOI18N
        botaoFechar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoHoverfechar.png"))); // NOI18N
        botaoFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoFecharActionPerformed(evt);
            }
        });
        getContentPane().add(botaoFechar, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 40, 220, 50));

        botaobuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoBuscar.png"))); // NOI18N
        botaobuscar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoBuscarHover.png"))); // NOI18N
        botaobuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaobuscarActionPerformed(evt);
            }
        });
        getContentPane().add(botaobuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 250, 50, 40));

        campoDePesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                campoDePesquisaKeyPressed(evt);
            }
        });
        getContentPane().add(campoDePesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 320, 40));

        try {
            campoEdicaoHoras.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("## h")));
        } catch (java.text.ParseException erroHora) {
            erroHora.printStackTrace();}
        campoEdicaoHoras.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(campoEdicaoHoras, new org.netbeans.lib.awtextra.AbsoluteConstraints(736, 300, 40, -1));

        try {
            campoEdicaoMinutos.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("## min")));
        } catch (java.text.ParseException erroMinutos) {
            erroMinutos.printStackTrace();}
        getContentPane().add(campoEdicaoMinutos, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 300, 50, -1));

        tabelaAlunos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tabelaAlunos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CodBanco", "Nome", "Quant(Max)", "Quant Presente", "Dias da Semana", "Horário"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaAlunos.getTableHeader().setResizingAllowed(false);
        tabelaAlunos.getTableHeader().setReorderingAllowed(false);
        tabelaAlunos.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(campoMaximoAlunos));
        tabelaAlunos.setGridColor(new java.awt.Color(255, 255, 255));
        tabelaAlunos.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tabelaAlunos.setRowHeight(25);
        tabelaAlunos.setShowVerticalLines(false);
        tabelaAlunos.getTableHeader().setReorderingAllowed(false);
        tabelaAlunos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaAlunosMouseClicked(evt);
            }
        });
        tabelaAlunos.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                tabelaAlunosComponentHidden(evt);
            }
        });
        tabelaAlunos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tabelaAlunosKeyReleased(evt);
            }
        });
        painelderolagem.setViewportView(tabelaAlunos);

        getContentPane().add(painelderolagem, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, 811, 340));

        planodefundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/turmas/fundo.jpg"))); // NOI18N
        getContentPane().add(planodefundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoRemoverTurmasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoRemoverTurmasActionPerformed
        try {
            controller.removerTurma();
        } catch (SQLException ex) {
            Logger.getLogger(TurmasView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botaoRemoverTurmasActionPerformed

    private void botaoEditarTurmasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoEditarTurmasActionPerformed
        this.diasDaSemana();
        this.edicaoDeHorario();
        try {
            controller.editarTurmas();
        } catch (SQLException ex) {
            Logger.getLogger(TurmasView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botaoEditarTurmasActionPerformed

    private void botaoAdicionarTurmasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoAdicionarTurmasActionPerformed
        // TODO add your handling code here:
        TurmasAdicionar abrir=new TurmasAdicionar();
        abrir.setVisible(true);
    }//GEN-LAST:event_botaoAdicionarTurmasActionPerformed

    private void botaoFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFecharActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_botaoFecharActionPerformed

    private void tabelaAlunosComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tabelaAlunosComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelaAlunosComponentHidden

    private void botaoListarTurmasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoListarTurmasActionPerformed
        // TODO add your handling code here:
        turmasFrequencia abrir=new turmasFrequencia();
        abrir.setVisible(true);
    }//GEN-LAST:event_botaoListarTurmasActionPerformed

    private void barraDeRolagemDiasdaSemanaMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barraDeRolagemDiasdaSemanaMouseDragged

        if(this.getMousePosition()!=null){
            int posicaoX = this.getMousePosition().x-this.blocoDiasdaSemana.getX();
            if(posicaoX>=26&&posicaoX<=94){
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

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try{
          controller.listarTurmas();  
        }catch(SQLException erro){System.out.println("Erro ao listar");}
        
    }//GEN-LAST:event_formWindowOpened

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        desabilitarVisibilidadeComponente();
    }//GEN-LAST:event_formMouseClicked

    private void tabelaAlunosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaAlunosKeyReleased
        try {
            controller.selecionarTabela();
        } catch (SQLException ex) {
            Logger.getLogger(TurmasView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tabelaAlunosKeyReleased

    private void tabelaAlunosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaAlunosMouseClicked
        try {
            controller.selecionarTabela();
        } catch (SQLException ex) {
            Logger.getLogger(TurmasView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tabelaAlunosMouseClicked

    private void botaobuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaobuscarActionPerformed
        try {
            controller.buscarTurmas();
        } catch (Exception ex) {
            Logger.getLogger(TurmasView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botaobuscarActionPerformed

    private void campoDePesquisaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoDePesquisaKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            getRootPane().setDefaultButton(botaobuscar);
        }
    }//GEN-LAST:event_campoDePesquisaKeyPressed

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
            java.util.logging.Logger.getLogger(TurmasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TurmasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TurmasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TurmasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TurmasView().setVisible(true);
            }
        });
    
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollBar barraDeRolagemDiasdaSemana;
    private javax.swing.JLayeredPane blocoDiasdaSemana;
    private javax.swing.JButton botaoAdicionarTurmas;
    private javax.swing.JButton botaoEditarTurmas;
    private javax.swing.JButton botaoFechar;
    private javax.swing.JButton botaoListarTurmas;
    private javax.swing.JButton botaoRemoverTurmas;
    private javax.swing.JButton botaobuscar;
    private javax.swing.JCheckBox caixaDomingo;
    private javax.swing.JCheckBox caixaQuarta;
    private javax.swing.JCheckBox caixaQuinta;
    private javax.swing.JCheckBox caixaSabado;
    private javax.swing.JCheckBox caixaSegunda;
    private javax.swing.JCheckBox caixaSexta;
    private javax.swing.JCheckBox caixaTerca;
    private javax.swing.JTextField campoDePesquisa;
    private javax.swing.JFormattedTextField campoEdicaoHoras;
    private javax.swing.JFormattedTextField campoEdicaoMinutos;
    private javax.swing.JScrollPane painelderolagem;
    private javax.swing.JLabel planodefundo;
    private javax.swing.JTable tabelaAlunos;
    // End of variables declaration//GEN-END:variables

    private void moverCaixas(){
    int positionX = this.getMousePosition().x-this.blocoDiasdaSemana.getX();
    this.caixaSegunda.setLocation(30+47-positionX*(7/2), 0);
    this.caixaTerca.setLocation(76+47-positionX*(7/2), 0);
    this.caixaQuarta.setLocation(120+47-positionX*(7/2), 0);
    this.caixaQuinta.setLocation(168+47-positionX*(7/2), 0);
    this.caixaSexta.setLocation(212+47-positionX*(7/2), 0);
    this.caixaSabado.setLocation(258+47-positionX*(7/2), 0);
    this.caixaDomingo.setLocation(304+47-positionX*(7/2), 0);
}

private void moverCaixasClique(){
    int positionX = this.barraDeRolagemDiasdaSemana.getValue()*3;
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
        
        if(!this.campoEdicaoHoras.getText().replace("  h", "").equals("")){hora = this.campoEdicaoHoras.getText().replace(" h", "");}
        if(!this.campoEdicaoMinutos.getText().replace(" min", "").equals("")){minutos = this.campoEdicaoMinutos.getText().replace(" min", "");}
        if(!hora.equals("")&&!minutos.equals("")){this.campoHorario = hora+":"+minutos;}
        
    }
    
    public boolean testeValidacaoHorario(){
        boolean verificar = true;
        int horas = Integer.parseInt(this.campoEdicaoHoras.getText().replace(" h", ""));
        int minutos = Integer.parseInt(this.campoEdicaoMinutos.getText().replace(" min", ""));
        
        if((horas<0||horas>23)||(minutos<0||minutos>59)){
            verificar=false;
        }
        return verificar;
    }
    
    public void exibeMensagem(String mensagem) {
      JOptionPane.showMessageDialog(null, mensagem);
    }
    public JTextField getCampoDePesquisa() {
        return campoDePesquisa;
    }

    public JFormattedTextField getCampoEdicaoHoras() {
        return campoEdicaoHoras;
    }

    public JFormattedTextField getCampoEdicaoMinutos() {
        return campoEdicaoMinutos;
    }

    public JTable getTabelaAlunos() {
        return tabelaAlunos;
    }

    public JLayeredPane getBlocoDiasdaSemana() {
        return blocoDiasdaSemana;
    }
    
    public void habilitarVisibilidadeComponente(){
        this.campoEdicaoHoras.setVisible(true);
        this.campoEdicaoMinutos.setVisible(true);
        this.blocoDiasdaSemana.setVisible(true);
    }
    
    public void desabilitarVisibilidadeComponente(){
        this.campoEdicaoHoras.setVisible(false);
        this.campoEdicaoMinutos.setVisible(false);
        this.blocoDiasdaSemana.setVisible(false);
    }

    public JCheckBox getCaixaDomingo() {
        return caixaDomingo;
    }

    public JCheckBox getCaixaQuarta() {
        return caixaQuarta;
    }

    public JCheckBox getCaixaQuinta() {
        return caixaQuinta;
    }

    public JCheckBox getCaixaSabado() {
        return caixaSabado;
    }

    public JCheckBox getCaixaSegunda() {
        return caixaSegunda;
    }

    public JCheckBox getCaixaSexta() {
        return caixaSexta;
    }

    public JCheckBox getCaixaTerca() {
        return caixaTerca;
    }

    public String getDiasDaSemana() {
        return diasDaSemana;
    }

    public ArrayList<String> getDiasDaSemanaUnitarios() {
        return diasDaSemanaUnitarios;
    }

    public String getCampoHorario() {
        return campoHorario;
    }

    public int getNumeroTela() {
        return numeroTela;
    }
    
    
    
}
