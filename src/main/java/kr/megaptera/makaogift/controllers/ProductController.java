package kr.megaptera.makaogift.controllers;

import kr.megaptera.makaogift.dtos.ProductDto;
import kr.megaptera.makaogift.dtos.ProductsDto;
import kr.megaptera.makaogift.models.Product;
import kr.megaptera.makaogift.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
  public ProductsDto products(
      @RequestParam Integer page
  ) {
    int pageSize = 8;

    Page<Product> products = productService.findByPage(page, pageSize);

    Long totalProductsSize = products.getTotalElements();

    List<ProductDto> productDtos = products.stream()
        .map(Product::toDto)
        .toList();

    Page<ProductDto> pageableProductDtos
        = new PageImpl<>(productDtos, PageRequest.of(page - 1, 8), productDtos.size());

    return new ProductsDto(pageableProductDtos, totalProductsSize);
  }
}
