package com.portfolio.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPortfolioImg is a Querydsl query type for PortfolioImg
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPortfolioImg extends EntityPathBase<PortfolioImg> {

    private static final long serialVersionUID = 410357843L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPortfolioImg portfolioImg = new QPortfolioImg("portfolioImg");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgName = createString("imgName");

    public final StringPath imgUrl = createString("imgUrl");

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final StringPath oriImgName = createString("oriImgName");

    public final QPortfolio portfolio;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regTime = _super.regTime;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public QPortfolioImg(String variable) {
        this(PortfolioImg.class, forVariable(variable), INITS);
    }

    public QPortfolioImg(Path<? extends PortfolioImg> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPortfolioImg(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPortfolioImg(PathMetadata metadata, PathInits inits) {
        this(PortfolioImg.class, metadata, inits);
    }

    public QPortfolioImg(Class<? extends PortfolioImg> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.portfolio = inits.isInitialized("portfolio") ? new QPortfolio(forProperty("portfolio"), inits.get("portfolio")) : null;
    }

}

