package com.chinatelecom.ctsi.workhelper.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.chinatelecom.ctsi.workhelper.R;
import com.chinatelecom.ctsi.workhelper.activity.MeAbout;
import com.chinatelecom.ctsi.workhelper.activity.MeHuoyue;
import com.chinatelecom.ctsi.workhelper.activity.MePersonInfo;
import com.chinatelecom.ctsi.workhelper.model.User;

public class MeFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_me_fragment, container, false);
		ImageView imageView = (ImageView) view.findViewById(R.id.me_personicon);
		TextView textView = (TextView) view.findViewById(R.id.me_name);
		String text = User.currentUser.name;
		if (text != null) {
			textView.setText(text);
		}
		int i = User.currentUser.iconId;
		if (i != 0) {
			imageView.setImageResource(i);
		}
		RelativeLayout rlinfo = (RelativeLayout) view.findViewById(R.id.me_personinfo);
		rlinfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), MePersonInfo.class);
				startActivity(intent);
			}
		});
		RelativeLayout rlhy = (RelativeLayout) view.findViewById(R.id.me_huoyue);
		rlhy.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), MeHuoyue.class);
				startActivity(intent);				
			}
		});
		RelativeLayout rlabout = (RelativeLayout) view.findViewById(R.id.me_about);
		rlabout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), MeAbout.class));
				
			}
		});
		return view;
	}

}
