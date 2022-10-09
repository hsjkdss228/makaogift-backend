package kr.megaptera.makaogift.dtos;

public class LoginFailedDto {
  // TODO: 에러 코드를 포함하도록 구조를 변경해야 함
  //  (validation으로부터 전달되는 에러와 구분 필요)

  private final String errorMessage;

  public LoginFailedDto(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getError() {
    return errorMessage;
  }
}
