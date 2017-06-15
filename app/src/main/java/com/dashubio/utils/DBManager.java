package com.dashubio.utils;

/**
 * Created by zzb on 2017/4/13.
 */

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.contec.jar.BC401.BC401_Struct;
import com.dashubio.gson.UserGet;
import com.dashubio.model.IDCard;
import com.dashubio.model.ucenter.SecondUser;

/**
 *DBManager是建立在DBHelper之上，封装了常用的业务方法
 */
public class DBManager {

    private DBHelper helper;
    private SQLiteDatabase db;
    public DBManager(Context context){
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    /**
     * 向表info中增加一个成员信息
     * @param memberInfo
     */
    public void add(List<MemberInfo> memberInfo) {
        db.beginTransaction();// 开始事务
        try {
            for (MemberInfo info : memberInfo) {
                // 向表info中插入数据
                db.execSQL("INSERT INTO info VALUES(null,?,?)", new Object[] { info.name,info.pwds});
            }
            db.setTransactionSuccessful();// 事务成功
        } finally {
            db.endTransaction();// 结束事务
        }
    }



    /**
     * 向表user中增加一个成员信息
     *
     * @param users
     */
    public void adduser(List<SecondUser> users) {
        db.beginTransaction();// 开始事务
        try {
            for (SecondUser user : users) {
                // 向表info中插入数据
                db.execSQL("INSERT INTO user VALUES(null,?)", new Object[] { user.getFullname()});
            }
            db.setTransactionSuccessful();// 事务成功
        } finally {
            db.endTransaction();// 结束事务
        }
    }





    /**
     * @param name
     */
    public void add(String name,String pwds) {
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("pwds", pwds);
        db.insert(DBHelper.DB_TABLE_NAME, null, cv);
    }


    //体温数据存储
    public void addtiwen(String _id,String user_id,String result) {
        ContentValues cv = new ContentValues();
        cv.put("_id", _id);
        cv.put("user_id", user_id);
        cv.put("tiwen", result);
        db.insert("tiwen", null, cv);
    }

    //血氧数据库存储
    public void addxueyang(String _id,String user_id,String xueyang,String xinlv) {
        ContentValues cv = new ContentValues();
        cv.put("user_id", user_id);
        cv.put("_id", _id);
        cv.put("xueyang", xueyang);
        cv.put("xinlv", xinlv);
        db.insert("xueyang", null, cv);
    }

    //血压数据库存储
    public void addxueya(String _id,String user_id,String gaoya,String diya) {
        ContentValues cv = new ContentValues();
        cv.put("_id", _id);
        cv.put("user_id", user_id);
        cv.put("gaoya", gaoya);
        cv.put("diya", diya);
        db.insert("xueya", null, cv);
    }


    //心电数据库存储
    public void addxindian(String _id,String user_id,String xinlv,String RRmax,String RRmin,String xinlvbianyi,String mood) {
        ContentValues cv = new ContentValues();
        cv.put("_id", _id);
        cv.put("user_id", user_id);
        cv.put("xinlv", xinlv);
        cv.put("RRmax", RRmax);
        cv.put("RRmin", RRmin);
        cv.put("xinlvbianyi", xinlvbianyi);
        cv.put("mood", mood);
        db.insert("xindian", null, cv);
    }



    public void addUserName(String name,String user_id,String card_id,String phone) {
        ContentValues cv = new ContentValues();
        cv.put("Fullname", name);
        cv.put("user_id", user_id);
        cv.put("card_id", card_id);
        cv.put("phone", phone);
        db.insert("user", null, cv);
    }


    public void addGanshi(String _id,String id,String message) {
        ContentValues cv = new ContentValues();
        cv.put("_id", _id);
        cv.put("user_id", id);
        cv.put("message", message);
        db.insert("ganshi", null, cv);
    }

    /**
     * 尿液数据存储
     * */
    public void addNiaoye(String _id,String user_id,String URO,String BLD,String BIL,String KET,String GLU,String PRO,String PH,String NIT,String LEU, String SG,String VC) {
        ContentValues cv = new ContentValues();
        cv.put("_id", _id);
        cv.put("user_id", user_id);
        cv.put("URO", URO);
        cv.put("BLD", BLD);
        cv.put("BIL", BIL);
        cv.put("KET", KET);
        cv.put("GLU", GLU);
        cv.put("PRO", PRO);
        cv.put("PH", PH);
        cv.put("NIT", NIT);
        cv.put("LEU", LEU);
        cv.put("SG", SG);
        cv.put("VC", VC);
        db.insert("niaoye", null, cv);
    }


    /**
     * 通过name来删除数据
     *
     * @param name
     */
    public void delData(String name) {
        // ExecSQL("DELETE FROM info WHERE name ="+"'"+name+"'");
        String[] args = { name };
        db.delete(DBHelper.DB_TABLE_NAME, "name=?", args);

    }

    /**
     * 清空数据
     */
    public void clearData() {
        ExecSQL("DELETE FROM info");
    }


    public void clearDataXueYa() {
        ExecSQL("DELETE FROM xueya");
    }

    public void clearDataTiWen() {
        ExecSQL("DELETE FROM tiwen");
    }

    public void clearDataXueYang() {
        ExecSQL("DELETE FROM xueyang");
    }

    public void clearDataXinDian() {
        ExecSQL("DELETE FROM xindian");
    }

    public void clearDataGanShi() {
        ExecSQL("DELETE FROM ganshi");
    }

    public void clearDataNiaoYe() {
        ExecSQL("DELETE FROM niaoye");
    }

    public void clearUser() {
        String sql = "DELETE FROM user";
        try {
            db.execSQL(sql);
            revertSeq(db);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void revertSeq(SQLiteDatabase db) {
        String sql = "UPDATE sqlite_sequence SET seq = 0 WHERE name = 'user'";
        db.execSQL(sql);
    }



    /**
     * 通过名字查询信息,返回所有的数据
     *
     * @param name
     */
    public ArrayList<MemberInfo> searchData(final String name,final String pwd) {
        String sql = "SELECT * FROM info WHERE name =" + "'" + name + "'"+"and pwds ="+ "'" + pwd + "'";
        return ExecSQLForMemberInfo(sql);
    }

    public ArrayList<MemberInfo> searchLoginData() {
        String sql = "SELECT * FROM info ";
        return ExecSQLForLoginData(sql);
    }

    public ArrayList<SecondUser> searchUserData() {
        String sql = "SELECT * FROM user ";
        return ExecSQLForSecondUser(sql);
    }


    public ArrayList<IDCard> searchUserIDCard() {
        String sql = "SELECT * FROM user WHERE user_id ISNULL";
        return ExecSQLForIDCard(sql);
    }

    public ArrayList<Tiwen> searchTiwenData() {
        String sql = "SELECT * FROM tiwen";
        return ExecSQLForTiwen(sql);
    }

    public ArrayList<UserGet.UserBean> searchUserData2() {
        String sql = "SELECT * FROM user";
        return ExecSQLForSecondUser2(sql);
    }

    public ArrayList<SecondUser2> searchUserAllId() {
        String sql = "SELECT * FROM user";
        return ExecSQLForSecondUserId3(sql);
    }


    public ArrayList<SecondUser2> searchUserMid(final String id) {
        String sql = "SELECT * FROM user WHERE _id =" + "'" + id + "'";
        return ExecSQLForSecondUserMid(sql);
    }

    public ArrayList<SecondUser> searchUserId(final String _id) {
        String sql = "SELECT * FROM user WHERE _id =" + "'" + _id + "'";
        return ExecSQLForSecondUserId2(sql);
    }

    public ArrayList<MemberInfo> searchAllData() {
        String sql = "SELECT * FROM info";
        return ExecSQLForMemberInfo(sql);
    }

    public ArrayList<XurYaEntity> searchXueYa() {
        String sql = "SELECT * FROM xueya";
        return ExecSQLForXueYa(sql);
    }

    public ArrayList<XueYangEntity> searchXueYang() {
        String sql = "SELECT * FROM xueyang";
        return ExecSQLForXueYang(sql);
    }

    public ArrayList<XinDianEntity> searchXinDidan() {
        String sql = "SELECT * FROM xindian";
        return ExecSQLForXinDian(sql);
    }

    public ArrayList<GanShi> searchGanShi() {
        String sql = "SELECT * FROM ganshi";
        return ExecSQLForGanShi(sql);
    }

    public ArrayList<BC401_Struct> searchNiaoYe() {
        String sql = "SELECT * FROM niaoye";
        return ExecSQLForNiaoYe(sql);
    }







    /**
     * 通过用户Id来修改值
     */
    public void upDateHealthy(String user_id,String gaoya,String diya,String xueyang,String tiwen) {
        String sql = "UPDATE healthy SET " + gaoya + " =" + " " + "'" + diya+ " =" + " " + "'" + xueyang + " =" + " " + "'" +tiwen   + "'" + " WHERE user_id =" + "'" + user_id
                + "'";
        ExecSQL(sql);
    }


    /**
     * 执行SQL命令返回list
     * @param sql
     * @return
     */
    private ArrayList<MemberInfo> ExecSQLForMemberInfo(String sql) {
        ArrayList<MemberInfo> list = new ArrayList<MemberInfo>();
        Cursor c = ExecSQLForCursor(sql);
        while (c.moveToNext()) {
            MemberInfo info = new MemberInfo();
            info._id = c.getInt(c.getColumnIndex("_id"));
            info.name = c.getString(c.getColumnIndex("name"));
            info.pwds = c.getString(c.getColumnIndex("pwds"));
            list.add(info);
        }
        c.close();
        return list;
    }


    private ArrayList<XurYaEntity> ExecSQLForXueYa(String sql) {
        ArrayList<XurYaEntity> list = new ArrayList<XurYaEntity>();
        Cursor c = ExecSQLForCursor(sql);
        while (c.moveToNext()) {
            XurYaEntity xueya = new XurYaEntity();
            xueya._id = c.getString(c.getColumnIndex("_id"));
            xueya.user_id = c.getString(c.getColumnIndex("user_id"));
            xueya.gaoya = c.getString(c.getColumnIndex("gaoya"));
            xueya.diya = c.getString(c.getColumnIndex("diya"));
            list.add(xueya);
        }
        c.close();
        return list;
    }

    private ArrayList<XueYangEntity> ExecSQLForXueYang(String sql) {
        ArrayList<XueYangEntity> list = new ArrayList<XueYangEntity>();
        Cursor c = ExecSQLForCursor(sql);
        while (c.moveToNext()) {
            XueYangEntity xueyang = new XueYangEntity();
            xueyang._id = c.getString(c.getColumnIndex("_id"));
            xueyang.user_id = c.getString(c.getColumnIndex("user_id"));
            xueyang.xueyang = c.getString(c.getColumnIndex("xueyang"));
            xueyang.xinlv = c.getString(c.getColumnIndex("xinlv"));
            list.add(xueyang);
        }
        c.close();
        return list;
    }

    private ArrayList<XinDianEntity> ExecSQLForXinDian(String sql) {
        ArrayList<XinDianEntity> list = new ArrayList<>();
        Cursor c = ExecSQLForCursor(sql);
        while (c.moveToNext()) {
            XinDianEntity xindian = new XinDianEntity();
            xindian._id = c.getString(c.getColumnIndex("_id"));
            xindian.user_id = c.getString(c.getColumnIndex("user_id"));
            xindian.xinlv = c.getString(c.getColumnIndex("xinlv"));
            xindian.RRmax = c.getString(c.getColumnIndex("RRmax"));
            xindian.RRmin = c.getString(c.getColumnIndex("RRmin"));
            xindian.xinlvbianyi = c.getString(c.getColumnIndex("xinlvbianyi"));
            xindian.mood = c.getString(c.getColumnIndex("mood"));
            list.add(xindian);
        }
        c.close();
        return list;
    }

    private ArrayList<GanShi> ExecSQLForGanShi(String sql) {
        ArrayList<GanShi> list = new ArrayList<>();
        Cursor c = ExecSQLForCursor(sql);
        while (c.moveToNext()) {
            GanShi ganShi = new GanShi();
            ganShi._id = c.getString(c.getColumnIndex("_id"));
            ganShi.user_id = c.getString(c.getColumnIndex("user_id"));
            ganShi.message = c.getString(c.getColumnIndex("message"));
            list.add(ganShi);
        }
        c.close();
        return list;
    }

    private ArrayList<BC401_Struct> ExecSQLForNiaoYe(String sql) {
        ArrayList<BC401_Struct> list = new ArrayList<>();
        Cursor c = ExecSQLForCursor(sql);
        while (c.moveToNext()) {
            BC401_Struct niaoye = new BC401_Struct();
            niaoye.ID = Integer.valueOf(c.getString(c.getColumnIndex("_id")));
            niaoye.URO =Byte.valueOf(c.getString(c.getColumnIndex("URO")));
            niaoye.BLD =Byte.valueOf(c.getString(c.getColumnIndex("BLD")));
            niaoye.BIL =Byte.valueOf(c.getString(c.getColumnIndex("BIL")));
            niaoye.KET =Byte.valueOf(c.getString(c.getColumnIndex("KET")));
            niaoye.GLU =Byte.valueOf(c.getString(c.getColumnIndex("GLU")));
            niaoye.PRO =Byte.valueOf(c.getString(c.getColumnIndex("PRO")));
            niaoye.PH =Byte.valueOf(c.getString(c.getColumnIndex("PH")));
            niaoye.NIT =Byte.valueOf(c.getString(c.getColumnIndex("NIT")));
            niaoye.LEU =Byte.valueOf(c.getString(c.getColumnIndex("LEU")));
            niaoye.SG =Byte.valueOf(c.getString(c.getColumnIndex("SG")));
            niaoye.VC =Byte.valueOf(c.getString(c.getColumnIndex("VC")));
            list.add(niaoye);
        }
        c.close();
        return list;
    }



    private ArrayList<SecondUser> ExecSQLForSecondUser(String sql) {
        ArrayList<SecondUser> list = new ArrayList<SecondUser>();
        Cursor c = ExecSQLForCursor(sql);
        while (c.moveToNext()) {
            SecondUser info = new SecondUser();
            info.fullname = c.getString(c.getColumnIndex("Fullname"));
            list.add(info);
        }
        c.close();
        return list;
    }


    private ArrayList<MemberInfo> ExecSQLForLoginData(String sql) {
        ArrayList<MemberInfo> list = new ArrayList<>();
        Cursor c = ExecSQLForCursor(sql);
        while (c.moveToNext()) {
            MemberInfo info = new MemberInfo();
            info.name = c.getString(c.getColumnIndex("name"));
            info.pwds = c.getString(c.getColumnIndex("pwds"));
            list.add(info);
        }
        c.close();
        return list;
    }


    private ArrayList<IDCard> ExecSQLForIDCard(String sql) {
        ArrayList<IDCard> list = new ArrayList<IDCard>();
        Cursor c = ExecSQLForCursor(sql);
        while (c.moveToNext()) {
            IDCard info = new IDCard();
            info.name = c.getString(c.getColumnIndex("Fullname"));
            info.phone = c.getString(c.getColumnIndex("phone"));
            info.cardno = c.getString(c.getColumnIndex("card_id"));
            info.sex = "0";
            info.folk = "其他";
            info.birthday = "2017-04-27";
            info.address = "待修改";
            info.province = "——选择省——";
            info.city = "——选择市——";
            info.district = "——选择区——";
            info.mphone = "号码待修改";

            list.add(info);
        }
        c.close();
        return list;
    }


    //体温搜索
    private ArrayList<Tiwen> ExecSQLForTiwen(String sql) {
        ArrayList<Tiwen> list = new ArrayList<Tiwen>();
        Cursor c = ExecSQLForCursor(sql);
        while (c.moveToNext()) {
            Tiwen tiwen = new Tiwen();
            tiwen.tiwen = c.getString(c.getColumnIndex("tiwen"));
            tiwen._id = c.getString(c.getColumnIndex("_id"));
            tiwen.user_id = c.getString(c.getColumnIndex("user_id"));
            list.add(tiwen);
        }
        c.close();
        return list;
    }


    private ArrayList<UserGet.UserBean> ExecSQLForSecondUser2(String sql) {
        ArrayList<UserGet.UserBean> list = new ArrayList<UserGet.UserBean>();
        Cursor c = ExecSQLForCursor(sql);
        while (c.moveToNext()) {
            UserGet.UserBean info = new UserGet.UserBean();
            info.card_id = c.getString(c.getColumnIndex("card_id"));
            list.add(info);
        }
        c.close();
        return list;
    }


    private ArrayList<SecondUser> ExecSQLForSecondUserId(String sql) {
        ArrayList<SecondUser> list = new ArrayList<SecondUser>();
        Cursor c = ExecSQLForCursor(sql);
        while (c.moveToNext()) {
            SecondUser info = new SecondUser();
            info.fullname = c.getString(c.getColumnIndex("Fullname"));
            list.add(info);
        }
        c.close();
        return list;
    }

    private ArrayList<SecondUser2> ExecSQLForSecondUserId3(String sql) {
        ArrayList<SecondUser2> list = new ArrayList<SecondUser2>();
        Cursor c = ExecSQLForCursor(sql);
        while (c.moveToNext()) {
            SecondUser2 info = new SecondUser2();
            info.Fullname = c.getString(c.getColumnIndex("Fullname"));
            info.user_id = c.getString(c.getColumnIndex("user_id"));
            list.add(info);
        }
        c.close();
        return list;
    }

    private ArrayList<SecondUser2> ExecSQLForSecondUserMid(String sql) {
        ArrayList<SecondUser2> list = new ArrayList<SecondUser2>();
        Cursor c = ExecSQLForCursor(sql);
        while (c.moveToNext()) {
            SecondUser2 info = new SecondUser2();
            info.user_id = c.getString(c.getColumnIndex("user_id"));
            list.add(info);
        }
        c.close();
        return list;
    }


    private ArrayList<SecondUser> ExecSQLForSecondUserId2(String sql) {
        ArrayList<SecondUser> list = new ArrayList<SecondUser>();
        Cursor c = ExecSQLForCursor(sql);
        while (c.moveToNext()) {
            SecondUser info = new SecondUser();
            info.id = c.getString(c.getColumnIndex("user_id"));
            list.add(info);
        }
        c.close();
        return list;
    }

    /**
     * 执行一个SQL语句
     *
     * @param sql
     */
    private void ExecSQL(String sql) {
        try {
            db.execSQL(sql);
            Log.i("execSql: ", sql);
        } catch (Exception e) {
            Log.e("ExecSQL Exception", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 执行SQL，返回一个游标
     *
     * @param sql
     * @return
     */
    private Cursor ExecSQLForCursor(String sql) {
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    public void closeDB() {
        db.close();
    }

}
