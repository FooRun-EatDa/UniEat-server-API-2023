package foorun.unieat.api.model.domain.email.request;

import foorun.unieat.api.model.base.dto.UniEatBaseDTO;
import lombok.Data;

@Data
public class EmailVerification implements UniEatBaseDTO {

    private String email;
    private String verificationCode;
}
