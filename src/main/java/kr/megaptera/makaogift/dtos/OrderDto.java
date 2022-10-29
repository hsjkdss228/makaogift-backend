package kr.megaptera.makaogift.dtos;

import kr.megaptera.makaogift.validations.NotBlankGroup;
import kr.megaptera.makaogift.validations.PatternMatchGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class OrderDto {
  private final Long productId;

  private final Integer purchaseCount;

  private final Long purchaseCost;

  @NotBlank(
      groups = NotBlankGroup.class,
      message = "성함을 입력해주세요")
  @Pattern(
      groups = PatternMatchGroup.class,
      regexp = "^[가-힣]{3,7}$",
      message = "3-7자까지 한글만 사용 가능합니다")
  private final String receiver;

  @NotBlank(
      groups = NotBlankGroup.class,
      message = "주소를 입력해주세요")
  private final String address;

  private final String messageToSend;


  public OrderDto(Long productId,
                  Integer purchaseCount, Long purchaseCost,
                  String receiver, String address, String messageToSend) {
    this.productId = productId;
    this.purchaseCount = purchaseCount;
    this.purchaseCost = purchaseCost;
    this.receiver = receiver;
    this.address = address;
    this.messageToSend = messageToSend;
  }

  public Long getProductId() {
    return productId;
  }

  public Integer getPurchaseCount() {
    return purchaseCount;
  }

  public Long getPurchaseCost() {
    return purchaseCost;
  }

  public String getReceiver() {
    return receiver;
  }

  public String getAddress() {
    return address;
  }

  public String getMessageToSend() {
    return messageToSend;
  }
}
