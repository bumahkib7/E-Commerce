package org.codeai.ecommerce.controllers;

import lombok.extern.slf4j.Slf4j;
import org.codeai.ecommerce.models.OrderItems;
import org.codeai.ecommerce.requests.OrderItemRequest;
import org.codeai.ecommerce.service.OrderItemSerivces;
import org.codeai.ecommerce.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/order")
@Slf4j(topic = "OrderController")
public class OrderController {

  private final OrderItemSerivces orderItemSerivces;
  private final OrderService orderService;

  private final Logger logger = Logger.getLogger(OrderController.class.getName());

  public OrderController(OrderItemSerivces orderItemSerivces, OrderService orderService) {
    this.orderItemSerivces = orderItemSerivces;
    this.orderService = orderService;
  }


  @PostMapping("/orderItems")
  public ResponseEntity<?> createOrderItems(@RequestBody OrderItemRequest orderItemsRequest) {
    try {
      OrderItems orderItems = orderItemSerivces.createOrderItems(orderItemsRequest);
      return ResponseEntity.ok(orderItems);
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @PostMapping("/orderItems/update")
  public ResponseEntity<?> updateOrderItems(@RequestBody OrderItemRequest orderItemsRequest) {
    try {
      OrderItems orderItems = orderItemSerivces.updateOrderItems(orderItemsRequest);
      return ResponseEntity.ok(orderItems);
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @PostMapping("/orderItems/delete")
  public ResponseEntity<?> deleteOrderItems(@RequestBody Long id) {
    try {
      orderItemSerivces.deleteOrderItems(id);
      return ResponseEntity.ok("OrderItems deleted");
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @PostMapping("/orderItems/getAll")
  public ResponseEntity<?> getAllOrderItems() {
    try {
      Iterable<OrderItems> orderItems = orderItemSerivces.getAllOrderItems();
      return ResponseEntity.ok(orderItems);
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }
}
