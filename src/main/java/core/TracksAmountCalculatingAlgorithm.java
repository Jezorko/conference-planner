package core;

import dto.TalkData;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.valueOf;

public interface TracksAmountCalculatingAlgorithm {

    BigDecimal MIN_TRACK_TIME = valueOf(6 * 60);
    BigDecimal MAX_TRACK_TIME = valueOf(7 * 60);

    int calculateTracksAmountFor(List<TalkData> talks);
}
