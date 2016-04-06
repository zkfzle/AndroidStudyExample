package com.example.administrator.localbindertext;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Random;

//对于localBinder的本地服务，所需要做的事情就是包含Binder类的局部声明，在其中提供了返回该LocalService的接口。
//并实现服务的函数。
public class LocalService extends Service {
    public LocalService() {
    }

    private final IBinder mBinder = new LocalBinder();
    public class LocalBinder extends Binder{
        LocalService getService(){
            //这个Binder对象中包含了LocalService类本身，就像把自己的引用传给了对方
            return LocalService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        //onBinder的时候，返回这个Binder对象
        return mBinder;
    }

    private final Random mGenerator = new Random();

    public int getRandomNumber(){
        return mGenerator.nextInt(100);
    }
}
