package com.valten.service.impl;

import com.valten.service.IArticleRepository;
import com.valten.util.RepositoryName;
import com.valten.vo.ArticleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ArticleRepositoryImpl extends BaseRepositoryImpl<ArticleVo> implements IArticleRepository {

    //索引
    @RepositoryName("articles-1")
    protected String index;

    //类型
    @RepositoryName("article")
    protected String type;

    public ArticleRepositoryImpl() {
        super();
    }

    /**
     * 写一些特殊的方法
     */
}







