package com.example.imitation_wechat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imitation_wechat.Bean.UsersBean;
import com.example.imitation_wechat.ChatActivity;
import com.example.imitation_wechat.R;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {
    private List<UsersBean> mUsersBeanList;
    private Context mContext;
    private final int ITEM_TYPE_HEADER = 0;
    private final int ITEM_TYPE_NRMAL = 1;
    private final int ITEM_TYPE_FOOTER = 2;
    private final int ITEM_TYPE_EMPTY = 3;
    private View mHeaderView;
    private View mFooterView;
    private View mEmptyView;
    private boolean header; //记录是否有header

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView user_image;
        TextView user_name;
        TextView catalog;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            user_image=itemView.findViewById(R.id.user_image);
            user_name=itemView.findViewById(R.id.user_name);
            catalog=itemView.findViewById(R.id.catalog);
        }
    }

    public UsersAdapter(Context context,List<UsersBean> usersbeansList) {
        this.mContext=context;
        this.mUsersBeanList = usersbeansList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {//通过不同的type，创建不同布局的ViewHolder
            case ITEM_TYPE_HEADER:
                return new ViewHolder(mHeaderView);
            case ITEM_TYPE_EMPTY:
                return new ViewHolder(mEmptyView);
            case ITEM_TYPE_FOOTER:
                return new ViewHolder(mFooterView);
            default:
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_users, parent, false));
        }
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_users, parent, false);
//        ViewHolder holder = new ViewHolder(view);
//        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == ITEM_TYPE_HEADER || type == ITEM_TYPE_FOOTER || type == ITEM_TYPE_EMPTY) return;
        final UsersBean usersBean = mUsersBeanList.get(getRealItemPosition(position));
        holder.user_image.setImageResource(usersBean.getUser_image());
        holder.user_name.setText(usersBean.getUser_name());

        String catalog = usersBean.getFirstLetter();         //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(catalog)) {
            holder.catalog.setVisibility(View.VISIBLE);
            holder.catalog.setText(catalog.toUpperCase());
        } else {
            holder.catalog.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra("chat_nameid",usersBean.getUser_name());
                intent.putExtra("chat_imageid",usersBean.getUser_image());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        int itemCount = mUsersBeanList.size();
        if (null != mEmptyView && itemCount == 0) itemCount++;//对Empty的判断，一定要放在第一位
        if (null != mHeaderView) itemCount++;
        if (null != mFooterView) itemCount++;
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {//设置不同类型的type
        if (null != mHeaderView && position == 0){
            header=true;
            return ITEM_TYPE_HEADER;
        }
        else if (null != mFooterView && position == getItemCount() - 1)
            return ITEM_TYPE_FOOTER;
        else if (null != mEmptyView && mUsersBeanList.size() == 0)
            return ITEM_TYPE_EMPTY;
        return ITEM_TYPE_NRMAL;

    }

    private int getRealItemPosition(int position) {//获取实际列表数据的position
        if (null != mHeaderView) return position - 1;
        return position;
    }

    /**
     * 设置header
     */
    public void setHeaderView(View headerView) {
        this.mHeaderView = headerView;
        notifyItemInserted(0);
    }

    /**
     * 设置footer
     */
    public void setFooterView(View footerView) {
        this.mFooterView = footerView;
        notifyItemInserted(getItemCount() - 1);
    }

    /**
     * 设置data为空的时候显示的view
     */
    public void setEmptyView(View emptyView) {
        this.mEmptyView = emptyView;
        notifyDataSetChanged();
    }

    /**
     * 获取catalog首次出现位置
     */
    public int getPositionForSection(String catalog) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = mUsersBeanList.get(i).getFirstLetter();
            if (catalog.equalsIgnoreCase(sortStr)) {
                if(header==true){
                    return i+1;
                }
               else {
                   return i;
               }
            }
        }
        return -1;
    }
}
