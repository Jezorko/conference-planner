package core;

import dto.TalkData;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static util.SpecificationConstants.MIN_TRACK_TIME;
import static util.TalkUtil.calculateTotalTimeOf;

public class SafeTracksAmountCalculatingAlgorithm implements TracksAmountCalculatingAlgorithm {

    @Override
    public int calculateTracksAmountFor(List<TalkData> talks) {
        int totalTime = calculateTotalTimeOf(talks);
        return new BigDecimal(totalTime).divide(MIN_TRACK_TIME, ROUND_HALF_UP)
                                        .intValueExact();
    }
}
