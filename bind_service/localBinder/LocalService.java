package com.example.administrator.localbindertext;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Random;

//����localBinder�ı��ط�������Ҫ����������ǰ���Binder��ľֲ��������������ṩ�˷��ظ�LocalService�Ľӿڡ�
//��ʵ�ַ���ĺ�����
public class LocalService extends Service {
    public LocalService() {
    }

    private final IBinder mBinder = new LocalBinder();
    public class LocalBinder extends Binder{
        LocalService getService(){
            //���Binder�����а�����LocalService�౾��������Լ������ô����˶Է�
            return LocalService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        //onBinder��ʱ�򣬷������Binder����
        return mBinder;
    }

    private final Random mGenerator = new Random();

    public int getRandomNumber(){
        return mGenerator.nextInt(100);
    }
}
