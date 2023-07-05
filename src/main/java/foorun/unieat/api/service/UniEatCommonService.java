package foorun.unieat.api.service;

import foorun.unieat.api.model.base.dto.UniEatRequestDTO;
import foorun.unieat.api.model.base.dto.UniEatResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UniEatCommonService<request extends UniEatRequestDTO> {
    <response extends UniEatResponseDTO> response service(request form);
}