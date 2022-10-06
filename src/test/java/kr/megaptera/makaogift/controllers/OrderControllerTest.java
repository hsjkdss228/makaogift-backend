package kr.megaptera.makaogift.controllers;

import kr.megaptera.makaogift.models.Transaction;
import kr.megaptera.makaogift.models.Product;
import kr.megaptera.makaogift.services.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@WebMvcTest(OrderController.class)
@ActiveProfiles("test")
class OrderControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private OrderService orderService;

  @Test
  void order() throws Exception {
    Product product = new Product(
        1L, "maker 1", "product name 1", 10000L,
        "description 1");
    Transaction transaction = new Transaction(
        1L, "maker 1", "product name 1", 5, 50000L,
        "recipient 1", "address 1", "message to send 1");
    given(orderService.createOrder(
        any(Long.class), any(Integer.class), any(Long.class),
        any(String.class), any(String.class), any(String.class)))
        .willReturn(transaction);

    mockMvc.perform(MockMvcRequestBuilders.post("/order")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"productId\":\"1\"," +
                "\"purchaseCount\":\"3\"," +
                "\"purchaseCost\":\"40000\"," +
                "\"recipient\":\"황인우\"," +
                "\"address\":\"Chungcheongnamdo\"," +
                "\"messageToSend\":\"good\"" +
                "}"))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().string(
            containsString("orderId")
        ));
  }
}
