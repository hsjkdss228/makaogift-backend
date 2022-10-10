package kr.megaptera.makaogift.controllers;

import kr.megaptera.makaogift.exceptions.RegistrationFailed;
import kr.megaptera.makaogift.models.Account;
import kr.megaptera.makaogift.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@WebMvcTest(UserController.class)
@ActiveProfiles
class UserControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @SpyBean
  private PasswordEncoder passwordEncoder;

  private String name;
  private String identification;
  private String password;
  private Account account;

  @BeforeEach
  void setUp() {
    name = "황인우";
    identification = "hsjkdss228";
    password = "Seedwhale!1";
    account = new Account(1L, name, identification, 100L);
    account.changePassword(password, passwordEncoder);
  }

  void mockMvcPerformAndExpectWhenOk(
      String name, String identification,
      String password, String confirmPassword,
      String expectedString) throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/user")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"name\":\"" + name + "\"," +
                "\"identification\":\"" + identification + "\"," +
                "\"password\":\"" + password + "\"," +
                "\"confirmPassword\":\"" + confirmPassword + "\"" +
                "}"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(
            containsString(expectedString)
        ));
  }

  void mockMvcPerformAndExpectWhenBadRequest(
      String name, String identification,
      String password, String confirmPassword,
      String expectedString) throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/user")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"name\":\"" + name + "\"," +
                "\"identification\":\"" + identification + "\"," +
                "\"password\":\"" + password + "\"," +
                "\"confirmPassword\":\"" + confirmPassword + "\"" +
                "}"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().string(
            containsString(expectedString)
        ));
  }

  @Test
  void register() throws Exception {
    given(userService.createAccount(
        name, identification, password, password))
        .willReturn(account);

    mockMvcPerformAndExpectWhenOk(
        name, identification, password, password, "name");

    verify(userService).createAccount(name, identification, password, password);
  }

  @Test
  void registerWithBlankName() throws Exception {
    mockMvcPerformAndExpectWhenBadRequest(
        "", identification, password, password, "1000");
  }

  @Test
  void registerWithBlankIdentification() throws Exception {
    mockMvcPerformAndExpectWhenBadRequest(
        name, "", password, password, "1001");
  }

  @Test
  void registerWithBlankPassword() throws Exception {
    mockMvcPerformAndExpectWhenBadRequest(
        name, identification, "", password, "1002");
  }

  @Test
  void registerWithBlankConfirmPassword() throws Exception {
    mockMvcPerformAndExpectWhenBadRequest(
        name, identification, password, "", "1003");
  }

  @Test
  void registerWithName2OrLessWord() throws Exception {
    String wrongName = "치코";
    mockMvcPerformAndExpectWhenBadRequest(
        wrongName, identification, password, password, "1004");
  }

  @Test
  void registerWithName8OrMoreWord() throws Exception {
    String wrongName = "우주킹왕짱치코리타";
    mockMvcPerformAndExpectWhenBadRequest(
        wrongName, identification, password, password, "1004");
  }

  @Test
  void registerWithIdentification3OrLessWord() throws Exception {
    String wrongIdentification = "ab1";
    mockMvcPerformAndExpectWhenBadRequest(
        name, wrongIdentification, password, password, "1005");
  }

  @Test
  void registerWithIdentification17OrMoreWord() throws Exception {
    String wrongIdentification = "kingchikorita1234";
    mockMvcPerformAndExpectWhenBadRequest(
        name, wrongIdentification, password, password, "1005");
  }

  @Test
  void registerWithIdentificationWithoutLowerCase() throws Exception {
    String wrongIdentification = "12412412";
    mockMvcPerformAndExpectWhenBadRequest(
        name, wrongIdentification, password, password, "1005");
  }

  @Test
  void registerWithIdentificationWithoutNumber() throws Exception {
    String wrongIdentification = "chikorita";
    mockMvcPerformAndExpectWhenBadRequest(
        name, wrongIdentification, password, password, "1005");
  }

  @Test
  void registerWithIdentificationWithWrongCharacter() throws Exception {
    String wrongIdentification = "KingKorita&^88";
    mockMvcPerformAndExpectWhenBadRequest(
        name, wrongIdentification, password, password, "1005");
  }

  @Test
  void registerWithPassword7OrLessWord() throws Exception {
    String wrongPassword = "Megap!1";
    mockMvcPerformAndExpectWhenBadRequest(
        name, identification, wrongPassword, password, "1006");
  }

  @Test
  void registerWithPasswordWithoutUpperCase() throws Exception {
    String wrongPassword = "megaptera!1";
    mockMvcPerformAndExpectWhenBadRequest(
        name, identification, wrongPassword, password, "1006");
  }

  @Test
  void registerWithPasswordWithoutLowerCase() throws Exception {
    String wrongPassword = "MEGAPTERA!1";
    mockMvcPerformAndExpectWhenBadRequest(
        name, identification, wrongPassword, password, "1006");
  }

  @Test
  void registerWithPasswordWithoutNumber() throws Exception {
    String wrongPassword = "Megaptera!";
    mockMvcPerformAndExpectWhenBadRequest(
        name, identification, wrongPassword, password, "1006");
  }

  @Test
  void registerWithPasswordWithoutSpecialCharacter() throws Exception {
    String wrongPassword = "Megaptera1";
    mockMvcPerformAndExpectWhenBadRequest(
        name, identification, wrongPassword, password, "1006");
  }

  @Test
  void registerWithAlreadyExistingIdentification() throws Exception {
    given(userService.createAccount(
        name, identification, password, password))
        .willThrow(new RegistrationFailed("해당 아이디는 사용할 수 없습니다"));

    mockMvcPerformAndExpectWhenBadRequest(
        name, identification, password, password, "1007");

    verify(userService).createAccount(name, identification, password, password);
  }

  @Test
  void registerWithNotMatchingPasswords() throws Exception {
    String notMatchingPassword = "mEGAPTERA@2";
    given(userService.createAccount(
        name, identification, password, notMatchingPassword))
        .willThrow(new RegistrationFailed("비밀번호가 일치하지 않습니다"));

    mockMvcPerformAndExpectWhenBadRequest(
        name, identification, password, notMatchingPassword, "1008");

    verify(userService).createAccount(
        name, identification, password, notMatchingPassword);
  }
}
