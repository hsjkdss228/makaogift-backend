package kr.megaptera.makaogift.services;

import kr.megaptera.makaogift.exceptions.ProductNotFound;
import kr.megaptera.makaogift.models.Transaction;
import kr.megaptera.makaogift.models.Product;
import kr.megaptera.makaogift.repositories.OrderRepository;
import kr.megaptera.makaogift.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {
  private final ProductRepository productRepository;
  private final OrderRepository orderRepository;

  public OrderService(ProductRepository productRepository,
                      OrderRepository orderRepository) {
    this.productRepository = productRepository;
    this.orderRepository = orderRepository;
  }

  public Transaction createOrder(Long productId, Integer purchaseCount, Long purchaseCost,
                                 String recipient, String address, String messageToSend) {
    Product found = productRepository.findById(productId)
        .orElseThrow(ProductNotFound::new);

    String maker = found.maker();
    String name = found.name();

    Transaction transaction = new Transaction(maker, name, purchaseCount, purchaseCost,
        recipient, address, messageToSend);

    return orderRepository.save(transaction);
  }
}
