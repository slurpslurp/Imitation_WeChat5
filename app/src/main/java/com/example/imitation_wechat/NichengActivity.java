package com.example.imitation_wechat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NichengActivity extends AppCompatActivity {
    private EditText name;
    private Button save;
    private String wechat_id;
    private String nicheng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nicheng);

        name=findViewById(R.id.name);
        save=findViewById(R.id.save);

        nicheng=(String) getIntent().getSerializableExtra("nicheng");
        wechat_id= (String) getIntent().getSerializableExtra("wechat_id");
        name.setText(nicheng);

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    save.setAlpha(1);
                    save.setEnabled(true);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nicheng=name.getText().toString();
                Intent intent = new Intent(NichengActivity.this,PersonalInfoActivity.class);
                intent.putExtra("nicheng",nicheng);
                intent.putExtra("wechat_id",wechat_id);
                startActivity(intent);
                finish();
            }
        });
    }

    public void toPersonalInfo(View view){
        Intent intent = new Intent(this,PersonalInfoActivity.class);
        intent.putExtra("nicheng",nicheng);
        intent.putExtra("wechat_id",wechat_id);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(this,PersonalInfoActivity.class);
            intent.putExtra("nicheng",nicheng);
            intent.putExtra("wechat_id",wechat_id);
            startActivity(intent);
            finish();
        }
        return true;
    }
}
