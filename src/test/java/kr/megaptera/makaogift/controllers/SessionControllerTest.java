package kr.megaptera.makaogift.controllers;

import kr.megaptera.makaogift.exceptions.LoginFailed;
import kr.megaptera.makaogift.models.Account;
import kr.megaptera.makaogift.services.LoginService;
import kr.megaptera.makaogift.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@WebMvcTest(SessionController.class)
class SessionControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private LoginService loginService;

  @SpyBean
  private JwtUtil jwtUtil;

  @SpyBean
  private PasswordEncoder passwordEncoder;

  @Test
  void login() throws Exception {
    Account account = new Account("hsjkdss228", "황인우", 500000L);
    account.changePassword("Megaptera!1", passwordEncoder);
    given(loginService.login(any(), any())).willReturn(account);

    mockMvc.perform(MockMvcRequestBuilders.post("/session")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"identification\":\"hsjkdss228\"," +
                "\"password\":\"Megaptera!1\"" +
                "}"))
        .andExpect(MockMvcResultMatchers.status().isCreated());

    verify(loginService).login(any(), any());
    verify(jwtUtil).encode(any());
  }

  @Test
  void loginWithWrongIdentification() throws Exception {
    given(loginService.login(any(), any())).willThrow(new LoginFailed());

    mockMvc.perform(MockMvcRequestBuilders.post("/session")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"identification\":\"wrongId\"," +
                "\"password\":\"Megaptera!1\"" +
                "}"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());

    verify(loginService).login(any(), any());
  }

  @Test
  void loginWithWrongPassword() throws Exception {
    given(loginService.login(any(), any())).willThrow(new LoginFailed());

    mockMvc.perform(MockMvcRequestBuilders.post("/session")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"identification\":\"hsjkdss228\"," +
                "\"password\":\"wrongPassword\"" +
                "}"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());

    verify(loginService).login(any(), any());
  }
}
