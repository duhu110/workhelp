package com.chinatelecom.ctsi.workhelper.model;

import java.util.HashMap;

import com.chinatelecom.ctsi.workhelper.R;

public class User {

	public static final HashMap<Integer,User> users = new HashMap<Integer,User>();
	public static User currentUser = null;
	static {

		users.put(1,new User(1, "李四", "1890022323", "1",R.drawable.icon_user1));
		users.put(2,new User(2, "王五", "1333333332", "1",R.drawable.icon_user2));
		users.put(3,new User(3, "赵六", "1891234567", "1",R.drawable.icon_user3));
		currentUser = users.get(1);
	}

	public int id;

	public String name;

	public String mobile;

	public String password;
	
	public int iconId;
	
	public User(int id, String name, String mobile, String password,int iconId) {
		super();
		this.id = id;
		this.name = name;
		this.mobile = mobile;
		this.password = password;
		this.iconId = iconId;
	}

}
