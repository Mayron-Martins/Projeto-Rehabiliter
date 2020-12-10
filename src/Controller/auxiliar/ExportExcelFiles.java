/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.auxiliar;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Mayro
 */
public class ExportExcelFiles {
    private final DefaultTableModel tabela;
    private final String caminho;

    public ExportExcelFiles(DefaultTableModel tabela, String caminho) {
        this.tabela = tabela;
        this.caminho = caminho;
    }
    
    public void exportarArquivoExcel() {
 
        //First Download Apache POI Library For Dealing with excel files.
        //Then add the library to the current project
        FileOutputStream excelFos=null;
        XSSFWorkbook excelJTableExport=null;
        BufferedOutputStream excelBos=null;
        try {
 
            //Escolhendo o local padrão para salvar
            //setar a localização
            JFileChooser salvarExcelnaPasta = new JFileChooser(System.getProperty("user.home")+this.caminho);
            //Caixa de diálogo que aparece durante salvamento
            salvarExcelnaPasta.setDialogTitle("Salvar como ...");
            //Filtrar apenas para os tipos xls, xlsx, xlsm
            FileNameExtensionFilter nomeExtensao = new FileNameExtensionFilter("EXCEL FILES", "xls", "xlsx", "xlsm");
            //Setting extension for selected file names
            salvarExcelnaPasta.setFileFilter(nomeExtensao);
            
            
            int chooser = salvarExcelnaPasta.showSaveDialog(null);
            //Checando se o botão salvar foi apertado
            if (chooser == JFileChooser.APPROVE_OPTION) {
                //If button is clicked execute this code
                excelJTableExport = new XSSFWorkbook();
                XSSFSheet excelSheet = excelJTableExport.createSheet("Jtable Sheet");
                
                
                //Loop through the jtable columns and rows to get its values
                for (int linhas = 0; linhas < tabela.getRowCount(); linhas++) {
                    XSSFRow excelRow = excelSheet.createRow(linhas);
                    for (int colunas = 0; colunas < tabela.getColumnCount(); colunas++) {
                        XSSFCell excelCell = excelRow.createCell(colunas);
                        //Verifica se a célula não é nula
                        if(tabela.getValueAt(linhas, colunas)!=null){
                           excelCell.setCellValue(tabela.getValueAt(linhas, colunas).toString());   
                        }
                        else{
                           excelCell.setCellValue(""); 
                        }
                    }
                }
                excelFos = new FileOutputStream(salvarExcelnaPasta.getSelectedFile() + ".xlsx");
                excelBos = new BufferedOutputStream(excelFos);
                excelJTableExport.write(excelBos);
                JOptionPane.showMessageDialog(null, "Exportado com Sucesso!");
                
                if (excelFos != null) {
                    excelFos.close();
                    System.out.println("Não");
                }
                System.out.println("Ponto");
                if (excelBos != null) {
                    excelBos.close();
                    System.out.println("Não");
                }
                System.out.println("Ponto");
                if (excelJTableExport != null) {
                    excelJTableExport.close();
                    System.out.println("Não");
                }
                System.out.println("Ponto");
            }
 
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Criar Arquivo");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Escrever Dados");
        } 
    } 
}

