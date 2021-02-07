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
import View.LoginFuncionario;
import View.LoginGerente;
import View.Paineis.FuncionariosPermissoes;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mayro
 */
public class FuncionariosController {
    private final Funcionarios view;
    private final FuncionariosPermissoes view2;
    private final DefaultTableModel tabelaDeFuncionarios;
    private final FuncionarioDao funcionarioDao = new FuncionarioDao();
    private final EnderecoFuncionarioDao enderecoDao = new EnderecoFuncionarioDao();
    private final LogAçoesFuncionarioDao logDao = new LogAçoesFuncionarioDao();
    private final ConversaoDeDinheiro converterDinheiro = new ConversaoDeDinheiro();
    private final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();

    public FuncionariosController(Funcionarios view, FuncionariosPermissoes view2) {
        this.view = view;
        this.view2 = view2;
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
    
    public void editarFuncionarios(){
        if(this.view.getTabelaFuncionarios().getSelectedRow()!= -1){
            //Dados Alunos
                int linhaSelecionada = this.view.getTabelaFuncionarios().getSelectedRow();
                int codFuncionario = Integer.parseInt(tabelaDeFuncionarios.getValueAt(linhaSelecionada, 0).toString());
                Funcionario funcionarioAnterior = this.funcionarioAnterior(codFuncionario);

                if(!funcionarioAnterior.getSituacao().equals("Desvinculado")){
                    String nome = this.tabelaDeFuncionarios.getValueAt(linhaSelecionada, 1).toString();
                    String cpf = this.tabelaDeFuncionarios.getValueAt(linhaSelecionada, 2).toString();
                    String telefone = this.tabelaDeFuncionarios.getValueAt(linhaSelecionada, 3).toString();
                    String celular = this.tabelaDeFuncionarios.getValueAt(linhaSelecionada, 4).toString();
                    String cargo = this.tabelaDeFuncionarios.getValueAt(linhaSelecionada, 5).toString();
                    BigDecimal valorSalario = new BigDecimal(converterDinheiro.converterParaBigDecimal(this.tabelaDeFuncionarios.getValueAt(linhaSelecionada, 6).toString()).toString());
                    String usuario = cpf;

                    String telasPermitidas = view2.getTelasPermitidas();


                    String situacao = tabelaDeFuncionarios.getValueAt(linhaSelecionada, 7).toString();

                    Funcionario funcionario = new Funcionario(codFuncionario, nome, cpf, "", telefone, celular, funcionarioAnterior.getEmail(), funcionarioAnterior.getDatadenascimento(),
                            usuario, funcionarioAnterior.getSenha(), valorSalario, cargo, telasPermitidas, null, situacao);

                    //Inserindo Dados
                    if(nome.equals("")|| cpf.equals("   .   .   -  ")){
                        view.exibeMensagem("Campos Preenchidos Incorretamente");
                    } else{
                        funcionarioDao.atualizarDados(funcionario);

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
    
    
    public void removerFuncionario() {
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
    
    
    private Funcionario funcionarioAnterior(int codFuncionario){
         return  funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE codFuncionario = "+codFuncionario).get(0);
    }
    
    private void buscas(ArrayList <Funcionario> funcionarios){     
        if(funcionarios==null){view.exibeMensagem("Sem Dados"); limparTabela();}
        else{
                limparTabela();
                for(Funcionario funcionario : funcionarios){
                Object[] dadosDaTabelaFuncionarios = {funcionario.getCodBanco(), 
                funcionario.getNome(),funcionario.getCpf(), 
                funcionario.getTelefone(), funcionario.getCelular(),funcionario.getCargo(),
                funcionario.getSalario(), funcionario.getSituacao()};
                this.tabelaDeFuncionarios.addRow(dadosDaTabelaFuncionarios);
            }
        }
    }
    
    public void selecionarTabela(){
        int linhaSelecionada = view.getTabelaFuncionarios().getSelectedRow();
        
        if(linhaSelecionada!=-1){
            view.getBotaoPermissoes().setVisible(true);
        }
        else{
            view.getBotaoPermissoes().setVisible(false);
        }
    }
    
    private void pegarTelasPermitidas(int codFuncionario){
        Funcionario funcionario = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE codFuncionario = "+codFuncionario).get(0);
        String[] telas = funcionario.getTelasPermitidas().split(",");
        view2.setTelasPermitidas(funcionario.getTelasPermitidas());
        for(int linhas=0; linhas<telas.length; linhas++){
            telas[linhas]=telas[linhas].replace(",", "");
            
            switch(telas[linhas]){
                case "1":
                    view2.getRadioAlunosSim().setSelected(true);
                break;
                case "2":
                    view2.getRadioTurmasSim().setSelected(true);
                break;
                case "3":
                    view2.getRadioProdutosSim().setSelected(true);
                break;
                case "4":
                    view2.getRadioServicosSim().setSelected(true);
                break;
                case "5":
                    view2.getRadioFinanceiroSim().setSelected(true);
                break;
                case "6":
                    view2.getRadioImprimirSim().setSelected(true);
                break;
                case "7":
                    view2.getRadioBackupSim().setSelected(true);
                break;
                case "8":
                    view2.getRadioCaixaSim().setSelected(true);
                break;
                case "10":
                    view2.getRadioReposicoesSim().setSelected(true);
                break;
            }
        }
        
        
    }
    
    public void setarPermissoes(){
        int linhaSelecionada = view.getTabelaFuncionarios().getSelectedRow();
        int codFuncionario = Integer.parseInt(tabelaDeFuncionarios.getValueAt(linhaSelecionada, 0).toString());
        this.pegarTelasPermitidas(codFuncionario);
        int x = view.getLocation().x-200;
        int y = view.getLocation().y;
        
        view.setLocation(x, y);
        view2.setLocation(x+view.getWidth()+4, y+view.getHeight()-view2.getHeight());
        view2.setModal(true);
        view2.setVisible(true);
    }
    
    public void atualizarTelasPermitidas(){
        view2.setarTelasPermitidas();
        int linhaSelecionada = view.getTabelaFuncionarios().getSelectedRow();
        
        if(linhaSelecionada!=-1){
            int codFuncionario = Integer.parseInt(tabelaDeFuncionarios.getValueAt(linhaSelecionada, 0).toString());
            Funcionario funcionario = new Funcionario(codFuncionario, null, null, null, null, null, null, null, null, null, BigDecimal.ZERO, null, view2.getTelasPermitidas(), null, null);
            
            funcionarioDao.atualizarTelasPermitidas(funcionario);
        }
        view2.resetarPermissoes();
        
        int x = view.getLocation().x+200;
        int y = view.getLocation().y;
        view.setLocation(x, y);
        
        view2.dispose();
        
    }
    
    
     
     public void desvincularContratarFuncionario(){
         int linhaSelecionada = view.getTabelaFuncionarios().getSelectedRow();
         
         if(linhaSelecionada>-1){
            int opcao = JOptionPane.showConfirmDialog(null, "Deseja modificar a Situação Contratual deste Funcionário? Ao fazer isso poderão ocorrer"
                    + " alterações referentes ao Login dos Funcionários!", "Alerta!", JOptionPane.YES_NO_OPTION);
            
            if(opcao == JOptionPane.YES_OPTION){
                int codFuncionario = Integer.parseInt(tabelaDeFuncionarios.getValueAt(linhaSelecionada, 0).toString());
                String cargo = tabelaDeFuncionarios.getValueAt(linhaSelecionada, 5).toString();
                if(!cargo.equals("Gerente")){
                    String situacao = tabelaDeFuncionarios.getValueAt(linhaSelecionada, 7).toString();
                    if(situacao.equals("Contratado")){
                        situacao = "Desvinculado";
                    }else{
                        situacao = "Contratado";
                    }
                    funcionarioDao.atualizarSituacao(codFuncionario, situacao);
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
     
     private Funcionario setarLog(String acao, String descricao){
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
