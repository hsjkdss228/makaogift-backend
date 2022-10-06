package kr.megaptera.makaogift.controllers;

import kr.megaptera.makaogift.dtos.OrderDto;
import kr.megaptera.makaogift.dtos.OrderResultDto;
import kr.megaptera.makaogift.models.Transaction;
import kr.megaptera.makaogift.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping("order")
  @ResponseStatus(HttpStatus.CREATED)
  public OrderResultDto order(
      @RequestBody OrderDto orderDto
  ) {
    System.out.println(orderDto.getProductId());
    System.out.println(orderDto.getPurchaseCount());
    System.out.println(orderDto.getPurchaseCost());
    System.out.println(orderDto.getRecipient());
    System.out.println(orderDto.getAddress());
    System.out.println(orderDto.getMessageToSend());

    Transaction transaction = orderService.createOrder(
        orderDto.getProductId(), orderDto.getPurchaseCount(), orderDto.getPurchaseCost(),
        orderDto.getRecipient(), orderDto.getAddress(), orderDto.getMessageToSend());

    return transaction.toResultDto();
  }
}
