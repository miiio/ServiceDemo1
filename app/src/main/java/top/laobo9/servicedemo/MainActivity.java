package top.laobo9.servicedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lzy.okgo.OkGo;

public class MainActivity extends AppCompatActivity {
    private TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = (TextView)findViewById(R.id.tv1);
        OkGo.init(getApplication());
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view   ) {
                Intent intent=new Intent(MainActivity.this,DownLoadService.class);
                intent.putExtra("downloadUrl","http://boji9.cn/pwBox.apk");
                startService(intent);
            }
        });
    }
}
