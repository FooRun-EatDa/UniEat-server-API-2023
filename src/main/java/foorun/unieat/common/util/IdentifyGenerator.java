package foorun.unieat.common.util;

import java.security.SecureRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 인증 코드 생성
 */
public class IdentifyGenerator {
    private static final int DIGIT = 10;
    private static final SecureRandom secureRandom;

    static {
        secureRandom = new SecureRandom();
    }

    /**
     * n자리 인증 코드 생성
     */
    public static String generate(int n) {
        return IntStream.range(0, n).map(i -> secureRandom.nextInt(DIGIT))
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());
    }
}
