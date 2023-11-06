package foorun.unieat.api.model.database.article.entity;

import foorun.unieat.api.model.base.jpa.UniEatBaseEntity;
import foorun.unieat.api.model.database.article.entity.primary_key.UniEatArticlePictures;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Article 사진 정보
 */
@Entity
@Table(name = "article_pictures")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
@IdClass(UniEatArticlePictures.class)
public class ArticlePicturesEntity extends UniEatBaseEntity {
    /**
     * article ID
     */
    @Id
    @Column(name = "article_id")
    private Long articleId;

    /**
     * 사진 번호
     */
    @Id
    @Column(name = "seq")
    private Integer seq;

    /**
     * 사진 위치
     */
    @Column(name = "file_url")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", referencedColumnName = "article_id", insertable = false, updatable = false)
    private ArticleBaseEntity articleBase;
}