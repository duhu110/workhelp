package com.chinatelecom.ctsi.workhelper;

import java.nio.channels.AlreadyConnectedException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.AlarmManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;

import com.chinatelecom.ctsi.workhelper.db.DutyInfoDao;
import com.chinatelecom.ctsi.workhelper.fragment.CalendarFragment;
import com.chinatelecom.ctsi.workhelper.fragment.MeFragment;
import com.chinatelecom.ctsi.workhelper.fragment.MessageFragment;
import com.chinatelecom.ctsi.workhelper.fragment.WrokFragment;
import com.chinatelecom.ctsi.workhelper.model.DutyInfo;
import com.chinatelecom.ctsi.workhelper.service.AlarmService;
import com.ctsi.alarm.SingleAlarmPlan;
import com.ctsi.alarm.service.ICtsiAlarmService;

public class MainActivity extends FragmentActivity {

	// 定义FragmentTabHost对象
	private FragmentTabHost mTabHost;
	private RadioGroup mTabRg;
	private ViewPager mViewPage;
	TabsAdapter mTabsAdapter;
	private final Class[] fragments = { MessageFragment.class,
			CalendarFragment.class, WrokFragment.class, MeFragment.class };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		if (savedInstanceState != null) {
			mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	public void initService() {

		if (AlarmService.isWorked(this)) {

		} else {
			Intent intent = new Intent(MainActivity.this, AlarmService.class);
			bindService(intent, conn, Service.BIND_AUTO_CREATE);
		}

	}

	private ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
		}

		@Override
		public void onServiceConnected(ComponentName arg0, IBinder binder) {

			ICtsiAlarmService alarmService = ICtsiAlarmService.Stub
					.asInterface(binder);
			if (alarmService != null) {

			}
			try {
				List<DutyInfo> duties = DutyInfoDao.getInstance()
						.getRecordList(DutyInfo.TYPE_ALL);
				List<SingleAlarmPlan> plans = new ArrayList<SingleAlarmPlan>();
				for (DutyInfo info : duties) {

					Date now = new Date();
					if (info.getDeadline() > now.getTime()) {
						long wakeTime = 0l;
						if (info.getType() == DutyInfo.TYPE_TASK) {

							wakeTime = info.getDeadline() - 5 * 60 * 1000;
							if (wakeTime < now.getTime()) {
								wakeTime = now.getTime() + 10 * 1000;
							}

						} else {
							wakeTime = info.getDeadline();
						}

						SingleAlarmPlan p = new SingleAlarmPlan(
								AlarmService.Broadcast_alarm, AlarmManager.RTC,
								String.valueOf(info.getId()), wakeTime);
						plans.add(p);
					}

				}
				alarmService.addSinglePlans(plans);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	private void initView() {

		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager());

		mViewPage = (ViewPager) findViewById(R.id.pager);
		mTabRg = (RadioGroup) findViewById(R.id.tab_rg_menu);
		mTabsAdapter = new TabsAdapter(this, mTabHost, mViewPage, mTabRg);
		// 得到fragment的个数
		int count = 4;
		for (int i = 0; i < count; i++) {
			// 为每一个Tab按钮设置图标、文字和内容
			TabSpec tabSpec = mTabHost.newTabSpec(i + "").setIndicator(i + "");
			// 将Tab按钮添加进Tab选项卡中
			mTabHost.addTab(tabSpec, fragments[i], null);

			mTabsAdapter.addTab(mTabHost.newTabSpec(i + "")
					.setIndicator(i + ""), fragments[i], null);
		}

		mTabRg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.tab_rb_1:
					mTabHost.setCurrentTab(0);
					break;
				case R.id.tab_rb_2:
					mTabHost.setCurrentTab(1);

					break;
				case R.id.tab_rb_3:

					mTabHost.setCurrentTab(2);
					break;
				case R.id.tab_rb_4:

					mTabHost.setCurrentTab(3);
					break;

				default:
					break;
				}
			}
		});
		// mTabHost.setCurrentTab(0);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("tab", mTabHost.getCurrentTabTag());
	}

	/**
	 * This is a helper class that implements the management of tabs and all
	 * details of connecting a ViewPager with associated TabHost. It relies on a
	 * trick. Normally a tab host has a simple API for supplying a View or
	 * Intent that each tab will show. This is not sufficient for switching
	 * between pages. So instead we make the content part of the tab host 0dp
	 * high (it is not shown) and the TabsAdapter supplies its own dummy view to
	 * show as the tab content. It listens to changes in tabs, and takes care of
	 * switch to the correct paged in the ViewPager whenever the selected tab
	 * changes.
	 */
	public static class TabsAdapter extends FragmentPagerAdapter implements
			TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
		private final Context mContext;
		private final TabHost mTabHost;
		private final ViewPager mViewPager;
		private final RadioGroup mTabRg;
		private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

		static final class TabInfo {
			private final String tag;
			private final Class<?> clss;
			private final Bundle args;

			TabInfo(String _tag, Class<?> _class, Bundle _args) {
				tag = _tag;
				clss = _class;
				args = _args;
			}
		}

		static class DummyTabFactory implements TabHost.TabContentFactory {
			private final Context mContext;

			public DummyTabFactory(Context context) {
				mContext = context;
			}

			@Override
			public View createTabContent(String tag) {
				View v = new View(mContext);
				v.setMinimumWidth(0);
				v.setMinimumHeight(0);
				return v;
			}
		}

		public TabsAdapter(FragmentActivity activity, TabHost tabHost,
				ViewPager pager, RadioGroup tabRg) {
			super(activity.getSupportFragmentManager());
			mContext = activity;
			mTabHost = tabHost;
			mViewPager = pager;
			mTabRg = tabRg;
			mTabHost.setOnTabChangedListener(this);
			mViewPager.setAdapter(this);
			mViewPager.setOnPageChangeListener(this);
		}

		public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
			tabSpec.setContent(new DummyTabFactory(mContext));
			String tag = tabSpec.getTag();

			TabInfo info = new TabInfo(tag, clss, args);
			mTabs.add(info);
			mTabHost.addTab(tabSpec);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return mTabs.size();
		}

		@Override
		public Fragment getItem(int position) {
			TabInfo info = mTabs.get(position);
			return Fragment.instantiate(mContext, info.clss.getName(),
					info.args);
		}

		@Override
		public void onTabChanged(String tabId) {
			int position = mTabHost.getCurrentTab();
			mViewPager.setCurrentItem(position);
			((RadioButton) mTabRg.getChildAt(position)).setChecked(true);
		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
		}

		@Override
		public void onPageSelected(int position) {
			// Unfortunately when TabHost changes the current tab, it kindly
			// also takes care of putting focus on it when not in touch mode.
			// The jerk.
			// This hack tries to prevent this from pulling focus out of our
			// ViewPager.
			TabWidget widget = mTabHost.getTabWidget();
			int oldFocusability = widget.getDescendantFocusability();
			widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
			mTabHost.setCurrentTab(position);
			widget.setDescendantFocusability(oldFocusability);
		}

		@Override
		public void onPageScrollStateChanged(int state) {
		}
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub

		menu.clear();
		MenuInflater inflater = this.getMenuInflater();
		switch (mTabHost.getCurrentTab()) {
		case 0:
			inflater.inflate(R.menu.message, menu);
			break;

		case 1:
			inflater.inflate(R.menu.main, menu);
			break;

		case 2:
			inflater.inflate(R.menu.none, menu);
			break;
		}
		return super.onPrepareOptionsMenu(menu);
	}
}
