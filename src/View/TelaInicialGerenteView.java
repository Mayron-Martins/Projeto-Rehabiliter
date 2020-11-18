/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.adicionais.TelaInicioGerenteController;
import java.awt.Color;
import java.awt.Dimension;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import sun.swing.table.DefaultTableCellHeaderRenderer;

/**
 *
 * @author 55989
 */
public class TelaInicialGerenteView extends javax.swing.JFrame {
    private final TelaInicioGerenteController controller;

    /**
     * Creates new form telaInicialGerente
     */
    public TelaInicialGerenteView() {
        initComponents();
        controller = new TelaInicioGerenteController(this);
        botaoSair.setBackground(new Color(0,0,0,0));
        botaoCaixa.setBackground(new Color(0,0,0,0));
        botaoAlunos.setBackground(new Color(0,0,0,0));
        botaoFinanceiro.setBackground(new Color(0,0,0,0));
        botaoFrequencia.setBackground(new Color(0,0,0,0));
        botaoMenu.setBackground(new Color(0,0,0,0));
        botaoFuncionarios.setBackground(new Color(0,0,0,0));
        jScrollPane1.setVisible(false);
        setExtendedState(MAXIMIZED_BOTH);
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

        botaoFuncionarios = new javax.swing.JButton();
        botaoSair = new javax.swing.JButton();
        botaoCaixa = new javax.swing.JButton();
        animaçãoRehabiliter = new javax.swing.JLabel();
        botaoAlunos = new javax.swing.JButton();
        botaoFrequencia = new javax.swing.JButton();
        botaoFinanceiro = new javax.swing.JButton();
        botaoMenu = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaAniversariantes = new javax.swing.JTable();
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

        botaoFuncionarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/telainicialgerente/funcionarios.png"))); // NOI18N
        botaoFuncionarios.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/telainicialgerente/funcionarioshover.png"))); // NOI18N
        botaoFuncionarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoFuncionariosActionPerformed(evt);
            }
        });
        getContentPane().add(botaoFuncionarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 90, 168, 203));

        botaoSair.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSairActionPerformed(evt);
            }
        });
        getContentPane().add(botaoSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 937, 90, 25));

        botaoCaixa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/telainicialfuncionario/caixaprincipal.png"))); // NOI18N
        botaoCaixa.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/telainicialfuncionario/caixaHoverPrincipal.png"))); // NOI18N
        botaoCaixa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoCaixaActionPerformed(evt);
            }
        });
        getContentPane().add(botaoCaixa, new org.netbeans.lib.awtextra.AbsoluteConstraints(544, 90, 168, 203));

        animaçãoRehabiliter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/rehab.gif"))); // NOI18N
        getContentPane().add(animaçãoRehabiliter, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 800, 420, 190));

        botaoAlunos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/telainicialfuncionario/alunosprincipal.png"))); // NOI18N
        botaoAlunos.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/telainicialfuncionario/alunosHoverprincipal.png"))); // NOI18N
        botaoAlunos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoAlunosActionPerformed(evt);
            }
        });
        getContentPane().add(botaoAlunos, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 168, 203));

        botaoFrequencia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/telainicialfuncionario/frequenciaprincipal.png"))); // NOI18N
        botaoFrequencia.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/telainicialfuncionario/frequenciaHoverprincipal.png"))); // NOI18N
        botaoFrequencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoFrequenciaActionPerformed(evt);
            }
        });
        getContentPane().add(botaoFrequencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(208, 90, 168, 203));

        botaoFinanceiro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/telainicialfuncionario/financeiroprincipal.png"))); // NOI18N
        botaoFinanceiro.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/telainicialfuncionario/financeiroHoverprincipal.png"))); // NOI18N
        botaoFinanceiro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoFinanceiroActionPerformed(evt);
            }
        });
        getContentPane().add(botaoFinanceiro, new org.netbeans.lib.awtextra.AbsoluteConstraints(376, 90, 168, 203));

        botaoMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/telainicialfuncionario/menuprincipal.png"))); // NOI18N
        botaoMenu.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/telainicialfuncionario/menuHoverPrincipal.png"))); // NOI18N
        botaoMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoMenuActionPerformed(evt);
            }
        });
        getContentPane().add(botaoMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(1140, 40, 110, 70));

        tabelaAniversariantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Aniversariante do Mês", "Turma", "Dia"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaAniversariantes.setAutoscrolls(false);
        tabelaAniversariantes.setRowSelectionAllowed(false);
        tabelaAniversariantes.getTableHeader().setResizingAllowed(false);
        this.defineRenderers();
        tabelaAniversariantes.getTableHeader().setReorderingAllowed(false);
        tabelaAniversariantes.setBackground(new Color(0,134, 190));
        tabelaAniversariantes.setForeground(Color.WHITE);
        tabelaAniversariantes.setShowGrid(false);
        jScrollPane1.setViewportView(tabelaAniversariantes);

        jScrollPane1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0,0), 1, true));
        jScrollPane1.setOpaque(false);
        jScrollPane1.getViewport().setOpaque(false);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 350, 316, 180));

        planodefundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/imagensparaseremtrocadas/tela-inicial-gerentefundo.jpg"))); // NOI18N
        getContentPane().add(planodefundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 1370, 1030));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSairActionPerformed
        // TODO add your handling code here:
        this.dispose();
        LoginGerente jump = new LoginGerente();
        jump.setVisible(true);
    }//GEN-LAST:event_botaoSairActionPerformed

    private void botaoAlunosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoAlunosActionPerformed
        // TODO add your handling code here:
        AlunosView jump=new AlunosView();
        jump.setVisible(true);
    }//GEN-LAST:event_botaoAlunosActionPerformed

    private void botaoFrequenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFrequenciaActionPerformed
        // TODO add your handling code here:
        turmasFrequencia abrir= new turmasFrequencia();
        abrir.setVisible(true);
    }//GEN-LAST:event_botaoFrequenciaActionPerformed

    private void botaoFinanceiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFinanceiroActionPerformed
        // TODO add your handling code here:
        Financeiro abrir=new Financeiro();
        abrir.setVisible(true);
    }//GEN-LAST:event_botaoFinanceiroActionPerformed

    private void botaoMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoMenuActionPerformed
        // TODO add your handling code here:
        MenuGerente jump=new MenuGerente();
        jump.setVisible(true);
    }//GEN-LAST:event_botaoMenuActionPerformed

    private void botaoFuncionariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFuncionariosActionPerformed
        Funcionarios telaFuncionarios = new Funcionarios();
        telaFuncionarios.setVisible(true);
    }//GEN-LAST:event_botaoFuncionariosActionPerformed

    private void botaoCaixaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoCaixaActionPerformed
        // TODO add your handling code here:
        Caixa abrir= new Caixa();
        abrir.setVisible(true);
    }//GEN-LAST:event_botaoCaixaActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            controller.inicializarTabela();
            controller.setarPlanos();
        } catch (SQLException ex) {
            Logger.getLogger(TelaInicialGerenteView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(TelaInicialGerenteView.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            java.util.logging.Logger.getLogger(TelaInicialGerenteView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaInicialGerenteView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaInicialGerenteView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaInicialGerenteView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaInicialGerenteView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel animaçãoRehabiliter;
    private javax.swing.JButton botaoAlunos;
    private javax.swing.JButton botaoCaixa;
    private javax.swing.JButton botaoFinanceiro;
    private javax.swing.JButton botaoFrequencia;
    private javax.swing.JButton botaoFuncionarios;
    private javax.swing.JButton botaoMenu;
    private javax.swing.JButton botaoSair;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel planodefundo;
    private javax.swing.JTable tabelaAniversariantes;
    // End of variables declaration//GEN-END:variables
    
    private void defineRenderers() {
    DefaultTableCellRenderer rendererCentro = new DefaultTableCellRenderer();
    rendererCentro.setHorizontalAlignment(SwingConstants.CENTER);
    DefaultTableCellHeaderRenderer headerCentro = new DefaultTableCellHeaderRenderer();
    headerCentro.setHorizontalAlignment(SwingConstants.CENTER);
    DefaultTableCellRenderer rendererDireita = new DefaultTableCellRenderer();
    rendererDireita.setHorizontalAlignment(SwingConstants.RIGHT);
    DefaultTableCellRenderer rendererEsquerda = new DefaultTableCellRenderer();
    rendererEsquerda.setHorizontalAlignment(SwingConstants.LEFT);

    JTableHeader header = tabelaAniversariantes.getTableHeader(); 
    header.setPreferredSize(new Dimension(0, 35)); 
    TableColumnModel modeloDaColuna = tabelaAniversariantes.getColumnModel();

    modeloDaColuna.getColumn(0).setCellRenderer(rendererCentro);
    modeloDaColuna.getColumn(1).setCellRenderer(rendererCentro);
    modeloDaColuna.getColumn(2).setCellRenderer(rendererCentro);

    DefaultTableCellRenderer defaultheader = (DefaultTableCellRenderer) tabelaAniversariantes.getTableHeader().getDefaultRenderer();
    defaultheader.setHorizontalAlignment(SwingConstants.CENTER);

    modeloDaColuna.getColumn(0).setMaxWidth(145);
    modeloDaColuna.getColumn(1).setMaxWidth(90);
    modeloDaColuna.getColumn(2).setMaxWidth(95);

    }

    public JScrollPane getjScrollPane1() {
        return jScrollPane1;
    }

    public JTable getTabelaAniversariantes() {
        return tabelaAniversariantes;
    }
    
}
