package cn.edu.gdmec.w07150837.myguard.m3communicationguard;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cn.edu.gdmec.w07150837.myguard.R;
import cn.edu.gdmec.w07150837.myguard.m3communicationguard.adapter.ContactAdapter;
import cn.edu.gdmec.w07150837.myguard.m3communicationguard.entity.ContactInfo;
import cn.edu.gdmec.w07150837.myguard.m3communicationguard.utils.ContactInfoParser;


public class ContactSelectActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView mListView;
    private ContactAdapter adapter;
    private List<ContactInfo> systemContacts;
    Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 10:
                    if (systemContacts != null) {
                        adapter = new ContactAdapter(systemContacts, ContactSelectActivity.this);
                        mListView.setAdapter(adapter);
                    }
                    break;
            }
        }

        ;

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_contact_select);
        initView();
    }

    private void initView() {
        ((TextView) findViewById(R.id.tv_title)).setText("选择联系人");
        findViewById(R.id.r1_titlebar).setBackgroundColor(getResources().getColor(R.color
                .bright_purple));
        ImageView mLeftImgv = (ImageView) findViewById(R.id.imgv_leftbtn);
        mLeftImgv.setOnClickListener(this);
        mLeftImgv.setImageResource(R.drawable.back);
        mListView = (ListView) findViewById(R.id.lv_contact);
        new Thread() {
            @Override
            public void run() {
                super.run();
                systemContacts = ContactInfoParser.getSystemContact(ContactSelectActivity.this);
                mHandler.sendEmptyMessage(10);
            }

            ;
        }.start();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ContactInfo item = (ContactInfo) adapter.getItem(i);
                Intent intent = new Intent();
                intent.putExtra("phone", item.phone);
                intent.putExtra("name", item.name);
                setResult(0, intent);
                finish();

            }
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.imgv_leftbtn:
                finish();
                break;
        }

    }
}
