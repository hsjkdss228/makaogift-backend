package kr.megaptera.makaogift.dtos;

public class OrderDto {
  private final Long productId;

  private final Integer purchaseCount;

  private final Long purchaseCost;

  private final String recipient;

  private final String address;

  private final String messageToSend;


  public OrderDto(Long productId,
                  Integer purchaseCount, Long purchaseCost,
                  String recipient, String address, String messageToSend) {
    this.productId = productId;
    this.purchaseCount = purchaseCount;
    this.purchaseCost = purchaseCost;
    this.recipient = recipient;
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

  public String getRecipient() {
    return recipient;
  }

  public String getAddress() {
    return address;
  }

  public String getMessageToSend() {
    return messageToSend;
  }
}
