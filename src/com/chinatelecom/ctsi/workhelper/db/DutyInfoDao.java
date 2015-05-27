package com.chinatelecom.ctsi.workhelper.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;

import com.chinatelecom.ctsi.workhelper.model.DutyInfo;

public class DutyInfoDao extends AbstractSQLManager {

	private static final String TABLE_NAME = AbstractSQLManager.TABLES_NAME_DUTY_INFO;

	private static final String[] fileds = new String[] { DutyColumn.FILED_ID,
			DutyColumn.FILED_TITLE, DutyColumn.FILED_CONTENT,
			DutyColumn.FILED_DEADLINE, DutyColumn.FILED_EXECUTER_ID,
			DutyColumn.FILED_CREATE_USER_ID, DutyColumn.FILED_STATUS,
			DutyColumn.FILED_TYPE };

	private static final String lock = "";
	private static DutyInfoDao instance = null;

	private DutyInfoDao() {

	}

	public static DutyInfoDao getInstance() {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					instance = new DutyInfoDao();
				}
			}
		}
		return instance;
	}

	private static String getTableName(boolean isTempTable) {

		return TABLE_NAME;

	}

	private static final String update_sql = "replace into "
			+ getTableName(false) + "( " + DutyColumn.FILED_ID + ","
			+ DutyColumn.FILED_TITLE + "," + DutyColumn.FILED_CONTENT + ","
			+ DutyColumn.FILED_DEADLINE + "," + DutyColumn.FILED_EXECUTER_ID
			+ "," + DutyColumn.FILED_CREATE_USER_ID + ","
			+ DutyColumn.FILED_STATUS + "," + DutyColumn.FILED_TYPE
			+ ") values (?,?,?,?,?, ?,?,?)";

	/**
	 * 保存
	 * 
	 * @param d
	 */
	public static void save(DutyInfo d) {
		getInstance().sqliteDB().execSQL(
				update_sql,
				new String[] { "" + d.getId(), d.getTitle(), d.getContent(),
						"" + d.getDeadline(), "" + d.getExcutorId(),
						"" + d.getCreateUserId(), "" + d.getStatus(),
						"" + d.getType() });

	}
	public static void setUnfinish() {
		String sql ="update "+getTableName(false)+" set status = "+DutyInfo.STATUS_UNFINISHED;
		getInstance().sqliteDB().execSQL(
				sql,
				new String[]{});

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
	public static DutyInfo getRecordById(String departmentId) {
		Cursor cursor = getInstance().sqliteDB().query(getTableName(false),
				fileds, "id=?", new String[] { departmentId }, null, null,
				null, null);
		DutyInfo d = null;
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
	public static List<DutyInfo> getRecordList(int type) {

		List<DutyInfo> list = new ArrayList<DutyInfo>();
		Cursor cursor = null;
		if(true || type==0){
			cursor = getInstance().sqliteDB().query(getTableName(false),
					fileds,null,null, null, null, "status asc, deadline desc", null);
		}else{
			cursor = getInstance().sqliteDB().query(getTableName(false),
					fileds, "type=?", new String[] { ""+type }, null, null, "status asc, deadline desc", null);
		}
			DutyInfo d = null;
			if (cursor != null) {
				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
						.moveToNext()) {
					d = getValues(cursor);
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
	private static ContentValues setValues(DutyInfo dInfo) {
		ContentValues values = new ContentValues();
		values.put(DutyColumn.FILED_ID, dInfo.getId());
		values.put(DutyColumn.FILED_TITLE, dInfo.getTitle());
		values.put(DutyColumn.FILED_CONTENT, dInfo.getContent());
		values.put(DutyColumn.FILED_DEADLINE, dInfo.getCreateUserId());
		values.put(DutyColumn.FILED_EXECUTER_ID, dInfo.getExcutorId());
		values.put(DutyColumn.FILED_CREATE_USER_ID, dInfo.getCreateUserId());
		values.put(DutyColumn.FILED_STATUS, dInfo.getStatus());

		return values;
	}

	/**
	 * 读取cursor
	 * 
	 * @param cursor
	 * @return
	 */

	private static DutyInfo getValues(Cursor cursor) {
		DutyInfo d = new DutyInfo();
		d.setId(cursor.getInt(cursor.getColumnIndex(DutyColumn.FILED_ID)));
		d.setTitle(cursor.getString(cursor
				.getColumnIndex(DutyColumn.FILED_TITLE)));
		d.setContent(cursor.getString(cursor
				.getColumnIndex(DutyColumn.FILED_CONTENT)));
		d.setDeadline(cursor.getLong(cursor
				.getColumnIndex(DutyColumn.FILED_DEADLINE)));

		d.setExcutorId(
				+ cursor.getInt(cursor
						.getColumnIndex(DutyColumn.FILED_EXECUTER_ID)));
		d.setCreateUserId(
				 cursor.getInt(cursor
						.getColumnIndex(DutyColumn.FILED_CREATE_USER_ID)));

		d.setType(cursor.getInt(cursor
				.getColumnIndex(DutyColumn.FILED_EXECUTER_ID)));
		d.setStatus(cursor.getInt(cursor
				.getColumnIndex(DutyColumn.FILED_STATUS)));
		return d;
	}

}
