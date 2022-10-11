package kr.megaptera.makaogift.controllers;

import kr.megaptera.makaogift.exceptions.OrderFailed;
import kr.megaptera.makaogift.models.Transaction;
import kr.megaptera.makaogift.services.OrderService;
import kr.megaptera.makaogift.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@WebMvcTest(OrderController.class)
@ActiveProfiles("test")
class OrderControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private OrderService orderService;

  @SpyBean
  private JwtUtil jwtUtil;

  private String token;

  @BeforeEach
  void setUp() {
    String identification = "hsjkdss228";
    token = jwtUtil.encode(identification);
  }

  @Test
  void orderDetail() throws Exception {
    Transaction transaction = new Transaction(
        1L, "황인우", "Forgotten Empires", "AOE2: Definitive Edition", 3, 40000L,
        "김종진", "순천", "게임은 역시 고전이죠",
        LocalDateTime.of(2022, 10, 8, 10, 43, 0, 0));
    given(orderService.orderDetail(any(Long.class)))
        .willReturn(transaction);

    mockMvc.perform(MockMvcRequestBuilders.get("/orders/1")
            .header("Authorization", "Bearer " + token))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(
            containsString("AOE2: Definitive Edition")
        ));
  }

  @Test
  void orders() throws Exception {
    List<Transaction> transactions = List.of(
        new Transaction(1L, "황인우", "Emart 24", "랜더스 수제맥주", 5, 15000L,
            "박수민", "인천 동구", "형님 잘 계십니까?",
            LocalDateTime.of(2022, 10, 7, 11, 3, 14, 0)),
        new Transaction(2L, "황인우", "Insight", "TDD", 1, 22000L,
            "김인우", "충청남도", "인우야 책읽자",
            LocalDateTime.of(2022, 10, 7, 11, 3, 14, 0)),
        new Transaction(3L, "황인우", "Starbucks", "돌체 연유라떼", 4, 24000L,
            "김커피", "커피나라", "커피애호가에게 보내는 최고의 선물",
            LocalDateTime.of(2022, 10, 7, 11, 3, 14, 0)),
        new Transaction(4L, "황인우", "Samsung Electronics", "주식", 10, 700000L,
            "불쌍한사람", "10층", "좀만 견디십쇼",
            LocalDateTime.of(2022, 10, 7, 11, 3, 14, 0))
    );
    int page = 1;
    int pageSize = 3;
    Pageable pageable = PageRequest.of(page - 1, pageSize);
    Page<Transaction> pageableTransactions
        = new PageImpl<>(transactions, pageable, transactions.size());
    given(orderService.findOrdersByIdentification(any(String.class), any(Integer.class), any(Integer.class)))
        .willReturn(pageableTransactions);

    mockMvc.perform(MockMvcRequestBuilders.get("/orders")
            .header("Authorization", "Bearer " + token)
            .param("page", "1"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(
            containsString("Emart 24")
        ))
        .andExpect(MockMvcResultMatchers.content().string(
            containsString("Insight")
        ))
        .andExpect(MockMvcResultMatchers.content().string(
            containsString("Starbucks")
        ))
        .andExpect(MockMvcResultMatchers.content().string(
            containsString("Samsung Electronics")
        ));
  }

  @Test
  void order() throws Exception {
    Transaction transaction = new Transaction(
        1L, "sender 1", "maker 1", "product name 1", 5, 50000L,
        "recipient 1", "address 1", "message to send 1");
    given(orderService.createOrder(
        any(String.class), any(Long.class), any(Integer.class), any(Long.class),
        any(String.class), any(String.class), any(String.class)))
        .willReturn(transaction);

    mockMvc.perform(MockMvcRequestBuilders.post("/order")
            .header("Authorization", "Bearer " + token)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"productId\":\"1\"," +
                "\"purchaseCount\":\"3\"," +
                "\"purchaseCost\":\"40000\"," +
                "\"receiver\":\"황인우\"," +
                "\"address\":\"Chungcheongnamdo\"," +
                "\"messageToSend\":\"good\"" +
                "}"))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().string(
            containsString("orderId")
        ));

    verify(orderService).createOrder(
        any(String.class), any(Long.class), any(Integer.class), any(Long.class),
        any(String.class), any(String.class), any(String.class)
    );
  }

  @Test
  void orderWithBlankName() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/order")
            .header("Authorization", "Bearer " + token)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"productId\":\"1\"," +
                "\"purchaseCount\":\"3\"," +
                "\"purchaseCost\":\"40000\"," +
                "\"receiver\":\"\"," +
                "\"address\":\"Chungcheongnamdo\"," +
                "\"messageToSend\":\"good\"" +
                "}"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().string(
            containsString("3000")
        ));
  }

  @Test
  void orderWithBlankAddress() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/order")
            .header("Authorization", "Bearer " + token)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"productId\":\"1\"," +
                "\"purchaseCount\":\"3\"," +
                "\"purchaseCost\":\"40000\"," +
                "\"receiver\":\"황인우\"," +
                "\"address\":\"\"," +
                "\"messageToSend\":\"good\"" +
                "}"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().string(
            containsString("3001")
        ));
  }

  @Test
  void orderWithReceiverWithLessThan3Words() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/order")
            .header("Authorization", "Bearer " + token)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"productId\":\"1\"," +
                "\"purchaseCount\":\"3\"," +
                "\"purchaseCost\":\"40000\"," +
                "\"receiver\":\"황인\"," +
                "\"address\":\"chungchungnamdo\"," +
                "\"messageToSend\":\"good\"" +
                "}"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().string(
            containsString("3002")
        ));
  }

  @Test
  void orderWithReceiverWithMoreThan7Words() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/order")
            .header("Authorization", "Bearer " + token)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"productId\":\"1\"," +
                "\"purchaseCount\":\"3\"," +
                "\"purchaseCost\":\"40000\"," +
                "\"receiver\":\"킹왕짱치코리타타\"," +
                "\"address\":\"chungchungnamdo\"," +
                "\"messageToSend\":\"good\"" +
                "}"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().string(
            containsString("3002")
        ));
  }

  @Test
  void orderWithAccountWithInsufficientAmount() throws Exception {
    given(orderService.createOrder(
        any(String.class), any(Long.class), any(Integer.class), any(Long.class),
        any(String.class), any(String.class), any(String.class)))
        .willThrow(new OrderFailed("잔액이 부족하여 선물하기가 불가합니다"));

    mockMvc.perform(MockMvcRequestBuilders.post("/order")
            .header("Authorization", "Bearer " + token)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"productId\":\"1\"," +
                "\"purchaseCount\":\"3\"," +
                "\"purchaseCost\":\"40000\"," +
                "\"receiver\":\"황인우\"," +
                "\"address\":\"chungchungnamdo\"," +
                "\"messageToSend\":\"good\"" +
                "}"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().string(
            containsString("3003")
        ));
  }
}
