/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.auxiliar;

import Model.Aluno;
import Model.auxiliar.EnderecoAlunos;
import Model.auxiliar.Matriculas;
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
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Date;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.slf4j.LoggerFactory;



/**
 *
 * @author Mayro
 */
public class ExportarArquivos {
    private final ConversaodeDataParaPadraoDesignado converterData = new ConversaodeDataParaPadraoDesignado();
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ExportarArquivos.class);
    
    
    
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
    
        public boolean exportarContratoWord(Aluno aluno, EnderecoAlunos endereco, Servicos servico, Matriculas matricula, int diasContrato) throws InvalidFormatException{
        try {
            //Local do Arquivo Contrato
            boolean existenciaArquivo = true;
            File enderecoArquivo = new File("C:/Rehabiliter/Contrato.docx");
            if(!enderecoArquivo.exists()){
                existenciaArquivo = false;
                JOptionPane.showMessageDialog(null, "Contrato não encontrado em C:/Rehabiliter/, por favor, selecione o arquivo especificado!");
                FileNameExtensionFilter extensao = new FileNameExtensionFilter("Arquivos do Word", "docx");
                JFileChooser arquivoEscolhido = new JFileChooser();
                arquivoEscolhido.setFileFilter(extensao);
                arquivoEscolhido.setDialogTitle("Selecione o Contrato");
                arquivoEscolhido.setFileSelectionMode(JFileChooser.FILES_ONLY);
                //arquivoEscolhido.setMultiSelectionEnabled(false);
                int resposta = arquivoEscolhido.showOpenDialog(null);

                if(resposta == JFileChooser.APPROVE_OPTION){
                    enderecoArquivo = new File(arquivoEscolhido.getSelectedFile().getAbsolutePath());
                    existenciaArquivo = true;
                }
            }
            
            if(existenciaArquivo){
                FileInputStream contrato = new FileInputStream(enderecoArquivo.getAbsolutePath());

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

                String diaEncerramento = "Indefinido";
                if(aluno.getRenovacaoAutomatica()==0){
                    Date dataEnc = converterData.parseDate(converterData.parseDate(aluno.getDataCadastro()));
                    LocalDate dataEncerramento = converterData.conversaoLocalforDate(dataEnc).plusDays(diasContrato);
                    diaEncerramento = converterData.parseDate(converterData.conversaoLocalforDate(dataEncerramento));
                }

                int ordem =0;
                for(XWPFParagraph listaParagrafos : documentoNovo.getParagraphs()){
                    for(XWPFRun run : listaParagrafos.getRuns()){
                        if(run.getText(0)!=null){
                            String palavra = run.getText(0);

                            if(palavra.contains("MATRICULA")&&ordem==0){palavra = palavra.replace("MATRICULA", matricula.getMatricula()+""); ordem++;}
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
                            if(palavra.contains("DATAFIM")&&ordem==13){palavra = palavra.replace("DATAFIM", diaEncerramento);ordem++;}
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
                return true;
            }
            
        } catch (FileNotFoundException ex) {
            gerarLog(ex);
        } catch (IOException ex) {
            gerarLog(ex);
        }
        return false;
    }
    
        
    public void ConvertToPDF(String docPath) {
        String pdfPath = docPath.substring(0, docPath.lastIndexOf(".docx")) + ".pdf";
        try {
            InputStream doc = new FileInputStream(new File(docPath));
            XWPFDocument document = new XWPFDocument(doc);
            PdfOptions options = PdfOptions.create();
            OutputStream out = new FileOutputStream(new File(pdfPath));
            PdfConverter.getInstance().convert(document, out, options);
        } catch (IOException | NoClassDefFoundError ex) {
            gerarLog(ex);
        }
    }

    public void ConvertTxtToPDF(String pathPDF, String txtPath){
        try{
            Document pdfDoc = new Document(PageSize.A4);
            PdfWriter.getInstance(pdfDoc, new FileOutputStream(pathPDF))
                    .setPdfVersion(PdfWriter.PDF_VERSION_1_7);
            pdfDoc.open();

            Font myFont = new Font();
            myFont.setStyle(Font.NORMAL);
            myFont.setSize(11);
            pdfDoc.add(new Paragraph("\n"));

            BufferedReader myBuffer = new BufferedReader(new FileReader(txtPath));
            String conect="";
            while((conect = myBuffer.readLine())!=null){
                Paragraph para = new Paragraph(conect + "\n", myFont);
                System.out.println(conect);
                para.setAlignment(Element.ALIGN_CENTER);
                pdfDoc.add(para);
            }
            pdfDoc.close();
            myBuffer.close();
        } catch (FileNotFoundException ex) {
            gerarLog(ex);
        } catch (DocumentException | IOException ex) {
            gerarLog(ex);
        }
    }
    /*    
    public void convertDocx2pdf(String docxFilePath, String extensao, String idAplicacao) {
        File docxFile = new File(docxFilePath);
        String pdfFile = docxFilePath.substring(0, docxFilePath.lastIndexOf(extensao)) + ".pdf";
        if (docxFile.exists()) {
            if (!docxFile.isDirectory()) { 
                ActiveXComponent app = null;
                try {
                    ComThread.InitMTA(true); 
                    app = new ActiveXComponent(idAplicacao);
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
                    Logger.getLogger(ExportarArquivos.class.getName()).log(Level.SEVERE, null, e);
                } finally {
                    if (app != null) {
                        app.invoke("Quit", new Variant[] {});
                    }
                    ComThread.Release();
                }
            }
        }
    }
    */
    public void geraArquivoTxt(String conteudo, String caminho) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(caminho, true));  
			writer.write(conteudo);
			writer.newLine();
                        writer.flush();
                        writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    //Gerar arquivo com o log de erro, caso haja
    private void gerarLog(Throwable erro){
        LogsSystem gerarLog = new LogsSystem();
        gerarLog.gravarErro(erro);
        gerarLog.close();
    }
}
