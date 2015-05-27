package com.chinatelecom.ctsi.workhelper.activity;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.chinatelecom.ctsi.workhelper.MainActivity;
import com.chinatelecom.ctsi.workhelper.R;
import com.chinatelecom.ctsi.workhelper.WHApplication;
import com.chinatelecom.ctsi.workhelper.db.DutyInfoDao;
import com.chinatelecom.ctsi.workhelper.model.DutyInfo;
import com.chinatelecom.ctsi.workhelper.model.User;
import com.chinatelecom.ctsi.workhelper.service.AlarmService;
import com.chinatelecom.ctsi.workhelper.util.TtsManager;
import com.chinatelecom.ctsi.workhelper.ztest.Activty_Test;
import com.ctsi.utils.DateUtil;
import com.ctsi.utils.Utils;

/**
 * A login screen that offers login via email/password.
 */

public class LoginActivity extends Activity {

	public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";
	public static final int HANDLER_MESSAGE_TIMER = 2008;

	// 验证码倒计时s数
	public static final int COUNT_SECOND = 60;

	public static int mCountRemain;

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	// private UserLoginTask mAuthTask = null;

	Handler handler = new Handler();
	View bt_sign_in;

	EditText account, password;

	WHApplication application;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setupActionBar();
		getActionBar().hide();

		setContentView(R.layout.activity_login);
		application = (WHApplication) getApplication();
		//
		// ((WHApplication) getApplication()).showToast("登陆成功");
		//
		// ((WHApplication) getApplication()).ShowFullDialog(LoginActivity.this,
		// "提示", "内容", "btn1", "btn2", null,
		// new DialogInterface.OnClickListener() {
		//
		// @Override
		// public void onClick(DialogInterface arg0, int arg1) {
		// // TODO Auto-generated method stub
		//
		// }
		// }, null, null);

		// DutyInfo info = new DutyInfo();
		// info.setTitle("认真落实新三者战略");
		// info.setContent("请中国电信各单位动员全体员工进一步解放思想、加快变革、扩大合作、提高效率，推进“一去两化新三者”，实现企业转型发展新突破，为再造一个新型中国电信奠定基础");
		// info.setDeadline(new Date().getTime());
		//
		// TtsManager.getManager(this).playString(info.getSpeechString());
		bt_sign_in = findViewById(R.id.bt_sign_in);
		account = (EditText) findViewById(R.id.account);
		password = (EditText) findViewById(R.id.password);
		account.setText(User.currentUser.mobile);

		bt_sign_in.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				login();
			}
		});
		bt_sign_in.setFocusable(true);
		bt_sign_in.setFocusableInTouchMode(true);
		bt_sign_in.requestFocus();
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
	}

	private void login() {

		application.showSpinnerDialog(this);

		SharedPreferences p = PreferenceManager
				.getDefaultSharedPreferences(this);

		if (!p.getBoolean("initData", false)) {
			initData();
			Editor edit = p.edit();
			edit.putBoolean("initData", true);
			edit.commit();
		}
		startService();

		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				application.dismissSpinnerDialog();
				Intent intent = new Intent(LoginActivity.this,
						MainActivity.class);
				startActivity(intent);
				finish();
			}
		}, 1000);

	}

	private void initData() {

		DutyInfo myduDutyInfo1 = new DutyInfo();
		DutyInfo myduDutyInfo2 = new DutyInfo();
		DutyInfo myduDutyInfo3 = new DutyInfo();

		if (User.currentUser.id == User.users.get(1).id) {
			myduDutyInfo1.setTitle("参加公司和职业学校进行合作事宜的会议通知");
			myduDutyInfo1
					.setContent("内容:对学生职业素养的实训。学习面试和工作中必须掌握的沟通表达能力、中文能力、团队工作能力等。");
			myduDutyInfo1.setDeadline(DateUtil.String2Date(
					"2015-05-15 17:30:00").getTime());
			myduDutyInfo1.setStatus(DutyInfo.STATUS_FINISHED);
			myduDutyInfo1.setId(1);

			myduDutyInfo2.setTitle("参加公司内部会议通知");
			myduDutyInfo2
					.setContent("中心各部室： 定于明天（5月24日）晚上6：00召开中心“战略穿透我先行”宣讲会，"
							+ "现将相关要求通知如下： 1、地点：省公司大楼二楼电视电话会议室； 2、主要内容：由省公司总经理刚对省公司2015年战略部署进行宣讲；"
							+ " 3、要求中心全体员工务必参加，不得请假； 4、请各部室主任负责组织本部室员工提前5分钟进入会场；"
							+ " 5、会议期间请注意会场纪律，并请将手机置于静音状态。");
			myduDutyInfo2.setDeadline(DateUtil.String2Date(
					"2015-05-24 18:00:00").getTime());
			myduDutyInfo2.setStatus(DutyInfo.STATUS_UNFINISHED);
			myduDutyInfo2.setId(2);

			myduDutyInfo3.setTitle("拜访开发区管委会王处长");
			myduDutyInfo3.setContent("拜访过程：1、介绍项目。我公司与天成集团合作，策划了《天成春秋》项目。"
					+ "2、我们的想法。我们将该项目引入到我市，我公司李总参与此项目操作。"
					+ "3、具体操作方法。将项目分包出去，通过此项目引进外地企业，聚集人气，同时带动我市相关企业");
			myduDutyInfo3.setDeadline(DateUtil.String2Date(
					"2015-05-26 12:30:00").getTime());
			myduDutyInfo3.setStatus(DutyInfo.STATUS_UNFINISHED);
			myduDutyInfo3.setId(3);
		}else if (User.currentUser.id == User.users.get(2).id) {
			myduDutyInfo1.setTitle("烽火HG220G-U软件升级");
			myduDutyInfo1
					.setContent("请严格按照升级指导说明进行终端版本升级，升级过程有问题及时与我沟通，联系人李奕，18112111760");
			myduDutyInfo1.setDeadline(DateUtil.String2Date(
					"2015-05-15 15:26:00").getTime());
			myduDutyInfo1.setStatus(DutyInfo.STATUS_FINISHED);
			myduDutyInfo1.setId(User.currentUser.id);

			myduDutyInfo2.setTitle("整理上行宽带设备信息");
			myduDutyInfo2
					.setContent("收集一下ATM上行的宽带设备信息，方便后续整改，最迟下周一前就要，谢谢");
			myduDutyInfo2.setDeadline(DateUtil.String2Date(
					"2015-05-24 16:20:00").getTime());
			myduDutyInfo2.setStatus(DutyInfo.STATUS_UNFINISHED);
			myduDutyInfo2.setId(User.currentUser.id);

			myduDutyInfo3.setTitle("用户实际接入设备核查");
			myduDutyInfo3.setContent(" 区公司要求核查用户的实际接入设备的IP和名称，请各位按要求核查自己所在区县的用户，" +
					"要求本周内完成，另外附上全市最新绑定信息一份，可以通过账号比对，" +
					"获得外标，节约时间。未尽事宜，请联系刘景咨询确定");
			myduDutyInfo3.setDeadline(DateUtil.String2Date(
					"2015-05-26 13:10:00").getTime());
			myduDutyInfo3.setStatus(DutyInfo.STATUS_UNFINISHED);
			myduDutyInfo3.setId(User.currentUser.id);
		}else if (User.currentUser.id == User.users.get(3).id) {
			myduDutyInfo1.setTitle("客户刘继明:电视配送及安装");
			myduDutyInfo1
					.setContent("客户：刘继明；联系电话： 021-60938197；地址： 军工路1100(松花江路)；机器型号：ER10000");
			myduDutyInfo1.setDeadline(DateUtil.String2Date(
					"2015-05-15 19:26:00").getTime());
			myduDutyInfo1.setStatus(DutyInfo.STATUS_FINISHED);
			myduDutyInfo1.setId(User.currentUser.id);

			myduDutyInfo2.setTitle("客户陈松:空调维修");
			myduDutyInfo2
					.setContent("客户：陈松；联系电话： 021-60528595；地址： 广中路区域大连西路550号；机器型号：R135300");
			myduDutyInfo2.setDeadline(DateUtil.String2Date(
					"2015-05-24 13:20:00").getTime());
			myduDutyInfo2.setStatus(DutyInfo.STATUS_UNFINISHED);
			myduDutyInfo2.setId(User.currentUser.id);

			myduDutyInfo3.setTitle("客户刘云:空调维修");
			myduDutyInfo3.setContent("客户：刘云；联系电话： 021-60769837；地址： 浦东新区康桥路1500号；机器型号：Z8883653");
			myduDutyInfo3.setDeadline(DateUtil.String2Date(
					"2015-05-26 10:10:00").getTime());
			myduDutyInfo3.setStatus(DutyInfo.STATUS_UNFINISHED);
			myduDutyInfo3.setId(User.currentUser.id);
		}

	
	
		ArrayList<DutyInfo> infos = new ArrayList<DutyInfo>();

		infos.add(myduDutyInfo1);
		infos.add(myduDutyInfo2);
		infos.add(myduDutyInfo3);
		for (DutyInfo info : infos) {
			DutyInfoDao.save(info);
		}

		List<DutyInfo> is = DutyInfoDao.getRecordList(DutyInfo.TYPE_ALL);
		Log.e("size", is.size() + "");
	}

	private void startService() {
		if (AlarmService.isWorked(this)) {

		} else {
			Intent intent = new Intent(LoginActivity.this, AlarmService.class);

			startService(intent);
		}

	}



	

}
