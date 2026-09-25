package cafe.project.services;

import java.util.List;

import org.springframework.stereotype.Service;

import cafe.project.repositories.PaymentRepository;
import cafe.project.repositories.entities.Payment;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    // GET ALL
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    // GET DELETED
    public List<Payment> findDeletedAll() {
        return paymentRepository.findDeletedAll();
    }

    // GET BY ID
    public Payment findById(String id) {
        return paymentRepository.findById(id);
    }

    // SAVE
    public int save(Payment payment) {
        return paymentRepository.save(payment);
    }

    // UPDATE
    public int edit(String id, Payment payment) {
        return paymentRepository.edit(id, payment);
    }

    // DELETE
    public int delete(String id) {
        return paymentRepository.delete(id);
    }
}