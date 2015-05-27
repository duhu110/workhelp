package com.chinatelecom.ctsi.workhelper.ztest;

import com.chinatelecom.ctsi.workhelper.R;
import com.chinatelecom.ctsi.workhelper.protocol.PostMessageThread;
import com.chinatelecom.ctsi.workhelper.protocol.base.BaseListener;
import com.chinatelecom.ctsi.workhelper.service.NoiticeManager;
import com.chinatelecom.ctsi.workhelper.util.TtsManager;
import com.ctsi.logs.Logcat;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

public class Activty_Test extends Activity {

	Handler handler = new Handler();
	NoiticeManager manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		manager = new NoiticeManager(this);
		manager.onCreate();
		manager.showButtonNotify("1");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		manager.onDestory();
	}

	/**
	 * 带按钮的通知栏
	 */

}
