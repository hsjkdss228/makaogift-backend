package kr.megaptera.makaogift.dtos;

import java.util.List;

public class TransactionsDto {
  private final List<TransactionDto> transactions;

  private final Integer pageSize;

  private final Long totalTransactionsSize;

  public TransactionsDto(List<TransactionDto> transactions, Integer pageSize,
                         Long totalTransactionsSize) {
    this.transactions = transactions;
    this.pageSize = pageSize;
    this.totalTransactionsSize = totalTransactionsSize;
  }

  public List<TransactionDto> getTransactions() {
    return transactions;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public Long getTotalTransactionsSize() {
    return totalTransactionsSize;
  }
}
