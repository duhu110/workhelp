package com.chinatelecom.ctsi.workhelper.protocol;

import java.util.HashMap;

import android.content.Context;
import android.util.Log;

import com.chinatelecom.ctsi.workhelper.R;
import com.chinatelecom.ctsi.workhelper.protocol.base.BaseListener;
import com.chinatelecom.ctsi.workhelper.protocol.base.Http;
import com.ctsi.protocol.HttpPostThread;
import com.ctsi.protocol.HttpStringResponseStatus;

public class PostMessageThread extends HttpPostThread {
	public static String TAGETUSER = "tag";
	public static String SENDDATA = "sendData";
	BaseListener listener;

	public PostMessageThread(Context context, String sendData,
			BaseListener listener) {
		super(context, Http.PUSH_SERVER, 10000, 10000);
		this.listener = listener;

		HashMap<String, String> tempMap = new HashMap<String, String>();
		tempMap.put(TAGETUSER, "wqzs");
		tempMap.put(SENDDATA, sendData);
		setParamers(tempMap);
	}

	@Override
	protected void HandleHttpResponseStatus(HttpStringResponseStatus status) {
		// TODO Auto-generated method stub
		switch (status.getCode()) {
		case HttpStringResponseStatus.STATUS_SUCCUES:

			Log.e("message", status.getResult());
			if (status.getResult().contains("\\u6210\\u529F")) {
				if (listener != null) {
					listener.onSuccess();
				}
			} else {
				if (listener != null) {
					listener.onServerException(getContext().getString(
							R.string.tips_uncatch_server_exception));
				}
			}

			break;
		case HttpStringResponseStatus.STATUS_EXCEPTION_UNAVAIABLENETWORK:

			if (listener != null) {
				listener.onUnavaiableNetwork();
			}

			break;
		case HttpStringResponseStatus.STATUS_EXCEPTION_TIMEOUT:
			if (listener != null) {
				listener.onTimeout();
			}
			break;

		default:
			if (listener != null) {
				listener.onServerException(getContext().getString(
						R.string.tips_uncatch_server_exception));
			}
			break;
		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		/*
		 * if (listener != null) listener.onPrev();
		 */

		super.run();
	}

}
