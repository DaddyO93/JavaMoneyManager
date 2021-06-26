package com.moneyManager;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.text.NumberFormat.getCurrencyInstance;

public class DataScrubber {

    public static String stringCannotBeNull(String passedString) {
        System.out.println(passedString + " - checking stringCannotBeNull");
        if (passedString == null)
            return "";
        return passedString;
    }

    public static Integer integerCannotBeNull(Integer passedInteger) {
        System.out.println(passedInteger + " - checking integerCannotBeNull");
        if (passedInteger==null)
            return 0;
        return passedInteger;
    }

    public static String stringCannotBeEmpty(String passedString) {
        System.out.println(passedString + " - checking stringCannotBeEmpty");
        if (passedString != null)
            if (passedString.isBlank())
                return null;
        return passedString;
    }

    public static Boolean mustBeBoolean(Object passedBoolean) {
        System.out.println(passedBoolean + " - checking mustBeBoolean");
        if (passedBoolean.equals(true) || passedBoolean.equals(false))
            return (Boolean) passedBoolean;
        return false;
    }

    //  Returns $0 if an incorrect currency is given
    public static String convertToCurrency(String amountString) throws NumberFormatException {
        System.out.println(amountString + " - converting to currency");
        String currencyString = "$0";

        Pattern pattern = Pattern.compile("[0-9]+([,.][0-9]?)?$+");
        Matcher finder = pattern.matcher(amountString);
        if (finder.find()) {
            double value = Double.parseDouble(finder.group(0).replaceAll("[^\\d]", ""));
            NumberFormat format = getCurrencyInstance(Locale.US);
            currencyString = format.format(value);
        }
        return currencyString;
    }

    public static class DateUtil {

        // The date pattern that is used for conversion.
        private static final String DATE_PATTERN = "dd/MM/yyyy";

        // The date formatter.
        private static final DateTimeFormatter DATE_FORMATTER =
                DateTimeFormatter.ofPattern(DATE_PATTERN);

        /**
         *  Converts LocalDate to String in the format of the defined
         */
        public static String formatLocalDateToString(LocalDate date) {
            System.out.println(date + " - formating date to local string");
            if (date == null) {
                return null;
            }
            return DATE_FORMATTER.format(date);
        }

        /**
         * Converts a String in the format of the defined
         */
        public static LocalDate formatStringToLocalDate(String dateString) {
            try {
                return DATE_FORMATTER.parse(dateString, LocalDate::from);
            } catch (DateTimeParseException e) {
                return null;
            }
        }

        /**
         * Checks the String whether it is a valid date.
         * @return true if the String is a valid date
         */
        public static boolean checkForValidDate(String dateString) {
            // Try to parse the String.
            return DateUtil.formatStringToLocalDate(dateString) != null;
        }
    }
}
