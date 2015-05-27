package com.chinatelecom.ctsi.workhelper.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.chinatelecom.ctsi.workhelper.R;

public class ShareReplyActivity extends Activity {
	private Context context;
	
	private EditText text;
	private CheckBox checkbox;
	private Button commit;
	
	private int dutyId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share_reply);
		context = this;
		
		dutyId = getIntent().getIntExtra("id", -1);
		
		
		
		initView();
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		text =(EditText) findViewById(R.id.share_reply_et);
		
		checkbox = (CheckBox) findViewById(R.id.share_reply_cb);
		
		commit = (Button) findViewById(R.id.share_reply_commit);
		
		commit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String content = text.getText().toString().trim();
				
				if(TextUtils.isEmpty(content)){
					Toast.makeText(context, "请输入回复内容", Toast.LENGTH_LONG).show();
				}
				finish();
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			break;

		}
		return true;
	}
}
