package foorun.unieat.api.service.article.impl;

import foorun.unieat.api.model.database.article.entity.ArticleBaseEntity;
import foorun.unieat.api.model.domain.article.request.ArticlePost;
import foorun.unieat.api.service.article.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    @Override
    public List<ArticleBaseEntity> getArticles(String search) {
        return null;
    }

    @Override
    public ArticleBaseEntity getArticle(Long articleId) {
        return null;
    }

    @Override
    public void addArticle(ArticlePost articlePost) {

    }

    @Override
    public void modifyArticle(Long articleId, ArticlePost articlePost) {

    }

    @Override
    public void removeArticle(Long articleId) {

    }
}