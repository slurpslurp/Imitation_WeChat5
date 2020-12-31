package com.example.imitation_wechat;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lcw.library.imagepicker.ImagePicker;


/**
 * A simple {@link Fragment} subclass.
 */
public class FourthFragment extends Fragment implements View.OnClickListener {
    private View view;
    private LinearLayout personal_info;
    private LinearLayout pay;
    private LinearLayout collect;
    private LinearLayout photo;
    private LinearLayout wallet;
    private LinearLayout expression;
    private LinearLayout setting;


    public FourthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_fourth, container, false);

        personal_info=view.findViewById(R.id.personal_info);
        pay=view.findViewById(R.id.pay);
        collect=view.findViewById(R.id.collect);
        photo=view.findViewById(R.id.photo);
        wallet=view.findViewById(R.id.wallet);
        expression=view.findViewById(R.id.expression);
        setting=view.findViewById(R.id.setting);

        personal_info.setOnClickListener(this);
        pay.setOnClickListener(this);
        collect.setOnClickListener(this);
        photo.setOnClickListener(this);
        wallet.setOnClickListener(this);
        expression.setOnClickListener(this);
        setting.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.personal_info:
                TextView nicheng=view.findViewById(R.id.nicheng);
                TextView wechat_id=view.findViewById(R.id.wechat_id);
                Intent intent = new Intent(getActivity(),PersonalInfoActivity.class);
                intent.putExtra("nicheng",nicheng.getText().toString());
                intent.putExtra("wechat_id",wechat_id.getText().toString());
                startActivity(intent);
                break;
            case R.id.pay:
                Toast.makeText(getActivity(),"支付",Toast.LENGTH_SHORT).show();
                break;
            case R.id.collect:
                Toast.makeText(getActivity(),"收藏",Toast.LENGTH_SHORT).show();
                break;
            case R.id.photo:
                Toast.makeText(getActivity(),"相册",Toast.LENGTH_SHORT).show();
                break;
            case R.id.wallet:
                Toast.makeText(getActivity(),"卡包",Toast.LENGTH_SHORT).show();
                break;
            case R.id.expression:
                Toast.makeText(getActivity(),"表情",Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                Toast.makeText(getActivity(),"设置",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
