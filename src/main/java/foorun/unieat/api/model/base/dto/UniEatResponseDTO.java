package foorun.unieat.api.model.base.dto;

import java.io.Serializable;

import foorun.unieat.common.util.JsonUtil;

/**
 * Dto 객체를 JSON 문자열로 변환하는 클래스
 * Serializable: 객체 직렬화/역직렬화를 지원하는 인터페이스
 * 객체 직렬화는 객체를 바이트 스트림으로 변환하여 전송이나 저장을 용이하게 해준다.
 * Cloneable: 객체를 복제하는 기능을 지원하는 인터페이스
 * Shallow Copy: 스택 영역에 존재하는 같은 주소값을 복사하므로 실제 값이 같음
 * Deep Copy: 실제값을 새로운 메모리 공간에 복사하므로 원본 객체로부터 독립적인 객체를 생성
 */
public interface UniEatResponseDTO extends Serializable, Cloneable {
    /**
     * 현재 객체를 JSON 형식의 문자열로 변환하여 반환
     */
    default String asJson() {
        return JsonUtil.asJson(this);
    }

    /**
     * JSON 데이터의 가독성을 좋게 해주는 형식인 pretty JSON 형식으로 변환하여 반환
     */
    default String asPrettyJson() {
        return JsonUtil.asJson(this, true);
    }
}