package foorun.unieat.api.service;

import foorun.unieat.api.model.base.dto.UniEatBaseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
public abstract class UniEatCommonService<T extends UniEatBaseDTO> {
    public abstract ResponseEntity service(T form);
}