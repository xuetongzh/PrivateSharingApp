package com.google.privatesharingapp.bean;

import android.app.Activity;

/**
 * Demo Information Class
 */

public class DemoInfo {
    public final int title;
    public final int desc;
    public final Class<? extends Activity> demoClass;

    public DemoInfo(int title, int desc,
                    Class<? extends Activity> demoClass) {
        this.title = title;
        this.desc = desc;
        this.demoClass = demoClass;
    }
}
