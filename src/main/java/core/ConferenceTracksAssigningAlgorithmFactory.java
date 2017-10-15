package core;

public class ConferenceTracksAssigningAlgorithmFactory {
    public ConferenceTracksAssigningAlgorithm getDefaultAlgorithm() {
        return new ConferenceTracksAssigningAlgorithm(new OptimalTracksAmountCalculatingAlgorithm(),
                                                      new EqualDataSegregationAlgorithm(),
                                                      new EqualTalksToTrackSessionsSeparatingAlgorithm());
    }
}
