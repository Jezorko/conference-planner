package io;

import core.TalkData;
import core.TrackData;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TrackDataToStringSerializer {
    public String serialize(TrackData trackData, int index) {
        StringBuilder result = new StringBuilder("Track " + index + "\n");
        LocalTime currentTime = getMorningSessionStartTime();
        for (TalkData talkData : trackData.getMorningSessionTalks()) {
            result.append(currentTime.format(DateTimeFormatter.ofPattern("hh:mma ")))
                  .append(talkData.getName())
                  .append(' ')
                  .append(formatTalkTime(talkData))
                  .append('\n');

            currentTime = currentTime.plusMinutes(talkData.getLengthInMinutes());
        }

        result.append("12:00PM Lunch\n");

        currentTime = getAfternoonSessionStartTime();
        for (TalkData talkData : trackData.getAfternoonSessionTalks()) {
            result.append(currentTime.format(DateTimeFormatter.ofPattern("hh:mma ")))
                  .append(talkData.getName())
                  .append(' ')
                  .append(formatTalkTime(talkData))
                  .append('\n');

            currentTime = currentTime.plusMinutes(talkData.getLengthInMinutes());
        }

        result.append(currentTime.format(DateTimeFormatter.ofPattern("hh:mma ")))
              .append("Networking Event");

        return result.toString();
    }

    private LocalTime getMorningSessionStartTime() {
        return LocalTime.of(9, 0);
    }

    private LocalTime getAfternoonSessionStartTime() {
        return LocalTime.of(13, 0);
    }

    private String formatTalkTime(TalkData talkData) {
        final int lengthInMinutes = talkData.getLengthInMinutes();
        return lengthInMinutes == 5 ? "lightning" : lengthInMinutes + "min";
    }
}
