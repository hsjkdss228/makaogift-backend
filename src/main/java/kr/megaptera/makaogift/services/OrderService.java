package kr.megaptera.makaogift.services;

import kr.megaptera.makaogift.exceptions.AccountNotFound;
import kr.megaptera.makaogift.exceptions.OrderFailed;
import kr.megaptera.makaogift.exceptions.ProductNotFound;
import kr.megaptera.makaogift.exceptions.TransactionNotFound;
import kr.megaptera.makaogift.models.Account;
import kr.megaptera.makaogift.models.Transaction;
import kr.megaptera.makaogift.models.Product;
import kr.megaptera.makaogift.repositories.AccountRepository;
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
  private final AccountRepository accountRepository;
  private final OrderRepository orderRepository;
  private final ProductRepository productRepository;

  public OrderService(AccountRepository accountRepository,
                      OrderRepository orderRepository,
                      ProductRepository productRepository) {
    this.accountRepository = accountRepository;
    this.orderRepository = orderRepository;
    this.productRepository = productRepository;
  }

  public Transaction orderDetail(Long transactionId) {
    return orderRepository.findById(transactionId)
        .orElseThrow(TransactionNotFound::new);
  }

  public Page<Transaction> findOrdersByIdentification(String identification,
                                                      int page, int pageSize) {
    Account account = accountRepository.findByIdentification(identification)
        .orElseThrow(AccountNotFound::new);

    String sender = account.name();

    Pageable pageable = PageRequest.of(page - 1, pageSize);
    return orderRepository.findAllBySender(sender, pageable);
  }

  public Transaction createOrder(String identification,
                                 Long productId, Integer purchaseCount, Long purchaseCost,
                                 String receiver, String address, String messageToSend) {
    Account account = accountRepository.findByIdentification(identification)
        .orElseThrow(AccountNotFound::new);

    if (account.amount() < purchaseCost) {
      throw new OrderFailed("잔액이 부족하여 선물하기가 불가합니다");
    }

    Product found = productRepository.findById(productId)
        .orElseThrow(ProductNotFound::new);

    account.reduceAmount(purchaseCost);

    String sender = account.name();
    String maker = found.maker();
    String name = found.name();
    String imageUrl = found.imageUrl();

    Transaction transaction = new Transaction(
        sender, maker, name, purchaseCount, purchaseCost,
        receiver, address, messageToSend, imageUrl);

    return orderRepository.save(transaction);
  }
}
