package foorun.unieat.api.auth;

import foorun.unieat.common.rules.SocialLoginType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;

@Component
public class JwtProvider {
    private final Key key;
    private final long tokenExpiryTime;

    private static final String AUTH_TYPE = "bearer";

    @Autowired
    public JwtProvider(@Value("${jwt.secret-key}") String secretKey, @Value("${jwt.expiration-seconds}") String strExpSec) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.tokenExpiryTime = Long.parseLong(strExpSec) * 1000L;
    }

    public String createToken(long memberId, SocialLoginType loginType) {
        Claims claims = Jwts.claims();
        claims.put("LOGIN_TYPE", loginType.name());

        Date now = Calendar.getInstance().getTime();
        return String.format("%s %s", AUTH_TYPE, Jwts.builder()
                .setClaims(claims)
                .setSubject(String.valueOf(memberId))
                .setIssuer("foorun")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenExpiryTime))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact()
        );
    }

    /*public Optional<String> resolveToken(String authorization) {
        if (!Strings.hasText(authorization)) {
            return Optional.empty();
        }

        if (authorization.contains("bearer")) {
            authorization = authorization.replaceFirst(AUTH_TYPE + " ", "");
        }

        return checkMatch(authorization);
    }*/
}