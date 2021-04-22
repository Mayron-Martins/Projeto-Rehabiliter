/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.auxiliar.ConversaoDeDinheiro;
import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Dao.DetOrcamentarioDao;
import Dao.FuncionarioDao;
import Dao.GastosDao;
import Dao.LogAçoesFuncionarioDao;
import Model.Funcionario;
import Model.auxiliar.Gastos;
import Model.auxiliar.LogAçoesFuncionario;
import View.FinanceiroPlanodeContra;
import View.LoginFuncionario;
import View.LoginGerente;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mayro
 */
public class PlanoGastosController {
    protected final FinanceiroPlanodeContra view;
    protected final DefaultTableModel tabelaGastos;
    protected final GastosDao gastosDao = new GastosDao();
    protected final DetOrcamentarioDao orcamentarioDao = new DetOrcamentarioDao();
    protected final FuncionarioDao funcionarioDao = new FuncionarioDao();
    protected final LogAçoesFuncionarioDao logDao = new LogAçoesFuncionarioDao();
    protected final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();
    protected final ConversaoDeDinheiro converterDinheiro = new ConversaoDeDinheiro();

    public PlanoGastosController(FinanceiroPlanodeContra view) {
        this.view = view;
        this.tabelaGastos = (DefaultTableModel) view.getTabelaGastos().getModel();
    }

        //Limpar tabela
    public void limparTabelaGastos(){
        int quantLinhas = this.view.getTabelaGastos().getRowCount();
        for(int quant=0; quant<quantLinhas; quant++){
            this.tabelaGastos.removeRow(0);
        }
    }
    
    //Setar Tabelas conforme forma Detalha ou Resumida
    public void setarTabelas(){
        if(view.getBotaoVResumida().isEnabled()){
            this.setarTabelasResumidas();
        }
        else{
            this.setarTabelasDetalhadas();
        }
    }
    
    //Parte Detalhada
    protected void setarTabelasDetalhadas(){
        int tipoSelecionado = view.getComboTipos().getSelectedIndex();
        String tipo = "LIKE 'Pagamento Salarial%'";
        ArrayList <Gastos> gastos;
        switch(tipoSelecionado){
            case 0:
                tipo = "NOT "+tipo;
                gastos = this.pegarGastosNoPeriodo(tipo);
                if(gastos!=null){
                   this.setarGastosDetalhados(gastos);
                }
                else{
                   view.exibeMensagem("Sem Dados de Gastos");
                   limparTabelaGastos();
                }
            break;
            
            case 1:
                gastos = this.pegarGastosNoPeriodo(tipo);
                if(gastos!=null){
                   this.setarGastosDetalhados(gastos);
                }
                else{
                   view.exibeMensagem("Sem Dados de Gastos");
                   limparTabelaGastos();
                }
            break;
        }
        
        
        
        
    }
    
    //Setar Entradas Detalhadas
    protected void setarGastosDetalhados(ArrayList <Gastos> gastos){
        limparTabelaGastos();
        for(Gastos gasto : gastos){
            Object[] dadosDaTabela = {gasto.getCodBanco(), gasto.getMotivo(), gasto.getValorGasto(), gasto.getDataCadastro()};
            tabelaGastos.addRow(dadosDaTabela);
        }
    }
    //__________________________________________________________________________
    
    //Parte Resumida
    //Setar Tabelas Resumidamente
    protected void setarTabelasResumidas(){
        int tipoSelecionado = view.getComboTipos().getSelectedIndex();
        String tipo = "LIKE 'Pagamento Salarial%'";
        ArrayList <Gastos> gastos;
        
        switch(tipoSelecionado){
            //Contra-Serviços sem Pagamento de Funcionários
            case 0:
                tipo = "NOT "+tipo;
                gastos = this.pegarGastosNoPeriodo(tipo);
                if(gastos!=null){
                   this.setarGastosResumidos(gastos);
                }
                else{
                   view.exibeMensagem("Sem Dados de Gastos");
                   limparTabelaGastos();
                }
            break;
            
            case 1:
                gastos = this.pegarGastosNoPeriodo(tipo);
                if(gastos!=null){
                   this.setarGastosResumidos(gastos);
                }
                else{
                   view.exibeMensagem("Sem Dados de Gastos");
                   limparTabelaGastos();
                }
            break;
        }
    }
    
    
    //Setar Entradas Resumidamente
    protected void setarGastosResumidos(ArrayList <Gastos> gastos){
        limparTabelaGastos();
        
        String codGasto = "Diversos";
        String motivo = "Diversas";
        if(view.getComboTipos().getSelectedIndex()==1){
            motivo = "Pagamento Salarial";
        }
        BigDecimal valor = new BigDecimal("0");
        
        for(int linhas=0; linhas<gastos.size(); linhas++){
            Date dataGasto = gastos.get(linhas).getDataCadastro();
            
            //Datas anteriores e próximas
            Date dataEntradaBanco = converterData.parseDate(converterData.parseDate(dataGasto));
            Date dataProxima;
            Date dataAnterior;
            
            //Verifica se há uma próxima data
            if(linhas!=gastos.size()-1){dataProxima = gastos.get(linhas+1).getDataCadastro();}
            else{
                LocalDate data = converterData.conversaoLocalforDate(dataEntradaBanco).plusDays(1);
                dataProxima = converterData.conversaoLocalforDate(data);}
            
            //Verifica se há uma data anterior
            if(linhas!=0){dataAnterior= gastos.get(linhas-1).getDataCadastro();}
            else{
                LocalDate data = converterData.conversaoLocalforDate(dataEntradaBanco).plusDays(-1);
                dataAnterior = converterData.conversaoLocalforDate(data);}
            
            //Se a data do gasto for diferente da anterior, o valor é zerado
            if(dataGasto.compareTo(dataAnterior)!=0){
                valor = new BigDecimal("0"); 
            }
            //Caso contrário é somado ao montante
            valor = valor.add(gastos.get(linhas).getValorGasto());
            
            //Se minha próxima data for diferente da atual, seto o valor na tabela, caso contrário, segue a repetição
            if(dataGasto.compareTo(dataProxima)!=0){
            String dataGastoFormatada = converterData.parseDate(dataGasto);
            Object[] dadosDaTabela = {codGasto, motivo, valor, dataGastoFormatada};
            tabelaGastos.addRow(dadosDaTabela);
            }
        }
    }
    
    //Pega os Gastos em determinado Período
    protected ArrayList <Gastos> pegarGastosNoPeriodo(String tipo){
        LocalDate dataAtual = LocalDate.now();
        //Captura a data Atual e a torna principal
        Date dataPrincipal = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAtual));
        
        int periodo = view.getComboPeriodo().getSelectedIndex();
        //Verifica se há algum data específica e torna ela a principal
        if(view.getCampoDataEspecífica().isEnabled()&&view.getCampoDataEspecífica().getDate()!=null){
            periodo = 0;
            Date dataCampo = view.getCampoDataEspecífica().getDate();
            dataPrincipal = converterData.getSqlDate(dataCampo);
        }
        
        Date dataPassada = dataPrincipal;
        
        String formaPagamento = view.getComboPagamento().getSelectedItem().toString();
        
        //Se não houver uma data específica ou diária retorna o correspondente àquele período
        switch(periodo){
            case 2:
                dataPassada = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAtual.minusWeeks(1)));
            break;
            
            case 3:
                dataPassada = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAtual.minusMonths(1)));  
            break;
            
            case 4:
                dataPassada = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAtual.minusMonths(6))); 
            break;
            
            case 5:
                dataPassada = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAtual.minusYears(1)));
            break;
        }
        
        if(formaPagamento.equals("[Nenhum]")){
            return gastosDao.pesquisarGastos("SELECT * FROM tblGastos WHERE dataGasto BETWEEN '"+dataPassada+"' AND '"+dataPrincipal+"' AND motivo "+tipo+";");
        }else{
            return gastosDao.pesquisarGastos("SELECT * FROM tblGastos WHERE  formaPagamento = '"+formaPagamento+"' AND dataGasto BETWEEN '"+dataPassada+"' AND '"+dataPrincipal+"' AND motivo "+tipo+";");
        }         
    }
    
   //Fim das Funções de Setar Tabela
    //Editar Entradas
    public void editarGasto(){
        if(view.getBotaoVDetalhada().isEnabled()){
            if(view.getTabelaGastos().getSelectedRow()!=-1){
                int linhaSelecionada = view.getTabelaGastos().getSelectedRow();

                int codGasto = Integer.parseInt(tabelaGastos.getValueAt(linhaSelecionada, 0).toString());
                String motivo = tabelaGastos.getValueAt(linhaSelecionada, 1).toString();

                if(motivo.trim().equals("")){
                    view.exibeMensagem("Valores Inválidos!");
                }
                else{
                    gastosDao.atualizarMotivo(codGasto, motivo);     
                    this.setarLog("Edição de Gasto", "Editou o gasto "+motivo);

                    view.exibeMensagem("Sucesso!");
                    setarTabelas();
                    view.getTabelaGastos().addRowSelectionInterval(linhaSelecionada, linhaSelecionada);
                }                   
            }else{view.exibeMensagem("Selecione uma Linha na Tabela Gastos!");}
        }else{view.exibeMensagem("Selecione a Exibição Detalhada");}
    }
    
    //Remover entradas
    public void removerGasto(){
        if(view.getBotaoVDetalhada().isEnabled()){
            if(view.getPainelGastos().isVisible()){
                if(view.getTabelaGastos().getSelectedRow()!=-1){
                    int linhaSelecionada = view.getTabelaGastos().getSelectedRow();
                    
                    int codGasto = Integer.parseInt(tabelaGastos.getValueAt(linhaSelecionada, 0).toString());
                    String motivo = tabelaGastos.getValueAt(linhaSelecionada, 1).toString();
                    
                    gastosDao.removerGastos(codGasto);
                    orcamentarioDao.removerOrcamentario("Gastos", this.chaveGasto(codGasto));
                    

                    this.setarLog("Remoção de Gasto", "Removeu o gasto "+motivo);
                        
                    view.exibeMensagem("Sucesso!");
                    setarTabelas();
                                    
                }else{view.exibeMensagem("Selecione uma Linha na Tabela Gastos!");}
            }else{view.exibeMensagem("Permitida Remoção Somente de Gastos!");}
        }else{view.exibeMensagem("Selecione a Exibição Detalhada");}
    }
        
    protected long chaveGasto(int codGasto){
        Gastos gasto = gastosDao.pesquisarGastos("SELECT * FROM tblGastos WHERE codGasto = "+codGasto).get(0);
        return gasto.getChaveTransacao();
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
    
    public void ajuda(){
        String atalhos = "\u07CBESC = Sair da Tela\n"
                + "\u07CBCTRL + N = Cadastrar Novo Gasto\n";
        
        view.getPainelAjuda().setModal(true);
        view.getPainelAjuda().getCampoAtalhos().setText("");
        view.getPainelAjuda().getCampoAtalhos().append(atalhos);
        view.getPainelAjuda().setVisible(true);
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
