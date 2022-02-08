package com.example.scau_faceid.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.scau_faceid.BaseActivity;
import com.example.scau_faceid.MainActivity;
import com.example.scau_faceid.R;
import com.example.scau_faceid.util.ProjectStatic;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_account)
    EditText accountEdit;
    @BindView(R.id.login_password)
    EditText passwordEdit;

    private int userType;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
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
            Toast.makeText(this, "请补全账号密码！", Toast.LENGTH_SHORT).show();
        }
        Map<String,String> map = new HashMap<>();
        map.put("type", String.valueOf(userType));
        map.put("account",accountEdit.getText().toString());
        map.put("password",passwordEdit.getText().toString());
        /*NetUtil.getNetData("account/login",map,new Handler(msg -> {
            if (msg.what == 1){
                dialog.dismiss();

                String data = msg.getData().getString("data");
                Intent loginIntent = new Intent(ProjectStatic.MAIN);
                updateLoginInfo(preferences, data,String.valueOf(userType));
                Toast.makeText(this, msg.getData().getString("message"), Toast.LENGTH_SHORT).show();
                startActivity(loginIntent);
                finish();
            } else{
                dialog.showSingleButton();
                dialog.setMessage(msg.getData().getString("message"));
            }
            return false;
        }));*/
    }

    @Override
    public void afterRequestPermission(int requestCode, boolean isAllGranted) {}
}
