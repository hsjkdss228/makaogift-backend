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
    // TODO: 계정 개념이 도입될 경우 보내는 사람 Column을 추가해줘야 함

    resetTransactionsDatabase();

    for (long i = 1; i <= count; i += 1) {
      jdbcTemplate.update("" +
                          "insert into TRANSACTION(" +
                          "ID, MAKER, NAME, PURCHASE_COUNT, PURCHASE_COST, " +
                          "RECIPIENT, ADDRESS, MESSAGE_TO_SEND, " +
                          "CREATED_AT) " +
                          "values(?, ?, ?, ?, ?, ?, ?, ?, ?)",
          i, "제조사 " + i, "상품 옵션명 " + i, i, i * (i * 100),
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
