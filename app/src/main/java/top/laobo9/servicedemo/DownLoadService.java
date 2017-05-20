package top.laobo9.servicedemo;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;

/**
 * Created by wax on 2017/5/20.
 */

public class DownLoadService extends Service {
    private String downloadUrl;
    private String saveDir = "/storage/emulated/0/download/";
    private String saveName = "update.apk";
    private int requestCode = (int) SystemClock.uptimeMillis();
    private NotifyUtil currentNotify;
    private File mFile;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mFile = new File(saveDir+saveName);
        downloadUrl = intent.getStringExtra("downloadUrl");
        Log.e("test","onStartCommand..");
        Intent intent_noti = new Intent();
        intent_noti.setAction(Intent.ACTION_VIEW);
        //文件的类型，从tomcat里面找
        intent_noti.setDataAndType(Uri.fromFile(mFile), "application/vnd.android.package-archive");
        PendingIntent rightPendIntent = PendingIntent.getActivity(this,
                requestCode, intent_noti, PendingIntent.FLAG_UPDATE_CURRENT);
        int smallIcon = R.mipmap.ic_launcher;
        String ticker = "正在更新...";
        //实例化工具类，并且调用接口
        NotifyUtil notify7 = new NotifyUtil(this, 7);
        notify7.notify_progress(rightPendIntent, smallIcon, ticker, "正在更新...", "正在下载中",
                false, false, false, downloadUrl,saveDir,saveName, new NotifyUtil.DownLoadListener() {
                    @Override
                    public void OnSuccess(File file) {
                        mFile=file;
                        DownLoadService.this.stopSelf();
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {

                    }
                });
        currentNotify = notify7;
        return super.onStartCommand(intent, flags, startId);

    }
}