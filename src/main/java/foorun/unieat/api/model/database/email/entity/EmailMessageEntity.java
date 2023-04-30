package foorun.unieat.api.model.database.email.entity;

import lombok.*;
import org.springframework.data.domain.Persistable;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 이메일 메세지 정보
 */
@Getter
@Entity
@Builder
@Validated
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "email_message")
public class EmailMessageEntity implements Persistable<Long> {
    @Id
    @Builder.Default
    @Column(name = "send_message_id")
    private Long no = 0L;

    /**
     * 이메일 발신자
     */
    @Column(name = "send_member_id")
    private Long sender;

    /**
     * 이메일 수신자
     */
    @Column(name = "receive_member_id")
    private Long receiver;

    /**
     * 이메일 제목
     */
    @Column(name = "title")
    private String title;

    /**
     * 이메일 내용
     */
    @Column(name = "content")
    private String content;

    /**
     * 이메일 수신 확인 여부
     */
    @Column(name = "is_read")
    @Builder.Default
    private boolean isRead = false;

    /**
     * 이메일 발송 일시
     */
    @Column(name = "email_send_time")
    private LocalDateTime createdAt;

    @Override
    public Long getId() {
        return this.no;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
