/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.auxiliar;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.ColorModel;
import java.awt.print.Book;
import java.awt.print.Pageable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.ServiceUIFactory;
import javax.print.SimpleDoc;
import javax.print.StreamPrintService;
import javax.print.attribute.Attribute;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttribute;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttribute;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.RequestingUserName;
import javax.print.event.PrintServiceAttributeListener;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocument1;
import sun.print.PSPrinterJob;
import sun.print.PSStreamPrintService;
import sun.print.PageableDoc;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.standard.Sides;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

/**
 *
 * @author Mayro
 */
public class ImpressaoComponentes {
    private static PrintService impressora;
    
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
}
