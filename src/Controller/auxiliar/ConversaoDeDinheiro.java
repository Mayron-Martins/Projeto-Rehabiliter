/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.auxiliar;

import java.math.BigDecimal;

/**
 *
 * @author Mayro
 */
public class ConversaoDeDinheiro {
    
    public BigDecimal converterParaBigDecimal(String valor){
       valor = valor.replace(".", "");
       valor = valor.replace(",", ".");
       valor = valor.trim();
       
       return new BigDecimal(valor);
    }
    
}
