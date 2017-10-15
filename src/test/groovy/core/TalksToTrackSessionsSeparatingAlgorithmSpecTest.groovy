package core

import spock.lang.Specification
import spock.lang.Unroll

import static data.SpecificationDatasets.SPECIFICATION_FIRST_TRACK_TALKS
import static org.apache.commons.collections.ListUtils.union

class TalksToTrackSessionsSeparatingAlgorithmSpecTest extends Specification {
    static ALGORITHMS_TO_TEST = [
            new EqualTalksToTrackSessionsSeparatingAlgorithm()
    ]

    @Unroll
    "#algorithm.getClass().getSimpleName() should give result which contains the same talks as input"() {
        when:
        def result = algorithm.separateFrom SPECIFICATION_FIRST_TRACK_TALKS

        then:
        union(result.getAfternoonSessionTalks(), result.getMorningSessionTalks())
                .containsAll(SPECIFICATION_FIRST_TRACK_TALKS)

        where:
        algorithm << ALGORITHMS_TO_TEST
    }

    @Unroll
    "#algorithm.getClass().getSimpleName() should throw if input is null"() {
        when:
        algorithm.separateFrom(null as List)

        then:
        thrown Exception

        where:
        algorithm << ALGORITHMS_TO_TEST
    }
}
