/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.auxiliar;

import java.util.ArrayList;

/**
 *
 * @author Mayro
 */
public class ConversaoDiasDaSemana {
    
    public String converterDiasDaSemana(String diasDaSemana){
        String diasformatados = diasDaSemana.replaceAll("null,", "");
        diasformatados = diasformatados.replaceAll("null", "");
        
        return diasformatados;
    }
    
    
}
