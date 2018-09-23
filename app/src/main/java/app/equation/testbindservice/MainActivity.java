package app.equation.testbindservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button startService;
    private Button stopService;
    private Button bindService;
    private Button unbindService;

    private MyService.MyBinder myBinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService = findViewById(R.id.btnStartService);
        stopService = findViewById(R.id.btnStopService);
        bindService = findViewById(R.id.btnBindService);
        unbindService = findViewById(R.id.btnUnbindService);

        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
        bindService.setOnClickListener(this);
        unbindService.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartService:
                Intent startIntent = new Intent(this, MyService.class);
                startService(startIntent);
                break;
            case R.id.btnStopService:
                Intent stopIntent = new Intent(this, MyService.class);
                stopService(stopIntent);
                break;
            case R.id.btnBindService:
                Intent bindIntent = new Intent(this, MyService.class);
                boolean result = bindService(bindIntent, connection, BIND_AUTO_CREATE);
                System.out.println("########### result: " + result);
                break;
            case R.id.btnUnbindService:
                System.out.println("######## connection: "+connection);
                unbindService(connection);
                break;
            default:
                break;
        }

    }


    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            System.out.println("onServiceDisconnected");
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            System.out.println("onServiceConnected");

            myBinder = (MyService.MyBinder) service;
            myBinder.startDownload();
        }
    };

}
