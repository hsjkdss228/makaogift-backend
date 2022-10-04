package kr.megaptera.makaogift.dtos;

import java.util.List;

public class ProductsDto {
  private List<ProductDto> products;

  public ProductsDto(List<ProductDto> products) {
    this.products = products;
  }

  public List<ProductDto> getProducts() {
    return products;
  }
}
