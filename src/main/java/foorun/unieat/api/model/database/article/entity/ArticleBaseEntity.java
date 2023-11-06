package foorun.unieat.api.model.database.article.entity;

import foorun.unieat.api.model.base.jpa.UniEatBaseTimeEntity;
import foorun.unieat.api.model.database.member.entity.UniEatMemberMyPageEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

/**
 * Article 기본정보
 */
@Entity
@Table(name = "article_base")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class ArticleBaseEntity extends UniEatBaseTimeEntity {
    /**
     * article ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id", updatable = false)
    private Long articleId;

    /**
     * 제목
     */
    @Column(name = "title", length = 100)
    private String title;

    /**
     * 내용
     */
    @Column(name = "contents", columnDefinition = "LONGTEXT")
    private String contents;

    /**
     * 회원 접속경로
     */
    @Column(name = "writer_member_provider")
    private String provider;

    /**
     * 회원 ID
     */
    @Column(name = "writer_member_id")
    private String memberId;

    /**
     * 회원 마이페이지 연결 ID
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "writer_member_provider", referencedColumnName = "member_provider", insertable = false, updatable = false),
            @JoinColumn(name = "writer_member_id", referencedColumnName = "member_id", insertable = false, updatable = false)
    })
    private UniEatMemberMyPageEntity myPage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", referencedColumnName = "article_id")
    private ArticleRestaurantEntity articleRestaurant;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", referencedColumnName = "article_id")
    private List<ArticlePicturesEntity> articlePictures;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", referencedColumnName = "article_id")
    private List<ArticleHashTagEntity> articleHashTag;
}