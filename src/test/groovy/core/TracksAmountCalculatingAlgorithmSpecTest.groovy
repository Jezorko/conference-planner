package core

import spock.lang.Specification
import spock.lang.Unroll

import static data.SpecificationDatasets.SPECIFICATION_TALKS

class TracksAmountCalculatingAlgorithmSpecTest extends Specification {

    static ALGORITHMS_TO_TEST = [
            new OptimalTracksAmountCalculatingAlgorithm(),
            new SafeTracksAmountCalculatingAlgorithm()
    ]

    @Unroll
    "#algorithm.getClass().getSimpleName() should calculate optimal tracks amount"() {
        when:
        def result = algorithm.calculateTracksAmountFor SPECIFICATION_TALKS

        then:
        result >= 2
        result <= 3

        where:
        algorithm << ALGORITHMS_TO_TEST
    }
}
