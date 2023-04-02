package foorun.unieat.api.service.member;

import foorun.unieat.api.model.base.dto.UniEatBaseDTO;
import foorun.unieat.api.model.domain.UniEatCommonResponse;
import foorun.unieat.api.service.UniEatCommonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberSignOutService implements UniEatCommonService {

    @Override
    public ResponseEntity service(UniEatBaseDTO form) {
        return UniEatCommonResponse.success();
    }
}