package kr.megaptera.makaogift.controllers;

import kr.megaptera.makaogift.models.Product;
import kr.megaptera.makaogift.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        "Chris Sawyer",
        "RollerCoaster Tycoon 2",
        5000L,
        "놀이공원 건설 경영 시뮬레이션 게임을 가장한 대혼돈의 실험실"
    );
    given(productService.product(any())).willReturn(product);

    mockMvc.perform(MockMvcRequestBuilders.get("/products/1"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(
            containsString("RollerCoaster Tycoon 2")
        ));
  }

  @Test
  void products() throws Exception {
    List<Product> products = List.of(
        new Product(1L, "제조사명 1", "Very Good Product", 100L, "상품 설명 1"),
        new Product(2L, "제조사명 2", "Gorgeous Product", 100L, "상품 설명 2"),
        new Product(3L, "제조사명 3", "Extraordinary Product", 100L, "상품 설명 3")
    );
    int page = 1;
    Pageable pageable = PageRequest.of(page - 1, 2);

    Page<Product> pageableProducts
        = new PageImpl<>(products, pageable, products.size());
    given(productService.findByPage(any(Integer.class), any(Integer.class)))
        .willReturn(pageableProducts);

    mockMvc.perform(MockMvcRequestBuilders.get("/products")
            .param("page", "1"))
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
