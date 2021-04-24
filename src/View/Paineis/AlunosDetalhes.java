/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Paineis;

import Controller.auxiliar.FormatacaoCamposRestritosLetras;
import Controller.auxiliar.FormatacaodeCamposRestritos;
import Controller.auxiliar.JMoneyField;

/**
 *
 * @author Mayro
 */
public class AlunosDetalhes extends javax.swing.JDialog {

    /**
     * Creates new form AlunosDetalhes
     */
    public AlunosDetalhes(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        botaoEditar = new javax.swing.JButton();
        botaoConcluir = new javax.swing.JButton();
        botaoSair = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        painelDados = new javax.swing.JPanel();
        campoNome = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        campoCPF = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        campoNascimento = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        campoCelular = new javax.swing.JFormattedTextField();
        jLabel10 = new javax.swing.JLabel();
        campoTelefone = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        campoDescricao = new javax.swing.JTextArea();
        checkAlteracoesDados = new javax.swing.JCheckBox();
        painelPais = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        campoNomePai = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        campoCPFPai = new javax.swing.JFormattedTextField();
        jLabel17 = new javax.swing.JLabel();
        campoCelularPai = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        campoNomeMae = new javax.swing.JTextField();
        campoCPFMae = new javax.swing.JFormattedTextField();
        campoCelularMae = new javax.swing.JFormattedTextField();
        jLabel14 = new javax.swing.JLabel();
        painelEndereco = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        campoRua = new FormatacaodeCamposRestritos(50, 0);
        jLabel19 = new javax.swing.JLabel();
        campoBairro = new FormatacaodeCamposRestritos(25, 0);
        jLabel20 = new javax.swing.JLabel();
        campoNum = new FormatacaodeCamposRestritos(10, 0);
        jLabel21 = new javax.swing.JLabel();
        campoCidade = new FormatacaoCamposRestritosLetras(20);
        jLabel22 = new javax.swing.JLabel();
        comboEstado = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        campoCEP = new javax.swing.JFormattedTextField();
        painelPlano = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        comboPlano = new javax.swing.JComboBox<>();
        jLabel32 = new javax.swing.JLabel();
        campoValor = new JMoneyField();
        campoValorMensal = new JMoneyField();
        jLabel30 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        comboTurma = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        campoDiaVencimento = new com.toedter.calendar.JDayChooser();
        jLabel29 = new javax.swing.JLabel();
        renovacaoAuto = new javax.swing.JCheckBox();
        painelAdicional = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        campoDataCadastro = new com.toedter.calendar.JDateChooser();
        jLabel25 = new javax.swing.JLabel();
        campoDataUltimoPag = new com.toedter.calendar.JDateChooser();
        jLabel33 = new javax.swing.JLabel();
        campoDataRenovacao = new com.toedter.calendar.JDateChooser();
        campoDataFimPlano = new com.toedter.calendar.JDateChooser();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        campoDataVencimento = new com.toedter.calendar.JDateChooser();
        botaoSetarVencimento = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(400, 700));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        botaoEditar.setBackground(new java.awt.Color(0, 204, 204));
        botaoEditar.setText("Editar");
        botaoEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoEditarActionPerformed(evt);
            }
        });
        jPanel1.add(botaoEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 580, 140, 50));

        botaoConcluir.setBackground(new java.awt.Color(0, 204, 0));
        botaoConcluir.setText("Concluir");
        botaoConcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoConcluirActionPerformed(evt);
            }
        });
        jPanel1.add(botaoConcluir, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 580, 140, 50));

        botaoSair.setBackground(new java.awt.Color(255, 51, 51));
        botaoSair.setText("X");
        botaoSair.setMaximumSize(new java.awt.Dimension(40, 25));
        botaoSair.setMinimumSize(new java.awt.Dimension(40, 25));
        botaoSair.setPreferredSize(new java.awt.Dimension(40, 25));
        botaoSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSairActionPerformed(evt);
            }
        });
        jPanel1.add(botaoSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 0, 40, 25));

        painelDados.setBackground(new java.awt.Color(157, 197, 188));
        painelDados.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        painelDados.add(campoNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 370, 30));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(11, 13, 138));
        jLabel6.setText("Nome");
        painelDados.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        try {
            campoCPF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        painelDados.add(campoCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 170, 30));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(11, 13, 138));
        jLabel8.setText("Telefone");
        painelDados.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 160, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(11, 13, 138));
        jLabel7.setText("Nascimento");
        painelDados.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 90, -1, -1));
        painelDados.add(campoNascimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 110, 170, 30));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(11, 13, 138));
        jLabel9.setText("CPF");
        painelDados.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        try {
            campoCelular.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) # ####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        painelDados.add(campoCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 170, 30));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(11, 13, 138));
        jLabel10.setText("Descrição");
        painelDados.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, -1, -1));

        try {
            campoTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) ####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        painelDados.add(campoTelefone, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 180, 170, 30));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(11, 13, 138));
        jLabel11.setText("Celular");
        painelDados.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, -1));

        campoDescricao.setColumns(20);
        campoDescricao.setRows(5);
        jScrollPane1.setViewportView(campoDescricao);

        painelDados.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 370, 170));

        checkAlteracoesDados.setFont(new java.awt.Font("Tahoma", 2, 10)); // NOI18N
        checkAlteracoesDados.setText("Há alterações não salvas");
        painelDados.add(checkAlteracoesDados, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 640, -1, -1));

        jTabbedPane1.addTab("Dados", painelDados);

        painelPais.setBackground(new java.awt.Color(157, 197, 188));
        painelPais.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(11, 13, 138));
        jLabel15.setText("Nome (Pai)");
        painelPais.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, -1));
        painelPais.add(campoNomePai, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 370, 30));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(11, 13, 138));
        jLabel16.setText("CPF (Pai)");
        painelPais.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, -1, -1));

        try {
            campoCPFPai.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        painelPais.add(campoCPFPai, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 170, 30));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(11, 13, 138));
        jLabel17.setText("Celular (Pai)");
        painelPais.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 260, -1, -1));

        try {
            campoCelularPai.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) # ####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        painelPais.add(campoCelularPai, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 280, 170, 30));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(11, 13, 138));
        jLabel12.setText("Nome (Mãe)");
        painelPais.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(11, 13, 138));
        jLabel13.setText("CPF (Mãe)");
        painelPais.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));
        painelPais.add(campoNomeMae, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 370, 30));

        try {
            campoCPFMae.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        painelPais.add(campoCPFMae, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 170, 30));

        try {
            campoCelularMae.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) # ####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        painelPais.add(campoCelularMae, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 110, 170, 30));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(11, 13, 138));
        jLabel14.setText("Celular (Mãe)");
        painelPais.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 90, -1, -1));

        jTabbedPane1.addTab("Pais", painelPais);

        painelEndereco.setBackground(new java.awt.Color(157, 197, 188));
        painelEndereco.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(11, 13, 138));
        jLabel18.setText("Bairro");
        painelEndereco.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 160, -1, -1));

        campoRua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoRuaActionPerformed(evt);
            }
        });
        painelEndereco.add(campoRua, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 370, 30));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(11, 13, 138));
        jLabel19.setText("Logradouro");
        painelEndereco.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        campoBairro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoBairroActionPerformed(evt);
            }
        });
        painelEndereco.add(campoBairro, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 170, 30));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(11, 13, 138));
        jLabel20.setText("Número");
        painelEndereco.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 90, -1, -1));
        painelEndereco.add(campoNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 110, 170, 30));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(11, 13, 138));
        jLabel21.setText("Bairro");
        painelEndereco.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        campoCidade.setText("Santa Inês");
        painelEndereco.add(campoCidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 170, 30));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(11, 13, 138));
        jLabel22.setText("CEP");
        painelEndereco.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 230, -1, -1));

        comboEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[Nenhum]", "AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RO", "RN", "RR", "RS", "SC", "SE", "SP", "TO" }));
        comboEstado.setSelectedItem("MA");
        painelEndereco.add(comboEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 180, 170, 30));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(11, 13, 138));
        jLabel23.setText("Cidade");
        painelEndereco.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, -1));

        try {
            campoCEP.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.###-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        painelEndereco.add(campoCEP, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 250, 130, 30));

        jTabbedPane1.addTab("Endereço", painelEndereco);

        painelPlano.setBackground(new java.awt.Color(157, 197, 188));
        painelPlano.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(11, 13, 138));
        jLabel24.setText("Valor Mensal");
        painelPlano.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(265, 90, -1, -1));

        comboPlano.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[Nenhum]" }));
        comboPlano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboPlanoActionPerformed(evt);
            }
        });
        painelPlano.add(comboPlano, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 370, 32));

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 102, 102));
        jLabel32.setText("R$");
        painelPlano.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 112, -1, -1));
        painelPlano.add(campoValor, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 140, 30));
        painelPlano.add(campoValorMensal, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 110, 140, 30));

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 102, 102));
        jLabel30.setText("R$");
        painelPlano.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 112, -1, -1));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(11, 13, 138));
        jLabel26.setText("Valor Total");
        painelPlano.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, -1, -1));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(11, 13, 138));
        jLabel27.setText("Plano/Serviço");
        painelPlano.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        comboTurma.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[Nenhuma]" }));
        comboTurma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTurmaActionPerformed(evt);
            }
        });
        painelPlano.add(comboTurma, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 370, 32));

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(11, 13, 138));
        jLabel28.setText("Turma");
        painelPlano.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, -1));

        campoDiaVencimento.setDay(28);
        campoDiaVencimento.setDecorationBordersVisible(true);
        painelPlano.add(campoDiaVencimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 370, -1));

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(11, 13, 138));
        jLabel29.setText("Dia Vencimento");
        painelPlano.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, -1, -1));

        renovacaoAuto.setText("Renovação Automática");
        renovacaoAuto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                renovacaoAutoActionPerformed(evt);
            }
        });
        painelPlano.add(renovacaoAuto, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 240, -1, -1));

        jTabbedPane1.addTab("Plano Atual", painelPlano);

        painelAdicional.setBackground(new java.awt.Color(157, 197, 188));
        painelAdicional.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(11, 13, 138));
        jLabel31.setText("Data Cadastro");
        painelAdicional.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));
        painelAdicional.add(campoDataCadastro, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 170, 30));

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(11, 13, 138));
        jLabel25.setText("Data Último Pagamento");
        painelAdicional.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, -1, -1));
        painelAdicional.add(campoDataUltimoPag, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 40, 170, 30));

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(11, 13, 138));
        jLabel33.setText("Data Final Plano");
        painelAdicional.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 90, -1, -1));
        painelAdicional.add(campoDataRenovacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 170, 30));
        painelAdicional.add(campoDataFimPlano, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 110, 170, 30));

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(11, 13, 138));
        jLabel34.setText("Data Vencimento");
        painelAdicional.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, -1));

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(11, 13, 138));
        jLabel35.setText("Data Renovação");
        painelAdicional.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));
        painelAdicional.add(campoDataVencimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 170, 30));

        botaoSetarVencimento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/imagensparaseremtrocadas/btnSetarVenc.png"))); // NOI18N
        botaoSetarVencimento.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoSetarVencimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSetarVencimentoActionPerformed(evt);
            }
        });
        painelAdicional.add(botaoSetarVencimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 180, 170, 30));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(11, 13, 138));
        jLabel4.setText("Histórico de Planos e Pagamento");
        painelAdicional.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, -1, -1));

        jInternalFrame1.setIconifiable(true);
        jInternalFrame1.setTitle("Histórico de Planos");
        jInternalFrame1.setVisible(true);
        jInternalFrame1.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 290, 100));

        jInternalFrame1.getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 290, -1));

        painelAdicional.add(jInternalFrame1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, 310, -1));

        jTabbedPane1.addTab("Adicional", painelAdicional);

        jPanel1.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 700));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 700));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botaoSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSairActionPerformed
        //controller.sairTela();
    }//GEN-LAST:event_botaoSairActionPerformed

    private void botaoEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoEditarActionPerformed
        trocaBotoes(true);
    }//GEN-LAST:event_botaoEditarActionPerformed

    private void botaoConcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoConcluirActionPerformed
        //controller.editarFuncionario();
    }//GEN-LAST:event_botaoConcluirActionPerformed

    private void campoRuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoRuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoRuaActionPerformed

    private void campoBairroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoBairroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoBairroActionPerformed

    private void comboPlanoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPlanoActionPerformed
        //controller.setarValorContrato();
    }//GEN-LAST:event_comboPlanoActionPerformed

    private void comboTurmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTurmaActionPerformed
        //controller.verificarQuantidadeLimiteAlunos();
    }//GEN-LAST:event_comboTurmaActionPerformed

    private void renovacaoAutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_renovacaoAutoActionPerformed
        //controller.setarValorContrato();
    }//GEN-LAST:event_renovacaoAutoActionPerformed

    private void botaoSetarVencimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSetarVencimentoActionPerformed
        //controller.setarDataVencimento();
    }//GEN-LAST:event_botaoSetarVencimentoActionPerformed

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
            java.util.logging.Logger.getLogger(AlunosDetalhes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AlunosDetalhes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AlunosDetalhes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AlunosDetalhes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AlunosDetalhes dialog = new AlunosDetalhes(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton botaoConcluir;
    private javax.swing.JButton botaoEditar;
    private javax.swing.JButton botaoSair;
    private javax.swing.JButton botaoSetarVencimento;
    private javax.swing.JTextField campoBairro;
    private javax.swing.JFormattedTextField campoCEP;
    private javax.swing.JFormattedTextField campoCPF;
    private javax.swing.JFormattedTextField campoCPFMae;
    private javax.swing.JFormattedTextField campoCPFPai;
    private javax.swing.JFormattedTextField campoCelular;
    private javax.swing.JFormattedTextField campoCelularMae;
    private javax.swing.JFormattedTextField campoCelularPai;
    private javax.swing.JTextField campoCidade;
    private com.toedter.calendar.JDateChooser campoDataCadastro;
    private com.toedter.calendar.JDateChooser campoDataFimPlano;
    private com.toedter.calendar.JDateChooser campoDataRenovacao;
    private com.toedter.calendar.JDateChooser campoDataUltimoPag;
    private com.toedter.calendar.JDateChooser campoDataVencimento;
    private javax.swing.JTextArea campoDescricao;
    private com.toedter.calendar.JDayChooser campoDiaVencimento;
    private com.toedter.calendar.JDateChooser campoNascimento;
    private javax.swing.JTextField campoNome;
    private javax.swing.JTextField campoNomeMae;
    private javax.swing.JTextField campoNomePai;
    private javax.swing.JTextField campoNum;
    private javax.swing.JTextField campoRua;
    private javax.swing.JFormattedTextField campoTelefone;
    private javax.swing.JFormattedTextField campoValor;
    private javax.swing.JFormattedTextField campoValorMensal;
    private javax.swing.JCheckBox checkAlteracoesDados;
    private javax.swing.JComboBox<String> comboEstado;
    private javax.swing.JComboBox<String> comboPlano;
    private javax.swing.JComboBox<String> comboTurma;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel painelAdicional;
    private javax.swing.JPanel painelDados;
    private javax.swing.JPanel painelEndereco;
    private javax.swing.JPanel painelPais;
    private javax.swing.JPanel painelPlano;
    private javax.swing.JCheckBox renovacaoAuto;
    // End of variables declaration//GEN-END:variables

    public void trocaBotoes(boolean editar){
        botaoEditar.setVisible(!editar);
        botaoConcluir.setVisible(editar);
        enableCampos(editar);
    }
    
    
    private void configsIniciais(){
    }
    
    private void enableCampos(boolean enable){

    }
}
