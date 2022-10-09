package kr.megaptera.makaogift.controllers;

import kr.megaptera.makaogift.dtos.LoginFailedDto;
import kr.megaptera.makaogift.dtos.LoginRequestDto;
import kr.megaptera.makaogift.dtos.LoginResultDto;
import kr.megaptera.makaogift.exceptions.LoginFailed;
import kr.megaptera.makaogift.models.Account;
import kr.megaptera.makaogift.services.LoginService;
import kr.megaptera.makaogift.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("session")
public class SessionController {
  private final LoginService loginService;
  private final JwtUtil jwtUtil;

  public SessionController(LoginService loginService, JwtUtil jwtUtil) {
    this.loginService = loginService;
    this.jwtUtil = jwtUtil;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public LoginResultDto login(
      @RequestBody LoginRequestDto loginRequestDto
  ) {
    Account account = loginService.login(
        loginRequestDto.getIdentification(), loginRequestDto.getPassword());

    String accessToken = jwtUtil.encode(account.identification());

    return new LoginResultDto(accessToken, account.name(), account.amount());
  }

  @ExceptionHandler(LoginFailed.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public LoginFailedDto loginFailed(LoginFailed exception) {
    return new LoginFailedDto(exception.getMessage());
  }
}
