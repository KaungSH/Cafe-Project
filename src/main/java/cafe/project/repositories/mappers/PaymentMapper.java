package cafe.project.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cafe.project.repositories.entities.Payment;

public class PaymentMapper implements RowMapper<Payment> {

    @Override
    public Payment mapRow(ResultSet rs, int rowNum) throws SQLException {

        Payment payment = new Payment();

        payment.setPayment_id(rs.getString("payment_id"));
        payment.setOrder_id(rs.getString("order_id"));

        if (rs.getTimestamp("paid_time") != null) {
            payment.setPaid_time(
                rs.getTimestamp("paid_time").toLocalDateTime()
            );
        }

        payment.setMethod_id(rs.getString("method_id"));
        payment.setNote(rs.getString("note"));
        payment.setIsedited(rs.getBoolean("isedited"));
        payment.setIsdeleted(rs.getBoolean("isdeleted"));

        if (rs.getDate("date") != null) {
            payment.setDate(
                rs.getDate("date").toLocalDate()
            );
        }

        payment.setFilepath(rs.getString("filepath"));
        payment.setEmployee_id(rs.getString("employee_id"));

        return payment;
    }
}