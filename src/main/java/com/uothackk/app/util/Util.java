package com.uothackk.app.util;

import java.util.Date;

public class Util {

    public static String timeAgo(Date placedAt) {
        long today = new Date().getTime();
        long diff = today - placedAt.getTime();

        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        if (diffDays > 0) {
            return  "" + diffDays + " days ago";
        } else if (diffDays == 0 && diffHours > 0) {
            return  "" + diffHours + " hours ago";
        } else {
            return  "" + diffMinutes + " minutes ago";
        }

    }

}
