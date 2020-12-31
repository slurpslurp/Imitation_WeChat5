package com.example.imitation_wechat;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imitation_wechat.Adapter.UsersAdapter;
import com.example.imitation_wechat.Bean.UsersBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment implements View.OnClickListener {
    private View view;
    private List<UsersBean> usersBeansList;
    private RecyclerView recyclerView;
    private UsersAdapter adapter;
    private SideBar sideBar;

    public SecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_second, container, false);
        initRecyclerView();
        initView();
        initData();
        return view;
    }

    private void initData() {
        UsersBean a=new UsersBean(R.drawable.newfriend,"a");
        usersBeansList.add(a);
        UsersBean b=new UsersBean(R.drawable.qunliao,"b");
        usersBeansList.add(b);
        UsersBean c=new UsersBean(R.drawable.biaoqian,"c");
        usersBeansList.add(c);
        UsersBean z=new UsersBean(R.drawable.gongzhonghao,"z");
        usersBeansList.add(z);
        for (int i=1;i<57;i++){
            String imgname = "user" + i;
            int imgid = getResources().getIdentifier(imgname, "drawable", "com.example.imitation_wechat");
            UsersBean friends=new UsersBean(imgid,"旺仔"+i+"号");
            usersBeansList.add(friends);
        }
        Collections.sort(usersBeansList);

        View mFooterView = LayoutInflater.from(getActivity()).inflate(R.layout.footer, recyclerView, false);
        String footer_num= String.valueOf(adapter.getItemCount());
        TextView footer= mFooterView.findViewById(R.id.footer);
        footer.setText(footer_num+footer.getText().toString());
        adapter.setFooterView(mFooterView);

        View mHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.header, recyclerView, false);
        adapter.setHeaderView(mHeaderView);
        LinearLayout newfriend=mHeaderView.findViewById(R.id.newfriend);
        LinearLayout qunliao=mHeaderView.findViewById(R.id.qunliao);
        LinearLayout biaoqian=mHeaderView.findViewById(R.id.biaoqian);
        LinearLayout gongzhonghao=mHeaderView.findViewById(R.id.gongzhonghao);
        newfriend.setOnClickListener(this);
        qunliao.setOnClickListener(this);
        biaoqian.setOnClickListener(this);
        gongzhonghao.setOnClickListener(this);
    }


    private void initRecyclerView() {
        usersBeansList = new ArrayList<>();
        //获取RecyclerView
        recyclerView=view.findViewById(R.id.recyclerview);
        //创建adapter
        adapter = new UsersAdapter(getContext(),usersBeansList);
        //给RecyclerView设置adapter
        recyclerView.setAdapter(adapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }

    private void initView() {
        recyclerView=view.findViewById(R.id.recyclerview);
        sideBar = (SideBar) view.findViewById(R.id.side_bar);
        sideBar.setOnStrSelectCallBack(new SideBar.ISideBarSelectCallBack() {
            @Override
            public void onSelectStr(int index, String selectStr) {
                for (int i = 0; i < usersBeansList.size(); i++) {
                    if (selectStr.equalsIgnoreCase(usersBeansList.get(i).getFirstLetter())) {
                        recyclerView.scrollToPosition(i+1); // 选择到首字母出现的位置
                        return;
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.newfriend:
                Toast.makeText(getActivity(),"新的朋友",Toast.LENGTH_SHORT).show();
                break;
            case R.id.qunliao:
                Toast.makeText(getActivity(),"群聊",Toast.LENGTH_SHORT).show();
                break;
            case R.id.biaoqian:
                Toast.makeText(getActivity(),"标签",Toast.LENGTH_SHORT).show();
                break;
            case R.id.gongzhonghao:
                Toast.makeText(getActivity(),"公众号",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
