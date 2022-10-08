package kr.megaptera.makaogift.services;

import kr.megaptera.makaogift.models.Transaction;
import kr.megaptera.makaogift.models.Product;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class OrderServiceTest {
  private OrderService orderService;
  private ProductRepository productRepository;
  private OrderRepository orderRepository;

  @BeforeEach
  void setUp() {
    productRepository = mock(ProductRepository.class);
    orderRepository = mock(OrderRepository.class);
    orderService = new OrderService(productRepository, orderRepository);
  }

  @Test
  void orderDetail() {
    Transaction transaction = new Transaction(
        1L, "Wilson", "글러브", 1, 350000L,
        "이동훈", "올림픽공원 선수촌아파트", "잘 있나 ㅋㅋㅋ 언제 만나나..",
        LocalDateTime.of(2022, 10, 8, 10, 43, 0, 0));
    given(orderRepository.findById(any()))
        .willReturn(Optional.of(transaction));

    Transaction found = orderService.orderDetail(1L);

    assertThat(found).isNotNull();

    verify(orderRepository).findById(any(Long.class));
  }

  @Test
  void findByPage() {
    // TODO: 현재는 거래 내역 전체를 찾고 있으나,
    //  계정 개념이 추가될 경우 Transaction에 추가된
    //  sender를 이용해 찾는 방식으로 바뀌어야 함

    List<Transaction> transactions = List.of(
        new Transaction(1L, "star", "축구공", 3, 45000L,
            "박지성", "전북 현대 모터스", "축구 꿈나무들에게 희망을"),
        new Transaction(2L, "Skyline", "야구공", 10, 30000L,
            "이승엽", "대구 삼성 라이온즈", "야구 꿈나무들에게 희망을"),
        new Transaction(3L, "star", "배구공", 2, 60000L,
            "김연경", "인천 흥국생명 핑크스파이더스", "배구 꿈나무들에게 희망을"),
        new Transaction(4L, "star", "농구공", 3, 54000L,
            "마이클 조던", "시카고 불스", "농구 꿈나무들에게 희망을"));
    int page = 1;
    int pageSize = 2;
    Pageable pageable = PageRequest.of(page - 1, pageSize);

    Page<Transaction> pageableTransactions
        = new PageImpl<>(transactions, pageable, transactions.size());

    given(orderRepository.findAll(any(Pageable.class)))
        .willReturn(pageableTransactions);

    Page<Transaction> founds = orderService.findByPage(page, pageSize);

    assertThat(founds).hasSize(transactions.size());

    verify(orderRepository).findAll(any(Pageable.class));
  }

  @Test
  void createOrder() {
    Long productId = 3L;
    String maker = "Polo";
    String name = "Polo shirt";
    Product product = new Product(
        productId, maker, name, 100000L,
        "Well made shirt");
    given(productRepository.findById(any(Long.class))).willReturn(Optional.of(product));

    Long transactionId = 1L;
    Integer purchaseCount = 3;
    Long purchaseCost = 300000L;
    String recipient = "Teacher";
    String address = "Sejong Metropolitan";
    String messageToSend = "How are you?";
    Transaction transaction = new Transaction(
        transactionId, maker, name, purchaseCount, purchaseCost,
        recipient, address, messageToSend,
        LocalDateTime.of(2022, 10, 7, 11, 3, 14, 0));
    given(orderRepository.save(any(Transaction.class))).willReturn(transaction);

    Transaction createdTransaction = orderService.createOrder(
        productId, purchaseCount, purchaseCost, recipient, address, messageToSend);

    assertThat(createdTransaction).isNotNull();
    assertThat(createdTransaction.id()).isEqualTo(1L);

    verify(productRepository).findById(3L);
    verify(orderRepository).save(any(Transaction.class));
  }
}
