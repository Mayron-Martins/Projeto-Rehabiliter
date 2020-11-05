/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Dao.Conexao;
import View.inicio;
import java.sql.SQLException;

/**
 *
 * @author Mayro
 */
public class InicioController {
    private final inicio telaDeInicio;

    public InicioController(inicio view) {
        this.telaDeInicio = view;
    }
    
    public void iniciarCriacaoBanco() throws SQLException{
        Conexao conexao = new Conexao() {};
        conexao.testConnection(telaDeInicio);
    }
    
    
    
}
