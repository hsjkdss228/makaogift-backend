package kr.megaptera.makaogift.services;

import kr.megaptera.makaogift.exceptions.LoginFailed;
import kr.megaptera.makaogift.models.Account;
import kr.megaptera.makaogift.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class LoginServiceTest {
  private AccountRepository accountRepository;
  private PasswordEncoder passwordEncoder;
  private LoginService loginService;

  @BeforeEach
  void setUp() {
    accountRepository = mock(AccountRepository.class);
    passwordEncoder = new Argon2PasswordEncoder();
    loginService = new LoginService(accountRepository, passwordEncoder);
  }

  @Test
  void login() {
    String identification = "hsjkdss228";
    String password = "Megaptera!1";
    Account account = spy(new Account(identification, "황인우", 1000000L));
    account.changePassword(password, passwordEncoder);

    given(accountRepository.findByIdentification(identification))
        .willReturn(Optional.of(account));

    Account found = loginService.login(identification, password);

    assertThat(found).isNotNull();

    verify(accountRepository).findByIdentification(identification);
    verify(account).authenticate(eq(password), any(PasswordEncoder.class));
  }

  @Test
  void loginWithWrongIdentification() {
    assertThrows(LoginFailed.class, () -> {
      loginService.login("wrongIdentification", "Meagptera!1");
    });
  }

  @Test
  void loginWithWrongPassword() {
    String identification = "hsjkdss228";
    Account account = spy(new Account(identification, "치코리타", 1000000L));
    String rawPassword = "Megaptera!1";
    account.changePassword(rawPassword, passwordEncoder);

    given(accountRepository.findByIdentification(identification))
        .willReturn(Optional.of(account));

    String wrongRawPassword = "wrongPassword";
    assertThrows(LoginFailed.class, () -> {
      loginService.login("hsjkdss228", wrongRawPassword);
    });

    verify(accountRepository).findByIdentification(identification);
    verify(account).authenticate(eq(wrongRawPassword), any(PasswordEncoder.class));
  }
}
