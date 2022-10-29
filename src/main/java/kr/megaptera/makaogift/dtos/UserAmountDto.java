package kr.megaptera.makaogift.dtos;

public class UserAmountDto {
  private final Long amount;

  public UserAmountDto(Long amount) {
    this.amount = amount;
  }

  public Long getAmount() {
    return amount;
  }
}
