package com.example.scau_faceid;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scau_faceid.adapter.AttendanceAdapter;
import com.example.scau_faceid.util.sqlite.AsyncResponse;
import com.example.scau_faceid.util.sqlite.DBEngine;
import com.example.scau_faceid.util.sqlite.SQliteStudent;

import java.util.List;

//本Activity用于查看学生出勤情况
public class CheckAttendance extends BaseActivity {
    private DBEngine dbEngine;
    private List<SQliteStudent> studentArrayList = null;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private AttendanceAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_attendance);

        //隐藏标题栏
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }

        initEngineAndStudentData();

        TextView clearTextView = findViewById(R.id.btnClearAll);
        clearTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dbEngine == null){
                    dbEngine = new DBEngine(CheckAttendance.this);
                }
                dbEngine.deleteAllStudents();
                adapter = new AttendanceAdapter(CheckAttendance.this, null);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    private void initEngineAndStudentData() {
        dbEngine = new DBEngine(CheckAttendance.this);
        final DBEngine.QueryAsyncTask queryAsyncTask = new DBEngine.QueryAsyncTask(dbEngine.studentDao);
        queryAsyncTask.execute();
        queryAsyncTask.setOnAsyncResponse(new AsyncResponse() {//匿名内部类对象，线程类获取到此对象后调用此对象的接口方法把线程得到的数据回传
            @Override
            public void onDataReceivedSuccess(List<SQliteStudent> listData) {
                studentArrayList = listData;

                recyclerView = findViewById(R.id.attendance_rv);

                gridLayoutManager = new GridLayoutManager(CheckAttendance.this, 1);
                recyclerView.setLayoutManager(gridLayoutManager);

                adapter = new AttendanceAdapter(CheckAttendance.this, studentArrayList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onDataReceivedFailed() {
                showToast("获取失败了！可恶！");
            }
        });
    }


    @Override
    public void afterRequestPermission(int requestCode, boolean isAllGranted) {}
}
