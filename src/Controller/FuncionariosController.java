/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.auxiliar.ConversaoDeDinheiro;
import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Dao.EnderecoFuncionarioDao;
import Dao.FuncionarioDao;
import Dao.LogAçoesFuncionarioDao;
import Model.Funcionario;
import Model.auxiliar.LogAçoesFuncionario;
import View.Funcionarios;
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
public class FuncionariosController {
    private final Funcionarios view;
    private final DefaultTableModel tabelaDeFuncionarios;
    private final FuncionarioDao funcionarioDao = new FuncionarioDao();
    private final EnderecoFuncionarioDao enderecoDao = new EnderecoFuncionarioDao();
    private final LogAçoesFuncionarioDao logDao = new LogAçoesFuncionarioDao();
    private final ConversaoDeDinheiro converterDinheiro = new ConversaoDeDinheiro();
    private final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();

    public FuncionariosController(Funcionarios view) {
        this.view = view;
        this.tabelaDeFuncionarios = (DefaultTableModel) view.getTabelaFuncionarios().getModel();
    }
    
    //Limpar tabela
    public void limparTabela(){
        int quantLinhas = this.view.getTabelaFuncionarios().getRowCount();
        for(int quant=0; quant<quantLinhas; quant++){
            this.tabelaDeFuncionarios.removeRow(0);
        }
    }
    
    //Lista todas as turmas 
    public void listarFuncionarios() {
        ArrayList <Funcionario> funcionarios = this.funcionarioDao.selecionarTodosFuncionarios();
        this.buscas(funcionarios);
    }
    
    public void editarFuncionarios(){
        if(this.view.getTabelaFuncionarios().getSelectedRow()!= -1){
            //Dados Alunos
            int linhaSelecionada = this.view.getTabelaFuncionarios().getSelectedRow();
            int codAluno = Integer.parseInt(tabelaDeFuncionarios.getValueAt(linhaSelecionada, 0).toString());
            String nome = this.tabelaDeFuncionarios.getValueAt(linhaSelecionada, 1).toString();
            String cpf = this.tabelaDeFuncionarios.getValueAt(linhaSelecionada, 2).toString();
            String telefone = this.tabelaDeFuncionarios.getValueAt(linhaSelecionada, 3).toString();
            String celular = this.tabelaDeFuncionarios.getValueAt(linhaSelecionada, 4).toString();
            String cargo = this.tabelaDeFuncionarios.getValueAt(linhaSelecionada, 5).toString();
            BigDecimal valorSalario = new BigDecimal(converterDinheiro.converterParaBigDecimal(this.tabelaDeFuncionarios.getValueAt(linhaSelecionada, 6).toString()).toString());
            String usuario = cpf;
            
            String telasPermitidas = view.getTelasPermitidas();
            Funcionario funcionarioAnterior = this.funcionarioAnterior(codAluno);
            
            Funcionario funcionario = new Funcionario(codAluno, nome, cpf, "", telefone, celular, funcionarioAnterior.getEmail(), funcionarioAnterior.getDatadenascimento(),
                    usuario, funcionarioAnterior.getSenha(), valorSalario, cargo, telasPermitidas, null);
        
//Inserindo Dados
        if(nome.equals("")|| cpf.equals("   .   .   -  ")){
         view.exibeMensagem("Campos Preenchidos Incorretamente");
        } else{
            funcionarioDao.atualizarDados(funcionario);
            
            ArrayList <Funcionario> funcionarios = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE status = 'Ativo'");
            if(funcionarios!=null){
                String acao = "Edição de Dados de Funcionário";
                String descricao = "Editou os dados do funcionário "+nome;
                this.setarLog(funcionarios, acao, descricao);
            }
            view.exibeMensagem("Sucesso!");
            //Limpando Campos
            listarFuncionarios();
        }
            //Turmas 
        }
        
        else{this.view.exibeMensagem("Erro, Nenhum Funcionário Selecionado!");}
        
    
    }
    
    
    public void removerFuncionario() throws SQLException, ParseException, Exception{
        if(this.view.getTabelaFuncionarios().getSelectedRow()!= -1){
            int linhaSelecionada = this.view.getTabelaFuncionarios().getSelectedRow();
            if(this.tabelaDeFuncionarios.getValueAt(linhaSelecionada, 5).equals("Gerente")){
                view.exibeMensagem("Não é Permitido Remover o Gerente!");
            }
            else{
                int codAluno = Integer.parseInt(tabelaDeFuncionarios.getValueAt(linhaSelecionada, 0).toString());

                funcionarioDao.removerFuncionario(codAluno);
                this.view.exibeMensagem("Sucesso");
                listarFuncionarios();
            }
        }
        else{this.view.exibeMensagem("Erro, Nenhum Funcionário Selecionado!");}
    }
    
    //Buscar Funcionários no campo de busca
    public void buscarFuncionarios(){
        String funcionarioPesquisa = view.getCampoBuscar().getText();
        System.out.println(funcionarioPesquisa);
        ArrayList <Funcionario> funcionarios = funcionarioDao.pesquisarPorNome(funcionarioPesquisa);
        if(funcionarioPesquisa.equals("")){listarFuncionarios();}
        else{this.buscas(funcionarios);}        
    }
    
    //Buscar Aniversariantes
    private void buscarAniversariantes(){
        ArrayList <Funcionario> funcionarios = funcionarioDao.selecionarTodosFuncionarios();
        ArrayList <Funcionario> funcionariosAniversariantes = new ArrayList<>();
        Date aniversario;
        for(int linhas = 0; linhas<funcionarios.size(); linhas++){
            aniversario = funcionarios.get(linhas).getDatadenascimento();
            
            if(converterData.aniversarianteDoDia(aniversario)){
                funcionariosAniversariantes.add(funcionarios.get(linhas));
            }
        }
        this.buscas(funcionariosAniversariantes);
    }
    
    //Listar
    public void listar(){
        String comboListar = view.getComboListar().getSelectedItem().toString();
        switch(comboListar){
            case "Todos":
                listarFuncionarios();
            break;
            
            case "Aniversariantes":
                this.buscarAniversariantes();
                
            break;
        }
    }

    
    private Funcionario funcionarioAnterior(int codFuncionario){
         return  funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE codFuncionario = "+codFuncionario).get(0);
    }
    
    private void buscas(ArrayList <Funcionario> listar){
        ArrayList<Funcionario> funcionarios = listar; 
        
        if(funcionarios==null){view.exibeMensagem("Funcionário Não Encontrado!"); limparTabela();}
        else{
            limparTabela();
            for(int linhas = 0; linhas<funcionarios.size(); linhas++){
            Object[] dadosDaTabelaFuncionarios = {funcionarios.get(linhas).getCodBanco(), 
            funcionarios.get(linhas).getNome(),funcionarios.get(linhas).getCpf(), 
            funcionarios.get(linhas).getTelefone(), funcionarios.get(linhas).getCelular(),funcionarios.get(linhas).getCargo(),
            funcionarios.get(linhas).getSalario()};
            this.tabelaDeFuncionarios.addRow(dadosDaTabelaFuncionarios);

            }
        }
    }
    
    public void selecionarTabela(){
        int linhaSelecionada = view.getTabelaFuncionarios().getSelectedRow();
        
        if(linhaSelecionada!=-1){
            int codFuncionario = Integer.parseInt(tabelaDeFuncionarios.getValueAt(linhaSelecionada, 0).toString());
            
            view.getBotaoPermissoes().setVisible(true);
            this.pegarTelasPermitidas(codFuncionario);
        }
        else{
            view.getBotaoPermissoes().setVisible(false);
        }
    }
    
    private void pegarTelasPermitidas(int codFuncionario){
        Funcionario funcionario = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE codFuncionario = "+codFuncionario).get(0);
        String[] telas = funcionario.getTelasPermitidas().split(",");
        for(int linhas=0; linhas<telas.length; linhas++){
            telas[linhas]=telas[linhas].replace(",", "");
            
            switch(telas[linhas]){
                case "1":
                    view.getRadioAlunosSim().setSelected(true);
                break;
                case "2":
                    view.getRadioTurmasSim().setSelected(true);
                break;
                case "3":
                    view.getRadioProdutosSim().setSelected(true);
                break;
                case "4":
                    view.getRadioServicosSim().setSelected(true);
                break;
                case "5":
                    view.getRadioFinanceiroSim().setSelected(true);
                break;
                case "6":
                    view.getRadioImprimirSim().setSelected(true);
                break;
                case "7":
                    view.getRadioBackupSim().setSelected(true);
                break;
                case "8":
                    view.getRadioCaixaSim().setSelected(true);
                break;
            }
        }
        view.setTelasPermitidas(funcionario.getTelasPermitidas());
        
    }
    
    public void atualizarTelasPermitidas(){
        int linhaSelecionada = view.getTabelaFuncionarios().getSelectedRow();
        
        if(linhaSelecionada!=-1){
            int codFuncionario = Integer.parseInt(tabelaDeFuncionarios.getValueAt(linhaSelecionada, 0).toString());
            Funcionario funcionario = new Funcionario(codFuncionario, null, null, null, null, null, null, null, null, null, BigDecimal.ZERO, null, view.getTelasPermitidas(), null);
            
            funcionarioDao.atualizarTelasPermitidas(funcionario);
        }
    }
    
     private LogAçoesFuncionario setarLog(ArrayList <Funcionario> funcionarios, String acao, String descricao){
        Funcionario funcionario = funcionarios.get(0);
        Date dataEvento = new Date();
        LogAçoesFuncionario logAcao = new LogAçoesFuncionario(funcionario.getCodBanco(), dataEvento, acao, descricao);
        return logAcao;
    }
}
