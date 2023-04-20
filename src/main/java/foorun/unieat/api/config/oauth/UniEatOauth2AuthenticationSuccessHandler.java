package foorun.unieat.api.config.oauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class UniEatOauth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.debug("oauth2 login success.");

        log.debug("#### Principal   : {}", authentication.getPrincipal());
        log.debug("#### Authorities : {}", authentication.getAuthorities());
        log.debug("#### Credentials : {}", authentication.getCredentials());
        log.debug("#### Details     : {}", authentication.getDetails());

        response.sendRedirect("/member/sign-in");
    }
}