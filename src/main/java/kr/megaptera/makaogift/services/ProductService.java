package kr.megaptera.makaogift.services;

import kr.megaptera.makaogift.exceptions.ProductNotFound;
import kr.megaptera.makaogift.models.Product;
import kr.megaptera.makaogift.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {
  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Product product(Long productId) {
    return productRepository.findById(productId)
        .orElseThrow(ProductNotFound::new);
  }

  public List<Product> findAll() {
    return productRepository.findAll();
  }
}
