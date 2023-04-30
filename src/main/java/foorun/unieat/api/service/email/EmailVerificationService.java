package foorun.unieat.api.service.email;

import foorun.unieat.api.model.database.email.repository.EmailVerificationCodeRepository;
import foorun.unieat.api.model.domain.email.request.EmailVerification;
import foorun.unieat.api.service.UniEatCommonService;
import foorun.unieat.common.util.IdentifyGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailVerificationService implements UniEatCommonService<EmailVerification> {
    private static final int CODE_LENGTH = 4;
    private final EmailVerificationCodeRepository emailVerificationCodeRepository;
    /**
     * 인증 코드 이메일 전송
     */
    public ResponseEntity send(EmailVerification emailVerification) {
        final String verificationCode = IdentifyGenerator.generate(CODE_LENGTH);
        return null;
    }
    /**
     * 이메일 인증 코드 확인
     */
    @Transactional
    public boolean verifyEmail(EmailVerification memberVerifyEmail) {
        /* TODO:인증 코드 일치 여부 판별 로직 */
        return true;
    }
    @Override
    public ResponseEntity service(EmailVerification form) {
        return null;
    }
}
