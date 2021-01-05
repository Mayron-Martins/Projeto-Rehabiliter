/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.auxiliar;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Mayro
 */
public class LogsSystem {
    private PrintWriter out;
    private String dataArquivo;
    
    public LogsSystem(){
       SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
       this.dataArquivo = sdf.format(new Date());
        try{
            this.out = new PrintWriter(new FileWriter("C:/Rehabiliter/Components/Logs/System error "+dataArquivo+".txt", true));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void gravarErro(Throwable erro){
            out.print("Data do Erro: ");
            out.println(dataArquivo);
            out.print("Mensagem de erro: ");
            out.println(erro);
            out.print("Stacktrace: ");
            erro.printStackTrace(out);
    }
    
    public void close(){
        if(this.out!=null){
            out.flush();
            out.close();
        }
    }
}
