package com.example.scau_faceid.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.scau_faceid.BaseActivity;
import com.example.scau_faceid.MainActivity;
import com.example.scau_faceid.R;
import com.example.scau_faceid.info.AccountStudent;
import com.example.scau_faceid.util.CommenUtil;
import com.example.scau_faceid.util.DBUtils;
import com.example.scau_faceid.util.ViewUtils;

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

@SuppressLint("NonConstantResourceId")
public class LoginRegisterActivity extends BaseActivity {
    /*@BindView(R.id.account_register_name) EditText nameText;
    @BindView(R.id.account_register_account) EditText studentText;
    @BindView(R.id.account_register_password) EditText passwordText;
    @BindView(R.id.account_register_confirm) EditText confirmText;
    @BindView(R.id.account_register_class) EditText classText;
    @BindView(R.id.account_register_phone) EditText phoneText;
    @BindView(R.id.account_register_email) EditText emailText;*/

    EditText nameText;
    EditText studentText;
    EditText passwordText;
    EditText confirmText;
    EditText classText;
    EditText phoneText;
    EditText emailText;
    RadioGroup radioGroup;
    RadioButton radioButton1;
    RadioButton radioButton2;

    private Boolean sex;
    private AccountStudent student;
    private String TAG = "LoginRegisterActivity";
    private boolean start_flag = false;//是否启动ConfirmActivity的标志位

    /**
     * 先判断账号是否已存在，账号不存在则进行手机号验证
     */
    /*Handler handler = new Handler(msg -> {
        String message = msg.getData().getString("message");
        if ("1".equals(message)){
            Toast.makeText(this, "账号已存在", Toast.LENGTH_SHORT).show();
            return false;
        }
        Intent intent = new Intent("com.example.project_android.activity.login.ConfirmActivity");
        intent.putExtra("data",student);
        startActivity(intent);
        return false;
    });*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        nameText = findViewById(R.id.account_register_name);
        studentText = findViewById(R.id.account_register_account);
        passwordText = findViewById(R.id.account_register_password);
        confirmText = findViewById(R.id.account_register_confirm);
        classText = findViewById(R.id.account_register_class);
        phoneText = findViewById(R.id.account_register_phone);
        emailText = findViewById(R.id.account_register_email);

        radioGroup = findViewById(R.id.radioGroup);
        radioButton1 = findViewById(R.id.account_register_male);
        radioButton2 = findViewById(R.id.account_register_female);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                onRadioCheck(checkId);
            }
        });

        ViewUtils.initActionBar(this, "注册账号");
    }

    public void onRegisterClicked(View view) {

        String name = nameText.getText().toString();
        String account = studentText.getText().toString();
        String major = classText.getText().toString();
        String password = passwordText.getText().toString();
        String phone = phoneText.getText().toString();
        String email = emailText.getText().toString();
        Log.i("COMEON", "onRegisterClicked: ");
        if (!passwordText.getText().toString().equals(confirmText.getText().toString())) {
            showToast("两次密码不一致");
            return;
        }
        if (name.isEmpty() || account.isEmpty() || password.isEmpty() || sex == null ||
                major.isEmpty() || phone.isEmpty() || email.isEmpty() ||
                !CommenUtil.IsEmail(email) || !CommenUtil.isPhone(phone)) {
            Log.i("WHAT?", "onRegisterClicked: " + name + account + password + major + phone + email);
            showToast("请将注册资料填写完整");
            return;
        }

        student = new AccountStudent(name, account, password, sex, major, phone, email);

        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) {

                boolean if_repeated = DBUtils.ifReapted(student);
                emitter.onNext(if_repeated);
            }
        })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Boolean if_repeated) {
                        if (if_repeated == false) {
                            showToast("学号/手机号/邮箱重复！");
                        } else {
                            start_flag = true;
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
        if (start_flag == true){
            startActivity(new Intent(LoginRegisterActivity.this, ConfirmActivity.class).putExtra("data", student));
        }
    }

    public void onRadioCheck(int checkId) {
        switch (checkId) {
            case R.id.account_register_male:
                sex = true;
                break;
            case R.id.account_register_female:
                sex = false;
                break;
            default:
                break;
        }
    }

    @Override
    public void afterRequestPermission(int requestCode, boolean isAllGranted) {
    }

}
