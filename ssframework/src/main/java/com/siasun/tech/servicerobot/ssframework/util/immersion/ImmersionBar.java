package com.siasun.tech.servicerobot.ssframework.util.immersion;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.siasun.tech.servicerobot.ssframework.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by 健 on 2017/8/21.
 */

public class ImmersionBar {

    private static final int IMMERSION_STATUS_BAR_VIEW_ID = R.id.immersion_status_bar_view;

    private ImmersionConfig.IcFit mConfigFit;
    private ImmersionConfig.IcStatusBar mConfigStatusBar;

    private ActionBar mActionBar;
    private ViewGroup mContentView;

    public static ImmersionBar newInstance(ImmersionConfig.IcFit configFit,
                                           ImmersionConfig.IcStatusBar icStatusBar) {
        return new ImmersionBar(configFit, icStatusBar);
    }

    private ImmersionBar(ImmersionConfig.IcFit configFit,
                         ImmersionConfig.IcStatusBar icStatusBar) {
        this.mConfigFit = configFit;
        this.mConfigStatusBar = icStatusBar;
    }

    public void initImmersion(Activity activity) {
        this.mContentView = (ViewGroup) activity.getWindow().getDecorView().findViewById(Window.ID_ANDROID_CONTENT);
        fitStatusBar(activity, null);
        init(activity, false);
    }

    public void initImmersion(Activity activity, View fitVew) {
        this.mContentView = (ViewGroup) activity.getWindow().getDecorView().findViewById(Window.ID_ANDROID_CONTENT);
        fitStatusBar(activity, fitVew);
        init(activity, false);
    }

    public void initImmersion(Activity activity, ActionBar actionBar) {
        this.mContentView = (ViewGroup) activity.getWindow().getDecorView().findViewById(Window.ID_ANDROID_CONTENT);
        this.mActionBar = actionBar;
        init(activity, true);
    }

    /**
     * 重新绘制标题栏高度，解决状态栏与顶部重叠问题
     * Sets title bar.
     */
    public void fitStatusBar(Activity activity, @Nullable View fitView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (mConfigFit.isNeedFit()){
                if (fitView == null){
                    fitView = new View(activity);
                    ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, 0);
                    fitView.setLayoutParams(layoutParams);
                    fitView.setBackgroundColor(Color.TRANSPARENT);
                    if (mContentView.getChildAt(0) instanceof LinearLayout){
                        ((ViewGroup) mContentView.getChildAt(0)).addView(fitView, 0);
                    }else {
                        throw new IllegalStateException("The root view must be LinearLayout");
                    }
                }
                ViewGroup.LayoutParams layoutParams = fitView.getLayoutParams();
                if (layoutParams.height != ViewGroup.LayoutParams.MATCH_PARENT
                        && layoutParams.height != ViewGroup.LayoutParams.WRAP_CONTENT){
                    layoutParams.height = layoutParams.height + ImmersionConfig.IcSystem.getINSTANCE(activity).getStatusBarHeight();
                }
                fitView.setPadding(fitView.getPaddingLeft(),
                        fitView.getPaddingTop() + ImmersionConfig.IcSystem.getINSTANCE(activity).getStatusBarHeight(),
                        fitView.getPaddingRight(), fitView.getPaddingBottom());
                fitView.setLayoutParams(layoutParams);
            }
        }
    }

    private void init(Activity activity, boolean isActionBar) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;  //防止系统栏隐藏时内容区域大小发生变化
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !ImmersionOsUtil.isEMUI3_1()) {
                uiFlags = initImmersionUpLOLLIPOP(activity, uiFlags); //初始化5.0以上，包含5.0
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && mConfigFit.isFitLightStatus()){
                    uiFlags = darkModeForM(activity, uiFlags); //android 6.0以上设置状态栏字体为暗色
                }
            } else {
                initImmersionDownLOLLIPOP(activity); //初始化5.0以下，4.4以上沉浸式
            }
            if (isActionBar){
                adjustActionBar(activity, isActionBar);
            }
            activity.getWindow().getDecorView().setSystemUiVisibility(uiFlags
                    | View.SYSTEM_UI_FLAG_VISIBLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        if (mConfigFit.isFitLightStatus()){
            if (ImmersionOsUtil.isMIUI6Later() && mConfigFit.isFitLightStatus()){
                darkModeForMIUI6(activity, true);
            }
            if (ImmersionOsUtil.isFlyme4Later()) {          // 修改Flyme OS状态栏字体颜色
                if (mConfigFit.isFitLightStatus()){
                    darkModeForFlyme4(activity, true);
                }
            }
        }
    }

    /**
     * 初始化android 5.0以上状态栏和导航栏
     *
     * @param uiFlags the ui flags
     * @return the int
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private int initImmersionUpLOLLIPOP(Activity activity, int uiFlags) {
        uiFlags |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;  //Activity全屏显示，但状态栏不会被隐藏覆盖，状态栏依然可见，Activity顶端布局部分会被状态栏遮住。
//        uiFlags |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION; //Activity全屏显示，但导航栏不会被隐藏覆盖，导航栏依然可见，Activity底部布局部分会被导航栏遮住。
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        if (ImmersionConfig.IcSystem.getINSTANCE(activity).isHasVirtualNavigation()){
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);  //需要设置这个才能设置状态栏颜色

        activity.getWindow().setStatusBarColor(blendStatusColor(mConfigStatusBar.getStatusBarColor(), mConfigStatusBar.getStatusBarAlpha()));  //设置状态栏颜色
        return uiFlags;
    }

    /**
     * android 6.0设置字体颜色
     */
    @RequiresApi(Build.VERSION_CODES.M)
    private int darkModeForM(Activity activity, int uiFlags) {
        //如果是6.0以上将状态栏文字改为黑色，并设置状态栏颜色
        /*activity.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        activity.getWindow().setStatusBarColor(color);*/

        if (mConfigFit.isFitLightStatus()) {
            return uiFlags | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        } else {
            return uiFlags & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
    }

    /**
     * 初始化android 4.4和emui3.1状态栏和导航栏
     */
    private void initImmersionDownLOLLIPOP(Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
        setFakeStatusView(activity, mConfigStatusBar.getStatusBarColor(), mConfigStatusBar.getStatusBarAlpha()); //创建一个假的状态栏
        //透明导航栏，设置这个，如果有导航栏，底部布局会被导航栏遮住
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    /**
     * 在状态栏添加一个与状态栏大小相同的彩色矩形条
     *
     * @param activity       需要设置的activity
     * @param color          状态栏颜色值
     * @param statusBarAlpha 透明值
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static void setFakeStatusView(Activity activity, @ColorInt int color, @IntRange(from = 0, to = 255) int statusBarAlpha) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        View fakeStatusBarView = decorView.findViewById(IMMERSION_STATUS_BAR_VIEW_ID);
        if (fakeStatusBarView != null) {
            if (fakeStatusBarView.getVisibility() == View.GONE) {
                fakeStatusBarView.setVisibility(View.VISIBLE);
            }
        } else {
            fakeStatusBarView = new View(activity);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ImmersionConfig.IcSystem.getINSTANCE(activity).getStatusBarHeight());
            fakeStatusBarView.setLayoutParams(lp);
            fakeStatusBarView.setId(IMMERSION_STATUS_BAR_VIEW_ID);
            decorView.addView(fakeStatusBarView);
        }
        if (fakeStatusBarView != null) {
            fakeStatusBarView.setBackgroundColor(blendStatusColor(color, statusBarAlpha));
        }
    }

    /**
     * 支持actionBar的界面
     * Support action bar.
     */
    private void adjustActionBar(Activity activity, boolean isActionBar) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !ImmersionOsUtil.isEMUI3_1()) {
            if (isActionBar) {
                mContentView.setPadding(0,
                        ImmersionConfig.IcSystem.getINSTANCE(activity).getStatusBarHeight()+ mActionBar.getHeight(),
                        0, 0);
            } else {
                if (mConfigFit.isNeedFit()){
                    mContentView.setPadding(0, ImmersionConfig.IcSystem.getINSTANCE(activity).getStatusBarHeight(),
                            0, 0);
                }
                else{
                    mContentView.setPadding(0, 0, 0, 0);
                }
            }
        }
    }


    /**
     * 设置Flyme4+的darkMode,darkMode时候字体颜色及icon变黑
     * http://open-wiki.flyme.cn/index.php?title=Flyme%E7%B3%BB%E7%BB%9FAPI
     */
    private static boolean darkModeForFlyme4(Activity activity, boolean darkMode) {
        boolean result = false;
        try {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (darkMode) {
                value |= bit;
            } else {
                value &= ~bit;
            }

            meizuFlags.setInt(lp, value);
            activity.getWindow().setAttributes(lp);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("StatusBar", "darkIcon: failed");
        }
        return result;
    }

    /**
     * MIUI 6的沉浸支持透明白色字体和透明黑色字体
     * 设置MIUI6+的状态栏是否为darkMode,darkMode时候字体颜色及icon变黑
     * http://dev.xiaomi.com/doc/p=4769/
     */
    private static boolean darkModeForMIUI6(Activity activity, boolean darkMode) {
        boolean result = false;
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkMode ? darkModeFlag : 0, darkModeFlag);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 计算状态栏颜色
     *
     * @param color color值
     * @param alpha alpha值
     * @return 最终的状态栏颜色
     */
    private static int blendStatusColor(@ColorInt int color, @IntRange(from = 0, to = 255) int alpha) {
        if (alpha == 0) {
            return color;
        }
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }
}
