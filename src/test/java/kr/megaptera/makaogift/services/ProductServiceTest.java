package kr.megaptera.makaogift.services;

import kr.megaptera.makaogift.models.Product;
import kr.megaptera.makaogift.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ProductServiceTest {
  private ProductService productService;
  private ProductRepository productRepository;

  @BeforeEach
  void setUp() {
    productRepository = mock(ProductRepository.class);
    productService = new ProductService(productRepository);
  }

  @Test
  void product() {
    Product product = new Product(
        1L,
        "제조사명",
        "Very Good Product",
        10000L,
        "이 상품은 이러이러합니다",
        "Product Image Url"
    );
    given(productRepository.findById(any()))
        .willReturn(Optional.of(product));

    Product found = productService.product(1L);

    assertThat(found).isNotNull();
    verify(productRepository).findById(any(Long.class));
  }

  @Test
  void findByPage() {
    List<Product> products = List.of(
        new Product(1L, "제조사명 1", "Bad Product", 100L, "상품 설명 1", "Image Url"),
        new Product(2L, "제조사명 2", "Disappointing Product", 200L, "상품 설명 2", "Image Url"),
        new Product(3L, "제조사명 3", "Disgusting Product", 300L, "상품 설명 3", "Image Url"),
        new Product(4L, "제조사명 4", "Terrible Product", 400L, "상품 설명 4", "Image Url"),
        new Product(5L, "제조사명 5", "Awful Product", 500L, "상품 설명 5", "Image Url"),
        new Product(6L, "제조사명 6", "Horrendous Product", 600L, "상품 설명 6", "Image Url")
    );
    int page = 2;
    int pageSize = 3;
    Pageable pageable = PageRequest.of(page - 1, pageSize);

    Page<Product> pageableProducts
        = new PageImpl<>(products, pageable, products.size());

    given(productRepository.findAll(any(Pageable.class)))
        .willReturn(pageableProducts);

    Page<Product> founds = productService.findByPage(page, pageSize);

    assertThat(founds).hasSize(products.size());

    verify(productRepository).findAll(any(Pageable.class));
  }

  @Test
  void findAllWhenEmpty() {
    List<Product> products = List.of();
    int page = 1;
    int pageSize = 3;
    Pageable pageable = PageRequest.of(page - 1, pageSize);

    Page<Product> pageableProducts = new PageImpl<>(products, pageable, products.size());

    given(productRepository.findAll(any(Pageable.class)))
        .willReturn(pageableProducts);

    Page<Product> founds = productService.findByPage(page, pageSize);

    assertThat(founds).isEmpty();
    verify(productRepository).findAll(any(Pageable.class));
  }
}
