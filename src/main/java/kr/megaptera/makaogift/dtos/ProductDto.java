package kr.megaptera.makaogift.dtos;

public class ProductDto {
  private Long id;

  private String maker;

  private String name;

  private Long price;

  private String description;

  public ProductDto(Long id, String maker, String name, Long price, String description) {
    this.id = id;
    this.maker = maker;
    this.name = name;
    this.price = price;
    this.description = description;
  }

  public Long getId() {
    return id;
  }

  public String getMaker() {
    return maker;
  }

  public String getName() {
    return name;
  }

  public Long getPrice() {
    return price;
  }

  public String getDescription() {
    return description;
  }
}
