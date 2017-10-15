package core;

import dto.TalkData;
import dto.TrackData;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static util.SpecificationConstants.AFTERNOON_SESSION_MAX_TIME;
import static util.SpecificationConstants.MORNING_SESSION_MAX_TIME;
import static util.TalkUtil.TIME_DESCENDING_COMPARATOR;
import static util.TalkUtil.calculateTotalTimeOf;

@RequiredArgsConstructor
public class ConferenceTracksAssigningAlgorithm {

    private final TracksAmountCalculatingAlgorithm tracksAmountCalculatingAlgorithm;

    List<TrackData> assignToTracks(List<TalkData> talks) {
        if (talks == null) {
            throw new IllegalArgumentException("The list of talks cannot be null");
        }

        talks.sort(TIME_DESCENDING_COMPARATOR);

        int optimalTracksAmount = tracksAmountCalculatingAlgorithm.calculateTracksAmountFor(talks);

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
                              List<TalkData> morningTalks = new ArrayList<>();
                              List<TalkData> afternoonTalks = new ArrayList<>();

                              talksForTrack.forEach(talk -> {
                                  if (calculateTotalTimeOf(morningTalks) + talk.getLengthInMinutes() <= MORNING_SESSION_MAX_TIME) {
                                      morningTalks.add(talk);
                                  }
                                  else if (calculateTotalTimeOf(afternoonTalks) + talk.getLengthInMinutes() <= AFTERNOON_SESSION_MAX_TIME) {
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

    private static class AlgorithmFailureException extends RuntimeException {
        AlgorithmFailureException(String description) {
            super(description);
        }
    }
}
