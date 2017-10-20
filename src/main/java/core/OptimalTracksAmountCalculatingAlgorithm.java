package core;

import dto.Talk;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static util.SpecificationConstants.MAX_TRACK_TIME;
import static util.SpecificationConstants.MIN_TRACK_TIME;
import static util.TalkUtil.calculateTotalTimeOf;

class OptimalTracksAmountCalculatingAlgorithm implements TracksAmountCalculatingAlgorithm {

    @Override
    public int calculateTracksAmountFor(List<Talk> talks) {
        int totalTime = calculateTotalTimeOf(talks);
        return new BigDecimal(totalTime).divide(MIN_TRACK_TIME, ROUND_HALF_UP)
                                        .add(new BigDecimal(totalTime).divide(MAX_TRACK_TIME, ROUND_HALF_UP))
                                        .divide(new BigDecimal(2), ROUND_HALF_UP)
                                        .intValueExact();
    }
}
