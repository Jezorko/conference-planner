package core;

import dto.TalkData;

import java.util.List;

interface TracksAmountCalculatingAlgorithm {
    int calculateTracksAmountFor(List<TalkData> talks);
}
