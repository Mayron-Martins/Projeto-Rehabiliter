/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.AlunosController;
import Controller.auxiliar.FormatacaodeCamposRestritos;
import Controller.auxiliar.JMoneyField;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/**
 *
 * @author 55989
 */
public class AlunosView extends javax.swing.JDialog {
    private final java.awt.Frame parent;
    private final int numeroTela = 1;
    private Point point = new Point();
    private final AlunosController controller;
    private AlunosCadastro telaAlunosCadastro;
    private javax.swing.JComboBox<String> comboServicos = new JComboBox<>();
    private javax.swing.JComboBox<String> comboTurmas = new JComboBox<>();
    private javax.swing.JComboBox<String> comboEstado; //= new JComboBox<>(telaAlunosCadastro.getComboEstado().getModel());
    private JFormattedTextField valorContrato = new JMoneyField();
    private JFormattedTextField valorDebito = new JMoneyField();
    private JFormattedTextField telefonePai;//= new JFormattedTextField(telaAlunosCadastro.getCampoContatoPai().getFormatter());
    private JFormattedTextField cpfPai; //= new JFormattedTextField(telaAlunosCadastro.getCampoCPFPai().getFormatter());
    private JFormattedTextField telefoneMae; //= new JFormattedTextField(telaAlunosCadastro.getCampoContatoMae().getFormatter());
    private JFormattedTextField cpfMae; //= new JFormattedTextField(telaAlunosCadastro.getCampoCPFMae().getFormatter());
    private JFormattedTextField cep; //= new JFormattedTextField(telaAlunosCadastro.getCampoCEP().getFormatter());
    private JTextField diaVencimento = new FormatacaodeCamposRestritos(2);
    /**
     * Creates new form Alunos
     * @param parent
     * @param modal
     */
    public AlunosView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        this.parent = parent;
        telaAlunosCadastro = new AlunosCadastro(parent, false);
        comboEstado = new JComboBox<>(telaAlunosCadastro.getComboEstado().getModel());
        telefonePai = new JFormattedTextField(telaAlunosCadastro.getCampoContatoPai().getFormatter());
        cpfPai = new JFormattedTextField(telaAlunosCadastro.getCampoCPFPai().getFormatter());
        telefoneMae = new JFormattedTextField(telaAlunosCadastro.getCampoContatoMae().getFormatter());
        cpfMae = new JFormattedTextField(telaAlunosCadastro.getCampoCPFMae().getFormatter());
        cep = new JFormattedTextField(telaAlunosCadastro.getCampoCEP().getFormatter());
        
        this.setarComposicaoTabelas();
        
        controller = new AlunosController(this);
        botaobuscar.setBackground(new Color(0,0,0,0));
        botaoFechar.setBackground(new Color(0,0,0,0));
        botaoAdicionar.setBackground(new Color(0,0,0,0));
        botaoRemover.setBackground(new Color(0,0,0,0));
        botaoEditar.setBackground(new Color(0,0,0,0));
        botaoListar.setBackground(new Color(0,0,0,0));
        tabelaAlunos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabelaAlunos.getTableHeader().setOpaque(false);
        tabelaAlunos.getTableHeader().setBackground(new Color(0,0,0));
        tabelaAlunos.getTableHeader().setForeground(new Color(0,0,0));
        tabelaAlunos.setRowHeight(25);
        botaoEndereco.setBackground(new Color(0,0,0,0));
        botaoPais.setBackground(new Color(0,0,0,0));
        botaoAlunos.setBackground(new Color(0,0,0,0));
        botaoPlanos.setBackground(new Color(0,0,0,0));
        this.setInicialBotoes();
        fecharTelaESC();
        this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/imagens/rehabi.png")).getImage());
        
        
        
        comboServicos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboServicosActionPerformed(evt);
            }
            private void comboServicosActionPerformed(ActionEvent evt) {
            int linhaSelecionada = tabelaAlunos.getSelectedRow();
                if(tabelaAlunos.isRowSelected(linhaSelecionada)){
                    try {
                          controller.setarValorContrato();
                    } catch (SQLException ex) {
                        Logger.getLogger(AlunosView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        
    }
        });
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelPlanos = new javax.swing.JScrollPane();
        tabelaPlanos = new javax.swing.JTable();
        painelAlunos = new javax.swing.JScrollPane();
        tabelaAlunos = new javax.swing.JTable();
        painelPais = new javax.swing.JScrollPane();
        tabelaPais = new javax.swing.JTable();
        painelEnderecos = new javax.swing.JScrollPane();
        tabelaEnderecos = new javax.swing.JTable();
        botaoRemover = new javax.swing.JButton();
        botaoEditar = new javax.swing.JButton();
        botaoListar = new javax.swing.JButton();
        botaoFechar = new javax.swing.JButton();
        botaobuscar = new javax.swing.JButton();
        botaoAdicionar = new javax.swing.JButton();
        campoPesquisa = new javax.swing.JTextField();
        comboListar = new javax.swing.JComboBox<>();
        botaoPlanos = new javax.swing.JButton();
        botaoPais = new javax.swing.JButton();
        botaoEndereco = new javax.swing.JButton();
        botaoAlunos = new javax.swing.JButton();
        planodefundo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabelaPlanos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tabelaPlanos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ChavePlano", "Dia Vencimento", "Situacao"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class
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
        tabelaPlanos.setFocusable(false);
        tabelaPlanos.setGridColor(new java.awt.Color(255, 255, 255));
        tabelaPlanos.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tabelaPlanos.setRowHeight(25);
        tabelaPlanos.setShowVerticalLines(false);
        tabelaPlanos.getTableHeader().setReorderingAllowed(false);
        tabelaPlanos.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                tabelaPlanosComponentHidden(evt);
            }
        });
        painelPlanos.setViewportView(tabelaPlanos);

        getContentPane().add(painelPlanos, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, 811, 340));

        tabelaAlunos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tabelaAlunos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CodBanco", "Nome", "Turma", "Forma de Pag.", "Valor Contrato", "Débito"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaAlunos.setFocusable(false);
        tabelaAlunos.setGridColor(new java.awt.Color(255, 255, 255));
        tabelaAlunos.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tabelaAlunos.setRowHeight(25);
        tabelaAlunos.setShowVerticalLines(false);
        tabelaAlunos.getTableHeader().setReorderingAllowed(false);
        tabelaAlunos.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                tabelaAlunosComponentHidden(evt);
            }
        });
        painelAlunos.setViewportView(tabelaAlunos);

        getContentPane().add(painelAlunos, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, 811, 340));

        tabelaPais.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tabelaPais.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Pai", "CPF (P)", "Contato (P)", "Mãe", "CPF (M)", "Contato (M)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tabelaPais.setFocusable(false);
        tabelaPais.setGridColor(new java.awt.Color(255, 255, 255));
        tabelaPais.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tabelaPais.setRowHeight(25);
        tabelaPais.setShowVerticalLines(false);
        tabelaPais.getTableHeader().setReorderingAllowed(false);
        tabelaPais.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                tabelaPaisComponentHidden(evt);
            }
        });
        painelPais.setViewportView(tabelaPais);

        getContentPane().add(painelPais, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, 811, 340));

        tabelaEnderecos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tabelaEnderecos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Logradouro", "Nº", "Bairro", "Cidade", "Estado", "CEP"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tabelaEnderecos.setFocusable(false);
        tabelaEnderecos.setGridColor(new java.awt.Color(255, 255, 255));
        tabelaEnderecos.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tabelaEnderecos.setRowHeight(25);
        tabelaEnderecos.setShowVerticalLines(false);
        tabelaEnderecos.getTableHeader().setReorderingAllowed(false);
        tabelaEnderecos.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                tabelaEnderecosComponentHidden(evt);
            }
        });
        painelEnderecos.setViewportView(tabelaEnderecos);

        getContentPane().add(painelEnderecos, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, 811, 340));

        botaoRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoRemover.png"))); // NOI18N
        botaoRemover.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoHoverremover.png"))); // NOI18N
        botaoRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoRemoverActionPerformed(evt);
            }
        });
        getContentPane().add(botaoRemover, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 168, 205, 50));

        botaoEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoEditar.png"))); // NOI18N
        botaoEditar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoEditarHover.png"))); // NOI18N
        botaoEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoEditarActionPerformed(evt);
            }
        });
        getContentPane().add(botaoEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 168, 205, 50));

        botaoListar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoListar.png"))); // NOI18N
        botaoListar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoHoverListar.png"))); // NOI18N
        botaoListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoListarActionPerformed(evt);
            }
        });
        getContentPane().add(botaoListar, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 170, 205, 50));

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
        getContentPane().add(botaobuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 230, 50, 40));

        botaoAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoAdicionar.png"))); // NOI18N
        botaoAdicionar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/botaoHoverAdicionar.png"))); // NOI18N
        botaoAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoAdicionarActionPerformed(evt);
            }
        });
        getContentPane().add(botaoAdicionar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 168, 205, 50));

        campoPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                campoPesquisaKeyPressed(evt);
            }
        });
        getContentPane().add(campoPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 230, 320, 40));

        comboListar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Aniversariantes", "Pendentes", "Pagos" }));
        getContentPane().add(comboListar, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 230, 170, 30));

        botaoPlanos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/btnPlanos.png"))); // NOI18N
        botaoPlanos.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/btnPlanosHover.png"))); // NOI18N
        botaoPlanos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoPlanosActionPerformed(evt);
            }
        });
        getContentPane().add(botaoPlanos, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 310, 120, -1));

        botaoPais.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/btnPais.png"))); // NOI18N
        botaoPais.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/btnPaishover.png"))); // NOI18N
        botaoPais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoPaisActionPerformed(evt);
            }
        });
        getContentPane().add(botaoPais, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 310, 120, -1));

        botaoEndereco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/btnEndereco.png"))); // NOI18N
        botaoEndereco.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/btnEnderecohover.png"))); // NOI18N
        botaoEndereco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoEnderecoActionPerformed(evt);
            }
        });
        getContentPane().add(botaoEndereco, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 310, 120, -1));

        botaoAlunos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/btnAlunosh.png"))); // NOI18N
        botaoAlunos.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/btnAlunos.png"))); // NOI18N
        botaoAlunos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoAlunosActionPerformed(evt);
            }
        });
        getContentPane().add(botaoAlunos, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 310, 120, -1));

        planodefundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alunos/Alunos.jpg"))); // NOI18N
        getContentPane().add(planodefundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_formMouseClicked

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        // TODO add your handling code here:
        point.x = evt.getX();
        point.y = evt.getY();
        
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        // TODO add your handling code here:
        Point p = this.getLocation();
        this.setLocation(p.x+ evt.getX()-point.x,p.y+ evt.getY()-point.y);
    }//GEN-LAST:event_formMouseDragged

    private void botaoEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoEditarActionPerformed
    try {
        controller.editarAlunos();
    } catch (ParseException ex) {
        Logger.getLogger(AlunosView.class.getName()).log(Level.SEVERE, null, ex);
    } catch (Exception ex) {
        Logger.getLogger(AlunosView.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_botaoEditarActionPerformed

    private void tabelaAlunosComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tabelaAlunosComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelaAlunosComponentHidden

    private void botaoFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFecharActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_botaoFecharActionPerformed

    private void botaoAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoAdicionarActionPerformed
        // TODO add your handling code here:
        telaAlunosCadastro.setModal(true);
        telaAlunosCadastro.setLocationRelativeTo(null);
        telaAlunosCadastro.setVisible(true);
    }//GEN-LAST:event_botaoAdicionarActionPerformed

    private void botaoRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoRemoverActionPerformed
    try {
        controller.removerAluno();
    } catch (ParseException ex) {
        Logger.getLogger(AlunosView.class.getName()).log(Level.SEVERE, null, ex);
    } catch (Exception ex) {
        Logger.getLogger(AlunosView.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_botaoRemoverActionPerformed

    private void tabelaPaisComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tabelaPaisComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelaPaisComponentHidden

    private void tabelaEnderecosComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tabelaEnderecosComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelaEnderecosComponentHidden

    private void botaoPaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoPaisActionPerformed
        this.trocarTabelas(2);
        this.selecionarTabelas(2);
    }//GEN-LAST:event_botaoPaisActionPerformed

    private void botaoEnderecoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoEnderecoActionPerformed
        this.trocarTabelas(3);
        this.selecionarTabelas(3);
    }//GEN-LAST:event_botaoEnderecoActionPerformed

    private void botaoAlunosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoAlunosActionPerformed
        this.trocarTabelas(4);
        this.selecionarTabelas(4);
    }//GEN-LAST:event_botaoAlunosActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
    try {
        controller.verificacaoDeTurmaEServico();
    } catch (SQLException ex) {
        Logger.getLogger(AlunosView.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    try {
        controller.listarAlunos();
    } catch (ParseException ex) {
        Logger.getLogger(AlunosView.class.getName()).log(Level.SEVERE, null, ex);
    } catch (Exception ex) {
        Logger.getLogger(AlunosView.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_formWindowOpened

    private void botaobuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaobuscarActionPerformed
        
    try {
        controller.buscarAlunos();
    } catch (Exception ex) {
        Logger.getLogger(AlunosView.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_botaobuscarActionPerformed

    private void campoPesquisaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoPesquisaKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            getRootPane().setDefaultButton(botaobuscar);
        }
    }//GEN-LAST:event_campoPesquisaKeyPressed

    private void botaoListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoListarActionPerformed
    try {
        controller.listar();
    } catch (Exception ex) {
        Logger.getLogger(AlunosView.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_botaoListarActionPerformed

    private void botaoPlanosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoPlanosActionPerformed
        this.trocarTabelas(1);
        this.selecionarTabelas(1);
    }//GEN-LAST:event_botaoPlanosActionPerformed

    private void tabelaPlanosComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tabelaPlanosComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelaPlanosComponentHidden

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode() == KeyEvent.VK_ESCAPE){
            this.dispose();
        }
    }//GEN-LAST:event_formKeyPressed
     
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
            java.util.logging.Logger.getLogger(AlunosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AlunosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AlunosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AlunosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AlunosView dialog = new AlunosView(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton botaoAdicionar;
    private javax.swing.JButton botaoAlunos;
    private javax.swing.JButton botaoEditar;
    private javax.swing.JButton botaoEndereco;
    private javax.swing.JButton botaoFechar;
    private javax.swing.JButton botaoListar;
    private javax.swing.JButton botaoPais;
    private javax.swing.JButton botaoPlanos;
    private javax.swing.JButton botaoRemover;
    private javax.swing.JButton botaobuscar;
    private javax.swing.JTextField campoPesquisa;
    private javax.swing.JComboBox<String> comboListar;
    private javax.swing.JScrollPane painelAlunos;
    private javax.swing.JScrollPane painelEnderecos;
    private javax.swing.JScrollPane painelPais;
    private javax.swing.JScrollPane painelPlanos;
    private javax.swing.JLabel planodefundo;
    private javax.swing.JTable tabelaAlunos;
    private javax.swing.JTable tabelaEnderecos;
    private javax.swing.JTable tabelaPais;
    private javax.swing.JTable tabelaPlanos;
    // End of variables declaration//GEN-END:variables

    private void trocarTabelas(int opcao){
        switch(opcao){
            case 1:
                this.botaoPlanos.setVisible(false);
                this.painelAlunos.setVisible(false);
                this.botaoPais.setVisible(true);
                this.painelPlanos.setVisible(true);
            break;
            
            case 2:
                this.botaoPais.setVisible(false);
                this.painelPlanos.setVisible(false);
                this.botaoEndereco.setVisible(true);
                this.painelPais.setVisible(true);
            break;
            
            case 3:
                this.botaoEndereco.setVisible(false);
                this.painelPais.setVisible(false);
                this.botaoAlunos.setVisible(true);
                this.painelEnderecos.setVisible(true);
            break;
            
            case 4:
                this.botaoAlunos.setVisible(false);
                this.painelEnderecos.setVisible(false);
                this.botaoPlanos.setVisible(true);
                this.painelAlunos.setVisible(true);
            break;
        }
        
    }
    
    public void exibeMensagem(String mensagem) {
      JOptionPane.showMessageDialog(null, mensagem);
    }
    
    private void setInicialBotoes(){
        this.botaoPais.setVisible(true);
        this.painelAlunos.setVisible(true);
        this.botaoEndereco.setVisible(false);
        this.painelPais.setVisible(false);
        this.botaoAlunos.setVisible(false);
        this.painelEnderecos.setVisible(false);
    }
    
    private void selecionarLinhasUniforme(){
        int linhaSelecionada = this.tabelaAlunos.getSelectedRow();
            this.tabelaPais.setRowSelectionInterval(linhaSelecionada, linhaSelecionada);
            this.tabelaEnderecos.setRowSelectionInterval(linhaSelecionada, linhaSelecionada);
    }

    public JComboBox<String> getComboServicos() {
        return comboServicos;
    }

    public JComboBox<String> getComboTurmas() {
        return comboTurmas;
    }

    public JComboBox<String> getComboEstado() {
        return comboEstado;
    }

    public JFormattedTextField getValorContrato() {
        return valorContrato;
    }

    public JFormattedTextField getValorDebito() {
        return valorDebito;
    }

    public JFormattedTextField getTelefonePai() {
        return telefonePai;
    }

    public JFormattedTextField getCpfPai() {
        return cpfPai;
    }

    public JFormattedTextField getTelefoneMae() {
        return telefoneMae;
    }

    public JFormattedTextField getCpfMae() {
        return cpfMae;
    }

    public JFormattedTextField getCep() {
        return cep;
    }

    public JTextField getCampoPesquisa() {
        return campoPesquisa;
    }

    public JComboBox<String> getComboListar() {
        return comboListar;
    }

    public JTable getTabelaAlunos() {
        return tabelaAlunos;
    }

    public JTable getTabelaEnderecos() {
        return tabelaEnderecos;
    }

    public JTable getTabelaPais() {
        return tabelaPais;
    }

    public JTable getTabelaPlanos() {
        return tabelaPlanos;
    }

    public int getNumeroTela() {
        return numeroTela;
    }
    
    
    
    private void selecionarTabelas(int opcao){
        switch(opcao){
            case 1:
            if(tabelaAlunos.getSelectedRow()>-1){
            tabelaPlanos.addRowSelectionInterval(tabelaAlunos.getSelectedRow(), tabelaAlunos.getSelectedRow());
            tabelaPais.addRowSelectionInterval(tabelaAlunos.getSelectedRow(), tabelaAlunos.getSelectedRow());
            tabelaEnderecos.addRowSelectionInterval(tabelaAlunos.getSelectedRow(), tabelaAlunos.getSelectedRow());
            }
            break;
            
            case 2:
            if(tabelaPlanos.getSelectedRow()>-1){
            tabelaPais.addRowSelectionInterval(tabelaPlanos.getSelectedRow(), tabelaPlanos.getSelectedRow());
            tabelaEnderecos.addRowSelectionInterval(tabelaPlanos.getSelectedRow(), tabelaPlanos.getSelectedRow());
            tabelaAlunos.addRowSelectionInterval(tabelaPlanos.getSelectedRow(), tabelaPlanos.getSelectedRow());
            }
            break;
            
            case 3:
            if(tabelaPais.getSelectedRow()>-1){
            tabelaEnderecos.addRowSelectionInterval(tabelaPais.getSelectedRow(), tabelaPais.getSelectedRow());
            tabelaAlunos.addRowSelectionInterval(tabelaPais.getSelectedRow(), tabelaPais.getSelectedRow());
            tabelaPlanos.addRowSelectionInterval(tabelaPais.getSelectedRow(), tabelaPais.getSelectedRow());
            }
            break;
            
            case 4:
            if(tabelaEnderecos.getSelectedRow()>-1){
            tabelaAlunos.addRowSelectionInterval(tabelaEnderecos.getSelectedRow(), tabelaEnderecos.getSelectedRow());
            tabelaPlanos.addRowSelectionInterval(tabelaEnderecos.getSelectedRow(), tabelaEnderecos.getSelectedRow());
            tabelaPais.addRowSelectionInterval(tabelaEnderecos.getSelectedRow(), tabelaEnderecos.getSelectedRow());
            }
            break;
        }
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

    public Frame getParent() {
        return parent;
    }
    
    private void setarComposicaoTabelas(){
        //Alunos
        tabelaAlunos.getTableHeader().setResizingAllowed(false);
        tabelaAlunos.getTableHeader().setReorderingAllowed(false);
        tabelaAlunos.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(comboServicos));
        tabelaAlunos.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(comboTurmas));
        tabelaAlunos.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(valorContrato));
        tabelaAlunos.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(valorDebito));
        
        //Planos
        tabelaPlanos.getTableHeader().setResizingAllowed(false);
        tabelaPlanos.getTableHeader().setReorderingAllowed(false);
        tabelaPlanos.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(diaVencimento));
        
        //Pais
        tabelaPais.getTableHeader().setResizingAllowed(false);
        tabelaPais.getTableHeader().setReorderingAllowed(false);
        tabelaPais.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(cpfPai));
        tabelaPais.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(telefonePai));
        tabelaPais.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(cpfMae));
        tabelaPais.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(telefoneMae));
        
        //Enderecos
        tabelaEnderecos.getTableHeader().setResizingAllowed(false);
        tabelaEnderecos.getTableHeader().setReorderingAllowed(false);
        tabelaEnderecos.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(cep));
        tabelaEnderecos.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(comboEstado));
    }
}
