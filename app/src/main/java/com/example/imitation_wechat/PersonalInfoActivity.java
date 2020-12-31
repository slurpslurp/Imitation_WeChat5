package com.example.imitation_wechat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PersonalInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout touxiang;
    private LinearLayout nicheng;
    private LinearLayout wechat_id;
    private LinearLayout erweima;
    private LinearLayout more;
    private LinearLayout myaddress;
    private TextView nicheng1;
    private TextView wechat_id1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        touxiang=findViewById(R.id.touxiang);
        nicheng=findViewById(R.id.nicheng);
        wechat_id=findViewById(R.id.wechat_id);
        erweima=findViewById(R.id.erweima);
        more=findViewById(R.id.more);
        myaddress=findViewById(R.id.myaddress);
        nicheng1=findViewById(R.id.nicheng1);
        wechat_id1=findViewById(R.id.wechat_id1);

        touxiang.setOnClickListener(this);
        nicheng.setOnClickListener(this);
        wechat_id.setOnClickListener(this);
        erweima.setOnClickListener(this);
        more.setOnClickListener(this);
        myaddress.setOnClickListener(this);
        nicheng1.setText((String) getIntent().getSerializableExtra("nicheng"));
        wechat_id1.setText((String) getIntent().getSerializableExtra("wechat_id"));
    }

    public void toMain(View view){
       PersonalInfoActivity.this.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            PersonalInfoActivity.this.finish();
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.touxiang:
                Toast.makeText(this,"头像",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nicheng:
                Intent intent = new Intent(this,NichengActivity.class);
                intent.putExtra("nicheng",nicheng1.getText().toString());
                intent.putExtra("wechat_id",wechat_id1.getText().toString());
                startActivity(intent);
                finish();
                break;
            case R.id.wechat_id:
                Toast.makeText(this,"微信号",Toast.LENGTH_SHORT).show();
                break;
            case R.id.erweima:
                Toast.makeText(this,"二维码名片",Toast.LENGTH_SHORT).show();
                break;
            case R.id.more:
                Toast.makeText(this,"更多",Toast.LENGTH_SHORT).show();
                break;
            case R.id.myaddress:
                Toast.makeText(this,"我的地址",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
