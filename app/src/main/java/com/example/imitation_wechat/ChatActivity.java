package com.example.imitation_wechat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imitation_wechat.Adapter.ChatAdapter;
import com.example.imitation_wechat.Bean.ChatBean;
import com.example.imitation_wechat.Bean.MsgBean;
import com.example.imitation_wechat.Helper.MyHelper;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements HttpUtils.CallBack{
    TextView chat_user;
    MyHelper myHelper;
    private List<ChatBean> chatBeanList;
    private RecyclerView recyclerView;
    private EditText textinfo;
    private Button send;
    private ChatAdapter chatAdapter;
    private TextView writing;
    private int chat_imageid;
    private String chat_nameid;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chat_user=findViewById(R.id.chat_user);
        chat_nameid= (String) getIntent().getSerializableExtra("chat_nameid");
        chat_user.setText(chat_nameid);

        chat_imageid= (int) getIntent().getSerializableExtra("chat_imageid");

        chatBeanList = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter(chatBeanList);
        recyclerView.setAdapter(chatAdapter);

        textinfo = findViewById(R.id.textinfo);
        send = findViewById(R.id.send);
        writing=findViewById(R.id.writing);

        myHelper=new MyHelper(this);
        init();

        textinfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(textinfo.getText().toString().equals("")){
                    send.setAlpha(0.5f);
                    send.setEnabled(false);
                }else {
                    send.setAlpha(1);
                    send.setEnabled(true);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = textinfo.getText().toString();
                Long timeStamp = System.currentTimeMillis();  //获取当前时间戳
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String sd = sdf.format(new Date(Long.parseLong(String.valueOf(timeStamp))));      // 时间戳转换成时间

                HttpUtils.doGetAsyn("https://api.qingyunke.com/api.php?key=free&appid=0&msg="+textinfo.getText(),ChatActivity.this);
                chat_user.setVisibility(View.GONE);
                writing.setVisibility(View.VISIBLE);
                ChatBean newItem = new ChatBean(s,R.drawable.touxiang,sd,true);
                chatBeanList.add(newItem);
                chatAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(chatAdapter.getItemCount()-1);
                textinfo.setText("");

                db=myHelper.getReadableDatabase();
                ContentValues values;
                values=new ContentValues();
                values.put("name",chat_nameid);
                values.put("image",R.drawable.touxiang);
                values.put("info",s);
                values.put("time",sd);
                values.put("pd","true");
                db.insert("chat",null,values);
            }
        });
    }

    @Override
    public void onRequestComplete(String result) {
        setText(result);
    }

    @Override
    public void onRequestError(String result) {
        setText(result);
    }

    private void setText(final String str){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Long timeStamp = System.currentTimeMillis();  //获取当前时间戳
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String sd = sdf.format(new Date(Long.parseLong(String.valueOf(timeStamp))));      // 时间戳转换成时间

                Gson gson=new Gson();
                MsgBean msgBean=gson.fromJson(str,MsgBean.class);
                ChatBean newItem = new ChatBean(msgBean.getContent(), chat_imageid,sd,false);
                chatBeanList.add(newItem);
                chatAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(chatAdapter.getItemCount()-1);
                writing.setVisibility(View.GONE);
                chat_user.setVisibility(View.VISIBLE);

                db=myHelper.getReadableDatabase();
                ContentValues values;
                values=new ContentValues();
                values.put("name",chat_nameid);
                values.put("image",chat_imageid);
                values.put("info",msgBean.getContent());
                values.put("time",sd);
                values.put("pd","false");
                db.insert("chat",null,values);
            }
        });
    }

    public void toMain(View view){
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void toMore(View view){
        Intent intent = new Intent(this,ChatMoreActivity.class);
        intent.putExtra("chat_nameid",chat_nameid);
        intent.putExtra("chat_imageid",chat_imageid);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return true;
    }

    private void init(){
        db=myHelper.getReadableDatabase();
        Cursor cursor1=db.query("chat",null,"name=?",new String[]{chat_nameid},null,null,null,null);
        if(cursor1.getCount()==0){
            Toast.makeText(this,"没有数据",Toast.LENGTH_SHORT).show();
        }else {
            cursor1.moveToFirst();
            ChatBean newItem = new ChatBean(cursor1.getString(3),cursor1.getInt(2),cursor1.getString(4),Boolean.valueOf(cursor1.getString(5)));
            chatBeanList.add(newItem);
            chatAdapter.notifyDataSetChanged();
        }
        while (cursor1.moveToNext()){
            ChatBean newItem = new ChatBean(cursor1.getString(3),cursor1.getInt(2),cursor1.getString(4),Boolean.valueOf(cursor1.getString(5)));
            chatBeanList.add(newItem);
            chatAdapter.notifyDataSetChanged();
        }
        cursor1.close();
        db.close();
    }
}
