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

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

//import com.example.pushtest.R;
//import com.example.pushtest.util.push.Connection.ConnectionStatus;


import com.chinatelecom.ctsi.workhelper.R;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


/**
 * This Class handles receiving information from the
 * {@link } and updating the {@link Connection} associated with
 * the action
 */
class ActionListener implements IMqttActionListener {

  /**
   * Actions that can be performed Asynchronously <strong>and</strong> associated with a
   * {@link com.chinatelecom.enterprisecontact_qx.pushutil.ActionListener} object
   * 
   */
  enum Action {
    /** Connect Action **/
    CONNECT,
    /** Disconnect Action **/
    DISCONNECT,
    /** Subscribe Action **/
    SUBSCRIBE,
    /** Publish Action **/
    PUBLISH,
    /** Subscribe Action **/
    UNSUBSCRIBE
  }

  /**
   * The {@link com.chinatelecom.enterprisecontact_qx.pushutil.ActionListener.Action} that is associated with this instance of
   * <code>ActionListener</code>
   **/
  private Action action;
  /** The arguments passed to be used for formatting strings**/
  private String[] additionalArgs;
  /** Handle of the {@link Connection} this action was being executed on **/
  private String clientHandle;
  /** {@link android.content.Context} for performing various operations **/
  private Context context;

  /**
   * Creates a generic action listener for actions performed form any activity
   * 
   * @param context
   *            The application context
   * @param action
   *            The action that is being performed
   * @param clientHandle
   *            The handle for the client which the action is being performed
   *            on
   * @param additionalArgs
   *            Used for as arguments for string formating
   */
  public ActionListener(Context context, Action action,
      String clientHandle, String... additionalArgs) {
    this.context = context;
    this.action = action;
    this.clientHandle = clientHandle;
    this.additionalArgs = additionalArgs;
  }

  /**
   * The action associated with this listener has been successful.
   * 
   * @param asyncActionToken
   *            This argument is not used
   */
  @Override
  public void onSuccess(IMqttToken asyncActionToken) {
	  
	Log.d("ActionListener", "onSuccess"+action) ;
    switch (action) {
      case CONNECT :
    	//连接成功 可以开始订阅了
        connect();
        break;
      case DISCONNECT :
        disconnect();
        break;
      case SUBSCRIBE :
        subscribe();
        break;
      case PUBLISH :
        publish();
        break;
      case UNSUBSCRIBE:
        unsubscribe();
    }

  }

    private void unsubscribe() {

        Log.d("unsubscribe","unsubscribe");
    }

    /**
   * A publish action has been successfully completed, update connection
   * object associated with the client this action belongs to, then notify the
   * user of success
   */
  private void publish() {

   // Connection c = Connections.getInstance(context).getConnection(clientHandle);
      //(Object[]) additionalArgs
    String actionTaken = context.getString(R.string.toast_pub_success,"",""
        );
   // c.addAction(actionTaken);
    Notify.toast(context, actionTaken, Toast.LENGTH_SHORT);
  }

  /**
   * A subscribe action has been successfully completed, update the connection
   * object associated with the client this action belongs to and then notify
   * the user of success
   */
  private void subscribe() {
    Connection c = Connections.getInstance(context).getConnection(clientHandle);
    String actionTaken = context.getString(R.string.toast_sub_success,
        (Object[]) additionalArgs);
    //c.addAction(actionTaken);
    Notify.toast(context, actionTaken, Toast.LENGTH_SHORT);
    if(c!=null) {
        c.setSubscribeSuccess(true);
    }
  }

  /**
   * A disconnection action has been successfully completed, update the
   * connection object associated with the client this action belongs to and
   * then notify the user of success.
   */
  private void disconnect() {
   Connection c = Connections.getInstance(context).getConnection(clientHandle);
    c.changeConnectionStatus(Connection.ConnectionStatus.DISCONNECTED);
    String actionTaken = context.getString(R.string.toast_disconnected);
   // c.addAction(actionTaken);
    Notify.toast(context, "disconnect", Toast.LENGTH_SHORT);
  }

  /**
   * A connection action has been successfully completed, update the
   * connection object associated with the client this action belongs to and
   * then notify the user of success.
   */
  private void connect() {

    Connection c = Connections.getInstance(context).getConnection(clientHandle);
    c.changeConnectionStatus(Connection.ConnectionStatus.CONNECTED);
    //c.addAction("Client Connected");
    if(c.getClient()!=null &&  c.getClient().isConnected()) {
    	Notify.toast(context, "Client Connected",	Toast.LENGTH_SHORT);
    	 //订阅
         //   PushUtil.unsubscribe(context, "20150319184625108");
           // Log.d("Subscrib","订阅");
            PushUtil.subscribe(context, PushUtil.TOPIC);
          
    }else {
    	Notify.toast(context, "Client connect client fail",	Toast.LENGTH_SHORT);
    }
   
  }

  /**
   * The action associated with the object was a failure
   * 
   * @param token
   *            This argument is not used
   * @param exception
   *            The exception which indicates why the action failed
   */
  @Override
  public void onFailure(IMqttToken token, Throwable exception) {
	  if(exception!=null) {
		  exception.printStackTrace();  
	  }
	  Log.d("ActionListener", "onFailure"+action) ;
	  
    switch (action) {
      case CONNECT :
    	//连接失败
        connect(exception);
        break;
      case DISCONNECT :
        disconnect(exception);
        break;
      case SUBSCRIBE :
        subscribe(exception);
        break;
      case PUBLISH :
        publish(exception);
        break;
    }

  }

  /**
   * A publish action was unsuccessful, notify user and update client history
   * 
   * @param exception
   *            This argument is not used
   */
  private void publish(Throwable exception) {
	
  //  Connection c = Connections.getInstance(context).getConnection(clientHandle);
    String action = context.getString(R.string.toast_pub_failed,
        (Object[]) additionalArgs);
  //  c.addAction(action);
    Notify.toast(context, action, Toast.LENGTH_SHORT);

  }

  /**
   * A subscribe action was unsuccessful, notify user and update client history
   * @param exception This argument is not used
   */
  private void subscribe(Throwable exception) {
	try {
		if(exception!=null) {
			exception.printStackTrace();
		}
	Connection c = Connections.getInstance(context).getConnection(clientHandle);
    String action = context.getString(R.string.toast_sub_failed,
        (Object[]) additionalArgs);
   // c.addAction(action);
    Notify.toast(context, action, Toast.LENGTH_SHORT);
    c.setSubscribeSuccess(false);
	}catch(Exception e) {e.printStackTrace();}
  }

  /**
   * A disconnect action was unsuccessful, notify user and update client history
   * @param exception This argument is not used
   */
  private void disconnect(Throwable exception) {
	Notify.toast(context, "disconnect exception", Toast.LENGTH_SHORT);
    Connection c = Connections.getInstance(context).getConnection(clientHandle);
    c.changeConnectionStatus(Connection.ConnectionStatus.DISCONNECTED);
    //c.addAction("Disconnect Failed - an error occured");

  }

  /**
   * A connect action was unsuccessful, notify the user and update client history
   * @param exception This argument is not used
   */
  private void connect(Throwable exception) {
    Connection c = Connections.getInstance(context).getConnection(clientHandle);
    c.changeConnectionStatus(Connection.ConnectionStatus.ERROR);
    //c.addAction("Client failed to connect");
    Notify.toast(context, "connect fail", Toast.LENGTH_SHORT);
  }

}