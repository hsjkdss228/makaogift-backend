package kr.megaptera.makaogift.models;

import kr.megaptera.makaogift.dtos.OrderResultDto;
import kr.megaptera.makaogift.dtos.TransactionDto;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Transaction {
  @Id
  @GeneratedValue
  private Long id;

  private String maker;

  private String name;

  private Integer purchaseCount;

  private Long purchaseCost;

  private String recipient;

  private String address;

  private String messageToSend;

  @CreationTimestamp
  private LocalDateTime createdAt;

  public Transaction() {

  }

  public Transaction(String maker, String name,
                     Integer purchaseCount, Long purchaseCost,
                     String recipient, String address, String messageToSend) {
    this.maker = maker;
    this.name = name;
    this.purchaseCount = purchaseCount;
    this.purchaseCost = purchaseCost;
    this.recipient = recipient;
    this.address = address;
    this.messageToSend = messageToSend;
  }

  public Transaction(Long id, String maker, String name,
                     Integer purchaseCount, Long purchaseCost,
                     String recipient, String address, String messageToSend) {
    this.id = id;
    this.maker = maker;
    this.name = name;
    this.purchaseCount = purchaseCount;
    this.purchaseCost = purchaseCost;
    this.recipient = recipient;
    this.address = address;
    this.messageToSend = messageToSend;
  }

  public Transaction(String maker, String name,
                     Integer purchaseCount, Long purchaseCost,
                     String recipient, String address, String messageToSend,
                     LocalDateTime createdAt) {
    this.maker = maker;
    this.name = name;
    this.purchaseCount = purchaseCount;
    this.purchaseCost = purchaseCost;
    this.recipient = recipient;
    this.address = address;
    this.messageToSend = messageToSend;
    this.createdAt = createdAt;
  }

  public Transaction(Long id, String maker, String name,
                     Integer purchaseCount, Long purchaseCost,
                     String recipient, String address, String messageToSend,
                     LocalDateTime createdAt) {
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

  public LocalDateTime createdAt() {
    return createdAt;
  }

  public OrderResultDto toOrderResultDto() {
    return new OrderResultDto(id);
  }

  public TransactionDto toTransactionDto() {
    return new TransactionDto(
        id, maker, name, purchaseCount, purchaseCost,
        recipient, address, messageToSend,
        createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    );
  }

  @Override
  public String toString() {
    return "Order: " + id + ", " + maker + ", " + name + ", " +
        purchaseCount + ", " + purchaseCost + ", " +
        recipient + ", " + address + ", " + messageToSend;
  }

  @Override
  public boolean equals(Object other) {
    return other != null
        && other.getClass() == Transaction.class
        && this.maker.equals(((Transaction) other).maker)
        && this.name.equals(((Transaction) other).name)
        && this.purchaseCount.equals(((Transaction) other).purchaseCount)
        && this.purchaseCost.equals(((Transaction) other).purchaseCost)
        && this.recipient.equals(((Transaction) other).recipient)
        && this.address.equals(((Transaction) other).address)
        && this.messageToSend.equals(((Transaction) other).messageToSend)
        && this.createdAt.equals(((Transaction) other).createdAt);
  }
}
