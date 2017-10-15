package core;

import dto.TalkData;
import dto.TrackData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static dto.TalkData.TIME_DESCENDING_COMPARATOR;
import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.math.BigDecimal.valueOf;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

public class ConferenceTracksAssigningAlgorithm {

    private static final BigDecimal MIN_TRACK_TIME = valueOf(6 * 60);
    private static final BigDecimal MAX_TRACK_TIME = valueOf(7 * 60);

    List<TrackData> assignToTracks(List<TalkData> talks) {
        if (talks == null) {
            throw new IllegalArgumentException("The list of talks cannot be null");
        }

        talks.sort(TIME_DESCENDING_COMPARATOR);

        int optimalTracksAmount = calculateOptimalTracksAmountFor(talks);

        List<List<TalkData>> tracksTalks = new ArrayList<>();
        range(0, optimalTracksAmount).forEach(i -> tracksTalks.add(new ArrayList<>()));

        int j = 0;
        for (TalkData talk1 : talks) {
            tracksTalks.get(j)
                       .add(talk1);
            ++j;
            if (j == optimalTracksAmount) {
                j = 0;
            }
        }

        return tracksTalks.stream()
                          .map(talksForTrack -> {
                              int morningTalksMaxTime = 3 * 60;
                              int afternoonTalksMaxTime = 4 * 60;
                              List<TalkData> morningTalks = new ArrayList<>();
                              List<TalkData> afternoonTalks = new ArrayList<>();

                              talksForTrack.forEach(talk -> {
                                  if (calculateTotalTimeOf(morningTalks) + talk.getLengthInMinutes() <= morningTalksMaxTime) {
                                      morningTalks.add(talk);
                                  }
                                  else if (calculateTotalTimeOf(afternoonTalks) + talk.getLengthInMinutes() <= afternoonTalksMaxTime) {
                                      afternoonTalks.add(talk);
                                  }
                                  else {
                                      throw new AlgorithmFailureException("Proportional separation failure");
                                  }
                              });

                              return new TrackData(morningTalks, afternoonTalks);
                          })
                          .collect(toList());
    }

    private int calculateOptimalTracksAmountFor(List<TalkData> talks) {
        int totalTime = calculateTotalTimeOf(talks);
        return new BigDecimal(totalTime).divide(MIN_TRACK_TIME, ROUND_HALF_UP)
                                        .add(new BigDecimal(totalTime).divide(MAX_TRACK_TIME, ROUND_HALF_UP))
                                        .divide(new BigDecimal(2), ROUND_HALF_UP)
                                        .intValueExact();
    }

    private int calculateTotalTimeOf(List<TalkData> talks) {
        return talks.stream()
                    .map(TalkData::getLengthInMinutes)
                    .reduce(Integer::sum)
                    .orElse(0);
    }

    private static class AlgorithmFailureException extends RuntimeException {
        AlgorithmFailureException(String description) {
            super(description);
        }
    }
}
