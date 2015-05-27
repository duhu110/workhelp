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

import java.util.Calendar;

import com.chinatelecom.ctsi.workhelper.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import android.widget.Toast;


//import com.example.pushtest.R;

/**
 * Provides static methods for creating and showing notifications to the user.
 *
 */
public class Notify {

  /** Message ID Counter **/
  private static int MessageID = 0;

  /**
   * Displays a notification in the notification area of the UI
   * @param context Context from which to create the notification
   * @param messageString The string to display to the user as a message
   * @param intent The intent which will start the activity when the user clicks the notification
   * @param notificationTitle The resource reference to the notification title
   */
  public static void notifcation(Context context, String messageString, Intent intent, String title) {

    //Get the notification manage which we will use to display the notification
    String ns = Context.NOTIFICATION_SERVICE;
    NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);

    Calendar.getInstance().getTime().toString();

    long when = System.currentTimeMillis();

    //get the notification title from the application's strings.xml file
   // CharSequence contentTitle = context.getString(notificationTitle);

    //the message that will be displayed as the ticker
    String ticker = title + " " + messageString;

    //build the pending intent that will start the appropriate activity
    PendingIntent pendingIntent = PendingIntent.getActivity(context,
        0, intent, 0);

    //build the notification
    Builder notificationCompat = new Builder(context);
    notificationCompat.setAutoCancel(true)
        .setContentTitle(title)
        .setContentIntent(pendingIntent)
        .setContentText(messageString)
        .setTicker(ticker)
        .setWhen(when)
        .setSmallIcon(R.drawable.ic_launcher);

    Notification notification = notificationCompat.build();
    notification.defaults = Notification.DEFAULT_ALL;
    //display the notification
    mNotificationManager.notify(MessageID, notification);
    MessageID++;

  }

  /**
   * Display a toast notification to the user
   * @param context Context from which to create a notification
   * @param text The text the toast should display
   * @param duration The amount of time for the toast to appear to the user
   */
  public static void toast(Context context, CharSequence text, int duration) {
	Log.d("NotifyToast", ""+text);
   // Toast toast = Toast.makeText(context, text, duration);
   // toast.show();
	MLog.log("Toast", ""+text.toString());
  }

}
