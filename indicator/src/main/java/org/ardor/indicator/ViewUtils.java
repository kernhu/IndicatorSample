package org.ardor.indicator;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * @author: Kern Hu
 * @emali:
 * create at: 2019/6/26 14:51.
 * modify at: 2019/6/26 14:51.
 * develop version name :
 * modify version name :
 * description: This's ...
 */
public class ViewUtils {


    /**
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {

        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();

        return dm.widthPixels;
    }

}
