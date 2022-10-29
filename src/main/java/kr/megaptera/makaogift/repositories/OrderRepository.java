package kr.megaptera.makaogift.repositories;

import kr.megaptera.makaogift.models.Product;
import kr.megaptera.makaogift.models.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Transaction, Long> {
  Optional<Transaction> findById(Long Id);


  // TODO: 이름만 갖고 찾으려 하면 동명이인이 있을 수 있음
  //  이름과 함께 Account의 ID를 사용해서도 찾게 수정할 것!!!!
  Page<Transaction> findAllBySender(String sender, Pageable pageable);

  Transaction save(Transaction transaction);
}
