package foorun.unieat.api.model.domain.email.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmailMessage {
    private String content;
    private Long receiverMemberId;
    private LocalDateTime createdAt = LocalDateTime.now();

}
