package com.zucc.zwy1317.myassistant.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zucc.zwy1317.myassistant.modle.ChatBean;
import com.zucc.zwy1317.myassistant.modle.RecordBean;
import com.zucc.zwy1317.myassistant.modle.ScheduleBean;
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
    public static final int VERSION = 1;

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

    public boolean register(String account, String pwd){
        final String sql_s = "SELECT * FROM User WHERE uID = ? ";//select
        final String sql_i = "INSERT INTO User(uID,pwd) VALUES(?,?)";//insert
        Cursor c = db.rawQuery(sql_s, new String[]{account});
        if (c.moveToFirst()) {
            c.close();
            return false;
        }
        db.execSQL(sql_i, new Object[]{account,pwd});
        return true;
    }
    public String login(String account, String pwd) {
        final String sql = "SELECT uID,pwd FROM User WHERE uID = ? ";
        Cursor c = db.rawQuery(sql, new String[]{account});
        if (c.moveToFirst()) {
            String uID = c.getString(0);
            String pwd2 = c.getString(1);
            if (pwd.equals(pwd2)) {
                return uID;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public void saveRecord(RecordBean recordBean){
        final String sql_s = "SELECT * FROM Record WHERE rID = ? ";//select
        final String sql_i = "INSERT INTO Record(rID,amount,time,type,title,note,location,photo,uID) VALUES(?,?,?,?,?,?,?,?,?)";//insert

        recordBean.bindID();
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
        final String sql = "SELECT rID,amount,time,type,title,note,location,photo FROM Record WHERE uID = ? ORDER BY time DESC";
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
            recordBean.setuID(uID);
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

    public List<TypeIconBean> loadTypeIconAll(int type){
        final String sql = "SELECT iID,title,color,type,icon FROM TypeIconBean WHERE type = ?";
        List<TypeIconBean> list = new ArrayList<>();
        TypeIconBean typeIconBean;
        Cursor c = db.rawQuery(sql,new String[]{type+""});
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
        final String sql = "SELECT iID,title,color,type,icon FROM TypeIconBean";
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
        HashMap<String,TypeIconBean> map = new HashMap<>();
        for(int i = 0; i < list.size() ;i++){
            map.put(list.get(i).getTitle(),list.get(i));
        }
        return map;
    }

    public void saveSchedule(ScheduleBean scheduleBean){
        final String sql_s = "SELECT * FROM Schedule WHERE sID = ? ";//select
        final String sql_i = "INSERT INTO Schedule(sID,title,note,startTime,endTime,alarmTime,alarmColor,alarmTonePath,uID) VALUES(?,?,?,?,?,?,?,?,?)";//insert

        scheduleBean.bindID();
        Cursor c = db.rawQuery(sql_s, new String[]{scheduleBean.getsID()});
        if (c.moveToFirst()) {
            c.close();
            return;
        }
        db.execSQL(sql_i, new Object[]{scheduleBean.getsID(),scheduleBean.getmTitle(),scheduleBean.getmNote(),
                scheduleBean.getmStartTime(),scheduleBean.getmEndTime(),scheduleBean.getmAlarmTime(),
                scheduleBean.getmAlarmColor(),scheduleBean.getmAlarmTonePath(),scheduleBean.getuID()});
    }

    public boolean deleteSchedule(String sID){
        final String sql_s = "SELECT * FROM Schedule WHERE sID = ?";
        final String sql_d = "DELETE FROM Schedule WHERE sID = ?";
        Cursor c = db.rawQuery(sql_s, new String[]{sID});
        if (!c.moveToFirst()) {
            return false;
        }
        c.close();
        db.execSQL(sql_d, new Object[]{sID});
        return true;
    }

    public boolean updateSchedule(ScheduleBean scheduleBean){
        final String sql = "UPDATE Schedule " +
                "SET sID = ?, title = ?, note = ?, startTime = ?, endTime = ?, alarmTime = ?, alarmColor = ?, alarmTonePath = ?, uID = ? " +
                "WHERE sID = ?;";
        try {
            db.execSQL(sql,new Object[]{scheduleBean.getsID(),scheduleBean.getmTitle(),scheduleBean.getmNote(),
                    scheduleBean.getmStartTime(),scheduleBean.getmEndTime(),scheduleBean.getmAlarmTime(),
                    scheduleBean.getmAlarmColor(),scheduleBean.getmAlarmTonePath(),scheduleBean.getuID(),scheduleBean.getsID()});
        }catch (Error e){
            return false;
        }
        return true;
    }

    public ScheduleBean querySchedule(String sID){
        ScheduleBean scheduleBean = new ScheduleBean();
        return scheduleBean;
    }

    public List<ScheduleBean> loadScheduleAll(String uID){
        final String sql = "SELECT sID,title,note,startTime,endTime,alarmTime,alarmColor,alarmTonePath FROM Schedule WHERE uID = ? ORDER BY startTime";
        List<ScheduleBean> scheduleList = new ArrayList<>();
        ScheduleBean scheduleBean;
        Cursor c = db.rawQuery(sql,new String[]{uID});
        while (c.moveToNext()){
            scheduleBean = new ScheduleBean();
            scheduleBean.setsID(c.getString(0));
            scheduleBean.setmTitle(c.getString(1));
            scheduleBean.setmNote(c.getString(2));
            scheduleBean.setmStartTime(c.getLong(3));
            scheduleBean.setmEndTime(c.getLong(4));
            scheduleBean.setmAlarmTime(c.getLong(5));
            scheduleBean.setmAlarmColor(c.getString(6));
            scheduleBean.setmAlarmTonePath(c.getString(7));
            scheduleBean.setuID(uID);
            scheduleList.add(scheduleBean);
        }
        c.close();
        return scheduleList;
    }
    public void saveChat(ChatBean chatBean) {
        if (chatBean.getmSenderID() == null || chatBean.getmRecipientID() == null)
            return;
        final String sql = "INSERT INTO Chat(cID,senderID,recipientID,timestamp,message) VALUES(?,?,?,?,?)";
        db.execSQL(sql, new Object[]{chatBean.getcID(),chatBean.getmSenderID(),chatBean.getmRecipientID(),chatBean.getmTimestamp(),
                chatBean.getmMessage()});
    }

    public boolean deleteChat(String cID) {
        final String sql_s = "SELECT * FROM Chat WHERE cID = ?";
        final String sql_d = "DELETE FROM Chat WHERE cID = ?";
        Cursor c = db.rawQuery(sql_s, new String[]{cID});
        if (!c.moveToFirst()) {
            return false;
        }
        c.close();
        db.execSQL(sql_d, new Object[]{cID});
        return true;
    }

    public List<ChatBean> loadChatAll(String uID) {
        List<ChatBean> list = new ArrayList<>();
        ChatBean chatBean;
        final String sql = "SELECT cID,senderID,recipientID,timestamp,message FROM Chat WHERE senderID = ? OR recipientID = ? ORDER BY timestamp DESC";
        Cursor c = db.rawQuery(sql, new String[]{uID,uID});
        while (c.moveToNext()) {
            chatBean = new ChatBean();
            chatBean.setcID(c.getString(0));
            chatBean.setmSenderID(c.getString(1));
            chatBean.setmRecipientID(c.getString(2));
            chatBean.setmTimestamp(c.getLong(3));
            chatBean.setmMessage(c.getString(4));
            list.add(chatBean);
        }
        return list;
    }

    public void initTypeIcon(){
        final String sql = "DELETE FROM TypeIconBean ";
        db.execSQL(sql);
        List<TypeIconBean> list = new ArrayList<>();
        for(int i = 9;i < 17;i++) {
            list.add(new TypeIconBean(TypeIconBean.TYPE_INCOME,
                    mContext.getResources().getIdentifier("ic_type_"+i, "drawable", "com.zucc.zwy1317.myassistant"),
                    "收" + i+"型",
                    null));
        }
        for(int i = 1;i < 8 ;i++) {
            list.add(new TypeIconBean(TypeIconBean.TYPE_SPENDING,
                    mContext.getResources().getIdentifier("ic_type_"+i, "drawable", "com.zucc.zwy1317.myassistant"),
                    "支" + i + "型",
                    null));
        }
        for(int i = 0;i<list.size();i++){
            saveTypeIcon(list.get(i));
        }
    }
}
