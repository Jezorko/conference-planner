package core;

import dto.Talk;
import dto.Track;

import java.util.ArrayList;
import java.util.List;

import static util.SpecificationConstants.AFTERNOON_SESSION_MAX_TIME;
import static util.SpecificationConstants.MORNING_SESSION_MAX_TIME;
import static util.TalkUtil.calculateTotalTimeOf;

class EqualTalksToTrackSessionsSeparatingAlgorithm implements TalksToTrackSessionsSeparatingAlgorithm {
    @Override
    public Track separateFrom(List<Talk> talks) {
        List<Talk> morningTalks = new ArrayList<>();
        List<Talk> afternoonTalks = new ArrayList<>();

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

        return new Track(morningTalks, afternoonTalks);
    }
}
