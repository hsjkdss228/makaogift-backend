package kr.megaptera.makaogift.controllers;

import kr.megaptera.makaogift.dtos.ProductDto;
import kr.megaptera.makaogift.dtos.ProductsDto;
import kr.megaptera.makaogift.models.Product;
import kr.megaptera.makaogift.services.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {
  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("/{id}")
  public ProductDto product(
      @PathVariable("id") Long productId
  ) {
    Product product = productService.product(productId);

    return product.toDto();
  }

  @GetMapping
  public ProductsDto products() {
    List<Product> products = productService.findAll();

    List<ProductDto> productDtos = products.stream()
        .map(Product::toDto)
        .toList();

    return new ProductsDto(productDtos);
  }
}
