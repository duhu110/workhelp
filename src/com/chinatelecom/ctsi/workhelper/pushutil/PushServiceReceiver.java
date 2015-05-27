package com.chinatelecom.ctsi.workhelper.pushutil;

//import com.example.pushtest.util.push.Connections;
//import com.example.pushtest.util.push.MLog;
//import com.example.pushtest.util.push.PushUtil;

import com.chinatelecom.ctsi.workhelper.WHApplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


public class PushServiceReceiver extends BroadcastReceiver {
	private static final String TAG = "PushServiceReceiver";

	private static final String ACTION_BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";
	private static Context context;
	
	
	public static final int TYPE_SCHEDULE =1;
	public static final int TYPE_NETCHANGE =2;
	
	@Override
	public void onReceive(final Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onReceive"+intent.getAction());
		String action = intent.getAction();
		boolean send = true;
		int type = 0;
		if(ACTION_BOOT_COMPLETED.equalsIgnoreCase(action)) {
			//PushUtil.initPush(context.getApplicationContext());
			send = true;
		}else if(WHApplication.ACTION_SCHEDULE_TASK.equalsIgnoreCase(action)) {
			//PushUtil.initPush(context.getApplicationContext());
			MLog.log("PushServiceReceiver", WHApplication.ACTION_SCHEDULE_TASK);
			type = TYPE_SCHEDULE;
			send = true;
		}else if("android.net.conn.CONNECTIVITY_CHANGE".equalsIgnoreCase(action)) {
			//PushUtil.initPush(context.getApplicationContext());
			type = TYPE_NETCHANGE;
			send = true;
		}
		if(send) {
			handler.removeMessages(3);
			handler.sendMessageDelayed(handler.obtainMessage(3, type, 0), 1500L);
			this.context = context;
		}
		//Notify.notifcation(context, "新通知", intent, "PR"+intent.getAction());
	}
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			
			PushUtil.initPush(context.getApplicationContext(),msg.arg1);
			// Toast.makeText(context, "up", Toast.LENGTH_SHORT).show();
		}
	};
}
