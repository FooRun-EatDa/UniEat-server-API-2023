package foorun.unieat.api.config.security.filter;

import foorun.unieat.api.auth.JwtProvider;
import foorun.unieat.api.exception.UniEatForbiddenException;
import foorun.unieat.api.exception.UniEatUnAuthorizationException;
import foorun.unieat.api.model.database.member.entity.UniEatMemberEntity;
import foorun.unieat.api.model.database.member.entity.clazz.UniEatMemberId;
import foorun.unieat.api.model.database.member.repository.UniEatMemberRepository;
import foorun.unieat.common.http.FooRunToken;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Slf4j
@RequiredArgsConstructor
public class UniEatJwtAuthentication extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final UniEatMemberRepository memberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken = request.getHeader(JwtProvider.REFRESH_TOKEN_HEADER_NAME);

        // header 내 인증정보 유무 확인
        if (authorization != null && !authorization.trim().isEmpty()) {
            if (jwtProvider.verifyToken(authorization)) {
                if (isNull(SecurityContextHolder.getContext().getAuthentication())) {
                    UniEatMemberEntity member = getPrincipal(authorization);
                    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(member, null, member.getAuthorities()));
                }
            } else if (refreshToken != null && !refreshToken.trim().isEmpty()) {
                if (jwtProvider.verifyToken(refreshToken)) {
                    UniEatMemberEntity member = getPrincipal(refreshToken);
                    // refresh token 확인
                    if (!refreshToken.equals(member.getRefreshToken())) {
                        throw new UniEatUnAuthorizationException();
                    }

                    // 신규 token 발급
                    FooRunToken token = jwtProvider.createToken(member.getProvider(), member.getPrimaryId(), member.getExpiredDate(), member.getAuthorities().stream().map(e -> e.getAuthority()).collect(Collectors.joining(", ")));

                    member.setRefreshToken(token.getRefreshToken());
                    member.updateSignInNow();
                    memberRepository.save(member);

                    response.setHeader(HttpHeaders.AUTHORIZATION, token.getAccessToken());
                    response.setHeader(JwtProvider.REFRESH_TOKEN_HEADER_NAME, token.getRefreshToken());

                    // spring security 인가 통과
                    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(member, null, member.getAuthorities()));
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private UniEatMemberEntity getPrincipal(String token) {
        Claims claims = jwtProvider.divideToken(token);

        final String provider = claims.get(JwtProvider.CLAIM_MEMBER_PROVIDER, String.class);
        final String memberId = claims.get(JwtProvider.CLAIM_MEMBER_ID, String.class);

        UniEatMemberEntity member = memberRepository.findById(UniEatMemberId.of(provider, memberId))
                .orElseThrow(UniEatForbiddenException::new);
        if (!member.isEnabled()) {
            throw new UniEatForbiddenException();
        }

        return member;
    }
}