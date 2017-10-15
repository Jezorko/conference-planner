package core;

import dto.TalkData;
import dto.TrackData;

import java.util.ArrayList;
import java.util.List;

import static util.SpecificationConstants.AFTERNOON_SESSION_MAX_TIME;
import static util.SpecificationConstants.MORNING_SESSION_MAX_TIME;
import static util.TalkUtil.calculateTotalTimeOf;

public class EqualTalksToTrackSessionsSeparatingAlgorithm implements TalksToTrackSessionsSeparatingAlgorithm {
    @Override
    public TrackData separateFrom(List<TalkData> talks) {
        List<TalkData> morningTalks = new ArrayList<>();
        List<TalkData> afternoonTalks = new ArrayList<>();

        talks.forEach(talk -> {
            if (calculateTotalTimeOf(morningTalks) + talk.getLengthInMinutes() <= MORNING_SESSION_MAX_TIME) {
                morningTalks.add(talk);
            }
            else if (calculateTotalTimeOf(afternoonTalks) + talk.getLengthInMinutes() <= AFTERNOON_SESSION_MAX_TIME) {
                afternoonTalks.add(talk);
            }
            else {
                throw new AlgorithmFailureException("Equal separation failure, talks list is not drained but sessions are already full");
            }
        });

        return new TrackData(morningTalks, afternoonTalks);
    }
}
