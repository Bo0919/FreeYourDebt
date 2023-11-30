package com.example.freeyourdebt;

import android.os.Environment;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;

public class PdfGenerator {
    public static final String FILE_NAME = "DebtListFile.pdf";
    public static void generatePdfFromText(String text){
        Document document = new Document();
        try{
            File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            String filePath = directory.getAbsolutePath()+ File.separator+FILE_NAME;
            File pdf = new File(filePath);
            if (pdf.exists()){
                pdf.delete();
            }
            PdfWriter.getInstance(document,new FileOutputStream(filePath));
            document.open();

            BaseFont baseFont = BaseFont.createFont(BaseFont.TIMES_ROMAN,BaseFont.CP1252,BaseFont.EMBEDDED);
            Font font = new Font(baseFont);
            font.setSize(12);
            font.setStyle(Font.NORMAL);
            font.setColor(BaseColor.BLACK);

            Paragraph paragraph = new Paragraph(text,font);
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);
            document.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
