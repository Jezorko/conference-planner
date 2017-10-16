package core;

public class ConferenceTracksAssigningAlgorithmFactory {
    public ConferenceTracksAssigningAlgorithm getOptimalAlgorithm() {
        return new ConferenceTracksAssigningAlgorithm(new OptimalTracksAmountCalculatingAlgorithm(),
                                                      new EqualDataSegregationAlgorithm(),
                                                      new EqualTalksToTrackSessionsSeparatingAlgorithm());
    }

    public ConferenceTracksAssigningAlgorithm getSafeAlgorithm() {
        return new ConferenceTracksAssigningAlgorithm(new SafeTracksAmountCalculatingAlgorithm(),
                                                      new EqualDataSegregationAlgorithm(),
                                                      new EqualTalksToTrackSessionsSeparatingAlgorithm());
    }
}
