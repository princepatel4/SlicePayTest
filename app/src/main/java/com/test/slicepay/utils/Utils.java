package com.test.slicepay.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by pardypanda05 on 2/2/18.
 */

public class Utils {

    public static int dpToPx(int dp, Activity activity) {
        Resources r = activity.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
