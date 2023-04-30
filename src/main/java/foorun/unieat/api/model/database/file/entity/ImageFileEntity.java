package foorun.unieat.api.model.database.file.entity;

import foorun.unieat.api.model.domain.JsonSerializable;
import foorun.unieat.common.rules.StatusType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 이미지 파일 정보
 */
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name="image_file")
public class ImageFileEntity implements JsonSerializable, Persistable<String> {

    @Id
    @Column(name="file_id")
    private String id;
    @Column(name="file_name")
    private String name;
    private String format;
    @Column(name="file_path")
    private String path;
    private Integer width;
    private Integer height;
    private long bytes;
    private String copyright;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private StatusType status = StatusType.ACTIVE;

    @CreatedDate
    private LocalDateTime createdAt;

    @Override
    public boolean isNew() {
        return true;
    }
}
