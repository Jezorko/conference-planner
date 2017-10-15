package core;

import dto.TalkData;

import java.util.List;

public interface TracksAmountCalculatingAlgorithm {
    int calculateTracksAmountFor(List<TalkData> talks);
}
