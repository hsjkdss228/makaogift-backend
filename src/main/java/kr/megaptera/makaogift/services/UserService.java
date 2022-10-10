package kr.megaptera.makaogift.services;

import kr.megaptera.makaogift.exceptions.RegistrationFailed;
import kr.megaptera.makaogift.models.Account;
import kr.megaptera.makaogift.repositories.AccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
  private final AccountRepository accountRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(AccountRepository accountRepository,
                     PasswordEncoder passwordEncoder) {
    this.accountRepository = accountRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public Account createAccount(String name, String identification,
                               String password, String confirmPassword) {
    if (accountRepository.findByIdentification(identification).isPresent()) {
      throw new RegistrationFailed("해당 아이디는 사용할 수 없습니다");
    }

    if (!password.equals(confirmPassword)) {
      throw new RegistrationFailed("비밀번호가 일치하지 않습니다");
    }

    Long amount = 10000000L;
    Account account = new Account(name, identification, amount);
    account.changePassword(password, passwordEncoder);

    return accountRepository.save(account);
  }
}
