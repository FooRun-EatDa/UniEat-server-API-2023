package foorun.unieat.api.model.database.article.entity;

import foorun.unieat.api.model.base.jpa.UniEatBaseEntity;
import foorun.unieat.api.model.database.article.entity.primary_key.UniEatArticleHashTag;
import foorun.unieat.api.model.database.category.entity.FoodCategoryEntity;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Article 분류정보
 */
@Entity
@Table(name = "article_category")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
@IdClass(UniEatArticleHashTag.class)
public class ArticleHashTagEntity extends UniEatBaseEntity {
    /**
     * article ID
     */
    @Id
    @Column(name = "article_id")
    private Long articleId;

    /**
     * 분류 ID
     */
    @Id
    @Column(name = "category_id")
    private String categoryId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", insertable = false, updatable = false)
    private FoodCategoryEntity foodCategory;
}