package foorun.unieat.common.rules;

import foorun.unieat.api.exception.UniEatServerErrorException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum ManagedStatusType {
    ACTIVE("활성"),
    INACTIVE("비활성"),
    REMOVED("삭제"),
    ;

    private final String description;
    public static List<ManagedStatusType> asList() {
        return Arrays.stream(values()).collect(Collectors.toList());
    }

    public static ManagedStatusType indexOf(int idx) {
        try {
            return asList().get(idx);
        } catch (IndexOutOfBoundsException e) {
            throw new UniEatServerErrorException();
        }
    }
}