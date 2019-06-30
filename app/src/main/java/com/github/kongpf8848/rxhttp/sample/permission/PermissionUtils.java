package com.github.kongpf8848.rxhttp.sample.permission;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtils {

    public static boolean isOverMarshmallow(){
        return Build.VERSION.SDK_INT>=Build.VERSION_CODES.M;
    }

    public static boolean hasPermission(Context context, String permission){
        boolean result = true;

        if (isOverMarshmallow())
        {
            int targetSdkVersion=getTargetSdkVersion(context);
            if (targetSdkVersion >= Build.VERSION_CODES.M)
            {

                result = ActivityCompat.checkSelfPermission(context,permission) == PackageManager.PERMISSION_GRANTED;
            }
            else
            {
                result = PermissionChecker.checkSelfPermission(context, permission) == PermissionChecker.PERMISSION_GRANTED;
            }
        }
        return result;

    }
    public static boolean isPermissionGranted(int result){
        return result==PackageManager.PERMISSION_GRANTED;
    }
    public static boolean isPermissionDenied(int result){
        return result==PackageManager.PERMISSION_DENIED;
    }

    public static List<String> getUsesPermissions(Context context){
        List<String> list=new ArrayList<>();
        PackageManager pm = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo != null) {
            String[] requestedPermissions= packageInfo.requestedPermissions;
            if (requestedPermissions != null) {
                for (String permission : requestedPermissions) {
                    list.add(permission);
                }
            }
        }
        return list;

    }
    private static int getTargetSdkVersion(Context context)
    {

        int targetSdkVersion=0;
        try
        {
            final PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            targetSdkVersion = info.applicationInfo.targetSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return targetSdkVersion;

    }



}
