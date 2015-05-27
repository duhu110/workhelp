package com.chinatelecom.ctsi.workhelper;

import java.util.List;

import com.ctsi.utils.Utils;

import android.R.anim;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.AdapterView.OnItemClickListener;

public class Dialog_SingleSelect<T extends Object> extends Dialog_Otherbase {
	List<KeyValuePair<T>> datasource;
	DialogInterface.OnClickListener listener;
	T defaultValue;
	RadioGroup rdg_items;
	Context mContext;

	private RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub

		}
	};
	private static final int MAX_LENGTH = 5;

	public Dialog_SingleSelect(Context context, String title,
			List<KeyValuePair<T>> datasource, T defaultValue,
			DialogInterface.OnClickListener listener) {
		super(context, title);
		this.mContext = context;
		this.datasource = datasource;
		this.listener = listener;
		this.defaultValue = defaultValue;
		DialogInterface.OnClickListener l = null;
		setButton("取消", l);
		rdg_items = new RadioGroup(mContext);
		rdg_items.setOrientation(LinearLayout.VERTICAL);

		// rdg_items.setOnCheckedChangeListener(onCheckedChangeListener);
	}

	@Override
	public int getWhich() {
		// TODO Auto-generated method stub
		return 0;
	}

	boolean loaded = false;

	private void initView() {
		rdg_items.removeAllViews();
		loaded = false;
		// TODO Auto-generated constructor stub
		if (datasource != null) {
			for (int i = 0; i < datasource.size(); i++) {
				KeyValuePair<T> item = datasource.get(i);

				RadioButton rdb_item = (RadioButton) View.inflate(mContext,
						R.layout.adapter_dialog_singleselect, null);
				rdb_item.setId(i);
				rdg_items.addView(rdb_item, new LinearLayout.LayoutParams(
						LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
				View line = new View(mContext);
				line.setBackgroundColor(Color.GRAY);
				rdg_items.addView(line, new LinearLayout.LayoutParams(
						LayoutParams.FILL_PARENT, 2));
				rdb_item.setText(item.getKey());
				if (item.getValue().equals(defaultValue)) {
					rdb_item.setChecked(true);
				} else {
					rdb_item.setChecked(false);
				}
				rdb_item.setOnClickListener(new onItemClickListener(i));
			}
		}
		loaded = true;
	}

	@Override
	public View getMidView() {
		// TODO Auto-generated method stub

		return rdg_items;
	}

	@Override
	protected void InitValues() {
		// TODO Auto-generated method stub
		super.InitValues();
		initView();
		if (datasource.size() > MAX_LENGTH) {
			android.view.ViewGroup.LayoutParams params = layout_body
					.getLayoutParams();
			params.height = Utils.convertDip2Px(mContext, 50)
					* MAX_LENGTH;
			layout_body.setLayoutParams(params);
		}
	}

	class ViewHolder extends LinearLayout {
		RadioButton rdb_item;

		public ViewHolder() {
			super(mContext);
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// TODO Auto-generated constructor stub
			inflater.inflate(R.layout.adapter_dialog_singleselect, this);
			rdb_item = (RadioButton) findViewById(R.id.rdb_item);
		}

		public void setText(CharSequence s) {
			rdb_item.setText(s);
		}

		public void setChecked(boolean checked) {
			rdb_item.setChecked(checked);
		}

	}

	class onItemClickListener implements View.OnClickListener {
		int which;

		public onItemClickListener(int witch) {
			// TODO Auto-generated constructor stub
			this.which = witch;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (!loaded)
				return;
			if (listener != null) {
				listener.onClick(instance, which);
			}
			defaultValue = datasource.get(which).getValue();
			Log.v("test", defaultValue.toString());
			instance.dismiss();
		}

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

}
