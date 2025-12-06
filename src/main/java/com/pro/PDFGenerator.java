package com.pro;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;


import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;

public class PDFGenerator {

    public static void generateBookingPDF(Booking booking, HttpServletResponse response) {
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=Disneyland_Ticket.pdf");

            OutputStream out = response.getOutputStream();
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();

            // ✅ Try to load logo image from resources (with fallback)
            try {
                InputStream is = PDFGenerator.class.getResourceAsStream("/images/disney_logo.png");
                if (is != null) {
                    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                    byte[] data = new byte[1024];
                    int nRead;
                    while ((nRead = is.read(data, 0, data.length)) != -1) {
                        buffer.write(data, 0, nRead);
                    }
                    buffer.flush();
                    Image logo = Image.getInstance(buffer.toByteArray());
                    logo.scaleToFit(100, 100);
                    logo.setAlignment(Element.ALIGN_CENTER);
                    document.add(logo);
                } else {
                    System.err.println("⚠ Logo image not found. Skipping logo.");
                }
            } catch (Exception ex) {
                System.err.println("⚠ Error loading logo image: " + ex.getMessage());
            }

            // ✅ Fancy fonts
            Font titleFont = FontFactory.getFont(FontFactory.COURIER_BOLD, 22, Font.UNDERLINE, BaseColor.BLUE);
            Font fieldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 13, BaseColor.DARK_GRAY);
            Font valueFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 12, BaseColor.BLACK);

            // ✅ Title
            Paragraph title = new Paragraph("🎟 Disneyland Booking Confirmation", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingBefore(10);
            title.setSpacingAfter(15);
            document.add(title);

            // ✅ Decorative line
            LineSeparator separator = new LineSeparator();
            separator.setLineColor(BaseColor.LIGHT_GRAY);
            document.add(separator);

            // ✅ Booking details table
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(15);
            table.setSpacingAfter(10);

            addRow(table, "Booking ID", String.valueOf(booking.getId()), fieldFont, valueFont);
            addRow(table, "Name", booking.getName(), fieldFont, valueFont);
            addRow(table, "Email", booking.getEmail(), fieldFont, valueFont);
            addRow(table, "Ticket Type", capitalize(booking.getTicketType()), fieldFont, valueFont);
            addRow(table, "Quantity", String.valueOf(booking.getQuantity()), fieldFont, valueFont);
            addRow(table, "Total Price", "$" + booking.getTotalPrice(), fieldFont, valueFont);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            addRow(table, "Visit Date", sdf.format(booking.getVisitDate()), fieldFont, valueFont);
            addRow(table, "Order ID", booking.getRazorpayOrderId(), fieldFont, valueFont);
            addRow(table, "Payment Status", booking.getPaymentStatus(), fieldFont, valueFont);
            addRow(table, "Booking Time", sdf.format(booking.getCreatedAt()), fieldFont, valueFont);

            document.add(table);

            // ✅ Add QR Code for Order ID
            BarcodeQRCode qrCode = new BarcodeQRCode("Order ID: " + booking.getRazorpayOrderId(), 100, 100, null);
            Image qrImage = qrCode.getImage();
            qrImage.setAlignment(Element.ALIGN_CENTER);
            qrImage.scaleToFit(100, 100);
            document.add(qrImage);

            Paragraph qrLabel = new Paragraph("Scan to verify Order ID", FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10, BaseColor.GRAY));
            qrLabel.setAlignment(Element.ALIGN_CENTER);
            qrLabel.setSpacingAfter(20);
            document.add(qrLabel);

            // ✅ Signature/Footer
            Paragraph footer = new Paragraph("Thank you for booking with Disneyland!\nHave a magical day ✨", valueFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(30);
            document.add(footer);

            document.close();
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addRow(PdfPTable table, String field, String value, Font fieldFont, Font valueFont) {
        PdfPCell cell1 = new PdfPCell(new Phrase(field, fieldFont));
        PdfPCell cell2 = new PdfPCell(new Phrase(value, valueFont));
        cell1.setPadding(8);
        cell2.setPadding(8);
        cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell1);
        table.addCell(cell2);
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}
