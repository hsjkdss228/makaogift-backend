package kr.megaptera.makaogift.exceptions;

import java.util.List;

public class RegistrationFailed extends RuntimeException {
  private final List<String> errorMessages;

  public RegistrationFailed(String errorMessage) {
    errorMessages = List.of(errorMessage);
  }

  public RegistrationFailed(List<String> errorMessages) {
    this.errorMessages = errorMessages.stream().toList();
  }

  public List<String> errorMessages() {
    return errorMessages.stream().toList();
  }
}
