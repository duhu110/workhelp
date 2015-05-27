package com.chinatelecom.ctsi.workhelper.service;

import com.chinatelecom.ctsi.workhelper.R;
import com.chinatelecom.ctsi.workhelper.WHApplication;
import com.chinatelecom.ctsi.workhelper.db.DutyInfoDao;
import com.chinatelecom.ctsi.workhelper.model.DutyInfo;
import com.chinatelecom.ctsi.workhelper.util.TtsManager;

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

public class NoiticeManager {
	public NotificationManager mNotificationManager;
	private final static String TAG = "CustomActivity";
	/** 按钮：显示自定义通知 */
	private Button btn_show_custom;
	/** 按钮：显示自定义带按钮的通知 */
	private Button btn_show_custom_button;
	/** Notification 的ID */
	int notifyId = 101;
	/** NotificationCompat 构造器 */
	NotificationCompat.Builder mBuilder;
	/** 是否在播放 */
	public boolean isPlay = false;
	/** 通知栏按钮广播 */
	public ButtonBroadcastReceiver bReceiver;
	/** 通知栏按钮点击事件对应的ACTION */
	public final static String ACTION_BUTTON = "com.notifications.intent.action.ButtonClick";

	Context context;

	WHApplication application;

	public NoiticeManager(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.application = (WHApplication) context.getApplicationContext();
	}

	public void onCreate() {
		// TODO Auto-generated method stub
		mNotificationManager = (NotificationManager) this.context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		bReceiver = new ButtonBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ACTION_BUTTON);
		context.registerReceiver(bReceiver, intentFilter);
	}

	public void onDestory() {

		if (bReceiver != null) {
			context.unregisterReceiver(bReceiver);
		}
	}

	public void showButtonNotify(String infoid) {
		NotificationCompat.Builder mBuilder = new Builder(this.context);
		RemoteViews mRemoteViews = new RemoteViews(
				this.context.getPackageName(), R.layout.view_custom_button);
		mRemoteViews.setImageViewResource(R.drawable.icon_message,
				R.drawable.icon_message);
		// API3.0 以上的时候显示按钮，否则消失
		// 如果版本号低于（3。0），那么不显示按钮
		if (android.os.Build.VERSION.SDK_INT <= 9) {
			mRemoteViews.setViewVisibility(R.id.ll_custom_button, View.GONE);
		} else {
			mRemoteViews.setViewVisibility(R.id.ll_custom_button, View.VISIBLE);
			//
			if (isPlay) {
				mRemoteViews.setImageViewResource(R.id.btn_custom_play,
						R.drawable.btn_pause);
			} else {
				mRemoteViews.setImageViewResource(R.id.btn_custom_play,
						R.drawable.btn_play);
			}
		}

		// 点击的事件处理
		Intent buttonIntent = new Intent(ACTION_BUTTON);
		// 这里加了广播，所及INTENT的必须用getBroadcast方法
		/* 播放/暂停 按钮 */
		buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_PALY_ID);
		buttonIntent.putExtra(INTENT_DUTYID_TAG, infoid);
		PendingIntent intent_paly = PendingIntent.getBroadcast(context, 2,
				buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		mRemoteViews.setOnClickPendingIntent(R.id.btn_custom_play, intent_paly);
		mBuilder.setContent(mRemoteViews).setContentIntent(

		getDefalutIntent(Notification.FLAG_ONGOING_EVENT))
				.setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
				.setTicker("正在播放").setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
				.setOngoing(true).setSmallIcon(R.drawable.sing_icon);
		Notification notify = mBuilder.build();
		notify.flags = Notification.FLAG_ONGOING_EVENT;
		// 会报错，还在找解决思路
		// notify.contentView = mRemoteViews;
		// notify.contentIntent = PendingIntent.getActivity(this, 0, new
		// Intent(), 0);
		mNotificationManager.notify(200, notify);
	}

	public final static String INTENT_BUTTONID_TAG = "ButtonId";

	public final static String INTENT_DUTYID_TAG = "DutyId";
	/** 上一首 按钮点击 ID */
	public final static int BUTTON_PREV_ID = 1;
	/** 播放/暂停 按钮点击 ID */
	public final static int BUTTON_PALY_ID = 2;
	/** 下一首 按钮点击 ID */
	public final static int BUTTON_NEXT_ID = 3;

	/**
	 * 广播监听按钮点击时间
	 */
	public class ButtonBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			if (action.equals(ACTION_BUTTON)) {
				// 通过传递过来的ID判断按钮点击属性或者通过getResultCode()获得相应点击事件
				int buttonId = intent.getIntExtra(INTENT_BUTTONID_TAG, 0);
				String dutyId = intent.getStringExtra(INTENT_DUTYID_TAG);
				switch (buttonId) {

				case BUTTON_PALY_ID:
					String play_status = "";
					isPlay = !isPlay;
					if (isPlay) {
						play_status = "开始播放";
						DutyInfo info = DutyInfoDao.getRecordById(dutyId);
						TtsManager.getManager(context).playString(
								info.getSpeechString());

					} else {
						play_status = "已暂停";
						TtsManager.getManager(context).stop();
					}
					showButtonNotify(dutyId);
					Log.d(TAG, play_status);
					application.showToast(play_status);

					break;

				default:
					break;
				}
			}
		}
	}

	public PendingIntent getDefalutIntent(int flags) {
		PendingIntent pendingIntent = PendingIntent.getActivity(this.context,
				1, new Intent(), flags);
		return pendingIntent;
	}
}
