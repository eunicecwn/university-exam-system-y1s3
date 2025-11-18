package ExtraFunction;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException; // Import ParseException

public class DateTimeValidator {

    public static boolean isValidDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);  // strict parsing
        try {
            Date date = sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isValidTime(String timeStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setLenient(false);  // strict parsing
        try {
            Date time = sdf.parse(timeStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}