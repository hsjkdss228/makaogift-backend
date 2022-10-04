package kr.megaptera.makaogift.services;

import kr.megaptera.makaogift.models.Product;
import kr.megaptera.makaogift.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        "이 상품은 이러이러합니다"
    );
    given(productRepository.findById(any()))
        .willReturn(Optional.of(product));

    Product found = productService.product(1L);

    assertThat(found).isNotNull();
    verify(productRepository).findById(any(Long.class));
  }

  @Test
  void findAll() {
    List<Product> products = List.of(
        new Product(1L, "제조사명 1", "Bad Product", 100L, "상품 설명 1"),
        new Product(2L, "제조사명 2", "Disappointing Product", 200L, "상품 설명 2"),
        new Product(2L, "제조사명 3", "Disgusting Product", 300L, "상품 설명 3")
    );
    given(productRepository.findAll())
        .willReturn(products);

    List<Product> founds = productService.findAll();

    assertThat(founds).isNotEmpty();
    verify(productRepository).findAll();
  }

  @Test
  void findAllWhenEmpty() {
    List<Product> products = List.of();
    given(productRepository.findAll())
        .willReturn(products);

    List<Product> founds = productService.findAll();

    assertThat(founds).isEmpty();
    verify(productRepository).findAll();
  }
}
