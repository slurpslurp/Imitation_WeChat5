package com.example.imitation_wechat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imitation_wechat.Helper.MyHelper;
import com.hb.dialog.myDialog.MyAlertDialog;

public class ChatMoreActivity extends AppCompatActivity implements View.OnClickListener {
    private int chat_imageid;
    private String chat_nameid;
    private RoundImageView chat_image;
    private TextView chat_name;
    private LinearLayout delete_chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_more);

        chat_nameid = (String) getIntent().getSerializableExtra("chat_nameid");
        chat_imageid = (int) getIntent().getSerializableExtra("chat_imageid");
        chat_name = findViewById(R.id.chat_name);
        chat_image = findViewById(R.id.chat_image);

        chat_name.setText(chat_nameid);
        chat_image.setImageResource(chat_imageid);

        delete_chat = findViewById(R.id.delete_chat);
        delete_chat.setOnClickListener(this);
    }

    public void toChat(View view) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("chat_nameid", chat_nameid);
        intent.putExtra("chat_imageid", chat_imageid);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(this, ChatActivity.class);
            intent.putExtra("chat_nameid", chat_nameid);
            intent.putExtra("chat_imageid", chat_imageid);
            startActivity(intent);
            finish();
        }
        return true;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.delete_chat:
                MyAlertDialog myAlertDialog = new MyAlertDialog(this).builder()
                        .setMsg("确定删除和" + chat_nameid + "的聊天记录吗?")
                        .setPositiveButton("清空", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SQLiteDatabase db;
                                MyHelper myHelper = new MyHelper(ChatMoreActivity.this);
                                db = myHelper.getWritableDatabase();
                                db.delete("chat", "name=?", new String[]{chat_nameid});
                                db.close();
                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        });
                myAlertDialog.show();
                break;
        }
    }
}
