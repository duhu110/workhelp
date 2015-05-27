package com.chinatelecom.ctsi.workhelper.activity;

import com.chinatelecom.ctsi.workhelper.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends Activity {
	Handler handler = new Handler();
	ImageView img, img_small;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_splash);
		img = (ImageView) findViewById(R.id.img);
		img_small = (ImageView) findViewById(R.id.img_small);
		img.setVisibility(View.GONE);
		img_small.setVisibility(View.GONE);
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				img.setVisibility(View.VISIBLE);
				img.startAnimation(AnimationUtils.loadAnimation(
						SplashActivity.this, R.anim.anim_splash));
				// Intent intent = new Intent(SplashActivity.this,
				// LoginActivity.class);
				// startActivity(intent);

				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						// Intent intent = new Intent(SplashActivity.this,
						// LoginActivity.class);
						// startActivity(intent);
						// finish();
						img_small.setVisibility(View.VISIBLE);
						img_small.startAnimation(AnimationUtils.loadAnimation(
								SplashActivity.this, R.anim.anim_splash_small));
						
						handler.postDelayed(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								Intent intent = new Intent(SplashActivity.this,
										LoginActivity.class);
								startActivity(intent);
								finish();
							}
						}, 1500);
						
					}
				}, 2200);

			}
		}, 1000);
	}

}
