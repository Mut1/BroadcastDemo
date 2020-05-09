package com.mut.broadcastdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tv__battery;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        registerBatteryReceiver();

    }

    private void initview() {
        //

        tv__battery = (TextView) findViewById(R.id.battery_level);
    }

    private void registerBatteryReceiver() {
        //第二步
        //我们要收听的频道：电量变化
        IntentFilter intentFilter=new IntentFilter();
        //第三步
        //设置频道
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);

        //添加权限 android.permission.BATTERY_STATS
        //第四步
        BatteryLevelReceiver batteryLevelReceiver=new BatteryLevelReceiver();
        //第五步
        //注册广播
        //这种注册方式是动态注册
        this.registerReceiver(batteryLevelReceiver,intentFilter);
    }


    /**
     * 第一步，创建一个广播接收者
     *
     */
    private class BatteryLevelReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
       String action = intent.getAction();
            Log.d(TAG,"action is =======>>>"+action);
            if(tv__battery!=null)
            {
                tv__battery.setText("当前电量 is =======>>>"+intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0));
            }
           int currentLevel =  intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
           int maxLevel= intent.getIntExtra(BatteryManager.EXTRA_SCALE,0);
            //拿到当前电量以后，再除以最大值
            float percent = currentLevel*1.0f/maxLevel*100;
            Log.d(TAG,"当前电量百分比："+percent+"%");
        }
    }
}
