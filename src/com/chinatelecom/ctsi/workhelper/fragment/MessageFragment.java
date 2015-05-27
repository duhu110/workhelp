package com.chinatelecom.ctsi.workhelper.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chinatelecom.ctsi.workhelper.R;
import com.chinatelecom.ctsi.workhelper.WHApplication;
import com.chinatelecom.ctsi.workhelper.db.DutyInfoDao;
import com.chinatelecom.ctsi.workhelper.db.SharedInfoDao;
import com.chinatelecom.ctsi.workhelper.helper.DutyInfoHelper;
import com.chinatelecom.ctsi.workhelper.model.DutyInfo;
import com.chinatelecom.ctsi.workhelper.model.PushMessage;
import com.chinatelecom.ctsi.workhelper.model.SharedDuty;
import com.chinatelecom.ctsi.workhelper.model.User;
import com.chinatelecom.ctsi.workhelper.protocol.PostFinishDutyThread;
import com.chinatelecom.ctsi.workhelper.protocol.PostMessageThread;
import com.chinatelecom.ctsi.workhelper.protocol.base.BaseListener;
import com.ctsi.logs.Logcat;

import com.chinatelecom.ctsi.workhelper.util.JsonUtil;
import com.ctsi.utils.DateUtil;

public class MessageFragment extends Fragment {

	private ListView listView;
	private List<DutyInfo> infos = new ArrayList<DutyInfo>();
	WHApplication application;

	ArrayList<User> users;

	Handler handler = new Handler();

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.tab_message_fragment, null);

		listView = (ListView) view.findViewById(R.id.ListView01);
		Log.e("dw", "" + infos.size());
		loadData();
		application = (WHApplication) getActivity().getApplication();
		DutyInfoDao.setUnfinish();
		DutyInfoHelper.newDutyListener = listener;
		return view;
	}

	private DutyInfoHelper.NewDutyListener listener = new DutyInfoHelper.NewDutyListener() {

		@Override
		public void OnNewDuty(DutyInfo info) {
			// TODO Auto-generated method stub
			if (infos != null && info != null) {
				infos.clear();
				infos.addAll(DutyInfoDao.getRecordList(DutyInfo.TYPE_TASK));
				//infos.add(0, info);
				adapter.notifyDataSetChanged();
			}
		}
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	};

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// 加载数据

	}

	public void loadData() {
		// 数据库加载数据

		infos = DutyInfoDao.getInstance().getRecordList(DutyInfo.TYPE_TASK);

		if (listView.getAdapter() == null) {
			listView.setAdapter(adapter);
			listView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {

				@Override
				public void onCreateContextMenu(ContextMenu menu, View arg1,
						ContextMenuInfo arg2) {
					// TODO Auto-generated method stub
					menu.setHeaderTitle("菜单");
					menu.add(0, 0, 0, "企业分享");
					menu.add(0, 1, 0, "好友分享");
				}
			});

		} else
			adapter.notifyDataSetChanged();
		users = new ArrayList<User>();
		for (User user : User.users.values()) {
			if (user != User.currentUser) {
				users.add(user);
			}
		}

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();

		int position = (int) info.id;
		final DutyInfo duty = infos.get(position);

		switch (item.getItemId()) {
		case 0:
			share(duty, -1);

			break;
		case 1:

			String[] items = new String[users.size()];

			for (int i = 0; i < users.size(); i++) {
				items[i] = users.get(i).name;

			}
			application.ShowSingleChoiceDialog(getActivity(), "请选择分享的好友",
					items, -1, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
							share(duty, users.get(arg1).id);
						}
					});
			break;

		default:
			break;
		}

		return true;
	}

	private void share(DutyInfo duty, int toUserId) {

		PushMessage message = new PushMessage();
		message.setType(PushMessage.TYPE_NEW_SHARED);
		SharedDuty share = new SharedDuty();

		share.setDuty(duty);
		share.setShareTo(toUserId);
		if (toUserId == -1) {
			share.setShareType(SharedDuty.TYPE_SHARE_TOALL);
		} else {
			share.setShareType(SharedDuty.TYPE_SHARE_PERSONAL);
		}
		share.setSharedTime(new Date().getTime());
		share.setId(share.getId());
		message.setSharedDuty(share);

		SharedInfoDao.save(share);
		PostMessageThread thread = new PostMessageThread(getActivity(),
				JsonUtil.toJsonStr(message), shareListener);
		thread.start();

	}

	private BaseListener shareListener = new BaseListener() {

		@Override
		public void onUnavaiableNetwork() {
			// TODO Auto-generated method stub
			application.dismissSpinnerDialog();
			application.showToast(getResources().getString(
					R.string.tips_unavaliable_network));
		}

		@Override
		public void onTimeout() {
			// TODO Auto-generated method stub
			application.dismissSpinnerDialog();
			application.showToast(getResources().getString(
					R.string.tips_timeout_network));
		}

		@Override
		public void onSuccess() {
			// TODO Auto-generated method stub

			application.dismissSpinnerDialog();
			application.showToast("完成分享");

		}

		@Override
		public void onServerException(String message) {
			// TODO Auto-generated method stub
			application.dismissSpinnerDialog();
			application.showToast(message);
		}

		@Override
		public void onPrev() {
			// TODO Auto-generated method stub
			application.showSpinnerDialog(getActivity());
		}
	};

	private BaseAdapter adapter = new BaseAdapter() {

		@Override
		public View getView(int position, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			ViewHolder view;
			if (arg1 == null)
				view = new ViewHolder();
			else
				view = (ViewHolder) arg1;
			view.setDetail(infos.get(position));
			return view;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return infos.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return infos.size();
		}
	};

	class ViewHolder extends LinearLayout {

		TextView txv_name, txv_time, txv_distance, txv_text, txv_chart_name1,
				txv_chart_text1, txv_chart_name2, txv_chart_text2,
				txv_chart_action1, txv_chart_action2, task_done_btn;
		ImageView img_pp, img_pic, img_feedback, task_done_img;

		View layout_chart;

		public ViewHolder() {
			super(getActivity());
			// TODO Auto-generated constructor stub
			getActivity().getLayoutInflater().inflate(
					R.layout.adapter_publicwall, this);
			txv_name = (TextView) findViewById(R.id.txv_name);
			txv_time = (TextView) findViewById(R.id.txv_time);
			txv_text = (TextView) findViewById(R.id.txv_text);
			txv_chart_name1 = (TextView) findViewById(R.id.txv_chart_name1);
			txv_chart_text1 = (TextView) findViewById(R.id.txv_chart_text1);
			txv_chart_name2 = (TextView) findViewById(R.id.txv_chart_name2);
			txv_chart_text2 = (TextView) findViewById(R.id.txv_chart_text2);
			task_done_btn = (TextView) findViewById(R.id.image_task_done);
			task_done_img = (ImageView) findViewById(R.id.order_wc_icon);
		}

		public void setDetail(final DutyInfo info) {
			txv_name.setText(info.getTitle());
			Date date = new Date(info.getDeadline());
			txv_time.setText(DateUtil.Date2String(date, "yyyy-MM-dd HH:mm"));
			txv_text.setText(info.getContent());

			if ((info.getStatus() == DutyInfo.STATUS_UNFINISHED)
					&& (System.currentTimeMillis() <= info.getDeadline())) {
				Log.e("dw", "未完成");
				task_done_btn.setVisibility(View.VISIBLE);
				task_done_img.setVisibility(GONE);

				task_done_img.setImageResource(R.drawable.order_wc_icon);
				task_done_btn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						// 提交服务器
						// dutyId

						Log.e("dw", "TOAST");

						new PostFinishDutyThread(getContext(), info.getId()
								+ "", new BaseListener() {
							@Override
							public void onUnavaiableNetwork() {
								// TODO Auto-generated method stub

								handler.postDelayed(new Runnable() {

									@Override
									public void run() {
										// TODO Auto-generated method stub

										application.dismissSpinnerDialog();
										application
												.showToast(getResources()
														.getString(
																R.string.tips_unavaliable_network));

									}
								}, 1000);
							}

							@Override
							public void onTimeout() {
								// TODO Auto-generated method stub

								handler.postDelayed(new Runnable() {

									@Override
									public void run() {
										// TODO Auto-generated method stub

										application.dismissSpinnerDialog();
										application
												.showToast(getResources()
														.getString(
																R.string.tips_timeout_network));

									}
								}, 1000);
							}

							@Override
							public void onSuccess() {
								// TODO Auto-generated method stub

								handler.postDelayed(new Runnable() {

									@Override
									public void run() {
										// TODO Auto-generated method stub

										info.setStatus(info.STATUS_FINISHED);
										DutyInfoDao.save(info);
										task_done_img.setVisibility(VISIBLE);
										task_done_btn.setVisibility(GONE);
										application.dismissSpinnerDialog();
										application.showToast("更新完成");

									}
								}, 1000);

							}

							@Override
							public void onServerException(final String message) {
								// TODO Auto-generated method stub
								handler.postDelayed(new Runnable() {

									@Override
									public void run() {
										// TODO Auto-generated method stub

										application.dismissSpinnerDialog();
										application.showToast(message);

									}
								}, 1000);

							}

							@Override
							public void onPrev() {
								// TODO Auto-generated method stub
								application.showSpinnerDialog(getActivity());
							}
						}).start();
						;
					}
				});
			} else if ((info.getStatus() == DutyInfo.STATUS_UNFINISHED)
					&& (System.currentTimeMillis() >= info.getDeadline())) {
				task_done_img.setVisibility(View.VISIBLE);
				task_done_img.setImageResource(R.drawable.order_cq_icon);
				task_done_btn.setVisibility(VISIBLE);
				Log.e("dw", "超期任务");
			} else if (info.getStatus() == DutyInfo.STATUS_FINISHED) {
				task_done_img.setVisibility(View.VISIBLE);
				task_done_img.setImageResource(R.drawable.order_wc_icon);
				task_done_btn.setVisibility(GONE);
				Log.e("dw", "完成任务");
			}

			else {
				task_done_img.setVisibility(View.VISIBLE);
				task_done_btn.setVisibility(GONE);
				task_done_img.setImageResource(R.drawable.order_wc_icon);
				Log.e("dw", "正在处理的任务");
			}

			//

			// new PostFinishDutyThread(getContext(), 1+"", new BaseListener() {
			//
			// @Override
			// public void onUnavaiableNetwork() {
			// // TODO Auto-generated method stub
			//
			// }
			//
			// @Override
			// public void onTimeout() {
			// // TODO Auto-generated method stub
			//
			// }
			//
			// @Override
			// public void onSuccess() {
			// Log.e("finishDuty","onSuccess");
			//
			// }
			//
			// @Override
			// public void onServerException(String message) {
			// Log.e("finishDuty", message);
			//
			// }
			//
			// @Override
			// public void onPrev() {
			// // TODO Auto-generated method stub
			//
			// }
			// });
			// }

			// }});

			// yuqi tiao

			// }else{
			// task_done_btn.setVisibility(View.GONE);
			//
			// //wancheng
			//
			// }
			//
			//
			//
			// }
			//
			//
			// }

		}

	}

	// @Override
	// public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	// // TODO Auto-generated method stub
	// // super.onCreateOptionsMenu(menu, inflater);
	// menu.clear();
	// getActivity().getMenuInflater().inflate(R.menu.message, menu);
	// }

};
