package kr.megaptera.makaogift.models;

import kr.megaptera.makaogift.dtos.TransactionDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionTest {
  @Test
  void toTransactionDto() {
    Transaction transaction = new Transaction(
        1L, "보내는녀석", "오버워치", "고급시계", 1, 1000000L,
        "시간이궁금해", "정신과시간의방", "createdAt이 만들어진 시간은?",
        LocalDateTime.of(2022, 10, 7, 11, 3, 14, 0), null
    );

    TransactionDto transactionDto = transaction.toTransactionDto();

    assertThat(transactionDto.getCreatedAt()).isEqualTo("2022-10-07");
  }
}
