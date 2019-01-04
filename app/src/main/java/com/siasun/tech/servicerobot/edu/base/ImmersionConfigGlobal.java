package com.siasun.tech.servicerobot.edu.base;

import android.content.Context;

import com.siasun.tech.servicerobot.edu.R;
import com.siasun.tech.servicerobot.ssframework.util.immersion.ImmersionConfig;


/**
 * Created by 健 on 2017/8/22.
 */

public class ImmersionConfigGlobal {
    private static volatile ImmersionConfigGlobal GLOBAL;
    public ImmersionConfig.IcFit icFit;
    private ImmersionConfig.IcStatusBar icStatusBar;

    public static ImmersionConfigGlobal global(Context context){
        if (GLOBAL == null) {
            synchronized (ImmersionConfigGlobal.class) {
                if (GLOBAL == null) {
                    GLOBAL = new ImmersionConfigGlobal(context);
                }
            }
        }
        return GLOBAL;
    }

    private ImmersionConfigGlobal(Context context) {
        icFit = new ImmersionConfig.IcFit(true, false);
        icStatusBar = new ImmersionConfig.IcStatusBar(context.getResources().getColor(R.color.colorPrimary), 0);
    }

    public ImmersionConfig.IcFit icFit() {
        return icFit;
    }

    public ImmersionConfig.IcStatusBar icStatusBar() {
        return icStatusBar;
    }
}
