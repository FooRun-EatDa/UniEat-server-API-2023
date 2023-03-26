package foorun.unieat.api.service.member;

import foorun.unieat.api.model.base.dto.UniEatBaseDTO;
import foorun.unieat.api.model.domain.response.UniEatCommonResponse;
import foorun.unieat.api.service.UniEatCommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberSignOutService extends UniEatCommonService {

    @Override
    public ResponseEntity service(UniEatBaseDTO form) {
        return UniEatCommonResponse.success();
    }
}