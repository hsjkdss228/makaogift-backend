package kr.megaptera.makaogift.services;

import kr.megaptera.makaogift.exceptions.OrderFailed;
import kr.megaptera.makaogift.models.Account;
import kr.megaptera.makaogift.models.Product;
import kr.megaptera.makaogift.models.Transaction;
import kr.megaptera.makaogift.repositories.AccountRepository;
import kr.megaptera.makaogift.repositories.OrderRepository;
import kr.megaptera.makaogift.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class OrderServiceTest {
  private OrderService orderService;
  private AccountRepository accountRepository;
  private OrderRepository orderRepository;
  private ProductRepository productRepository;

  @BeforeEach
  void setUp() {
    accountRepository = mock(AccountRepository.class);
    productRepository = mock(ProductRepository.class);
    orderRepository = mock(OrderRepository.class);
    orderService = new OrderService(
        accountRepository, orderRepository, productRepository);
  }

  @Test
  void orderDetail() {
    Transaction transaction = new Transaction(
        1L, "강재형", "Wilson", "글러브", 1, 350000L,
        "이동훈", "올림픽공원 선수촌아파트", "보급반 1티어 이동훈!!!",
        LocalDateTime.of(2022, 10, 8, 10, 43, 0, 0));
    given(orderRepository.findById(any()))
        .willReturn(Optional.of(transaction));

    Transaction found = orderService.orderDetail(1L);

    assertThat(found).isNotNull();

    verify(orderRepository).findById(any(Long.class));
  }

  @Test
  void findOrdersByIdentification() {
    String userName = "황인우";
    String identification = "dhkddlsgn228";
    Account account = new Account(1L, userName, identification, 0L);
    given(accountRepository.findByIdentification(identification))
        .willReturn(Optional.of(account));

    List<Transaction> transactions = List.of(
        new Transaction(1L, userName, "star", "축구공", 3, 45000L,
            "축구꿈나무", "동네축구단", "축구 꿈나무들에게 희망을"),
        new Transaction(2L, userName, "Skyline", "야구공", 10, 30000L,
            "야구꿈나무", "동네야구단", "야구 꿈나무들에게 희망을"),
        new Transaction(3L, userName, "star", "배구공", 2, 60000L,
            "배구하는친구들", "동네배구장", "배구 꿈나무들에게 희망을"),
        new Transaction(4L, userName, "star", "농구공", 3, 54000L,
            "농구꿈나무", "동네농구장", "농구 꿈나무들에게 희망을"));
    int page = 1;
    int pageSize = 2;
    Pageable pageable = PageRequest.of(page - 1, pageSize);

    Page<Transaction> pageableTransactions
        = new PageImpl<>(transactions, pageable, transactions.size());

    given(orderRepository.findAllBySender(eq(userName), any(Pageable.class)))
        .willReturn(pageableTransactions);

    Page<Transaction> founds
        = orderService.findOrdersByIdentification(identification, page, pageSize);

    assertThat(founds).hasSize(transactions.size());

    verify(accountRepository).findByIdentification(identification);
    verify(orderRepository).findAllBySender(eq(userName), any(Pageable.class));
  }

  @Test
  void createOrder() {
    Long accountId = 5L;
    String userName = "Inwoo";
    String identification = "hsjkdss228";
    Long userAmount = 1000000L;
    Account account
        = spy(new Account(accountId, userName, identification, userAmount));

    given(accountRepository.findByIdentification(identification))
        .willReturn(Optional.of(account));

    Long productId = 3L;
    String productMaker = "Polo";
    String productName = "Polo shirt";
    Long productPrice = 100000L;
    Product product = new Product(
        productId, productMaker, productName, productPrice,
        "Well made shirt");

    given(productRepository.findById(any(Long.class)))
        .willReturn(Optional.of(product));

    Long transactionId = 1L;
    String sender = userName;
    Integer purchaseCount = 3;
    Long purchaseCost = productPrice * purchaseCount;
    String receiver = "Park Ki Hyeon";
    String address = "Sejong Metropolitan";
    String messageToSend = "The Greatest Mathematics Teacher";
    Transaction transaction = new Transaction(
        transactionId, sender, productMaker, productName, purchaseCount, purchaseCost,
        receiver, address, messageToSend,
        LocalDateTime.of(2022, 10, 7, 11, 3, 14, 0));

    given(orderRepository.save(any(Transaction.class)))
        .willReturn(transaction);

    Transaction createdTransaction
        = orderService.createOrder(
        identification, productId, purchaseCount, purchaseCost,
        receiver, address, messageToSend);

    assertThat(createdTransaction).isNotNull();
    assertThat(createdTransaction.id()).isEqualTo(1L);

    verify(accountRepository).findByIdentification(identification);
    verify(productRepository).findById(3L);
    verify(account).reduceAmount(purchaseCost);
    verify(orderRepository).save(any(Transaction.class));
  }

  @Test
  void createOrderWithAccountWithInsufficientAmount() {Long accountId = 5L;
    String userName = "Inwoo";
    String identification = "hsjkdss228";
    Long userAmount = 1000000L;
    Account account
        = spy(new Account(accountId, userName, identification, userAmount));

    given(accountRepository.findByIdentification(identification))
        .willReturn(Optional.of(account));

    assertThrows(OrderFailed.class, () -> {
      orderService.createOrder(
          identification, 1L, 1, 90000000L,
          "Anonymous receiver", "Anonymous address", "Anonymous message");
    });
  }
}
