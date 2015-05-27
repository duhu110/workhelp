package com.chinatelecom.ctsi.workhelper.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ScrollView;

import com.chinatelecom.ctsi.workhelper.R;
import com.chinatelecom.ctsi.workhelper.activity.AddScheduleActivity;
import com.chinatelecom.ctsi.workhelper.adapter.DutyListAdapter;
import com.chinatelecom.ctsi.workhelper.model.DutyInfo;
import com.chinatelecom.ctsi.workhelper.pushutil.PushStatusActivity;
import com.chinatelecom.ctsi.workhelper.util.JsonUtil;
import com.chinatelecom.ctsi.workhelper.view.QuickReturnListView;
import com.chinatelecom.ctsi.workhelper.view.SwipeViewFlipper;
import com.chinatelecom.ctsi.workhelper.view.SwipeViewFlipper.OnViewFlipperListener;
import com.squareup.timessquare.CalendarPickerView;

public class CalendarFragment extends Fragment implements OnViewFlipperListener {
	private CalendarPickerView calendarView;
	private View mHeader;
	private DutyListAdapter adapter;
	private QuickReturnListView dutyList;
	private List<Date> dates = new ArrayList<Date>();
	private List<DutyInfo> dutys = new ArrayList<DutyInfo>();

	private View coverView;
	private static int currentMonth;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		currentMonth = Calendar.getInstance().get(Calendar.MONTH);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// return super.onCreateView(inflater, container, savedInstanceState);

		View rootView = inflater.inflate(R.layout.tab_calendar_fragment,
				container, false);

		SwipeViewFlipper swiper = (SwipeViewFlipper) rootView
				.findViewById(R.id.calendar_swipe_view);
		// 给ViewFlipper绑定自定义的滑动监听器
		swiper.setOnViewFlipperListener(this);
		// 初始化页面数据，即View
		swiper.addView(createView());

		// calendarView = (CalendarPickerView) mHeader
		// .findViewById(R.id.calendar_view);
		dutyList = (QuickReturnListView) rootView
				.findViewById(R.id.calendar_list);
		// dutyList.addHeaderView(mHeader);
		dutyList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						PushStatusActivity.class);
				getActivity().startActivity(intent);
			}

		});

		// coverView.setOnTouchListener(new OnSwipeTouchListener(getActivity())
		// {
		// public void onSwipeTop() {
		// //Toast.makeText(getActivity(), "top", Toast.LENGTH_SHORT).show();
		// }
		//
		// public void onSwipeRight() {
		// Log.d("currentMonth", "right"+currentMonth);
		// currentMonth--;
		// initData();
		// }
		//
		// public void onSwipeLeft() {
		// Log.d("currentMonth", "right"+currentMonth);
		// currentMonth++;
		// initData();
		// // Toast.makeText(getActivity(), "left", Toast.LENGTH_SHORT)
		// // .show();
		// }
		//
		// public void onSwipeBottom() {
		// // Toast.makeText(getActivity(), "bottom", Toast.LENGTH_SHORT)
		// // .show();
		// }
		//
		// public boolean onTouch(View v, MotionEvent event) {
		// return gestureDetector.onTouchEvent(event);
		// }
		// });

		adapter = new DutyListAdapter(dutys, getActivity());

		dutyList.setAdapter(adapter);

		// 获取当前月第一天：
		initData();
		return rootView;
	}

	private void initData() {
		Calendar firstDay = Calendar.getInstance();
		firstDay.set(Calendar.MONTH, currentMonth);
		firstDay.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天

		// 获取当前月最后一天
		Calendar lastDay = Calendar.getInstance();
		lastDay.set(Calendar.MONTH, currentMonth);
		// lastDay.set(Calendar.DAY_OF_MONTH,
		// lastDay.getActualMaximum(Calendar.DAY_OF_MONTH));
		lastDay.set(Calendar.MONTH, currentMonth + 1);
		lastDay.set(Calendar.DAY_OF_MONTH, 1);
		Date today = new Date();

		// 有日程的日期
		Calendar tDay = Calendar.getInstance();
		tDay.set(Calendar.DAY_OF_MONTH, 5);
		Calendar t1Day = Calendar.getInstance();
		t1Day.set(Calendar.DAY_OF_MONTH, 15);

		dates.add(tDay.getTime());
		dates.add(t1Day.getTime());
		// 没法显示下个月
		Calendar t2Day = Calendar.getInstance();
		t2Day.add(Calendar.MONTH, 1);
		t2Day.set(Calendar.DAY_OF_MONTH, 2);
		dates.add(t2Day.getTime());

		calendarView.init(firstDay.getTime(), lastDay.getTime())
				.withHighlightedDates(dates).withSelectedDate(today);

		DutyInfo d1 = new DutyInfo();
		d1.setContent("拜访中燃刘主任");
		d1.setDeadline(tDay.getTimeInMillis());
		dutys.add(d1);

		DutyInfo d2 = new DutyInfo();
		d2.setContent("提交进度计划文档");
		d2.setDeadline(tDay.getTimeInMillis() + 60 * 60 * 1000);
		dutys.add(d2);

		DutyInfo d3 = new DutyInfo();
		d3.setContent("提交进度计划文档");
		d3.setDeadline(tDay.getTimeInMillis() + 60 * 60 * 1000);
		dutys.add(d3);
		DutyInfo d4 = new DutyInfo();
		d4.setContent("提交进度计划文档");
		d4.setDeadline(tDay.getTimeInMillis() + 60 * 60 * 1000);
		dutys.add(d4);
		DutyInfo d5 = new DutyInfo();
		d5.setContent("提交进度计划文档");
		d5.setDeadline(tDay.getTimeInMillis() + 60 * 60 * 1000);
		dutys.add(d5);
		DutyInfo d6 = new DutyInfo();
		d6.setContent("提交进度计划文档");
		d6.setDeadline(tDay.getTimeInMillis() + 60 * 60 * 1000);
		dutys.add(d6);
		DutyInfo d7 = new DutyInfo();
		d7.setContent("提交进度计划文档");
		d7.setDeadline(tDay.getTimeInMillis() + 60 * 60 * 1000);
		dutys.add(d7);
		Log.d("JSON", JsonUtil.toJsonStr(d2));

	}

	private View createView() {
		// TODO Auto-generated method stub
		LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
		ScrollView resultView = (ScrollView) layoutInflater.inflate(
				R.layout.filpper_view, null);
		calendarView = (CalendarPickerView) resultView
				.findViewById(R.id.calendar_view);

		return resultView;

	}

	@Override
	public View getNextView() {
		// TODO Auto-generated method stub
		currentMonth++;
		return createView();
	}

	@Override
	public View getPreviousView() {
		// TODO Auto-generated method stub
		currentMonth--;
		return createView();
	}

//	@Override
//	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//		menu.clear();
//		getActivity().getMenuInflater().inflate(R.menu.main, menu);
//
//	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		// noinspection SimplifiableIfStatement
		if (id == R.id.action_plus) {
			Intent intent = new Intent(getActivity(), AddScheduleActivity.class);
			getActivity().startActivity(intent);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

}
