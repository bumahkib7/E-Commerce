package org.codeai.ecommerce.service;

import org.codeai.ecommerce.models.OrderItems;
import org.codeai.ecommerce.repository.OrderItemRepository;
import org.codeai.ecommerce.requests.OrderItemRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.TreeMap;

@Service
public class OrderItemSerivces {

  private final OrderItemRepository orderItemsRepository;
  private final OrderService orderService;



  public OrderItemSerivces(OrderItemRepository orderItemsRepository, OrderService orderService) {
    this.orderItemsRepository = orderItemsRepository;
    this.orderService = orderService;
  }

  public OrderItems createOrderItems(OrderItemRequest orderItemsRequest) {
    OrderItems orderItems = new OrderItems();
    orderItems.setQuantity(orderItemsRequest.quantity());
    orderItems.setProductId(orderItemsRequest.productId());
    orderItems.setPrice(orderItemsRequest.price());
    return orderItemsRepository.save(orderItems);
  }

  public OrderItems updateOrderItems(OrderItemRequest orderItemsRequest) {
    OrderItems orderItems = orderItemsRepository.findById(orderItemsRequest.id()).orElseThrow();
    orderItems.setQuantity(orderItemsRequest.quantity());
    orderItems.setProductId(orderItemsRequest.productId());
    orderItems.setPrice(orderItemsRequest.price());
    return orderItemsRepository.save(orderItems);
  }
  public void deleteOrderItems(Long id) {
    orderItemsRepository.deleteById(id);
  }


  public Iterable<OrderItems> getAllOrderItems() {

    return orderItemsRepository.findAll(Pageable.ofSize(30)).getContent();
  }


  public OrderItems getOrderItems(Long id) {
    return orderItemsRepository.findById(id).orElseThrow();
  }

  public TreeMap<Long, OrderItems> getOrderItemsByOrderId(Long id) {
    TreeMap<Long, OrderItems> orderItems = new TreeMap<>();
    orderService.getOrder(id).getOrderItems().forEach(orderItem -> orderItems.put(orderItem.getId(), orderItem));
    return orderItems;
  }



}
