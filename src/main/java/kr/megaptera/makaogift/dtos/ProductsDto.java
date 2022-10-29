package kr.megaptera.makaogift.dtos;

import org.springframework.data.domain.Page;

public class ProductsDto {
  private final Page<ProductDto> products;

  private final Long totalProductsSize;

  public ProductsDto(Page<ProductDto> products, Long totalProductsSize) {
    this.products = products;
    this.totalProductsSize = totalProductsSize;
  }

  public Page<ProductDto> getProducts() {
    return products;
  }

  public Long getTotalProductsSize() {
    return totalProductsSize;
  }
}
