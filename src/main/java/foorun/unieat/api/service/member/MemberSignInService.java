package foorun.unieat.api.service.member;

import foorun.unieat.api.auth.JwtProvider;
import foorun.unieat.api.exception.UniEatUnAuthorizationException;
import foorun.unieat.api.model.base.dto.UniEatResponseDTO;
import foorun.unieat.api.model.database.member.entity.UniEatMemberMyPageEntity;
import foorun.unieat.api.model.database.member.entity.primary_key.UniEatMemberId;
import foorun.unieat.api.model.database.member.repository.UniEatMemberMyPageRepository;
import foorun.unieat.api.model.domain.member.request.MemberSignIn;
import foorun.unieat.api.model.database.member.entity.UniEatMemberEntity;
import foorun.unieat.api.model.database.member.repository.UniEatMemberRepository;
import foorun.unieat.api.exception.UniEatForbiddenException;
import foorun.unieat.api.model.domain.member.request.OAuth2SignIn;
import foorun.unieat.api.model.domain.member.response.OAuth2Token;
import foorun.unieat.api.service.UniEatCommonService;
import foorun.unieat.common.http.FooRunToken;
import foorun.unieat.common.rules.ManagedStatusType;
import foorun.unieat.common.rules.SocialLoginType;
import foorun.unieat.common.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberSignInService implements UniEatCommonService<MemberSignIn> {
    private final ClientRegistrationRepository clientRegistrationRepository;

    private final JwtProvider jwtProvider;

    private final UniEatMemberRepository memberRepository;
    private final UniEatMemberMyPageRepository myPageRepository;

    @Deprecated
    @Override
    public UniEatResponseDTO service(MemberSignIn form) {
        UniEatMemberEntity member = UniEatMemberEntity.builder().status(ManagedStatusType.INACTIVE).build();
        if (!member.isEnabled()) {
            throw new UniEatForbiddenException();
        }

        /*member.updateSignInNow();

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        HttpHeaders httpHeaders = HttpHeaders.readOnlyHttpHeaders(headers);

        return UniEatCommonResponse.success(httpHeaders);*/
        return null;
    }

    public OAuth2Token service(SocialLoginType provider, OAuth2SignIn form) {
        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(provider.name().toLowerCase());
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.set(HttpHeaders.AUTHORIZATION, form.getAccessToken());

        log.debug("#### {} 로그인 시도", provider.name());
        ResponseEntity<String> principal = null;
        try {
            /*
            * 소셜 로그인 사용자 정보 획득 시도
            */
            RestTemplate restTemplate = new RestTemplate();
            principal = restTemplate.exchange(
                    clientRegistration.getProviderDetails().getUserInfoEndpoint().getUri(),
                    HttpMethod.POST,
                    new HttpEntity<>(headers),
                    String.class
            );
        } catch (RestClientException e) {
            log.error("#### oauth2 인증 실패: {}", e.getMessage());
            throw new UniEatUnAuthorizationException();
        }

        if (principal == null) {
            log.error("#### oauth2 principal empty.");
            throw new UniEatForbiddenException();
        }

        if (!principal.getStatusCode().is2xxSuccessful()) {
            log.error("#### oauth2 principal status: {}",  principal.getStatusCode().value());
            throw new UniEatForbiddenException();
        }

        log.debug("#### principal: {}", JsonUtil.asJson(principal.getBody().toString(), true));

        String userNameAttributeName = clientRegistration.getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        Map<String, Object> attributes = JsonUtil.ofJson(principal.getBody(), Map.class);
        String username = null;
        Object ageRange = null;
        Object birthday = null;
        Object birthdayType = null;
        Object gender = null;

        switch (provider) {
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

        UniEatMemberId findMember = UniEatMemberId.of(provider.name().toLowerCase(), username);
        /* Member의 정보 업데이트나 신규 가입처리 TODO: DB model에 연령대, 생일, 성별이 추가되면 update할 것 */
        UniEatMemberEntity memberEntity = memberRepository.findById(findMember)
                .orElse(UniEatMemberEntity.builder()
                        .provider(provider.name().toLowerCase())
                        .primaryId(username)
                        .build()
                );
        if (!memberEntity.isEnabled()) {
            log.warn("#### disabled user: {}", memberEntity.getProvider() + "_" + memberEntity.getPrimaryId());
            throw new UniEatForbiddenException();
        }
        autoCreateMemberEntityRelationship(memberEntity);

        FooRunToken token = jwtProvider.createToken(provider.name().toLowerCase(), username, memberEntity.getExpiredDate(), memberEntity.getAuthorities().stream().map(e -> e.getAuthority()).collect(Collectors.joining(", ")));
        memberEntity.setRefreshToken(token.getRefreshToken());
        memberEntity.updateSignInNow();

        memberRepository.save(memberEntity);

        return OAuth2Token.of(token);
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