package foorun.unieat.api.auth;

import foorun.unieat.api.model.database.member.entity.UniEatMemberEntity;
import foorun.unieat.api.model.database.member.entity.primary_key.UniEatMemberId;
import foorun.unieat.api.model.database.member.repository.UniEatMemberRepository;
import foorun.unieat.common.http.FooRunToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {
    private final Key key;
    private final long tokenExpiryTime;
    private final long tokenRefreshTime;

    public static final String REFRESH_TOKEN_HEADER_NAME = "Authorization-Update";

    public static final String CLAIM_MEMBER_PROVIDER = "UNIEAT-MEMBER-PROVIDER";
    public static final String CLAIM_MEMBER_ID = "UNIEAT-MEMBER-ID";
    public static final String CLAIM_MEMBER_ROLE = "UNIEAT-MEMBER-ROLE";

    public static final String DEFAULT_ISSUER = "foorun";
    public static final String DEFAULT_SUBJECT = "USER-AUTHORIZATION";
    public static final String REFRESH_SUBJECT = "REFRESH-AUTHORIZATION";

    @Autowired
    public JwtProvider(@Value("${jwt.secret-key}") String secretKey, @Value("${jwt.expiration-seconds}") String strExpSec, @Value("${jwt.refresh-seconds}") String strRefSec) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.tokenExpiryTime = Long.parseLong(strExpSec);
        this.tokenRefreshTime = Long.parseLong(strRefSec);
    }

    public boolean verifyToken(String token) {
        /* token type과 token 분리 */
        final String BEARER = OAuth2AccessToken.TokenType.BEARER.getValue();
        if (token.contains(BEARER)) {
            token = token.replaceFirst(BEARER, "").trim();
        }

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

            return true;
        } catch (ExpiredJwtException e) {
            /* token 기한 만료 */
            log.error(e.toString());
        } catch (SecurityException | MalformedJwtException e) {
            /* token 변조 감지 */
            log.error(e.toString());
        } catch (UnsupportedJwtException e) {
            /* JWT 미지원 */
            log.error(e.toString());
        } catch (IllegalArgumentException e) {
            /* JWT 없음 */
            log.error(e.toString());
        }

        return false;
    }

    public FooRunToken createToken(String provider, String memberId, LocalDateTime expiredDate, String authorities) {
        LocalDateTime tokenExpiry = LocalDateTime.now().plusSeconds(this.tokenExpiryTime); // 토큰 만료시간
        LocalDateTime refreshExpiry = LocalDateTime.now().plusSeconds(this.tokenRefreshTime); // 갱신 토큰 만료시간
        Date accessExpiration = Date.from(tokenExpiry.isAfter(expiredDate) ? expiredDate.atZone(ZoneId.systemDefault()).toInstant() : tokenExpiry.atZone(ZoneId.systemDefault()).toInstant());
        Date refreshExpiration = Date.from(refreshExpiry.isAfter(expiredDate) ? expiredDate.atZone(ZoneId.systemDefault()).toInstant() : refreshExpiry.atZone(ZoneId.systemDefault()).toInstant());

        Claims claims = Jwts.claims();
        claims.put(CLAIM_MEMBER_PROVIDER, provider);
        claims.put(CLAIM_MEMBER_ID, memberId);
        claims.put(CLAIM_MEMBER_ROLE, authorities);

        return FooRunToken.builder()
                .accessToken(
                generateToken(claims
                            , DEFAULT_ISSUER
                            , DEFAULT_SUBJECT
                            , String.valueOf(memberId)
                            , accessExpiration
                            , null))
                .refreshToken(
                generateToken(claims
                            , DEFAULT_ISSUER
                            , REFRESH_SUBJECT
                            , String.valueOf(memberId)
                            , refreshExpiration
                            , null))
                .build();
    }

    public UniEatMemberEntity resolveToken(String token, UniEatMemberRepository memberRepository) {
        Claims claims = divideToken(token);
        // 정보 요소 확인
        String provider = claims.get(CLAIM_MEMBER_PROVIDER, String.class);
        String memberId = claims.get(CLAIM_MEMBER_ID, String.class);
        String memberRoleStr = claims.get(CLAIM_MEMBER_ROLE, String.class);
        if (provider == null || provider.trim().isEmpty()) {
            return null;
        }
        if (memberId == null || memberId.trim().isEmpty()) {
            return null;
        }
        if (memberRoleStr == null || memberRoleStr.trim().isEmpty()) {
            return null;
        }

        // 계정정보 확인
        UniEatMemberEntity memberInfo = memberRepository.findById(UniEatMemberId.of(provider, memberId)).orElse(null);
        return memberInfo;
    }

    public Claims divideToken(String token) {
        /* token type과 token 분리 */
        final String BEARER = OAuth2AccessToken.TokenType.BEARER.getValue();
        if (token.contains(BEARER)) {
            token = token.replaceFirst(BEARER, "").trim();
        }

        Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        return jws.getBody();
    }

    private String generateToken(Claims claims, String issuer, String subject, String audience, @NotNull Date expiration, Date nbf) {
        Date issuedAt = Calendar.getInstance().getTime();
        return Jwts.builder()
                .setClaims(claims)                       /* 정보 */
                .setIssuer(issuer)                       /* token 발급자 */
                .setSubject(subject)                     /* token 제목 */
                .setAudience(audience)                   /* token 대상자 */
                .setIssuedAt(issuedAt)                   /* token 발급시간 */
                .setExpiration(expiration)               /* token 만료일시 */
                .setNotBefore(nbf)                       /* token 시작일시 */
                .signWith(key, SignatureAlgorithm.HS512) /* signature 알고리즘과 key */
                .compact()
        ;
    }
}