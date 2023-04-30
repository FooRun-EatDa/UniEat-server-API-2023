package foorun.unieat.api.model.database.file.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseFileEntity {

    /**
     * 대표 이미지 여부
     */
    private boolean thumbnail;

    /**
     * 정렬 순서
     */
    private int sequence;

    @Id
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name="file_id")
    private ImageFileEntity file;
}
