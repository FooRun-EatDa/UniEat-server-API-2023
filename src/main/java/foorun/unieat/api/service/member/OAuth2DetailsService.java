package foorun.unieat.api.service.member;

import foorun.unieat.api.auth.oauth.UniEatOAuth2User;
import foorun.unieat.api.exception.UniEatForbiddenException;
import foorun.unieat.api.model.database.member.entity.UniEatMemberEntity;
import foorun.unieat.api.model.database.member.repository.UniEatMemberRepository;
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

        String username = null;
        String password = null;

        String provider = userRequest.getClientRegistration().getRegistrationId();
        SocialLoginType loginType = SocialLoginType.valueOfIgnoreCase(provider);
        switch (loginType) {
            case APPLE: {
                username = provider + "_" + attributes.get("");
                password = null;
            } break;

            case GOOGLE: {
                username = provider + "_" + attributes.get("sub");
                password = null;
            } break;

            case NAVER: {
                attributes = (Map) attributes.get("response");

                username = provider + "_" + attributes.get("id");
                password = null;
            } break;

            case KAKAO: {
                username = provider + "_" + attributes.get("id");
                password = null;
            } break;

            default:
                throw new UniEatForbiddenException();
        }

        UniEatMemberEntity memberEntity = memberRepository.findById(username).orElse(null);
        if (memberEntity == null) {
            memberEntity = UniEatMemberEntity.builder()
                    .primaryId(username)
                    .password(password)
                    .build();

            memberRepository.save(memberEntity);
        }

        return UniEatOAuth2User.create(memberEntity, attributes);
    }
}