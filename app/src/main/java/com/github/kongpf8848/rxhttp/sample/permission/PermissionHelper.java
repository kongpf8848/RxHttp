package com.github.kongpf8848.rxhttp.sample.permission;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.github.kongpf8848.rxhttp.sample.R;


public class PermissionHelper {

    private Object mObject;
    private OnPermissionListener listener;

    private boolean bShowSettingDialog=true;
    public static int RC_APPLICATION_DETAILS_SETTINGS=2002;
    private String[]permissions=null;
    private int requestCode=0;

    public void setShowSettingDialog(boolean bShowSettingDialog) {
        this.bShowSettingDialog = bShowSettingDialog;
    }
    public PermissionHelper(Activity actviity, OnPermissionListener listener){
        this.mObject=actviity;
        this.listener=listener;
    }
    public PermissionHelper(Fragment fragment, OnPermissionListener listener){
        this.mObject=fragment;
        this.listener=listener;

    }

    public static Activity getActivity(Object object)
    {
        if(object instanceof Fragment)
        {
            return ((Fragment)object).getActivity();
        } else if(object instanceof Activity){
            return (Activity) object;
        }
        return null;
    }

    public void requestPermissions(@NonNull String[]permissions,int requestCode){
         if(this.listener==null){
             throw new IllegalArgumentException("OnPermissionListener is null...");
         }
         this.permissions=permissions;
         this.requestCode=requestCode;

         if(PermissionUtils.isOverMarshmallow()){
             if(mObject instanceof Fragment){
                 ((Fragment)mObject).requestPermissions(permissions,requestCode);
             }
             else if(mObject instanceof Activity){
                 ((Activity)mObject).requestPermissions(permissions,requestCode);
             }

         }
         else{
             this.listener.onPermissionGranted();
         }

    }

    private boolean checkPermission(){

        boolean bResult=true;
        for(String permission:permissions){
            if(!PermissionUtils.hasPermission(getActivity(mObject),permission)){
                bResult=false;
                break;
            }
        }

        return bResult;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
         if(this.requestCode!=requestCode){
             return;
         }
         if(checkPermission()){
             this.listener.onPermissionGranted();
         }
         else{
             if(bShowSettingDialog){
                 showSettingDialog();
             }
             else{
                 this.listener.onPermissionFailed();
             }

         }
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==RC_APPLICATION_DETAILS_SETTINGS){
            if(checkPermission()){
                this.listener.onPermissionGranted();
            }
            else{
                this.listener.onPermissionFailed();
            }
        }
    }

    private void showSettingDialog() {
        final Activity activity=getActivity(mObject);
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        builder.setTitle(R.string.permission_dialog_title);
        String message=String.format(activity.getString(R.string.permission_dialog_message),
                getApplicationLabel(activity), TextUtils.join(",",Permission.transformText(activity,permissions)));
        builder.setMessage(message);
        builder.setPositiveButton(R.string.permission_dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.fromParts("package",activity.getPackageName(),null));
                if(mObject instanceof Fragment){
                    ((Fragment)mObject).startActivityForResult(intent,RC_APPLICATION_DETAILS_SETTINGS);
                }
                else if(mObject instanceof Activity){
                    ((Activity)mObject).startActivityForResult(intent,RC_APPLICATION_DETAILS_SETTINGS);
                }
            }

        });
        builder.setNegativeButton(R.string.permission_dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                  listener.onPermissionFailed();
            }
        });
        builder.show();
    }

    private String getApplicationLabel(Context context) {
        String applicationLabel = "";
        PackageManager packageManager = context.getPackageManager();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
            applicationLabel = (String) packageManager.getApplicationLabel(applicationInfo);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return applicationLabel;
    }

    public interface OnPermissionListener{
        void onPermissionGranted();
        void onPermissionFailed();

    }
}
