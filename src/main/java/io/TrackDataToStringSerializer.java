package io;

import core.TalkData;
import core.TrackData;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@NoArgsConstructor
public class TrackDataToStringSerializer {

    private final static String LINE_SEPARATOR = System.getProperty("line.separator");
    private final static String TIMESTAMP_FORMAT = "hh:mma ";
    private final static LocalTime LUNCH_TIMESTAMP = LocalTime.of(12, 0);
    private final static LocalTime MORNING_SESSION_TIMESTAMP = LocalTime.of(9, 0);
    private final static LocalTime AFTERNOON_SESSION_TIMESTAMP = LocalTime.of(13, 0);

    public String serialize(TrackData trackData, int index) {
        StringBuilder result = new StringBuilder("Track " + index + LINE_SEPARATOR);

        appendSessionData(trackData.getMorningSessionTalks(), result, MORNING_SESSION_TIMESTAMP);

        result.append(formatTimestamp(LUNCH_TIMESTAMP))
              .append("Lunch")
              .append(LINE_SEPARATOR);

        LocalTime currentTimestamp = appendSessionData(trackData.getAfternoonSessionTalks(), result, AFTERNOON_SESSION_TIMESTAMP);

        result.append(formatTimestamp(currentTimestamp))
              .append("Networking Event");

        return result.toString();
    }

    private LocalTime appendSessionData(List<TalkData> sessionData, StringBuilder result, LocalTime currentTimestamp) {
        for (TalkData talkData : sessionData) {
            result.append(formatTimestamp(currentTimestamp))
                  .append(talkData.getName())
                  .append(' ')
                  .append(formatTalkTime(talkData))
                  .append(LINE_SEPARATOR);

            currentTimestamp = currentTimestamp.plusMinutes(talkData.getLengthInMinutes());
        }

        return currentTimestamp;
    }

    private static String formatTimestamp(LocalTime timestamp) {
        return timestamp.format(DateTimeFormatter.ofPattern(TIMESTAMP_FORMAT));
    }

    private static String formatTalkTime(TalkData talkData) {
        final int lengthInMinutes = talkData.getLengthInMinutes();
        return lengthInMinutes == 5 ? "lightning" : lengthInMinutes + "min";
    }
}
