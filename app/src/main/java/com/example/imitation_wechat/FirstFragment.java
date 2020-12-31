package com.example.imitation_wechat;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imitation_wechat.Adapter.InfoAdapter;
import com.example.imitation_wechat.Adapter.UsersAdapter;
import com.example.imitation_wechat.Bean.ChatBean;
import com.example.imitation_wechat.Bean.InfoBean;
import com.example.imitation_wechat.Bean.UsersBean;
import com.example.imitation_wechat.Helper.MyHelper;
import com.hb.dialog.myDialog.MyAlertDialog;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment {
    private View view;
    private List<InfoBean> infoBeanList;
    private RecyclerView recyclerView;
    private InfoAdapter adapter;
    MyHelper myHelper;

    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_first, container, false);
        myHelper = new MyHelper(getContext());
        initRecyclerView();
        initData();
        return view;
    }

    private void initData() {
        SQLiteDatabase db=myHelper.getReadableDatabase();
        Cursor cursor1=db.query("chat",null,null,null,"name",null,null);
        if(cursor1.getCount()!=0) {
            cursor1.moveToFirst();
            InfoBean newItem = new InfoBean(cursor1.getInt(2), cursor1.getString(1), cursor1.getString(3), cursor1.getString(4));
            infoBeanList.add(newItem);
            adapter.notifyDataSetChanged();
        }
        while (cursor1.moveToNext()){
            InfoBean newItem1 = new InfoBean(cursor1.getInt(2),cursor1.getString(1),cursor1.getString(3),cursor1.getString(4));
            infoBeanList.add(newItem1);
            adapter.notifyDataSetChanged();
         }
        cursor1.close();
        db.close();
    }

    private void initRecyclerView() {
        infoBeanList = new ArrayList<>();
        //获取RecyclerView
        recyclerView=view.findViewById(R.id.recyclerview);
        //创建adapter
        adapter = new InfoAdapter(getContext(),infoBeanList);
        //给RecyclerView设置adapter
        recyclerView.setAdapter(adapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

    }
}
