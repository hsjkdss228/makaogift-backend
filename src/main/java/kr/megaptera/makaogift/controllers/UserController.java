package kr.megaptera.makaogift.controllers;

import kr.megaptera.makaogift.dtos.RegistrationErrorDto;
import kr.megaptera.makaogift.dtos.RegistrationRequestDto;
import kr.megaptera.makaogift.dtos.RegistrationResultDto;
import kr.megaptera.makaogift.dtos.UserAmountDto;
import kr.megaptera.makaogift.exceptions.RegistrationFailed;
import kr.megaptera.makaogift.models.Account;
import kr.megaptera.makaogift.services.UserService;
import kr.megaptera.makaogift.validations.ValidationSequence;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("user")
public class UserController {
  private static final Integer BLANK_NAME = 1000;
  private static final Integer BLANK_IDENTIFICATION = 1001;
  private static final Integer BLANK_PASSWORD = 1002;
  private static final Integer BLANK_CONFIRM_PASSWORD = 1003;
  private static final Integer INVALID_NAME = 1004;
  private static final Integer INVALID_IDENTIFICATION = 1005;
  private static final Integer INVALID_PASSWORD = 1006;
  private static final Integer ALREADY_EXISTING_IDENTIFICATION = 1007;
  private static final Integer NOT_MATCHING_PASSWORD = 1008;
  private static final Integer DEFAULT_ERROR = 1009;

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("amount")
  public UserAmountDto amount(
      @RequestAttribute("identification") String identification
  ) {
    Long amount = userService.amount(identification);

    return new UserAmountDto(amount);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public RegistrationResultDto register(
      @Validated(value = {ValidationSequence.class})
      @RequestBody RegistrationRequestDto registrationRequestDto,
      BindingResult bindingResult
  ) {
    if (bindingResult.hasErrors()) {
      List<String> errorMessages = bindingResult.getAllErrors().stream()
          .map(error -> error.getDefaultMessage())
          .toList();
      throw new RegistrationFailed(errorMessages);
    }

    Account createdAccount = userService.createAccount(
        registrationRequestDto.getName(),
        registrationRequestDto.getIdentification(),
        registrationRequestDto.getPassword(),
        registrationRequestDto.getConfirmPassword());

    String name = createdAccount.name();

    return new RegistrationResultDto(name);
  }

  @ExceptionHandler(RegistrationFailed.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public RegistrationErrorDto registrationFailed(RegistrationFailed exception) {
    Map<Integer, String> errorCodesAndMessages
        = exception.errorMessages().stream()
            .collect(Collectors.toMap(
                errorMessage -> mapToErrorCode(errorMessage),
                errorMessage -> mapToErrorCode(errorMessage).equals(DEFAULT_ERROR)
                      ? "알 수 없는 회원가입 실패 오류입니다."
                      : errorMessage
            ));
    return new RegistrationErrorDto(errorCodesAndMessages);
  }

  public Integer mapToErrorCode(String errorMessage) {
    return switch (errorMessage) {
      case "이름을 입력해주세요" -> BLANK_NAME;
      case "아이디를 입력해주세요" -> BLANK_IDENTIFICATION;
      case "비밀번호를 입력해주세요" -> BLANK_PASSWORD;
      case "비밀번호 확인을 입력해주세요" -> BLANK_CONFIRM_PASSWORD;
      case "이름을 다시 확인해주세요" -> INVALID_NAME;
      case "아이디를 다시 확인해주세요" -> INVALID_IDENTIFICATION;
      case "비밀번호를 다시 확인해주세요" -> INVALID_PASSWORD;
      case "해당 아이디는 사용할 수 없습니다" -> ALREADY_EXISTING_IDENTIFICATION;
      case "비밀번호가 일치하지 않습니다" -> NOT_MATCHING_PASSWORD;
      default -> DEFAULT_ERROR;
    };
  }
}
