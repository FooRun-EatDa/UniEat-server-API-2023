package foorun.unieat.api.model.domain.member.request;

import foorun.unieat.api.model.base.dto.UniEatBaseDTO;
import lombok.Data;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Validated
public class MemberSignInByKakao implements UniEatBaseDTO {
    @NotBlank
    private long memberId;

    /* REST API key */
    @NotBlank
    private String clientId;

    /* REDIRECT URI */
    @NotBlank
    private String redirectUri;

    /* 카카오에서 인가된 authorization code */
    @NotBlank
    private String code;
}