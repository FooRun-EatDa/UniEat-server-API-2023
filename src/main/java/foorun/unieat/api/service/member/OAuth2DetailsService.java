package foorun.unieat.api.service.member;

import foorun.unieat.api.auth.oauth.UniEatAppleOAuth2UserInfo;
import foorun.unieat.api.auth.oauth.UniEatGoogleOAuth2UserInfo;
import foorun.unieat.api.auth.oauth.UniEatKakaoOAuth2UserInfo;
import foorun.unieat.api.auth.oauth.UniEatNaverOAuth2UserInfo;
import foorun.unieat.api.exception.UniEatBadRequestException;
import foorun.unieat.api.model.database.member.entity.UniEatMemberEntity;
import foorun.unieat.api.model.database.member.repository.UniEatMemberRepository;
import foorun.unieat.common.auth.UniEatDefaultOAuth2UserInfo;
import foorun.unieat.common.rules.SocialLoginType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2DetailsService extends DefaultOAuth2UserService {
    private final UniEatMemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        UniEatDefaultOAuth2UserInfo userInfo = null;
        SocialLoginType loginType = SocialLoginType.valueOfIgnoreCase(userRequest.getClientRegistration().getRegistrationId());
        switch (loginType) {
            case APPLE:
                userInfo = new UniEatAppleOAuth2UserInfo(attributes);
                break;

            case GOOGLE:
                userInfo = new UniEatGoogleOAuth2UserInfo(attributes);
                break;

            case NAVER:
                attributes = (Map) attributes.get("response");
                userInfo = new UniEatNaverOAuth2UserInfo(attributes);
                break;

            case KAKAO:
                userInfo = new UniEatKakaoOAuth2UserInfo(attributes);
                break;

            default:
                throw new UniEatBadRequestException();
        }

        String username = userInfo.getProvider() + userInfo.getUsername();
        String password = userInfo.getPassword();

        UniEatMemberEntity memberEntity = memberRepository.findById(username).orElse(null);
        if (memberEntity == null) {
            memberEntity = UniEatMemberEntity.builder()
                    .pId(username)
                    .password(password)
                    .build();
            memberRepository.save(memberEntity);
        }

        return oAuth2User;
    }
}