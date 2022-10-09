package kr.megaptera.makaogift.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JwtUtilTest {
  @Test
  void encodeAndDecode() {
    final String secret = "JESUSISREAL";
    JwtUtil jwtUtil = new JwtUtil(secret);

    String identification = "dhkddlsgn228";

    String accessToken = jwtUtil.encode(identification);
    assertThat(accessToken).contains(".");

    String original = jwtUtil.decode(accessToken);
    assertThat(original).isEqualTo(identification);
  }
}
