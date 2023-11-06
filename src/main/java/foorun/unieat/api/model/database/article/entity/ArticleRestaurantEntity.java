package foorun.unieat.api.model.database.article.entity;

import foorun.unieat.api.model.base.jpa.UniEatBaseEntity;
import foorun.unieat.api.model.database.restaurant.entity.RestaurantEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Article 식당정보
 */
@Entity
@Table(name = "article_restaurant")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class ArticleRestaurantEntity extends UniEatBaseEntity {
    /**
     * article ID
     */
    @Id
    @Column(name = "article_id")
    private Long articleId;

    /**
     * 식당 ID
     */
    @Column(name = "article_restaurant_id")
    private Long restaurantId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", referencedColumnName = "article_id")
    private ArticleBaseEntity articleBase;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_restaurant_id", referencedColumnName = "restaurant_id", insertable = false, updatable = false)
    private RestaurantEntity restaurant;
}