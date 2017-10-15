package util;

import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalTime;

import static java.math.BigDecimal.valueOf;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class SpecificationConstants {
    public static final int MORNING_SESSION_MAX_TIME = 3 * 60;
    public static final int AFTERNOON_SESSION_MAX_TIME = 4 * 60;
    public static final BigDecimal MIN_TRACK_TIME = valueOf(6 * 60);
    public static final BigDecimal MAX_TRACK_TIME = valueOf(7 * 60);

    public static final String TALK_DATA_TITLE_PATTERN = "\\D+";
    public static final String QUICK_TALK_TIME_PATTERN = "lightning";
    public static final String TALK_DATA_TIME_PATTERN = " ((\\d+min)|(" + QUICK_TALK_TIME_PATTERN + "))";
    public static final String TALK_DATA_PATTERN = "^" + TALK_DATA_TITLE_PATTERN + TALK_DATA_TIME_PATTERN + "$";

    public final static String TIMESTAMP_FORMAT = "hh:mma ";
    public final static LocalTime LUNCH_TIMESTAMP = LocalTime.of(12, 0);
    public final static LocalTime MORNING_SESSION_TIMESTAMP = LocalTime.of(9, 0);
    public final static LocalTime AFTERNOON_SESSION_TIMESTAMP = LocalTime.of(13, 0);
}
