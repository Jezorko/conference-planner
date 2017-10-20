package core;

import dto.Talk;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ROUND_CEILING;
import static util.SpecificationConstants.MIN_TRACK_TIME;
import static util.TalkUtil.calculateTotalTimeOf;

class SafeTracksAmountCalculatingAlgorithm implements TracksAmountCalculatingAlgorithm {

    @Override
    public int calculateTracksAmountFor(List<Talk> talks) {
        int totalTime = calculateTotalTimeOf(talks);
        return new BigDecimal(totalTime).divide(MIN_TRACK_TIME, ROUND_CEILING)
                                        .intValueExact();
    }
}
