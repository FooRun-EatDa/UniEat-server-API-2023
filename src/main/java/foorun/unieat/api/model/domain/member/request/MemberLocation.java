package foorun.unieat.api.model.domain.member.request;

import foorun.unieat.api.model.base.dto.UniEatBaseDTO;
import lombok.Data;

@Data
public class MemberLocation implements UniEatBaseDTO {
    private float longitude;
    private float latitude;
}