package com.chinatelecom.ctsi.workhelper.pushutil;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

//import com.example.pushtest.broadcast.PushServiceReceiver;
//import com.example.pushtest.mqtt.service.MqttAndroidClient;
//import com.example.pushtest.util.push.Connection;
//import com.example.pushtest.util.push.Connections;
//import com.example.pushtest.util.push.PushUtil;




import com.chinatelecom.ctsi.workhelper.WHApplication;
import com.chinatelecom.ctsi.workhelper.R;
import com.chinatelecom.ctsi.workhelper.push.service.MqttAndroidClient;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class PushStatusActivity extends Activity {
	private static final String TAG = "PushStatusActivity";
	private Button buttonBind;
	private Button buttonUnbind;
	private Button buttonSubscrib;
	private Button buttonRefresh;
	private EditText textViewTopic,textViewContent;
	private Button buttonSend;
	private TextView textView;
	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pushstatus);
		context = this;
		buttonBind = (Button)findViewById(R.id.button1);
		buttonBind.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PushUtil.initPush(PushStatusActivity.this);
				
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(serviceIsRunning()) {
							buttonBind.setText("已启动");
						}else {
							buttonBind.setText("未启动");
						}
					}}, 2000);
			}
		});
		
		if(serviceIsRunning()) {
			buttonBind.setText("推送服务已启动");
		}else {
			buttonBind.setText("推送服务未启动");
		}
		
		
		buttonUnbind = (Button) findViewById(R.id.button3);
		buttonUnbind.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//PushUtil.closePush(MainActivity.this);
				addSchecduleTask();
			}});
		buttonSubscrib =  (Button)findViewById(R.id.button2);
		buttonSubscrib.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PushUtil.subscribe(PushStatusActivity.this,PushUtil.TOPIC);
			}
		});
		
		
		textView = (TextView)findViewById(R.id.text);
		buttonRefresh = (Button)findViewById(R.id.refresh);
		buttonRefresh.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String s = "服务状态：";
				if(serviceIsRunning()) {
					s = s+"已启动";
					buttonBind.setText("推送服务已启动");
				}else {
					buttonBind.setText("推送服务未启动");
					s = s+"未启动";
				}
				s=s+"\n";
				
				s += "连接状态：";
				String handle = PushUtil.getHandle(context);
				Connection connection = Connections.getInstance(context).getConnection(handle);
				if(connection==null) {
					s = s+"null";
					//Connections.getInstance(context).
					
				}else if( connection.isConnected()) {
					s = s+"已连接";
				}else {
					s = s+"未连接";
				}
				s=s+"\n";
				if(connection!=null) {
				MqttAndroidClient client = connection.getClient();
				s += "Client状态：";
				if(client==null) {
					s = s+"null";
					//Connections.getInstance(context).
				}else if( client.isConnected()) {
					s = s+"已连接";
				}else {
					s = s+"未连接";
				}
				s=s+"\n";
				}
				
				if(connection!=null) {
					boolean isSuc = connection.isSubscribeSuccess();
					s += "订阅状态状态：";
					if(!isSuc) {
						s = s+"订阅失败";
					}else {
						s = s+"订阅成功";
					}
					s=s+"\n";
				}
				
				
				textView.setText(s);
			}});
		
		
		
		
		  textViewTopic = (EditText)findViewById(R.id.editText_topic);
		  textViewContent= (EditText)findViewById(R.id.editText_content);
		  buttonSend= (Button)findViewById(R.id.buttonSend);
		  
		  buttonSend.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!Connections.getInstance(context).getConnection(PushUtil.getHandle(context)).isConnected()) {
					Toast.makeText(context, "客户端未连接", Toast.LENGTH_LONG).show();
					return;
				}
				
				String topic = textViewTopic.getText().toString().trim();
				String message = textViewContent.getText().toString().trim();
				
				PushUtil.publish(context,topic,message);
			}
		});
		  
		  
	}
	
	/**
	 * 增加定时任务
	 */
	private void addSchecduleTask() {
		try {

			// TODO Auto-generated method stub
			// 创建Intent对象，action为ELITOR_CLOCK，附加信息为字符串“你该打酱油了”
			Intent intent = new Intent(WHApplication.ACTION_SCHEDULE_TASK);
			// intent.putExtra("msg","你该打酱油了");

			// 定义一个PendingIntent对象，PendingIntent.getBroadcast包含了sendBroadcast的动作。
			// 也就是发送了action 为"ELITOR_CLOCK"的intent
			PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, 0);

			// AlarmManager对象,注意这里并不是new一个对象，Alarmmanager为系统级服务
			AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
			// 确保不用重复
			am.cancel(pi);
			// 设置闹钟从当前时间开始，每隔5s执行一次PendingIntent对象pi，注意第一个参数与第二个参数的关系
			// n秒后通过PendingIntent pi对象发送广播
			/*am.setRepeating(AlarmManager.RTC_WAKEUP,
					System.currentTimeMillis(), GlobalUtil.NOTICE_SYNC_DEP * 1000, pi);*/
			long currTime = System.currentTimeMillis();
			
				am.setInexactRepeating(AlarmManager.RTC_WAKEUP,
						System.currentTimeMillis(), 10 * 1000, pi);
			
			
			Log.d(TAG, "定时任务启动成功");
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "定时任务异常");
		}
	}
	
	 private boolean serviceIsRunning() {
	        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
	        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	            if (PushUtil.SERVICE_NAME.equals(service.service.getClassName())) {
	                return true;
	            }
	        }
	        return false;
	    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

	
}
