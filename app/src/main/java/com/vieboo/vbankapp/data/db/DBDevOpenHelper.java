package com.vieboo.vbankapp.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.vieboo.vbankapp.DaoMaster;
import com.vieboo.vbankapp.UserInfoDao;


/**
 * @author Administrator
 * @date 2019/4/25 0025
 * 数据库升级
 */
public class DBDevOpenHelper extends DaoMaster.OpenHelper {

    public DBDevOpenHelper(Context context, String name) {
        super(context, name);
    }

    public DBDevOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            MigrationHelper.migrate(db , UserInfoDao.class);
        }
        super.onUpgrade(db, oldVersion, newVersion);
    }


}
