package kr.megaptera.makaogift.controllers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/backdoor")
@Transactional
public class BackdoorController {
  private final JdbcTemplate jdbcTemplate;

  public BackdoorController(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @GetMapping("/delete-account-for-test")
  public String deleteAccountForTest(
      @RequestParam String identification
  ) {
    jdbcTemplate.update("" +
        "delete from ACCOUNT where IDENTIFICATION = '" + identification + "'"
    );

    return "Backdoor 테스트용 계정 삭제가 완료되었습니다.";
  }

  @GetMapping("/delete-transaction-for-test")
  public String deleteTransactionForTest(
      @RequestParam String messageToSend
  ) {
    jdbcTemplate.update("" +
        "delete from TRANSACTION where MESSAGE_TO_SEND = '" + messageToSend + "'"
    );

    return "Backdoor 테스트용 계정 삭제가 완료되었습니다.";
  }

  @GetMapping("/reset-products")
  public String resetProducts() {
    resetProductsDatabase();

    return "Backdoor 상품 목록 비우기가 완료되었습니다.";
  }

  @GetMapping("/setup-product")
  public String setupProduct(
      @RequestParam Long id,
      @RequestParam String maker,
      @RequestParam String name,
      @RequestParam Long price,
      @RequestParam String description
  ) {
    resetProductsDatabase();

    jdbcTemplate.update("" +
            "insert into PRODUCT(" +
            "ID, MAKER, NAME, PRICE, DESCRIPTION) " +
            "values(?, ?, ?, ?, ?)",
        id, maker, name, price, description);

    return "Backdoor 단일 상품 세팅이 완료되었습니다.";
  }

  @GetMapping("/setup-products")
  public String setupProducts(
      @RequestParam Long count
  ) {
    resetProductsDatabase();

    for (long i = 1; i <= count; i += 1) {
      jdbcTemplate.update("" +
              "insert into PRODUCT(" +
              "ID, MAKER, NAME, PRICE, DESCRIPTION) " +
              "values(?, ?, ?, ?, ?)",
          i, "제조사 " + i, "상품 옵션명 " + i, i * 100, "상품 설명 " + i);
    }

    return "Backdoor 상품 여러 개 세팅이 완료되었습니다.";
  }

  @GetMapping("/setup-transactions")
  public String setupTransactions(
      @RequestParam Long count
  ) {
    // TODO: 보내는 사람 한 명이 여러 개의 주문 내역을 만드는 과정은
    //  어떻게 구현할 것인지 고민이 필요할 듯

    resetTransactionsDatabase();

    for (long i = 1; i <= count; i += 1) {
      jdbcTemplate.update("" +
              "insert into TRANSACTION(" +
              "ID, SENDER, MAKER, NAME, PURCHASE_COUNT, PURCHASE_COST, " +
              "RECEIVER, ADDRESS, MESSAGE_TO_SEND, " +
              "CREATED_AT) " +
              "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
          i, "보내는 사람" + i, "제조사 " + i, "상품 옵션명 " + i, i, i * (i * 100),
          "받는 사람 " + i, "주소 " + i, "보낼 메세지 " + i,
          LocalDateTime.now());
    }

    return "Backdoor 거래 내역 여러 개 세팅이 완료되었습니다.";
  }

  public void resetProductsDatabase() {
    jdbcTemplate.execute("delete from PRODUCT");
  }

  public void resetTransactionsDatabase() {
    jdbcTemplate.execute("delete from TRANSACTION");
  }
}
