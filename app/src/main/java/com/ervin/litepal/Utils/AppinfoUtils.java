package com.ervin.litepal.Utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.ervin.litepal.Model.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ervin on 2016/3/2.
 */
public class AppinfoUtils {
    /**
     * 获取手机所有应用信息
     * @param context ：上下文
     */
    public static List<AppInfo> getAllAppInfo(Context context)
    {
        //TextView tv;
        List<AppInfo> appInfos = new ArrayList<AppInfo>();

        PackageManager pm =  context.getPackageManager();
        //Return a List of all packages that are installed on the device.
        List<PackageInfo> packageInfos = pm.getInstalledPackages(0); //获得所有已安装APP的包信息
        for(PackageInfo packageinfo : packageInfos )
        {
            AppInfo appInfo = new AppInfo();
            String packageName = packageinfo.packageName;  //包名
            Drawable icon = packageinfo.applicationInfo.loadIcon(pm);  //应用程序的icon
            String labelName = (String) packageinfo.applicationInfo.loadLabel(pm); //应用程序名称
            int flags = packageinfo.applicationInfo.flags;//点击看源码，表示应用的一些状态

            appInfo.setPackagename(packageName);
            appInfo.setIcon(icon);
            appInfo.setLabelname(labelName);

            appInfos.add(appInfo);
        }
        return appInfos;
    }
}
