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
		System.out.println("----初始化 MyActionProvider----");
	}

	@Override
	public View onCreateActionView() {

		return null;
	}

	@Override
	public void onPrepareSubMenu(SubMenu subMenu) {
		super.onPrepareSubMenu(subMenu);
		subMenu.clear();
		subMenu.add("最近3天").setOnMenuItemClickListener(
				new OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						return true;
					}
				});
		subMenu.add("最近1周").setOnMenuItemClickListener(
				new OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						return true;
					}
				});
		subMenu.add("最近1月").setOnMenuItemClickListener(
				new OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						return true;
					}
				});
		subMenu.add("全 部").setOnMenuItemClickListener(
				new OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						return true;
					}
				});
	}

	@Override
	public boolean hasSubMenu() {
		// 告诉系统，这个有子菜单
		return true;
	}
}
