package com.group2.whatsup.Debug;

import java.text.MessageFormat;

public class Log {

    public static final String TAG = "WUDB";

    private enum LogType{
        Error,
        Warn,
        Debug,
        Info
    }

    public static void Debug(String format, Object... args){
        WriteMessage(LogType.Debug, format, args);
    }

    public static void Error(String format, Object... args){
        WriteMessage(LogType.Error, format, args);
    }

    public static void Info(String format, Object... args){
        WriteMessage(LogType.Info, format, args);
    }

    public static void Warn(String format, Object... args){
        WriteMessage(LogType.Warn, format, args);
    }

    private static void WriteMessage(LogType type, String format, Object... args){
        String msg = MessageFormat.format(format, args);
        StackTraceElement[] traces = Thread.currentThread().getStackTrace();
        String className = "?";
        String methodName = "?";
        int lineNumber = 0;

        if(traces.length >= 5){
            className = traces[4].getFileName();
            methodName = traces[4].getMethodName();
            lineNumber = traces[4].getLineNumber();
        }

        WriteLog(type, "[{0}][{1}][{2}] {3}", className, methodName, lineNumber, msg);
    }


    private static void WriteLog(LogType type, String format, Object... args){
        String t = TAG;

        switch(type){
            case Error:
                android.util.Log.e(t, MessageFormat.format(format, args));
                break;
            case Warn:
                android.util.Log.w(t, MessageFormat.format(format, args));
                break;
            case Debug:
                android.util.Log.d(t, MessageFormat.format(format, args));
                break;
            case Info:
                android.util.Log.i(t, MessageFormat.format(format, args));
                break;
        }
    }
}