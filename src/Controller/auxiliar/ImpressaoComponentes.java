/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.auxiliar;

import java.awt.print.PrinterException;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author Mayro
 */
public class ImpressaoComponentes {
    
    public void imprimirTabelas(String titulo, String data, JTable tabela){
        MessageFormat cabecalho = new MessageFormat(titulo);
        MessageFormat rodape = new MessageFormat(data);
        
        try {
            tabela.print(JTable.PrintMode.FIT_WIDTH, cabecalho, rodape);
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível imprimir a tabela");
        }
        
        
    }
}
