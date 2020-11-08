/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.auxiliar;

import java.awt.Font;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import sun.swing.table.DefaultTableCellHeaderRenderer;

/**
 *
 * @author Mayro
 */
public class FormatacaoDeTabelas extends JTable{
    private DefaultTableCellRenderer formatar = new DefaultTableCellHeaderRenderer();
    

    public FormatacaoDeTabelas(char posicao, Font font, TableModel dataModel) {
        setFont(font);
        setModel(dataModel);
        formatar(this, posicao);
    }
    
    
    
    
    public void formatar(JTable tabela, char posicao){
        DefaultTableModel modelodeTabela = (DefaultTableModel)tabela.getModel();
        tabela.setRowSorter(new TableRowSorter(modelodeTabela));
        int numColunas =tabela.getColumnCount();
        switch(posicao){
            case 'E':
                formatar.setHorizontalAlignment(SwingConstants.LEFT);
                for(int cont=0; cont<numColunas;cont++){
                    tabela.getColumnModel().getColumn(cont).setCellRenderer(formatar);
                }
                formatar = (DefaultTableCellRenderer) tabela.getTableHeader().getDefaultRenderer();
                formatar.setHorizontalAlignment(SwingConstants.LEFT);
            break;
            case 'D':
                formatar.setHorizontalAlignment(SwingConstants.RIGHT);
                for(int cont=0; cont<numColunas;cont++){
                    tabela.getColumnModel().getColumn(cont).setCellRenderer(formatar);
                }
                formatar = (DefaultTableCellRenderer) tabela.getTableHeader().getDefaultRenderer();
                formatar.setHorizontalAlignment(SwingConstants.RIGHT);
            break;
            case 'C':
                formatar.setHorizontalAlignment(SwingConstants.CENTER);
                for(int cont=0; cont<numColunas;cont++){
                    tabela.getColumnModel().getColumn(cont).setCellRenderer(formatar);
                }
                formatar = (DefaultTableCellRenderer) tabela.getTableHeader().getDefaultRenderer();
                formatar.setHorizontalAlignment(SwingConstants.CENTER);
            break;
        }
    }
    
     
}