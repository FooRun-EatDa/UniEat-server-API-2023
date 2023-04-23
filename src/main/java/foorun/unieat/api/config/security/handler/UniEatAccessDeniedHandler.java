package foorun.unieat.api.config.security.handler;

import foorun.unieat.common.http.FooRunResponseCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UniEatAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        accessDeniedException.printStackTrace();

        response.sendError(HttpServletResponse.SC_FORBIDDEN, FooRunResponseCode.CODE_403.getResponseMessage());
    }
}