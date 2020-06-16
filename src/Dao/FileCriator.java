/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import java.io.File;

/**
 *
 * @author Mayro
 */
public class FileCriator {
    
    //Função para criar pastas
    //OBS.: adicionar pastas filhas conforme necessário
    public void fileCriator(){
        File pastapai = new File("C:/Rehabiliter");
        File pastaDatabase = new File("C:/Rehabiliter/Databases");
        
        if(!pastapai.isFile()){
            pastapai.mkdirs();
            pastaDatabase.mkdirs();
        }
    }
}

