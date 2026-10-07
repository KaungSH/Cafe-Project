package cafe.project;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class ReceiptPdfGenerator {

    // 1. Controller မှ byte[] အဖြစ် တိုက်ရိုက်ယူရန် method
    public byte[] generateReceiptPdfBytes(String orderId, 
                                          Map<String, Object> orderData, 
                                          List<Map<String, Object>> items) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        writeReceiptToStream(baos, orderId, orderData, items);
        return baos.toByteArray();
    }

    // 2. File အဖြစ် သိမ်းဆည်းရန် method (ရှိပြီးသား)
    public void generateReceipt(String filePath, String orderId, 
                                Map<String, Object> orderData, 
                                List<Map<String, Object>> items) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            writeReceiptToStream(fos, orderId, orderData, items);
        }
    }

    // Common Receipt Drawing Method
    private void writeReceiptToStream(OutputStream outputStream, String orderId, 
                                      Map<String, Object> orderData, 
                                      List<Map<String, Object>> items) throws Exception {
        
        // 80mm Thermal Printer size (width: 226 point, height: 650 point)
        Rectangle receiptSize = new Rectangle(226, 650);
        
        Document document = new Document(receiptSize, 10, 10, 10, 10);
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // Fonts 
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Font tokenFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
        Font boldFont = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);
        Font normalFont = new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL);
        Font italicFont = new Font(Font.FontFamily.HELVETICA, 6.5f, Font.ITALIC);
        DecimalFormat currencyFormat = new DecimalFormat("#,##0");

        // Divider Line
        LineSeparator separator = new LineSeparator();
        separator.setLineColor(BaseColor.GRAY);
        separator.setLineWidth(0.5f);

        // Header (Branch Name)
        String branchName = "Cafe Luft";
        if (orderData != null && orderData.get("branch_name") != null) {
            String rawBranch = orderData.get("branch_name").toString();
            // "- Original" သို့မဟုတ် တခြား suffix ပါနေပါက ဖြုတ်ပစ်ရန်
            if (rawBranch.contains("-")) {
                branchName = rawBranch.split("-")[0].trim();
            } else {
                branchName = rawBranch;
            }
        }

        Paragraph branch = new Paragraph(branchName.toUpperCase(), titleFont);
        branch.setAlignment(Element.ALIGN_CENTER);
        document.add(branch);

        // Token Number
        if (orderData != null && orderData.get("token_number") != null) {
            Paragraph token = new Paragraph("TOKEN: #" + orderData.get("token_number"), tokenFont);
            token.setAlignment(Element.ALIGN_CENTER);
            document.add(token);
        }

        document.add(new Chunk(separator));

        // Order & Cashier Info
        String cashier = (orderData != null && orderData.get("employee_name") != null) 
                         ? orderData.get("employee_name").toString() 
                         : (orderData != null && orderData.get("employee_id") != null 
                             ? orderData.get("employee_id").toString() : "-");

        String orderTime = "-";
        if (orderData != null) {
            if (orderData.get("created_time") != null) {
                orderTime = orderData.get("created_time").toString();
            } else if (orderData.get("received_time") != null) {
                orderTime = orderData.get("received_time").toString();
            }
        }
        
        document.add(new Paragraph("Order ID : " + orderId, normalFont));
        document.add(new Paragraph("Cashier  : " + cashier, normalFont));
        document.add(new Paragraph("Date/Time: " + orderTime, normalFont));

        document.add(new Chunk(separator));

        // Items Table (4 Columns: Item, Qty, Price, Total)
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{4.5f, 1.2f, 2.0f, 2.3f});

        addTableCell(table, "Item", boldFont, Element.ALIGN_LEFT);
        addTableCell(table, "Qty", boldFont, Element.ALIGN_CENTER);
        addTableCell(table, "Price", boldFont, Element.ALIGN_RIGHT);
        addTableCell(table, "Total", boldFont, Element.ALIGN_RIGHT);

        BigDecimal calculatedTotal = BigDecimal.ZERO;
        if (items != null && !items.isEmpty()) {
            for (Map<String, Object> item : items) {
                String pName = item.get("product_name") != null ? item.get("product_name").toString() : "Item";
                String sName = item.get("size_name") != null ? " (" + item.get("size_name") + ")" : "";
                int qty = item.get("quantity") != null ? ((Number) item.get("quantity")).intValue() : 1;
                BigDecimal price = item.get("price") != null ? new BigDecimal(item.get("price").toString()) : BigDecimal.ZERO;
                BigDecimal lineTotal = price.multiply(BigDecimal.valueOf(qty));
                calculatedTotal = calculatedTotal.add(lineTotal);

                // Remark
                String remark = item.get("remark") != null ? item.get("remark").toString() : "";

                addTableCell(table, pName + sName, normalFont, Element.ALIGN_LEFT);
                addTableCell(table, String.valueOf(qty), normalFont, Element.ALIGN_CENTER);
                addTableCell(table, currencyFormat.format(price), normalFont, Element.ALIGN_RIGHT);
                addTableCell(table, currencyFormat.format(lineTotal), normalFont, Element.ALIGN_RIGHT);

                // Remark (italic)
                if (!remark.trim().isEmpty()) {
                    PdfPCell remarkCell = new PdfPCell(new Phrase(" * " + remark, italicFont));
                    remarkCell.setColspan(4);
                    remarkCell.setBorder(Rectangle.NO_BORDER);
                    remarkCell.setPaddingLeft(5);
                    remarkCell.setPaddingBottom(3);
                    table.addCell(remarkCell);
                }
            }
        }

        document.add(table);
        document.add(new Chunk(separator));

        // Total Amount & Payment Info
        Object totalObj = orderData != null ? orderData.get("total_amount") : null;
        BigDecimal finalTotal = totalObj != null ? new BigDecimal(totalObj.toString()) : calculatedTotal;
        
//        String payMethod = (orderData != null && orderData.get("payment_method") != null) 
//                           ? orderData.get("payment_method").toString() : "Cash";
        String payMethod = (orderData != null && orderData.get("payment_method") != null)
                ? orderData.get("payment_method").toString()
                : "N/A";

        Paragraph totalPara = new Paragraph("TOTAL : " + currencyFormat.format(finalTotal) + " MMK", boldFont);
        totalPara.setAlignment(Element.ALIGN_RIGHT);
        document.add(totalPara);

        Paragraph payPara = new Paragraph("Payment: " + payMethod, normalFont);
        payPara.setAlignment(Element.ALIGN_RIGHT);
        document.add(payPara);

        // Payment Note
        if (orderData != null && orderData.get("payment_note") != null && !orderData.get("payment_note").toString().trim().isEmpty()) {
            Paragraph notePara = new Paragraph("Note: " + orderData.get("payment_note").toString(), italicFont);
            notePara.setAlignment(Element.ALIGN_RIGHT);
            document.add(notePara);
        }

        document.add(new Chunk(separator));

        // Footer
        Paragraph footer = new Paragraph("THANK YOU! PLEASE COME AGAIN", boldFont);
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);

        document.close();
    }

    private void addTableCell(PdfPTable table, String text, Font font, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(alignment);
        cell.setPadding(2);
        table.addCell(cell);
    }
}