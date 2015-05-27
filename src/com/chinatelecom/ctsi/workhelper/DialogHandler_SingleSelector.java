package com.chinatelecom.ctsi.workhelper;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class DialogHandler_SingleSelector extends DialogHandler_Base {
	String[] items;
	int selectedindex;

	OnClickListener selectListener;

	public DialogHandler_SingleSelector(Activity activity, String title,
			String[] items, int selectedindex,
			DialogInterface.OnClickListener onSelectListener) {
		super(activity, title);
		this.items = items;
		this.selectedindex = selectedindex;
		this.selectListener = onSelectListener;
		// TODO Auto-generated constructor stub
	}

	public String[] getItems() {
		return items;
	}

	public int getSelectedindex() {
		return selectedindex;
	}

	public OnClickListener getSelectListener() {
		return selectListener;
	}

}
