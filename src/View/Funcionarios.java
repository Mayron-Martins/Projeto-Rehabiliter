/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.FuncionariosController;
import Controller.auxiliar.JMoneyField;
import Controller.auxiliar.LogsSystem;
import View.Paineis.FuncionariosPermissoes;
import View.Paineis.PainelAjuda;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/**
 *
 * @author 55989
 */
public class Funcionarios extends javax.swing.JDialog {
    private final java.awt.Frame parent; 
    private final FuncionariosController controller;
    private final FuncionariosAdicionar telaAdicionarFuncionarios;
    private final FrequenciaFuncionariosView telaFrequencia;
    private final PainelAjuda painelAjuda;
    private final FuncionariosPermissoes painelPermissoes;
    

    /**
     * Creates new form Funcionarios
     * @param parent
     * @param modal
     */
    public Funcionarios(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        this.parent = parent;
        telaAdicionarFuncionarios = new FuncionariosAdicionar(parent, false);
        telaFrequencia = new FrequenciaFuncionariosView(parent, false);
        painelAjuda = new PainelAjuda(parent, false, this.getLocation().x+this.getSize().width+4, this.getLocation().y);
        this.painelPermissoes = new FuncionariosPermissoes(parent, false, this);
        
        controller = new FuncionariosController(this, this.painelPermissoes);
        botaobuscar1.setBackground(new Color(0,0,0,0));
        botaoFechar1.setBackground(new Color(0,0,0,0));
        botaoAdicionar1.setBackground(new Color(0,0,0,0));
        botaoRemover1.setBackground(new Color(0,0,0,0));
        botaoEditar1.setBackground(new Color(0,0,0,0));
        botaoListar1.setBackground(new Color(0,0,0,0));
        tabelaFuncionarios.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabelaFuncionarios.getTableHeader().setOpaque(false);
        tabelaFuncionarios.getTableHeader().setBackground(new Color(0,0,0));
        tabelaFuncionarios.getTableHeader().setForeground(new Color(0,0,0));
        tabelaFuncionarios.setRowHeight(25);
        btnRelatorios.setBackground(new Color(0,0,0,0));
        this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/imagens/rehabi.png")).getImage());
        botaoPermissoes.setBackground(new Color(0,0,0,0));
        botaoAjuda.setBackground(new Color(0,0,0,0));
        botaoPermissoes.setVisible(false);
        this.teclasDeAtalho();
        this.setarComponentesTabela();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        botaoFechar1 = new javax.swing.JButton();
        botaoAdicionar1 = new javax.swing.JButton();
        botaoRemover1 = new javax.swing.JButton();
        botaoEditar1 = new javax.swing.JButton();
        botaoListar1 = new javax.swing.JButton();
        comboListar = new javax.swing.JComboBox<>();
        campoBuscar = new javax.swing.JTextField();
        botaobuscar1 = new javax.swing.JButton();
        painelderolagem1 = new javax.swing.JScrollPane();
        tabelaFuncionarios = new javax.swing.JTable();
        btnRelatorios = new javax.swing.JButton();
        botaoPermissoes = new javax.swing.JButton();
        botaoAjuda = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        botaoFechar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaofechar.png"))); // NOI18N
        botaoFechar1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoFechar1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoHoverfechar.png"))); // NOI18N
        botaoFechar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoFechar1ActionPerformed(evt);
            }
        });
        getContentPane().add(botaoFechar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 40, 220, 50));

        botaoAdicionar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoAdicionar.png"))); // NOI18N
        botaoAdicionar1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoAdicionar1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoHoverAdicionar.png"))); // NOI18N
        botaoAdicionar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoAdicionar1ActionPerformed(evt);
            }
        });
        getContentPane().add(botaoAdicionar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 168, 205, 50));

        botaoRemover1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoRemover.png"))); // NOI18N
        botaoRemover1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoRemover1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoHoverremover.png"))); // NOI18N
        botaoRemover1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoRemover1ActionPerformed(evt);
            }
        });
        getContentPane().add(botaoRemover1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 168, 205, 50));

        botaoEditar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoEditar.png"))); // NOI18N
        botaoEditar1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoEditar1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoEditarHover.png"))); // NOI18N
        botaoEditar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoEditar1ActionPerformed(evt);
            }
        });
        getContentPane().add(botaoEditar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 168, 205, 50));

        botaoListar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoListar.png"))); // NOI18N
        botaoListar1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoListar1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoHoverListar.png"))); // NOI18N
        botaoListar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoListar1ActionPerformed(evt);
            }
        });
        getContentPane().add(botaoListar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 170, 205, 50));

        comboListar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Contratados", "Desvinculados", "Aniversariantes" }));
        getContentPane().add(comboListar, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 230, 170, 30));

        campoBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                campoBuscarKeyPressed(evt);
            }
        });
        getContentPane().add(campoBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 260, 320, 40));

        botaobuscar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoBuscar.png"))); // NOI18N
        botaobuscar1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoBuscarHover.png"))); // NOI18N
        botaobuscar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaobuscar1ActionPerformed(evt);
            }
        });
        getContentPane().add(botaobuscar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 260, 50, 40));

        tabelaFuncionarios.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tabelaFuncionarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CodBanco", "Nome", "CPF", "Telefone", "Celular", "Cargo", "Remuneração", "Situação"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaFuncionarios.setFocusable(false);
        tabelaFuncionarios.setGridColor(new java.awt.Color(255, 255, 255));
        tabelaFuncionarios.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tabelaFuncionarios.setRowHeight(25);
        tabelaFuncionarios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelaFuncionarios.setShowVerticalLines(false);
        tabelaFuncionarios.getTableHeader().setReorderingAllowed(false);
        tabelaFuncionarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaFuncionariosMouseClicked(evt);
            }
        });
        tabelaFuncionarios.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                tabelaFuncionariosComponentHidden(evt);
            }
        });
        tabelaFuncionarios.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tabelaFuncionariosKeyReleased(evt);
            }
        });
        painelderolagem1.setViewportView(tabelaFuncionarios);

        getContentPane().add(painelderolagem1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, 811, 340));

        btnRelatorios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/funcionarios/btnrelatorios.png"))); // NOI18N
        btnRelatorios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRelatorios.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/funcionarios/btnrelatoriosHover.png"))); // NOI18N
        btnRelatorios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRelatoriosActionPerformed(evt);
            }
        });
        getContentPane().add(btnRelatorios, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 210, 40));

        botaoPermissoes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/imagensparaseremtrocadas/btnPermissoes2.png"))); // NOI18N
        botaoPermissoes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoPermissoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoPermissoesActionPerformed(evt);
            }
        });
        getContentPane().add(botaoPermissoes, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 306, 130, 20));

        botaoAjuda.setBackground(new java.awt.Color(255, 255, 255));
        botaoAjuda.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        botaoAjuda.setForeground(new java.awt.Color(255, 255, 255));
        botaoAjuda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/imagensparaseremtrocadas/btnDuvidas.png"))); // NOI18N
        botaoAjuda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoAjuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoAjudaActionPerformed(evt);
            }
        });
        getContentPane().add(botaoAjuda, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 30, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/funcionarios/Sem título-1.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoFechar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFechar1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_botaoFechar1ActionPerformed

    private void botaoAdicionar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoAdicionar1ActionPerformed
        // TODO add your handling code here:
        telaAdicionarFuncionarios.setModal(true);
        telaAdicionarFuncionarios.setLocationRelativeTo(null);
        telaAdicionarFuncionarios.setVisible(true);
    }//GEN-LAST:event_botaoAdicionar1ActionPerformed

    private void botaoRemover1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoRemover1ActionPerformed
    controller.removerFuncionario();
    }//GEN-LAST:event_botaoRemover1ActionPerformed

    private void botaoEditar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoEditar1ActionPerformed
        controller.editarFuncionarios();

    }//GEN-LAST:event_botaoEditar1ActionPerformed

    private void tabelaFuncionariosComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tabelaFuncionariosComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelaFuncionariosComponentHidden

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        controller.listarFuncionarios();
    }//GEN-LAST:event_formWindowOpened

    private void botaoListar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoListar1ActionPerformed
        controller.listarFuncionarios();
    }//GEN-LAST:event_botaoListar1ActionPerformed

    private void botaobuscar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaobuscar1ActionPerformed
        controller.buscarFuncionarios();
    }//GEN-LAST:event_botaobuscar1ActionPerformed

    private void campoBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoBuscarKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            getRootPane().setDefaultButton(botaobuscar1);
        }
    }//GEN-LAST:event_campoBuscarKeyPressed

    private void btnRelatoriosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRelatoriosActionPerformed
        telaFrequencia.setModal(true);
        telaFrequencia.setLocationRelativeTo(null);
        telaFrequencia.setVisible(true);
    }//GEN-LAST:event_btnRelatoriosActionPerformed

    private void botaoPermissoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoPermissoesActionPerformed
        controller.setarPermissoes();
    }//GEN-LAST:event_botaoPermissoesActionPerformed

    private void botaoAjudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoAjudaActionPerformed
        controller.ajuda();
    }//GEN-LAST:event_botaoAjudaActionPerformed

    private void tabelaFuncionariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaFuncionariosMouseClicked
        controller.selecionarTabela();
    }//GEN-LAST:event_tabelaFuncionariosMouseClicked

    private void tabelaFuncionariosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaFuncionariosKeyReleased
        controller.selecionarTabela();
    }//GEN-LAST:event_tabelaFuncionariosKeyReleased

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
            java.util.logging.Logger.getLogger(Funcionarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Funcionarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Funcionarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Funcionarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Funcionarios dialog = new Funcionarios(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton botaoAdicionar1;
    private javax.swing.JButton botaoAjuda;
    private javax.swing.JButton botaoEditar1;
    private javax.swing.JButton botaoFechar1;
    private javax.swing.JButton botaoListar1;
    private javax.swing.JButton botaoPermissoes;
    private javax.swing.JButton botaoRemover1;
    private javax.swing.JButton botaobuscar1;
    private javax.swing.JButton btnRelatorios;
    private javax.swing.JTextField campoBuscar;
    private javax.swing.JComboBox<String> comboListar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane painelderolagem1;
    private javax.swing.JTable tabelaFuncionarios;
    // End of variables declaration//GEN-END:variables

    
    public void exibeMensagem(String mensagem) {
      JOptionPane.showMessageDialog(null, mensagem);
    }


    public JTable getTabelaFuncionarios() {
        return tabelaFuncionarios;
    }
    

    public JTextField getCampoBuscar() {
        return campoBuscar;
    }

    public JComboBox<String> getComboListar() {
        return comboListar;
    }

    public JButton getBotaoPermissoes() {
        return botaoPermissoes;
    }

    public Frame getParent() {
        return parent;
    }

    public PainelAjuda getPainelAjuda() {
        return painelAjuda;
    }
    
    
    

    public void teclasDeAtalho() {
        //Sair da Tela
        JRootPane meurootpane = getRootPane();
        meurootpane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "ESCAPE");
        meurootpane.getRootPane().getActionMap().put("ESCAPE", new AbstractAction("ESCAPE") {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        //Atualizar Tabela
        meurootpane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "F5");
        meurootpane.getRootPane().getActionMap().put("F5", new AbstractAction("F5") {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.listarFuncionarios();
            }
        });
        
        //Deletar Funcionário
        meurootpane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "DELETE");
        meurootpane.getRootPane().getActionMap().put("DELETE", new AbstractAction("DELETE") {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.removerFuncionario();
            }
        });
        
        this.conjuntoTeclasAtalho(meurootpane);
    }
    
    private void conjuntoTeclasAtalho(JRootPane meurootpane){
        //editar Funcionário
        meurootpane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ctrl E"), "EDITARFUNCIONARIO");
        meurootpane.getRootPane().getActionMap().put("EDITARFUNCIONARIO", new AbstractAction("EDITARFUNCIONARIO") {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.editarFuncionarios();
            }
        });
        
        //Encerrar Contrato
        meurootpane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ctrl F"), "ENCERRARREABRIR");
        meurootpane.getRootPane().getActionMap().put("ENCERRARREABRIR", new AbstractAction("ENCERRARREABRIR") {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.desvincularContratarFuncionario();
            }
        });
        
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
        
        //Adicionar Novo
        meurootpane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ctrl N"), "NOVO");
        meurootpane.getRootPane().getActionMap().put("NOVO", new AbstractAction("NOVO") {
            @Override
            public void actionPerformed(ActionEvent e) {
                telaAdicionarFuncionarios.setModal(true);
                telaAdicionarFuncionarios.setLocationRelativeTo(null);
                telaAdicionarFuncionarios.setVisible(true); 
            }
        });
    }
    
    private void setarComponentesTabela(){
        JFormattedTextField cpf = new JFormattedTextField();
        JFormattedTextField telefone = new JFormattedTextField();
        JFormattedTextField celular = new JFormattedTextField();
            
        try {
            //Transformação de variáveis
            cpf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
            telefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) ####-####")));
            celular.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) # ####-####")));
            
        } catch (ParseException ex) {
            gerarLog(ex);
        }
        
        tabelaFuncionarios.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(cpf));
        tabelaFuncionarios.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(telefone));
        tabelaFuncionarios.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(celular));
        tabelaFuncionarios.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(new  JMoneyField()));
    } 
    
    private void gerarLog(Throwable erro){
        LogsSystem gerarLog = new LogsSystem();
        gerarLog.gravarErro(erro);
        gerarLog.close();
    }
}
