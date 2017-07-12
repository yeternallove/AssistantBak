package com.zucc.zwy1317.myassistant.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @description:
 * @author: eternallove
 * @date: 2017/07/02
 */
public class AssistantOpenHelper extends SQLiteOpenHelper {

    public static final String CREATE_USER = "create table User("
            + "uID varchar(20) primary key,"
            + "QQ varchar(20),"
            + "emal varchar(20),"
            + "nickname varchar(20),"
            + "pwd varchar(20),"
            + "avatar text,"
            + "data text)";

    public static final String CREATE_SCHEDULE = "create table Schedule("
            + "sID varchar(20) primary key,"
            + "title text,"
            + "note text,"
            + "startTime timestamp,"
            + "endTime timestamp,"
            + "alarmTime timestamp,"
            + "alarmColor varchar(20),"
            + "alarmTonePath text,"
            + "uID varchar(20))";

    public static final String CREATE_CHATS = "create table Chats("
            + "cID varchar(20) primary key,"
            + "senderID varchar(20),"
            + "recipientID varchar(20),"
            + "timestamp integer ,"
            + "buttons text,"
            + "message text)";

    public static final String CREATE_RECORD = "create table Record("
            + "rID varchar(20) primary key,"
            + "amount double,"
            + "time timestamp,"        //流水账时间
            + "type integer,"
            + "title varchar(20),"      //标签
            + "note text,"
            + "location text,"
            + "photo text,"
            + "uID varchar(20))";

    private static final String CREATE_ICON = "create table TypeIconBean("
            + "iID integer primary key autoincrement,"
            + "title varchar(20),"
            + "color text,"
            + "type integer,"
            + "icon integer)";


    public AssistantOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_SCHEDULE);
        db.execSQL(CREATE_CHATS);
        db.execSQL(CREATE_RECORD);
        db.execSQL(CREATE_ICON);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old_i, int new_i) {
        db.execSQL("drop table if exists User");
        db.execSQL("drop table if exists Schedule");
        db.execSQL("drop table if exists Chats");
        db.execSQL("drop table if exists Record");
        db.execSQL("drop table if exists TypeIconBean");
        onCreate(db);
    }
}
