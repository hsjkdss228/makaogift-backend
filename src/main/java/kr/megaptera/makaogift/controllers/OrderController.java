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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// TODO: 모든 컨트롤러, 서비스에 대해 예외처리에 대한 테스트 코드와
//  예외처리 로직을 작성해줄 수 있도록 하자.

@RestController
public class OrderController {
  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping("orders")
  public TransactionsDto orders(
      @RequestAttribute("identification") String identification,
      @RequestParam Integer page
  ) {
    int pageSize = 8;

    Page<Transaction> transactions
        = orderService.findOrdersByIdentification(identification, page, pageSize);

    Long totalTransactionsSize = transactions.getTotalElements();

    List<TransactionDto> transactionDtos = transactions.stream()
        .map(Transaction::toTransactionDto)
        .toList();

    return new TransactionsDto(transactionDtos, pageSize, totalTransactionsSize);
  }

  @GetMapping("orders/{id}")
  public TransactionDto orderDetail(
      @RequestAttribute("identification") String identification,
      @PathVariable("id") Long transactionId
  ) {
    Transaction transaction = orderService.orderDetail(transactionId);

    return transaction.toTransactionDto();
  }

  @PostMapping("order")
  @ResponseStatus(HttpStatus.CREATED)
  public OrderResultDto order(
      @RequestAttribute("identification") String identification,
      @RequestBody OrderDto orderDto
  ) {
    Transaction transaction = orderService.createOrder(
        identification,
        orderDto.getProductId(), orderDto.getPurchaseCount(), orderDto.getPurchaseCost(),
        orderDto.getRecipient(), orderDto.getAddress(), orderDto.getMessageToSend());

    return transaction.toOrderResultDto();
  }
}
