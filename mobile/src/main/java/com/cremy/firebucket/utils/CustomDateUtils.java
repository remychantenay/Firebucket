package com.cremy.firebucket.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.cremy.firebucket.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by remychantenay on 26/05/2016.
 */
public class CustomDateUtils {

    /**
     * Allows to get the date (string format) of now
     * @return
     */
    public static String getNow() {
        return Calendar.getInstance().getTime().toString();
    }

    /**
     * Allows to know if a given calendar instance is set before today
     * @param calendar
     * @return
     */
    public static boolean isBeforeToday(Calendar calendar) {
        return calendar.before(Calendar.getInstance());
    }

    /**
     * Allows to know if a given calendar instance is set today
     * @param calendar
     * @return
     */
    public static boolean isToday(Calendar calendar) {
        Calendar now = Calendar.getInstance();
        if (now.get(Calendar.DAY_OF_MONTH)==calendar.get(Calendar.DAY_OF_MONTH)) {
            if (now.get(Calendar.MONTH)==calendar.get(Calendar.MONTH)) {
                if (now.get(Calendar.YEAR)==calendar.get(Calendar.YEAR)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Allows to know if a given calendar instance is set Today+1 (Tomorrow)
     * @param calendar
     * @return
     */
    @SuppressWarnings("WrongConstant")
    public static boolean isTomorrow(Calendar calendar) {
        Calendar now = Calendar.getInstance();
        if ((now.get(Calendar.DAY_OF_MONTH)+1)==calendar.get(Calendar.DAY_OF_MONTH)) {
            if (now.get(Calendar.MONTH)==calendar.get(Calendar.MONTH)) {
                if (now.get(Calendar.YEAR)==calendar.get(Calendar.YEAR)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Allows to know if a given calendar instance is set Today-1 (Yesterday)
     * @param calendar
     * @return
     */
    @SuppressWarnings("WrongConstant")
    public static boolean isYesterday(Calendar calendar) {
        Calendar now = Calendar.getInstance();
        if ((now.get(Calendar.DAY_OF_MONTH)-1)==calendar.get(Calendar.DAY_OF_MONTH)) {
            if (now.get(Calendar.MONTH)==calendar.get(Calendar.MONTH)) {
                if (now.get(Calendar.YEAR)==calendar.get(Calendar.YEAR)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Allows to get the display date from a given {@link Calendar} Instance
     *
     * 1. "Today" if same day
     * 2. "Tomorrow" if tomorrow
     * 3. "Wednesday 3" otherwise
     *
     * @param context
     * @param calendar
     * @return
     */
    public static String getDisplayDate(@NonNull Context context,
                                        @NonNull Calendar calendar) {
        if (CustomDateUtils.isToday(calendar)) {
            return context.getResources().getString(R.string.today);
        }
        else if (CustomDateUtils.isTomorrow(calendar)) {
            return context.getResources().getString(R.string.tomorrow);
        }
        else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d MMM");
            return dateFormat.format(calendar.getTime());
        }
    }

}
