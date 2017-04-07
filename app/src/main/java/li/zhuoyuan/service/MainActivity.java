package li.zhuoyuan.service;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static li.zhuoyuan.service.MyIntentService.ACTION_BAZ;
import static li.zhuoyuan.service.MyIntentService.EXTRA_PARAM1;
import static li.zhuoyuan.service.MyIntentService.EXTRA_PARAM2;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "com";
    Button btnstart, btnstop, btnbind, btnunbind, btnintent;
    Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        initevent();
    }

    private void initevent() {
        registerReceiver();
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
        btnintent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentS = new Intent(MainActivity.this, MyIntentService.class);
                intentS.putExtra(EXTRA_PARAM1, "11");
                intentS.putExtra(EXTRA_PARAM2, "2");
                intentS.setAction(ACTION_BAZ);
                startService(intentS);
            }
        });
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(TAG);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    private void initview() {
        serviceIntent = new Intent(this, MyService.class);
        btnstart = (Button) findViewById(R.id.start);
        btnstop = (Button) findViewById(R.id.stop);
        btnbind = (Button) findViewById(R.id.bind);
        btnunbind = (Button) findViewById(R.id.unbind);
        btnintent = (Button) findViewById(R.id.intentS);
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

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean a = intent.getBooleanExtra("a", false);

            if (a) {
                String b = intent.getStringExtra("b");
                Toast.makeText(MainActivity.this, "" + b, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}
