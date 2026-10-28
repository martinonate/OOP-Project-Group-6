package ec.edu.espe.cosmoclick.controller;

import java.util.Calendar;

/**
 * 
 * @author Martin Onate <Team 6, espe>
 */
public class DateUtil {

    private DateUtil() {
    }

    public static String format(Calendar date) {
        if (date == null) {
            return "";
        }
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        return year + "-" + twoDigits(month) + "-" + twoDigits(day);
    }

    public static Calendar parse(String text) {
        if (text == null) {
            return null;
        }
        String[] parts = text.trim().split("-");
        if (parts.length != 3) {
            return null;
        }
        try {
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);
            Calendar date = Calendar.getInstance();
            date.clear();
            date.setLenient(false);
            date.set(year, month - 1, day);
            date.getTime();
            return date;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private static String twoDigits(int number) {
        if (number < 10) {
            return "0" + number;
        }
        return String.valueOf(number);
    }
}
