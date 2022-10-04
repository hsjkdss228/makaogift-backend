package kr.megaptera.makaogift.repositories;

import kr.megaptera.makaogift.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
  Optional<Product> findById(Long Id);
}
