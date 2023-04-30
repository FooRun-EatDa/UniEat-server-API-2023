package foorun.unieat.common.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FooRunToken {
    @Builder.Default
    private String grantType = OAuth2AccessToken.TokenType.BEARER.getValue();
    private String accessToken;
    private String refreshToken;

    public String getAccessToken() {
        return getAccessToken(false);
    }

    public String getAccessToken(boolean ignoreType) {
        return !ignoreType ? grantType + " " + accessToken : accessToken;
    }

    public String getRefreshToken() {
        return getRefreshToken(false);
    }

    public String getRefreshToken(boolean ignoreType) {
        return !ignoreType ? grantType + " " + refreshToken : refreshToken;
    }
}