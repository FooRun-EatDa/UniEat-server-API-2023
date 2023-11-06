package foorun.unieat.api.model.database.article.entity.primary_key;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class UniEatArticleHashTag implements Serializable {
    private Long articleId;
    private String categoryId;
}
