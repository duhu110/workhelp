package com.chinatelecom.ctsi.workhelper.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinatelecom.ctsi.workhelper.R;
import com.chinatelecom.ctsi.workhelper.model.Comment;
import com.chinatelecom.ctsi.workhelper.model.User;

public class ShareCommentView extends LinearLayout {



	/**Item内容区域*/
	private TextView mContent;
	 private ImageView mIcon;
	/**标题View*/
	private TextView mTitle;
	/**概要View*/
	private TextView mSummary;
	
	private String mTitleText,mSummaryText;
   
	/**
	 * @param context
	 * @param attrs
	 */
	public ShareCommentView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.view_share_reply, this, true);
		mIcon = (ImageView) findViewById(R.id.share_comment_icon);
		mTitle = (TextView) findViewById(R.id.share_comment_user);
		mContent = (TextView) findViewById(R.id.share_comment_content);
		mSummary = (TextView) findViewById(R.id.share_comment_time);
		
	}
	
	public void setValue(Comment comment){
		mIcon.setImageResource(User.users.get( comment.getSender()).iconId);
		setTitleText(User.users.get( comment.getSender()).name);
		mContent.setText(comment.getContent());
		mSummary.setText("5-18 20:01");
	} 

	/**
	 * 设置标题信息
	 * @param text 
	 */
	public void setTitleText(String text) {
		mTitleText = text;
		if(text == null) {
			mTitle.setText("");
			return ;
		}
		mTitle.setText(mTitleText);
	}
	/**
	 * @param text 
	 * 
	 */
	public void setDetailText(String text) {
		mSummaryText = text;
        if(mSummaryText == null) {
            mSummary.setText("");
            return ;
        }
        mSummary.setText(mSummaryText);
	}
	

}
