package com.vieboo.vbankapp.data.db;

import android.database.sqlite.SQLiteDatabase;

import com.vieboo.vbankapp.DaoMaster;
import com.vieboo.vbankapp.DaoSession;
import com.vieboo.vbankapp.application.VBankAppApplication;


/**
 * @author Administrator
 * @date 2019/4/25 0025
 * <p>
 * 数据库管理
 */
public class DBManager {
    private static final String DB_NAME = "VBankApp-db";
    private static volatile DBManager mInstance;
    /**
     * 以一定的模式管理Dao类的数据库对象
     */
    private DaoMaster mDaoMaster;
    /**
     * 管理指定模式下的所有可用 Dao对象
     */
    private DaoSession mDaoSession;

    /**
     * Database
     */
    private SQLiteDatabase mDb;

    private DBManager() {
//        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(VBankAppApplication.getInstance().getApplicationContext(), DB_NAME, null);
        DBDevOpenHelper devOpenHelper = new DBDevOpenHelper(VBankAppApplication.getInstance().getApplicationContext(), DB_NAME, null);
        mDb = devOpenHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(mDb);
        mDaoSession = mDaoMaster.newSession();
    }

    /**
     * @return DBManager
     */
    public static DBManager getInstance() {
        if (mInstance == null) {
            mInstance = new DBManager();
        }
        return mInstance;
    }

    public DaoMaster getDaoMaster() {
        return mDaoMaster;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getWritableDatabase() {
        return mDb;
    }
}
