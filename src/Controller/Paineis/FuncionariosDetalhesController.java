/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Paineis;

import Controller.FuncionariosController;
import Model.Funcionario;
import View.Funcionarios;
import View.Paineis.FuncionariosDetalhes;
import java.awt.Dialog;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;

/**
 *
 * @author Mayro
 */
public class FuncionariosDetalhesController extends FuncionariosController{
    private final FuncionariosDetalhes viewSecundaria;

    public FuncionariosDetalhesController(Funcionarios view, FuncionariosDetalhes viewSecundaria) {
        super(view);
        this.viewSecundaria = viewSecundaria;
    }
    
    public void selecionarTabela(){
        if(view.getTabelaFuncionarios().getSelectedRow()!=-1){
            int linhaSelecionada = view.getTabelaFuncionarios().getSelectedRow();
            int codBanco = Integer.parseInt(tabelaDeFuncionarios.getValueAt(linhaSelecionada, 0).toString());
            
            Funcionario funcionario = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE codFuncionario = "+codBanco).get(0);
            
            //Listar cargos
            if(!funcionario.getCargo().equals("Gerente")){
                listarCargos();
                viewSecundaria.getComboCargos().removeItem("Gerente");
            }else{
                viewSecundaria.getComboCargos().removeAllItems();
                viewSecundaria.getComboCargos().addItem("Gerente");
            }
            
            //Painel Dados
            viewSecundaria.getCampoNome().setText(funcionario.getNome());
            viewSecundaria.getCampoCPF().setText(funcionario.getCpf());
            viewSecundaria.getCampoNascimento().setDate(funcionario.getDatadenascimento());
            viewSecundaria.getComboCargos().setSelectedItem(funcionario.getCargo());
            viewSecundaria.getCampoSalario().setText(funcionario.getSalario().toString());
            
            //Painel Contatos
            viewSecundaria.getCampoTelefone().setText(funcionario.getTelefone());
            viewSecundaria.getCampoCelular().setText(funcionario.getCelular());
            viewSecundaria.getCampoEmail().setText(funcionario.getEmail());
            viewSecundaria.getCampoSenha().setText(funcionario.getSenha());
            
            //Painel Permissões
            setarTelasPermitidas(funcionario);
            
            if(!viewSecundaria.isVisible()){
                alterarLocalizacaoViews(true);
            }
            
           //Notificação de Alteração
            for(JCheckBox caixa : viewSecundaria.getGrupoAlteracoes()){
                viewSecundaria.ativarCheckAlteracao(caixa, false);
            }
        }    
    }
    
    public void realeasedTable(){
        if(viewSecundaria.isVisible()){
            limparCampos();
            selecionarTabela();
        }
    }
    
    @Override
    public void editarFuncionario(){
        int linhaSelecionada = view.getTabelaFuncionarios().getSelectedRow();
        int codBanco = Integer.parseInt(tabelaDeFuncionarios.getValueAt(linhaSelecionada, 0).toString());
        String nome = viewSecundaria.getCampoNome().getText();
        String cpf = viewSecundaria.getCampoCPF().getText();
        
        
        boolean erroDados = false, erroContato=false, permissoesEditadas=false;
        if(viewSecundaria.getGrupoAlteracoes().get(0).isSelected()){
            erroDados = editarDados(codBanco, nome, cpf);
        }
        if(viewSecundaria.getGrupoAlteracoes().get(1).isSelected()){
            erroContato = editarContatos(codBanco, cpf);
        }
        if(viewSecundaria.getGrupoAlteracoes().get(2).isSelected()){
            editarPermissoes(codBanco);
            permissoesEditadas = true;
        }
        
        if(erroDados){
            view.mensagemCritica("Alguns dados não puderam ser editados, verifique a aba DADOS!", "Atenção");
        }
        if(erroContato){
            view.mensagemCritica("Alguns dados não puderam ser editados, verifique a aba CONTATO!", "Atenção");
        }
        
        if(!erroDados||!erroContato||permissoesEditadas){
            setarLog("Edição de Dados de Turma", "Editou os dados da turma "+nome);
        }
        listarFuncionarios();
        view.getTabelaFuncionarios().addRowSelectionInterval(linhaSelecionada, linhaSelecionada);
        view.exibeMensagem("Sucesso!");
        viewSecundaria.trocaBotoes(false);
    }
    
    private boolean editarDados(int codBanco, String nome, String cpf){
        Date nascimento = viewSecundaria.getCampoNascimento().getDate();
        String cargo = viewSecundaria.getComboCargos().getSelectedItem().toString();
        if(viewSecundaria.getCampoNovoTipo().isEnabled()){
            cargo = viewSecundaria.getCampoNovoTipo().toString();
        }
        BigDecimal salario = new BigDecimal(converterDinheiro.converterParaBigDecimal(viewSecundaria.getCampoSalario().getText()).toString());
        
        Funcionario funcionario = new Funcionario(codBanco, nome, cpf, nascimento, cargo, salario);
        
        if(!nome.trim().equals("")||nascimento!=null||!cargo.trim().equals("")){
            return true;
        }else{
            funcionarioDao.atualizarDados(funcionario);
            viewSecundaria.getGrupoAlteracoes().get(0).setSelected(false);
            viewSecundaria.getGrupoAlteracoes().get(0).setVisible(false);
            return false;
        } 
    }
    
    private boolean editarContatos(int codBanco, String cpf){
        String telefone = viewSecundaria.getCampoTelefone().getText();
        String celular = viewSecundaria.getCampoCelular().getText();
        String email = viewSecundaria.getCampoEmail().getText();
        String usuario = cpf;
        String senha = Arrays.toString(viewSecundaria.getCampoSenha().getPassword());
        
        Funcionario funcionario = new Funcionario(codBanco, telefone, celular, email, usuario, senha);
        
        if(senha.trim().equals("")){
            return true;
        }else{
            funcionarioDao.atualizarContato(funcionario);
            funcionarioDao.atualizarSenha(funcionario);
            viewSecundaria.getGrupoAlteracoes().get(1).setSelected(false);
            viewSecundaria.getGrupoAlteracoes().get(1).setVisible(false);
            return false;
        }     
    }
    
    private void editarPermissoes(int codBanco){
        String telasPermitidas = "";
        boolean telaReposicao = false;
        int camada=1;
        Enumeration <AbstractButton> enu;
        AbstractButton button;
        
        for(ButtonGroup grupo : viewSecundaria.getGrupoRadios()){
            enu = grupo.getElements();
            while(enu.hasMoreElements()){
                button = enu.nextElement();
                if(button.getActionCommand().equals("Sim")&&button.isSelected()){
                    telasPermitidas+=camada+",";
                    if(camada == 2){
                        telaReposicao = true;
                    }
                }
            }
            camada++;
            if(camada==9&&telaReposicao){
                telasPermitidas+="9,";
                camada++;
            }else{
                camada++;
            }
        }
        
        //Inserir telas permitidas
        funcionarioDao.atualizarTelasPermitidas(codBanco, telasPermitidas.substring(0, telasPermitidas.length()-1));
        viewSecundaria.getGrupoAlteracoes().get(2).setSelected(false);
        viewSecundaria.getGrupoAlteracoes().get(2).setVisible(false);
    }
    
    private void setarTelasPermitidas(Funcionario funcionario){
        String[] telas = funcionario.getTelasPermitidas().split(",");
        for(int linhas=0; linhas<telas.length; linhas++){
            telas[linhas]=telas[linhas].replace(",", "");
            
            switch(telas[linhas]){
                case "1":
                    viewSecundaria.alternarRadios(viewSecundaria.getGrupoRadios().get(0), true);
                break;
                case "2":
                    viewSecundaria.alternarRadios(viewSecundaria.getGrupoRadios().get(1), true);
                break;
                case "3":
                    viewSecundaria.alternarRadios(viewSecundaria.getGrupoRadios().get(2), true);
                break;
                case "4":
                    viewSecundaria.alternarRadios(viewSecundaria.getGrupoRadios().get(3), true);
                break;
                case "5":
                    viewSecundaria.alternarRadios(viewSecundaria.getGrupoRadios().get(4), true);
                break;
                case "6":
                    viewSecundaria.alternarRadios(viewSecundaria.getGrupoRadios().get(5), true);
                break;
                case "7":
                    viewSecundaria.alternarRadios(viewSecundaria.getGrupoRadios().get(6), true);
                break;
                case "8":
                    viewSecundaria.alternarRadios(viewSecundaria.getGrupoRadios().get(7), true);
                break;
                case "10":
                    viewSecundaria.alternarRadios(viewSecundaria.getGrupoRadios().get(8), true);
                break;
            }
        }
        
        
    }
    
    private void listarCargos(){
        ArrayList <String> cargos = funcionarioDao.selecionarCargos();
        if(cargos!=null){
            viewSecundaria.getComboCargos().removeAllItems();
            
            for(String cargo : cargos){
                viewSecundaria.getComboCargos().addItem(cargo);
            }
        }
    }
    
    private void alterarLocalizacaoViews(boolean ativarDetalhes){
        int view1X = view.getX();
        int view1Y = view.getY();
        int largView1 = view.getWidth();
        int largView2 = viewSecundaria.getWidth();
        int deslocamentoX = largView2/2;
        int espacoEntreViews = 1;
        
        if(ativarDetalhes){
            view.setLocation(view1X-deslocamentoX, view1Y);
            viewSecundaria.setLocation(view1X+largView1+espacoEntreViews-deslocamentoX, view1Y);
            viewSecundaria.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
            viewSecundaria.setVisible(true);
        }
        else{
            view.setLocation(view1X+deslocamentoX, view1Y);
            viewSecundaria.setLocation(view1X+largView1+espacoEntreViews+deslocamentoX, view1Y);
            limparCampos();
            viewSecundaria.dispose();
        }
        
    }
    
    @Override
    public void sairTela(){
        alterarLocalizacaoViews(false);
    }
   
    private void limparCampos(){
        //Painel Dados
        viewSecundaria.getCampoNome().setText("");
        viewSecundaria.getCampoCPF().setValue(null);
        viewSecundaria.getCampoNovoTipo().setText("Outro");
        viewSecundaria.getCampoSalario().setText("");
        
        //Painel Contato
        viewSecundaria.getCampoTelefone().setValue(null);
        viewSecundaria.getCampoCelular().setValue(null);
        viewSecundaria.getCampoEmail().setText("");
        viewSecundaria.getCampoSenha().setText("");
        viewSecundaria.getCheckExibirSenha().setSelected(false);
        
        //Painel Permissões
        for(ButtonGroup grupo : viewSecundaria.getGrupoRadios()){
            viewSecundaria.alternarRadios(grupo, false);
        }
    }
}
