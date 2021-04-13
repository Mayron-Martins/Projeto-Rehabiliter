/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.auxiliar.ConversaoDeDinheiro;
import Dao.AlunosDao;
import Dao.FuncionarioDao;
import Dao.LogAçoesFuncionarioDao;
import Dao.PlanosDao;
import Dao.ServicosDao;
import Model.Aluno;
import Model.Funcionario;
import Model.auxiliar.LogAçoesFuncionario;
import Model.auxiliar.Planos;
import Model.auxiliar.Servicos;
import View.LoginFuncionario;
import View.LoginGerente;
import View.ServicosView;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mayro
 */
public class ServicosController {
    protected final ServicosView view;
    protected final DefaultTableModel tabelaDeServicos;
    protected final ServicosDao servicosDao = new ServicosDao();
    protected final AlunosDao alunoDao = new AlunosDao();
    protected final PlanosDao planosDao = new PlanosDao();
    protected final FuncionarioDao funcionarioDao = new FuncionarioDao();
    protected final LogAçoesFuncionarioDao logDao = new LogAçoesFuncionarioDao();
    protected final ConversaoDeDinheiro converterDinheiro = new ConversaoDeDinheiro();

    public ServicosController(ServicosView view) {
        this.view = view;
        this.tabelaDeServicos = (DefaultTableModel) view.getTabelaServicos().getModel();
    }
    
    //Limpar tabela
    public void limparTabela(){
        int quantLinhas = this.view.getTabelaServicos().getRowCount();    
        for(int quant=0; quant<quantLinhas; quant++){
            this.tabelaDeServicos.removeRow(0);
        }
    }
    
    //Lista todas as turmas 
    public void listarServicos(){
        String situacao = view.getComboSituacao().getSelectedItem().toString();
        situacao = situacao.substring(0, situacao.length()-1);
        ArrayList <Servicos> servicos = this.servicosDao.pesquisarServicos("SELECT * FROM tblServicos WHERE situacao = '"+situacao+"'");
        if(servicos==null){
            view.exibeMensagem("Sem Dados");
            limparTabela();
        } else{
            limparTabela();
            for(Servicos servico : servicos){                    
                Object[] dadosDaTabela = {servico.getCodBanco(), servico.getNome(), servico.getPeriodo(), servico.getSituacao()};
                    this.tabelaDeServicos.addRow(dadosDaTabela);
            }
        }        
    }
    
    public void editarServico(){
        if(this.view.getTabelaServicos().getSelectedRow()!= -1){
            int linhaSelecionada = view.getTabelaServicos().getSelectedRow();
            int codServico = Integer.parseInt(tabelaDeServicos.getValueAt(linhaSelecionada, 0).toString());
            String nome = tabelaDeServicos.getValueAt(linhaSelecionada, 1).toString();
            String situacao = tabelaDeServicos.getValueAt(linhaSelecionada, 3).toString();
        //Inserindo Dados
            if(nome.equals("")){
                 view.exibeMensagem("Campos Preenchidos Incorretamente");
            } else{
                servicosDao.atualizarSituacao(codServico, nome, situacao);   
                setarLog("Edição de Serviços", "Edição do serviço "+nome);

                listarServicos();
                view.getTabelaServicos().addRowSelectionInterval(linhaSelecionada, linhaSelecionada);
                view.exibeMensagem("Sucesso!");
                //Limpando Campos
                
            }   
        }
        else{view.mensagemCritica("Nenhum Servio Selecionado!", "Erro");}
    }
    
    public void editarVariosServicos(){
        int linhasTabela = tabelaDeServicos.getRowCount();
        boolean erro = false;
        String erroServicos="(";
        if(linhasTabela>0){
            for(int linha=0; linha<linhasTabela; linha++){
                int codServico = Integer.parseInt(tabelaDeServicos.getValueAt(linha, 0).toString());
                String nome = tabelaDeServicos.getValueAt(linha, 1).toString();
                String situacao = tabelaDeServicos.getValueAt(linha, 3).toString();
            //Inserindo Dados
                if(nome.equals("")){
                     view.exibeMensagem("Campos Preenchidos Incorretamente");
                     erro=true;
                     erroServicos+=nome+", ";
                } else{
                    servicosDao.atualizarSituacao(codServico, nome, situacao);   
                }
            }
            
            setarLog("Edição de Serviços", "Edição de vários serviços ");
            
            if(erro){
                view.mensagemCritica("Os serviços "+erroServicos.substring(0, erroServicos.length()-2)+") não puderam ser editados", "Aviso");
            }
            view.exibeMensagem("Sucesso!");
            listarServicos();
            
        }
        else{view.mensagemCritica("Sem dados na tabela", "Erro");}
    }
    
    public void removerServico(){
        if(this.view.getTabelaServicos().getSelectedRow()!= -1){
            int linhaSelecionada = this.view.getTabelaServicos().getSelectedRow();
            int codServico = Integer.parseInt(tabelaDeServicos.getValueAt(linhaSelecionada, 0).toString());
            String nomeServico = tabelaDeServicos.getValueAt(linhaSelecionada, 1).toString();
            
            if(retornarAlunosUsando(codServico)){
                servicosDao.removerServico(codServico);

                this.setarLog("Remoção de Serviços", "Remoção do serviço "+nomeServico);
                
                this.view.exibeMensagem("Sucesso");
                limparTabela();
                listarServicos();
            }
            else{
                view.mensagemCritica("Não é possível remover, pois ainda existem alunos utilizando o serviço!", "Erro");
            }
            
        }
        else{this.view.exibeMensagem("Erro, Nenhum Serviço Selecionado!");}
    }
    
    public void buscarServico(){
        String nomeServico = view.getCampoDePesquisa().getText();
        
        if(nomeServico.equals("")){listarServicos();}
        else{
            ArrayList <Servicos> servicos = servicosDao.pesquisarPorNome(nomeServico);
            if(servicos==null){view.exibeMensagem("Serviço Não Encontrada!"); limparTabela();}
            else{
                limparTabela();
                for(Servicos servico : servicos){
                Object[] dadosDaTabela = {servico.getCodBanco(), servico.getNome(),  servico.getPeriodo(), servico.getSituacao()};
                tabelaDeServicos.addRow(dadosDaTabela);
                  
            }
            }
        }
    }
    
    protected boolean retornarAlunosUsando(int codServico){
        ArrayList <Aluno> alunos = alunoDao.pesquisarAlunos("SELECT * FROM tblAlunos WHERE codServico = "+codServico);
        return alunos ==null;
    }
    
    public void encerrarReabrirServico(){
        int linhaSelecionada = view.getTabelaServicos().getSelectedRow();
        
        if(linhaSelecionada>-1){
            int codServico = Integer.parseInt(tabelaDeServicos.getValueAt(linhaSelecionada, 0).toString());
            String nome = tabelaDeServicos.getValueAt(linhaSelecionada, 1).toString();
            
            ArrayList <Planos> planos= planosDao.pesquisarPlanos("SELECT * FROM tblPlanos WHERE codServico = "+codServico+" AND situacao != 'Encerrado'");
            
            String situacao = tabelaDeServicos.getValueAt(linhaSelecionada, 3).toString();
            if(situacao.equals("Aberto")){
                situacao = "Encerrado";
            }else{
                situacao = "Aberto";
            }
            
            if(situacao.equals("Encerrado")&&planos!=null){
                view.exibeMensagem("Não é possível encerrar o Serviço, pois ainda existem Planos Não Encerrados!");
            }else{
                servicosDao.atualizarSituacao(codServico, nome, situacao);
                view.exibeMensagem("Sucesso!");
                listarServicos();
            }
        }
    }
    
    public void ajuda(){
        String atalhos = "\u07CBESC = Sair da Tela\n"
                + "\u07CBF5 = Atualizar Tabela\n"
                + "\u07CBDEL = Remover Serviço\n"
                + "\u07CBCTRL + E = Editar Serviço\n"
                + "\u07CBCTRL + F = Encerrar ou Reabrir Serviço\n"
                + "\u07CBCTRL + N = Cadastrar Novo Serviço\n";
        
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
