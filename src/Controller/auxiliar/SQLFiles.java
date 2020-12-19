/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.auxiliar;

import java.io.File;

/**
 *
 * @author Mayro
 */
public class SQLFiles {
    private final ExportarArquivos exportar = new ExportarArquivos();
    
    public void stopDatabase(){
        File arquivo = new File("C:/Rehabiliter/Components/System/stopDatabase.sql");
        if(!arquivo.exists()){
            String comando = "DECLARE @query VARCHAR(MAX) = ''\n"
                    + "SELECT\n"
                    + "@query = COALESCE(@query, ',') + 'KILL ' + CONVERT(VARCHAR, spid) + '; '\n"
                    + "FROM\n"
                    + "master..sysprocesses\n"
                    + "WHERE\n"
                    + "dbid = DB_ID('Rehabiliter_Database')\n"
                    + "AND dbid > 4\n"
                    + "AND spid <> @@SPID\n"
                    + "IF (LEN(@query) > 0)\n"
                    + "EXEC(@query)";

            exportar.geraArquivoTxt(comando, "C:/Rehabiliter/Components/System/stopDatabase.sql");
        }
    }
}
