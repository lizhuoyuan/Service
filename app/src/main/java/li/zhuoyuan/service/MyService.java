package li.zhuoyuan.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

import static android.content.ContentValues.TAG;

public class MyService extends Service {

    private Thread a;
    private boolean threadrun;

    public MyService() {
    }

    //创建一个binder对象，给前台调用
    class MyBinder extends Binder {
        public void showToast() {
            Log.e("Service", "showToast");
        }
    }

    //绑定服务时调用
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("Service", "onBind");

        startThread();
        return new MyBinder();
    }

    //服务创建时调用
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("Service", "onCreate");
        showNotification();
    }

    //执行StartService时调用
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("Service", "onStartCommand");
        startThread();
        return super.onStartCommand(intent, flags, startId);
    }

    //服务被销毁时调用
    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy: ");
        stopThread(a);
        super.onDestroy();
    }

    //停止线程
    public void stopThread(Thread t) {
        if (t == null) {
            return;
        }
        threadrun = false;
    }


    //解除绑定时调用
    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("Service", "onUnbind");
        return super.onUnbind(intent);
    }

    //开启线程
    private void startThread() {
        a = new Thread(new Runnable() {
            @Override
            public void run() {
                //执行耗时操作
                threadrun = true;
                while (threadrun) {
                    try {
                        Log.e("Service", "doSomething");
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        a.start();
    }

    //启动前台 通知
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void showNotification() {
        //创建跳转intent
        Intent i = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);
        //创建通知详细信息
        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.qq)
                .setContentTitle(new Date().toString())
                .setContentText("天气真热")
                .setContentIntent(pendingIntent)
                //点击通知之后会清除该通知
                .setAutoCancel(true);
        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, notification);
        startForeground(0, notification); //变成前台服务
    }


}
