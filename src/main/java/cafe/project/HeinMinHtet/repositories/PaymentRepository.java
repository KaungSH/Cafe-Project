package cafe.project.HeinMinHtet.repositories;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cafe.project.HeinMinHtet.repositories.entities.Payment;
import cafe.project.HeinMinHtet.repositories.mappers.PaymentMapper;

@Repository
public class PaymentRepository {

    private final JdbcTemplate jdbcTemplate;

    public PaymentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // GET ALL
    public List<Payment> findAll() {

        String sql = "SELECT * FROM payments WHERE isdeleted = false";

        return jdbcTemplate.query(sql, new PaymentMapper());
    }

    // GET DELETED
    public List<Payment> findDeletedAll() {

        String sql = "SELECT * FROM payments WHERE isdeleted = true";

        return jdbcTemplate.query(sql, new PaymentMapper());
    }

    // GET BY ID
    public Payment findById(String id) {

        String sql = "SELECT * FROM payments WHERE payment_id = ?";

        return jdbcTemplate.queryForObject(
                sql,
                new PaymentMapper(),
                id
        );
    }

    // SAVE
    public int save(Payment payment) {

        String sql = "INSERT INTO payments "
                + "(payment_id, order_id, paid_time, method_id, note, "
                + "isedited, isdeleted, date, filepath, employee_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        return jdbcTemplate.update(
                sql,
                payment.getPayment_id(),
                payment.getOrder_id(),
                payment.getPaid_time(),
                payment.getMethod_id(),
                payment.getNote(),
                payment.isIsedited(),
                payment.isIsdeleted(),
                payment.getDate(),
                payment.getFilepath(),
                payment.getEmployee_id()
        );
    }

    // UPDATE
    public int edit(String id, Payment payment) {

        String sql = "UPDATE payments SET "
                + "order_id = ?, "
                + "paid_time = ?, "
                + "method_id = ?, "
                + "note = ?, "
                + "date = ?, "
                + "filepath = ?, "
                + "employee_id = ?, "
                + "isedited = 1 "
                + "WHERE payment_id = ?";

        return jdbcTemplate.update(
                sql,
                payment.getOrder_id(),
                payment.getPaid_time(),
                payment.getMethod_id(),
                payment.getNote(),
                payment.getDate(),
                payment.getFilepath(),
                payment.getEmployee_id(),
                id
        );
    }

    // SOFT DELETE
    public int delete(String id) {

        String sql =
                "UPDATE payments SET isdeleted = 1 WHERE payment_id = ?";

        return jdbcTemplate.update(sql, id);
    }
}