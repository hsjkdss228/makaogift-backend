package kr.megaptera.makaogift.services;

import kr.megaptera.makaogift.models.Transaction;
import kr.megaptera.makaogift.models.Product;
import kr.megaptera.makaogift.repositories.OrderRepository;
import kr.megaptera.makaogift.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
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
  void createOrder() {
    String maker = "Polo";
    String name = "Polo shirt";
    Long productId = 3L;
    Integer purchaseCount = 3;
    Long purchaseCost = 300000L;
    String recipient = "Teacher";
    String address = "Sejong Metropolitan";
    String messageToSend = "How are you?";

    Product product = new Product(
        productId, maker, name, 100000L,
        "Well made shirt");

    Transaction createdTransaction = new Transaction(
        1L, maker, name, purchaseCount, purchaseCost,
        recipient, address, messageToSend,
        LocalDateTime.of(2022, 10, 7, 11, 3, 14, 0));

    given(productRepository.findById(any(Long.class))).willReturn(Optional.of(product));
    given(orderRepository.save(any(Transaction.class))).willReturn(createdTransaction);

    Transaction transaction = orderService.createOrder(
        productId, purchaseCount, purchaseCost, recipient, address, messageToSend);

    assertThat(createdTransaction).isNotNull();

    verify(productRepository).findById(3L);
    verify(orderRepository).save(any(Transaction.class));
  }
}
