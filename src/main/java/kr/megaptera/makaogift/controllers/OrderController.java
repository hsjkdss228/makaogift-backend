package kr.megaptera.makaogift.controllers;

import kr.megaptera.makaogift.dtos.OrderDto;
import kr.megaptera.makaogift.dtos.OrderErrorDto;
import kr.megaptera.makaogift.dtos.OrderResultDto;
import kr.megaptera.makaogift.dtos.TransactionDto;
import kr.megaptera.makaogift.dtos.TransactionsDto;
import kr.megaptera.makaogift.exceptions.OrderFailed;
import kr.megaptera.makaogift.models.Transaction;
import kr.megaptera.makaogift.services.OrderService;
import kr.megaptera.makaogift.validations.ValidationSequence;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// TODO: 모든 컨트롤러, 서비스에 대해 예외처리 테스트 코드와
//  예외처리 로직을 작성해줄 수 있도록 하자.

@RestController
public class OrderController {
  private static final Integer BLANK_RECEIVER = 3000;
  private static final Integer BLANK_ADDRESS = 3001;
  private static final Integer INVALID_RECEIVER = 3002;
  private static final Integer DEFAULT_ERROR = 3003;

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
      @Validated(value = {ValidationSequence.class})
      @RequestBody OrderDto orderDto,
      BindingResult bindingResult
  ) {
    if (bindingResult.hasErrors()) {
      List<String> errorMessages = bindingResult.getAllErrors().stream()
          .map(error -> error.getDefaultMessage())
          .toList();
      System.out.println(errorMessages);
      throw new OrderFailed(errorMessages);
    }

    Transaction transaction = orderService.createOrder(
        identification,
        orderDto.getProductId(),
        orderDto.getPurchaseCount(),
        orderDto.getPurchaseCost(),
        orderDto.getReceiver(),
        orderDto.getAddress(),
        orderDto.getMessageToSend());

    return transaction.toOrderResultDto();
  }

  @ExceptionHandler(OrderFailed.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public OrderErrorDto orderFailed(OrderFailed exception) {
    Map<Integer, String> errorCodesAndMessages
        = exception.errorMessages().stream()
        .collect(Collectors.toMap(
            errorMessage -> mapToErrorCode(errorMessage),
            errorMessage -> mapToErrorCode(errorMessage).equals(DEFAULT_ERROR)
                ? "알 수 없는 선물하기 실패 오류입니다."
                : errorMessage
        ));
    return new OrderErrorDto(errorCodesAndMessages);
  }

  public Integer mapToErrorCode(String errorMessage) {
    return switch (errorMessage) {
      case "성함을 입력해주세요" -> BLANK_RECEIVER;
      case "주소를 입력해주세요" -> BLANK_ADDRESS;
      case "3-7자까지 한글만 사용 가능합니다" -> INVALID_RECEIVER;
      default -> DEFAULT_ERROR;
    };
  }
}
