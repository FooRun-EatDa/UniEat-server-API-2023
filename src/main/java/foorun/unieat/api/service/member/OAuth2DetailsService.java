package foorun.unieat.api.service.member;

import foorun.unieat.api.exception.UniEatForbiddenException;
import foorun.unieat.api.model.database.member.entity.UniEatMemberEntity;
import foorun.unieat.api.model.database.member.entity.clazz.UniEatMemberId;
import foorun.unieat.api.model.database.member.repository.UniEatMemberRepository;
import foorun.unieat.common.rules.SocialLoginType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2DetailsService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UniEatMemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        /*OAuth 인증정보 불러오기 */
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        log.debug("##### Token Type : {}", userRequest.getAccessToken().getTokenType().getValue());
        log.debug("##### Token Value: {}", userRequest.getAccessToken().getTokenValue());
        log.debug("##### parameter  : {}", userRequest.getAdditionalParameters());
        log.debug("##### User       : {}", oAuth2User);

        // 공급자 확인 (application-oauth2)
        String provider = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String username = null;
        String password = null;

        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        SocialLoginType loginType = SocialLoginType.valueOfIgnoreCase(provider);
        switch (loginType) {
            case FOORUN: {
                // 자체 로그인?
            } break;

            case APPLE: {
                log.debug("apple attributes: {}", attributes);
                /* TODO: 구현 필요 */
                username = String.valueOf(attributes.get(userNameAttributeName));
                password = null;
            } break;

            case GOOGLE: {
                /* TODO: 구현 필요 */
                log.debug("google attributes: {}", attributes);
                username = String.valueOf(attributes.get(userNameAttributeName));
                password = null;
            } break;

            case NAVER: {
                /* TODO: 구현 필요 */
                log.debug("naver attributeName: {}", userNameAttributeName);
                log.debug("naver attributes: {}", attributes);
                attributes = (Map) attributes.get(userNameAttributeName);
                userNameAttributeName = "id";

                username = String.valueOf(attributes.get(userNameAttributeName));
                password = null;
            } break;

            case KAKAO: {
                username = String.valueOf(attributes.get(userNameAttributeName));
                password = null;
            } break;

            default:
                throw new UniEatForbiddenException();
        }

        UniEatMemberId findMember = new UniEatMemberId(provider, username);
        /* Member의 정보 업데이트나 신규 가입처리 */
        UniEatMemberEntity memberEntity = memberRepository.findById(findMember)
                .orElse(UniEatMemberEntity.builder()
                        .primaryId(username)
                        .password(password)
                        .provider(provider)
                        .build());

        memberRepository.save(memberEntity);

        return oAuth2User;
    }
}