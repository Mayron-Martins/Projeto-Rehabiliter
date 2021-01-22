/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scripts;

/**
 *
 * @author Mayro
 */
public class StopDatabase {
    public String script(){
        return "DECLARE @query VARCHAR(MAX) = ''\n"
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
    }
}
