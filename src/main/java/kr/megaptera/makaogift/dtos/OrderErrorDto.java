package kr.megaptera.makaogift.dtos;

import java.util.Map;

public class OrderErrorDto {
  private final Map<Integer, String> errorCodesAndMessages;

  public OrderErrorDto(Map<Integer, String> errorCodesAndMessages) {
    this.errorCodesAndMessages = errorCodesAndMessages;
  }

  public Map<Integer, String> getErrorCodesAndMessages() {
    return errorCodesAndMessages;
  }
}
