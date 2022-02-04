package com.example.scau_faceid.widget;

import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scau_faceid.R;
import com.example.scau_faceid.faceserver.CompareResult;
import com.example.scau_faceid.faceserver.FaceServer;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

public class FaceSearchResultAdapter extends RecyclerView.Adapter<FaceSearchResultAdapter.CompareResultHolder> {
    private List<CompareResult> compareResultList;
    private LayoutInflater inflater;
    private String TAG = "PATH";

    public FaceSearchResultAdapter(List<CompareResult> compareResultList, Context context) {
        inflater = LayoutInflater.from(context);
        this.compareResultList = compareResultList;
    }

    @NonNull
    @Override
    public CompareResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recycler_item_search_result, null, false);
        CompareResultHolder compareResultHolder = new CompareResultHolder(itemView);
        compareResultHolder.textView = itemView.findViewById(R.id.tv_item_name);
        compareResultHolder.imageView = itemView.findViewById(R.id.iv_item_head_img);
        return compareResultHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CompareResultHolder holder, int position) {//绑定数据和viewHolder
        if (compareResultList == null) {
            return;
        }
        File imgFile = new File(FaceServer.ROOT_PATH + File.separator + FaceServer.SAVE_IMG_DIR + File.separator + compareResultList.get(position).getUserName() + FaceServer.IMG_SUFFIX);
        Log.i(TAG, "onBindViewHolder: " + FaceServer.ROOT_PATH + File.separator + FaceServer.SAVE_IMG_DIR + File.separator + compareResultList.get(position).getUserName() + FaceServer.IMG_SUFFIX);// /data/user/0/com.example.scau_faceid/files/register/imgs/registered 10.jpg
        Log.i(TAG, "onBindViewHolder: " + FaceServer.ROOT_PATH);//  /data/user/0/com.example.scau_faceid/files   --> if (ROOT_PATH == null) {ROOT_PATH = context.getFilesDir().getAbsolutePath(); }
        Log.i(TAG, "onBindViewHolder: " + File.separator);//  分隔符/
        Log.i(TAG, "onBindViewHolder: " + FaceServer.SAVE_IMG_DIR);//  register/imgs --> 自定义：public static final String SAVE_IMG_DIR = "register" + File.separator + "imgs";
        Log.i(TAG, "onBindViewHolder: " + File.separator);//  分割符/
        Log.i(TAG, "onBindViewHolder: " + compareResultList.get(position).getUserName());//  registered 10
        Log.i(TAG, "onBindViewHolder: " + FaceServer.IMG_SUFFIX);//  .jpg  --> 自定义：public static final String IMG_SUFFIX = ".jpg";
        Glide.with(holder.imageView)
                .load(imgFile)
                .into(holder.imageView);//加载图片
        holder.textView.setText(compareResultList.get(position).getUserName());
    }

    @Override
    public int getItemCount() {
        return compareResultList == null ? 0 : compareResultList.size();
    }

    class CompareResultHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;

        CompareResultHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
