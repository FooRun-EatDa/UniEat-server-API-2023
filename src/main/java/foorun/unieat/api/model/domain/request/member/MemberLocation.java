package foorun.unieat.api.model.domain.request.member;

import foorun.unieat.api.model.base.dto.UniEatBaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberLocation implements UniEatBaseDTO {
    private float longitude;
    private float latitude;
}
