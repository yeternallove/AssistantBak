package com.zucc.zwy1317.myassistant.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zucc.zwy1317.myassistant.modle.RecordBean;
import com.zucc.zwy1317.myassistant.modle.TypeIconBean;
import com.zucc.zwy1317.myassistant.modle.UserBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @description:
 * @author: eternallove
 * @date: 2017/07/02
 */
public class AssistantDB {
    public static final String DB_NAME = "Eternal";
    public static final int VERSION = 2;

    private static AssistantDB massistantDB;
    private SQLiteDatabase db;
    private Context mContext;

    private AssistantDB(Context context) {
        this.mContext = context;
        AssistantOpenHelper dbHelper = new AssistantOpenHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    public synchronized static AssistantDB getInstance(Context context) {
        if (massistantDB == null) {
            massistantDB = new AssistantDB(context);
        }
        return massistantDB;
    }

    /**
     * 保存用户到SQLite
     *
     * @param userBean
     */
    public void saveUser(UserBean userBean) {
        final String sql_s = "SELECT * FROM User WHERE uID = ? ";//select
        final String sql_i = "INSERT INTO User(uID,QQ,emal,nickname,pwd,avatar,data) VALUES(?,?,?,?,?,?,?)";//insert
        Cursor c = db.rawQuery(sql_s, new String[]{userBean.getuID()});
        if (c.moveToFirst()) {
            c.close();
            return;
        }
        db.execSQL(sql_i, new Object[]{userBean.getuID(), userBean.getmQQ(), userBean.getmEmal(),
                userBean.getmNickname(), userBean.getmPwd(), userBean.getmAvatar(), userBean.getmData()});
    }

    public void saveRecord(RecordBean recordBean){
        final String sql_s = "SELECT * FROM Record WHERE rID = ? ";//select
        final String sql_i = "INSERT INTO Record(rID,amount,time,type,title,note,location,photo,uID) VALUES(?,?,?,?,?,?,?,?,?)";//insert

        if(recordBean.getrID() == null) recordBean.bindID();
        Cursor c = db.rawQuery(sql_s, new String[]{recordBean.getrID()});
        if (c.moveToFirst()) {
            c.close();
            return;
        }
        db.execSQL(sql_i, new Object[]{recordBean.getrID(), recordBean.getmAmount(), recordBean.getmTime(),
                recordBean.getType(), recordBean.getTitle(), recordBean.getmNote(),recordBean.getLocation(),
                recordBean.getPhoto(), recordBean.getuID()});
    }

    public boolean deleteRecord(String rID){
        final String sql_s = "SELECT * FROM Record WHERE rID = ?";
        final String sql_d = "DELETE FROM Record WHERE rID = ?";
        Cursor c = db.rawQuery(sql_s, new String[]{rID});
        if (!c.moveToFirst()) {
            return false;
        }
        c.close();
        db.execSQL(sql_d, new Object[]{rID});
        return true;
    }

    public boolean updateRecord(RecordBean recordBean){
        final String sql = "UPDATE Record " +
                "SET rID = ?, amount = ?, time = ?, type = ?, title = ?, note = ?, location = ?, photo = ?, uID = ? " +
                "WHERE rID = ?; ";
        try {
            db.execSQL(sql, new Object[]{recordBean.getrID(), recordBean.getmAmount(), recordBean.getmTime(),
                    recordBean.getType(), recordBean.getTitle(), recordBean.getmNote(),recordBean.getLocation(),
                    recordBean.getPhoto(), recordBean.getuID(),recordBean.getrID()});
        } catch (Error e) {
            return false;
        }
        return true;
    }

    public RecordBean queryRecord(String rID){
        RecordBean recordBean = new RecordBean();
        return recordBean;
    }

    public List<RecordBean> loadRecordAll(String uID){
        final String sql = "SELECT rID,amount,time,type,title,note,location,photo,uID FROM Record WHERE uID = ? ORDER BY time DESC";
        List<RecordBean> recordList = new ArrayList<>();
        RecordBean recordBean;
        Cursor c = db.rawQuery(sql,new String[]{uID});
        while (c.moveToNext()){
            recordBean = new RecordBean();
            recordBean.setrID(c.getString(0));
            recordBean.setmAmount(c.getDouble(1));
            recordBean.setmTime(c.getLong(2));
            recordBean.setType(c.getInt(3));
            recordBean.setTitle(c.getString(4));
            recordBean.setmNote(c.getString(5));
            recordBean.setLocation(c.getString(6));
            recordBean.setPhoto(c.getString(7));
            recordBean.setuID(c.getString(8));
            recordList.add(recordBean);
        }
        c.close();
        return recordList;
    }

    public void saveTypeIcon(TypeIconBean iconBean){
        final String sql_i = "INSERT INTO TypeIconBean(title,color,type,icon) VALUES(?,?,?,?)";//insert
        db.execSQL(sql_i, new Object[]{iconBean.getTitle(), iconBean.getColor(),
                iconBean.getType(),iconBean.getIcon()});
    }
    public boolean deleteTypeIcon(int iID){
        final String sql_s = "SELECT * FROM TypeIconBean WHERE iID = ?";
        final String sql_d = "DELETE FROM TypeIconBean WHERE iID = ?";
        Cursor c = db.rawQuery(sql_s, new String[]{iID+""});
        if (!c.moveToFirst()) {
            return false;
        }
        c.close();
        db.execSQL(sql_d, new Object[]{iID});
        return true;
    }

    public boolean updateTypeIcon(TypeIconBean iconBean){
        final String sql = "UPDATE TypeIconBean " +
                "SET title = ?, color = ?, type = ?, icon = ?" +
                "WHERE iID = ?; ";
        try {
            db.execSQL(sql, new Object[]{iconBean.getTitle(), iconBean.getColor(), iconBean.getType(),
                    iconBean.getIcon(), iconBean.getiID()});
        } catch (Error e) {
            return false;
        }
        return true;
    }

    public List<TypeIconBean> loadTypeIconAll(){
        final String sql = "SELECT iID,title,color,type,icon FROM TypeIconBean ";
        List<TypeIconBean> list = new ArrayList<>();
        TypeIconBean typeIconBean;
        Cursor c = db.rawQuery(sql,new String[]{});
        while (c.moveToNext()){
            typeIconBean = new TypeIconBean();
            typeIconBean.setiID(c.getInt(0));
            typeIconBean.setTitle(c.getString(1));
            typeIconBean.setColor(c.getString(2));
            typeIconBean.setType(c.getInt(3));
            typeIconBean.setIcon(c.getInt(4));
            list.add(typeIconBean);
        }
        c.close();
        return list;
    }

    public HashMap<String,TypeIconBean> loadTypeIconMap(){
        List<TypeIconBean> list = loadTypeIconAll();
        HashMap<String,TypeIconBean> map = new HashMap<>();
        for(int i = 0; i < list.size() ;i++){
            map.put(list.get(i).getTitle(),list.get(i));
        }
        return map;
    }
}
