package com.example.west2_3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    private TextView mTextViewName;
    private TextView mTextViewFans;
    private ImageView mImageView;
    private int pos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activiity);
        mTextViewName = findViewById(R.id.name);
        mTextViewFans = findViewById(R.id.fans);
        mImageView = findViewById(R.id.iv);
        Button mButton = findViewById(R.id.button);

        init();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailActivity.this,"取关成功",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();
                intent.putExtra("pos2",pos);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }

    private void init() {
        Intent intent = getIntent();
        Up up = new Up();

        pos = intent.getIntExtra("pos",0);
        up.icon = intent.getIntExtra("data_icon",0);
        up.name = intent.getStringExtra("data_name");
        up.fans = intent.getIntExtra("data_fans",0);

        mImageView.setImageResource(up.icon);
        mTextViewName.setText(up.name);
        String fans = "  粉丝数 ： " + up.fans;
        mTextViewFans.setText(fans);

    }

}