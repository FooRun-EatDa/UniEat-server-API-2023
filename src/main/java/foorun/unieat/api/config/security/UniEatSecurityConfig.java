package foorun.unieat.api.config.security;

import foorun.unieat.api.auth.JwtProvider;
import foorun.unieat.api.config.oauth.UniEatOauth2AuthenticationFailureHandler;
import foorun.unieat.api.config.security.filter.UniEatJwtAuthentication;
import foorun.unieat.api.config.security.handler.UniEatAccessDeniedHandler;
import foorun.unieat.api.config.security.handler.UniEatAuthenticationEntryPoint;
import foorun.unieat.api.config.oauth.UniEatOauth2AuthenticationSuccessHandler;
import foorun.unieat.api.model.database.member.repository.UniEatMemberAuthRepository;
import foorun.unieat.api.model.database.member.repository.UniEatMemberRepository;
import foorun.unieat.api.service.member.OAuth2DetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class UniEatSecurityConfig {
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;
    private final OAuth2DetailsService oAuth2DetailsService;

    private final UniEatMemberRepository memberRepository;
    private final UniEatMemberAuthRepository authRepository;
    private final JwtProvider jwtProvider;

    /* Spring security Password Encoder */
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOriginPattern("*");                 // port가 다른 pattern 허용
        configuration.addAllowedHeader("*");                        // header 응답 허용
        configuration.addAllowedMethod("*");                        // method 응답 허용
        configuration.setAllowCredentials(true);                    // javascript 허용
        configuration.addExposedHeader(HttpHeaders.AUTHORIZATION);
        configuration.addExposedHeader(JwtProvider.REFRESH_TOKEN_HEADER_NAME);
        configuration.addExposedHeader(HttpHeaders.LOCATION);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);

        return source;
    }

    /* http(Hyper Text Transfer Protocol) 설정 */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic()                                        /* http */
            .disable()

            .csrf()                                             /* Cross Site Request Forgery */
            .disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()

            .cors()                                                 /* Cross-Origin Resource Sharing */
            .configurationSource(corsConfigurationSource())
            .and()

            .formLogin()                                            /* form login 처리 */
            .disable()

            .headers()
            .frameOptions()
            .sameOrigin()

            .and()

            .exceptionHandling()                                            /* Exception 처리 */
            .authenticationEntryPoint(new UniEatAuthenticationEntryPoint()) /* 인증 자격 확인 */
            .accessDeniedHandler(new UniEatAccessDeniedHandler())           /* 권한 확인 */

            .and()

            .logout()                                               /* logout 처리 */
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true)                            /* 세션 무효화 */
            .deleteCookies("JSESSIONID")        /* 쿠키 제거 */

            .and()

            .authorizeHttpRequests()                                /* 요청에 대한 검사 처리 */
            .antMatchers("/member/sign-*")     /* URL 패턴 */
            .permitAll()                                            /* 인가 */
            .anyRequest()
            .authenticated()
            .and()

            /* 회원 인증처리 전 Json Web Token 확인 및 재발급 여부 */
            .addFilterBefore(new UniEatJwtAuthentication(jwtProvider, memberRepository, authRepository), UsernamePasswordAuthenticationFilter.class)

            .oauth2Login()
            .clientRegistrationRepository(clientRegistrationRepository)
            .authorizedClientService(oAuth2AuthorizedClientService)
            .authorizationEndpoint()                                /* 인가 요청      default: /oauth2/authorization/{registrationId} */
            .and()
            .redirectionEndpoint()                                  /* 인가 코드 발급 default: /login/oauth2/code/{registrationId} */
            .and()
            .tokenEndpoint()                                        /* Access Token 발급 */
            .and()
            .userInfoEndpoint().userService(oAuth2DetailsService)   /* oauth2 login 성공 이후 설정 */
            .and()
            .successHandler(new UniEatOauth2AuthenticationSuccessHandler(jwtProvider, authRepository))
            .failureHandler(new UniEatOauth2AuthenticationFailureHandler())
        ;

        return http.build();
    }

    /* 정적 자원 등의 Page Ignore */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web
                .ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
        ;
    }
}