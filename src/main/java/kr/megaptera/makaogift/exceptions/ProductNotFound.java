package kr.megaptera.makaogift.exceptions;

public class ProductNotFound extends RuntimeException {
  public ProductNotFound() {
    super("Id에 해당하는 상품을 찾을 수 없습니다.");
  }
}
