package kr.megaptera.makaogift.exceptions;

public class AccountNotFound extends RuntimeException {
  public AccountNotFound() {
    super("계정을 찾을 수 없습니다.");
  }
}
