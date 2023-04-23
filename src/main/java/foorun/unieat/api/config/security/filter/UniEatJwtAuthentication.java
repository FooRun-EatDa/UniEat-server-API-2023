package foorun.unieat.api.config.security.filter;

import foorun.unieat.api.auth.JwtProvider;
import foorun.unieat.api.exception.UniEatForbiddenException;
import foorun.unieat.api.exception.UniEatUnAuthorizationException;
import foorun.unieat.api.model.base.security.UniEatUserDetails;
import foorun.unieat.api.model.database.member.entity.UniEatMemberAuthEntity;
import foorun.unieat.api.model.database.member.entity.UniEatMemberEntity;
import foorun.unieat.api.model.database.member.entity.clazz.UniEatMemberId;
import foorun.unieat.api.model.database.member.repository.UniEatMemberAuthRepository;
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
    private final UniEatMemberAuthRepository authRepository;

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
                    UniEatUserDetails principal = getPrincipal(authorization);
                    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities()));
                }
            } else {
                // referesh token 확인
                if (refreshToken != null && !refreshToken.trim().isEmpty()) {
                    if (jwtProvider.verifyToken(refreshToken)) {
                        if (!validRefreshToken(refreshToken)) {
                            throw new UniEatUnAuthorizationException();
                        }
                        UniEatUserDetails principal = getPrincipal(refreshToken);
                        FooRunToken token = jwtProvider.createToken(principal.getProvider(), principal.getUsername(), principal.getExpiredDate(), principal.getAuthorities().stream().map(e -> e.getAuthority()).collect(Collectors.joining(", ")));
                        UniEatMemberAuthEntity memberAuth = authRepository.findById(new UniEatMemberId(principal.getProvider(), principal.getUsername())).orElse(UniEatMemberAuthEntity.builder().provider(principal.getProvider()).primaryId(principal.getUsername()).build());

                        memberAuth.setRefreshToken(token.getRefreshToken());
                        authRepository.save(memberAuth);

                        response.setHeader(HttpHeaders.AUTHORIZATION, token.getAccessToken());
                        response.setHeader(JwtProvider.REFRESH_TOKEN_HEADER_NAME, token.getRefreshToken());

                        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities()));
                        UniEatMemberEntity member = memberRepository.findById(new UniEatMemberId(principal.getProvider(), principal.getUsername())).orElse(UniEatMemberEntity.builder().provider(principal.getProvider()).primaryId(principal.getUsername()).build());
                        member.updateSignInNow();
                        memberRepository.save(member);
                    }
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private UniEatUserDetails getPrincipal(String token) {
        Claims claims = jwtProvider.divideToken(token);

        final String provider = claims.get(JwtProvider.CLAIM_MEMBER_PROVIDER, String.class);
        final String memberId = claims.get(JwtProvider.CLAIM_MEMBER_ID, String.class);

        UniEatUserDetails member = memberRepository.findById(new UniEatMemberId(provider, memberId)).orElseThrow(UniEatUnAuthorizationException::new);
        if (!member.isEnabled()) {
            throw new UniEatForbiddenException();
        }

        return member;
    }

    private boolean validRefreshToken(String token) {
        Claims claims = jwtProvider.divideToken(token);

        final String provider = claims.get(JwtProvider.CLAIM_MEMBER_PROVIDER, String.class);
        final String memberId = claims.get(JwtProvider.CLAIM_MEMBER_ID, String.class);

        UniEatMemberAuthEntity memberAuth = authRepository.findById(new UniEatMemberId(provider, memberId)).orElseThrow(UniEatUnAuthorizationException::new);
        return token.equals(memberAuth.getRefreshToken());
    }
}