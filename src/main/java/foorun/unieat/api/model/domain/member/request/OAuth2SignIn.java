package foorun.unieat.api.model.domain.member.request;

import foorun.unieat.api.model.base.dto.UniEatBaseDTO;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Validated
public class OAuth2SignIn implements UniEatBaseDTO {
    @NotBlank
    private String accessToken;
}