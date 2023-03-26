package foorun.unieat.api.model.base.dto;

import java.io.Serializable;

import foorun.unieat.common.util.JsonUtil;
import lombok.Getter;
import lombok.Setter;

public interface UniEatBaseDTO extends Serializable, Cloneable {
    default String asJson() {
        return JsonUtil.asJson(this);
    }

    default String asPrettyJson() {
        return JsonUtil.asJson(this, true);
    }
}