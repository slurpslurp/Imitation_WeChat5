package com.example.imitation_wechat.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imitation_wechat.Bean.ChatBean;
import com.example.imitation_wechat.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyView> {

    private List<ChatBean> mChatBeanList;
    public ChatAdapter(List<ChatBean> chatBeanList) {
        mChatBeanList = chatBeanList;
    }


    //创建一个viewHolder  ,
    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //不需要理解，死记硬背
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        MyView myView = new MyView(view);
        return myView;
    }


    //绑定对应的值
    @Override
    public void onBindViewHolder(@NonNull MyView holder, int position) {
        ChatBean chatBean = mChatBeanList.get(position);
        boolean pd = chatBean.isPd();

        if(position!=0){
            ChatBean chatBean2 = mChatBeanList.get(position-1);
            Long nowtime=getStringToDate(chatBean.getTime());
            Long beforetime=getStringToDate(chatBean2.getTime());
            if(nowtime-beforetime<120000){
                holder.time1.setVisibility(View.VISIBLE);
                holder.time2.setVisibility(View.VISIBLE);
                holder.me_time.setVisibility(View.GONE);
                holder.you_time.setVisibility(View.GONE);
            }else {
                holder.time1.setVisibility(View.GONE);
                holder.time1.setVisibility(View.GONE);
                holder.me_time.setVisibility(View.VISIBLE);
                holder.you_time.setVisibility(View.VISIBLE);
            }
        }

        if (pd) {
            holder.me_image.setImageResource(chatBean.getImage());
            holder.me_info.setText(chatBean.getInfo());
            holder.me_time.setText(chatBean.getTime());
            holder.you_layout.setVisibility(View.GONE);
            return;
        }else {
            holder.you_image.setImageResource(chatBean.getImage());
            holder.you_info.setText(chatBean.getInfo());
            holder.you_time.setText(chatBean.getTime());
            holder.me_layout.setVisibility(View.GONE);
            return;
        }

    }

    /*将字符串转为时间戳*/
    public static long getStringToDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try{
            date = sdf.parse(time);
        } catch(ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    @Override
    public int getItemCount() {
        return mChatBeanList.size();
    }

    public int getItemViewType(int position) {
        return position;
    }

    //初始化
    static class MyView extends RecyclerView.ViewHolder {
        LinearLayout me_layout;
        ImageView me_image;
        TextView me_info;
        TextView me_time;

        LinearLayout you_layout;
        ImageView you_image;
        TextView you_info;
        TextView you_time;

        TextView time1;
        TextView time2;
        public MyView(@NonNull View itemView) {
            super(itemView);
            me_layout = itemView.findViewById(R.id.me_layout);
            me_image = itemView.findViewById(R.id.me_image);
            me_info = itemView.findViewById(R.id.me_info);
            me_time = itemView.findViewById(R.id.me_time);

            you_image = itemView.findViewById(R.id.you_image);
            you_info = itemView.findViewById(R.id.you_info);
            you_time = itemView.findViewById(R.id.you_time);
            you_layout = itemView.findViewById(R.id.you_layout);

            time1 = itemView.findViewById(R.id.time1);
            time2 = itemView.findViewById(R.id.time2);
        }
    }

}
