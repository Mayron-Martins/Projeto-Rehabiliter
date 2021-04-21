/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.auxiliar.ConversaoDeDinheiro;
import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Dao.FuncionarioDao;
import Dao.LogAçoesFuncionarioDao;
import Model.Funcionario;
import Model.auxiliar.LogAçoesFuncionario;
import View.Funcionarios;
import View.LoginFuncionario;
import View.LoginGerente;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mayro
 */
public class FuncionariosController {
    protected final Funcionarios view;
    protected final DefaultTableModel tabelaDeFuncionarios;
    protected final FuncionarioDao funcionarioDao = new FuncionarioDao();
    protected final LogAçoesFuncionarioDao logDao = new LogAçoesFuncionarioDao();
    protected final ConversaoDeDinheiro converterDinheiro = new ConversaoDeDinheiro();
    protected final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();

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
        int linhaSelecionada = view.getComboListar().getSelectedIndex();
        if(linhaSelecionada<2){
            String situacao = view.getComboListar().getSelectedItem().toString();
            situacao = situacao.substring(0, situacao.length()-1);
            ArrayList <Funcionario> funcionarios = this.funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE situacao = '"+situacao+"'");
            this.buscas(funcionarios);
        }
        else{
            this.buscarAniversariantes();
        }
    }
    
    public void editarFuncionario(){
        if(this.view.getTabelaFuncionarios().getSelectedRow()!= -1){
            //Dados Alunos
                int linhaSelecionada = this.view.getTabelaFuncionarios().getSelectedRow();
                int codFuncionario = Integer.parseInt(tabelaDeFuncionarios.getValueAt(linhaSelecionada, 0).toString());
                String situacao = tabelaDeFuncionarios.getValueAt(linhaSelecionada, 3).toString();
                
                if(!situacao.equals("Desvinculado")){
                    String nome = tabelaDeFuncionarios.getValueAt(linhaSelecionada, 1).toString();
                    String cargo = tabelaDeFuncionarios.getValueAt(linhaSelecionada, 2).toString();

                    Funcionario funcionario = new Funcionario(codFuncionario, nome, cargo, situacao);

                    //Inserindo Dados
                    if(nome.equals("")){
                        view.exibeMensagem("Campos Preenchidos Incorretamente");
                    } else{
                        funcionarioDao.atualizarSituacao(funcionario);

                        this.setarLog("Edição de Dados de Funcionário", "Editou os dados do funcionário "+nome);
                        
                        view.exibeMensagem("Sucesso!");
                        //Limpando Campos
                        listarFuncionarios();
                    } 
                }else{
                    view.exibeMensagem("Não é possível editar os dados de um Funcionário Desvinculado");
                }
        }
        else{this.view.exibeMensagem("Erro, Nenhum Funcionário Selecionado!");}
    }
    
    public void editarVariosFuncionario(){
        int totalLinhas = tabelaDeFuncionarios.getRowCount();
        if(totalLinhas>0){
            boolean erros = false;
            String erro = "(";
            for(int linhas=0; linhas<totalLinhas; linhas++){
                int codFuncionario = Integer.parseInt(tabelaDeFuncionarios.getValueAt(linhas, 0).toString());
                String situacao = tabelaDeFuncionarios.getValueAt(linhas, 3).toString();
                String nome = tabelaDeFuncionarios.getValueAt(linhas, 1).toString();
                
                if(!situacao.equals("Desvinculado")){  
                    String cargo = tabelaDeFuncionarios.getValueAt(linhas, 2).toString();

                    Funcionario funcionario = new Funcionario(codFuncionario, nome, cargo, situacao);

                    //Inserindo Dados
                    if(nome.trim().equals("")){
                        erros=true;
                        erro+=nome+", ";
                    } else{
                        funcionarioDao.atualizarSituacao(funcionario);
                    } 
                }else{
                    erros=true;
                    erro+=nome+", ";
                }
            }
            this.setarLog("Edição de Dados de Vários Funcionários", "Editou os dados de vários funcionários");
            
            if(erros){
                view.mensagemCritica("Não foi possível editar os dados dos funcionários "+erro.substring(0, erro.length()-2) +")"
                        + ", Verifique se os dados foram preenchidos corretamente ou se não estão desvinculados.", "Atenção");
            }
            
            view.exibeMensagem("Sucesso!");
            //Limpando Campos
            listarFuncionarios();
        }
        else{view.mensagemCritica("Sem dados na tabela!", "Erro");}
    }
    
    public void removerFuncionario() {
        if(this.view.getTabelaFuncionarios().getSelectedRow()!= -1){
            int linhaSelecionada = this.view.getTabelaFuncionarios().getSelectedRow();
            if(this.tabelaDeFuncionarios.getValueAt(linhaSelecionada, 5).equals("Gerente")){
                view.mensagemCritica("Não é Permitido Remover o Gerente!", "Atenção");
            }
            else{
                int codAluno = Integer.parseInt(tabelaDeFuncionarios.getValueAt(linhaSelecionada, 0).toString());

                funcionarioDao.removerFuncionario(codAluno);
                this.view.exibeMensagem("Sucesso");
                listarFuncionarios();
            }
        }
        else{view.mensagemCritica("Nenhum Funcionário Selecionado!", "Erro");}
    }
    
    //Buscar Funcionários no campo de busca
    public void buscarFuncionarios(){
        String funcionarioPesquisa = view.getCampoBuscar().getText();
        ArrayList <Funcionario> funcionarios = funcionarioDao.pesquisarPorNome(funcionarioPesquisa);
        if(funcionarioPesquisa.equals("")){listarFuncionarios();}
        else{this.buscas(funcionarios);}        
    }
    
    //Buscar Aniversariantes
    protected void buscarAniversariantes(){
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
    
    protected void buscas(ArrayList <Funcionario> funcionarios){     
        if(funcionarios==null){view.exibeMensagem("Sem Dados"); limparTabela();}
        else{
                limparTabela();
                for(Funcionario funcionario : funcionarios){
                Object[] dadosDaTabelaFuncionarios = {funcionario.getCodBanco(), 
                    funcionario.getNome(),funcionario.getCargo(), funcionario.getSituacao()};
                this.tabelaDeFuncionarios.addRow(dadosDaTabelaFuncionarios);
            }
        }
    }
    
    
     
     public void desvincularContratarFuncionario(){
         int linhaSelecionada = view.getTabelaFuncionarios().getSelectedRow();
         
         if(linhaSelecionada>-1){
            int opcao = JOptionPane.showConfirmDialog(null, "Deseja modificar a Situação Contratual deste Funcionário? Ao fazer isso poderão ocorrer"
                    + " alterações referentes ao Login dos Funcionários!", "Alerta!", JOptionPane.YES_NO_OPTION);
            
            if(opcao == JOptionPane.YES_OPTION){
                int codFuncionario = Integer.parseInt(tabelaDeFuncionarios.getValueAt(linhaSelecionada, 0).toString());
                String nome = tabelaDeFuncionarios.getValueAt(linhaSelecionada, 1).toString();
                String cargo = tabelaDeFuncionarios.getValueAt(linhaSelecionada, 2).toString();
                if(!cargo.equals("Gerente")){
                    String situacao = tabelaDeFuncionarios.getValueAt(linhaSelecionada, 3).toString();
                    if(situacao.equals("Contratado")){
                        situacao = "Desvinculado";
                    }else{
                        situacao = "Contratado";
                    }
                    funcionarioDao.atualizarSituacao(new Funcionario(codFuncionario, nome, cargo, situacao));
                    view.exibeMensagem("Sucesso.");
                    listarFuncionarios();
                }
                else{
                    view.exibeMensagem("Não é possível modificar a Situação Contratual do(a) Gerente");
                }
            }
         }
     }
     
     public void ajuda(){
        String atalhos = "\u07CBESC = Sair da Tela\n"
                + "\u07CBF5 = Atualizar Tabela\n"
                + "\u07CBDEL = Remover Funcionário\n"
                + "\u07CBCTRL + E = Editar Funcionário\n"
                + "\u07CBCTRL + F = Encerrar ou Reativar Contrato\n"
                + "\u07CBCTRL + N = Cadastrar Novo Funcionário\n";
        
        view.getPainelAjuda().setModal(true);
        view.getPainelAjuda().getCampoAtalhos().setText("");
        view.getPainelAjuda().getCampoAtalhos().append(atalhos);
        view.getPainelAjuda().setVisible(true);
    }
     
     public void sairTela(){
        funcionarioDao.atualizarStatusAll();
        Funcionario funcionario = this.setarLog("Saída do Sistema", null);
        view.getParent().dispose();
        if(funcionario==null||!funcionario.getCargo().equals("Gerente")){
            LoginFuncionario jump = new LoginFuncionario();
            jump.setVisible(true);
        }
        else{
            LoginGerente jump = new LoginGerente();
            jump.setVisible(true);
        }
    }
     
     protected Funcionario setarLog(String acao, String descricao){
         ArrayList <Funcionario> funcionarios = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE status = 'Ativo'");
        if(funcionarios!=null){
            Funcionario funcionario = funcionarios.get(0);
            Date dataEvento = new Date();
            LogAçoesFuncionario logAcao = new LogAçoesFuncionario(funcionario.getCodBanco(), dataEvento, acao, descricao);
            return funcionario;
        }
        return null;
    }
     
}
