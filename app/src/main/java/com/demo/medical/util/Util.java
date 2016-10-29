package com.demo.medical.util;

import android.text.TextUtils;

/**
 * Created by Moosa moosa.bh@gmail.com on 10/30/2016 30 October.
 * Everything is possible in programming.
 */

public class Util {


    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static boolean isValidTextField(CharSequence pattern) {
        return !pattern.equals("") && !pattern.equals(null) && pattern.length() > 2;//&&(pattern.toString().matches(android.util.Patterns.));
    }

    public static boolean isValidPassword(CharSequence pattern1, CharSequence pattern2) {
        return (!pattern1.equals("") && !pattern1.equals(null) && pattern1.length() > 4) &&
                (!pattern2.equals("") && !pattern2.equals(null) && pattern2.length() > 4) &&
                (pattern1.equals(pattern2));
    }
}
