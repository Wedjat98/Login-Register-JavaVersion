package cn.lionlemon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class TokenUtil {
    static final long MILLI_SECONDS_IN_HOUR = 60 * 60 * 1000;

    final static String ISSUER = "Wedjat";

    final static String USER_ID = "userid";
    static Algorithm algorithm = Algorithm.HMAC256("aHbK69RJjN");

    public static String signToken(Integer uid, int expirationInHour) {

        return JWT.create().withIssuer(ISSUER).withClaim(USER_ID, uid).withExpiresAt(new Date(System.currentTimeMillis() +
                expirationInHour + MILLI_SECONDS_IN_HOUR)).sign(algorithm);
    }

    public static Integer verifyToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getClaim(USER_ID).asInt();
    }
}
