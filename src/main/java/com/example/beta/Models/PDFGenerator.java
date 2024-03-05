package com.example.beta.Models;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.util.Map;
public class PDFGenerator {
    private static final String API_KEY = "mohamediheb.bendhaou@esprit.tn_7hkdQqfd9I9w63vqTm4uoBRBAQJRKRT5QorL5iAzs1lbl35lBYV8E8fqpY7DFS0F";
    private static final String PDF_CO_API_URL = "https://api.pdf.co/v1/pdf/convert/from/html";
    public static void createPDF(int age, double weight, double height, String gender, String activityLevel,
                                 String calorie, String fats, String carbs, Map<String, Double> vitamins) {
        try {
            // Create a new PDF document
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            // Create a new content stream for adding content to the PDF
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(50, 700);

            // Write content to the PDF
            contentStream.showText("User Information:");
            contentStream.newLine();
            contentStream.showText("Age: " + age + " years");
            contentStream.newLine();
            contentStream.showText("Weight: " + weight + " kg");
            contentStream.newLine();
            contentStream.showText("Height: " + height + " cm");
            contentStream.newLine();
            contentStream.showText("Gender: " + gender);
            contentStream.newLine();
            contentStream.showText("Activity Level: " + activityLevel);
            contentStream.newLine();
            contentStream.showText("Calorie Intake: " + calorie + " kcal");
            contentStream.newLine();
            contentStream.showText("Fats Intake: " + fats + " grams");
            contentStream.newLine();
            contentStream.showText("Carbs Intake: " + carbs + " grams");
            contentStream.newLine();
            contentStream.newLine();
            contentStream.showText("Vitamin Recommendations:");
            contentStream.newLine();
            for (Map.Entry<String, Double> entry : vitamins.entrySet()) {
                contentStream.showText(entry.getKey() + ": " + entry.getValue());
                contentStream.newLine();
            }

            contentStream.endText();
            contentStream.close();

            // Save the PDF to a file
            document.save("user_bilan.pdf");
            document.close();
            System.out.println("PDF created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
