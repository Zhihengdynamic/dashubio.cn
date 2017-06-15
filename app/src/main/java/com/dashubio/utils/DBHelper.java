package com.dashubio.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DBHelper继承了SQLiteOpenHelper，作为维护和管理数据库的基类
 */
public class DBHelper  extends SQLiteOpenHelper{
	
	public static final String DB_NAME = "user.db";
	public static final String DB_TABLE_NAME = "info";
	private static final int DB_VERSION=1;
	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}
	//数据第一次创建的时候会调用onCreate
	@Override
	public void onCreate(SQLiteDatabase db) {		
		//创建表
		  db.execSQL("CREATE TABLE IF NOT EXISTS info"+"(_id INTEGER PRIMARY KEY AUTOINCREMENT, name STRING,pwds STRING)");
		  db.execSQL("CREATE TABLE IF NOT EXISTS user"+"(_id INTEGER PRIMARY KEY AUTOINCREMENT,user_id,Fullname STRING,card_id STRING,phone STRING)");
		  db.execSQL("CREATE TABLE IF NOT EXISTS xueya"+"(_id,user_id,gaoya STRING,diya STRING)");
		  db.execSQL("CREATE TABLE IF NOT EXISTS xueyang"+"(_id,user_id,xueyang STRING,xinlv STRING)");
		  db.execSQL("CREATE TABLE IF NOT EXISTS tiwen"+"(_id,user_id,tiwen STRING)");
		  db.execSQL("CREATE TABLE IF NOT EXISTS xindian"+"(_id,user_id,xinlv STRING,RRmax STRING,RRmin STRING,xinlvbianyi STRING,mood STRING)");
		  db.execSQL("CREATE TABLE IF NOT EXISTS ganshi"+"(_id,user_id,message STRING)");
		  db.execSQL("CREATE TABLE IF NOT EXISTS niaoye"+"(_id,user_id,URO STRING,BLD STRING,BIL STRING,KET STRING,GLU STRING,PRO STRING,PH STRING,NIT STRING,LEU STRING,SG STRING,VC STRING)");
//		  db.execSQL("CREATE TABLE IF NOT EXISTS niaoye"+"(user_id,id STRING,User STRING,Year STRING,Month STRING,Date STRING,Hour STRING,Min STRING,Sec STRING,Item STRING,URO STRING,BLD STRING,BIL STRING,KET STRING,GLU STRING,PRO STRING,PH STRING,NIT STRING,LEU STRING,SG STRING,VC STRING,URO_Real STRING,BLD_Real STRING,BIL_Real STRING,KET_Real STRING,GLU_Real STRING,PRO_Real STRING,PH_Real STRING,NIT_Real STRING,LEU_Real STRING,SG_Real STRING,VC_Real STRING,)");


	}
	//数据库第一次创建时onCreate方法会被调用，我们可以执行创建表的语句，当系统发现版本变化之后，会调用onUpgrade方法，我们可以执行修改表结构等语句
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//在表info中增加一列other
		//db.execSQL("ALTER TABLE info ADD COLUMN other STRING");  
	}
	

}
