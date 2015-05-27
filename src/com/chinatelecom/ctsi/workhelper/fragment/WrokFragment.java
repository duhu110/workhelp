package com.chinatelecom.ctsi.workhelper.fragment;


import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.chinatelecom.ctsi.workhelper.R;
import com.chinatelecom.ctsi.workhelper.adapter.ShareListAdapter;
import com.chinatelecom.ctsi.workhelper.db.DutyInfoDao;
import com.chinatelecom.ctsi.workhelper.model.Comment;
import com.chinatelecom.ctsi.workhelper.model.DutyInfo;
import com.chinatelecom.ctsi.workhelper.model.SharedDuty;

public class WrokFragment extends Fragment {
	ListView listView;
	ShareListAdapter adapter;
	
	List<SharedDuty> shares = new ArrayList<SharedDuty>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
//		return super.onCreateView(inflater, container, savedInstanceState);
		View root = inflater.inflate(R.layout.tab_work_fragment, container, false);
		listView = (ListView)root.findViewById(R.id.share_list);
		adapter = new ShareListAdapter(shares, getActivity());
		listView.setAdapter(adapter);
		
		
		initData();
		return root;
	}

	private void initData() {
		// TODO Auto-generated method stub
		shares.clear();
		List<DutyInfo> dutys = DutyInfoDao.getRecordList(DutyInfo.TYPE_ALL);
		
		
		SharedDuty sd1 = new SharedDuty();
		sd1.setId(System.currentTimeMillis());
		sd1.setSharedTime(System.currentTimeMillis());
		sd1.setContent("请学习");
		sd1.setShareTo(0);
		sd1.setShareFrom(1);
		sd1.setDuty(dutys.get(dutys.size()-1));
		sd1.setLikeCount(3);
		
		
		Comment comment1 = new Comment();
		comment1.sender = 2;
		comment1.setTime(System.currentTimeMillis());
		comment1.setContent("已阅");
		
		Comment comment2 = new Comment();
		comment2.sender = 3;
		comment2.setTime(System.currentTimeMillis());
		comment2.setContent("收到");
		
		sd1.getComments().add(comment1);
		sd1.getComments().add(comment2);
		shares.add(sd1);
//		shares.add(new SharedDuty());
//		shares.add(new SharedDuty());
//		shares.add(new SharedDuty());
//		shares.add(new SharedDuty());
//		shares.add(new SharedDuty());
//		shares.add(new SharedDuty());
		
		adapter.notifyDataSetChanged();
		
	}
//	@Override
//	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//		// TODO Auto-generated method stub
////		super.onCreateOptionsMenu(menu, inflater);
//		inflater.inflate(R.menu.none, menu);
//		
//	}
	

}
