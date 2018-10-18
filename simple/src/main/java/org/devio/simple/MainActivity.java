package org.devio.simple;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;


/**
 * - 支持通过相机拍照获取图片
 * - 支持从相册选择图片
 * - 支持从文件选择图片
 * - 支持多图选择
 * - 支持批量图片裁切
 * - 支持批量图片压缩
 * - 支持对图片进行压缩
 * - 支持对图片进行裁剪
 * - 支持对裁剪及压缩参数自定义
 * - 提供自带裁剪工具(可选)
 * - 支持智能选取及裁剪异常处理
 * - 支持因拍照Activity被回收后的自动恢复
 * Author: crazycodeboy
 * Date: 2016/9/21 0007 20:10
 * Version:4.0.0
 * 技术博文：http://www.devio.org
 * GitHub:https://github.com/crazycodeboy
 * Email:crazycodeboy@gmail.com
 */
public class MainActivity extends Activity implements View.OnClickListener {

    private static final int NOTIFICATIONS_ID = 11111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        testNotification();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void testNotification() {
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        PendingIntent contentIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, ResultActivity.class), 0);

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_done_white)
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setContentIntent(contentIntent)
                .build();// getNotification()

        mNotifyMgr.notify(NOTIFICATIONS_ID, notification);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnTakePhotoActivity:
                startActivity(new Intent(this, SimpleActivity.class));
                break;
            case R.id.btnTakePhotoFragment:
                startActivity(new Intent(this, SimpleFragmentActivity.class));
                break;
            case R.id.btnTest:
                startActivity(new Intent(this, TestActivity.class));
                break;
            default:
        }
    }
}
