package foorun.unieat.common.rules;

import foorun.unieat.api.exception.UniEatServerErrorException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum MemberRole {
    GUEST("손님"),
    MEMBER("회원"),
    UNIEAT("UNIEAT"),
    TEST("비인가 계정"),
    ;

    private final String description;

    public static List<MemberRole> asList() {
        return Arrays.stream(values()).collect(Collectors.toList());
    }

    public static MemberRole indexOf(int idx) {
        try {
            return asList().get(idx);
        } catch (IndexOutOfBoundsException e) {
            throw new UniEatServerErrorException();
        }
    }
}
