package kr.megaptera.makaogift.repositories;

import kr.megaptera.makaogift.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
  Optional<Account> findByIdentification(String identification);

  Account save(Account account);
}
