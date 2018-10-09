package org.devio.simple;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

/**
 * 手机设备参数工具类
 */
public class PhoneUtils {
    private static float density = 1.0f;
    private static Display defaultDisplay;
    private static int screenWidth = 0;
    private static int screenHeight = 0;
    private static String IMEI = "";

    static {
        WindowManager systemService = (WindowManager) SmmApplication
                .getInstance().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metric = new DisplayMetrics();
        defaultDisplay = systemService.getDefaultDisplay();
        defaultDisplay.getMetrics(metric);
        screenWidth = metric.widthPixels;
        screenHeight = metric.heightPixels;
        density = metric.density;
        Log.e("PhoneUtil", "screenWidth:" + screenWidth + " screenHeight:"
                + screenHeight + "  density:" + density);
    }

    public static float getDensity() {
        return density;
    }

    /**
     * 获取屏幕的宽度
     *
     * @return
     */
    public static int getScreenWidth() {
        return screenWidth;
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    public static int getScreenHeight() {
        return screenHeight;
    }

    /**
     * 返回设备的名字（如MI3）
     *
     * @return
     */
    public static String getModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 返回屏幕高度的比例（0---1.0）
     *
     * @param percent
     * @return
     */
    public static int percentOfScreenHeight(float percent) {
        if ((percent <= 0) || (percent > 1.0f)) {
            percent = 1.0f;
        }
        return (int) (getScreenHeight() * percent);
    }

    /**
     * 返回屏幕宽度的比例（0---1.0）
     *
     * @param percent
     * @return
     */
    public static int percentOfScreenWidth(float percent) {
        if ((percent <= 0) || (percent > 1.0f)) {
            percent = 1.0f;
        }
        return (int) (getScreenWidth() * percent);
    }

    /**
     * 手机号 加 *号
     *
     * @param phone
     * @return
     */
    public static String phoneAddStar(String phone) {
        if (StrUtil.isEmpty(phone) || phone.length() < 11) {
            return phone;
        }
        StringBuffer phoneBuffer = new StringBuffer();
        for (int i = 0; i < phone.length(); i++) {
            if (i >= 3 && i <= 6) {
                phoneBuffer.append("*");
            } else {
                phoneBuffer.append(phone.charAt(i));
            }
        }
        return phoneBuffer.toString();
    }
}
