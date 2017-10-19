package com.myreliablegames.troutscout;

import android.util.Log;

/**
 * Created by Joe on 10/15/2017.
 */

public class Logger {

    private static final String TAG = "Logger TAG";

    public static void v(String message) {
        if (BuildConfig.DEBUG) {
            Log.v(TAG, message);
        }
    }

    public static void d(String message) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, message);
        }
    }

    public static void w(String message) {
        if (BuildConfig.DEBUG) {
            Log.w(TAG, message);
        }
    }

    public static void i(String message) {
        if (BuildConfig.DEBUG) {
            Log.i(TAG, message);
        }
    }

    public static void e(String message) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, message);
        }
    }

    public static void v(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, message);
        }
    }

    public static void d(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message);
        }
    }

    public static void w(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, message);
        }
    }

    public static void i(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message);
        }
    }

    public static void v(String tag, String message, Throwable t) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, message, t);
        }
    }

    public static void d(String tag, String message, Throwable t) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message, t);
        }
    }

    public static void w(String tag, String message, Throwable t) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, message, t);
        }
    }

    public static void i(String tag, String message, Throwable t) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, message, t);
        }
    }

    public static void e(String tag, String message, Throwable t) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message, t);
        }
    }
}
