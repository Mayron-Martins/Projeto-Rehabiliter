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
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.Doc;
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
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;
import javax.swing.SwingUtilities;
import jdk.internal.util.xml.impl.ReaderUTF8;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.xmlbeans.impl.common.ReaderInputStream;

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
    
    public void imprimirString(String caminho){
        InputStream prin = new ByteArrayInputStream(caminho.getBytes());
        DocFlavor docFlavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        SimpleDoc documentoTexto = new SimpleDoc(prin, docFlavor, null);
        PrintService impressoraPadrao = PrintServiceLookup.lookupDefaultPrintService();
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        PrintService printService = ServiceUI.printDialog(null, 300, 200, printServices, impressoraPadrao, DocFlavor.INPUT_STREAM.AUTOSENSE, new HashPrintRequestAttributeSet()).createPrintJob().getPrintService();
        
        PrintService impressora = findPrintService(printService.getName(), printServices);
        PrintRequestAttributeSet printerAttributes= new HashPrintRequestAttributeSet();
        //printerAttributes.add(new JobName("Impressão", null));
        //printerAttributes.add(OrientationRequested.PORTRAIT);
        //printerAttributes.add(MediaSizeName.ISO_A4);
        //Informa o tipo da folha
        DocPrintJob printJob = impressora.createPrintJob();
        
        
        try{
            printJob.print(documentoTexto, null);
        } catch (PrintException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível emitir Comprovante");
        }
        
        try {
            prin.close();
        } catch (IOException ex) {
            Logger.getLogger(ImpressaoComponentes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void printTextFile(String caminho){
        try{
            //FileInputStream in = new FileInputStream(new File(caminho));
           BufferedReader myBuffer = new BufferedReader(new InputStreamReader(new FileInputStream(caminho), "UTF-8"));
           String texto = myBuffer.readLine();
           String conect="";
           while(texto!=null){
               conect+=texto+"\n";
               texto = myBuffer.readLine();
           }
           InputStream in = new ByteArrayInputStream((conect+"\f").getBytes("UTF-8"));
            
            PrintRequestAttributeSet  pras = new HashPrintRequestAttributeSet();
            pras.add(new Copies(1));
            
            DocFlavor flavor = new DocFlavor.INPUT_STREAM ("application/octet-stream");
            SimpleDoc doc = new SimpleDoc(in, flavor, null);
            
            PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
            
            PrintService impressoraPadrao = PrintServiceLookup.lookupDefaultPrintService();
            PrintService[] printServices = PrintServiceLookup.lookupPrintServices(flavor, aset);           
            if (printServices.length == 0) {
                if (impressoraPadrao == null) {
                  JOptionPane.showMessageDialog(null, "Não foram encontradas impressoras");
                } else {
                  DocPrintJob job = impressoraPadrao.createPrintJob();
                  PrintJobWatcher pjw = new PrintJobWatcher(job);
                  job.print(doc, aset);
                  pjw.waitForDone();
                  myBuffer.close();
                }
            } else {
                SwingUtilities.invokeLater(() -> {
                  PrintService service = ServiceUI.printDialog(null, 200, 200,
                      printServices, impressoraPadrao, flavor, aset);
                  if (service != null) {
                    DocPrintJob job = service.createPrintJob();
                    PrintJobWatcher pjw = new PrintJobWatcher(job);
                      try {
                          job.print(doc, aset);
                          pjw.waitForDone();
                          myBuffer.close();
                      } catch (PrintException ex) {
                          Logger.getLogger(ImpressaoComponentes.class.getName()).log(Level.SEVERE, null, ex);
                      } catch (IOException ex) {
                          Logger.getLogger(ImpressaoComponentes.class.getName()).log(Level.SEVERE, null, ex);
                      }
                    
                  }
                });
            }
            
            
            /*
            DocPrintJob job = service.createPrintJob();
            PrintJobWatcher pjw = new PrintJobWatcher(job);
            job.print(doc, pras);
            pjw.waitForDone();
            //in.close();
            myBuffer.close();
            */
            /*
            // send FF to eject the page
            InputStream ff = new ByteArrayInputStream("\f".getBytes("UTF-8"));
            Doc docff = new SimpleDoc(ff, flavor, null);
            DocPrintJob jobff = service.createPrintJob();
            pjw = new PrintJobWatcher(jobff);
            jobff.print(docff, null);
            pjw.waitForDone();
*/
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Nenhum arquivo encontrado");
        } catch (PrintException ex) {
            Logger.getLogger(ImpressaoComponentes.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao enviar para a impressora");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao finalizar conexão");
            Logger.getLogger(ImpressaoComponentes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
  


    class PrintJobWatcher {
        boolean done = false;

        PrintJobWatcher(DocPrintJob job) {
            job.addPrintJobListener(new PrintJobAdapter() { 
                public void printJobCanceled(PrintJobEvent pje) {
                    System.out.println("printJobCanceled");
                    allDone();
                }
                public void printJobCompleted(PrintJobEvent pje) {
                    System.out.println("printJobCompleted");
                    allDone();
                }
                public void printJobFailed(PrintJobEvent pje) {
                    System.out.println("printJobFailed");
                    allDone();
                }
                public void printJobNoMoreEvents(PrintJobEvent pje) {
                    System.out.println("printJobNoMoreEvents");
                    allDone();
                }
                void allDone() {
                    synchronized (PrintJobWatcher.this) {
                        done = true;
                        System.out.println("Printing done ...");
                        PrintJobWatcher.this.notify();
                    }
                }
            });
        }
        
        public synchronized void waitForDone() {
            try {
                while (!done) {
                    wait();
                }
            } catch (InterruptedException e) {
            }
        }
    }
}
