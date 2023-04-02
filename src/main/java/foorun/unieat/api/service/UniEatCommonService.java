package foorun.unieat.api.service;

import foorun.unieat.api.model.base.dto.UniEatBaseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UniEatCommonService<T extends UniEatBaseDTO> {
    ResponseEntity service(T form);
}