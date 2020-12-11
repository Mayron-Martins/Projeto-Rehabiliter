/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.auxiliar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Mayro
 */
public class ExportarArquivos {
    private final TableModel tabela;
    private final String caminho;

    public ExportarArquivos(TableModel tabela, String caminho) {
        this.tabela = tabela;
        this.caminho = caminho;
    }
    
    
    
    public void exportarExcel(){
        JFileChooser excelExportChooser = new JFileChooser();
        excelExportChooser.setCurrentDirectory(new File(System.getProperty("user.home")+caminho));
        excelExportChooser.setDialogTitle("Salvar Aquivo do Excel");
        //filter the file
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivo do Excel", "*.xls", "xls", "xlsx", "xlsn");
        excelExportChooser.addChoosableFileFilter(filter);
        excelExportChooser.setFileFilter(filter);
        int excelchooser = excelExportChooser.showSaveDialog(null);

        if (excelchooser == JFileChooser.APPROVE_OPTION) {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("SalesReturnsDetails");

            Row row;
            Cell cell;

            try {
                // write the column headers
                row = sheet.createRow(0);
                for (int colunas = 0; colunas < this.tabela.getColumnCount(); colunas++) {
                    cell = row.createCell(colunas);
                    cell.setCellValue(this.tabela.getColumnName(colunas));
                }

                for (int linhas = 0; linhas < this.tabela.getRowCount(); linhas++) {

                    row = sheet.createRow(linhas + 1);

                    for (int colunas = 0; colunas < this.tabela.getColumnCount(); colunas++) {
                        cell = row.createCell(colunas);
                        
                        //Verificar se a célula é nula
                        if(tabela.getValueAt(linhas, colunas)!=null){
                           cell.setCellValue(tabela.getValueAt(linhas, colunas).toString());   
                        }
                        else{
                           cell.setCellValue(""); 
                        }

                    }

                }
                
               FileOutputStream excelFIS = new FileOutputStream(excelExportChooser.getSelectedFile() + ".xlsx");
               workbook.write(excelFIS);
               workbook.close();
               excelFIS.close();
                
               JOptionPane.showMessageDialog(null, "Exportado com Sucesso!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
     }
    
    public void exportarExcel(String ganhoTotal, String ganhoPendente, String despesaTotal, String ganhoRelativo, String totalParcial){
        JFileChooser excelExportChooser = new JFileChooser();
        excelExportChooser.setCurrentDirectory(new File(System.getProperty("user.home")+caminho));
        excelExportChooser.setDialogTitle("Salvar Aquivo do Excel");
        //filter the file
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivo do Excel", "*.xls", "xls", "xlsx", "xlsn");
        excelExportChooser.addChoosableFileFilter(filter);
        excelExportChooser.setFileFilter(filter);
        int excelchooser = excelExportChooser.showSaveDialog(null);

        if (excelchooser == JFileChooser.APPROVE_OPTION) {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("SalesReturnsDetails");

            Row row;
            Cell cell;

            try {
                // write the column headers
                row = sheet.createRow(0);
                for (int colunas = 0; colunas < this.tabela.getColumnCount(); colunas++) {
                    cell = row.createCell(colunas);
                    cell.setCellValue(this.tabela.getColumnName(colunas));
                }

                for (int linhas = 0; linhas < this.tabela.getRowCount(); linhas++) {

                    row = sheet.createRow(linhas + 1);

                    for (int colunas = 0; colunas < this.tabela.getColumnCount(); colunas++) {
                        cell = row.createCell(colunas);
                        
                        //Verificar se a célula é nula
                        if(tabela.getValueAt(linhas, colunas)!=null){
                           if(colunas==3){
                             cell.setCellValue("R$ "+tabela.getValueAt(linhas, colunas).toString());  
                           }else{
                             cell.setCellValue(tabela.getValueAt(linhas, colunas).toString());    
                           }
                             
                        }
                        else{
                           cell.setCellValue(""); 
                        }

                    }

                }
                
                //Adicionar uma Coluna Vazia
                row = sheet.createRow(tabela.getRowCount()+1);
                for (int colunas = 0; colunas < this.tabela.getColumnCount(); colunas++) {
                        cell = row.createCell(colunas);
                        cell.setCellValue(""); 
                }
                
                //Adicionar Títulos
                row = sheet.createRow(tabela.getRowCount()+2);
                cell = row.createCell(0);
                cell.setCellValue("Ganho Total");
                cell = row.createCell(1);
                cell.setCellValue("Pendente");
                cell = row.createCell(2);
                cell.setCellValue("Despesa Total");
                cell = row.createCell(3);
                cell.setCellValue("Ganho Relativo");
                cell = row.createCell(4);
                cell.setCellValue("Total Parcial");
                
                //Adicionar valores
                row = sheet.createRow(tabela.getRowCount()+3);
                cell = row.createCell(0);
                cell.setCellValue("R$ "+ganhoTotal);
                cell = row.createCell(1);
                cell.setCellValue("R$ "+ganhoPendente);
                cell = row.createCell(2);
                cell.setCellValue("R$ "+despesaTotal);
                cell = row.createCell(3);
                cell.setCellValue("R$ "+ganhoRelativo);
                cell = row.createCell(4);
                cell.setCellValue("R$ "+totalParcial);
                
               FileOutputStream excelFIS = new FileOutputStream(excelExportChooser.getSelectedFile() + ".xlsx");
               workbook.write(excelFIS);
               workbook.close();
               excelFIS.close();
                
               JOptionPane.showMessageDialog(null, "Exportado com Sucesso!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
     }
}
