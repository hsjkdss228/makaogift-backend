package kr.megaptera.makaogift.dtos;

import javax.validation.constraints.NotBlank;

public class LoginRequestDto {
  @NotBlank(message = "아이디를 입력해주세요")
  private final String identification;

  @NotBlank(message = "비밀번호를 입력해주세요")
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
