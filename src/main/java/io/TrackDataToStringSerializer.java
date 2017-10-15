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

    private LocalTime currentTimestamp;

    public String serialize(TrackData trackData, int index) {
        currentTimestamp = MORNING_SESSION_TIMESTAMP;
        StringBuilder result = new StringBuilder("Track " + index + LINE_SEPARATOR);
        appendSessionData(trackData.getMorningSessionTalks(), result);

        result.append(formatTimestamp(LUNCH_TIMESTAMP))
              .append("Lunch")
              .append(LINE_SEPARATOR);

        currentTimestamp = AFTERNOON_SESSION_TIMESTAMP;
        appendSessionData(trackData.getAfternoonSessionTalks(), result);

        result.append(getFormattedCurrentTimestamp())
              .append("Networking Event");

        return result.toString();
    }

    private void appendSessionData(List<TalkData> sessionData, StringBuilder result) {
        for (TalkData talkData : sessionData) {
            result.append(getFormattedCurrentTimestamp())
                  .append(talkData.getName())
                  .append(' ')
                  .append(formatTalkTime(talkData))
                  .append(LINE_SEPARATOR);

            currentTimestamp = currentTimestamp.plusMinutes(talkData.getLengthInMinutes());
        }
    }

    private String getFormattedCurrentTimestamp() {
        return formatTimestamp(currentTimestamp);
    }

    private String formatTimestamp(LocalTime timestamp) {
        return timestamp.format(DateTimeFormatter.ofPattern(TIMESTAMP_FORMAT));
    }

    private String formatTalkTime(TalkData talkData) {
        final int lengthInMinutes = talkData.getLengthInMinutes();
        return lengthInMinutes == 5 ? "lightning" : lengthInMinutes + "min";
    }
}
