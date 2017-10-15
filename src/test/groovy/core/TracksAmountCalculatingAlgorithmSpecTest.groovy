package core

import spock.lang.Specification

import static data.SpecificationDatasets.SPECIFICATION_TALKS

class TracksAmountCalculatingAlgorithmSpecTest extends Specification {
    def "should calculate optimal tracks amount"() {
        when:
        def result = algorithm.calculateTracksAmountFor SPECIFICATION_TALKS

        then:
        result >= 2
        result <= 3

        where:
        algorithm << [
                new OptimalTracksAmountCalculatingAlgorithm(),
                new SafeTracksAmountCalculatingAlgorithm()
        ]
    }
}
