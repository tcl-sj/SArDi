package com.siasun.tech.servicerobot.ssframework.util.immersion;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.IntRange;

/**
 * Created by 健 on 2017/8/21.
 * 沉浸配置
 */

public class ImmersionConfig {

    public static class IcFit {
        private boolean needFit;  //view是否需要显示在statusbar下面
        private boolean isFitLightStatus;   //状态栏是否显示深色文字以适应浅色状态栏

        public IcFit(boolean needFit, boolean isFitLightStatus) {
            this.needFit = needFit;
            this.isFitLightStatus = isFitLightStatus;
        }

        public boolean isNeedFit() {
            return needFit;
        }

        public boolean isFitLightStatus() {
            return isFitLightStatus;
        }
    }
        public static class IcStatusBar{
        private @ColorInt
        int statusBarColor;   //状态栏颜色
        private @IntRange(from = 0, to = 255) int statusBarAlpha;   //透明度

        public IcStatusBar(@ColorInt int statusBarColor, @IntRange(from = 0, to = 255) int statusBarAlpha) {
            this.statusBarColor = statusBarColor;
            this.statusBarAlpha = statusBarAlpha;
        }

            public int getStatusBarColor() {
                return statusBarColor;
            }

            public int getStatusBarAlpha() {
                return statusBarAlpha;
            }
        }

    public static class IcTitleBar{
        private Drawable titleBgDrawable;
        private @ColorRes
        int titleBgColorRes;

        public IcTitleBar(Drawable titleBgDrawable) {
            this.titleBgDrawable = titleBgDrawable;
        }

        public IcTitleBar(@ColorRes int titleBgColorRes) {
            this.titleBgColorRes = titleBgColorRes;
        }

        public Drawable getTitleBgDrawable() {
            return titleBgDrawable;
        }

        public int getTitleBgColorRes() {
            return titleBgColorRes;
        }
    }

    public static class IcSystem{
        private volatile static IcSystem INSTANCE;
        private boolean hasVirtualNavigation;
        private int statusBarHeight;
        private int navigationBarHeight;

        public static IcSystem getINSTANCE(Activity activity){
            if (INSTANCE == null){
                synchronized (IcSystem.class){
                    if (INSTANCE == null){
                        INSTANCE = new IcSystem(activity);
                    }
                }
            }
            return INSTANCE;
        }

        private IcSystem(Activity activity) {
            this.hasVirtualNavigation = BarsUtil.checkVirtualNavigationBar(activity);
            this.statusBarHeight = BarsUtil.getStatusBarHeight(activity);
            this.navigationBarHeight = BarsUtil.getNavigationBarHeight(activity);
        }

        public boolean isHasVirtualNavigation() {
            return hasVirtualNavigation;
        }

        public int getStatusBarHeight() {
            return statusBarHeight;
        }

        public int getNavigationBarHeight() {
            return navigationBarHeight;
        }
    }
}
