package foorun.unieat.api.config.oauth;

import foorun.unieat.api.auth.JwtProvider;
import foorun.unieat.api.exception.UniEatForbiddenException;
import foorun.unieat.api.exception.UniEatUnAuthorizationException;
import foorun.unieat.api.model.database.member.entity.UniEatMemberAuthEntity;
import foorun.unieat.api.model.database.member.entity.clazz.UniEatMemberId;
import foorun.unieat.api.model.database.member.repository.UniEatMemberAuthRepository;
import foorun.unieat.common.http.FooRunResponseCode;
import foorun.unieat.common.http.FooRunToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class UniEatOauth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtProvider jwtProvider;

    private final UniEatMemberAuthRepository authRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());

        try {
            OAuth2User user = (OAuth2User) authentication.getPrincipal();
            final String provider = user.getAttribute("provider");
            final String username = user.getName();
            final LocalDateTime expiredDate = user.getAttribute("expired_date");

            FooRunToken token = jwtProvider.createToken(provider, username, expiredDate, authentication.getAuthorities().stream().map(e -> e.getAuthority()).collect(Collectors.joining(", ")));
            UniEatMemberAuthEntity auth = authRepository.findById(new UniEatMemberId(provider, username))
                    .orElse(UniEatMemberAuthEntity.builder()
                            .provider(provider)
                            .primaryId(username)
                            .build()
                    );

            auth.setRefreshToken(token.getRefreshToken());
            authRepository.save(auth);

            response.addHeader(HttpHeaders.AUTHORIZATION, token.getAccessToken());
            response.addHeader(JwtProvider.REFRESH_TOKEN_HEADER_NAME, token.getRefreshToken());

            log.debug("#### ACCESS TOKEN : {}", token.getAccessToken());
            log.debug("#### REFRESH TOKEN: {}", token.getRefreshToken());
        } catch (UniEatUnAuthorizationException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, FooRunResponseCode.CODE_401.getResponseMessage());
        } catch (UniEatForbiddenException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, FooRunResponseCode.CODE_403.getResponseMessage());
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, FooRunResponseCode.CODE_500.getResponseMessage());
        }
    }
}