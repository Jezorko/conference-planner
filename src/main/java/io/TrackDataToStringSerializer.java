package io;

import dto.Talk;
import dto.Track;
import io.vavr.Tuple2;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static io.vavr.control.Option.of;
import static util.SpecificationConstants.*;

@NoArgsConstructor
class TrackDataToStringSerializer {

    private final static String LINE_SEPARATOR = System.getProperty("line.separator");

    private LocalTime currentTimestamp;

    String serialize(Tuple2<Track, Integer> trackDataWithIndex) {
        return serialize(trackDataWithIndex._1, trackDataWithIndex._2);
    }

    /**
     * This method modifies one of the classes' fields,
     * therefore it is not thread-safe. Use with caution.
     */
    // TODO: refactoring to make this method thread-safe
    String serialize(Track track, int index) {
        StringBuilder result = new StringBuilder("Track " + index + LINE_SEPARATOR);

        currentTimestamp = MORNING_SESSION_TIMESTAMP;
        result.append(serializeSessionData(track.getMorningSessionTalks()));

        result.append(serializeLunchData());

        currentTimestamp = AFTERNOON_SESSION_TIMESTAMP;
        result.append(serializeSessionData(track.getAfternoonSessionTalks()));

        result.append(serializeNetworkingEventData());

        return result.toString();
    }

    private StringBuilder serializeLunchData() {
        return new StringBuilder(formatTimestamp(LUNCH_TIMESTAMP)).append("Lunch")
                                                                  .append(LINE_SEPARATOR);
    }

    private StringBuilder serializeNetworkingEventData() {
        return of(currentTimestamp).filter(NETWORKING_EVENT_TIMESTAMP::isBefore)
                                   .orElse(of(NETWORKING_EVENT_TIMESTAMP))
                                   .map(TrackDataToStringSerializer::formatTimestamp)
                                   .map(StringBuilder::new)
                                   .getOrElseThrow(RuntimeException::new)
                                   .append("Networking Event");
    }

    private StringBuilder serializeSessionData(List<Talk> sessionData) {
        StringBuilder result = new StringBuilder();
        for (Talk talk : sessionData) {
            result.append(getFormattedCurrentTimestamp())
                  .append(talk.getName())
                  .append(' ')
                  .append(formatTalkTime(talk))
                  .append(LINE_SEPARATOR);

            currentTimestamp = currentTimestamp.plusMinutes(talk.getLengthInMinutes());
        }
        return result;
    }

    private String getFormattedCurrentTimestamp() {
        return formatTimestamp(currentTimestamp);
    }

    private static String formatTimestamp(LocalTime timestamp) {
        return timestamp.format(DateTimeFormatter.ofPattern(TIMESTAMP_FORMAT));
    }

    private static String formatTalkTime(Talk talk) {
        final int lengthInMinutes = talk.getLengthInMinutes();
        return lengthInMinutes == 5 ? "lightning" : lengthInMinutes + "min";
    }
}
