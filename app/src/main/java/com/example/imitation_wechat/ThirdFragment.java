package com.example.imitation_wechat;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThirdFragment extends Fragment implements View.OnClickListener {


    private View view;
    private LinearLayout pyq;
    private LinearLayout sao;
    private LinearLayout shake;
    private LinearLayout see;
    private LinearLayout find;
    private LinearLayout near_people;
    private LinearLayout shopping;
    private LinearLayout game;
    private LinearLayout xiaochengxu;


    public ThirdFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_third, container, false);

        pyq=view.findViewById(R.id.pyq);
        sao=view.findViewById(R.id.sao);
        shake=view.findViewById(R.id.shake);
        see=view.findViewById(R.id.see);
        find=view.findViewById(R.id.find);
        near_people=view.findViewById(R.id.near_people);
        shopping=view.findViewById(R.id.shopping);
        game=view.findViewById(R.id.game);
        xiaochengxu=view.findViewById(R.id.xiaochengxu);

        pyq.setOnClickListener(this);
        sao.setOnClickListener(this);
        shake.setOnClickListener(this);
        see.setOnClickListener(this);
        find.setOnClickListener(this);
        near_people.setOnClickListener(this);
        shopping.setOnClickListener(this);
        game.setOnClickListener(this);
        xiaochengxu.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.pyq:
                Toast.makeText(getActivity(),"朋友圈",Toast.LENGTH_SHORT).show();
                break;
            case R.id.sao:
                Toast.makeText(getActivity(),"扫一扫",Toast.LENGTH_SHORT).show();
                break;
            case R.id.shake:
                Toast.makeText(getActivity(),"摇一摇",Toast.LENGTH_SHORT).show();
                break;
            case R.id.see:
                Toast.makeText(getActivity(),"看一看",Toast.LENGTH_SHORT).show();
                break;
            case R.id.find:
                Toast.makeText(getActivity(),"搜一搜",Toast.LENGTH_SHORT).show();
                break;
            case R.id.near_people:
                Toast.makeText(getActivity(),"附近的人",Toast.LENGTH_SHORT).show();
                break;
            case R.id.shopping:
                Toast.makeText(getActivity(),"购物",Toast.LENGTH_SHORT).show();
                break;
            case R.id.game:
                Toast.makeText(getActivity(),"游戏",Toast.LENGTH_SHORT).show();
                break;
            case R.id.xiaochengxu:
                Toast.makeText(getActivity(),"小程序",Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
