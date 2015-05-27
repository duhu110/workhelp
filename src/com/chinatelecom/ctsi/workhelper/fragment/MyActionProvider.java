package com.chinatelecom.ctsi.workhelper.fragment;

import com.chinatelecom.ctsi.workhelper.R;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.ActionProvider;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class MyActionProvider extends ActionProvider {

	public MyActionProvider(Context context) {
		super(context);
		System.out.println("----��ʼ�� MyActionProvider----");
	}

	@Override
	public View onCreateActionView() {

		return null;
	}

	@Override
	public void onPrepareSubMenu(SubMenu subMenu) {
		super.onPrepareSubMenu(subMenu);
		subMenu.clear();
		subMenu.add("���3��").setOnMenuItemClickListener(
				new OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						return true;
					}
				});
		subMenu.add("���1��").setOnMenuItemClickListener(
				new OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						return true;
					}
				});
		subMenu.add("���1��").setOnMenuItemClickListener(
				new OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						return true;
					}
				});
		subMenu.add("ȫ ��").setOnMenuItemClickListener(
				new OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						return true;
					}
				});
	}

	@Override
	public boolean hasSubMenu() {
		// ����ϵͳ��������Ӳ˵�
		return true;
	}
}
