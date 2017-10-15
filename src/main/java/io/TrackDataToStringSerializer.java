package io;

import core.TalkData;
import core.TrackData;
import io.vavr.Tuple2;
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

    String serialize(Tuple2<TrackData, Integer> trackDataWithIndex) {
        return serialize(trackDataWithIndex._1, trackDataWithIndex._2);
    }

    /**
     * This method modifies one of the classes' fields,
     * therefore it is not thread-safe. Use with caution.
     */
    // TODO: refactoring to make this method thread-safe
    String serialize(TrackData trackData, int index) {
        StringBuilder result = new StringBuilder("Track " + index + LINE_SEPARATOR);

        currentTimestamp = MORNING_SESSION_TIMESTAMP;
        result.append(serializeSessionData(trackData.getMorningSessionTalks()));

        result.append(serializeLunchData());

        currentTimestamp = AFTERNOON_SESSION_TIMESTAMP;
        result.append(serializeSessionData(trackData.getAfternoonSessionTalks()));

        result.append(serializeNetworkingEventData());

        return result.toString();
    }

    private StringBuilder serializeLunchData() {
        return new StringBuilder(formatTimestamp(LUNCH_TIMESTAMP)).append("Lunch")
                                                                  .append(LINE_SEPARATOR);
    }

    private StringBuilder serializeNetworkingEventData() {
        return new StringBuilder(getFormattedCurrentTimestamp()).append("Networking Event");
    }

    private StringBuilder serializeSessionData(List<TalkData> sessionData) {
        StringBuilder result = new StringBuilder();
        for (TalkData talkData : sessionData) {
            result.append(getFormattedCurrentTimestamp())
                  .append(talkData.getName())
                  .append(' ')
                  .append(formatTalkTime(talkData))
                  .append(LINE_SEPARATOR);

            currentTimestamp = currentTimestamp.plusMinutes(talkData.getLengthInMinutes());
        }
        return result;
    }

    private String getFormattedCurrentTimestamp() {
        return formatTimestamp(currentTimestamp);
    }

    private static String formatTimestamp(LocalTime timestamp) {
        return timestamp.format(DateTimeFormatter.ofPattern(TIMESTAMP_FORMAT));
    }

    private static String formatTalkTime(TalkData talkData) {
        final int lengthInMinutes = talkData.getLengthInMinutes();
        return lengthInMinutes == 5 ? "lightning" : lengthInMinutes + "min";
    }
}
