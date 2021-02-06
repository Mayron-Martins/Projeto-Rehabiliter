/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.auxiliar;

import Scripts.Sqlcmd;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 *
 * @author Mayro
 */
public class Utilitarios {
    private final ExportarArquivos exportar = new ExportarArquivos();
    private final Sqlcmd sqlcmd = new Sqlcmd();
    
    public void inicializacao(){
        String conteudo = "Version = 1.0.0\n"
            + "Update Notes= ON\n"
            + "Execute Script Master = OFF\n"
            + "Set Pay = OFF";
        
        File arquivo = new File("C:/Rehabiliter/Components/System/Inicial.txt");
        
        if(!arquivo.exists()){
            exportar.geraArquivoTxt(conteudo, "C:/Rehabiliter/Components/System/Inicial.txt");
        }
    }
    
    public boolean testeMaster(){
        return teste("Master", "OFF", "ON");
    }
    
    public boolean testeNotes(){
        return teste("Notes", "ON", "OFF");
    }
    
    public boolean testePay(String anterior, String posterior){
        return teste("Pay", anterior, posterior);
    }
    
    private boolean teste(String referencia, String valorOriginal, String substituto){
        try{
           BufferedReader myBuffer = new BufferedReader(new InputStreamReader(new FileInputStream("C:/Rehabiliter/Components/System/Inicial.txt")));
           String texto = myBuffer.readLine();
           String conect="";
           boolean alterado = false;
           while(texto!=null){
               if(texto.contains(referencia)){
                   if(texto.contains(valorOriginal)){
                       texto = texto.replace(valorOriginal, substituto);
                       alterado = true;
                   }
               }
               conect+=texto+"\n";
               texto = myBuffer.readLine();
           }
           myBuffer.close();
           if(alterado){
               File file = new File("C:/Rehabiliter/Components/System/Inicial.txt");
               file.delete();
               exportar.geraArquivoTxt(conect, "C:/Rehabiliter/Components/System/Inicial.txt");
           }
           return alterado;
        } catch (IOException ex) {
            gerarLog(ex);
            return false;
        } 
    }
    
    //Gerar arquivo com o log de erro, caso haja
    private void gerarLog(Throwable erro){
        LogsSystem gerarLog = new LogsSystem();
        gerarLog.gravarErro(erro);
        gerarLog.close();
    }
}
