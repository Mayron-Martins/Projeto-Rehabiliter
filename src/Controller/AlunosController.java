/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.auxiliar.ConversaoDeDinheiro;
import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Dao.AlunosDao;
import Dao.EnderecoAlunosDao;
import Dao.MatriculasDao;
import Dao.PlanosDao;
import Dao.ServicosDao;
import Dao.TurmasDao;
import Model.Aluno;
import Model.auxiliar.EnderecoAlunos;
import Model.auxiliar.Matriculas;
import Model.auxiliar.Planos;
import Model.auxiliar.Servicos;
import Model.auxiliar.Turmas;
import View.AlunosView;
import View.ServicosView;
import View.TurmasView;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mayro
 */
public class AlunosController {
    private final AlunosView view;
    private final DefaultTableModel tabelaDeAlunos;
    private final DefaultTableModel tabelaDePais;
    private final DefaultTableModel tabelaDeEnderecos;
    private final DefaultTableModel tabelaDePlanos;
    private final AlunosDao alunosDao = new AlunosDao();
    private final MatriculasDao matriculasDao = new MatriculasDao();
    private final TurmasDao turmasDao = new TurmasDao();
    private final ServicosDao servicosDao = new ServicosDao();
    private final EnderecoAlunosDao enderecoDao = new EnderecoAlunosDao();
    private final PlanosDao planosDao = new PlanosDao();
    private final ConversaoDeDinheiro converterDinheiro = new ConversaoDeDinheiro();
    private final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();

    public AlunosController(AlunosView view) {
        this.view = view;
        this.tabelaDeAlunos = (DefaultTableModel)view.getTabelaAlunos().getModel();
        this.tabelaDePais = (DefaultTableModel)view.getTabelaPais().getModel();
        this.tabelaDeEnderecos = (DefaultTableModel)view.getTabelaEnderecos().getModel();
        this.tabelaDePlanos = (DefaultTableModel) view.getTabelaPlanos().getModel();
    }
    
    //Limpar tabela
    public void limparTabela(){
        int quantLinhas = this.view.getTabelaAlunos().getRowCount();
        for(int quant=0; quant<quantLinhas; quant++){
            this.tabelaDeAlunos.removeRow(0);
            this.tabelaDePlanos.removeRow(0);
            this.tabelaDePais.removeRow(0);
            this.tabelaDeEnderecos.removeRow(0);
        }
    }
    
    //Lista todas as turmas 
    public void listarAlunos() throws SQLException, ParseException, Exception {
        ArrayList <Aluno> alunos = this.alunosDao.selecionarTodosAlunos();
        this.buscas(alunos);
    }
    
    public void editarAlunos() throws SQLException, ParseException, Exception{
        if(this.view.getTabelaAlunos().getSelectedRow()!= -1){
            //Dados Alunos
            int linhaSelecionada = this.view.getTabelaAlunos().getSelectedRow();
            int codAluno = Integer.parseInt(tabelaDeAlunos.getValueAt(linhaSelecionada, 0).toString());
            String nome = this.tabelaDeAlunos.getValueAt(linhaSelecionada, 1).toString();
            String nomeTurma = view.getComboTurmas().getSelectedItem().toString();
            int codTurma = Integer.parseInt(String.valueOf(nomeTurma.charAt(0)));
            String nomeServico = this.tabelaDeAlunos.getValueAt(linhaSelecionada, 3).toString();
            int codServico = Integer.parseInt(String.valueOf(nomeServico.charAt(0)));
            BigDecimal valorContrato = new BigDecimal(converterDinheiro.converterParaBigDecimal(this.tabelaDeAlunos.getValueAt(linhaSelecionada, 4).toString()).toString());
            BigDecimal valorDebito = new BigDecimal(converterDinheiro.converterParaBigDecimal(this.tabelaDeAlunos.getValueAt(linhaSelecionada, 5).toString()).toString());

            //Dados Planos
            int chavePlano = Integer.parseInt(this.tabelaDePlanos.getValueAt(linhaSelecionada, 0).toString());
            int diaVencimento = Integer.parseInt(this.tabelaDePlanos.getValueAt(linhaSelecionada, 1).toString());
            String situacao = this.tabelaDePlanos.getValueAt(linhaSelecionada, 2).toString();
            
            //Dados Pais
            String nomePai = this.tabelaDePais.getValueAt(linhaSelecionada, 0).toString();
            String cpfPai = this.tabelaDePais.getValueAt(linhaSelecionada, 1).toString();
            String telefonePai = this.tabelaDePais.getValueAt(linhaSelecionada, 2).toString();
            String nomeMae = this.tabelaDePais.getValueAt(linhaSelecionada, 3).toString();
            String cpfMae = this.tabelaDePais.getValueAt(linhaSelecionada, 4).toString();
            String telefoneMae = this.tabelaDePais.getValueAt(linhaSelecionada, 5).toString();
            
            //Dados Endereço
            String logradouro = this.tabelaDeEnderecos.getValueAt(linhaSelecionada, 0).toString();
            String numero = this.tabelaDeEnderecos.getValueAt(linhaSelecionada, 1).toString();
            String bairro = this.tabelaDeEnderecos.getValueAt(linhaSelecionada, 2).toString();
            String cidade = this.tabelaDeEnderecos.getValueAt(linhaSelecionada, 3).toString();
            String estado = view.getComboEstado().getSelectedItem().toString();
            String cep = this.tabelaDeEnderecos.getValueAt(linhaSelecionada, 5).toString();
            
            //Dados matrícula
            int anoAtual = converterData.obterAnoAtual();
            String nomeMatricula = this.converterMatricula(anoAtual, codTurma, codAluno, codServico);
            Aluno alunoAnterior = this.alunoAnterior(codAluno);
            
            Aluno aluno = new Aluno(codAluno, nome, alunoAnterior.getCpf(), alunoAnterior.getRg(), alunoAnterior.getTelefone(), 
                    alunoAnterior.getCelular(), alunoAnterior.getEmail(), alunoAnterior.getDatadenascimento(), 
                    nomeMae, nomePai, telefoneMae, telefonePai, cpfMae, cpfPai, codTurma, codServico, alunoAnterior.getDescricao(), 
                    valorDebito, valorContrato);
            
            Matriculas matricula = new Matriculas(codAluno, codTurma, codAluno, anoAtual, nomeMatricula);
            EnderecoAlunos endereco = new EnderecoAlunos(codAluno, codAluno, logradouro, bairro, numero, nomeMae, telefoneMae, cidade, estado, cep);
            Planos planoAluno = new Planos(codAluno, codTurma, codServico, diaVencimento, null, null, situacao);
        
        //Inserindo Dados
        if(nome.equals("")|| logradouro.equals("") || numero.equals("")|| bairro.equals("")|| cidade.equals("")||
        estado.equals("[Nenhum]")|| cep.equals("  .   -   ")){
         view.exibeMensagem("Campos Preenchidos Incorretamente");
        } else{
            alunosDao.atualizarDados(aluno, endereco, matricula, planoAluno, anoAtual);
            view.exibeMensagem("Sucesso!");
            //Limpando Campos
            listarAlunos();
        }
            //Turmas 
        }
        
        else{this.view.exibeMensagem("Erro, Nenhum Aluno Selecionado!");}
        
    
    }
    
    
    public void removerAluno() throws SQLException, ParseException, Exception{
        if(this.view.getTabelaAlunos().getSelectedRow()!= -1){
            int linhaSelecionada = this.view.getTabelaAlunos().getSelectedRow();
            int codAluno = Integer.parseInt(tabelaDeAlunos.getValueAt(linhaSelecionada, 0).toString());
            
            
            alunosDao.removerAluno(codAluno);
            this.view.exibeMensagem("Sucesso");
            listarAlunos();
        }
        else{this.view.exibeMensagem("Erro, Nenhum Aluno Selecionado!");}
    }
    
    //Buscar turmas no campo de busca
    public void buscarAlunos() throws Exception{
        String alunoPesquisa = view.getCampoPesquisa().getText();
        ArrayList <Aluno> alunos = alunosDao.pesquisarPorNome(alunoPesquisa);
        if(alunoPesquisa.equals("")){listarAlunos();}
        else{this.buscas(alunos);}        
    }
    
    //Buscar Aniversariantes
    private void buscarAniversariantes() throws SQLException, ParseException, Exception{
        ArrayList <Aluno> alunos = alunosDao.selecionarTodosAlunos();
        ArrayList <Aluno> alunosAniversariantes = new ArrayList<>();
        Date aniversario;
        for(int linhas = 0; linhas<alunos.size(); linhas++){
            aniversario = alunos.get(linhas).getDatadenascimento();
            
            if(converterData.aniversarianteDoDia(aniversario)){
                alunosAniversariantes.add(alunos.get(linhas));
            }
        }
        this.buscas(alunosAniversariantes);
    }
    
    //Buscar Débito existentes
    private void buscarDebitos(char opcao) throws SQLException, ParseException, Exception{
        ArrayList <Aluno> alunos = alunosDao.selecionarTodosAlunos();
        ArrayList <Aluno> alunosComDebito = new ArrayList<>();
        ArrayList <Aluno> alunosSemDebito = new ArrayList<>();
        BigDecimal debito;
        for(int linhas = 0; linhas<alunos.size(); linhas++){
            debito = new BigDecimal(alunos.get(linhas).getDebito().toString());
            if(debito.compareTo(BigDecimal.ZERO)>0){
                alunosComDebito.add(alunos.get(linhas));
            } else {
                alunosSemDebito.add(alunos.get(linhas));
            }
        }
        
        switch(opcao){
            case 'C':
                this.buscas(alunosComDebito);
            break;
            
            case 'S':
                this.buscas(alunosSemDebito);
            break;
        }
    }
    
    //Listar
    public void listar() throws ParseException, Exception{
        String comboListar = view.getComboListar().getSelectedItem().toString();
        switch(comboListar){
            case "Todos":
                listarAlunos();
            break;
            
            case "Aniversariantes":
                this.buscarAniversariantes();
                
            break;
            
            case "Pendentes":
                this.buscarDebitos('C');
            break;
            
            case "Pagos": 
                this.buscarDebitos('S');
            break;
        }
    }

    public void verificacaoDeTurmaEServico() throws SQLException {
        ArrayList <Turmas> turmas = turmasDao.selecionarTodasTurmas();
        ArrayList <Servicos> servicos = servicosDao.selecionarTodosServicos();
        ServicosView telaServicos = new ServicosView();
        TurmasView telaTurmas = new TurmasView();
        
        if(turmas==null){
            view.exibeMensagem("Sem Turmas Cadastradas! Adicione Alguma Para Entrar Nessa Tela!");
            telaTurmas.setVisible(true);
            if(view.isVisible()){
               view.dispose(); 
            }
        }
        else{
            for(int linhas=0; linhas<turmas.size(); linhas++){
            String subgrupo = "";    
            if(turmas.get(linhas).getSubgrupo()!=null){subgrupo = "-"+turmas.get(linhas).getSubgrupo();}
            view.getComboTurmas().addItem(turmas.get(linhas).getCodBanco()+"."+turmas.get(linhas).getNomeTurma()+subgrupo);
            }
        }
        
        if(servicos==null){
            view.exibeMensagem("Sem Serviços Cadastrados! Adicione Algum Para Entrar Nessa Tela!");
            telaServicos.setVisible(true);
            if(view.isVisible()){
               view.dispose(); 
            }
        }
        else{
            for(int linhas=0; linhas<servicos.size(); linhas++){ 
            view.getComboServicos().addItem(servicos.get(linhas).getCodBanco()+"."+servicos.get(linhas).getNome()+"-"+servicos.get(linhas).getFormaPagamento());
            }
        }
    }
    
    public void setarValorContrato() throws SQLException{
        if(view.getComboServicos().getSelectedIndex()>0){
            int linhaSelecionada = view.getTabelaAlunos().getSelectedRow();
            String nomeServico = view.getComboServicos().getSelectedItem().toString();
            int codServico = Integer.parseInt(String.valueOf(nomeServico.charAt(0)));
            ArrayList<Servicos> servicos = servicosDao.pesquisarServicos("SELECT * FROM tblServicos WHERE codServico = "+codServico);
            
            String metodoDePagamento = servicos.get(0).getFormaPagamento();
            
            BigDecimal valorContrato = null;
            if(metodoDePagamento.equals("Dinheiro")){valorContrato = new BigDecimal(servicos.get(0).getValorVista().toString());}
            if(metodoDePagamento.equals("Boleto")){valorContrato = new BigDecimal(servicos.get(0).getValorBoleto().toString());}
            if(metodoDePagamento.equals("Cartão de Crédito")){valorContrato = new BigDecimal(servicos.get(0).getValorPrazoCredito().toString());}
            if(metodoDePagamento.equals("Cartão de Débito")){valorContrato = new BigDecimal(servicos.get(0).getValorPrazoDebito().toString());}   
            
            view.getTabelaAlunos().setValueAt(valorContrato.toString(), linhaSelecionada, 4);
            view.getTabelaAlunos().setValueAt(nomeServico, linhaSelecionada, 3);
        }
    }
    
    private String converterMatricula(int anoAtual, int codTurma, int codAluno, int codPlano) {
        String matricula = anoAtual+"T"+codTurma+"A"+codAluno+"S"+codPlano;
        
                return  matricula; 
    }
    
    private Aluno alunoAnterior(int codAluno) throws SQLException, ParseException{
         return  alunosDao.pesquisarAlunos("SELECT * FROM tblAlunos WHERE codAluno = "+codAluno).get(0);
    }
    
    private void buscas(ArrayList <Aluno> listar) throws Exception{
        ArrayList<Turmas> turmas = new ArrayList<>();
        ArrayList<Servicos> servicos = new ArrayList<>();
        ArrayList <EnderecoAlunos> enderecos = new ArrayList<>();
        ArrayList <Planos> planos;
        ArrayList <Aluno> alunos = listar;
        
        removerSelecaoBox();
        if(alunos==null){view.exibeMensagem("Aluno Não Encontrado!"); limparTabela();}
        else{
            limparTabela();
            for(int linhas = 0; linhas<alunos.size(); linhas++){
            enderecos = this.enderecoDao.pesquisarEndereco("SELECT * FROM tblEndAlunoseClientes WHERE codAluno = "+
                    alunos.get(linhas).getCodBanco());
            turmas = this.turmasDao.pesquisarTurmas("SELECT * FROM tblTurmas WHERE codTurma = "+
                    alunos.get(linhas).getTurma());
            servicos = this.servicosDao.pesquisarServicos("SELECT * FROM tblServicos WHERE codServico = "+
                    alunos.get(linhas).getPlano());
            planos = this.planosDao.pesquisarPlanos("SELECT * FROM tblPlanos WHERE codAluno = "+alunos.get(linhas).getCodBanco());
            
            
            //Inserindo dados na tabela de alunos
            String subgrupo="";
            if(turmas.get(0).getSubgrupo()!=null){subgrupo = "-"+turmas.get(0).getSubgrupo();}


            Object[] dadosDaTabelaAlunos = {alunos.get(linhas).getCodBanco(), 
            alunos.get(linhas).getNome(),turmas.get(0).getCodBanco()+"."+turmas.get(0).getNomeTurma()+subgrupo, 
            servicos.get(0).getCodBanco()+"."+servicos.get(0).getNome()+"-"+servicos.get(0).getFormaPagamento(), alunos.get(linhas).getValorContrato(),
            alunos.get(linhas).getDebito()};
            this.tabelaDeAlunos.addRow(dadosDaTabelaAlunos);
            this.view.getComboTurmas().setSelectedItem(turmas.get(0).getCodBanco()+"."+turmas.get(0).getNomeTurma()+subgrupo);
            this.view.getComboServicos().setSelectedItem(servicos.get(0).getCodBanco()+"."+servicos.get(0).getNome()+"-"+servicos.get(0).getFormaPagamento());
            
            
            //Inserindo dados na tabela de Planos
            int chavePlano = planos.get(0).getChavePlano();
            int diaVencimento = planos.get(0).getDiaVencimento();
            String situacao = planos.get(0).getSituacao();
            
            
            
            
            
            Object[] dadosDaTabelaPlanos = {chavePlano, diaVencimento, situacao};
            this.tabelaDePlanos.addRow(dadosDaTabelaPlanos);
            
            
            //Inserino dados na tabela de Pais
            String telefonePai = alunos.get(linhas).getTelefonedopai();
            String telefoneMae = alunos.get(linhas).getTelefonedamae();
            String cpfPai = alunos.get(linhas).getCpfdopai();
            String cpfMae = alunos.get(linhas).getCpfdamae();

            Object[] dadosDaTabelaPais = {alunos.get(linhas).getNomedopai(), 
            cpfPai,telefonePai, 
            alunos.get(linhas).getNomedamae(),cpfMae,
            telefoneMae};
            this.tabelaDePais.addRow(dadosDaTabelaPais);

            //Inserindo dados na tabela Endereços
            String cep = enderecos.get(0).getCep();
            Object[] dadosDaTabelaEnderecos  = {enderecos.get(0).getLogradouro(), 
            enderecos.get(0).getNumero(),enderecos.get(0).getBairro(), 
            enderecos.get(0).getCidade(),enderecos.get(0).getEstado(), cep};
            this.tabelaDeEnderecos.addRow(dadosDaTabelaEnderecos);
            this.view.getComboEstado().setSelectedItem(enderecos.get(0).getEstado());

            }
        }
       ativarSelecaoBox();
    }
    
    public void removerSelecaoBox(){
        this.view.getComboTurmas().setEnabled(false);
        this.view.getComboServicos().setEnabled(false);
        this.view.getComboEstado().setEnabled(false);
        this.view.getComboListar().setEnabled(false);
    }
    public void ativarSelecaoBox(){
        this.view.getComboTurmas().setEnabled(true);
        this.view.getComboServicos().setEnabled(true);
        this.view.getComboEstado().setEnabled(true);
        this.view.getComboListar().setEnabled(true);
    }
}
