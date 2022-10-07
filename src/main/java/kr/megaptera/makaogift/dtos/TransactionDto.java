package kr.megaptera.makaogift.dtos;

public class TransactionDto {
  private final Long id;

  private final String maker;

  private final String name;

  private final Integer purchaseCount;

  private final Long purchaseCost;

  private final String recipient;

  private final String address;

  private final String messageToSend;

  private final String createdAt;

  public TransactionDto(Long id, String maker, String name,
                        Integer purchaseCount, Long purchaseCost,
                        String recipient, String address, String messageToSend,
                        String createdAt) {
    this.id = id;
    this.maker = maker;
    this.name = name;
    this.purchaseCount = purchaseCount;
    this.purchaseCost = purchaseCost;
    this.recipient = recipient;
    this.address = address;
    this.messageToSend = messageToSend;
    this.createdAt = createdAt;
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

  public String getCreatedAt() {
    return createdAt;
  }
}
