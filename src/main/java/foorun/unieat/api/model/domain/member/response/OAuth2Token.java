package foorun.unieat.api.model.domain.member.response;

import foorun.unieat.api.model.base.dto.UniEatResponseDTO;
import foorun.unieat.common.http.FooRunToken;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class OAuth2Token implements UniEatResponseDTO {
    private FooRunToken token;
}