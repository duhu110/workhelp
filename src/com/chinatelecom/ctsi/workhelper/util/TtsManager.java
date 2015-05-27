package com.chinatelecom.ctsi.workhelper.util;

import com.chinatelecom.ctsi.workhelper.WHApplication;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.GrammarListener;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class TtsManager {

	private SpeechSynthesizer mTts;
	private static final String KEY_GRAMMAR_ABNF_ID = "grammar_abnf_id";
	private static final String GRAMMAR_TYPE_ABNF = "abnf";
	private static final String GRAMMAR_TYPE_BNF = "bnf";
	String text;

	private static TtsManager manager = null;
	WHApplication application;

	private Toast mToast;
	private SharedPreferences mSharedPreferences;
	// 缓冲进度
	private int mPercentForBuffering = 0;
	// 播放进度
	private int mPercentForPlaying = 0;

	public static synchronized TtsManager getManager(Context context) {
		if (manager == null) {
			manager = new TtsManager(context);

		}

		return manager;
	}

	Context context;
	boolean isStart;

	private TtsManager(Context context) {
		// TODO Auto-generated constructor stub
		mTts = SpeechSynthesizer.createSynthesizer(context, mInitListener);
		this.context = context;
		application = (WHApplication) this.context.getApplicationContext();
		mSharedPreferences = context.getSharedPreferences(
				context.getPackageName(), context.MODE_PRIVATE);
		mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
	}

	public void playString(String str) {
		stop();
		setParam();
		int code = mTts.startSpeaking(str, mTtsListener);

	}

	public void stop() {
		if (mTts.isSpeaking())
			mTts.stopSpeaking();

	}

	private InitListener mInitListener = new InitListener() {

		@Override
		public void onInit(int code) {
			if (code != ErrorCode.SUCCESS) {
				showTip("语音模块初始化失败");
			}
		}
	};
	private SynthesizerListener mTtsListener = new SynthesizerListener() {
		@Override
		public void onSpeakBegin() {
			showTip("开始播放");
		}

		@Override
		public void onSpeakPaused() {
			showTip("暂停播放");
		}

		@Override
		public void onSpeakResumed() {
			showTip("继续播放");
		}

		@Override
		public void onBufferProgress(int percent, int beginPos, int endPos,
				String info) {
			// 合成进度
			mPercentForBuffering = percent;
			showTip("缓冲进度为" + mPercentForBuffering + "%" + "，播放进度为"
					+ mPercentForPlaying + "%");
		}

		@Override
		public void onSpeakProgress(int percent, int beginPos, int endPos) {
			// 播放进度
			mPercentForPlaying = percent;
			showTip("缓冲进度为" + mPercentForBuffering + "%" + "，播放进度为"
					+ mPercentForPlaying + "%");
		}

		@Override
		public void onCompleted(SpeechError error) {
			if (error == null) {
				showTip("播放完成");
			} else if (error != null) {
				showTip(error.getPlainDescription(true));
			}
		}

		@Override
		public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {

		}
	};

	private void setParam() {
		// 清空参数
		mTts.setParameter(SpeechConstant.PARAMS, null);
		// 根据合成引擎设置相应参数
		mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
		// 设置在线合成发音人
		mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyu");

		// 设置合成语速
		mTts.setParameter(SpeechConstant.SPEED,
				mSharedPreferences.getString("speed_preference", "50"));
		// 设置合成音调
		mTts.setParameter(SpeechConstant.PITCH,
				mSharedPreferences.getString("pitch_preference", "50"));
		// 设置合成音量
		mTts.setParameter(SpeechConstant.VOLUME,
				mSharedPreferences.getString("volume_preference", "50"));
		// 设置播放器音频流类型
		mTts.setParameter(SpeechConstant.STREAM_TYPE,
				mSharedPreferences.getString("stream_preference", "3"));

		// 设置播放合成音频打断音乐播放，默认为true
		mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

		// 设置合成音频保存路径，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
		mTts.setParameter(SpeechConstant.PARAMS, "tts_audio_path="
				+ Environment.getExternalStorageDirectory() + "/test.pcm");
	}

	private RecognizerListener mRecognizerListener = new RecognizerListener() {

		@Override
		public void onVolumeChanged(int volume) {
		}

		@Override
		public void onResult(final RecognizerResult result, boolean isLast) {
			if (null != result) {

				String text = JsonParser.parseGrammarResult(result
						.getResultString());
				showTip(text);
				// 显示
			} else {

			}
		}

		@Override
		public void onEndOfSpeech() {
			showTip("结束说话");
		}

		@Override
		public void onBeginOfSpeech() {
			showTip("开始说话");
		}

		@Override
		public void onError(SpeechError error) {
			showTip("onError Code：" + error.getErrorCode());

		}

		@Override
		public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
			// TODO Auto-generated method stub

		}

	};

	private void showTip(final String str) {
		mToast.setText(str);
		mToast.show();
	}

}
