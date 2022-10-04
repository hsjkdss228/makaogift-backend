package kr.megaptera.makaogift.controllers;

import kr.megaptera.makaogift.models.Product;
import kr.megaptera.makaogift.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@WebMvcTest(ProductController.class)
@ActiveProfiles("test")
class ProductControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ProductService productService;

  @Test
  void product() throws Exception {
    Product product = new Product(
        1L,
        "제조사명",
        "Very Good Product",
        10000L,
        "이 상품은 이러이러합니다"
    );
    given(productService.product(any())).willReturn(product);

    mockMvc.perform(MockMvcRequestBuilders.get("/products/1"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(
            containsString("Very Good Product")
        ))
    ;
  }

  @Test
  void products() throws Exception {
    List<Product> products = List.of(
        new Product(1L, "제조사명 1", "Very Good Product", 100L, "상품 설명 1"),
        new Product(2L, "제조사명 2", "Gorgeous Product", 100L, "상품 설명 2"),
        new Product(2L, "제조사명 3", "Extraordinary Product", 100L, "상품 설명 3")
    );
    given(productService.findAll()).willReturn(products);

    mockMvc.perform(MockMvcRequestBuilders.get("/products"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(
            containsString("Very Good Product")
        ))
        .andExpect(MockMvcResultMatchers.content().string(
            containsString("Gorgeous Product")
        ))
        .andExpect(MockMvcResultMatchers.content().string(
            containsString("Extraordinary Product")
        ));
  }
}
