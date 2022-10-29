package kr.megaptera.makaogift.exceptions;

public class TransactionNotFound extends RuntimeException {
  public TransactionNotFound() {
    super("Id에 해당하는 주문 내역을 찾을 수 없습니다.");
  }
}
