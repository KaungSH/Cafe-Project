package cafe.project.KaungSattHein.services;


import org.springframework.stereotype.Service;

import cafe.project.KaungSattHein.models.DiscountEntryModel;
import cafe.project.KaungSattHein.models.DiscountListModel;
import cafe.project.KaungSattHein.repositories.DiscountRepository;
import cafe.project.KaungSattHein.repositories.entities.Discount;

import java.util.List;
import java.util.UUID;

@Service
public class DiscountService {

 
    private final DiscountRepository discountRepository;
    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public List<DiscountListModel> getAllDiscounts() {
        return discountRepository.findAllForList();
    }

    public DiscountEntryModel getDiscountById(String id) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Discount not found with ID: " + id));
        
        DiscountEntryModel dto = new DiscountEntryModel();
        dto.setDiscount_id(discount.getDiscount_id());
        dto.setEmployee_id(discount.getEmployee_id());
        dto.setName(discount.getName());
        dto.setDescription(discount.getDescription());
        dto.setDiscount_value(discount.getDiscount_value());
        dto.setStartdate(discount.getStartdate());
        dto.setEnddate(discount.getEnddate());
        dto.setIs_active(discount.getIs_active());
        dto.setPromo_type_id(discount.getPromo_type_id());
        dto.setAudience_type_id(discount.getAudience_type_id());
        return dto;
    }

    public void createDiscount(DiscountEntryModel dto) {
        Discount discount = new Discount();
        discount.setDiscount_id(UUID.randomUUID().toString());
        discount.setEmployee_id(dto.getEmployee_id());
        discount.setName(dto.getName());
        discount.setDescription(dto.getDescription());
        discount.setDiscount_value(dto.getDiscount_value());
        discount.setStartdate(dto.getStartdate());
        discount.setEnddate(dto.getEnddate());
        discount.setIs_active( dto.isIs_active());
        discount.setPromo_type_id(dto.getPromo_type_id());
        discount.setAudience_type_id(dto.getAudience_type_id());

        discountRepository.save(discount);
    }

    public void updateDiscount(DiscountEntryModel dto) {
        Discount discount = discountRepository.findById(dto.getDiscount_id())
                .orElseThrow(() -> new RuntimeException("Discount not found with ID: " + dto.getDiscount_id()));

        discount.setEmployee_id(dto.getEmployee_id());
        discount.setName(dto.getName());
        discount.setDescription(dto.getDescription());
        discount.setDiscount_value(dto.getDiscount_value());
        discount.setStartdate(dto.getStartdate());
        discount.setEnddate(dto.getEnddate());
        discount.setIs_active(dto.isIs_active());
        discount.setPromo_type_id(dto.getPromo_type_id());
        discount.setAudience_type_id(dto.getAudience_type_id());

        discountRepository.update(discount);
    }

    public void deleteDiscount(String id) {
        discountRepository.softDelete(id);
    }
}


