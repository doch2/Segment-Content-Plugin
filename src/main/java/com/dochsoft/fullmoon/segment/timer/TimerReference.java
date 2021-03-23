package com.dochsoft.fullmoon.segment.timer;

public class TimerReference {
    public static int timerTime = 0;
    public static boolean showTimer;

    public static String getTimerTimeHour() {
        String int2String = Integer.toString(timerTime / 3600);
        if (int2String.length() == 1) {
            return "0" + int2String;
        } else {
            return int2String;
        }
    }

    public static String getTimerTimeMinute() {
        Integer temp = timerTime - (Integer.parseInt(getTimerTimeHour()) * 3600);

        if (Integer.toString((temp / 60)).length() == 1) {
            return "0" + Integer.toString((temp / 60));
        } else {
            return Integer.toString((temp / 60));
        }
    }

    public static String getTimerTimeSecond() {
        Integer temp = timerTime - (Integer.parseInt(getTimerTimeHour()) * 3600) - (Integer.parseInt(getTimerTimeMinute()) * 60);

        if (Integer.toString(temp).length() == 1) {
            return "0" + Integer.toString(temp);
        } else {
            return Integer.toString(temp);
        }
    }
}
