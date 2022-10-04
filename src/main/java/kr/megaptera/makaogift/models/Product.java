package kr.megaptera.makaogift.models;

import kr.megaptera.makaogift.dtos.ProductDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Product {
  @Id
  @GeneratedValue
  private Long id;

  private String maker;

  private String name;

  private Long price;

  private String description;

  public Product() {

  }

  public Product(Long id, String maker, String name, Long price, String description) {
    this.id = id;
    this.maker = maker;
    this.name = name;
    this.price = price;
    this.description = description;
  }

  public ProductDto toDto() {
    return new ProductDto(
        id,
        maker,
        name,
        price,
        description
    );
  }
}
