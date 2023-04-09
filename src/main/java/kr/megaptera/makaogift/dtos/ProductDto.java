package kr.megaptera.makaogift.dtos;

public class ProductDto {
  private final Long id;

  private final String maker;

  private final String name;

  private final Long price;

  private final String description;

  private final String imageUrl;

  public ProductDto(Long id, String maker, String name, Long price,
                    String description, String imageUrl) {
    this.id = id;
    this.maker = maker;
    this.name = name;
    this.price = price;
    this.description = description;
    this.imageUrl = imageUrl;
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

  public String getImageUrl() {
    return imageUrl;
  }
}
