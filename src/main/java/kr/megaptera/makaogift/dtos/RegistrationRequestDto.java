package kr.megaptera.makaogift.dtos;

import kr.megaptera.makaogift.validations.NotBlankGroup;
import kr.megaptera.makaogift.validations.PatternMatchGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class RegistrationRequestDto {
  @NotBlank(
      groups = NotBlankGroup.class,
      message = "이름을 입력해주세요")
  @Pattern(
      groups = PatternMatchGroup.class,
      regexp = "^[가-힣]{3,7}$",
      message = "이름을 다시 확인해주세요")
  private final String name;

  @NotBlank(
      groups = NotBlankGroup.class,
      message = "아이디를 입력해주세요")
  @Pattern(
      groups = PatternMatchGroup.class,
      regexp = "^(?=.*[a-z])(?=.*\\d)[a-z\\d]{4,16}$",
      message = "아이디를 다시 확인해주세요")
  private final String identification;

  @NotBlank(
      groups = NotBlankGroup.class,
      message = "비밀번호를 입력해주세요")
  @Pattern(
      groups = PatternMatchGroup.class,
      regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d(?=.*@$!%*#?&)]{8,}$",
      message = "비밀번호를 다시 확인해주세요")
  private final String password;

  @NotBlank(
      groups = NotBlankGroup.class,
      message = "비밀번호 확인을 입력해주세요")
  private final String confirmPassword;

  public RegistrationRequestDto(String name, String identification,
                                String password, String confirmPassword) {
    this.name = name;
    this.identification = identification;
    this.password = password;
    this.confirmPassword = confirmPassword;
  }

  public String getName() {
    return name;
  }

  public String getIdentification() {
    return identification;
  }

  public String getPassword() {
    return password;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }
}
