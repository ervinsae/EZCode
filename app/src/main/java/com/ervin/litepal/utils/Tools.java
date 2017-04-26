package com.ervin.litepal.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

/**
 * Created by Ervin on 2015/12/22.
 */
public class Tools {

    public static Bitmap readBitMap(Context context, int resId){

        BitmapFactory.Options opt = new BitmapFactory.Options();

        opt.inPreferredConfig = Bitmap.Config.RGB_565;

        opt.inPurgeable = true;

        opt.inInputShareable = true;

        //获取资源图片

        InputStream is = context.getResources().openRawResource(resId);

        return BitmapFactory.decodeStream(is,null,opt);

    }
}
