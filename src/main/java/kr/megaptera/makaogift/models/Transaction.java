package kr.megaptera.makaogift.models;

import kr.megaptera.makaogift.dtos.OrderResultDto;
import kr.megaptera.makaogift.dtos.TransactionDto;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

// TODO 2: 상품의 maker, name을 직접 가져오는 지금의 구조에 변경을 가해서
//  상품의 Id만을 가지고 있게 하고, service에서는 transaction과 product를 묶어서 반환하게 한 뒤
//  나중에 dto로 돌려줄 때 transactionDto에 product의 제조사, 이름을 넣는 구조로 변환해야 할까?
//  작업이 커질 것 같아 선뜻 손대기는 두렵고, 전체를 완성한 뒤에 고민해보도록 하자.

@Entity
public class Transaction {
  @Id
  @GeneratedValue
  private Long id;

  private String sender;

  private String maker;

  private String name;

  private Integer purchaseCount;

  private Long purchaseCost;

  private String receiver;

  private String address;

  private String messageToSend;

  @CreationTimestamp
  private LocalDateTime createdAt;

  private String imageUrl;

  public Transaction() {

  }

  public Transaction(String sender, String maker, String name,
                     Integer purchaseCount, Long purchaseCost,
                     String receiver, String address, String messageToSend,
                     String imageUrl) {
    this.sender = sender;
    this.maker = maker;
    this.name = name;
    this.purchaseCount = purchaseCount;
    this.purchaseCost = purchaseCost;
    this.receiver = receiver;
    this.address = address;
    this.messageToSend = messageToSend;
    this.imageUrl = imageUrl;
  }

  public Transaction(Long id, String sender, String maker, String name,
                     Integer purchaseCount, Long purchaseCost,
                     String receiver, String address, String messageToSend,
                     String imageUrl) {
    this.id = id;
    this.sender = sender;
    this.maker = maker;
    this.name = name;
    this.purchaseCount = purchaseCount;
    this.purchaseCost = purchaseCost;
    this.receiver = receiver;
    this.address = address;
    this.messageToSend = messageToSend;
    this.imageUrl = imageUrl;
  }

  public Transaction(String sender, String maker, String name,
                     Integer purchaseCount, Long purchaseCost,
                     String receiver, String address, String messageToSend,
                     LocalDateTime createdAt, String imageUrl) {
    this.sender = sender;
    this.maker = maker;
    this.name = name;
    this.purchaseCount = purchaseCount;
    this.purchaseCost = purchaseCost;
    this.receiver = receiver;
    this.address = address;
    this.messageToSend = messageToSend;
    this.createdAt = createdAt;
    this.imageUrl = imageUrl;
  }

  public Transaction(Long id, String sender, String maker, String name,
                     Integer purchaseCount, Long purchaseCost,
                     String receiver, String address, String messageToSend,
                     LocalDateTime createdAt, String imageUrl) {
    this.id = id;
    this.sender = sender;
    this.maker = maker;
    this.name = name;
    this.purchaseCount = purchaseCount;
    this.purchaseCost = purchaseCost;
    this.receiver = receiver;
    this.address = address;
    this.messageToSend = messageToSend;
    this.createdAt = createdAt;
    this.imageUrl = imageUrl;
  }

  public Long id() {
    return id;
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
        receiver, address, messageToSend,
        createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
        imageUrl
    );
  }

  @Override
  public String toString() {
    return "Order: " + id + ", " + maker + ", " + name + ", " +
        purchaseCount + ", " + purchaseCost + ", " +
        receiver + ", " + address + ", " + messageToSend
        + ", " + imageUrl;
  }

  @Override
  public boolean equals(Object other) {
    return other != null
        && other.getClass() == Transaction.class
        && Objects.equals(this.id, ((Transaction) other).id)
        && this.sender.equals(((Transaction) other).sender)
        && this.maker.equals(((Transaction) other).maker)
        && this.name.equals(((Transaction) other).name)
        && this.purchaseCount.equals(((Transaction) other).purchaseCount)
        && this.purchaseCost.equals(((Transaction) other).purchaseCost)
        && this.receiver.equals(((Transaction) other).receiver)
        && this.address.equals(((Transaction) other).address)
        && this.messageToSend.equals(((Transaction) other).messageToSend)
        && this.createdAt.equals(((Transaction) other).createdAt)
        && this.imageUrl.equals(((Transaction) other).imageUrl);
  }
}
