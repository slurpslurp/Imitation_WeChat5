package com.example.imitation_wechat;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TextView textView;
    private BottomBar bottomBar;
    private Intent intent;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager =findViewById(R.id.pager);
        textView=findViewById(R.id.title);
        bottomBar=findViewById(R.id.bottom_bar);//底部菜单栏

        final List<Fragment> fragmentlist=new ArrayList<>();
        fragmentlist.add(new FirstFragment());
        fragmentlist.add(new SecondFragment());
        fragmentlist.add(new ThirdFragment());
        fragmentlist.add(new FourthFragment());

        final String[] strings={"微信","通讯录","发现","我"};

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentlist.get(position);
            }

            @Override
            public int getCount() {
                return fragmentlist.size();
            }
            public CharSequence getPageTitle(int position){
                return strings[position];
            }

        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                textView.setText(strings[position]);
                bottomBar.changeIndex(position);
                LinearLayout linearLayout=findViewById(R.id.lay_top);
                ImageView search=findViewById(R.id.search);
                ImageView add=findViewById(R.id.add);
                LinearLayout take_photo=findViewById(R.id.take_photo);
                TextView title=findViewById(R.id.title);
                if (position==3) {
                    linearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                    search.setVisibility(View.GONE);
                    add.setVisibility(View.GONE);
                    title.setVisibility(View.GONE);
                    take_photo.setVisibility(View.VISIBLE);
                }else {
                    linearLayout.setBackgroundColor(getResources().getColor(R.color.Huibackground));
                    search.setVisibility(View.VISIBLE);
                    add.setVisibility(View.VISIBLE);
                    title.setVisibility(View.VISIBLE);
                    take_photo.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bottomBar.setContainer(R.id.root)
                .setTitleBeforeAndAfterColor("#999999","#06c161")
                .addItem(FirstFragment.class,"微信",R.drawable.wechat_before,R.drawable.wechat_after)
                .addItem(SecondFragment.class,"通讯录",R.drawable.phone_before,R.drawable.phone_after)
                .addItem(ThirdFragment.class,"发现",R.drawable.found_before,R.drawable.found_after)
                .addItem(FourthFragment.class,"我",R.drawable.me_before,R.drawable.me_after)
                .setCallBack(new BottomBar.CallBack() {
                    @Override
                    public void onSwitch(int pos) {
                        viewPager.setCurrentItem(pos,false);
                    }
                }).build();

    }

}
