package foorun.unieat.api.model.domain.member.request;

import foorun.unieat.api.model.base.dto.UniEatBaseDTO;
import lombok.Data;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Validated
public class MemberSignUp implements UniEatBaseDTO {
    @NotBlank
    @Email(message = "E-Mail 양식이 아닙니다.")
    private String email;

    @ToString.Exclude
    private String password;
    @ToString.Exclude
    private String passwordRe;

    @Size(max = 10, message = "별칭 최대 글자 수는 10자리입니다.")
    private String nickname;
}