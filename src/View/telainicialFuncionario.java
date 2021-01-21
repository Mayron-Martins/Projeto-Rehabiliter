/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.adicionais.TelaInicioFuncionariosController;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import sun.swing.table.DefaultTableCellHeaderRenderer;

/**
 *
 * @author 55989
 */
public class telainicialFuncionario extends javax.swing.JFrame {
    private final TelaInicioFuncionariosController controller;
    private final AlunosView telaAlunos;
    private final TurmasView telaTurmas;
    private final turmasFrequencia telaTurmasFrequencia;
    private final ProdutosView telaProdutos;
    private final ServicosView telaServicos;
    private final Financeiro telaFinanceiro;
    private final ImprimirExportarGerente telaImprimirExportar;
    private final BackupView telaBackup;
    private final Caixa telaCaixa;

    /**
     * Creates new form telainicialFuncionario
     */
    public telainicialFuncionario() {
        initComponents();
        
        telaAlunos=new AlunosView(this, false);
        telaTurmas=new TurmasView(this, false);
        telaTurmasFrequencia= new turmasFrequencia(this, false);
        telaProdutos=new ProdutosView(this, false);
        telaServicos=new ServicosView(this, false);
        telaFinanceiro = new Financeiro(this, false);
        telaImprimirExportar= new ImprimirExportarGerente(this, false);
        telaBackup= new BackupView(this, false);
        telaCaixa= new Caixa(this, false);
        
        
        controller = new TelaInicioFuncionariosController(this);
        botaoSair.setBackground(new Color(0,0,0,0));
        botaoCaixa.setBackground(new Color(0,0,0,0));
        botaoAlunos.setBackground(new Color(0,0,0,0));
        botaoFinanceiro.setBackground(new Color(0,0,0,0));
        botaoFrequencia.setBackground(new Color(0,0,0,0));
        botaoMenu.setBackground(new Color(0,0,0,0));
        setExtendedState(MAXIMIZED_BOTH);
        
        this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/imagens/rehabi.png")).getImage());
        jScrollPane1.setVisible(false);
        menuFuncionario.setVisible(false);
        menuFuncionario.setBackground(new Color(0,0,0,2));
        
        
        
        
        
        botaoFechar.setBackground(new Color(0,0,0,0));
        botaoSair1.setBackground(new Color(0,0,0,0));
        botaoAlunos1.setBackground(new Color(0,0,0,0));
        botaoTurmas.setBackground(new Color(0,0,0,0));
        botaoProdutos.setBackground(new Color(0,0,0,0));
        botaoServicos.setBackground(new Color(0,0,0,0));
        botaoFinanceiro1.setBackground(new Color(0,0,0,0));
        botaoBackup.setBackground(new Color(0,0,0,0));
        botaoImprimirExportar.setBackground(new Color(0,0,0,0));
        botaoCaixa1.setBackground(new Color(0,0,0,0));
        
        this.teclasDeAtalho();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuFuncionario = new javax.swing.JPanel();
        botaoFechar = new javax.swing.JButton();
        botaoBackup = new javax.swing.JButton();
        botaoImprimirExportar = new javax.swing.JButton();
        botaoFinanceiro1 = new javax.swing.JButton();
        botaoServicos = new javax.swing.JButton();
        botaoTurmas = new javax.swing.JButton();
        botaoAlunos1 = new javax.swing.JButton();
        botaoProdutos = new javax.swing.JButton();
        botaoCaixa1 = new javax.swing.JButton();
        botaoSair1 = new javax.swing.JButton();
        planodefundo1 = new javax.swing.JLabel();
        botaoSair = new javax.swing.JButton();
        botaoCaixa = new javax.swing.JButton();
        botaoAlunos = new javax.swing.JButton();
        botaoFrequencia = new javax.swing.JButton();
        botaoFinanceiro = new javax.swing.JButton();
        botaoMenu = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaAniversariantes = new javax.swing.JTable();
        animaçãoRehabiliter = new javax.swing.JLabel();
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

        menuFuncionario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        botaoFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoFecharActionPerformed(evt);
            }
        });
        menuFuncionario.add(botaoFechar, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 0, 40, 40));

        botaoBackup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/menufuncionario/botaoBackup.png"))); // NOI18N
        botaoBackup.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/menufuncionario/botaoHoverBackup.png"))); // NOI18N
        botaoBackup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoBackupActionPerformed(evt);
            }
        });
        menuFuncionario.add(botaoBackup, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 500, 360, 50));

        botaoImprimirExportar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/menufuncionario/botaoImprimir.png"))); // NOI18N
        botaoImprimirExportar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/menufuncionario/botaoHoverImprimir.png"))); // NOI18N
        botaoImprimirExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoImprimirExportarActionPerformed(evt);
            }
        });
        menuFuncionario.add(botaoImprimirExportar, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 430, 360, 50));

        botaoFinanceiro1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/menufuncionario/botaoFinanceiro.png"))); // NOI18N
        botaoFinanceiro1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/menufuncionario/botaoHoverFinanceiro.png"))); // NOI18N
        botaoFinanceiro1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoFinanceiro1ActionPerformed(evt);
            }
        });
        menuFuncionario.add(botaoFinanceiro1, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 370, 360, 50));

        botaoServicos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/menufuncionario/botaoServicos.png"))); // NOI18N
        botaoServicos.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/menufuncionario/botaoHoverServicos.png"))); // NOI18N
        botaoServicos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoServicosActionPerformed(evt);
            }
        });
        menuFuncionario.add(botaoServicos, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 300, 360, 50));

        botaoTurmas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/menufuncionario/botaoTurmas.png"))); // NOI18N
        botaoTurmas.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/menufuncionario/botaoHoverTurmas.png"))); // NOI18N
        botaoTurmas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoTurmasActionPerformed(evt);
            }
        });
        menuFuncionario.add(botaoTurmas, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 170, 360, 50));

        botaoAlunos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/menufuncionario/botaoAlunos.png"))); // NOI18N
        botaoAlunos1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/menufuncionario/botaoHoverAlunos.png"))); // NOI18N
        botaoAlunos1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoAlunos1ActionPerformed(evt);
            }
        });
        menuFuncionario.add(botaoAlunos1, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 110, 360, 50));

        botaoProdutos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/menufuncionario/botaoProdutos.png"))); // NOI18N
        botaoProdutos.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/menufuncionario/botaoHoverProdutos.png"))); // NOI18N
        botaoProdutos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoProdutosActionPerformed(evt);
            }
        });
        menuFuncionario.add(botaoProdutos, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 240, 360, 50));

        botaoCaixa1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/menufuncionario/botaoCaixa.png"))); // NOI18N
        botaoCaixa1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/menufuncionario/botaoHoverCaixa.png"))); // NOI18N
        botaoCaixa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoCaixa1ActionPerformed(evt);
            }
        });
        menuFuncionario.add(botaoCaixa1, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 560, 360, 50));

        botaoSair1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/menufuncionario/botaoSair.png"))); // NOI18N
        botaoSair1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/menufuncionario/botaoHoverSair.png"))); // NOI18N
        botaoSair1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSair1ActionPerformed(evt);
            }
        });
        menuFuncionario.add(botaoSair1, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 930, 270, 52));

        planodefundo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/imagensparaseremtrocadas/menu-principal-funcionárioFundo.png"))); // NOI18N
        menuFuncionario.add(planodefundo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -2, -1, -1));

        getContentPane().add(menuFuncionario, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        botaoSair.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSairActionPerformed(evt);
            }
        });
        getContentPane().add(botaoSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(168, 935, 50, 20));

        botaoCaixa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/telainicialfuncionario/caixaprincipal.png"))); // NOI18N
        botaoCaixa.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/telainicialfuncionario/caixaHoverPrincipal.png"))); // NOI18N
        botaoCaixa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoCaixaActionPerformed(evt);
            }
        });
        getContentPane().add(botaoCaixa, new org.netbeans.lib.awtextra.AbsoluteConstraints(432, 120, -1, -1));

        botaoAlunos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/telainicialfuncionario/alunosprincipal.png"))); // NOI18N
        botaoAlunos.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/telainicialfuncionario/alunosHoverprincipal.png"))); // NOI18N
        botaoAlunos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoAlunosActionPerformed(evt);
            }
        });
        getContentPane().add(botaoAlunos, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, -1, -1));

        botaoFrequencia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/telainicialfuncionario/frequenciaprincipal.png"))); // NOI18N
        botaoFrequencia.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/telainicialfuncionario/frequenciaHoverprincipal.png"))); // NOI18N
        botaoFrequencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoFrequenciaActionPerformed(evt);
            }
        });
        getContentPane().add(botaoFrequencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(245, 118, -1, -1));

        botaoFinanceiro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/telainicialfuncionario/financeiroprincipal.png"))); // NOI18N
        botaoFinanceiro.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/telainicialfuncionario/financeiroHoverprincipal.png"))); // NOI18N
        botaoFinanceiro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoFinanceiroActionPerformed(evt);
            }
        });
        getContentPane().add(botaoFinanceiro, new org.netbeans.lib.awtextra.AbsoluteConstraints(618, 120, -1, -1));

        botaoMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/telainicialfuncionario/menuprincipal.png"))); // NOI18N
        botaoMenu.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/telainicialfuncionario/menuHoverPrincipal.png"))); // NOI18N
        botaoMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoMenuActionPerformed(evt);
            }
        });
        getContentPane().add(botaoMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(1140, 20, -1, -1));

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

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, -1, -1));

        animaçãoRehabiliter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/rehab.gif"))); // NOI18N
        getContentPane().add(animaçãoRehabiliter, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 740, 430, 260));

        planodefundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/imagensparaseremtrocadas/tela-inicial-funcionáriofundo.jpg"))); // NOI18N
        getContentPane().add(planodefundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 1000));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSairActionPerformed
        controller.sairTela();
        LoginFuncionario jump = new LoginFuncionario();
        jump.setVisible(true);
    }//GEN-LAST:event_botaoSairActionPerformed

    private void botaoMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoMenuActionPerformed
        // TODO add your handling code here:
        menuFuncionario.setVisible(true);
    }//GEN-LAST:event_botaoMenuActionPerformed

    private void botaoAlunosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoAlunosActionPerformed
        if(controller.permissaoDeAcessoATela(telaAlunos.getNumeroTela()+"")){
            telaAlunos.setModal(true);
            telaAlunos.setLocationRelativeTo(null);
            telaAlunos.setVisible(true);
        }
        else{this.exibeMensagem("Acesso Negado");}
        
    }//GEN-LAST:event_botaoAlunosActionPerformed

    private void botaoFrequenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFrequenciaActionPerformed
        if(controller.permissaoDeAcessoATela(telaTurmasFrequencia.getNumeroTela()+"")){
            telaTurmasFrequencia.setModal(true);
            telaTurmasFrequencia.setLocationRelativeTo(null);
            telaTurmasFrequencia.setVisible(true);
        }
        else{this.exibeMensagem("Acesso Negado");}
    }//GEN-LAST:event_botaoFrequenciaActionPerformed

    private void botaoFinanceiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFinanceiroActionPerformed
        if(controller.permissaoDeAcessoATela(telaFinanceiro.getNumeroTela()+"")){
            telaFinanceiro.setModal(true);
            telaFinanceiro.setLocationRelativeTo(null);
            telaFinanceiro.setVisible(true);
        }
        else{this.exibeMensagem("Acesso Negado");}
    }//GEN-LAST:event_botaoFinanceiroActionPerformed

    private void botaoCaixaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoCaixaActionPerformed
        if(controller.permissaoDeAcessoATela(telaCaixa.getNumeroTela()+"")){
            telaCaixa.setModal(true);
            telaCaixa.setLocationRelativeTo(null);
            telaCaixa.setVisible(true);
        }
        else{this.exibeMensagem("Acesso Negado");}
    }//GEN-LAST:event_botaoCaixaActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        /*
        JIFAlunosCadastro tela = new JIFAlunosCadastro();
        jdpPrincipal.add(tela);
        tela.setVisible(true);*/
        controller.inicializarTabela();
        controller.setarPlanos();
    }//GEN-LAST:event_formWindowOpened

    private void botaoFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFecharActionPerformed
        menuFuncionario.setVisible(false);

    }//GEN-LAST:event_botaoFecharActionPerformed

    private void botaoImprimirExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoImprimirExportarActionPerformed
        // TODO add your handling code here:
        menuFuncionario.setVisible(false);
        if(controller.permissaoDeAcessoATela(telaImprimirExportar.getNumeroTela()+"")){
            telaImprimirExportar.setModal(true);
            telaImprimirExportar.setLocationRelativeTo(null);
            telaImprimirExportar.setVisible(true);
        }
        else{this.exibeMensagem("Acesso Negado");}
    }//GEN-LAST:event_botaoImprimirExportarActionPerformed

    private void botaoFinanceiro1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFinanceiro1ActionPerformed
        // TODO add your handling code here:
        menuFuncionario.setVisible(false);
        if(controller.permissaoDeAcessoATela(telaFinanceiro.getNumeroTela()+"")){
            telaFinanceiro.setModal(true);
            telaFinanceiro.setLocationRelativeTo(null);
            telaFinanceiro.setVisible(true);
        }
        else{this.exibeMensagem("Acesso Negado");}
    }//GEN-LAST:event_botaoFinanceiro1ActionPerformed

    private void botaoServicosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoServicosActionPerformed
        // TODO add your handling code here:
         menuFuncionario.setVisible(false);
         if(controller.permissaoDeAcessoATela(telaServicos.getNumeroTela()+"")){
             telaServicos.setModal(true);
             telaServicos.setLocationRelativeTo(null);
             telaServicos.setVisible(true);
         }
         else{this.exibeMensagem("Acesso Negado");}
    }//GEN-LAST:event_botaoServicosActionPerformed

    private void botaoTurmasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoTurmasActionPerformed
        menuFuncionario.setVisible(false);
        if(controller.permissaoDeAcessoATela(telaTurmas.getNumeroTela()+"")){
            telaTurmas.setModal(true);
            telaTurmas.setLocationRelativeTo(null);
            telaTurmas.setVisible(true);
        }
        else{this.exibeMensagem("Acesso Negado");}
    }//GEN-LAST:event_botaoTurmasActionPerformed

    private void botaoAlunos1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoAlunos1ActionPerformed
        menuFuncionario.setVisible(false);
        if(controller.permissaoDeAcessoATela(telaAlunos.getNumeroTela()+"")){
            telaAlunos.setModal(true);
            telaAlunos.setLocationRelativeTo(null);
            telaAlunos.setVisible(true);
        }
        else{this.exibeMensagem("Acesso Negado");}
    }//GEN-LAST:event_botaoAlunos1ActionPerformed

    private void botaoProdutosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoProdutosActionPerformed
        // TODO add your handling code here:
        menuFuncionario.setVisible(false);
        if(controller.permissaoDeAcessoATela(telaProdutos.getNumeroTela()+"")){
            telaProdutos.setModal(true);
            telaProdutos.setLocationRelativeTo(null);
            telaProdutos.setVisible(true);
        }
        else{this.exibeMensagem("Acesso Negado");}
    }//GEN-LAST:event_botaoProdutosActionPerformed

    private void botaoCaixa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoCaixa1ActionPerformed
        // TODO add your handling code here:
        menuFuncionario.setVisible(false);
        if(controller.permissaoDeAcessoATela(telaCaixa.getNumeroTela()+"")){
            telaCaixa.setModal(true);
            telaCaixa.setLocationRelativeTo(null);
            telaCaixa.setVisible(true);
        }
        else{this.exibeMensagem("Acesso Negado");}
    }//GEN-LAST:event_botaoCaixa1ActionPerformed

    private void botaoSair1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSair1ActionPerformed
        controller.sairTela();
        LoginFuncionario jump = new LoginFuncionario();
        jump.setVisible(true);
    }//GEN-LAST:event_botaoSair1ActionPerformed

    private void botaoBackupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoBackupActionPerformed
        // TODO add your handling code here:
        menuFuncionario.setVisible(false);
        if(controller.permissaoDeAcessoATela(telaBackup.getNumeroTela()+"")){
            telaBackup.setModal(true);
            telaBackup.setLocationRelativeTo(null);
            telaBackup.setVisible(true);
        }
        else{this.exibeMensagem("Acesso Negado");}
    }//GEN-LAST:event_botaoBackupActionPerformed

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
            java.util.logging.Logger.getLogger(telainicialFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(telainicialFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(telainicialFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(telainicialFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new telainicialFuncionario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel animaçãoRehabiliter;
    private javax.swing.JButton botaoAlunos;
    private javax.swing.JButton botaoAlunos1;
    private javax.swing.JButton botaoBackup;
    private javax.swing.JButton botaoCaixa;
    private javax.swing.JButton botaoCaixa1;
    private javax.swing.JButton botaoFechar;
    private javax.swing.JButton botaoFinanceiro;
    private javax.swing.JButton botaoFinanceiro1;
    private javax.swing.JButton botaoFrequencia;
    private javax.swing.JButton botaoImprimirExportar;
    private javax.swing.JButton botaoMenu;
    private javax.swing.JButton botaoProdutos;
    private javax.swing.JButton botaoSair;
    private javax.swing.JButton botaoSair1;
    private javax.swing.JButton botaoServicos;
    private javax.swing.JButton botaoTurmas;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel menuFuncionario;
    private javax.swing.JLabel planodefundo;
    private javax.swing.JLabel planodefundo1;
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

    public void exibeMensagem(String mensagem) {
      JOptionPane.showMessageDialog(null, mensagem);
    }
    
    public JScrollPane getjScrollPane1() {
        return jScrollPane1;
    }

    public JTable getTabelaAniversariantes() {
        return tabelaAniversariantes;
    }
    
    public void teclasDeAtalho() {
        //Fechar Tela
        JRootPane meurootpane = getRootPane();
        meurootpane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "ESCAPE");
        meurootpane.getRootPane().getActionMap().put("ESCAPE", new AbstractAction("ESCAPE") {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.sairTela();
                LoginFuncionario jump = new LoginFuncionario();
                jump.setVisible(true);
            }
        });
        
        this.conjuntoTeclasAtalho(meurootpane);
    }
    
    private void conjuntoTeclasAtalho(JRootPane meurootpane){
        //Fechar Tela
        meurootpane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("alt F4"), "FECHAR");
        meurootpane.getRootPane().getActionMap().put("FECHAR", new AbstractAction("FECHAR") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int showConfirmDialog = JOptionPane.showConfirmDialog(null, "Deseja Realmente encerrar esta sessão", "Nota", JOptionPane.YES_NO_OPTION);
                if(showConfirmDialog == JOptionPane.YES_OPTION){
                    controller.sairTela();
                    LoginFuncionario jump = new LoginFuncionario();
                    jump.setVisible(true);
                }
            }
        });
    }
}
