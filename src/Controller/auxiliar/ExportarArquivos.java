/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.auxiliar;

import Model.Aluno;
import Model.auxiliar.EnderecoAlunos;
import Model.auxiliar.Planos;
import Model.auxiliar.Servicos;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import java.io.FileWriter;
import java.io.PrintWriter;


/**
 *
 * @author Mayro
 */
public class ExportarArquivos {
    private final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();

    
    
    
    public void exportarExcel(TableModel tabela, String caminho){
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
                for (int colunas = 0; colunas < tabela.getColumnCount(); colunas++) {
                    cell = row.createCell(colunas);
                    cell.setCellValue(tabela.getColumnName(colunas));
                }

                for (int linhas = 0; linhas < tabela.getRowCount(); linhas++) {

                    row = sheet.createRow(linhas + 1);

                    for (int colunas = 0; colunas < tabela.getColumnCount(); colunas++) {
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
    
    public void exportarExcel(TableModel tabela, String caminho, String ganhoTotal, String ganhoPendente, String despesaTotal, String ganhoRelativo, String totalParcial){
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
                for (int colunas = 0; colunas < tabela.getColumnCount(); colunas++) {
                    cell = row.createCell(colunas);
                    cell.setCellValue(tabela.getColumnName(colunas));
                }

                for (int linhas = 0; linhas < tabela.getRowCount(); linhas++) {

                    row = sheet.createRow(linhas + 1);

                    for (int colunas = 0; colunas < tabela.getColumnCount(); colunas++) {
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
                for (int colunas = 0; colunas < tabela.getColumnCount(); colunas++) {
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
    
        public void exportarContratoWord(Aluno aluno, EnderecoAlunos endereco, Servicos servico, Planos plano) throws InvalidFormatException{
        try {
            //Local do Arquivo Contrato
            FileInputStream contrato = new FileInputStream("C:/Rehabiliter/Contrato.docx");
            //Local Onde o Arquivo ContratoEditado ficará
            FileOutputStream novoContrato = new FileOutputStream(new File("C:/Rehabiliter/ContratoEditado.docx"));
            //criação dos docs
            //XWPFDocument documento = new XWPFDocument(contrato);
            XWPFDocument documentoNovo = new XWPFDocument(OPCPackage.open(contrato));
            
            //Dados do Aluno
            //Verifica o Endereço completo
            String enderecoCompleto = endereco.getLogradouro()+", "
                    +endereco.getNumero()+", "+endereco.getBairro()+",  "+endereco.getCep();
            
            //Verifica de há algum telefone
            String telefone="";
            if(aluno.getTelefone()==null){
                if(aluno.getTelefonedamae()==null){
                    telefone = aluno.getTelefonedopai();
                }else{telefone = aluno.getTelefonedamae();}
            }else{telefone = aluno.getTelefone();}
            
            //Verifica se há algum celular
            String celular="";
            if(aluno.getCelular()!=null){celular = aluno.getCelular();}
            
            //Verifica qual responsável o aluno possui
            String responsavel="";
            if(aluno.getNomedamae()==null){
                if(aluno.getNomedopai()!=null){
                    responsavel = aluno.getNomedopai();
                }
            }else{responsavel = aluno.getNomedamae();}
            
            int ordem =0;
            for(XWPFParagraph listaParagrafos : documentoNovo.getParagraphs()){
                for(XWPFRun run : listaParagrafos.getRuns()){
                    if(run.getText(0)!=null){
                        String palavra = run.getText(0);
                        
                        if(palavra.contains("MATRICULA")&&ordem==0){palavra = palavra.replace("MATRICULA", plano.getChavePlano()+""); ordem++;}
                        if(palavra.contains("NOME")&&ordem==1){palavra = palavra.replace("NOME", aluno.getNome());ordem++;}
                        if(palavra.contains("ENDERECO")&&ordem==2){palavra = palavra.replace("ENDERECO", enderecoCompleto);ordem++;}
                        if(palavra.contains("TELEFONE")&&ordem==3){palavra = palavra.replace("TELEFONE", telefone);ordem++;}
                        if(palavra.contains("CELULAR")&&ordem==4){palavra = palavra.replace("CELULAR", celular);ordem++;}
                        if(palavra.contains("DATADENASCIMENTO")&&ordem==5){palavra = palavra.replace("DATADENASCIMENTO", converterData.parseDate(aluno.getDatadenascimento()));ordem++;}
                        if(palavra.contains("RESPONSAVEL")&&ordem==6){palavra = palavra.replace("RESPONSAVEL", responsavel);ordem++;}
                        if(palavra.contains("PLANO")&&ordem==7){palavra = palavra.replace("PLANO", servico.getPeriodo());ordem++;}
                        if(palavra.contains("DATA")&&ordem==8){palavra = palavra.replace("DATA", converterData.parseDate(aluno.getDataCadastro()));ordem++;}
                        if(palavra.contains("MODALIDADE")&&ordem==9){palavra = palavra.replace("MODALIDADE", servico.getPeriodo());ordem++;}
                        if(palavra.contains("FORMADEPAGAMENTO")&&ordem==10){palavra = palavra.replace("FORMADEPAGAMENTO", servico.getFormaPagamento());ordem++;}
                        if(palavra.contains("VALORMATRICULA")&&ordem==11){palavra = palavra.replace("VALORMATRICULA", "R$ "+aluno.getValorContrato());ordem++;}
                        if(palavra.contains("DATAINICIO")&&ordem==12){palavra = palavra.replace("DATAINICIO", converterData.parseDate(aluno.getDataCadastro()));ordem++;}
                        palavra = palavra.replace("{", "");
                        palavra = palavra.replace("}", "");
                        run.setText(palavra, 0);
                    }
                    
                }
            }
            
            documentoNovo.write(novoContrato);
            documentoNovo.close();
            contrato.close();
            novoContrato.close();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Arquivo Contrato.docx não encontrado em C:/Rehabiliter");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (ParseException ex) {
            Logger.getLogger(ExportarArquivos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void convertDocx2pdf(String docxFilePath) {
        File docxFile = new File(docxFilePath);
        String pdfFile = docxFilePath.substring(0, docxFilePath.lastIndexOf(".docx")) + ".pdf";

        if (docxFile.exists()) {
            if (!docxFile.isDirectory()) { 
                ActiveXComponent app = null;
                try {
                    ComThread.InitMTA(true); 
                    app = new ActiveXComponent("Word.Application");
                    Dispatch documents = app.getProperty("Documents").toDispatch();
                    Dispatch document = Dispatch.call(documents, "Open", docxFilePath, false, true).toDispatch();
                    File target = new File(pdfFile);
                    if (target.exists()) {
                        target.delete();
                    }
                    Dispatch.call(document, "SaveAs", pdfFile, 17);
                    Dispatch.call(document, "Close", false);
                    //long end = System.currentTimeMillis();
                    //logger.info("============Convert Finished：" + (end - start) + "ms");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Não foi possível Converter");
                } finally {
                    if (app != null) {
                        app.invoke("Quit", new Variant[] {});
                    }
                    ComThread.Release();
                }
            }
        }
    }
    
    private void geraArquivoTxt(String conteudo) {
		try {
			FileWriter writer = new FileWriter(new File("C:/Rehabiliter/Comprovante.txt"));  
			PrintWriter saida = new PrintWriter(writer);
			saida.print(conteudo);
			saida.close();  
			writer.close();
			System.out.println("Arquivo criado com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
}
