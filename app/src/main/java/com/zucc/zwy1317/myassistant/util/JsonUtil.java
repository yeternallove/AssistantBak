package com.zucc.zwy1317.myassistant.util;

import android.content.Context;
import android.util.Log;

import com.zucc.zwy1317.myassistant.db.AssistantDB;
import com.zucc.zwy1317.myassistant.modle.UserBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @description:
 * @author: eternallove
 * @date: 2016/12/4
 */
public class JsonUtil {
    private String mjsonStr;
    private Context mcontext;
    private AssistantDB assistantDB;
    public JsonUtil(Context context, String jsonStr){
        this.mjsonStr = jsonStr;
        this.mcontext = context;
    }
    public void refreshSQLite(){
        if(this.mjsonStr == null) return;
        this.assistantDB = AssistantDB.getInstance(mcontext);
        try {
            JSONObject jsonObj = new JSONObject(this.mjsonStr);
            if(jsonObj.has("user")) {
                JSONArray user = jsonObj.getJSONArray("user");
                String userId;
                String pwd;
                String QQ;
                String email;
                String nickname;
                UserBean userBean;
                for (int i = 0; i < user.length(); i++) {
                    JSONObject c = user.getJSONObject(i);
                    userId = c.getString("userID");
                    pwd = c.getString("pwd");
                    QQ = c.getString("QQ");
                    email = c.getString("emal");
                    nickname = c.getString("nickname");
                    userBean = new UserBean(userId,nickname);
                    this.assistantDB.saveUser(userBean);
                }
            }
//            if(jsonObj.has("reply")){
//                JSONArray reply = jsonObj.getJSONArray("reply");
//                String key;
//                String content;
//                ReplyBean replyBean;
//                for(int i=0;i<reply.length();i++){
//                    JSONObject r = reply.getJSONObject(i);
//                    key = r.getString("key");
//                    content = r.getString("content");
//                    replyBean = new ReplyBean(key,content);
//                    this.mfairyDB.saveReply(replyBean);
//                }
//            }
        } catch (final JSONException e) {
            Log.e(JsonUtil.class.getName(), "Json parsing error: " + e.getMessage());
        }
    }

    public String getJsonStr() {
        return mjsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.mjsonStr = jsonStr;
    }
}