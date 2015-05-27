package com.chinatelecom.ctsi.workhelper;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class DialogHandler_ListSelector extends DialogHandler_Base {
	OnClickListener clickListener;
	String[] items;

	public DialogHandler_ListSelector(Activity activity,String title, String[] items,
			DialogInterface.OnClickListener listener) {
		super(activity,title);
		this.items = items;
		this.clickListener = listener;
		// TODO Auto-generated constructor stub
	}

	public OnClickListener getClickListener() {
		return clickListener;
	}

	public String[] getItems() {
		return items;
	}

}
