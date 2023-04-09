package kr.megaptera.makaogift.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BackdoorControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Test
  void deleteAccountForTest() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/backdoor/delete-account-for-test" +
        "?identification=raboot3368"))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  void deleteTransactionAccountForTest() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/backdoor/delete-transaction-for-test" +
            "?messageToSend=messagefortest"))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  void resetProducts() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/backdoor/reset-products"))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  void setupProduct() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/backdoor/setup-product" +
            "?id=1" +
            "&maker=pokemonCenter" +
            "&name=milk" +
            "&price=300" +
            "&description=delicious"))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  void setupProducts() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/backdoor/setup-products" +
            "?count=40"))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  void setupTransactions() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/backdoor/setup-transactions" +
            "?count=40"))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }
}
