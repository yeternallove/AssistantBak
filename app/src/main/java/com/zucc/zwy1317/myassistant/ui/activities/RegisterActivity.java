package com.zucc.zwy1317.myassistant.ui.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.EditText;
import android.widget.Toast;

import com.zucc.zwy1317.myassistant.R;
import com.zucc.zwy1317.myassistant.db.AssistantDB;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    private AssistantDB assistantDB;

    @BindView(R.id.register_fab)
    FloatingActionButton fab;
    @BindView(R.id.register_card)
    CardView cardView;
    @BindView(R.id.register_edt_username)
    EditText edtUsername;
    @BindView(R.id.register_edt_password)
    EditText edtPassword;
    @BindView(R.id.register_edt_repeatpassword)
    EditText edtRepeatPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        ShowEnterAnimation();
        assistantDB = AssistantDB.getInstance(this);
    }
    @OnClick({R.id.register_fab, R.id.register_btn_register})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register_fab:
                animateRevealClose();
                break;
            case R.id.register_btn_register:
                register();
                animateRevealClose();
                break;
        }
    }
    public boolean register() {
        String account =edtUsername.getText().toString().trim();
        String password1 =edtPassword.getText().toString().trim();
        String password2 =edtRepeatPassword.getText().toString().trim();
        if(account.equals("@")){
            Toast.makeText(this,"非法用户名",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!password1.equals(password2)){
            Toast.makeText(this,"两次输入密码不一致",Toast.LENGTH_SHORT).show();
            return false;
        }else if(assistantDB.register(account,password1)){
            Toast.makeText(this,"用户名已被注册",Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.transition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cardView.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }


        });
    }

    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cardView, cardView.getWidth() / 2, 0, fab.getWidth() / 2, cardView.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cardView.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cardView, cardView.getWidth() / 2, 0, cardView.getHeight(), fab.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cardView.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab.setImageResource(R.drawable.ic_add_black_24dp);
                RegisterActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    public void onBackPressed() {
        animateRevealClose();
    }
}
