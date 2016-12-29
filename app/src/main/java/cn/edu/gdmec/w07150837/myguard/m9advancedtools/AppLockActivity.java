package cn.edu.gdmec.w07150837.myguard.m9advancedtools;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gdmec.w07150837.myguard.R;
import cn.edu.gdmec.w07150837.myguard.m9advancedtools.fragment.AppLockFragment;
import cn.edu.gdmec.w07150837.myguard.m9advancedtools.fragment.AppUnLockFragment;

/**
 * 程序锁
 */
public class AppLockActivity extends FragmentActivity implements View.OnClickListener {

    private ViewPager mAppViewPager;
    List<Fragment> mFragments = new ArrayList<Fragment>();

    private TextView mLockTV;
    private TextView mUnLockTV;

    private View slideLockView;
    private View slideUnLockView;
    private MyAdapter myAdapter;

    AppUnLockFragment unLock;
    AppLockFragment lock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_app_lock);
        initView();
        initListener();
    }


    private void initListener() {
        mAppViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    slideUnLockView.setBackgroundResource(R.drawable.slide_view);
                    slideLockView.setBackgroundColor(getResources().getColor(R.color.transparent));
                    //未加锁
                    mLockTV.setTextColor(getResources().getColor(R.color.black));
                    mUnLockTV.setTextColor(getResources().getColor(R.color.bright_red));
                } else {
                    slideLockView.setBackgroundResource(R.drawable.slide_view);
                    slideUnLockView.setBackgroundColor(getResources().getColor(R.color.transparent));
                    //已加锁
                    mLockTV.setTextColor(getResources().getColor(R.color.bright_red));
                    mUnLockTV.setTextColor(getResources().getColor(R.color.black));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                Log.d("test","华东");
//                lock.adapter.notifyDataSetChanged();
//                unLock.adapter.notifyDataSetChanged();
//                myAdapter.notifyDataSetChanged();
//                Log.d("test","gengxin");
            }

        });
    }

    private void initView() {
        findViewById(R.id.r1_titlebar).setBackgroundColor(getResources().getColor(R.color.bright_red));

        ImageView mLeftImgv = (ImageView) findViewById(R.id.imgv_leftbtn);
        ((TextView) findViewById(R.id.tv_title)).setText("程序锁");
        mLeftImgv.setOnClickListener(this);
        mLeftImgv.setImageResource(R.drawable.back);
        mAppViewPager = (ViewPager) findViewById(R.id.vp_applock);
        mLockTV = (TextView) findViewById(R.id.tv_lock);
        mUnLockTV = (TextView) findViewById(R.id.tv_unlock);

        mLockTV.setOnClickListener(this);
        mUnLockTV.setOnClickListener(this);

        slideLockView = findViewById(R.id.view_slide_lock);
        slideUnLockView = findViewById(R.id.view_slide_unlock);

        unLock = new AppUnLockFragment();
        lock = new AppLockFragment();


        mFragments.add(unLock);
        mFragments.add(lock);
        myAdapter = new MyAdapter(getSupportFragmentManager());
        mAppViewPager.setAdapter(myAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgv_leftbtn:
                finish();
                break;
            case R.id.tv_lock:
                mAppViewPager.setCurrentItem(1);
                break;
            case R.id.tv_unlock:
                mAppViewPager.setCurrentItem(0);
                break;
        }
    }

    class MyAdapter extends FragmentPagerAdapter {


        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            return mFragments.get(arg0);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
}
