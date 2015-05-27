package com.chinatelecom.ctsi.workhelper.helper;

import android.content.Intent;

import com.chinatelecom.ctsi.workhelper.WHApplication;
import com.chinatelecom.ctsi.workhelper.MainActivity;
import com.chinatelecom.ctsi.workhelper.db.DutyInfoDao;
import com.chinatelecom.ctsi.workhelper.httpresponse.NewMsgResponseInfo;
import com.chinatelecom.ctsi.workhelper.model.DutyInfo;
import com.chinatelecom.ctsi.workhelper.pushutil.Notify;

public class DutyInfoHelper {
	
	public static NewDutyListener newDutyListener;
	
	/**
	 * 处理任务推送
	 * @param info
	 */
	public static void disposeDutyMessage(NewMsgResponseInfo info) {
		// TODO Auto-generated method stub
		if (info.getDutyInfoList() == null
				|| info.getDutyInfoList().size() == 0) {
			return;
		}
		try {
			DutyInfoDao.getInstance().sqliteDB().beginTransaction();
			for (DutyInfo d : info.getDutyInfoList()) {
				DutyInfoDao.save(d);
			}
			DutyInfoDao.getInstance().sqliteDB().setTransactionSuccessful();
		} catch (Exception e) {
			DutyInfoDao.getInstance().sqliteDB().endTransaction();
			e.printStackTrace();
		}

		to_notify(info);
	}
	/**
	 * 发布通知
	 * 包括发布通知提醒
	 * 和通知监听器
	 * @param info
	 */
	private static void to_notify(NewMsgResponseInfo info) {
		
		Intent intent = new Intent(WHApplication.getInstance(),MainActivity.class);
		
		Notify.notifcation(WHApplication.getInstance(), "type"+info.getType(), intent,"新任务");
		
		
		
	}
	
	public interface NewDutyListener{
		public void OnNewDuty(DutyInfo info);
		
	}
}
