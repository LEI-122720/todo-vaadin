package com.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.List;

public class PDFExporter {

    public static void exportTasksToPDF(List<String> tasks, String fileName) {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 14);
            contentStream.newLineAtOffset(50, 700);
            contentStream.showText("Lista de Tarefas:");
            contentStream.newLineAtOffset(0, -30);

            for (String task : tasks) {
                contentStream.showText("- " + task);
                contentStream.newLineAtOffset(0, -20);
            }

            contentStream.endText();
            contentStream.close();

            document.save(fileName);
            System.out.println("PDF criado com sucesso: " + fileName);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
