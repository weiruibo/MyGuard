//package cn.edu.gdmec.w07150837.myguard.m7processmanager;
//
//import android.app.ActivityManager;
//import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.view.Window;
//import android.widget.AbsListView;
//import android.widget.AdapterView;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//import java.util.Formatter;
//import java.util.List;
//import java.util.Objects;
//
//import cn.edu.gdmec.w07150837.myguard.R;
//import cn.edu.gdmec.w07150837.myguard.m7processmanager.adapter.ProcessManagerAdapter;
//import cn.edu.gdmec.w07150837.myguard.m7processmanager.entity.TaskInfo;
//import cn.edu.gdmec.w07150837.myguard.m7processmanager.utils.SystemInfoUtils;
//import cn.edu.gdmec.w07150837.myguard.m7processmanager.utils.TaskInfoParser;
//
//public class ProcessManagerActivity extends AppCompatActivity implements View.OnClickListener {
//    private TextView mRunProcessNum;
//    private TextView mMemoryTV;
//    private TextView mProcessNumTV;
//    private ListView mListView;
//    ProcessManagerActivity adapter;
//    private List<TaskInfo> runningTaskInfos;
//    private List<TaskInfo> userTaskInfos=new ArrayList<TaskInfo>();
//    private List<TaskInfo> sysTaskInfo=new ArrayList<TaskInfo>();
//    private ActivityManager manager;
//    private int runningPocessCount;
//    private long tota1Mem;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.activity_process_manager);
//        initView();
//        //fillData();
//    }
//
//    @Override
//    protected void onResume(){
//        if (adapter!=null){
//            //adapter.notifyDataSetChanged();
//        }
//        super.onResume();
//    }
//    private void initView(){
//        //findViewById(R.id.rl_titlebar).setBackground(getResources().getColor(R.color.bright_green));
//        ImageView mLeftImgv=(ImageView) findViewById(R.id.imgv_leftbtn);
//        mLeftImgv.setOnClickListener(this);
//        mLeftImgv.setImageResource(R.drawable.back);
//        //ImageView mRightImhv=(ImageView) findViewById(R.id.imgv_rightbtn);
//        //mRightImhv.setImageResource(R.drawable.processmanager_setting_icon);
//        //mRightImhv.setOnClickListener(this);
//        ((TextView) findViewById(R.id.tv_title)).setText("进程管理");
//        mRunProcessNum=(TextView) findViewById(R.id.tv_memory_processmanager);
//        mProcessNumTV=(TextView) findViewById(R.id.tv_user_runningprocess);
//        //runningPocessCount= SystemInfoUtils.getRunningRunningPocessCount(ProcessManagerActivity.this);
//        mRunProcessNum.setText("运行中的进程:"+runningPocessCount+"个");
//        //long totalAvailMem=SystemInfoUtils.getAvailMem(this);
//        //totalMem=SystemInfoUtils.getTotalMem();
//        //mMemoryTV.setText("可用/总内存:"+ Formatter.formatFileSize(this,totalAvailMem)+"/"+Formatter.formatFileSize(this,totalMem));
//        mListView=(ListView) findViewById(R.id.lv_runningapps);
//        initListener();
//    }
//
//    private void initListener(){
//        findViewById(R.id.btn_selectall).setOnClickListener(this);
//        findViewById(R.id.btn_select_inverse).setOnClickListener(this);
//        findViewById(R.id.btn_cleanprocess).setOnClickListener(this);
////        mListView.setOnClickListener(new AdapterView.OnItemClickListener(){
//
////            @Override
////            public void onItemClick(AdapterView<?>parent,View view,int position,long id) {
////                Object object=mListView.getItemAtPosition(position);
////                if (object1=null&object instanceof TaskInfo){
////                    TaskInfo info=(TaskInfo) object;
////                    if(info.packageName.equals(getPackageName())){
////                        return;
////                    }
////                    info.isChecked=!info.isChecked;
////                    adapter.notifyDataSetChanged();
////                }
////            }
////        });
////        mListView.setOnClickListener(new AbsListView.OnScrollListener(){
////
////            @Override
////            public void onScrollStateChanged(AbsListView view,int scrollState){
////
////            }
////
////            @Override
////            public void onScroll(AbsListView view,int firstVisibleItem,int visibleItemCount,int totalItemCount){
////                if(firstVisibleItem>=userTaskInfos.size()+1){
////                    mProcessNumTV.setText("系统进程:"+sysTaskInfo.size+"个");
////                }else{
////                    mProcessNumTV.setText("用户进程:"+userTaskInfos.size()+"个");
////                }
////            }
////        });
//    }
////    private void fillData(){
////        userTaskInfos.clear();
////        sysTaskInfo.clear();
////        new Thread(){
////            public void run(){
////                //runningTaskInfos= TaskInfoParser.getRunningTaskInfos(getApplicationContext());
////                runOnUiThread(new Runnable(){
////
////                    @Override
////                    public void run(){
////                        for(TaskInfo taskInfo:runningTaskInfos){
////                            if(taskInfo.isUserApp){
////                                userTaskInfos.add(taskInfo);
////                            }else{
////                                sysTaskInfo.add(taskInfo);
////                            }
////                        }
////                        if(adapter==null){
////                            adapter=new ProcessManagerAdapter(getApplicationContext(),userTaskInfos,sysTaskInfo);
////                            mListView.setAdapter(adapter);
////                        }else{
////                            adapter.notifyDataSetChanged();
////                        }
////                        if(userTaskInfos.size()>0){
////                            mProcessNumTV.setText("用户进程:"+userTaskInfos.size()+"个");
////                        }else{
////                            mProcessNumTV.setText("系统进程:"+sysTaskInfo.size()+"个");
////                        }
//                    }
////                });
////            };
////        }.start();
////    }
//
////    @Override
////    public void onClick(View v){
////        switch (v.getId()){
////            case R.id.imgv_leftbtn:
////                finish();
////                break;
////            case R.id.imgv_rightbtn:
////                startActivity(new Intent(this,ProcessManagerSettingActivity.class));
////                break;
////            case R.id.btn_selectall:
////                selectAll();
////                break;
////            case R.id.btn_select_inverse:
////                inverse();
////                break;
////            case R.id.btn_cleanprocess:
////                cleanProcess();
////                break;
////        }
////    }
////    private void cleanProcess(){
////        manager=(ActivityManager) getSystemService(ACTIVITY_SERVICE);
////        int count=0;
////        long saveMemory=0;
////        List<TaskInfo> killedtaskInfos=new ArrayList<TaskInfo>();
////        for(TaskInfo info:userTaskInfos){
////            if(info.isChecked){
////                count++;
////                saveMemory+=info.appMemory;
////                manager.killBackgroundProcesses(info.packageName);
////                killedtaskInfos.add(info);
////            }
////        }
////        for(TaskInfo info:killedtaskInfos){
////            if(info.isUserApp){
////                userTaskInfos.remove(info);
////            }else{
////                sysTaskInfo.remove(info);
////            }
////        }
////        runningPocessCount-=count;
////        mRunProcessNum.setText("运行中的进程:"+runningPocessCount+"个");
////        mMemoryTV.setText("可用/总内存:"+Formatter.formatFileSize(this,SystemInfoUtils.getAvailMem(this))+"/"+Formatter.formatFileSize(this,totalMem));
////        Toast.makeText(this,"清理了"+count+"个进程,释放了"+Formatter.formatFileSize(this,saveMemory)+"内存",1).show();
////        mProcessNumTV.setText("用户进程:"+userTaskInfos.size()+"个");
////        adapter.notifyDataSetChanged();
////    }
//
////    private void inverse(){
////        for(TaskInfo.taskInfo:userTaskInfos){
////            if(taskInfo.packageName.equals(getPackageName())){
////                continue;
////            }
////            boolean checked=taskInfo.isChecked;
////            taskInfo.isChecked=!checked;
////        }
////        for(TaskInfo taskInfo:sysTaskInfo){
////            boolean checked=taskInfo.isChecked;
////        }
////        adapter.notifyDataSetChanged();
////    }
////
////    private void selectAll(){
////        for(TaskInfo taskInfo:userTaskInfos){
////            if(taskInfo.packageName.equals(getPackageName())){
////                continue;
////            }
////            taskInfo.isChecked=true;
////        }
////        for(TaskInfo taskInfo:sysTaskInfo){
////            taskInfo.isChecked=true;
////        }
////        adapter.notifyDataSetChanged();
////    }
////}
