package com.chinatelecom.ctsi.workhelper.util;



import com.chinatelecom.ctsi.workhelper.model.PhoneInfo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;


/**
 * Created by apple on 14/10/31.
 */
public class SystemUtil {
    private static final String TAG = SystemUtil.class.getName();
    /*
    * 获取应用包名
    * */
    public static String getPackageName(Context context){
        return context.getPackageName();
    }
    public static String getVersionName(Context context) {
        try {

            String packageName = context.getPackageName();
            PackageInfo pinfo = context.getPackageManager().getPackageInfo(
                    packageName,
                    PackageManager.GET_CONFIGURATIONS);


            return pinfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static int getVersionCode(Context context) {
        try {

            String packageName = context.getPackageName();
            PackageInfo pinfo = context.getPackageManager().getPackageInfo(
                    packageName,
                    PackageManager.GET_CONFIGURATIONS);


            return pinfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }



    public static PhoneInfo getPhoneInfo(Context context) {
        PhoneInfo p = new PhoneInfo();
        try {
            TelephonyManager phoneMgr = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            //Build.MANUFACTURER +"@@"+ Build.BRAND +"@@"+  Build.MODEL
            //HUAWEI@@Huawei@@huwwei p6c00
            //BBK@@VIVO@VIVO X3V
            p.setPhoneName(Build.BRAND +"__"+  Build.MODEL); // 手机型号
            // phoneName
            // D530
            Log.d("DISPLAY", Build.DISPLAY + Build.PRODUCT); // 手机型号 sdk 2.1
            p.setSdkVersion(Build.VERSION.RELEASE); // 手机型号 sdk 2.1
            p.setOther(phoneMgr.getLine1Number()); // 手机型号
            String imsi = phoneMgr.getSubscriberId();
            p.setImsi(imsi);
            p.setClientVersion(getVersionName(context));

            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            p.setScreenHeight(dm.heightPixels);
            p.setScreenWidth(dm.widthPixels);
            p.setDensity(dm.densityDpi);

            String deviceId = phoneMgr.getDeviceId();
            p.setDeviceId(deviceId);

            String tel = phoneMgr.getLine1Number();
            // telephonemanage.getDeviceId(),
            String imei = phoneMgr.getSimSerialNumber();
            p.setImei(imei);
           /* Log.d("deviceid", deviceId); // A10000015F61FF47
            Log.d("tel", tel); //
            Log.d("imei", imei); // 89860310804710482259
            Log.d("imsi", imsi); // 460036040710977*/
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "获取手机信息失败"+e.getMessage());
        }
        return p;
    }
}
