package com.example.scau_faceid.login;

import android.content.Intent;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scau_faceid.BaseActivity;
import com.example.scau_faceid.MainActivity;
import com.example.scau_faceid.R;
import com.example.scau_faceid.info.AccountStudent;
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

/**
 * 验证页，包括短信验证和语音验证，默认使用中国区号
 */
public class ConfirmActivity extends BaseActivity {

    public AccountStudent student;
    private Button btnSendMsg, btnSubmitCode;
    private TimeCount time;
    private EditText etEmail, etCode;
    private int i = 60;
    private String To;
    private String code;
    private boolean start_flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        student = (AccountStudent) getIntent().getSerializableExtra("data");

        btnSendMsg = findViewById(R.id.btnSendMsg);
        btnSubmitCode = findViewById(R.id.btnSubmitCode);
        etCode = findViewById(R.id.etCode);
        etEmail = findViewById(R.id.etEmail);
        etEmail.setText(student.getEmail());

        time = new TimeCount(60000, 1000, btnSendMsg);//构造CountDownTimer对象


        btnSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                To = etEmail.getText().toString();
                code = new CreateCode().getCode();
                new SendMailUtil(To, code);
                time.start();
            }
        });
        btnSubmitCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputCode = etCode.getText().toString();
                if (inputCode.equals(code)) {
                    showToast("验证码正确");

                    Observable.create(new ObservableOnSubscribe<Boolean>() {
                        @Override
                        public void subscribe(ObservableEmitter<Boolean> emitter) {

                            boolean if_uploaded = DBUtils.UploadInfo(student);
                            emitter.onNext(if_uploaded);
                        }
                    })
                            .subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<Boolean>() {
                                @Override
                                public void onSubscribe(Disposable d) {
                                }

                                @Override
                                public void onNext(Boolean if_uploaded) {
                                    if (if_uploaded == true) {
                                        start_flag = true;
                                    } else {
                                        showToast("上传失败，请检测网络!");
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                    e.printStackTrace();
                                    showToast("register failed!");
                                }

                                @Override
                                public void onComplete() {
                                }
                            });

                    if (start_flag == true) {
                        startActivity(new Intent(ConfirmActivity.this, MainActivity.class)
                                .putExtra("data", student)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));//结束掉之前的Activity
                    }
                } else {
                    showToast("验证码错误");
                    etCode.setText("");
                }
            }
        });
    }

    @Override
    public void afterRequestPermission(int requestCode, boolean isAllGranted) {
    }

    class TimeCount extends CountDownTimer {
        private Button btn;

        public TimeCount(long millisInFuture, long countDownInterval, Button btn) {

            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
            this.btn = btn;
        }

        @Override
        public void onFinish() {//计时完毕时触发
            btn.setText("重新验证");
            btn.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            btn.setClickable(false);
            btn.setText(millisUntilFinished / 1000 + "秒");
        }
    }
}
