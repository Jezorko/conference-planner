package core;

import dto.TalkData;
import dto.TrackData;

import java.util.List;

interface TalksToTrackSessionsSeparatingAlgorithm {
    TrackData separateFrom(List<TalkData> talks);
}
