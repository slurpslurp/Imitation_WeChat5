package com.example.imitation_wechat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imitation_wechat.Bean.InfoBean;
import com.example.imitation_wechat.ChatActivity;
import com.example.imitation_wechat.ChatMoreActivity;
import com.example.imitation_wechat.FirstFragment;
import com.example.imitation_wechat.Helper.MyHelper;
import com.example.imitation_wechat.R;
import com.hb.dialog.myDialog.MyAlertDialog;

import java.util.Collections;
import java.util.List;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.MyView>  {
    private List<InfoBean> mInfoBeanList;
    private Context mContext;
    PopupWindow mPopupWindow;
    int screenHeight;
    int screenWidth;
    int downX;
    int downY;

    public InfoAdapter(Context context,List<InfoBean> infoBeanList) {
        this.mContext=context;
        mInfoBeanList = infoBeanList;

    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info, parent, false);
        MyView myView = new MyView(view);
        return myView;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyView holder, final int position) {
        final InfoBean infoBean = mInfoBeanList.get(position);
        holder.info_image.setImageResource(infoBean.getImage());
        holder.info_name.setText(infoBean.getName());
        holder.info_time.setText(infoBean.getTime());
        holder.info_info.setText(infoBean.getInfo());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra("chat_nameid",infoBean.getName());
                intent.putExtra("chat_imageid",infoBean.getImage());
                mContext.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Vibrator vibrator = (Vibrator)mContext.getSystemService(mContext.VIBRATOR_SERVICE);
                vibrator.vibrate(500);
                showPopupWindow(holder.itemView,infoBean.getName(),position);
                return false;
            }
        });
        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                downX = (int) motionEvent.getRawX();
                downY = (int) motionEvent.getRawY();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mInfoBeanList.size();
    }


    public class MyView extends RecyclerView.ViewHolder {
        ImageView info_image;
        TextView info_name;
        TextView info_time;
        TextView info_info;

        public MyView(@NonNull View itemView) {
            super(itemView);
            info_image=itemView.findViewById(R.id.info_image);
            info_name=itemView.findViewById(R.id.info_name);
            info_time=itemView.findViewById(R.id.info_time);
            info_info=itemView.findViewById(R.id.info_info);
        }
    }

    public void showPopupWindow(final View anchorView, final String name,final int position) {
        screenHeight = anchorView.getResources().getDisplayMetrics().heightPixels;
        screenWidth = anchorView.getResources().getDisplayMetrics().widthPixels;
        final View contentView = LayoutInflater.from(anchorView.getContext()).inflate(R.layout.item_popup_window, null);
        View.OnClickListener menuItemOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Click " + ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
            }
        };
        contentView.findViewById(R.id.menu_item1).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.menu_item2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
                Collections.swap(mInfoBeanList,position,0);
                notifyDataSetChanged();
            }
        });
        contentView.findViewById(R.id.menu_item3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
                MyAlertDialog myAlertDialog = new MyAlertDialog(contentView.getContext()).builder()
                        .setMsg("删除后，将清空该聊天的消息记录")
                        .setPositiveButton("删除", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SQLiteDatabase db;
                                MyHelper myHelper = new MyHelper(contentView.getContext());
                                db = myHelper.getWritableDatabase();
                                db.delete("chat", "name=?", new String[]{name});
                                db.close();
                                mInfoBeanList.remove(position);
                                notifyDataSetChanged();

                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        });
                myAlertDialog.show();
            }
        });
        mPopupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
//计算View位置
        final int windowPos[] = new int[2];
        final int anchorLoc[] = new int[2];
        // 获取锚点View在屏幕上的左上角坐标位置
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
// 计算contentView的高宽
        final int popuHeight = contentView.getMeasuredHeight();
        final int popuWidth = contentView.getMeasuredWidth();
        // 判断Y坐标
        if (downY > screenHeight / 2) {
//向上弹出
            windowPos[1] = downY - popuHeight;
        } else {
            //向下弹出
            windowPos[1] = downY;
        }
// 判断X坐标
        if (downX > screenWidth / 2) {
//向左弹出
            windowPos[0] = downX - popuWidth;
        } else {
//向右弹出
            windowPos[0] = downX;
        }
        int xOff = -20; // 调整偏移
        windowPos[0] -= xOff;
        mPopupWindow.showAtLocation(anchorView, Gravity.TOP | Gravity.START, windowPos[0], windowPos[1]);
    }

}
