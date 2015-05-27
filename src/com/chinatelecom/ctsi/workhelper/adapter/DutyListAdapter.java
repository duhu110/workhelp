package com.chinatelecom.ctsi.workhelper.adapter;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinatelecom.ctsi.workhelper.R;
import com.chinatelecom.ctsi.workhelper.model.DutyInfo;
import com.chinatelecom.ctsi.workhelper.util.DateFormatUtil;


public class DutyListAdapter extends BaseAdapter {
	private List<DutyInfo> fileList;
	private Context context;
	private LayoutInflater inflater;


	public DutyListAdapter(List<DutyInfo> fileList, Context context) {
		this.fileList = fileList;
		this.context = context;
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return fileList.size();
	}

	public Object getItem(int arg0) {
		return null;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = this.inflater.inflate(R.layout.view_calendar_duty_item, null);
            //viewHolder.itemIcon = (ImageView) convertView.findViewById(R.id.);
            viewHolder.text = (TextView) convertView
					.findViewById(R.id.textview);
            viewHolder.status = (TextView) convertView.findViewById(R.id.status);
		    viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
            viewHolder.checkBox.setChecked(true);
            viewHolder.checkBox.setVisibility(View.INVISIBLE);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		DutyInfo dutyInfo = fileList.get(position);

		viewHolder.text.setText(dutyInfo.getContent());
		//TypeFaceUtil.changeViewFont(context, viewHolder.userNameTv);
        viewHolder.status.setText(DateFormatUtil.toreadTime(new Date(dutyInfo.getDeadline())));
        //viewHolder.checkBox.setChecked(bindEnterpriseInfo.isChecked());
		return convertView;
	}

	public final class ViewHolder {
		public ImageView itemIcon;
        public TextView text;
        public TextView status;
        public CheckBox checkBox;
	}
}