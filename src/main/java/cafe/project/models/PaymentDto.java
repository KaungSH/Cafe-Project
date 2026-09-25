package cafe.project.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PaymentDto {

    private String payment_id;
    private String order_id;
    private LocalDateTime paid_time;
    private String method_id;
    private String note;
    private boolean isedited;
    private boolean isdeleted;
    private LocalDate date;
    private String filepath;
    private String employee_id;

    public PaymentDto() {
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public LocalDateTime getPaid_time() {
        return paid_time;
    }

    public void setPaid_time(LocalDateTime paid_time) {
        this.paid_time = paid_time;
    }

    public String getMethod_id() {
        return method_id;
    }

    public void setMethod_id(String method_id) {
        this.method_id = method_id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isIsedited() {
        return isedited;
    }

    public void setIsedited(boolean isedited) {
        this.isedited = isedited;
    }

    public boolean isIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }
}