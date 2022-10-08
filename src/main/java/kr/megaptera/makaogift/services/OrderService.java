package kr.megaptera.makaogift.services;

import kr.megaptera.makaogift.exceptions.ProductNotFound;
import kr.megaptera.makaogift.exceptions.TransactionNotFound;
import kr.megaptera.makaogift.models.Transaction;
import kr.megaptera.makaogift.models.Product;
import kr.megaptera.makaogift.repositories.OrderRepository;
import kr.megaptera.makaogift.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

  public Transaction orderDetail(Long transactionId) {
    return orderRepository.findById(transactionId)
        .orElseThrow(TransactionNotFound::new);
  }

  public Page<Transaction> findByPage(int page, int pageSize) {
    Pageable pageable = PageRequest.of(page - 1, pageSize);
    return orderRepository.findAll(pageable);
  }

  public Transaction createOrder(Long productId, Integer purchaseCount, Long purchaseCost,
                                 String recipient, String address, String messageToSend) {
    Product found = productRepository.findById(productId)
        .orElseThrow(ProductNotFound::new);

    String maker = found.maker();
    String name = found.name();

    Transaction transaction = new Transaction(
        maker, name, purchaseCount, purchaseCost,
        recipient, address, messageToSend);

    return orderRepository.save(transaction);
  }
}
