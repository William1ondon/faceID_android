package com.example.scau_faceid.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;


import com.example.scau_faceid.BaseActivity;

import com.example.scau_faceid.R;
import com.example.scau_faceid.util.DBUtils;
import com.example.scau_faceid.util.email.CreateCode;
import com.example.scau_faceid.util.email.SendMailUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ModifyPasswordActivity extends BaseActivity {
    private EditText ETEmail;
    private EditText ETCode;
    private EditText ETPassword;
    private TextView TextSendCode;
    private TextView TextSummitModify;
    private TextView TextClickToConfirm;
    private TimeCount timeCount;
    private String Code;
    private String email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifypassword);

        //隐藏标题栏
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }

        ETEmail = findViewById(R.id.email1);
        ETCode = findViewById(R.id.etCode1);
        ETPassword = findViewById(R.id.newPassword);
        TextSendCode = findViewById(R.id.btnSendMsg1);
        TextSummitModify = findViewById(R.id.btnSubmitModify);
        TextClickToConfirm = findViewById(R.id.btnClickToConfirm);
        timeCount = new TimeCount(60*1000,1000,TextSendCode);

        TextSummitModify.setVisibility(View.INVISIBLE);
        TextSummitModify.setClickable(false);
        ETPassword.setFocusable(false);
        ETPassword.setFocusableInTouchMode(false);

        TextSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    email = ETEmail.getText().toString();
                    Code = new CreateCode().getCode();
                    new SendMailUtil(email, Code);
                    timeCount.start();
            }
        });

        TextClickToConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputCode = ETCode.getText().toString();
                if (inputCode.equals(Code)){
                    showToast("验证码正确");

                    TextClickToConfirm.setClickable(false);
                    TextClickToConfirm.setVisibility(View.INVISIBLE);

                    TextSummitModify.setVisibility(View.VISIBLE);
                    TextSummitModify.setClickable(true);

                    ETPassword.setFocusableInTouchMode(true);
                    ETPassword.setFocusable(true);
                    ETPassword.requestFocus();
                } else {
                    showToast("请输入正确的验证码");
                }
            }
        });

        TextSummitModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String etPassword = ETPassword.getText().toString();
                if (etPassword == null){
                    showToast("请输入新密码");
                } else {
                    Observable.create(new ObservableOnSubscribe<Boolean>() {
                        @Override
                        public void subscribe(ObservableEmitter<Boolean> emitter) {

                            boolean if_updated = DBUtils.updatePassword(etPassword,email);
                            emitter.onNext(if_updated);
                        }
                    })
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<Boolean>() {
                                @Override
                                public void onSubscribe(Disposable d) {
                                }

                                @Override
                                public void onNext(Boolean if_updated) {
                                    if (if_updated == true){
                                        showToast("修改成功");
                                        startActivity(new Intent(ModifyPasswordActivity.this, LoginActivity.class)
                                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));//结束掉之前的Activity
                                    } else {
                                        showToast("修改失败，请检测网络");
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                    e.printStackTrace();
                                    showToast("修改失败，请检查网络状态");
                                }

                                @Override
                                public void onComplete() {
                                }
                            });
                }
            }
        });
    }

    private class TimeCount extends CountDownTimer {
        private TextView textView;

        public TimeCount(long millisInFuture, long countDownInterval, TextView textView) {

            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
            this.textView = textView;
        }

        @Override
        public void onFinish() {//计时完毕时触发
            textView.setText("重新验证");
            textView.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            textView.setClickable(false);
            textView.setText(millisUntilFinished / 1000 + "秒");
        }
    }

    @Override
    public void afterRequestPermission(int requestCode, boolean isAllGranted) {}
}
