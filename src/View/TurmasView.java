/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;


import Controller.Paineis.TurmasDetalhesController;
import Controller.TurmasController;
import View.Paineis.PainelAjuda;
import View.Paineis.TurmasDetalhes;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.TableColumn;

/**
 *
 * @author 55989
 */
public class TurmasView extends javax.swing.JDialog {
    private final java.awt.Frame parent;
    private final int numeroTela = 2;
    private final TurmasController controller;
    private final TurmasDetalhesController controllerDetalhes;
    private ArrayList <String> diasDaSemanaUnitarios;
    private final TurmasAdicionar telaTurmasAdicionar;
    private final turmasFrequencia telaTurmasFrequencia;
    private final PainelAjuda painelAjuda;
    private final TurmasDetalhes turmasDetalhes;

    /**
     * Creates new form Turmas
     * @param parent
     * @param modal
     */
    public TurmasView(java.awt.Frame parent, boolean modal)  {
        super(parent, modal);
        initComponents();
        this.parent = parent;
        telaTurmasAdicionar=new TurmasAdicionar(parent, false);
        telaTurmasFrequencia=new turmasFrequencia(parent, false);
        painelAjuda = new PainelAjuda(parent, false, this.getLocation().x+this.getSize().width+4, this.getLocation().y);
        turmasDetalhes = new TurmasDetalhes(this, parent, false);
        
        controller = new TurmasController(this);
        controllerDetalhes = new TurmasDetalhesController(this, turmasDetalhes);
        
        botaoAdicionarTurmas.setBackground(new Color(0,0,0,0));
        botaoFechar.setBackground(new Color(0,0,0,0));
        botaoAjuda.setBackground(new Color(0,0,0,0));
        botaoListarTurmas.setBackground(new Color(0,0,0,0));
        botaoRemoverTurmas.setBackground(new Color(0,0,0,0));
        botaobuscar.setBackground(new Color(0,0,0,0));
        this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/imagens/rehabi.png")).getImage());
        
        this.teclasDeAtalho();
        this.setarBotaoEditar(false, 0);
        this.tableResizing();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        botaoRemoverTurmas = new javax.swing.JButton();
        botaoListarTurmas = new javax.swing.JButton();
        botaoAdicionarTurmas = new javax.swing.JButton();
        botaoFechar = new javax.swing.JButton();
        botaobuscar = new javax.swing.JButton();
        campoDePesquisa = new javax.swing.JTextField();
        comboSituacao = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaTurmas = new javax.swing.JTable();
        botaoAjuda = new javax.swing.JButton();
        botaoEditar = new javax.swing.JButton();
        planodefundo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
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

        botaoRemoverTurmas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoRemover.png"))); // NOI18N
        botaoRemoverTurmas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoRemoverTurmas.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoHoverremover.png"))); // NOI18N
        botaoRemoverTurmas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoRemoverTurmasActionPerformed(evt);
            }
        });
        getContentPane().add(botaoRemoverTurmas, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 168, 205, 50));

        botaoListarTurmas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/turmas/frequencia.png"))); // NOI18N
        botaoListarTurmas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoListarTurmas.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/turmas/frequenciaHover.png"))); // NOI18N
        botaoListarTurmas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoListarTurmasActionPerformed(evt);
            }
        });
        getContentPane().add(botaoListarTurmas, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 170, 205, 50));

        botaoAdicionarTurmas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoAdicionar.png"))); // NOI18N
        botaoAdicionarTurmas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoAdicionarTurmas.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoHoverAdicionar.png"))); // NOI18N
        botaoAdicionarTurmas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoAdicionarTurmasActionPerformed(evt);
            }
        });
        getContentPane().add(botaoAdicionarTurmas, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 168, 205, 50));

        botaoFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaofechar.png"))); // NOI18N
        botaoFechar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
        getContentPane().add(botaobuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 240, 50, 40));

        campoDePesquisa.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                campoDePesquisaFocusLost(evt);
            }
        });
        campoDePesquisa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                campoDePesquisaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                campoDePesquisaMouseExited(evt);
            }
        });
        campoDePesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                campoDePesquisaKeyPressed(evt);
            }
        });
        getContentPane().add(campoDePesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 60, 40));

        comboSituacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Abertas", "Encerradas" }));
        getContentPane().add(comboSituacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 210, 30));

        tabelaTurmas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CodBanco", "Nome", "Situação"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaTurmas.setGridColor(new java.awt.Color(255, 255, 255));
        tabelaTurmas.getTableHeader().setReorderingAllowed(false);
        tabelaTurmas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaTurmasMouseClicked(evt);
            }
        });
        tabelaTurmas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tabelaTurmasKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaTurmas);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, 811, 340));

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
        getContentPane().add(botaoAjuda, new org.netbeans.lib.awtextra.AbsoluteConstraints(831, 10, 30, 30));

        botaoEditar.setText("...");
        botaoEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoEditarActionPerformed(evt);
            }
        });
        getContentPane().add(botaoEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 354, 20, 20));

        planodefundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/turmas/fundo.jpg"))); // NOI18N
        getContentPane().add(planodefundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoRemoverTurmasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoRemoverTurmasActionPerformed
        if(turmasDetalhes.isVisible()){
            controllerDetalhes.sairTela();
        }
        controller.removerTurma();
    }//GEN-LAST:event_botaoRemoverTurmasActionPerformed

    private void botaoAdicionarTurmasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoAdicionarTurmasActionPerformed
        if(turmasDetalhes.isVisible()){
            controllerDetalhes.sairTela();
        }
        telaTurmasAdicionar.setModal(true);
        telaTurmasAdicionar.setLocationRelativeTo(null);         
        telaTurmasAdicionar.setVisible(true);
    }//GEN-LAST:event_botaoAdicionarTurmasActionPerformed

    private void botaoFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFecharActionPerformed
        // TODO add your handling code here:
        if(turmasDetalhes.isVisible()){
            turmasDetalhes.dispose();
        }
        this.dispose();
    }//GEN-LAST:event_botaoFecharActionPerformed

    private void botaoListarTurmasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoListarTurmasActionPerformed
        // TODO add your handling code here:
        if(turmasDetalhes.isVisible()){
            controllerDetalhes.sairTela();
        }
        telaTurmasFrequencia.setModal(true);
        telaTurmasFrequencia.setLocationRelativeTo(null);         
        telaTurmasFrequencia.setVisible(true);
    }//GEN-LAST:event_botaoListarTurmasActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        controller.listarTurmas();
    }//GEN-LAST:event_formWindowOpened

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked

    }//GEN-LAST:event_formMouseClicked

    private void botaobuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaobuscarActionPerformed
        controller.buscarTurmas();
    }//GEN-LAST:event_botaobuscarActionPerformed

    private void campoDePesquisaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoDePesquisaKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            getRootPane().setDefaultButton(botaobuscar);
        }
    }//GEN-LAST:event_campoDePesquisaKeyPressed

    private void tabelaTurmasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaTurmasMouseClicked
        controllerDetalhes.releasedTable();
        if(tabelaTurmas.getSelectedRow()>-1){
            setarBotaoEditar(true, evt.getYOnScreen()); 
        }else{
            setarBotaoEditar(false, 0);
        }
    }//GEN-LAST:event_tabelaTurmasMouseClicked

    private void tabelaTurmasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaTurmasKeyReleased
        controllerDetalhes.releasedTable();
    }//GEN-LAST:event_tabelaTurmasKeyReleased

    private void botaoAjudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoAjudaActionPerformed
        controller.ajuda();
    }//GEN-LAST:event_botaoAjudaActionPerformed

    private void botaoEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoEditarActionPerformed
        if(!turmasDetalhes.isVisible()){
            controllerDetalhes.selecionarTabela();
        }
    }//GEN-LAST:event_botaoEditarActionPerformed

    private void campoDePesquisaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_campoDePesquisaMouseExited
        aumentarPesquisa(false);
    }//GEN-LAST:event_campoDePesquisaMouseExited

    private void campoDePesquisaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_campoDePesquisaMouseEntered
        aumentarPesquisa(true);
    }//GEN-LAST:event_campoDePesquisaMouseEntered

    private void campoDePesquisaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_campoDePesquisaFocusLost
        aumentarPesquisa(false);
    }//GEN-LAST:event_campoDePesquisaFocusLost
   
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
                TurmasView dialog = new TurmasView(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton botaoAdicionarTurmas;
    private javax.swing.JButton botaoAjuda;
    private javax.swing.JButton botaoEditar;
    private javax.swing.JButton botaoFechar;
    private javax.swing.JButton botaoListarTurmas;
    private javax.swing.JButton botaoRemoverTurmas;
    private javax.swing.JButton botaobuscar;
    private javax.swing.JTextField campoDePesquisa;
    private javax.swing.JComboBox<String> comboSituacao;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel planodefundo;
    private javax.swing.JTable tabelaTurmas;
    // End of variables declaration//GEN-END:variables

    
    public void exibeMensagem(String mensagem) {
      JOptionPane.showMessageDialog(null, mensagem);
    }
    
    public void mensagemCritica(String mensagem, String titulo){
       JOptionPane.showMessageDialog(null, mensagem, titulo, JOptionPane.WARNING_MESSAGE);
    }
    public JTextField getCampoDePesquisa() {
        return campoDePesquisa;
    }

    public JTable getTabelaTurmas() {
        return tabelaTurmas;
    }

    public ArrayList<String> getDiasDaSemanaUnitarios() {
        return diasDaSemanaUnitarios;
    }

    public int getNumeroTela() {
        return numeroTela;
    }

    public JComboBox<String> getComboSituacao() {
        return comboSituacao;
    }

    public PainelAjuda getPainelAjuda() {
        return painelAjuda;
    }

    public Frame getParent() {
        return parent;
    }
    
    private void tableResizing(){
        //Definição de Colunas
        TableColumn coluna0 = tabelaTurmas.getColumn("CodBanco");
        TableColumn coluna1 = tabelaTurmas.getColumn("Nome");
        TableColumn coluna2 = tabelaTurmas.getColumn("Situação");
        
        //Tamanhos de colunas
        int widhtTabela = tabelaTurmas.getWidth();
        int codBanco = 70;
        int situacao = 70;
        int nome = widhtTabela-codBanco-situacao;

        
        //Definição de Tamanho Preferencial (Total Tabela = 225)
        coluna0.setMaxWidth(codBanco);
        coluna1.setMaxWidth(nome);
        coluna2.setMaxWidth(situacao);
    }
    
    
    private void setarBotaoEditar(boolean visible, int y){
        
        if(visible){
            int cont=370;
            while(cont<=670){
                if(y>=cont&&y<cont+15){
                    botaoEditar.setLocation(botaoEditar.getX(), cont-16);
                    botaoEditar.setVisible(visible);
                    break;
                }
                cont+=15;
            }  
        }
        else{
            botaoEditar.setVisible(false);
        }
    }
    
    private void aumentarPesquisa(boolean aumentar){
        int campoX = campoDePesquisa.getX();
        int campoY = campoDePesquisa.getY();
        int botaoX = botaobuscar.getX();
        int botaoY = botaobuscar.getY();
        int largCampo = campoDePesquisa.getWidth();
        int altCampo = campoDePesquisa.getHeight();
        
        if(aumentar){
            if(campoDePesquisa.getSize().width<260){
                campoDePesquisa.setSize(largCampo+200, altCampo);
                campoDePesquisa.setLocation(campoX, campoY);
                botaobuscar.setLocation(botaoX+200, botaoY);
            }
        }
        else{
            if(campoDePesquisa.getText().trim().equals("")){
                campoDePesquisa.setSize(largCampo-200, altCampo);
                campoDePesquisa.setLocation(campoX, campoY);
                botaobuscar.setLocation(botaoX-200, botaoY);
            }
            
        }
    }
    
    private void teclasDeAtalho() {
        JRootPane meurootpane = getRootPane();
        meurootpane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "ESCAPE");
        meurootpane.getRootPane().getActionMap().put("ESCAPE", new AbstractAction("ESCAPE") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(turmasDetalhes.isVisible()){
                    turmasDetalhes.dispose();
                }
                dispose();
            }
        });
        
        //Atualizar tabela
        meurootpane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "F5");
        meurootpane.getRootPane().getActionMap().put("F5", new AbstractAction("F5") {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.listarTurmas();
            }
        });
        
        //Remover Turma
        meurootpane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "DELETE");
        meurootpane.getRootPane().getActionMap().put("DELETE", new AbstractAction("DELETE") {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.removerTurma();
            }
        });
        
        this.conjuntoTeclasAtalho(meurootpane);
    }
    
    private void conjuntoTeclasAtalho(JRootPane meurootpane){
        //Editar Turma
        meurootpane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ctrl E"), "EDITARTURMA");
        meurootpane.getRootPane().getActionMap().put("EDITARTURMA", new AbstractAction("EDITARTURMA") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(turmasDetalhes.isVisible()){
                    controllerDetalhes.sairTela();
                }
                controller.editarTurma();
            }
        });
        
        meurootpane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ctrl shift E"), "EDITARVARIASTURMA");
        meurootpane.getRootPane().getActionMap().put("EDITARVARIASTURMA", new AbstractAction("EDITARVARIASTURMA") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(turmasDetalhes.isVisible()){
                    controllerDetalhes.sairTela();
                }
                controller.editarVariasTurmas();
            }
        });
    
        
        //Encerrar ou Reabrir Turma
        meurootpane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ctrl F"), "ENCERRARREABRIR");
        meurootpane.getRootPane().getActionMap().put("ENCERRARREABRIR", new AbstractAction("ENCERRARREABRIR") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(turmasDetalhes.isVisible()){
                    controllerDetalhes.sairTela();
                }
                controller.encerrarReabrirTurma();
            }
        });
        
        //fechar programa
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
                if(turmasDetalhes.isVisible()){
                    controllerDetalhes.sairTela();
                }
                telaTurmasAdicionar.setModal(true);
                telaTurmasAdicionar.setLocationRelativeTo(null);         
                telaTurmasAdicionar.setVisible(true);
            }
        });
    }
}
