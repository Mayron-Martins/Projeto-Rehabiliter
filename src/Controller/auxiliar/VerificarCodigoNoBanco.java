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
    public long verificarUltimo(String tabela, String dado) throws SQLException{
        long ultimo=0;
        String comandoDeBusca = "SELECT ";
        comandoDeBusca = comandoDeBusca.concat("MAX("+dado+") AS valor FROM "+tabela);
        Conexao conexao = new Conexao() {};
        Statement statement = conexao.getConnection().createStatement();
        ResultSet resultset = statement.executeQuery(comandoDeBusca);
        
        while(resultset.next()){
            if(resultset.getRow()== 0){
                return ultimo;
            }
            else{
                ultimo =resultset.getInt("valor");
            }
        }
        statement.close();
        return ultimo;
    }
}
