package com.chinatelecom.ctsi.workhelper.db;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;

import com.chinatelecom.ctsi.workhelper.model.DutyInfo;
import com.chinatelecom.ctsi.workhelper.model.SharedDuty;
import com.chinatelecom.ctsi.workhelper.util.JsonUtil;

public class SharedInfoDao extends AbstractSQLManager {

	private static final String TABLE_NAME = AbstractSQLManager.TABLES_NAME_SHARE_INFO;

	private static final String[] fileds = new String[] { ShareColumn.FILED_ID,
			ShareColumn.FILED_SHARETIME, ShareColumn.FILED_JSON };

	private static final String lock = "";
	private static SharedInfoDao instance = null;

	private SharedInfoDao() {

	}

	public static SharedInfoDao getInstance() {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					instance = new SharedInfoDao();
				}
			}
		}
		return instance;
	}

	private static String getTableName(boolean isTempTable) {

		return TABLE_NAME;

	}

	private static final String update_sql = "replace into "
			+ getTableName(false) + "( " + ShareColumn.FILED_ID + ","
			+ ShareColumn.FILED_SHARETIME + "," + ShareColumn.FILED_JSON
			+ ") values (?,?,?)";

	/**
	 * 保存
	 * 
	 * @param d
	 */
	public static void save(SharedDuty d) {

		getInstance().sqliteDB().execSQL(update_sql,
				new String[] { ""+d.getId(), String.valueOf(d.getSharedTime()), JsonUtil.toJsonStr(d) });

	}

	/**
	 * 删除一条记录
	 * 
	 * @param dutyId
	 * @return
	 */
	public static int deleteRecordById(String dutyId) {
		return getInstance().sqliteDB().delete(getTableName(false), "id=?",
				new String[] { dutyId });
	}

	/**
	 * 获取一条记录
	 * 
	 * @return
	 */
	public static SharedDuty getRecordById(String departmentId) {
		Cursor cursor = getInstance().sqliteDB().query(getTableName(false),
				fileds, "id=?", new String[] { departmentId }, null, null,
				null, null);
		SharedDuty d = null;
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				d = getValues(cursor);
				
			}
			cursor.close();
		}
		return d;
	}

	/**
	 * 获取列表
	 * 
	 * @return
	 */
	public static List<DutyInfo> getRecordList() {

		List<DutyInfo> list = new ArrayList<DutyInfo>();
		Cursor cursor = getInstance().sqliteDB().query(getTableName(false),
				fileds, null, null, null, null, "time desc ", null);
		DutyInfo d = null;
		if (cursor != null) {
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
					.moveToNext()) {
				//d = getValues(cursor);
				list.add(d);
			}
			cursor.close();
		}

		return list;
	}

	/**
	 * 设置参数
	 * 
	 * @param dInfo
	 * @return
	 */
//	private static ContentValues setValues(DutyInfo dInfo) {
//		ContentValues values = new ContentValues();
//		values.put(DutyColumn.FILED_ID, dInfo.getId());
//		values.put(DutyColumn.FILED_TITLE, dInfo.getContent());
//		values.put(DutyColumn.FILED_CONTENT, dInfo.getRemark());
//		values.put(DutyColumn.FILED_DEADLINE, dInfo.getCreateUserId());
//		values.put(DutyColumn.FILED_EXECUTER_ID, dInfo.getCreateUserId());
//		values.put(DutyColumn.FILED_CREATE_USER_ID, dInfo.getBeginTime());
//		values.put(DutyColumn.FILED_STATUS, dInfo.getEndTime());
//
//		return values;
//	}
//
//	/**
//	 * 读取cursor
//	 * 
//	 * @param cursor
//	 * @return
//	 */
//
	private static SharedDuty getValues(Cursor cursor) {
		SharedDuty d = new SharedDuty();
		
		String json = cursor.getString(cursor.getColumnIndex(ShareColumn.FILED_JSON));
		d = JsonUtil.readJsonFromString(json, SharedDuty.class);
		d.setId(cursor.getInt(cursor.getColumnIndex(DutyColumn.FILED_ID)));
		

		return d;
	}

}
