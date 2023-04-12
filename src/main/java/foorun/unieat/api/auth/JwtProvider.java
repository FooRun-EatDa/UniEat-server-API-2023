package foorun.unieat.api.auth;

import foorun.unieat.api.model.base.security.UniEatUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtProvider {
    private final Key key;
    private final long tokenExpiryTime;

    private static final String AUTH_TYPE = "bearer";

    private static final String CLAIM_MEMBER_ID = "UNIEAT-MEMBER-ID";
    private static final String CLAIM_MEMBER_ROLE = "UNIEAT-MEMBER-ROLE";

    @Autowired
    public JwtProvider(@Value("${jwt.secret-key}") String secretKey, @Value("${jwt.expiration-seconds}") String strExpSec) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.tokenExpiryTime = Long.parseLong(strExpSec);
    }

    public String createToken(UniEatUserDetails userInfo) {
        String memberId = userInfo.getUsername();
        LocalDateTime expiredDate = userInfo.getExpiredDate();                              // 계정 만료시간
        LocalDateTime tokenExpiry = LocalDateTime.now().plusSeconds(this.tokenExpiryTime);  // 토큰 만료시간

        Claims claims = Jwts.claims();
        claims.put(CLAIM_MEMBER_ID, memberId);
        claims.put(CLAIM_MEMBER_ROLE, userInfo.getAuthorities().stream().map(e -> e.getAuthority()).collect(Collectors.joining(", ")));

        Date now = Calendar.getInstance().getTime();
        return String.format("%s %s", AUTH_TYPE, Jwts.builder()
                .setClaims(claims)
                .setSubject(String.valueOf(memberId))
                .setIssuer("foorun")
                .setIssuedAt(now)
                .setExpiration(Date.from(tokenExpiry.isAfter(expiredDate) ? expiredDate.atZone(ZoneId.systemDefault()).toInstant() : tokenExpiry.atZone(ZoneId.systemDefault()).toInstant()))
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