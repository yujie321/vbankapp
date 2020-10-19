package com.vieboo.vbankapp.data.db;


import com.vieboo.vbankapp.DaoSession;
import com.vieboo.vbankapp.UserInfoDao;

/**
 * DB帮助类
 *
 * @author Administrator
 * @date 2019/4/25
 */
public class DBHelper {

    private DaoSession daoSession;
    private static volatile DBHelper mInstance;

    private DBHelper() {
        daoSession = DBManager.getInstance().getDaoSession();
    }

    public static DBHelper getInstance() {
        if (mInstance == null) {
            synchronized (DBManager.class) {
                if (mInstance == null) {
                    mInstance = new DBHelper();
                }
            }
        }
        return mInstance;
    }

    /**
     * 用户表
     *
     * @return userInfoDao
     */
    public UserInfoDao getUserInfoDao() {
        return daoSession.getUserInfoDao();
    }


}
