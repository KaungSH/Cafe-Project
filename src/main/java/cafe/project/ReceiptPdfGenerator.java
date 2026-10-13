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

    // 1. Controller byte[] method
    public byte[] generateReceiptPdfBytes(String orderId, 
                                          Map<String, Object> orderData, 
                                          List<Map<String, Object>> items) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        writeReceiptToStream(baos, orderId, orderData, items);
        return baos.toByteArray();
    }

    // 2. File method 
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
        
        // 1. Header, Order Details, Totals, Footers နှင့် Spacing အားလုံးအတွက် Base Height (190f ~ 200f ပေးရန် လိုအပ်ပါသည်)
        float baseHeight = 190f; 

        // Discount ရှိပါက အမြင့်ထပ်တိုးရန်
        if (orderData != null && (orderData.get("discount_amount") != null || orderData.get("discount") != null)) {
            baseHeight += 14f;
        }
        // Note ရှိပါက အမြင့်ထပ်တိုးရန်
        if (orderData != null && orderData.get("payment_note") != null 
                && !orderData.get("payment_note").toString().trim().isEmpty()) {
            baseHeight += 12f;
        }

        float itemsHeight = 0f;
        if (items != null) {
            for (Map<String, Object> item : items) {
                String pName = item.get("product_name") != null ? item.get("product_name").toString() : "";
                String sName = (item.get("size_name") != null && !item.get("size_name").toString().trim().isEmpty()) 
                               ? item.get("size_name").toString() : "";
                String fullName = sName.isEmpty() ? pName : pName + " (" + sName + ")";

                if (fullName.length() > 20) {
                    itemsHeight += 26f;
                } else {
                    itemsHeight += 16f;
                }

                if (item.get("remark") != null && !item.get("remark").toString().trim().isEmpty()) {
                    itemsHeight += 12f;
                }
            }
        }

        // စာမျက်နှာ အမြင့်တွက်ချက်ခြင်း (Overflow မဖြစ်စေရန် padding 15f ထည့်ပေးထားသည်)
        float totalHeight = Math.max(220f, baseHeight + itemsHeight + 15f);

        // 80mm Thermal Printer Width (226 pt) နှင့် Dynamic Height
        Rectangle receiptSize = new Rectangle(226, totalHeight);
        
        Document document = new Document(receiptSize, 10, 10, 10, 10);
        PdfWriter.getInstance(document, outputStream);
        document.open();
        
        
        // Fonts 
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
        Font tokenFont = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD);
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

        // Cashier Info
        String empId = (orderData != null && orderData.get("employee_id") != null) 
                       ? orderData.get("employee_id").toString().trim() : "";
        String empName = (orderData != null && orderData.get("employee_name") != null) 
                         ? orderData.get("employee_name").toString().trim() : "";

        String cashierDisplay = "-";
        if (!empId.isEmpty() && !empName.isEmpty()) {
            cashierDisplay = empId + " (" + empName + ")";
        } else if (!empName.isEmpty()) {
            cashierDisplay = empName;
        } else if (!empId.isEmpty()) {
            cashierDisplay = empId;
        }

        // Order Time
        String orderTime = "-";
        if (orderData != null) {
            if (orderData.get("created_time") != null) {
                orderTime = orderData.get("created_time").toString();
            } else if (orderData.get("received_time") != null) {
                orderTime = orderData.get("received_time").toString();
            }
        }

        document.add(new Paragraph("Order ID : " + orderId, boldFont));
        document.add(new Paragraph("Cashier  : " + cashierDisplay, boldFont));
        document.add(new Paragraph("Date/Time: " + orderTime, boldFont));
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

        // =====================================================
        // Subtotal, Discount & Total Calculation
        // =====================================================
        BigDecimal subtotal = calculatedTotal;
        if (orderData != null && orderData.get("subtotal") != null) {
            try {
                subtotal = new BigDecimal(orderData.get("subtotal").toString());
            } catch (Exception ignored) {}
        }

        // discount_amount သို့မဟုတ် discount key နှစ်ခုလုံးကို စစ်ပေးထားပါသည်
        BigDecimal discountAmount = BigDecimal.ZERO;
        if (orderData != null) {
            if (orderData.get("discount_amount") != null) {
                try {
                    discountAmount = new BigDecimal(orderData.get("discount_amount").toString());
                } catch (Exception ignored) {}
            } else if (orderData.get("discount") != null) {
                try {
                    discountAmount = new BigDecimal(orderData.get("discount").toString());
                } catch (Exception ignored) {}
            }
        }

        BigDecimal finalTotal = subtotal.subtract(discountAmount);
        if (finalTotal.compareTo(BigDecimal.ZERO) < 0) {
            finalTotal = BigDecimal.ZERO;
        }

        // Subtotal
        Paragraph subtotalPara = new Paragraph(
                "Subtotal : " + currencyFormat.format(subtotal) + " MMK",
                normalFont
        );
        subtotalPara.setAlignment(Element.ALIGN_RIGHT);
        document.add(subtotalPara);

     // Discount : 1,000 MMK ပြသခြင်း
        if (discountAmount.compareTo(BigDecimal.ZERO) > 0) {
            Paragraph discountPara = new Paragraph(
                    "Discount : " + currencyFormat.format(discountAmount) + " MMK",
                    normalFont
            );
            discountPara.setAlignment(Element.ALIGN_RIGHT);
            document.add(discountPara);
        }

        // Divider
        document.add(new Chunk(separator));

        // Final Total
        Paragraph totalPara = new Paragraph(
                "TOTAL : " + currencyFormat.format(finalTotal) + " MMK",
                boldFont
        );
        totalPara.setAlignment(Element.ALIGN_RIGHT);
        document.add(totalPara);

        // Payment Method
        String payMethod = (orderData != null && orderData.get("payment_method") != null)
                ? orderData.get("payment_method").toString()
                : "N/A";

        Paragraph payPara = new Paragraph(
                "Paymethod: " + payMethod,
                normalFont
        );
        payPara.setAlignment(Element.ALIGN_RIGHT);
        document.add(payPara);

        // Payment Note
        if (orderData != null && orderData.get("payment_note") != null 
                && !orderData.get("payment_note").toString().trim().isEmpty()) {
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