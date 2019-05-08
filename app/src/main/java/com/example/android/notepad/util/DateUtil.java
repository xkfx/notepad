package com.example.android.notepad.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.CHINA);

    public static String timeMillis2Date(Long timeMillis) {
        return formatter.format(new Date(timeMillis));
    }
}
