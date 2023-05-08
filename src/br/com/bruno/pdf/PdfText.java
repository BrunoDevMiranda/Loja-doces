package br.com.bruno.pdf;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfText {
    public PdfText(String fraseImprimir){
        Document documentPDF = new com.lowagie.text.Document();
        String arquivoPDF = "document/Relatorio.pdf";
        try{
            PdfWriter.getInstance(documentPDF, new FileOutputStream(arquivoPDF));
            documentPDF.open();
            Paragraph paragrafoTeste = new Paragraph(fraseImprimir);
            paragrafoTeste.setAlignment(1);
            documentPDF.add(paragrafoTeste);
            paragrafoTeste = new Paragraph("");
            documentPDF.add(paragrafoTeste);

            documentPDF.close();
            Desktop.getDesktop().open(new File(arquivoPDF));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

    }

}
