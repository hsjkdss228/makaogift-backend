package kr.megaptera.makaogift.utils;

import com.auth0.jwt.exceptions.JWTDecodeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JwtUtilTest {
  private static final String SECRET = "THEREALLYREALLYSECRETCODE";

  private JwtUtil jwtUtil;

  @BeforeEach
  void setUp() {
    jwtUtil = new JwtUtil(SECRET);
  }

  @Test
  void encodeAndDecode() {
    String identification = "dhkddlsgn228";

    String accessToken = jwtUtil.encode(identification);
    assertThat(accessToken).contains(".");

    String original = jwtUtil.decode(accessToken);
    assertThat(original).isEqualTo(identification);
  }

  @Test
  void decodeError() {
    assertThrows(JWTDecodeException.class, () -> {
      jwtUtil.decode("wrongIdentification");
    });
  }
}
