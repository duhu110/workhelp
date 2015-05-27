package com.chinatelecom.ctsi.workhelper.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.chinatelecom.ctsi.workhelper.R;
import com.chinatelecom.ctsi.workhelper.db.DutyInfoDao;
import com.chinatelecom.ctsi.workhelper.model.DutyInfo;
import com.ctsi.alarm.SingleAlarmPlan;
import com.ctsi.alarm.service.CtsiAlarmManager;
import com.ctsi.alarm.service.CtsiAlarmService;

public class AlarmService extends CtsiAlarmService {
	public static String Broadcast_alarm = "Broadcast_alarm";

	public static String Broadcast_alarm_add = "Broadcast_alarm_add";

	NoiticeManager notice;

	public static AlarmService ALAM_THIS;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		IntentFilter filIntent = new IntentFilter();
		notice = new NoiticeManager(AlarmService.this);
		notice.onCreate();
		filIntent.addAction(Broadcast_alarm);
		filIntent.addAction(Broadcast_alarm_add);
		registerReceiver(receiver, filIntent);
		ALAM_THIS = this;
		addReminds();
	}

	private void addReminds() {

		List<DutyInfo> dutyInfos = DutyInfoDao.getRecordList(DutyInfo.TYPE_ALL);
		for (DutyInfo info : dutyInfos) {

			long now = new Date().getTime();
			long wake = info.getDeadline();

			if (wake > now) {
				if (info.getType() == DutyInfo.TYPE_TASK) {

				}

				wake = info.getDeadline();
				if (wake < now) {
					wake = now + 10 * 1000;
				}
				SingleAlarmPlan plan;
				try {
					plan = new SingleAlarmPlan(AlarmService.Broadcast_alarm,
							AlarmManager.RTC, String.valueOf(info.getId()),
							wake);
					List<SingleAlarmPlan> plans = new ArrayList<SingleAlarmPlan>();
					plans.add(plan);
					addSinglePlans(plans);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(receiver);
		notice.onDestory();

	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context arg0, Intent in) {
			// TODO Auto-generated method stub
			if (in.getAction().equals(Broadcast_alarm)) {

				String id = in.getStringExtra(CtsiAlarmManager.ALARM_EXTRA);
				// notification or dialog
				Intent intent = new Intent(AlarmService.this,
						Activity_Alert.class);
				intent.putExtra("id", id);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			} else {
				String id = in.getExtras().getString("id");
				DutyInfo info = DutyInfoDao.getRecordById(id);
				long now = new Date().getTime();

				long wakeTime = info.getDeadline();

				if (wakeTime > now) {
					if (info.getType() == DutyInfo.TYPE_TASK) {
						// wakeTime = info.getDeadline() - 5 * 60 * 1000;
						wakeTime = info.getDeadline();
						if (wakeTime < now) {
							wakeTime = now + 10 * 1000;
						}

						SingleAlarmPlan plan;
						try {
							plan = new SingleAlarmPlan(
									AlarmService.Broadcast_alarm,
									AlarmManager.RTC, String.valueOf(info
											.getId()), wakeTime);
							List<SingleAlarmPlan> plans = new ArrayList<SingleAlarmPlan>();
							plans.add(plan);
							addSinglePlans(plans);
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}

				// NOTICE
				notice.showButtonNotify(id);
			}

		}
	};

	public final static String INTENT_BUTTONID_TAG = "ButtonId";
	/** 播放/暂停 按钮点击 ID */
	public final static int BUTTON_PALY_ID = 2;

	public static boolean isWorked(Context context) {
		ActivityManager myManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager
				.getRunningServices(200);
		for (int i = 0; i < runningService.size(); i++) {
			if (runningService.get(i).service
					.getClassName()
					.toString()
					.equals("com.chinatelecom.ctsi.workhelper.service.AlarmService")) {
				return true;
			}
		}
		return false;
	}

}
