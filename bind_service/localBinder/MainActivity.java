package com.example.administrator.localbindertext;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    LocalService mService;
    boolean mBound = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
//在start的时候，activity创建对service的bind请求，并立即返回
    @Override
    protected void onStart(){
        super.onStart();
        Intent intent = new Intent(this, LocalService.class);
        //binderService中第二个参数，是ServiceConnection对象，负责接收连接和断开的事件，所以要override其响应函数
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }
//在stop的时候，需要对binder进行释放
    @Override
    protected void onStop(){
        super.onStop();
        if(mBound){
            unbindService(mConnection);
            mBound = false;
        }
    }
//对于LocalService的client端，要实现一个ServiceConnection的对象，并实现其重载接口：与Service进行连接和失去连接时发生什么样的操作。
    private ServiceConnection mConnection = new ServiceConnection(){
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //当bindService产生时，将返回的service对象转换为LocalService的LocalBinder对象，并获取LocalService类的引用
            LocalService.LocalBinder binder = (LocalService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Menu Setting selected!",Toast.LENGTH_SHORT).show();
            onMenuSettingSelected();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onMenuSettingSelected(){
        if(mBound){
            int num = mService.getRandomNumber();
            Toast.makeText(this, "Random returned is " + num,Toast.LENGTH_SHORT).show();
        }
    }

}
