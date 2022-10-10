package kr.megaptera.makaogift.services;

import kr.megaptera.makaogift.exceptions.RegistrationFailed;
import kr.megaptera.makaogift.models.Account;
import kr.megaptera.makaogift.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UserServiceTest {
  private AccountRepository accountRepository;
  private PasswordEncoder passwordEncoder;
  private UserService userService;

  private String name;
  private String identification;
  private String password;
  private Long amount;

  private Account accountBeforeSaved;
  private Account accountAfterSaved;

  @BeforeEach
  void setUp() {
    accountRepository = mock(AccountRepository.class);
    passwordEncoder = new Argon2PasswordEncoder();
    userService = new UserService(accountRepository, passwordEncoder);

    name = "김인우";
    identification = "merrong3812";
    password = "Seedwhale!1";
    amount = 10000000L;

    accountBeforeSaved = new Account(name, identification, amount);
    accountBeforeSaved.changePassword(password, passwordEncoder);
    accountAfterSaved = new Account(15L, name, identification, amount);
    accountAfterSaved.changePassword(password, passwordEncoder);
  }

  @Test
  void createAccount() {
    given(accountRepository.save(accountBeforeSaved))
        .willReturn(accountAfterSaved);

    Account account
        = userService.createAccount(name, identification, password, password);

    assertThat(account).isNotNull();
    assertThat(account.id()).isEqualTo(15L);

    verify(accountRepository).save(accountBeforeSaved);
  }

  @Test
  void createAccountWithAlreadyExistingIdentification() {
    given(accountRepository.findByIdentification(identification))
        .willReturn(Optional.of(accountAfterSaved));

    assertThrows(RegistrationFailed.class, () -> {
      userService.createAccount(name, identification, password, password);
    });

    verify(accountRepository).findByIdentification(identification);
  }

  @Test
  void createAccountWithNotMatchingPasswords() {
    String notMatchingPassword = "sEEDWHALE@2";
    assertThrows(RegistrationFailed.class, () -> {
      userService.createAccount(
          name, identification, password, notMatchingPassword);
    });
  }
}
