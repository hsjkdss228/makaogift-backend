package kr.megaptera.makaogift.dtos;

public class OrderResultDto {
  private final Long orderId;

  public OrderResultDto(Long orderId) {
    this.orderId = orderId;
  }

  public Long getOrderId() {
    return orderId;
  }
}
