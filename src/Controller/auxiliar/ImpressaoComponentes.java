/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.auxiliar;

import Dao.AlunosDao;
import Model.Aluno;
import Model.Vendas;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.ServiceUI;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

/**
 *
 * @author Mayro
 */
public class ImpressaoComponentes {
    
    public ImpressaoComponentes() {
        //detectaImpressoras();
    }
    
    public void imprimirTabelas(String titulo, String data, JTable tabela){
        MessageFormat cabecalho = new MessageFormat(titulo);
        MessageFormat rodape = new MessageFormat(data);
        
        try {
            tabela.print(JTable.PrintMode.FIT_WIDTH, cabecalho, rodape);
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível imprimir a tabela");
        }
    }
    

    public void impressao(String caminho){
        
        try {
            PDDocument document = PDDocument.load(new File(caminho));
            PrintService impressoraPadrao = PrintServiceLookup.lookupDefaultPrintService();
            PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
            PrintService printService = ServiceUI.printDialog(null, 300, 200, printServices, impressoraPadrao, DocFlavor.INPUT_STREAM.AUTOSENSE, new HashPrintRequestAttributeSet()).createPrintJob().getPrintService();

            PrintService myPrintService = findPrintService(printService.getName(), printServices);

            PrinterJob job = PrinterJob.getPrinterJob();    
            job.setPageable(new PDFPageable(document));
            job.setPrintService(myPrintService);
            job.print();
            document.close();
        } catch (IOException | PrinterException ex) {
            JOptionPane.showMessageDialog(null, "Não é possível imprimir");
        }
        
    }
    
    private static PrintService findPrintService(String printerName, PrintService[] printServices) {
        for (PrintService printService : printServices) {
        if (printService.getName().trim().equals(printerName)) {
        return printService;
        }
        }
        return null;
    }
    
    public void imprimirString(String texto){
        InputStream prin = new ByteArrayInputStream(texto.getBytes());
        DocFlavor docFlavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        SimpleDoc documentoTexto = new SimpleDoc(prin, docFlavor, null);
        PrintService impressoraPadrao = PrintServiceLookup.lookupDefaultPrintService();
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        PrintService printService = ServiceUI.printDialog(null, 300, 200, printServices, impressoraPadrao, DocFlavor.INPUT_STREAM.AUTOSENSE, new HashPrintRequestAttributeSet()).createPrintJob().getPrintService();
        
        PrintService impressora = findPrintService(printService.getName(), printServices);
        PrintRequestAttributeSet printerAttributes= new HashPrintRequestAttributeSet();
        printerAttributes.add(new JobName("Impressão", null));
        printerAttributes.add(OrientationRequested.PORTRAIT);
        printerAttributes.add(MediaSizeName.ISO_A4);
        //Informa o tipo da folha
        DocPrintJob printJob = impressora.createPrintJob();
        
        try{
            printJob.print(documentoTexto, printerAttributes);
        } catch (PrintException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível emitir Comprovante");
        }
        
        try {
            prin.close();
        } catch (IOException ex) {
            Logger.getLogger(ImpressaoComponentes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
