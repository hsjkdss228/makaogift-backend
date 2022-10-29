package kr.megaptera.makaogift.dtos;

import java.util.Map;

public class RegistrationErrorDto {
  private final Map<Integer, String> codesAndMessages;

  public RegistrationErrorDto(Map<Integer, String> codesAndMessages) {
    this.codesAndMessages = Map.copyOf(codesAndMessages);
  }

  public Map<Integer, String> getCodesAndMessages() {
    return codesAndMessages;
  }
}
