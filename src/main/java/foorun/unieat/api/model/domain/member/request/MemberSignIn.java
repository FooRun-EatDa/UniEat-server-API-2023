package foorun.unieat.api.model.domain.member.request;

import foorun.unieat.api.model.base.dto.UniEatRequestDTO;
import lombok.Data;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Validated
public class MemberSignIn implements UniEatRequestDTO {
    @NotBlank
    private String primaryId;

    @ToString.Exclude
    private String password;
}