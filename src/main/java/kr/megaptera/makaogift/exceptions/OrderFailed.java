package kr.megaptera.makaogift.exceptions;

import java.util.List;

public class OrderFailed extends RuntimeException {
  private final List<String> errorMessages;

  public OrderFailed(List<String> errorMessages) {
    this.errorMessages = errorMessages.stream().toList();
  }

  public List<String> errorMessages() {
    return errorMessages.stream().toList();
  }
}
