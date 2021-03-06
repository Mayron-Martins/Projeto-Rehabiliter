/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Controller.auxiliar.LogsSystem;
import Controller.auxiliar.Utilitarios;
import Controller.auxiliar.VerificarCodigoNoBanco;
import Dao.Conexao;
import Dao.DetOrcamentarioDao;
import Model.Funcionario;
import Dao.FrequenciaFuncionariosDao;
import Dao.FuncionarioDao;
import Dao.GastosDao;
import Model.auxiliar.DetOrcamentario;
import Model.auxiliar.FrequenciaFuncionarios;
import Model.auxiliar.Gastos;
import View.inicio;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mayro
 */
public class InicioController {
    private final inicio telaDeInicio;
    private final FuncionarioDao funcionariosDao = new FuncionarioDao();
    private final GastosDao gastosDao = new GastosDao();
    private final DetOrcamentarioDao orcamentarioDao = new DetOrcamentarioDao();
    private final FrequenciaFuncionariosDao frequencia = new FrequenciaFuncionariosDao();
    private final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();
    private final VerificarCodigoNoBanco verificar = new VerificarCodigoNoBanco();
    private final Utilitarios utilitarios = new Utilitarios();

    public InicioController(inicio view) {
        this.telaDeInicio = view;
    }
    
    public void iniciarCriacaoBanco() throws SQLException{
        Conexao conexao = new Conexao() {};
        conexao.testConnection(telaDeInicio);
    }
    
    public void setarFrequenciaFuncionarios(){
        if(!this.verificarFuncionarios()){
            if(this.verificarSeHaDadosNoSistema()){
            ArrayList <Funcionario> funcionarios = funcionariosDao.selecionarTodosFuncionarios();

            LocalDate dataAtual = LocalDate.now();
            String dataInput = converterData.parseDate(converterData.conversaoLocalforDate(dataAtual));

            for(int linhas = 0; linhas<funcionarios.size(); linhas++){
                FrequenciaFuncionarios frequenciaFuncionarios = new FrequenciaFuncionarios(funcionarios.get(linhas).getCodBanco(), dataInput, "A");
                frequencia.inserirDados(frequenciaFuncionarios);
            }
        }
        }
    }
    private boolean verificarSeHaDadosNoSistema(){
        LocalDate dataAtual = LocalDate.now();
        Date dataInpult = converterData.getSqlDate(converterData.conversaoLocalforDate(dataAtual));
        ArrayList <FrequenciaFuncionarios> frequencias = frequencia.pesquisarFrequencias("SELECT * FROM tblFreqFuncionarios WHERE data = '"+dataInpult+"'");
        return frequencias==null;
    }
    
    public void setarPagamento(){
        LocalDate dataAtual = LocalDate.now();
        LocalDate dataAgendada = LocalDate.of(dataAtual.getYear(), dataAtual.getMonthValue(), 5);
        
        if(utilitarios.testePay(dataAgendada.getMonthValue()+"-"+dataAgendada.getYear())){
            this.verificarDataPagamento(dataAgendada);
        }
    }
    
    
    private void verificarDataPagamento(LocalDate dataAgendada){
        //Verificar se está entre os dias 5 e 7
        if(dataAgendada.getDayOfMonth()>=5&&dataAgendada.getDayOfMonth()<=7){
            //Verificar se é um fim de semana
            if(dataAgendada.getDayOfWeek()!=DayOfWeek.SATURDAY && dataAgendada.getDayOfWeek()!=DayOfWeek.SUNDAY){
                ArrayList <Funcionario> funcionarios = funcionariosDao.pesquisarFuncionario("SELECT * FROM tblFuncionarios WHERE situacao != 'Desvinculado'");
                //Verificar se há funcionários habilitados
                if(funcionarios!=null){

                    String motivo, formaPagamento = "Dinheiro";
                    Date dataCadastro = converterData.conversaoLocalforDate(dataAgendada);

                    Gastos gasto;
                    DetOrcamentario orcamentario;
                    for(Funcionario funcionario : funcionarios){
                        //Verificar se o salário não é zero
                        if(funcionario.getSalario().compareTo(BigDecimal.ZERO)>0){
                            motivo = "Pagamento Salarial do "+funcionario.getNome();

                            gasto = new Gastos(motivo, 1, formaPagamento, funcionario.getSalario(), dataCadastro, "Pago");
                            orcamentario = new DetOrcamentario("Gastos", formaPagamento, funcionario.getSalario(), dataCadastro, gasto.getChaveTransacao());

                            gastosDao.inserirDados(gasto);
                            orcamentarioDao.inserirDados(orcamentario);
                        }
                    }
                }
            }
            else{
                verificarDataPagamento(dataAgendada.plusDays(1));
            }
        }  
    }
    
    private boolean verificarFuncionarios(){
        ArrayList <Funcionario> funcionarios = funcionariosDao.selecionarTodosFuncionarios();
        return funcionarios == null;
    }
    
    private void gerarLog(Throwable erro){
        LogsSystem gerarLog = new LogsSystem();
        gerarLog.gravarErro(erro);
        gerarLog.close();
    }
}
