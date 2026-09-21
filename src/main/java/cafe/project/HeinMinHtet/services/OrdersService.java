package cafe.project.HeinMinHtet.services;



import java.util.List;

import org.springframework.stereotype.Service;


import cafe.project.HeinMinHtet.repositories.OrdersRepository;
import cafe.project.HeinMinHtet.repositories.entities.Orders;
import cafe.project.HeinMinHtet.repositories.mappers.OrdersMapper;

@Service
public class OrdersService {

  private final OrdersRepository ordersRepository;

  public OrdersService(OrdersRepository ordersRepository) {
    this.ordersRepository = ordersRepository;
  }

  public List<Orders> getAllOrders() {
    List<Orders> entities = ordersRepository.findAll();
    return OrdersMapper.toDTOList(entities);
  }

  public Orders getOrderById(String order_id) {
    Orders entity = ordersRepository.findById(order_id);
    return OrdersMapper.toDTO(entity);
  }
  

 public boolean saveOrder(Orders order) {
   order.setCreated_time(java.time.LocalDateTime.now());
   order.setIsedited(false);
   order.setIsdeleted(false);

   return ordersRepository.save(order) > 0;
 }

  public boolean updateOrder(String id, Orders dto) {
    Orders entity = new Orders();
    entity.setEmployee_id(dto.getEmployee_id());
    entity.setCustomer_id(dto.getCustomer_id());
    entity.setBranch_id(dto.getBranch_id());
    entity.setReceived_time(dto.getReceived_time());
    entity.setOrder_type_id(dto.getOrder_type_id());
    entity.setTotal_amount(dto.getTotal_amount());

    return ordersRepository.edit(id, entity) > 0;
  }

  public boolean deleteOrder(String order_id) {
    return ordersRepository.delete(order_id) > 0;
  }
}