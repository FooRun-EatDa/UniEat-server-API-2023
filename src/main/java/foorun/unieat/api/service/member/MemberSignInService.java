package foorun.unieat.api.service.member;

import foorun.unieat.api.auth.JwtProvider;
import foorun.unieat.api.exception.UniEatUnAuthorizationException;
import foorun.unieat.api.model.database.member.entity.UniEatMemberMyPageEntity;
import foorun.unieat.api.model.database.member.entity.primary_key.UniEatMemberId;
import foorun.unieat.api.model.database.member.repository.UniEatMemberMyPageRepository;
import foorun.unieat.api.model.domain.member.request.MemberSignIn;
import foorun.unieat.api.model.database.member.entity.UniEatMemberEntity;
import foorun.unieat.api.model.database.member.repository.UniEatMemberRepository;
import foorun.unieat.api.exception.UniEatForbiddenException;
import foorun.unieat.api.model.domain.member.response.OAuth2Token;
import foorun.unieat.api.service.UniEatCommonService;
import foorun.unieat.common.http.FooRunToken;
import foorun.unieat.common.rules.SocialLoginType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberSignInService implements UniEatCommonService<MemberSignIn>, OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final ClientRegistrationRepository clientRegistrationRepository;

    private final UniEatMemberRepository memberRepository;
    private final UniEatMemberMyPageRepository myPageRepository;

    private final JwtProvider jwtProvider;

    @Override
    public OAuth2Token service(MemberSignIn form) {
        ClientRegistration provider = clientRegistrationRepository.findByRegistrationId(form.getProvider());
        if (provider == null) {
            log.error("#### 지원하지 않는 소셜 로그인 시도: {}", form.getProvider());
            throw new UniEatUnAuthorizationException();
        }
        final OAuth2AccessToken.TokenType BEARER = OAuth2AccessToken.TokenType.BEARER;
        String accessToken = form.getAccessToken();
        if (accessToken.contains(BEARER.getValue())) {
            accessToken = accessToken.replaceAll(BEARER.getValue(), "").trim();
        }
        OAuth2AccessToken oauth2Token = new OAuth2AccessToken(BEARER, accessToken, Instant.now(), null, provider.getScopes());
        OAuth2UserRequest userRequest = new OAuth2UserRequest(provider, oauth2Token);

        OAuth2User oAuth2User = loadUser(userRequest);
        final String username = oAuth2User.getAttribute("username");
        UniEatMemberEntity memberEntity = memberRepository.findById(UniEatMemberId.of(provider.getRegistrationId(), username)).get();

        FooRunToken token = jwtProvider.createToken(memberEntity.getProvider(), memberEntity.getPrimaryId(), memberEntity.getExpiredDate(), memberEntity.getAuthorities().stream().map(e -> e.getAuthority()).collect(Collectors.joining(", ")));
        memberEntity.setRefreshToken(token.getRefreshToken());
        memberEntity.updateSignInNow();

        memberRepository.save(memberEntity);

        return OAuth2Token.of(token);
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        /*OAuth 인증정보 불러오기 */
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        log.debug("OAUTH TOKEN TYPE : {}", userRequest.getAccessToken().getTokenType().getValue());
        log.debug("OAUTH TOKEN VALUE: {}", userRequest.getAccessToken().getTokenValue());

        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String username = null;
        Object ageRange = null;
        Object birthday = null;
        Object birthdayType = null;
        Object gender = null;

        String provider = clientRegistration.getRegistrationId();
        String userNameAttributeName = clientRegistration.getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        SocialLoginType loginType = SocialLoginType.valueOf(provider.toUpperCase());
        switch (loginType) {
            case FOORUN: {
                // 자체 로그인?
            } break;

            case APPLE: {
                log.debug("apple attributes: {}", attributes);
                /* TODO: apple 구현 필요 */
                username = String.valueOf(attributes.get(userNameAttributeName));
            } break;

            case GOOGLE: {
                /* TODO: google 구현 필요 */
                log.debug("google attributes: {}", attributes);
                username = String.valueOf(attributes.get(userNameAttributeName));
            } break;

            case NAVER: {
                /* TODO: naver 구현 필요 */
                log.debug("naver attributeName: {}", userNameAttributeName);
                log.debug("naver attributes: {}", attributes);
                attributes = (Map) attributes.get(userNameAttributeName);
                userNameAttributeName = "id";

                username = String.valueOf(attributes.get(userNameAttributeName));
            } break;

            case KAKAO: {
                username = String.valueOf(attributes.get(userNameAttributeName));

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
                        .build()
                );
        if (!memberEntity.isEnabled()) {
            log.warn("#### disabled user: {}", memberEntity.getProvider() + "_" + memberEntity.getPrimaryId());
            throw new UniEatForbiddenException();
        }
        autoCreateMemberEntityRelationship(memberEntity);

        Map<String, Object> memberAttributes = new LinkedHashMap<>();
        memberAttributes.put("provider", provider);
        memberAttributes.put("username", username);
        memberAttributes.put("expired_date", memberEntity.getExpiredDate());
        memberAttributes.put("age_range", ageRange);
        memberAttributes.put("birthday", birthday);
        memberAttributes.put("birthday_type", birthdayType);
        memberAttributes.put("gender", gender);

        memberRepository.save(memberEntity);
        return new DefaultOAuth2User(memberEntity.getAuthorities(), memberAttributes, "username");
    }

    private void autoCreateMemberEntityRelationship(UniEatMemberEntity member) {
        if (member.getMyPage() == null) {
            UniEatMemberMyPageEntity memberMyPage = UniEatMemberMyPageEntity.builder()
                    .provider(member.getProvider())
                    .primaryId(member.getPrimaryId())
                    .nickname(member.getProvider() + "_" + member.getPrimaryId())
                    .build();

            myPageRepository.save(memberMyPage);
        }
    }
}