package core;

import dto.Talk;
import dto.Track;

import java.util.List;

interface TalksToTrackSessionsSeparatingAlgorithm {
    Track separateFrom(List<Talk> talks);
}
