package kr.megaptera.makaogift.services;

import kr.megaptera.makaogift.exceptions.ProductNotFound;
import kr.megaptera.makaogift.models.Product;
import kr.megaptera.makaogift.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  public Page<Product> findByPage(int page, int pageSize) {
    Pageable pageable = PageRequest.of(page - 1, pageSize);
    return productRepository.findAll(pageable);
  }
}
