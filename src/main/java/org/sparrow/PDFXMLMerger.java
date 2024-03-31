package org.sparrow;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfFileSpecification;
import com.itextpdf.text.pdf.PdfReader;

public class PDFXMLMerger{

    public static void main(String[] args) {
        System.out.println("Let's Start");
        if(args.length<4){
            System.out.println("Please insert the valid argument as below:-> ");
            System.out.println("1. PDF PATH");
            System.out.println("2. XML PATH");
            System.out.println("3. OUTPUT PDF FILE PATH");
            System.out.println("4. XML FILE NAME");
            return;
        }
       // String pdfFilePath = "src/main/resources/SP1_Inv.pdf";
        String pdfFilePath = args[0];
       // String xmlFilePath = "src/main/resources/EN.xml";
        String xmlFilePath = args[1];
       // String outputFilePath = "src/main/resources/merged2.pdf";
        String outputFilePath = args[2];
        String xmlFileName=args[3];

        System.out.println("1. PDF PATH"+ pdfFilePath);
        System.out.println("2. XML PATH"+ xmlFilePath);
        System.out.println("3. OUTPUT PDF FILE PATH"+ outputFilePath);
        System.out.println("4. XML FILE NAME"+ xmlFileName);
        try{
            Thread.sleep(30000);
            mergePDFWithXML(pdfFilePath, xmlFilePath, outputFilePath,xmlFileName);
        }
        catch (Exception e){
            System.out.println("Error while  merging file into pdf");
        }

    }



    public static void mergePDFWithXML(String pdfFilePath, String xmlFilePath, String outputFilePath, String xmlFileName) {
        System.out.println("Let's merge the PDF");
        Document document = new Document();
        FileInputStream xmlInputStream = null;
        try {
            PdfCopy copy = new PdfCopy(document, new FileOutputStream(outputFilePath));
            document.open();
            PdfReader reader = new PdfReader(pdfFilePath);
            copy.addDocument(reader);

            File xmlFile = new File(xmlFilePath);
            xmlInputStream = new FileInputStream(xmlFile);
            copy.addFileAttachment(xmlFile.getName(), readBytesFromFile(xmlInputStream), "src/resources/duplicate", xmlFileName);
            Rectangle rectangle= new Rectangle(0,0,39,39);
            copy.setPageSize(rectangle);
            document.close();

            reader.close();
            copy.close();
            System.out.println("PDF with XML attachment created successfully.");
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (xmlInputStream != null) {
                    xmlInputStream.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    private static byte[] readBytesFromFile(FileInputStream inputStream) throws IOException {
        byte[] fileData = new byte[inputStream.available()];
        inputStream.read(fileData);
        return fileData;
    }
}
