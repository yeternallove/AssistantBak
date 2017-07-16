package com.zucc.zwy1317.myassistant.ui.activities;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zucc.zwy1317.myassistant.R;
import com.zucc.zwy1317.myassistant.db.AssistantDB;
import com.zucc.zwy1317.myassistant.util.JsonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity {

    private AssistantDB assistantDB;
    private ProgressDialog pDialog;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String userName;
    private String passWord;
    private String uID;

    @BindView(R.id.login_fab)
    FloatingActionButton fab;
    @BindView(R.id.login_edt_username)
    EditText edtUsername;
    @BindView(R.id.login_edt_password)
    EditText edtPassword;
    @BindView(R.id.login_btn_login)
    Button btnLogin;
    @BindView(R.id.login_btn_trd)
    Button btnTrd;
    @BindView(R.id.login_tv_forgot)
    TextView tvForgot;
    @BindView(R.id.login_card)
    CardView cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = pref.edit();
        assistantDB = AssistantDB.getInstance(this);
    }

    //点击登录按钮
    public boolean loginClicked() {
        userName = edtUsername.getText().toString();
        passWord = edtPassword.getText().toString();
        uID = assistantDB.login(userName,passWord);
        if (uID != null) {
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(this, "账号或密码错误", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @OnClick({R.id.login_btn_login, R.id.login_fab, R.id.login_btn_trd, R.id.login_tv_forgot})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_fab:
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, fab, fab.getTransitionName());
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent, options.toBundle());
                break;
            case R.id.login_btn_login:
                if (loginClicked()) {
                    SplashActivity.actionStart(this);
                    finish();
                }
                break;
            case R.id.login_btn_trd:
                Toast.makeText(this,"还没成功注册为**",Toast.LENGTH_SHORT).show();
                break;
            case R.id.login_tv_forgot:
                Toast.makeText(this,"等着吧",Toast.LENGTH_SHORT).show();
                break;
        }
    }

//    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
//
//        private final String mAccount;
//        private final String mPassword;
//
//        UserLoginTask(String userID, String password) {
//            mAccount = userID;
//            mPassword = password;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            // Showing progress dialog
//            pDialog = new ProgressDialog(LoginActivity.this);
//            pDialog.setMessage("Please wait...");
//            pDialog.setCancelable(true);
//            pDialog.show();
//
//        }
//
//        @Override
//        protected Boolean doInBackground(Void... params) {
//            // TODO: attempt authentication against a network service.
////            HttpHandler hh = new HttpHandler();
////            String jsonStr = hh.makeServiceCall(url);
////            Log.e("json", "Response from url: " + jsonStr);
//            String jsonStr = "{\n" +
//                    "    \"user\": [\n" +
//                    "        {\n" +
//                    "            \"userID\": \"admin\",\n" +
//                    "            \"pwd\": \"admin\",\n" +
//                    "            \"QQ\": \"1234567\",\n" +
//                    "            \"email\": \"\",\n" +
//                    "            \"nickname\": \"管理员\"\n" +
//                    "        },\n" +
//                    "        {\n" +
//                    "            \"userID\": \"1\",\n" +
//                    "            \"pwd\": \"1\",\n" +
//                    "            \"QQ\": \"1\",\n" +
//                    "            \"email\": \"\",\n" +
//                    "            \"nickname\": \"测试员1号\"\n" +
//                    "        }\n" +
//                    "    ]\n" +
//                    "}";
//            JsonUtil jsonUtil = new JsonUtil(LoginActivity.this,jsonStr);
//            jsonUtil.refreshSQLite();
//
//            String userId = assistantDB.login(mAccount, mPassword);
//            if (userId == null)
//                return false;
//            else {
//                editor.putString("userID", userId);
//                editor.commit();
//            }
//            // TODO: register the new account here.
//            return true;
//        }
//
//        @Override
//        protected void onPostExecute(final Boolean success) {
//            mAuthTask = null;
//            if (pDialog.isShowing())
//                pDialog.dismiss();
//            if (success) {
//                if (rememberpass.isChecked()) {
//                    editor.putBoolean("remember_Password", true);
//                    editor.putBoolean("Login", true);
//                    editor.putString("mEmail",mAccount);
//                    editor.putString("mPassword",mPassword);
//                } else {
//                    editor.putBoolean("remember_Password", false);
//                }
//                editor.commit();
//                MainActivity.actionStart(LoginActivity.this);
//                finish();
//            } else {
//                mPasswordView.setError(getString(R.string.error_incorrect_password));
//                mPasswordView.requestFocus();
//            }
//        }
//
//        @Override
//        protected void onCancelled() {
//            mAuthTask = null;
//            if (pDialog.isShowing())
//                pDialog.dismiss();
//        }
//    }


}