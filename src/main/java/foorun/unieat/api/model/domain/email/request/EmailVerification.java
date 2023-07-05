package foorun.unieat.api.model.domain.email.request;

import foorun.unieat.api.model.base.dto.UniEatRequestDTO;
import lombok.Data;

@Data
public class EmailVerification implements UniEatRequestDTO {

    private String email;
    private String verificationCode;
}
