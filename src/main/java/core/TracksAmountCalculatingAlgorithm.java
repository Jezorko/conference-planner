package core;

import dto.Talk;

import java.util.List;

interface TracksAmountCalculatingAlgorithm {
    int calculateTracksAmountFor(List<Talk> talks);
}
