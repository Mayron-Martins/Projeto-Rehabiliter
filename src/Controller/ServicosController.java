/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.adicionais.AdicionarServicosController;
import Controller.auxiliar.ConversaoDeDinheiro;
import Dao.AlunosDao;
import Dao.FuncionarioDao;
import Dao.LogAçoesFuncionarioDao;
import Dao.ServicosDao;
import Model.Aluno;
import Model.Funcionario;
import Model.auxiliar.LogAçoesFuncionario;
import Model.auxiliar.Servicos;
import View.ServicosView;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mayro
 */
public class ServicosController {
    private final ServicosView view;
    private final DefaultTableModel tabelaDeServicos;
    private final ServicosDao servicosDao = new ServicosDao();
    private final AlunosDao alunoDao = new AlunosDao();
    private final FuncionarioDao funcionarioDao = new FuncionarioDao();
    private final LogAçoesFuncionarioDao logDao = new LogAçoesFuncionarioDao();
    private final ConversaoDeDinheiro converterDinheiro = new ConversaoDeDinheiro();

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
        ArrayList <Servicos> servicos = new ArrayList<>();
        servicos = this.servicosDao.selecionarTodosServicos();
        if(servicos==null){
            view.exibeMensagem("Sem Serviços Cadastradas");
            limparTabela();
        } else{
            limparTabela();
            for(Servicos servico : servicos){
                    String formaPagamento = servico.getFormaPagamento();
                    BigDecimal valor = servico.getValor();

                    if(formaPagamento.equals("Dinheiro")){
                        valor = servico.getValorVista();
                    }
                    if(formaPagamento.equals("Boleto")){
                        valor = servico.getValorBoleto();
                    }
                    if(formaPagamento.equals("Cartão de Crédito")){
                        valor = servico.getValorPrazoCredito();
                    }
                    if(formaPagamento.equals("Cartão de Débito")){
                        valor = servico.getValorPrazoDebito();
                    }
                    
                    Object[] dadosDaTabela = {servico.getCodBanco(), servico.getNome(),servico.getPeriodo(),
                        servico.getFormaPagamento(), valor, servico.getSituacao()};
                        this.tabelaDeServicos.addRow(dadosDaTabela);
            }
        }        
    }
    
    public void editarServicos(){
        if(this.view.getTabelaServicos().getSelectedRow()!= -1){
            int linhaSelecionada = this.view.getTabelaServicos().getSelectedRow();
            int codServico = Integer.parseInt(tabelaDeServicos.getValueAt(linhaSelecionada, 0).toString());
            String nome = this.tabelaDeServicos.getValueAt(linhaSelecionada, 1).toString();
            
            String periodo = this.retornarPeriodo();
            String metodoDePagamento = view.getMetodoPagamento().getSelectedItem().toString();
            String situacao = tabelaDeServicos.getValueAt(linhaSelecionada, 5).toString();
            
            BigDecimal valor = new BigDecimal("0");
            BigDecimal valorAVista = new BigDecimal("0");
            BigDecimal valorBoleto = new BigDecimal("0");
            BigDecimal valorAPrazoCredito = new BigDecimal("0");
            BigDecimal valorAPrazoDebito = new BigDecimal("0");
            
            
            String valorDinheiro = converterDinheiro.converterParaBigDecimal(view.getTabelaServicos().getValueAt(linhaSelecionada, 4).toString()).toString();
            
            if(metodoDePagamento.equals("[Nenhuma]")){valor = new BigDecimal(valorDinheiro);}
            if(metodoDePagamento.equals("Dinheiro")){valorAVista = new BigDecimal(valorDinheiro);}
            if(metodoDePagamento.equals("Boleto")){valorBoleto = new BigDecimal(valorDinheiro);}
            if(metodoDePagamento.equals("Cartão de Crédito")){valorAPrazoCredito = new BigDecimal(valorDinheiro);}
            if(metodoDePagamento.equals("Cartão de Débito")){valorAPrazoDebito = new BigDecimal(valorDinheiro);}
            
            int periodDays = this.periodDays();
            Servicos servico = new Servicos(codServico, nome, periodo, metodoDePagamento, valor, valorAVista, valorBoleto, valorAPrazoCredito, valorAPrazoDebito, periodDays, situacao);
            Servicos servicoAnterior = this.retornarServicoAnterior(codServico);
        //Inserindo Dados
        if(nome.equals("")){
         view.exibeMensagem("Campos Preenchidos Incorretamente");
        } else{
            if(!this.retornarAlunosUsando(codServico)){
                if(periodDays!=servicoAnterior.getPeriodDays()||valor.compareTo(servicoAnterior.getValor())!=0
                    ||valorAPrazoCredito.compareTo(servicoAnterior.getValorPrazoCredito())!=0
                    ||valorAPrazoDebito.compareTo(servicoAnterior.getValorPrazoDebito())!=0
                    ||valorAVista.compareTo(servicoAnterior.getValorVista())!=0
                    ||valorBoleto.compareTo(servicoAnterior.getValorBoleto())!=0){
                    
                    view.exibeMensagem("Não é possível alterar, pois ainda existem alunos utilizando o serviço!");
                 }
                else{
                    servicosDao.atualizarDados(servico);   
                    ArrayList <Funcionario> funcionarios = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE status = 'Ativo'");
                    if(funcionarios!=null){
                        String acao = "Edição de Serviços";
                        String descricao = "Edição do serviço "+nome;
                        this.setarLog(funcionarios, acao, descricao);
                    }
                    view.exibeMensagem("Sucesso!");
                    //Limpando Campos
                    limparTabela();
                    listarServicos();
                    view.setarValores();
                    selecionarTabela();
                }
            }
            else{
                if(this.verificarPeriodo(periodDays)){
                    servicosDao.atualizarDados(servico);   
                    ArrayList <Funcionario> funcionarios = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE status = 'Ativo'");
                    if(funcionarios!=null){
                        String acao = "Edição de Serviços";
                        String descricao = "Edição do serviço "+nome;
                        this.setarLog(funcionarios, acao, descricao);
                    }
                    view.exibeMensagem("Sucesso!");
                    //Limpando Campos
                    limparTabela();
                    listarServicos();
                    view.setarValores();
                    selecionarTabela(); 
                }
                else{
                    view.exibeMensagem("Por motivos de compatibilidade, esse período de dias não pôde ser aceito!");
                }
            }
        }
            //Turmas 
        }
        
        else{this.view.exibeMensagem("Erro, Nenhum Servio Selecionado!");}
    }
    
    public void selecionarTabela(){
      if(this.view.getTabelaServicos().getSelectedRow()!=-1){
        view.setarValores();
        //Número da linha selecionada
          int linhaSelecionada = this.view.getTabelaServicos().getSelectedRow();
          //Habilita itens que ficam acima da tabela
          this.view.habilitarComponentes();
          //Pega o valor da coluna Tipo
          String periodo = tabelaDeServicos.getValueAt(linhaSelecionada, 2).toString();
          //Pega o valor da coluna Forma de Pagamento
          String metodoDePagamento = tabelaDeServicos.getValueAt(linhaSelecionada, 3).toString();
          //Joga o valor das colunas no combobox
          this.preencherComboPeriodo();
          view.getComboPeriodo().setSelectedItem(periodo);
          view.getComboPeriodo().setSelectedItem(metodoDePagamento);
          view.getComboDias().setSelectedIndex(0);
          
          int codServico = Integer.parseInt(view.getTabelaServicos().getValueAt(linhaSelecionada, 0).toString());
          
          Servicos servico = this.retornarServicoAnterior(codServico);
          view.getCampoDias().setText(servico.getPeriodDays()+"");
      }
    }
    
    public void removerServico(){
        if(this.view.getTabelaServicos().getSelectedRow()!= -1){
            int linhaSelecionada = this.view.getTabelaServicos().getSelectedRow();
            int codServico = Integer.parseInt(tabelaDeServicos.getValueAt(linhaSelecionada, 0).toString());
            String nomeServico = tabelaDeServicos.getValueAt(linhaSelecionada, 1).toString();
            
            if(this.retornarAlunosUsando(codServico)){
                servicosDao.removerServico(codServico);

                ArrayList <Funcionario> funcionarios = funcionarioDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE status = 'Ativo'");
                if(funcionarios!=null){
                    String acao = "Remoção de Serviços";
                    String descricao = "Remoção do serviço "+nomeServico;
                    this.setarLog(funcionarios, acao, descricao);
                }
                this.view.exibeMensagem("Sucesso");
                limparTabela();
                this.view.desabilitarComponentes();
                listarServicos();
            }
            else{
                view.exibeMensagem("Não é possível remover, pois ainda existem alunos utilizando o serviço!");
            }
            
        }
        else{this.view.exibeMensagem("Erro, Nenhum Serviço Selecionado!");}
    }
    
    private String retornarPeriodo(){
        if(view.getComboPeriodo().isEnabled()){
            return view.getComboPeriodo().getSelectedItem().toString();
        }
        else{
            return view.getCampoOutroPeriodo().getText();
        }
    }
    
    public void preencherComboPeriodo(){
        ArrayList <Servicos> servicos = servicosDao.selecionarTodosServicos();
        
        String periodoServicoAnterior="";
        String periodoServico;
        if(servicos!=null){
            view.getComboPeriodo().removeAllItems();
            view.getComboPeriodo().addItem("[Nenhum]");
            view.getComboPeriodo().addItem("Diária");
            view.getComboPeriodo().addItem("Semanal");
            view.getComboPeriodo().addItem("Mensal");
            view.getComboPeriodo().addItem("Trimestral");
            view.getComboPeriodo().addItem("Quadrimestral");
            view.getComboPeriodo().addItem("Semestral");
            view.getComboPeriodo().addItem("Anual");
            
            for(int linhas=0; linhas<servicos.size();linhas++){
                periodoServico = servicos.get(linhas).getPeriodo();
                
                if(!periodoServico.equals(periodoServicoAnterior)){
                    if(!periodoServico.equals("[Nenhum]")&&!periodoServico.equals("Diária")&&!periodoServico.equals("Semanal")
                        &&!periodoServico.equals("Mensal")&&!periodoServico.equals("Trimestral")&&!periodoServico.equals("Quadrimestral")
                        &&!periodoServico.equals("Semestral")&&!periodoServico.equals("Anual")){
                        view.getComboPeriodo().addItem(periodoServico);
                    }
                }
                periodoServicoAnterior=periodoServico;
            }
        }
    }
    
    private LogAçoesFuncionario setarLog(ArrayList <Funcionario> funcionarios, String acao, String descricao){
        Funcionario funcionario = funcionarios.get(0);
        Date dataEvento = new Date();
        LogAçoesFuncionario logAcao = new LogAçoesFuncionario(funcionario.getCodBanco(), dataEvento, acao, descricao);
        return logAcao;
    }
    
    private int periodDays(){
        if(view.getComboPeriodo().isEnabled()){
            String tipo = view.getComboPeriodo().getSelectedItem().toString();
            switch(tipo){
                case "Diária":
                    return 1;
                case "Semanal":
                    return 7;
                case "Mensal":
                    return 30;
                case "Trimestral":
                    return 90;
                case "Quadrimestral":
                    return 120;
                case "Semestral":
                    return 180;
                case "Anual":
                    return 365;
            }
            ArrayList <Servicos> servicos = servicosDao.pesquisarServicos("SELECT * FROM tblServicos WHERE periodo = '"+tipo+"'");
            if(servicos!=null){
                return servicos.get(0).getPeriodDays();
            }
        }
        else{
            
        }
        
        return this.periodOfOutro();
    }
    
    private int periodOfOutro(){
        String period = view.getComboDias().getSelectedItem().toString();
        int dias=0;
        if(view.getCampoDias() !=null){
         dias = Integer.parseInt(view.getCampoDias().getText());
        }
        
        if(period.equals("M")){
            return 30*dias;
        }
        if(period.equals("A")){
            return 365*dias;
        }
        return dias;
    }
    
    private Servicos retornarServicoAnterior(int codServico){
        return servicosDao.pesquisarServicos("SELECT * FROM tblServicos WHERE codServico = "+codServico).get(0);
    }
    
    private boolean retornarAlunosUsando(int codServico){
        ArrayList <Aluno> alunos = alunoDao.pesquisarAlunos("SELECT * FROM tblAlunos WHERE codServico = "+codServico);
        return alunos ==null;
    }
    
    private boolean verificarPeriodo(int period){
        BigDecimal periodDays = new BigDecimal(period);
        
        String mensal = periodDays.divide(new BigDecimal(30), 2, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
        String anual = periodDays.divide(new BigDecimal(365), 3, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();

        
        boolean resultadoMensal = mensal.matches("[0-9]*");
        boolean resultadoAnual = anual.matches("[0-9]*");
        
        if(resultadoMensal||resultadoAnual){
            return true;
        }
        else{
            return period<30;
        }
    }
}
