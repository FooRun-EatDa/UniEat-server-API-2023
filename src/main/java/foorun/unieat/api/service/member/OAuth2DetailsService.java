package foorun.unieat.api.service.member;

import foorun.unieat.api.exception.UniEatForbiddenException;
import foorun.unieat.api.model.database.member.entity.UniEatMemberEntity;
import foorun.unieat.api.model.database.member.entity.clazz.UniEatMemberId;
import foorun.unieat.api.model.database.member.repository.UniEatMemberRepository;
import foorun.unieat.common.rules.SocialLoginType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2DetailsService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UniEatMemberRepository memberRepository;

    @SuppressWarnings("unchecked")
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        /*OAuth 인증정보 불러오기 */
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        log.debug("OAUTH TOKEN TYPE : {}", userRequest.getAccessToken().getTokenType().getValue());
        log.debug("OAUTH TOKEN VALUE: {}", userRequest.getAccessToken().getTokenValue());

        // 공급자 확인 (application-oauth2)
        String provider = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String username = null;
        String password = null;
        Object ageRange = null;
        Object birthday = null;
        Object birthdayType = null;
        Object gender = null;

        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        SocialLoginType loginType = SocialLoginType.valueOf(provider.toUpperCase());
        switch (loginType) {
            case FOORUN: {
                // 자체 로그인?
            } break;

            case APPLE: {
                log.debug("apple attributes: {}", attributes);
                /* TODO: apple 구현 필요 */
                username = String.valueOf(attributes.get(userNameAttributeName));
                password = null;
            } break;

            case GOOGLE: {
                /* TODO: google 구현 필요 */
                log.debug("google attributes: {}", attributes);
                username = String.valueOf(attributes.get(userNameAttributeName));
                password = null;
            } break;

            case NAVER: {
                /* TODO: naver 구현 필요 */
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

                final String keyAgeRange = "age_range";
                final String keyBirthday = "birthday";
                final String keyBirthdayType = "birthday_type";
                final String keyGneder = "gender";

                // kakao 정책 데이터 가져오기
                Map<String, Object> kakaoAttributes = (Map<String, Object>) attributes.get("kakao_account");
                ageRange = kakaoAttributes.get(keyAgeRange);
                birthday = kakaoAttributes.get(keyBirthday);
                birthdayType = kakaoAttributes.get(keyBirthdayType);
                gender = kakaoAttributes.get(keyGneder);
            } break;

            default:
                throw new UniEatForbiddenException();
        }

        UniEatMemberId findMember = UniEatMemberId.of(provider, username);
        /* Member의 정보 업데이트나 신규 가입처리 TODO: DB model에 연령대, 생일, 성별이 추가되면 update할 것 */
        UniEatMemberEntity memberEntity = memberRepository.findById(findMember)
                .orElse(UniEatMemberEntity.builder()
                        .provider(provider)
                        .primaryId(username)
                        .password(password)
                        .build());

        memberRepository.save(memberEntity);

        Map<String, Object> memberAttributes = new LinkedHashMap<>();
        memberAttributes.put("provider", provider);
        memberAttributes.put("username", username);
        memberAttributes.put("expired_date", memberEntity.getExpiredDate());
        memberAttributes.put("age_range", ageRange);
        memberAttributes.put("birthday", birthday);
        memberAttributes.put("birthday_type", birthdayType);
        memberAttributes.put("gender", gender);

        return new DefaultOAuth2User(memberEntity.getAuthorities(), memberAttributes, "username");
    }
}