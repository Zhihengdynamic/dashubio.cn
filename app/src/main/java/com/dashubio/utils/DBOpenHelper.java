package com.dashubio.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

// 内建数据库工具类
public class DBOpenHelper extends SQLiteOpenHelper{

	// 省份表
	private static final String CREATE_DATA_T_PROVINCE = "CREATE TABLE IF NOT EXISTS DATA_T_PROVINCE ( p_id NUMBER NOT NULL, p_code VARCHAR2 (32) NOT NULL, p_name VARCHAR2 (32) NOT NULL, PRIMARY KEY (p_id) )";
	// 城市表
	private static final String CREATE_DATA_T_CITY = "CREATE TABLE IF NOT EXISTS DATA_T_CITY ( c_id NUMBER NOT NULL, c_code VARCHAR2 (32) NOT NULL, c_name VARCHAR2 (32) NOT NULL, PRIMARY KEY (c_id) )";
	public DBOpenHelper(Context context) {
		super(context, "dashu.db", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_DATA_T_PROVINCE);
		db.execSQL(CREATE_DATA_T_CITY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
