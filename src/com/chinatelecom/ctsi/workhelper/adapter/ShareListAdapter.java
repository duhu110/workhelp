package com.chinatelecom.ctsi.workhelper.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinatelecom.ctsi.workhelper.R;
import com.chinatelecom.ctsi.workhelper.activity.ShareReplyActivity;
import com.chinatelecom.ctsi.workhelper.model.Comment;
import com.chinatelecom.ctsi.workhelper.model.SharedDuty;
import com.chinatelecom.ctsi.workhelper.model.User;
import com.chinatelecom.ctsi.workhelper.view.ShareCommentView;


public class ShareListAdapter extends BaseAdapter {
	private List<SharedDuty> fileList;
	private Context context;
	private LayoutInflater inflater;


	public ShareListAdapter(List<SharedDuty> fileList, Context context) {
		this.fileList = fileList;
		this.context = context;
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return fileList.size();
	}

	public Object getItem(int arg0) {
		return null;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = this.inflater.inflate(R.layout.view_share_item, null);
            //viewHolder.itemIcon = (ImageView) convertView.findViewById(R.id.);
            viewHolder.userName = (TextView) convertView
					.findViewById(R.id.share_user);
            viewHolder.shareTip = (TextView) convertView.findViewById(R.id.share_tip);
            viewHolder.comment = (TextView) convertView.findViewById(R.id.share_comment);
		    viewHolder.content = (TextView) convertView.findViewById(R.id.share_content);
		    
		    viewHolder.time = (TextView) convertView.findViewById(R.id.share_time);
		    
		    viewHolder.likeBtn = (Button) convertView.findViewById(R.id.share_like_btn);
		    viewHolder.replyBtn = (Button) convertView.findViewById(R.id.share_show_reply_btn);
		    
		    viewHolder.comments = (LinearLayout) convertView.findViewById(R.id.share_comments);
		    viewHolder.likeCountTv = (TextView) convertView.findViewById(R.id.share_like_count);
		    viewHolder.itemIcon = (ImageView) convertView.findViewById(R.id.share_icon);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final SharedDuty dutyInfo = fileList.get(position);

		
		//TypeFaceUtil.changeViewFont(context, viewHolder.userNameTv);
		String toName = "@所有人";
		if(dutyInfo.getShareTo()!=0){
			toName = "@"+User.users.get(dutyInfo.getShareFrom()).name;
		}
		viewHolder.shareTip.setText(toName);
        viewHolder.comment.setText(dutyInfo.getContent());
        User from = User.users.get(dutyInfo.getShareFrom());
        if(from==null){
        	viewHolder.itemIcon.setImageResource(R.drawable.icon_user0);
        	
        	viewHolder.userName.setText("管理员");
        }else{
        	viewHolder.userName.setText(from
        			.name);
        	viewHolder.itemIcon.setImageResource(from.iconId);
        }
        //viewHolder.checkBox.setChecked(bindEnterpriseInfo.isChecked());
        viewHolder.content.setText(dutyInfo.getDuty().getContent());
        //viewHolder.time.setText(DateFormatUtil.toreadTime(new Date(dutyInfo.getDeadline())));
		final ViewHolder t = viewHolder;
        viewHolder.likeBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(context, "like", Toast.LENGTH_LONG).show();
				dutyInfo.setLikeCount(dutyInfo.getLikeCount()+1);
				t.likeCountTv.setText(""+dutyInfo.getLikeCount());
			}
		});
        
        viewHolder.replyBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(context, "reply", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(context,ShareReplyActivity.class);
				context.startActivity(intent);
			}
		});
        
        viewHolder.comments.removeAllViews();
        for(Comment cmt:dutyInfo.getComments()){
	        ShareCommentView view = new ShareCommentView(context,null);
	        view.setValue(cmt);
	        viewHolder.comments.addView(view);
        }
        
//        ShareCommentView view1 = new ShareCommentView(context,null);
//        viewHolder.comments.addView(view1);
//        
//        ShareCommentView view2 = new ShareCommentView(context,null);
//        viewHolder.comments.addView(view2);
        
        return convertView;
	}

	public final class ViewHolder {
		public ImageView itemIcon;
        public TextView userName;
        public TextView shareTip;
        public TextView comment;
        public TextView content;
        
        public TextView time;
        public TextView likeCountTv;
        public Button likeBtn;
        public Button replyBtn;
        
        public LinearLayout comments;
        
        
        public CheckBox checkBox;
        
        
        
	}
}