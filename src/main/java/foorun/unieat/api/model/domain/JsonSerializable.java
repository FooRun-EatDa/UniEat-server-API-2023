package foorun.unieat.api.model.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import foorun.unieat.common.util.JsonUtil;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface JsonSerializable extends Serializable {
    default String asJson() {
        return JsonUtil.asJson(this);
    }

    default String asPrettyJson() {
        return JsonUtil.asPrettyJson(this);
    }
}
