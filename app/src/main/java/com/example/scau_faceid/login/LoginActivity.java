package com.example.scau_faceid.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.scau_faceid.BaseActivity;
import com.example.scau_faceid.MainActivity;
import com.example.scau_faceid.R;
import com.example.scau_faceid.info.AccountStudent;
import com.example.scau_faceid.info.AccountTeacher;
import com.example.scau_faceid.util.DBUtils;
import com.example.scau_faceid.util.ProjectStatic;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseActivity {

    private EditText accountEdit;
    private EditText passwordEdit;
    private RadioGroup radioGroup;
    private RadioButton radioButton1;
    private RadioButton radioButton2;

    private int userType;
    private SharedPreferences preferences;
    private AccountStudent loginStudent = null;
    private AccountTeacher loginTeacher = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //隐藏标题栏
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }

        accountEdit = findViewById(R.id.login_account);
        passwordEdit = findViewById(R.id.login_password);
        radioGroup = findViewById(R.id.radioGroup1);
        radioButton1 = findViewById(R.id.login_radio_student);
        radioButton2 = findViewById(R.id.login_radio_teacher);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                onRadioCheck(checkId);
            }
        });
    }

    @OnClick({R.id.login_register, R.id.login_forget_password, R.id.login_button})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.login_register:
                startActivity(new Intent(this, LoginRegisterActivity.class));
                break;
            case R.id.login_forget_password:
                startActivity(new Intent(this, ModifyPasswordActivity.class));
                break;
            case R.id.login_button:
                processLogin();
                break;
        }
    }

    private void processLogin() {
        if (userType == 0 || accountEdit.getText().toString().isEmpty() || passwordEdit.getText().toString().isEmpty()) {
            showToast("请补全账号密码！");
            return;
        }

        if (userType == 2) {
            Observable.create(new ObservableOnSubscribe<Boolean>() {
                @Override
                public void subscribe(ObservableEmitter<Boolean> emitter) {

                    loginStudent = DBUtils.StudentLogin(accountEdit.getText().toString(), passwordEdit.getText().toString());
                    Boolean success = DBUtils.returnBoolean(loginStudent);
                    emitter.onNext(success);
                }
            })
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Boolean>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(Boolean success) {
                            if (success == true) {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class)
                                        .putExtra("data", loginStudent)
                                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));//结束掉之前的Activity
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            showToast("login failed!");
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        } else {
            Observable.create(new ObservableOnSubscribe<Boolean>() {
                @Override
                public void subscribe(ObservableEmitter<Boolean> emitter) {

                    loginTeacher = DBUtils.TeacherLogin(accountEdit.getText().toString(), passwordEdit.getText().toString());
                    Boolean success = DBUtils.ReturnBoolean(loginTeacher);
                    emitter.onNext(success);
                }
            })
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Boolean>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(Boolean success) {
                            if (success == true) {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class)
                                        .putExtra("data", loginTeacher)
                                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));//结束掉之前的Activity
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            showToast("login failed!");
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        }
    }

    /**
     * 监控单选结果
     **/
    private void onRadioCheck(int checkId) {
        switch (checkId) {
            case R.id.login_radio_teacher:
                userType = 1;
                break;
            case R.id.login_radio_student:
                userType = 2;
                break;
            default:
                userType = 0;
                break;
        }
    }

    @Override
    public void afterRequestPermission(int requestCode, boolean isAllGranted) {
    }
}
