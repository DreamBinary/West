package com.example.west2_3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecycleView;
    HomeAdapter mAdapter;
    public List<Up> mData;
    //    private ImageView mImageView2;
    private TextView mTextViewName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecycleView = findViewById(R.id.recycler_view);
        mTextViewName = findViewById(R.id.ti_tv);
        ImageView mImageView1 = findViewById(R.id.ti_iv1);
//        mImageView2 = findViewById(R.id.ti_iv2);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecycleView.setLayoutManager(layoutManager);
        initData();
        mAdapter = new HomeAdapter(mData);
        mRecycleView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String str = "   " +  mData.get(position).name + " 的动态简介";
                mTextViewName.setText(str);
                mImageView1.setVisibility(View.VISIBLE);
                mImageView1.setImageResource(mData.get(position).icon);
            }
        });

        mAdapter.setOnItemClickListener2(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mTextViewName.setText(null);
                mImageView1.setVisibility(View.GONE);
            }
        });

        mAdapter.setOnItemLongClickListener(new HomeAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int position) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("pos",position);
                intent.putExtra("data_icon", mData.get(position).icon);
                intent.putExtra("data_name", mData.get(position).name);
                intent.putExtra("data_fans", mData.get(position).fans);
                startActivityForResult(intent,1);
            }
        });
    }

    private void initData() {
        mData = new ArrayList<>();
        for (int i = 0; i < Datas.name.length; i++) {
            Up data = new Up();
            data.icon = Datas.icon[i];
            data.name = Datas.name[i];
            data.fans = Datas.fans[i];
            mData.add(data);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            int pos2 = data.getIntExtra("pos2",0);
            mData.remove(pos2);
            mAdapter.notifyDataSetChanged();
        }
    }




}