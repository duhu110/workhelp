package com.chinatelecom.ctsi.workhelper.pushutil;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import com.chinatelecom.ctsi.workhelper.WHApplication;
import com.chinatelecom.ctsi.workhelper.push.service.MqttAndroidClient;
import com.chinatelecom.ctsi.workhelper.push.service.MqttService;
import com.chinatelecom.ctsi.workhelper.util.SystemUtil;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.Preference;
import android.util.Log;
import android.widget.Toast;


//import com.example.pushtest.PreferenceUtil;
//import com.example.pushtest.SystemUtil;
//import com.example.pushtest.broadcast.PushServiceReceiver;
//import com.example.pushtest.mqtt.service.MqttAndroidClient;
//import com.example.pushtest.mqtt.service.MqttService;
//import com.example.pushtest.util.push.Connection.ConnectionStatus;

public class PushUtil {
	private static final String TAG = "PushUtil";
	public static final String ip = "222.74.224.150";// 172.19.89.130
	public static final int port = 8009;// 61613 1883
	public static final String BROKER_URL = "tcp://" + ip + ":" + port;
	public static final String PREFERENCE_KEY = "push_setting";
	// public static final String BROKER_URL = "tcp://222.74.224.150:8009";
	// public static final String BROKER_URL = "tcp://test.mosquitto.org:1883";
    public static final String SERVICE_NAME = "com.chinatelecom.ctsi.workhelper.push.service.MqttService";

	public static final String TOPIC = "wqzs";
	private MqttClient mqttClient;

	private static MqttConnectOptions options;
	private static final String userName = "admin";
	// private String passWord = "nmgtele!QAZ";
	private static final String password = "password";

	private static MqttAndroidClient client;
    //闹钟间隔
	public static final int seconds = 300;
    //心跳间隔
    public static final  int keepaliveeconds = 280;
	// private static Connection connection;
	public static void initPush(Context context, int type) {
		// TODO Auto-generated method stub
		if (type == PushServiceReceiver.TYPE_NETCHANGE) {
			if (isOnline(context)) {
				initPush(context);
			}else {
				Connection connection = Connections.getInstance(context)
						.getConnection(getHandle(context));
                if (connection !=null) {
                    connection.changeConnectionStatus(Connection.ConnectionStatus.DISCONNECTED);
                }else{
                    Log.d(TAG,"connection is null");
                }
			}
		} else if (type == PushServiceReceiver.TYPE_SCHEDULE)  {
			//PushUtil.addAlarmTask(context);
			
			initPush(context);
		}else{// if (type == PushServiceReceiver.TYPE_SCHEDULE) 
			initPush(context);
		}
	}
	
	public static String getClientId(Context context) {
		return SystemUtil.getPhoneInfo(context).getDeviceId();
	}
	
	public static void initPush(Context context) {
		PushUtil.addAlarmTask(context);
		
		Connection connection = Connections.getInstance(context)
				.getConnection(getHandle(context));
		if(connection == null || connection.getClient()==null) {
			if(connection!=null) {
				Connections.getInstance(context).removeConnection(
						connection);
			}
			MqttAndroidClient client = new MqttAndroidClient(context,
					BROKER_URL, getClientId(context));
			connection = new Connection(getHandle(context), getClientId(context), ip, port,
					context, client, false);
			connection.addConnectionOptions(getOptions());
			//if(callbackHandler==null) {
			callbackHandler = new MqttCallbackHandler(context, getHandle(context));
			//}
			// 设置回调
			client.setCallback(callbackHandler);

			// set traceCallback 日志回调吧
			//if(traceCallback==null) {
			traceCallback = new MqttTraceCallback();
			//}
			client.setTraceCallback(traceCallback);

			Connections.getInstance(context).addConnection(connection);
		}
		client = connection.getClient();
		synchronized (connection) {
			//两个状态可能不一致
			if(connection.isConnectedOrConnecting() && connection.getClient().isConnected()) {
				return;
			}
			//connection.getClient().connect()
			connection.changeConnectionStatus(Connection.ConnectionStatus.CONNECTING);
			connectAction(context);
		}
		
		/* if(!connection.getClient().isConnected()) {
			try {
				client.connect();
			} catch (MqttException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		*/
		
		
	}
	

	public static boolean isOnline(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isAvailable()
				&& cm.getActiveNetworkInfo().isConnected()) {// cm.getActiveNetworkInfo().isConnectedOrConnecting()
			return true;
		}

		return false;
	}
	public static String getPreMessage(Context context) {
	
		SharedPreferences sp = context.getSharedPreferences(PREFERENCE_KEY, 0);
		String time = sp.getString("PreMessage", "");
		return time;
	}
	public static void setPreMessage(Context context,String message) {
		SharedPreferences sp = context.getSharedPreferences(PREFERENCE_KEY, 0);
		sp.edit().putString("PreMessage", message).commit();
	}
	
	
	
	public static long getPreAlarmTime(Context context) {
		long time = 0;
		SharedPreferences sp = context.getSharedPreferences(PREFERENCE_KEY, 0);
		time = sp.getLong("PreAlarmTime", 0);
		return time;
	}
	public static void setPreAlarmTime(Context context,long time) {
		SharedPreferences sp = context.getSharedPreferences(PREFERENCE_KEY, 0);
		sp.edit().putLong("PreAlarmTime", time).commit();
	}
	public static int timess = 0;
	public static void addAlarmTask(Context context) {
		long currTime = System.currentTimeMillis();
		if (currTime - getPreAlarmTime(context) < seconds * 1000-2000) {
			Log.d(TAG, "定时任务太频繁，取消");
            if( timess>2){
                timess = 0;
            }
            timess ++;
			return;
		}
		long ct = System.currentTimeMillis(); // get current time
		AlarmManager mgr = (AlarmManager) context.getApplicationContext()
				.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context.getApplicationContext(),
				MqttService.class);
		i.putExtra("from", "1");
		//i.addFlags(1000153);
		i.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
		PendingIntent pi = PendingIntent.getService(
				context.getApplicationContext(), 0, i, 0);
		
		// 创建Intent对象，action为ELITOR_CLOCK，附加信息为字符串“你该打酱油了”
		Intent intent = new Intent(WHApplication.ACTION_SCHEDULE_TASK);
		// intent.putExtra("msg","你该打酱油了");

		// 定义一个PendingIntent对象，PendingIntent.getBroadcast包含了sendBroadcast的动作。
		// 也就是发送了action 为"ELITOR_CLOCK"的intent
		PendingIntent pIntent = PendingIntent
				.getBroadcast(context, 0, intent, 0);
		intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);

		mgr.set(AlarmManager.RTC_WAKEUP, ct + seconds * 1000, pIntent);
		//mgr.set(AlarmManager.RTC_WAKEUP, ct + seconds * 1000, pi); // 60 seconds
																	// is 60000
																	// milliseconds
		Log.d("Alarm", "创建定时任务");
		//preCallTime = currTime;
		setPreAlarmTime(context,currTime) ;
	}

	/**
	 * 增加定时任务
	 */
	//private static long 1preCallTime = 0;



	public static void closePush(Context context) {
		Connection connection = Connections.getInstance(context).getConnection(
				getHandle(context));
		if (connection != null && connection.getClient() != null) {
			try {
				connection.getClient().disconnect();
				connection = null;
			} catch (MqttException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static String getHandle(Context context) {
		String uri = BROKER_URL;

		String clientId = SystemUtil.getPhoneInfo(context).getDeviceId();
		return uri + ":wqzs"+clientId;
	}

	private static long preCallTimeConnect = 0;
	private static final int secondsConnect = 200;

	private static void connectAction(Context context) {
		Notify.toast(context, "init push", Toast.LENGTH_LONG);
		long currTime = System.currentTimeMillis();
		if (currTime - preCallTimeConnect < secondsConnect * 1000) {
			//Log.d(TAG, "太频繁");
			//return;
		}
		preCallTimeConnect = currTime;
		
		boolean doConnect = true;
		// create a client handle 客户端唯一标示？
		String clientHandle = getHandle(context);
		

		//if (listener == null) {
			String[] actionArgs = new String[1];
			actionArgs[0] = getClientId(context);
			listener = new ActionListener(context,
					ActionListener.Action.CONNECT, clientHandle, actionArgs);
		//}

		if (doConnect) {
			try {
				// 连接结果回调
				Connection connection = Connections.getInstance(context).getConnection(
						getHandle(context));
				connection.changeConnectionStatus(Connection.ConnectionStatus.CONNECTING);
				connection.getClient().connect(
						connection.getConnectionOptions(), null, listener);

			} catch (MqttException e) {
				e.printStackTrace();
				Log.e("PushUtil", "MqttException Occured", e);
			}
		}

	}
	
	public static MqttConnectOptions getOptions() {
		MqttConnectOptions conOpt = new MqttConnectOptions();
		// 是否保存会话
		boolean cleanSession = false;
		// last will message
		// String message = (String) data.get(ActivityConstants.message);
		// String topic = (String) data.get(ActivityConstants.topic);
		// Integer qos = (Integer) data.get(ActivityConstants.qos);
		// Boolean retained = (Boolean) data.get(ActivityConstants.retained);

		// connection options
		// 连接用户名
		// String username = (String) data.get(ActivityConstants.username);
		// 连接密码
		// String password = (String) data.get(ActivityConstants.password);
		// 超时时间 60
		int timeout = 60;
		// 心跳时间 200
		int keepalive = keepaliveeconds;
		// 创建连接记录 要显示在列表中

		// arrayAdapter.add(connection);
		// 连接状态改变更新UI吧
		// connection.registerChangeListener(changeListener);
		// connect client

		// connection.changeConnectionStatus(ConnectionStatus.CONNECTING);

		conOpt.setCleanSession(cleanSession);
		conOpt.setConnectionTimeout(timeout);
		conOpt.setKeepAliveInterval(keepalive);
		// if (!username.equals(ActivityConstants.empty)) {
		conOpt.setUserName(userName);
		// }
		// if (!password.equals(ActivityConstants.empty)) {
		conOpt.setPassword(password.toCharArray());
		// }

		return conOpt;
	}

	private static ActionListener listener;
	private static MqttCallbackHandler callbackHandler ;
	private static MqttTraceCallback traceCallback;
	public static void subscribe(Context context, String topic) {
        if(topic==null){
            return;
        }
		Connection connection = Connections.getInstance(context).getConnection(
				getHandle(context));
		String[] topics = new String[1];
		topics[0] = topic;
		int[] qoses = new int[1];
		qoses[0] = 1;
		
		
		String uri = BROKER_URL;
		String clientHandle = uri + getClientId(context);
		try {
			// if(listener==null) {
			ActionListener listener = new ActionListener(context,
					ActionListener.Action.SUBSCRIBE, clientHandle, topics);
			// }
			
			connection.getClient().subscribe(topics, qoses, null, listener);
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void publish(Context context, String topic, String message) {
		// TODO Auto-generated method stub
		Connection connection = Connections.getInstance(context).getConnection(
				getHandle(context));
		String[] topics = new String[1];
		message = message + System.currentTimeMillis();
		topics[0] = topic;
		String uri = BROKER_URL;
		String clientHandle = uri + getClientId(context);
		try {
			// if(listener==null) {
			ActionListener listener = new ActionListener(context,
					ActionListener.Action.PUBLISH, clientHandle, topics);
			// }

			connection.getClient().publish(topic, message.getBytes(), 1, false);
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	
	
	private static void connectAction1(Context context) {
		long currTime = System.currentTimeMillis();
		if (currTime - preCallTimeConnect < secondsConnect * 1000) {
			Log.d(TAG, "太频繁");
			return;
		}
		preCallTimeConnect = currTime;
		/*
		 * Mutal Auth connections could do something like this
		 * 
		 * 
		 * SSLContext context = SSLContext.getDefault(); context.init({new
		 * CustomX509KeyManager()},null,null); //where CustomX509KeyManager
		 * proxies calls to keychain api SSLSocketFactory factory =
		 * context.getSSLSocketFactory();
		 * 
		 * MqttConnectOptions options = new MqttConnectOptions();
		 * options.setSocketFactory(factory);
		 * 
		 * client.connect(options);
		 */

		// The basic client information

		boolean doConnect = true;
		// create a client handle 客户端唯一标示？
		String clientHandle = getHandle(context);
		// 创建MQTT客户端
		Connection connection = Connections.getInstance(context).getConnection(
				getHandle(context));
		if (connection == null) {
			MqttAndroidClient client = new MqttAndroidClient(context,
					BROKER_URL, getClientId(context));
			connection = new Connection(clientHandle, getClientId(context), ip, port,
					context, client, false);
			connection.addConnectionOptions(getOptions());

			// 如果没有消息也没有订阅的主题
			/*
			 * if ((!message.equals(ActivityConstants.empty)) ||
			 * (!topic.equals(ActivityConstants.empty))) { // need to make a
			 * message since last will is set //让服务器给我发个东西过来 try {
			 * conOpt.setWill(topic, message.getBytes(), qos.intValue(),
			 * retained.booleanValue()); } catch (Exception e) {
			 * e.printStackTrace(); Log.e(this.getClass().getCanonicalName(),
			 * "Exception Occured", e); doConnect = false;
			 * callback.onFailure(null, e); } }
			 */
			if(callbackHandler==null) {
				callbackHandler = new MqttCallbackHandler(context, clientHandle);
			}
			// 设置回调
			client.setCallback(callbackHandler);

			// set traceCallback 日志回调吧
			if(traceCallback==null) {
				traceCallback = new MqttTraceCallback();
			}
			client.setTraceCallback(traceCallback);

			Connections.getInstance(context).addConnection(connection);
		}

		if (listener == null) {
			String[] actionArgs = new String[1];
			actionArgs[0] = getClientId(context);
			listener = new ActionListener(context,
					ActionListener.Action.CONNECT, clientHandle, actionArgs);
		}

		if (doConnect) {
			try {
				// 连接结果回调
				connection.changeConnectionStatus(Connection.ConnectionStatus.CONNECTING);
				connection.getClient().connect(
						connection.getConnectionOptions(), null, listener);

			} catch (MqttException e) {
				e.printStackTrace();
				Log.e("PushUtil", "MqttException Occured", e);
			}
		}

	}

    public static void unsubscribe(Context context, String topic) {
        if(topic==null){
            return;
        }
        Connection connection = Connections.getInstance(context).getConnection(
                getHandle(context));
        String[] topics = new String[1];
        topics[0] = topic;
        int[] qoses = new int[1];
        qoses[0] = 1;


        String uri = BROKER_URL;
        String clientHandle = uri + getClientId(context);
        try {
            // if(listener==null) {
            ActionListener listener = new ActionListener(context,
                    ActionListener.Action.UNSUBSCRIBE, clientHandle, topics);
            // }

            connection.getClient().unsubscribe(topics, null, listener);
        } catch (MqttException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }


//	public static void addSchecduleTask1(Context context) {
//		try {
//			long currTime = System.currentTimeMillis();
//			if (currTime - preCallTime < seconds * 2 * 1000) {
//				Log.d(TAG, "太频繁");
//				return;
//			}
//			preCallTime = currTime;
//			// TODO Auto-generated method stub
//			// 创建Intent对象，action为ELITOR_CLOCK，附加信息为字符串“你该打酱油了”
//			Intent intent = new Intent(PushServiceReceiver.ACTION_SCHEDULE_TASK);
//			// intent.putExtra("msg","你该打酱油了");
//
//			// 定义一个PendingIntent对象，PendingIntent.getBroadcast包含了sendBroadcast的动作。
//			// 也就是发送了action 为"ELITOR_CLOCK"的intent
//			PendingIntent pi = PendingIntent
//					.getBroadcast(context, 0, intent, 0);
//			intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
//
//			// AlarmManager对象,注意这里并不是new一个对象，Alarmmanager为系统级服务
//			AlarmManager am = (AlarmManager) context
//					.getSystemService(Context.ALARM_SERVICE);
//			// 确保不用重复
//			am.cancel(pi);
//			// 设置闹钟从当前时间开始，每隔5s执行一次PendingIntent对象pi，注意第一个参数与第二个参数的关系
//			// n秒后通过PendingIntent pi对象发送广播
//			/*
//			 * am.setRepeating(AlarmManager.RTC_WAKEUP,
//			 * System.currentTimeMillis(), GlobalUtil.NOTICE_SYNC_DEP * 1000,
//			 * pi);
//			 */
//
//			am.setInexactRepeating(AlarmManager.RTC_WAKEUP,
//					System.currentTimeMillis(), seconds * 1000, pi);
//
//			Log.d(TAG, "定时任务启动成功");
//		} catch (Exception e) {
//			e.printStackTrace();
//			Log.e(TAG, "定时任务异常");
//		}
//	}
}
