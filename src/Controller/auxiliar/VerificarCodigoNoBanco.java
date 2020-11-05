/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.auxiliar;

import Dao.Conexao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Mayro
 */
public class VerificarCodigoNoBanco {
    public int verificarUltimo(String tabela, String dado) throws SQLException{
        int ultimo=0;
        String comandoDeBusca = "SELECT ";
        comandoDeBusca = comandoDeBusca.concat("MAX("+dado+") FROM "+tabela);
        Conexao conexao = new Conexao() {};
        Statement statement = conexao.getConnection().createStatement();
        ResultSet resultset = statement.executeQuery(comandoDeBusca);
        
        while(resultset.next()){
            if(resultset.getObject(0)==null){
                return ultimo;
            }
            else{
                ultimo =(int) resultset.getObject(0);
            }
        }
        statement.close();
        return ultimo;
    }
}
