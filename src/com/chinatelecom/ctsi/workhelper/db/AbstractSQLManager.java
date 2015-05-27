package com.chinatelecom.ctsi.workhelper.db;

import com.chinatelecom.ctsi.workhelper.WHApplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



/**
 * 数据库访问接口
 *
 * @author
 *
 * @date 2014-12-11
 */
public abstract class AbstractSQLManager {

    public static final String TAG = AbstractSQLManager.class.getName();
    private int databaseVersionCode =1;
    private static DatabaseHelper databaseHelper;
    private static SQLiteDatabase sqliteDB;
    
    public static final String TABLES_NAME_USER_INFO = "tb_user_info";
    public static final String TABLES_NAME_DUTY_INFO = "tb_duty_info";
    public static final String TABLES_NAME_SHARE_INFO = "tb_share_info";
    
    
    public AbstractSQLManager() {
        openDatabase(WHApplication.getInstance(), databaseVersionCode);
    }

    private void openDatabase(Context context, int databaseVersion) {
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper(context, this, databaseVersion);
        }
        if (sqliteDB == null) {
            sqliteDB = databaseHelper.getWritableDatabase();

        }
    }

    public void destroy() {
        try {
            if (databaseHelper != null) {
                databaseHelper.close();
            }
            if (sqliteDB != null) {
                sqliteDB.close();
            }
        } catch (Exception e) {
        }
    }

    private void open(boolean isReadonly) {
        if (sqliteDB == null) {
            if (isReadonly) {
                sqliteDB = databaseHelper.getReadableDatabase();
            } else {
                sqliteDB = databaseHelper.getWritableDatabase();
            }
        }
    }

    public final void reopen() {
        closeDB();
        open(false);
    }

    private void closeDB() {
        if (sqliteDB != null) {
            sqliteDB.close();
            sqliteDB = null;
        }
    }

    public final SQLiteDatabase sqliteDB() {
        open(false);
        return sqliteDB;
    }

    /**
     * 创建基础表结构
     */
    static class DatabaseHelper extends SQLiteOpenHelper {

        /**
         * 数据库名称
         */
        static final String DATABASE_NAME = "wqzs_msg.db";
        static final String DESC = "DESC";
        static final String ASC = "ASC";

      
        //消息记录表
        public static final String TABLES_NAME_IM_MESSAGE = "im_message";

        //联系人表
        public static final String TABLES_NAME_CONTACT = "contacts";
       
        private AbstractSQLManager mAbstractSQLManager;

        public DatabaseHelper(Context context, AbstractSQLManager manager, int version) {
           // this(context, manager, CCPAppManager.getPersonInfo().getPersonId() + "_" + DATABASE_NAME, null, version);
            this(context, manager, DATABASE_NAME, null, version);
        }

        public DatabaseHelper(Context context, AbstractSQLManager manager, String name,
                              CursorFactory factory, int version) {
            super(context, name, factory, version);
            mAbstractSQLManager = manager;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            createTables(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            
            dropTables(db);
            createTables(db);
        }

        /**
         * @param db
         */
        private void createTables(SQLiteDatabase db) {



          
            // 创建部门表
            createTableForDuty(db);

           
        }




        public static final String[] DROP_TABLES_V0 = new String[] {        
                "drop index if exists "+TABLES_NAME_DUTY_INFO,
                "drop index if exists "+TABLES_NAME_SHARE_INFO
        };
        private void dropTables(SQLiteDatabase db) {
            // 联系人表
                for (int i = 0; i < DROP_TABLES_V0.length; ++i) {
                    Log.d("删除", DROP_TABLES_V0[i]);
                    db.execSQL(DROP_TABLES_V0[i]);
                }
        }
        
        /**
         * IM消息表
         *
         * @param db
         */
        void createTableForDuty(SQLiteDatabase db) {
        	
            String sql_duty = "CREATE TABLE IF NOT EXISTS "
                    + TABLES_NAME_DUTY_INFO
                    + " ("
                    + DutyColumn.FILED_ID + " TEXT UNIQUE ON CONFLICT ABORT, "
                    + DutyColumn.FILED_TITLE + " TEXT, "
                    + DutyColumn.FILED_CONTENT + " TEXT, "
                    + DutyColumn.FILED_DEADLINE + " TEXT, "
                    + DutyColumn.FILED_EXECUTER_ID + " TEXT, "
                    + DutyColumn.FILED_CREATE_USER_ID + " TEXT, "
                    + DutyColumn.FILED_STATUS + " TEXT, "
                    + DutyColumn.FILED_TYPE + " TEXT "
                    + ")";
            
            
            String sql_share = "CREATE TABLE IF NOT EXISTS "
                    + TABLES_NAME_SHARE_INFO
                    + " ("
                    + ShareColumn.FILED_ID + " TEXT UNIQUE ON CONFLICT ABORT, "
                    + ShareColumn.FILED_SHARETIME + " TEXT, "
                    + ShareColumn.FILED_JSON + " TEXT"
                    + ")";
            
            db.execSQL(sql_duty);
            db.execSQL(sql_share);

        }

       

        


        private void createSettingInfoTable(SQLiteDatabase db) {
            String CREATE_TABLE_SETTING =
                    "CREATE TABLE  if not exists  [setting_info] ( [key] VARCHAR2(100) NOT NULL, [value] NTEXT ,  CONSTRAINT [] " +
                            " PRIMARY KEY ([key]) ON CONFLICT REPLACE);";
            //LogUtil.v(TAG + ":" + CREATE_TABLE_SETTING);
            db.execSQL(CREATE_TABLE_SETTING);
        }


    }



    class DutyColumn{
        public static final String FILED_ID = "id";
        public static final String FILED_TITLE = "title";
        public static final String FILED_CONTENT = "content";
        public static final String FILED_DEADLINE = "deadline";
        public static final String FILED_EXECUTER_ID = "executerId";
        public static final String FILED_CREATE_USER_ID = "createUserId";
        public static final String FILED_STATUS = "status";
        public static final String FILED_TYPE = "type";
    }
    
    
    class ShareColumn{
        public static final String FILED_ID = "id";
        public static final String FILED_SHARETIME="time";
        public static final String FILED_JSON = "json";	
    }




    protected void release() {
        destroy();
        closeDB();
        databaseHelper = null;
    }
}
