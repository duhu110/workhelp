/*******************************************************************************
 * Copyright (c) 1999, 2014 IBM Corp.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution. 
 *
 * The Eclipse Public License is available at 
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 *   http://www.eclipse.org/org/documents/edl-v10.php.
 */
package com.chinatelecom.ctsi.workhelper.pushutil;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

//import com.example.pushtest.MainActivity;
//import com.example.pushtest.broadcast.PushServiceReceiver;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;


/**
 * Handles call backs from the MQTT Client
 *
 */
public class MqttCallbackHandler implements MqttCallback {

  /** {@link android.content.Context} for the application used to format and import external strings**/
  private Context context;
  /** Client handle to reference the connection that this handler is attached to**/
  private String clientHandle;

  /**
   * Creates an <code>MqttCallbackHandler</code> object
   * @param context The application's context
   * @param clientHandle The handle to a {@link Connection} object
   */
  public MqttCallbackHandler(Context context, String clientHandle)
  {
    this.context = context;
    this.clientHandle = clientHandle;
  }

  /**
   * @see org.eclipse.paho.client.mqttv3.MqttCallback#connectionLost(Throwable)
   */
  @Override
  public void connectionLost(Throwable cause) {
	//Log.d("MqttCallbackHandler", "connectionLost");
	MLog.log("MqttCallbackHandler", "connectionLost");
	Notify.toast(context, "MqttCallbackHandler connectionLost", Toast.LENGTH_LONG);
	 Connection c = Connections.getInstance(context).getConnection(clientHandle);
     c.addAction("Connection Lost");
     c.changeConnectionStatus(Connection.ConnectionStatus.DISCONNECTED);
	 if (cause != null) {
	  cause.printStackTrace();
	 
      
      MLog.log("MqttCallbackHandler", "connectionLost"+cause.getMessage());
      String message = "连接丢失";
      
      //build intent
      Intent intent = new Intent();
      intent.setClassName(context, "org.eclipse.paho.android.service.sample.ConnectionDetails");
      intent.putExtra("handle", clientHandle);

      //notify the user
      //Notify.notifcation(context, message, intent, "提示");
      
      //重新连接
      new Handler().postDelayed(new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			//PushUtil.initPush(context,PushServiceReceiver.TYPE_NETCHANGE);
		}
    	  
      }, 2000);
      
    }
  }

  /**
   * @see org.eclipse.paho.client.mqttv3.MqttCallback#messageArrived(String, org.eclipse.paho.client.mqttv3.MqttMessage)
   */
  @Override
  public void messageArrived(String topic, MqttMessage message) throws Exception {
	Log.d("MqttCallbackHandlerzwt", "messageArrived");
    //Get connection object associated with this object
   
    //create arguments to format message arrived notifcation string
    String[] args = new String[2];
    args[0] = new String(message.getPayload());
    args[1] = topic+";qos:"+message.getQos()+";retained:"+message.isRetained();

    //get the string from strings.xml and format
    //String messageString = context.getString(R.string.messageRecieved, (Object[]) args);

    //create intent to start activity
   // Intent intent = new Intent(context, MainActivity.class);
    //intent.setClassName(context, MainActivity.class);
    //intent.putExtra("handle", clientHandle);

    //format string args
    

    //notify the user 
    //Notify.notifcation(context, new String(message.getPayload()), intent, "新消息");

    //update client history
    //c.addAction(messageString);
    
  }

  /**
   * @see org.eclipse.paho.client.mqttv3.MqttCallback#deliveryComplete(org.eclipse.paho.client.mqttv3.IMqttDeliveryToken)
   */
  @Override
  public void deliveryComplete(IMqttDeliveryToken token) {
    // Do nothing
  }

}
