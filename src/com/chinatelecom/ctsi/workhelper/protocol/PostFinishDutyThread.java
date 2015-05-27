package com.chinatelecom.ctsi.workhelper.protocol;

import java.util.HashMap;

import org.json.JSONObject;

import android.content.Context;

import com.chinatelecom.ctsi.workhelper.R;
import com.chinatelecom.ctsi.workhelper.protocol.base.BaseListener;
import com.chinatelecom.ctsi.workhelper.protocol.base.BaseResponse;
import com.chinatelecom.ctsi.workhelper.protocol.base.Http;
import com.chinatelecom.ctsi.workhelper.util.JsonUtil;
import com.ctsi.protocol.HttpPostJSONThread;
import com.ctsi.protocol.HttpStringResponseStatus;

public class PostFinishDutyThread extends HttpPostJSONThread {
	public static String FINISHDUTYID = "dutyId";
	BaseListener listener;

	public PostFinishDutyThread(Context context, final String dutyId,
			BaseListener listener) {
		super(context, Http.SERVER, 10000, 10000);
		this.listener = listener;

		HashMap<String, String> tempMap = new HashMap<String, String>();
		tempMap.put(FINISHDUTYID, dutyId);
		String json = (new JSONObject(tempMap)).toString();
		setString(json);

	}

	@Override
	protected void HandleHttpResponseStatus(HttpStringResponseStatus status) {
		// TODO Auto-generated method stub
		switch (status.getCode()) {
		case HttpStringResponseStatus.STATUS_SUCCUES:

			try {
				BaseResponse response = JsonUtil.readJsonFromString(
						status.getResult(), BaseResponse.class);

				switch (response.getCode()) {
				case BaseResponse.RESPONSE_CODE_SUCCESS:
					if (listener != null) {
						listener.onSuccess();
					}
					break;

				default:
					if (listener != null) {
						listener.onServerException(response.getMessage());
					}
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
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

		if (listener != null)
			listener.onPrev();

		super.run();
	}

}
