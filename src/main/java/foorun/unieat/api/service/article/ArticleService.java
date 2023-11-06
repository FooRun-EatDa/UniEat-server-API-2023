package foorun.unieat.api.service.article;

import foorun.unieat.api.model.database.article.entity.ArticleBaseEntity;
import foorun.unieat.api.model.domain.article.request.ArticlePost;

import java.util.List;

public interface ArticleService {
    List<ArticleBaseEntity> getArticles(String search);
    ArticleBaseEntity getArticle(Long articleId);
    void addArticle(ArticlePost articlePost);
    void modifyArticle(Long articleId, ArticlePost articlePost);
    void removeArticle(Long articleId);
}