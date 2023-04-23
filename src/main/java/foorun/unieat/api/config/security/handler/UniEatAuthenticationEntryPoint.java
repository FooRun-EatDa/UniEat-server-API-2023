package foorun.unieat.api.config.security.handler;

import foorun.unieat.common.http.FooRunResponseCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UniEatAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        /**
         * TODO: token 만료 예외 처리
         */
        authException.printStackTrace();

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, FooRunResponseCode.CODE_401.getResponseMessage());
    }
}