/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.auxiliar;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Mayro
 */
public class LogsSystem {
    private final ExportarArquivos exportar = new ExportarArquivos();
    
    public void logIssue(String erro){
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
            String dataArquivo = sdf.format(new Date());
            exportar.geraArquivoTxt(erro, "C:/Rehabiliter/Components/Logs/System error "+dataArquivo+".txt");
    }
    
}
