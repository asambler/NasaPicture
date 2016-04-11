package gov.nasa.client.utils;

import android.database.Cursor;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Closeable;

public class Util {
    public static final DateTimeFormatter DAY_FORMATTER = DateTimeFormat.forPattern("YYYY-MM-DD");

    public static void safelyClose(Closeable c) {
        if (c != null) {
            if (c instanceof Cursor) {
                try {
                    ((Cursor) c).close();
                } catch (Exception ignored) {

                }
            } else {
                try {
                    c.close();
                } catch (Exception ignored) {
                }
            }
        }
    }
}
