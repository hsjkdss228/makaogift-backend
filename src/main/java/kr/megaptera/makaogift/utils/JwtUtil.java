package kr.megaptera.makaogift.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtil {
  private final Algorithm algorithm;

  public JwtUtil(String secret) {
    this.algorithm = Algorithm.HMAC256(secret);
  }

  public String encode(String identification) {
    return JWT.create()
        .withClaim("identification", identification)
        .sign(algorithm);
  }

  public String decode(String token) {
    JWTVerifier verifier = JWT.require(algorithm).build();
    DecodedJWT verified = verifier.verify(token);
    return verified.getClaim("identification").asString();
  }
}
