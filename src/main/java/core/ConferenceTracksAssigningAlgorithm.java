package core;

import dto.TalkData;
import dto.TrackData;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static util.TalkUtil.TIME_DESCENDING_COMPARATOR;

@RequiredArgsConstructor
public class ConferenceTracksAssigningAlgorithm {

    private final TracksAmountCalculatingAlgorithm tracksAmountCalculatingAlgorithm;
    private final DataSegregationAlgorithm dataSegregationAlgorithm;
    private final TalksToTrackSessionsSeparatingAlgorithm talksToTrackSessionsSeparatingAlgorithm;

    List<TrackData> assignToTracks(List<TalkData> talks) {
        if (talks == null) {
            throw new IllegalArgumentException("The list of talks cannot be null");
        }

        talks.sort(TIME_DESCENDING_COMPARATOR);

        int optimalTracksAmount = tracksAmountCalculatingAlgorithm.calculateTracksAmountFor(talks);

        List<List<TalkData>> tracksTalks = dataSegregationAlgorithm.segregateBetween(optimalTracksAmount, talks);

        return tracksTalks.stream()
                          .map(talksToTrackSessionsSeparatingAlgorithm::separateFrom)
                          .collect(toList());
    }
}
