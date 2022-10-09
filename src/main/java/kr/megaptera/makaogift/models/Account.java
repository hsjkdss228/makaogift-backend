package kr.megaptera.makaogift.models;

import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Account {
  @Id
  @GeneratedValue
  private Long id;

  private String name;

  private String identification;

  private String encodedPassword;

  private Long amount;

  public Account() {

  }

  public Account(String name, String identification, Long amount) {
    this.identification = identification;
    this.amount = amount;
  }

  public Account(Long id, String name, String identification, Long amount) {
    this.id = id;
    this.identification = identification;
    this.amount = amount;
  }

  public String identification() {
    return identification;
  }

  public String name() {
    return name;
  }

  public String encodedPassword() {
    return encodedPassword;
  }

  public Long amount() {
    return amount;
  }

  public void changePassword(String password, PasswordEncoder passwordEncoder) {
    encodedPassword = passwordEncoder.encode(password);
  }

  public boolean authenticate(String password, PasswordEncoder passwordEncoder) {
    return passwordEncoder.matches(password, encodedPassword);
  }
}
