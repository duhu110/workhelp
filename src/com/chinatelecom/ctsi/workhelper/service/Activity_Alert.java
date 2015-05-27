package com.chinatelecom.ctsi.workhelper.service;

import java.util.Date;

import com.chinatelecom.ctsi.workhelper.R;
import com.chinatelecom.ctsi.workhelper.WHApplication;
import com.chinatelecom.ctsi.workhelper.db.DutyInfoDao;
import com.chinatelecom.ctsi.workhelper.model.DutyInfo;
import com.chinatelecom.ctsi.workhelper.protocol.PostFinishDutyThread;
import com.chinatelecom.ctsi.workhelper.protocol.base.BaseListener;
import com.ctsi.utils.DateUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity_Alert extends Activity {
	public static MediaPlayer mediaPlayer;
	public static Vibrator vibrator;
	WHApplication application;
	TextView txv_name, txv_time, txv_distance, txv_text, txv_chart_name1,
			txv_chart_text1, txv_chart_name2, txv_chart_text2,
			txv_chart_action1, txv_chart_action2, task_done_btn;
	ImageView img_pp, img_pic, img_feedback, task_done_img;
	View img_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		setContentView(R.layout.activity_alert);

		String id = getIntent().getStringExtra("id");

		DutyInfo info = DutyInfoDao.getRecordById(id);

		Log.e("info", info.getSpeechString());
		txv_name = (TextView) findViewById(R.id.txv_name);
		txv_time = (TextView) findViewById(R.id.txv_time);
		txv_text = (TextView) findViewById(R.id.txv_text);
		img_back = findViewById(R.id.img_back);
		img_back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		txv_chart_name1 = (TextView) findViewById(R.id.txv_chart_name1);
		txv_chart_text1 = (TextView) findViewById(R.id.txv_chart_text1);
		txv_chart_name2 = (TextView) findViewById(R.id.txv_chart_name2);
		txv_chart_text2 = (TextView) findViewById(R.id.txv_chart_text2);
		task_done_btn = (TextView) findViewById(R.id.image_task_done);
		task_done_img = (ImageView) findViewById(R.id.order_wc_icon);
		task_done_img.setVisibility(View.GONE);
		task_done_btn.setVisibility(View.GONE);
		setDetail(info);

		application = (WHApplication) getApplicationContext();
		// 如果设置了声音提醒，则播放res\raw目录中音频文件
		if (true) {
			mediaPlayer = MediaPlayer.create(this, R.raw.ring);
			try {
				mediaPlayer.setLooping(true);
				mediaPlayer.prepare();
				mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
				mediaPlayer.setVolume(100, 100);
			} catch (Exception e) {
				setTitle(e.getMessage());
			}
			mediaPlayer.start();
		}
		// 如果设置了振动提醒，则使手机振动
		if (true) {
			vibrator = (Vibrator) getApplication().getSystemService(
					Service.VIBRATOR_SERVICE);
			vibrator.vibrate(new long[] { 1000, 100, 100, 1000 }, 0);
		}

		application.ShowSimpleDialog(this, "提示", "您有一条新的提醒",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						vibrator.cancel();
						mediaPlayer.stop();
					}
				});
	}

	public void setDetail(final DutyInfo info) {
		txv_name.setText(info.getTitle());
		Date date = new Date(info.getDeadline());
		txv_time.setText(DateUtil.Date2String(date, "yyyy-MM-dd HH:mm"));
		txv_text.setText(info.getContent());

	}

}
