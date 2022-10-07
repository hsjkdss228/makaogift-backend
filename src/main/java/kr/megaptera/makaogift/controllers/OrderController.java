package kr.megaptera.makaogift.controllers;

import kr.megaptera.makaogift.dtos.OrderDto;
import kr.megaptera.makaogift.dtos.OrderResultDto;
import kr.megaptera.makaogift.dtos.TransactionDto;
import kr.megaptera.makaogift.dtos.TransactionsDto;
import kr.megaptera.makaogift.models.Transaction;
import kr.megaptera.makaogift.services.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {
  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping("orders")
  public TransactionsDto orders(
      @RequestParam Integer page
  ) {
    int pageSize = 8;

    Page<Transaction> transactions = orderService.findByPage(page, pageSize);

    Long totalTransactionsSize = transactions.getTotalElements();

    List<TransactionDto> transactionDtos = transactions.stream()
        .map(Transaction::toTransactionDto)
        .toList();

    return new TransactionsDto(transactionDtos, pageSize, totalTransactionsSize);
  }

  @PostMapping("order")
  @ResponseStatus(HttpStatus.CREATED)
  public OrderResultDto order(
      @RequestBody OrderDto orderDto
  ) {
    Transaction transaction = orderService.createOrder(
        orderDto.getProductId(), orderDto.getPurchaseCount(), orderDto.getPurchaseCost(),
        orderDto.getRecipient(), orderDto.getAddress(), orderDto.getMessageToSend());

    return transaction.toOrderResultDto();
  }
}
