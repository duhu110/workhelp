package com.chinatelecom.ctsi.workhelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Dialog_Tips extends Dialog_Base {

	public static final int TYPE_ERROR = 1;
	public static final int TYPE_INFO = 2;
	public static final int TYPE_YESNO = 3;
	
	int type;
	TextView txv_msg;
	ImageView img_type;
	View view;

	public Dialog_Tips(Context context, int type, String title, String text) {
		super(context, title);
		// TODO Auto-generated constructor stub
		this.type = type;
		LayoutInflater mInflater = LayoutInflater.from(getContext());
		this.view = mInflater.inflate(R.layout.layout_dialog_tips, null);
		txv_msg = (TextView) view.findViewById(R.id.txv_msg);
		txv_msg.setText(text);
		img_type = (ImageView) view.findViewById(R.id.img_type);
		if (getImageSource(type) == -1) {
			img_type.setVisibility(View.GONE);
		} else {
			img_type.setVisibility(View.VISIBLE);
			img_type.setImageResource(getImageSource(type));
		}

	}

	private int getImageSource(int type) {
		int result = -1;
		switch (type) {
		case TYPE_ERROR:
			result = R.drawable.icon_dialog_error;
			break;
		case TYPE_INFO:
			result = R.drawable.icon_dialog_info;
			break;
		case TYPE_YESNO:
			result = R.drawable.icon_dialog_yesno;
			break;

		}
		return result;
	}

	@Override
	public int getWhich() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getUpperBackground() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getBodyBackground() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getBottomBackground() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getMidView() {
		// TODO Auto-generated method stub
		return view;
	}

	@Override
	public int getAllBackground() {
		// TODO Auto-generated method stub
		return R.drawable.bg_dialog_tip;
	}

}
