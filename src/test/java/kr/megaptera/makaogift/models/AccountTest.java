package kr.megaptera.makaogift.models;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

class AccountTest {
  @Test
  void authenticate() {
    Account account = new Account("hsjkdss228", "황인우", 0L);
    String password = "Seedwhale!1";

    PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
    account.changePassword(password, passwordEncoder);

    assertThat(account.authenticate(password, passwordEncoder)).isTrue();
    assertThat(account.authenticate("wrongPassword", passwordEncoder)).isFalse();
  }

  @Test
  void reduceAmount() {
    Account account = new Account("hsjkdss228", "황인우", 1000000L);
    account.reduceAmount(999999L);
    assertThat(account.amount()).isEqualTo(1L);
  }
}
