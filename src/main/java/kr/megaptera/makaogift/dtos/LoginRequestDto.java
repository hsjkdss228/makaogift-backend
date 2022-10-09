package kr.megaptera.makaogift.dtos;

public class LoginRequestDto {
  private final String identification;

  private final String password;

  public LoginRequestDto(String identification, String password) {
    this.identification = identification;
    this.password = password;
  }

  public String getIdentification() {
    return identification;
  }

  public String getPassword() {
    return password;
  }
}
