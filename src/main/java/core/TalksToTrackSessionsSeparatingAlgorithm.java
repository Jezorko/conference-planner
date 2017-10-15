package core;

import dto.TalkData;
import dto.TrackData;

import java.util.List;

public interface TalksToTrackSessionsSeparatingAlgorithm {
    TrackData separateFrom(List<TalkData> talks);
}
