package kr.megaptera.makaogift.models;

import kr.megaptera.makaogift.dtos.OrderResultDto;
import kr.megaptera.makaogift.dtos.TransactionDto;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// TODO 1: 보내는 사람의 데이터가 추가되어야 하고 (sender),
//  받는 사람의 데이터 이름을 recipient >> receiver로 수정해야 함 (쉬운 명칭으로)

// TODO 2: 상품의 maker, name을 직접 가져오는 지금의 구조에 변경을 가해서
//  상품의 Id만을 가지고 있게 하고, service에서는 transaction과 product를 묶어서 반환하게 한 뒤
//  나중에 dto로 돌려줄 때 transactionDto에 product의 제조사, 이름을 넣는 구조로 변환해야 할까?
//  작업이 커질 것 같아 선뜻 손대기는 두렵고, 전체를 완성한 뒤에 고민해보도록 하자.

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
