package kr.megaptera.makaogift.repositories;

import kr.megaptera.makaogift.models.Product;
import kr.megaptera.makaogift.models.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Transaction, Long> {
  Optional<Transaction> findById(Long Id);

  Page<Transaction> findAllBySender(String sender, Pageable pageable);

  Transaction save(Transaction transaction);
}
