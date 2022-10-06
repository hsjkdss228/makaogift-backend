package kr.megaptera.makaogift.repositories;

import kr.megaptera.makaogift.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Transaction, Long> {

}
