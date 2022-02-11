package com.example.scau_faceid.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scau_faceid.CheckAttendance;
import com.example.scau_faceid.util.sqlite.SQliteStudent;

import java.util.ArrayList;
import java.util.List;

import com.example.scau_faceid.R;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.MyViewHolder> {
    private Context context;
    private List<SQliteStudent> data;

    public AttendanceAdapter(Context context, List<SQliteStudent> studentArrayList) {
        this.context = context;
        this.data = studentArrayList;
    }

    @NonNull
    @Override
    public AttendanceAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.recylerview_item, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv.setText("姓名:" + data.get(position).getName() + "  " + "考勤时间:" + data.get(position).getAttendanceTime());
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("William", "onClick: " + getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
}
