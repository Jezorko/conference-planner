package core

import dto.TalkData
import spock.lang.Specification

import static data.SpecificationDatasets.SPECIFICATION_TALKS
import static java.lang.Integer.MAX_VALUE

class ConferenceTracksAssigningAlgorithmSpecTest extends Specification {

    def tracksAmountCalculatingAlgorithm = Mock TracksAmountCalculatingAlgorithm
    def dataSegregationAlgorithm = new EqualDataSegregationAlgorithm()
    def talksToTrackSessionsSeparatingAlgorithm = new EqualTalksToTrackSessionsSeparatingAlgorithm()
    def algorithm = new ConferenceTracksAssigningAlgorithm(tracksAmountCalculatingAlgorithm,
                                                           dataSegregationAlgorithm,
                                                           talksToTrackSessionsSeparatingAlgorithm)

    def "should generate optimal solution"() {
        given:
        def morningSessionMaximumTimeInMinutes = 3 * 60
        def afternoonSessionMaximumTimeInMinutes = 4 * 60

        when:
        def tracks = algorithm.assignToTracks SPECIFICATION_TALKS

        then: "tracks amount is calculated"
        1 * tracksAmountCalculatingAlgorithm.calculateTracksAmountFor(SPECIFICATION_TALKS) >> 2

        then: "there are only two tracks"
        tracks.size() == 2 as int

        and: "each track has valid session times"
        tracks.forEach {
            assert SPECIFICATION_TALKS.containsAll(it.morningSessionTalks)
            assert it.morningSessionTalks.stream().map({ it.lengthInMinutes }).reduce(Integer.&sum).orElse(MAX_VALUE) <= morningSessionMaximumTimeInMinutes

            assert SPECIFICATION_TALKS.containsAll(it.afternoonSessionTalks)
            assert it.afternoonSessionTalks.stream().map({ it.lengthInMinutes }).reduce(Integer.&sum).orElse(MAX_VALUE) <= afternoonSessionMaximumTimeInMinutes
        }

        and: "there are no talks lost in the process"
        def resultingTalks = []
        tracks.forEach {
            resultingTalks.addAll(it.morningSessionTalks)
            resultingTalks.addAll(it.afternoonSessionTalks)
        }
        resultingTalks.containsAll(SPECIFICATION_TALKS)
    }

    def "should throw if the input is null"() {
        given:
        List<TalkData> talks = null

        when:
        algorithm.assignToTracks talks

        then:
        thrown IllegalArgumentException
    }
}
