package com.jiyun.kaiyuanzhongguo.Dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.jiyun.kaiyuanzhongguo.model.bean.SearchDetail;

import com.jiyun.kaiyuanzhongguo.Dao.SearchDetailDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig searchDetailDaoConfig;

    private final SearchDetailDao searchDetailDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        searchDetailDaoConfig = daoConfigMap.get(SearchDetailDao.class).clone();
        searchDetailDaoConfig.initIdentityScope(type);

        searchDetailDao = new SearchDetailDao(searchDetailDaoConfig, this);

        registerDao(SearchDetail.class, searchDetailDao);
    }
    
    public void clear() {
        searchDetailDaoConfig.clearIdentityScope();
    }

    public SearchDetailDao getSearchDetailDao() {
        return searchDetailDao;
    }

}
