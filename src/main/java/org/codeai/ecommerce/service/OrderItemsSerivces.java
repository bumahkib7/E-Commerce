package org.codeai.ecommerce.service;

import org.codeai.ecommerce.models.OrderItems;
import org.codeai.ecommerce.repository.OrderItemRepository;
import org.codeai.ecommerce.requests.OrderItemRequest;
import org.springframework.stereotype.Service;

@Service
public class OrderItemsSerivces {

  private final OrderItemRepository orderItemsRepository;
  private final OrderService orderService;


  public OrderItemsSerivces(OrderItemRepository orderItemsRepository, OrderService orderService) {
    this.orderItemsRepository = orderItemsRepository;
    this.orderService = orderService;
  }

  public OrderItems orderItems(OrderItemRequest orderItemsRequest) {
    OrderItems orderItems = new OrderItems();
    orderItems.setQuantity(orderItemsRequest.quantity());
    orderItems.setProductId(orderItemsRequest.productId());
    orderItems.setOrderId(orderItemsRequest.orderId());
    orderItems.setPrice(orderItemsRequest.price());
    return orderItemsRepository.save(orderItems);
  }

  public OrderItems updateOrderItems(OrderItemRequest orderItemsRequest) {
    OrderItems orderItems = orderItemsRepository.findById(orderItemsRequest.id()).orElseThrow();
    orderItems.setQuantity(orderItemsRequest.quantity());
    orderItems.setProductId(orderItemsRequest.productId());
    orderItems.setOrderId(orderItemsRequest.orderId());
    orderItems.setPrice(orderItemsRequest.price());
    return orderItemsRepository.save(orderItems);
  }


  public void deleteOrderItems(Long id) {
    orderItemsRepository.deleteById(id);
  }


  public Iterable<OrderItems> getAllOrderItems() {

    return orderItemsRepository.findAll();
  }


  public OrderItems getOrderItems(Long id) {
    return orderItemsRepository.findById(id).orElseThrow();
  }



}
