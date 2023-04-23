package foorun.unieat.common.rules;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AgeGroupType {
    NONE("미확인"),
    TEENS("10대"),
    TWENTIES("20대"),
    THIRTIES("30대"),
    FORTIES("40대"),
    FIFTIES("50대"),
    SIXTIES("60대"),
    SEVENTIES("70대"),
    EIGHTES("80대"),
    NINETIES("90대"),
    ;

    private final String description;
}
