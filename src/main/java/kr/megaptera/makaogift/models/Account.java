package kr.megaptera.makaogift.models;

import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

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
    this.name = name;
    this.identification = identification;
    this.amount = amount;
  }

  public Account(Long id, String name, String identification, Long amount) {
    this.id = id;
    this.name = name;
    this.identification = identification;
    this.amount = amount;
  }

  public Long id() {
    return id;
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

  @Override
  public String toString() {
    return "Account(" +
        "id: " + id + "\n" +
        "name: " + name + "\n" +
        "identification: " + identification + "\n" +
        "amount" + amount + ")\n";
  }

  @Override
  public boolean equals(Object other) {
    return other != null
        && other.getClass() == Account.class
        && Objects.equals(this.id, ((Account) other).id)
        && this.name.equals(((Account) other).name)
        && this.identification.equals(((Account) other).identification)
        && this.amount.equals(((Account) other).amount);
  }
}
