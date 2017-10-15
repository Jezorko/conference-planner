package util

import dto.TalkData
import spock.lang.Specification

import static util.TalkUtil.calculateTotalTimeOf

class TalkUtilSpecTest extends Specification {

    def "should calculate the total time of talks"() {
        given:
        def talks = [
                new TalkData("example", 5),
                new TalkData("example", 10)
        ]

        when:
        def result = calculateTotalTimeOf talks

        then:
        15 == result
    }

    def "should return 0 if the list of talks is empty"() {
        given:
        List<TalkData> talks = []

        when:
        def result = calculateTotalTimeOf talks

        then:
        0 == result
    }

    def "should throw if the list contains a null object"() {
        given:
        List<TalkData> talks = [null as TalkData]

        when:
        calculateTotalTimeOf talks

        then:
        thrown NullPointerException
    }
}
