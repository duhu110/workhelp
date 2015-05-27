package com.chinatelecom.ctsi.workhelper.activity;

import com.chinatelecom.ctsi.workhelper.R;
import com.chinatelecom.ctsi.workhelper.model.User;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class MePersonInfo extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.me_personinfo);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		ImageView imageView = (ImageView) findViewById(R.id.me_personicon);
		TextView textView = (TextView) findViewById(R.id.me_personinfonamename);
		String text = User.currentUser.name;
		if (text != null) {
			textView.setText(text);
		}
		int i = User.currentUser.iconId;
		if (i != 0) {
			imageView.setImageResource(i);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
		default:
			return super.onOptionsItemSelected(item);
		}
		
	}
}
