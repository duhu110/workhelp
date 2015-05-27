package com.chinatelecom.ctsi.workhelper;

import java.util.ArrayList;
import java.util.List;

import com.chinatelecom.ctsi.workhelper.pushutil.PushUtil;
import com.iflytek.cloud.SpeechUtility;

import android.app.Activity;
import android.app.Application;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by apple on 14/10/31.
 */
public class WHApplication extends Application {
	public static final String ACTION_SCHEDULE_TASK = "com.chinatelecom.enterprisecontact_qx.ScheduleTask";

	private static WHApplication instance;

	private WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();

	/**
	 * 单例，返回一个实例
	 * 
	 * @return
	 */
	public static WHApplication getInstance() {
		if (instance == null) {
		}
		return instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		SpeechUtility.createUtility(WHApplication.this, "appid="
				+ getString(R.string.app_id));
		PushUtil.initPush(instance);
	}

	private final int showToast = 3;
	private final int dialog_error = 4;
	private final int dialog_info = 5;
	private final int dialog_yesno = 6;
	private final int dialog_simple = 7;
	private final int dialog_singleselector = 10;
	private final int dialog_full = 8;
	private final int showPDialog = 1;
	private final int dissmissPDialog = 2;
	Dialog_SpinnerDialog pdialog;
	Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case showPDialog: {
				DialogHandler_Base handler = (DialogHandler_Base) msg.obj;

				pdialog = new Dialog_SpinnerDialog(handler.activity);
				pdialog.setCancelable(false);

				if (!pdialog.isShowing()) {
					pdialog.dismiss();
					pdialog.show();
				}
				break;
			}
			case dissmissPDialog: {
				if (pdialog != null)
					if (pdialog.isShowing()) {
						pdialog.dismiss();
						pdialog = null;
					}
				break;
			}
			case showToast: {

				Toast.makeText(WHApplication.this, msg.obj.toString(),
						Toast.LENGTH_SHORT).show();

				break;
			}
			case dialog_info: {

				DialogInterface.OnClickListener listener = null;
				DialogHandler_Alert handler = (DialogHandler_Alert) msg.obj;
				Dialog_Tips dialog = new Dialog_Tips(handler.getActivity(),
						Dialog_Tips.TYPE_INFO, handler.getTitle(), handler
								.getMsg());
				dialog.setCancelable(false);
				dialog.setButton("我知道了...", listener);
				dialog.show();
				break;
			}
			case dialog_error: {
				DialogInterface.OnClickListener listener = null;
				DialogHandler_Error handler = (DialogHandler_Error) msg.obj;
				Dialog_Tips dialog = new Dialog_Tips(handler.getActivity(),
						Dialog_Tips.TYPE_ERROR, handler.getTitle(), handler
								.getMsg());
				dialog.setCancelable(false);
				dialog.setButton("确定", listener);
				dialog.show();
				break;
			}
			case dialog_yesno: {
				DialogHandler_YesNo handler = (DialogHandler_YesNo) msg.obj;

				Dialog_Tips dialog = new Dialog_Tips(handler.getActivity(),
						Dialog_Tips.TYPE_YESNO, handler.getTitle(), handler
								.getMsg());
				dialog.setCancelable(false);
				dialog.setButton("确定", handler.getOklistener());
				dialog.setButton2("取消", handler.getCancellistener());
				dialog.show();

				break;
			}
			case dialog_simple: {
				DialogHandler_Simple handler = (DialogHandler_Simple) msg.obj;
				Dialog_Tips dialog = new Dialog_Tips(handler.getActivity(), -1,
						handler.getTitle(), handler.getMsg());
				dialog.setCancelable(false);
				dialog.setButton("确定", handler.getOklistener());
				dialog.show();
				break;
			}
			case dialog_singleselector: {
				DialogHandler_SingleSelector handler = (DialogHandler_SingleSelector) msg.obj;
				String[] items = handler.getItems();
				List<KeyValuePair<String>> datasource = new ArrayList<KeyValuePair<String>>();
				for (int i = 0; i < items.length; i++) {
					datasource
							.add(new KeyValuePair<String>(items[i], items[i]));
				}

				Dialog_SingleSelect<String> dialog = new Dialog_SingleSelect<String>(
						handler.getActivity(), handler.getTitle(), datasource,
						handler.getSelectedindex() > 0 ? items[handler
								.getSelectedindex()] : null, handler
								.getSelectListener());
				dialog.show();

				break;
			}
			case dialog_full: {
				DialogHandler_FullDialog handler = (DialogHandler_FullDialog) msg.obj;

				Dialog_Tips dialog = new Dialog_Tips(handler.getActivity(), -1,
						handler.title, handler.message);

				dialog.setCancelable(false);
				dialog.setButton(handler.firstText, handler.firstlistener);
				dialog.show();
				if (!TextUtils.isEmpty(handler.thirdText)) {

					dialog.setButton3(handler.thirdText, handler.thirdListener);
					dialog.setButton2(handler.secondText,
							handler.secondListener);

				} else {
					if (!TextUtils.isEmpty(handler.secondText)) {
						dialog.setButton2(handler.secondText,
								handler.secondListener);
					}
				}
				dialog.show();
				break;
			}
			}

			return true;
		}
	});

	public void showSpinnerDialog(Activity activity) {
		DialogHandler_Base base = new DialogHandler_Base(activity, "");

		Message msg = handler.obtainMessage();
		msg.what = showPDialog;
		msg.obj = base;

		handler.sendMessage(msg);
	}

	public void dismissSpinnerDialog() {
		Message msg = handler.obtainMessage();
		msg.what = dissmissPDialog;
		handler.sendMessage(msg);
	}

	public void showToast(String mssage) {
		Message msg = handler.obtainMessage();
		msg.what = showToast;
		msg.obj = mssage;
		handler.sendMessage(msg);
	}

	public void ShowSingleChoiceDialog(Activity activity, String title,
			String[] items, int selectedindex,
			DialogInterface.OnClickListener selectListener) {
		Message msg = handler.obtainMessage();
		msg.what = dialog_singleselector;
		msg.obj = new DialogHandler_SingleSelector(activity, title, items,
				selectedindex, selectListener);
		handler.sendMessage(msg);
	}

	public void ShowYesNoDialog(Activity activity, String title,
			String message, OnClickListener oklistener,
			OnClickListener cancellistener) {
		Message msg = handler.obtainMessage();
		msg.what = dialog_yesno;
		msg.obj = new DialogHandler_YesNo(activity, title, message, oklistener,
				cancellistener);
		handler.sendMessage(msg);
	}

	public void ShowFullDialog(Activity activity, String title, String message,
			String firstText, String secondText, String thirdText,
			OnClickListener firstlistener, OnClickListener secondListener,
			OnClickListener thirdListener) {
		Message msg = handler.obtainMessage();
		msg.what = dialog_full;
		msg.obj = new DialogHandler_FullDialog(activity, title, message,
				firstText, secondText, thirdText, firstlistener,
				secondListener, thirdListener);
		handler.sendMessage(msg);
	}

	public void ShowErrorAlertDialog(Activity activity, String title,
			String message) {
		Message msg = handler.obtainMessage();
		msg.what = dialog_error;
		msg.obj = new DialogHandler_Error(activity, title, message);
		handler.sendMessage(msg);
	}

	public void ShowInfoAlertDialog(Activity activity, String title,
			String message) {
		Message msg = handler.obtainMessage();
		msg.what = dialog_info;
		msg.obj = new DialogHandler_Alert(activity, title, message);
		handler.sendMessage(msg);
	}

	public void ShowSimpleDialog(Activity activity, String title,
			String message, DialogInterface.OnClickListener listener) {
		Message msg = handler.obtainMessage();
		msg.what = dialog_simple;
		msg.obj = new DialogHandler_Simple(activity, title, message, listener);
		handler.sendMessage(msg);
	}
}
