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
import Dao.ServicosDao;
import Dao.TurmasDao;
import Model.Aluno;
import Model.auxiliar.EnderecoAlunos;
import Model.auxiliar.Horarios;
import Model.auxiliar.Matriculas;
import Model.auxiliar.Servicos;
import Model.auxiliar.Turmas;
import View.AlunosView;
import View.ServicosView;
import View.TurmasView;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
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
    private final AlunosDao alunosDao = new AlunosDao();
    private final MatriculasDao matriculasDao = new MatriculasDao();
    private final TurmasDao turmasDao = new TurmasDao();
    private final ServicosDao servicosDao = new ServicosDao();
    private final EnderecoAlunosDao enderecoDao = new EnderecoAlunosDao();
    private final ConversaoDeDinheiro converterDinheiro = new ConversaoDeDinheiro();
    private final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();

    public AlunosController(AlunosView view) {
        this.view = view;
        this.tabelaDeAlunos = (DefaultTableModel)view.getTabelaAlunos().getModel();
        this.tabelaDePais = (DefaultTableModel)view.getTabelaPais().getModel();
        this.tabelaDeEnderecos = (DefaultTableModel)view.getTabelaEnderecos().getModel();
    }
    
    //Limpar tabela
    public void limparTabela(){
        int quantLinhas = this.view.getTabelaAlunos().getRowCount();    
        for(int quant=0; quant<quantLinhas; quant++){
            this.tabelaDeAlunos.removeRow(quant);
            this.tabelaDePais.removeRow(quant);
            this.tabelaDeEnderecos.removeRow(quant);
        }
    }
    
    //Lista todas as turmas 
    public void listarAlunos() throws SQLException, ParseException {
        ArrayList <Aluno> alunos = new ArrayList<>();
        ArrayList<Turmas> turmas = new ArrayList<>();
        ArrayList<Servicos> servicos = new ArrayList<>();
        ArrayList <EnderecoAlunos> enderecos = new ArrayList<>();
        alunos = this.alunosDao.selecionarTodosAlunos();
        if(alunos==null){
            view.exibeMensagem("Sem Alunos Cadastrados");
        } else{
            for(int linhas=0; linhas<alunos.size(); linhas++){
                enderecos = this.enderecoDao.pesquisarEndereco("SELECT * FROM tblEndAlunoseClientes WHERE codAluno = "+
                        alunos.get(linhas).getCodBanco());
                turmas = this.turmasDao.pesquisarTurmas("SELECT * FROM tblTurmas WHERE codTurma = "+
                        alunos.get(linhas).getTurma());
                servicos = this.servicosDao.pesquisarServicos("SELECT * FROM tblServicos WHERE codServico = "+
                        alunos.get(linhas).getPlano());
                
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
    }
    
    public void editarAlunos() throws SQLException, ParseException{
        if(this.view.getTabelaAlunos().getSelectedRow()!= -1){
            //Dados Alunos
            int linhaSelecionada = this.view.getTabelaAlunos().getSelectedRow();
            int codAluno = Integer.parseInt(tabelaDeAlunos.getValueAt(linhaSelecionada, 0).toString());
            String nome = this.tabelaDeAlunos.getValueAt(linhaSelecionada, 1).toString();
            String nomeTurma = view.getComboTurmas().getSelectedItem().toString();
            int codTurma = Integer.parseInt(String.valueOf(nomeTurma));
            String nomeServico = view.getComboTurmas().getSelectedItem().toString();
            int codServico = Integer.parseInt(String.valueOf(nomeServico));
            BigDecimal valorContrato = new BigDecimal(converterDinheiro.converterParaBigDecimal(this.tabelaDeAlunos.getValueAt(linhaSelecionada, 4).toString()).toString());
            BigDecimal valorDebito = new BigDecimal(converterDinheiro.converterParaBigDecimal(this.tabelaDeAlunos.getValueAt(linhaSelecionada, 5).toString()).toString());

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
            
            Aluno aluno = new Aluno(codAluno, nome, alunoAnterior.getCpf(), alunoAnterior.getRg(), telefoneMae, cep, estado, estado, nomeMae, nomePai, telefoneMae, telefonePai, cpfMae, cpfPai, codTurma, codAluno, estado, valorDebito, valorContrato);
            ArrayList <Horarios> horarios = new ArrayList <>();
            if(!view.getCampoHorario().equals("")){
            turma = new Turmas(codTurma, nome, subgrupo, quantAlunos, quantidadeMax, diasDaSemana, horario);    

        
        int diferenca, contador = diasDaSemanaUnitarios.size();
        while(contador>0){
        diferenca = diasDaSemanaUnitarios.size()-contador;
        Horarios auxiliar = new Horarios(codTurma, diasDaSemanaUnitarios.get(diferenca), horario, 0, codTurma);
        horarios.add(auxiliar);
        contador--;
        }
        }
        
        //Inserindo Dados
        if(nome.equals("")|| horario.equals("") ||view.testeValidacaoHorario()==false){
         view.exibeMensagem("Campos Preenchidos Incorretamente");
        } else{
            TurmasDao turmaDao = new TurmasDao();
            turmaDao.atualizarDados(turma, horarios);
            view.exibeMensagem("Sucesso!");
            //Limpando Campos
            limparTabela();
            listarTurmas();
        }
            //Turmas 
        }
        
        else{this.view.exibeMensagem("Erro, Nenhuma Turma Selecionada!");}
        
    
    }
    
    
    public void removerTurma() throws SQLException{
        if(this.view.getTabelaAlunos().getSelectedRow()!= -1){
            int linhaSelecionada = this.view.getTabelaAlunos().getSelectedRow();
            int codTurma = Integer.parseInt(tabelaDeTurmas.getValueAt(linhaSelecionada, 0).toString());
            
            turmasDao.removerTurma(codTurma);
            this.view.exibeMensagem("Sucesso");
            limparTabela();
            this.view.desabilitarVisibilidadeComponente();
            listarTurmas();
        }
        else{this.view.exibeMensagem("Erro, Nenhuma Turma Selecionada!");}
    }
    
    //Buscar turmas no campo de busca
    public void buscarTurmas() throws Exception{
        String nomeTurma = view.getCampoDePesquisa().getText();
        ArrayList <Turmas> turmas = turmasDao.pesquisarPorNome(nomeTurma);
        
        if(nomeTurma.equals("")){limparTabela();listarTurmas();}
        else{
            if(turmas==null){view.exibeMensagem("Turma Não Encontrada!"); limparTabela();}
            else{
                limparTabela();
                for(int linhas = 0; linhas<turmas.size(); linhas++){
                    if(turmas.get(linhas).getQuantidadeMaximaAlunos()==0){
                        Object[] dadosDaTabela = {turmas.get(linhas).getCodBanco(), 
                        turmas.get(linhas).getNomeTurma(),turmas.get(linhas).getSubgrupo(),
                        "",turmas.get(linhas).getQuantidadeAlunos(),
                        this.converterDias.converterDiasDaSemana(turmas.get(linhas).getDiasDaSemana()),
                        converterHora.parseHour(turmas.get(linhas).getHorario())};
                        this.tabelaDeTurmas.addRow(dadosDaTabela);
                    }
                    else{
                        Object[] dadosDaTabela = {turmas.get(linhas).getCodBanco(), 
                        turmas.get(linhas).getNomeTurma(),turmas.get(linhas).getSubgrupo(),
                        turmas.get(linhas).getQuantidadeMaximaAlunos(),turmas.get(linhas).getQuantidadeAlunos(),
                        this.converterDias.converterDiasDaSemana(turmas.get(linhas).getDiasDaSemana()),
                        converterHora.parseHour(turmas.get(linhas).getHorario())};
                        this.tabelaDeTurmas.addRow(dadosDaTabela);
                    }
                }
            }
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
            //view.getValorContrato().setText(valorContrato.toString());
        }
    }
    
    private String converterMatricula(int anoAtual, int codTurma, int codAluno, int codPlano) {
        String matricula = anoAtual+"T"+codTurma+"A"+codAluno+"S"+codPlano;
        
                return  matricula; 
    }
    
    private Aluno alunoAnterior(int codAluno) throws SQLException, ParseException{
         return  alunosDao.pesquisarAlunos("SELECT * FROM tblAlunos WHERE codAluno = "+codAluno).get(0);
    }
}
