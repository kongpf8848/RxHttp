package com.github.kongpf8848.rxhttp.sample.permission;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Permission {

    public static List<String> transformText(Context context, String... permissions) {
        return transformText(context, Arrays.asList(permissions));
    }
    public static List<String> transformText(Context context, String[]... groups) {
        List<String> permissionList = new ArrayList<>();
        for (String[] group : groups) {
            permissionList.addAll(Arrays.asList(group));
        }
        return transformText(context, permissionList);
    }

    public static List<String> transformText(Context context, List<String> permissions) {
        List<String> textList = new ArrayList<>();
        PackageManager pm=context.getPackageManager();
        for (String permission : permissions) {
            String message="";
            try {
                PermissionInfo permissionInfo = pm.getPermissionInfo(permission, 0);
                PermissionGroupInfo groupInfo=pm.getPermissionGroupInfo(permissionInfo.group,0);
                if(groupInfo!=null){
                    message=groupInfo.loadLabel(pm).toString();
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            if (!textList.contains(message)) {
                textList.add(message);
            }

        }
        return textList;
    }

}