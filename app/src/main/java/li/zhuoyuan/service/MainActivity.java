package li.zhuoyuan.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnstart, btnstop, btnbind, btnunbind;
    Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        initevent();
    }

    private void initevent() {
        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(serviceIntent);
            }
        });

        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(serviceIntent);
            }
        });

        btnbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bindService(serviceIntent, conn, BIND_AUTO_CREATE);
            }
        });
        btnunbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unbindService(conn);
            }
        });
    }

    private void initview() {
        serviceIntent = new Intent(this, MyService.class);
        btnstart = (Button) findViewById(R.id.start);
        btnstop = (Button) findViewById(R.id.stop);
        btnbind = (Button) findViewById(R.id.bind);
        btnunbind = (Button) findViewById(R.id.unbind);
    }

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //拿到后台服务代理对象
            MyService.MyBinder myBinder = (MyService.MyBinder) iBinder;
            //调用后台服务的方法
            myBinder.showToast();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e("MainActivity", "onServiceDisconnected: onServiceDisconnected");
        }
    };
}
