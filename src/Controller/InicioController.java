/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.auxiliar.ConversaodeDataParaPadraoDesignado;
import Controller.auxiliar.LogsSystem;
import Dao.Conexao;
import Model.Funcionario;
import Dao.FrequenciaFuncionariosDao;
import Dao.FuncionarioDao;
import Model.auxiliar.FrequenciaFuncionarios;
import View.inicio;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Mayro
 */
public class InicioController {
    private final inicio telaDeInicio;
    private final FuncionarioDao funcionariosDao = new FuncionarioDao();
    private final FrequenciaFuncionariosDao frequencia = new FrequenciaFuncionariosDao();
    private final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();

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
    
    private boolean verificarFuncionarios(){
        ArrayList <Funcionario> funcionarios = funcionariosDao.selecionarTodosFuncionarios();
        return funcionarios == null;
    }
    
}
