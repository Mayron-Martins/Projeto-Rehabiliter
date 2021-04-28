/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Paineis.ServicosDetalhesController;
import Controller.ServicosController;
import View.Paineis.PainelAjuda;
import View.Paineis.ServicosDetalhes;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
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
public class ServicosView extends javax.swing.JDialog {
    private final java.awt.Frame parent;
    private final int numeroTela = 4;
    private final ServicosController controller;
    private final ServicosDetalhesController controllerDetalhes;
    private final ServicosAdicionar telaServicosAdicionar;
    private final PainelAjuda painelAjuda;
    private final ServicosDetalhes servicosDetalhes;

    /**
     * Creates new form Servicos
     * @param parent
     * @param modal
     */
    public ServicosView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        this.parent = parent;
        painelAjuda = new PainelAjuda(parent, false, this.getLocation().x+this.getSize().width+4, this.getLocation().y);
        telaServicosAdicionar=new ServicosAdicionar(parent, false);
        servicosDetalhes = new ServicosDetalhes(this, parent, false);
        
        controller = new ServicosController(this);
        controllerDetalhes = new ServicosDetalhesController(this, servicosDetalhes);
        btnAdicionar.setBackground(new Color(0,0,0,0));
        btnRemover.setBackground(new Color(0,0,0,0));
        btnFechar.setBackground(new Color(0,0,0,0));
        botaobuscar.setBackground(new Color(0,0,0,0));
        this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/imagens/rehabi.png")).getImage());
        
        this.teclasDeAtalho();
        setarBotaoEditar(false, 0);
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

        btnRemover = new javax.swing.JButton();
        btnAdicionar = new javax.swing.JButton();
        btnFechar = new javax.swing.JButton();
        painelderolagem = new javax.swing.JScrollPane();
        tabelaServicos = new javax.swing.JTable();
        comboSituacao = new javax.swing.JComboBox<>();
        botaoAjuda = new javax.swing.JButton();
        campoDePesquisa = new javax.swing.JTextField();
        botaobuscar = new javax.swing.JButton();
        botaoEditar = new javax.swing.JButton();
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

        btnRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/servicos/btnRemover.png"))); // NOI18N
        btnRemover.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRemover.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/servicos/btnRemoverHover.png"))); // NOI18N
        btnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverActionPerformed(evt);
            }
        });
        getContentPane().add(btnRemover, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 210, 210, 60));

        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/servicos/btnAdicionar.png"))); // NOI18N
        btnAdicionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdicionar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/servicos/btnAdicionarHover.png"))); // NOI18N
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });
        getContentPane().add(btnAdicionar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 210, 60));

        btnFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaofechar.png"))); // NOI18N
        btnFechar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFechar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoHoverfechar.png"))); // NOI18N
        btnFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharActionPerformed(evt);
            }
        });
        getContentPane().add(btnFechar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 230, 60));

        tabelaServicos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CodBanco", "Nome", "Período", "Situação"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaServicos.setFocusable(false);
        tabelaServicos.setGridColor(new java.awt.Color(255, 255, 255));
        tabelaServicos.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tabelaServicos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelaServicos.setShowVerticalLines(false);
        tabelaServicos.getTableHeader().setReorderingAllowed(false);
        tabelaServicos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaServicosMouseClicked(evt);
            }
        });
        tabelaServicos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tabelaServicosKeyReleased(evt);
            }
        });
        painelderolagem.setViewportView(tabelaServicos);

        getContentPane().add(painelderolagem, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, 811, 340));

        comboSituacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Abertos", "Encerrados" }));
        comboSituacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboSituacaoActionPerformed(evt);
            }
        });
        getContentPane().add(comboSituacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 210, 30));

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
        getContentPane().add(campoDePesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 230, 60, 40));

        botaobuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoBuscar.png"))); // NOI18N
        botaobuscar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoBuscarHover.png"))); // NOI18N
        botaobuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaobuscarActionPerformed(evt);
            }
        });
        getContentPane().add(botaobuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 230, 50, 40));

        botaoEditar.setText("...");
        botaoEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoEditarActionPerformed(evt);
            }
        });
        getContentPane().add(botaoEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 354, 20, 20));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/servicos/telafundo.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed
        if(servicosDetalhes.isVisible()){
            servicosDetalhes.dispose();
        }
        this.dispose();
    }//GEN-LAST:event_btnFecharActionPerformed

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        // TODO add your handling code here:
        if(servicosDetalhes.isVisible()){
            controllerDetalhes.sairTela();
        }
        telaServicosAdicionar.setModal(true);
        telaServicosAdicionar.setLocationRelativeTo(null);
        telaServicosAdicionar.setVisible(true);
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void tabelaServicosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaServicosMouseClicked
        controllerDetalhes.releasedTable();
        if(tabelaServicos.getSelectedRow()>-1){
            setarBotaoEditar(true, evt.getYOnScreen()); 
        }else{
            setarBotaoEditar(false, 0);
        }
    }//GEN-LAST:event_tabelaServicosMouseClicked

    private void tabelaServicosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaServicosKeyReleased
        controllerDetalhes.releasedTable();
    }//GEN-LAST:event_tabelaServicosKeyReleased

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        if(servicosDetalhes.isVisible()){
            controllerDetalhes.sairTela();
        }
        controller.removerServico();
    }//GEN-LAST:event_btnRemoverActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        controller.listarServicos();
    }//GEN-LAST:event_formWindowOpened

    private void botaoAjudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoAjudaActionPerformed
        controller.ajuda();
    }//GEN-LAST:event_botaoAjudaActionPerformed

    private void campoDePesquisaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_campoDePesquisaMouseEntered
        aumentarPesquisa(true);
    }//GEN-LAST:event_campoDePesquisaMouseEntered

    private void campoDePesquisaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_campoDePesquisaMouseExited
        aumentarPesquisa(false);
    }//GEN-LAST:event_campoDePesquisaMouseExited

    private void campoDePesquisaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoDePesquisaKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            getRootPane().setDefaultButton(botaobuscar);
        }
    }//GEN-LAST:event_campoDePesquisaKeyPressed

    private void botaobuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaobuscarActionPerformed
        controller.buscarServico();
    }//GEN-LAST:event_botaobuscarActionPerformed

    private void botaoEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoEditarActionPerformed
        if(!servicosDetalhes.isVisible()){
            controllerDetalhes.selecionarTabela();
        }
    }//GEN-LAST:event_botaoEditarActionPerformed

    private void comboSituacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboSituacaoActionPerformed
        controller.listarServicos();
    }//GEN-LAST:event_comboSituacaoActionPerformed

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
            java.util.logging.Logger.getLogger(ServicosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ServicosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ServicosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServicosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ServicosView dialog = new ServicosView(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton botaoAjuda;
    private javax.swing.JButton botaoEditar;
    private javax.swing.JButton botaobuscar;
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnFechar;
    private javax.swing.JButton btnRemover;
    private javax.swing.JTextField campoDePesquisa;
    private javax.swing.JComboBox<String> comboSituacao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane painelderolagem;
    private javax.swing.JTable tabelaServicos;
    // End of variables declaration//GEN-END:variables

    public void exibeMensagem(String mensagem) {
      JOptionPane.showMessageDialog(null, mensagem);
    }
    
    public void mensagemCritica(String mensagem, String titulo){
       JOptionPane.showMessageDialog(null, mensagem, titulo, JOptionPane.WARNING_MESSAGE);
    }

    public JTable getTabelaServicos() {
        return tabelaServicos;
    }

    public int getNumeroTela() {
        return numeroTela;
    }

    public JComboBox<String> getComboSituacao() {
        return comboSituacao;
    }

    public JTextField getCampoDePesquisa() {
        return campoDePesquisa;
    }
    

    public Frame getParent() {
        return parent;
    }

    public PainelAjuda getPainelAjuda() {
        return painelAjuda;
    }
    
    private void tableResizing(){
        //Definição de Colunas
        TableColumn coluna0 = tabelaServicos.getColumn("CodBanco");
        TableColumn coluna1 = tabelaServicos.getColumn("Nome");
        TableColumn coluna2 = tabelaServicos.getColumn("Período");
        TableColumn coluna3 = tabelaServicos.getColumn("Situação");
        
        //Tamanhos de colunas
        int widhtTabela = tabelaServicos.getWidth();
        int codBanco = 70;
        int periodo = 120;
        int situacao = 70;
        int nome = widhtTabela-codBanco-situacao;

        
        //Definição de Tamanho Preferencial (Total Tabela = 225)
        coluna0.setMaxWidth(codBanco);
        coluna1.setMaxWidth(nome);
        coluna2.setMaxWidth(periodo);
        coluna3.setMaxWidth(situacao);
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
        int largCampo = campoDePesquisa.getWidth();
        int altCampo = campoDePesquisa.getHeight();
        
        if(aumentar){
            if(campoDePesquisa.getSize().width<260){
                campoDePesquisa.setSize(largCampo+200, altCampo);
                campoDePesquisa.setLocation(campoX-200, campoY);
            }
            
        }
        else{
            if(campoDePesquisa.getText().trim().equals("")){
                campoDePesquisa.setSize(largCampo-200, altCampo);
                campoDePesquisa.setLocation(campoX+200, campoY);
            }
        }
    }
    
    private void teclasDeAtalho() {
        JRootPane meurootpane = getRootPane();
        //Sair com Esc
        meurootpane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "ESCAPE");
        meurootpane.getRootPane().getActionMap().put("ESCAPE", new AbstractAction("ESCAPE") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(servicosDetalhes.isVisible()){
                    controllerDetalhes.sairTela();
                }
                dispose();
            }
        });
        
        //Atualizar tabela
        meurootpane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "F5");
        meurootpane.getRootPane().getActionMap().put("F5", new AbstractAction("F5") {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.listarServicos();
            }
        });
        
        //Remover Turma
        meurootpane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "DELETE");
        meurootpane.getRootPane().getActionMap().put("DELETE", new AbstractAction("DELETE") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(servicosDetalhes.isVisible()){
                    controllerDetalhes.sairTela();
                }
                controller.removerServico();
            }
        });
        
        this.conjuntoTeclasAtalho(meurootpane);
    }
    
    private void conjuntoTeclasAtalho(JRootPane meurootpane){
        //Editar Serviço
        meurootpane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ctrl E"), "EDITARSERVICO");
        meurootpane.getRootPane().getActionMap().put("EDITARSERVICO", new AbstractAction("EDITARSERVICO") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(servicosDetalhes.isVisible()){
                    controllerDetalhes.sairTela();
                }
                controller.editarServico();
            }
        });
        
        //Editar Vários Serviços
        meurootpane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ctrl shift E"), "EDITARVARIOSSERVICOS");
        meurootpane.getRootPane().getActionMap().put("EDITARVARIOSSERVICOS", new AbstractAction("EDITARVARIOSSERVICOS") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(servicosDetalhes.isVisible()){
                    controllerDetalhes.sairTela();
                }
                controller.editarVariosServicos();
            }
        });
        
        //Encerrar ou Reabrir Serviço
        meurootpane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ctrl F"), "ENCERRARREABRIR");
        meurootpane.getRootPane().getActionMap().put("ENCERRARREABRIR", new AbstractAction("ENCERRARREABRIR") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(servicosDetalhes.isVisible()){
                    controllerDetalhes.sairTela();
                }
                controller.encerrarReabrirServico();
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
                if(servicosDetalhes.isVisible()){
                    controllerDetalhes.sairTela();
                }
                telaServicosAdicionar.setModal(true);
                telaServicosAdicionar.setLocationRelativeTo(null);
                telaServicosAdicionar.setVisible(true);
            }
        });
    }
    
}
