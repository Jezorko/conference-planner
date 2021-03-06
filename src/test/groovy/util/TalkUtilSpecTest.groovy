package util

import dto.Talk
import spock.lang.Specification

import static org.apache.commons.lang3.RandomUtils.nextInt
import static util.TalkUtil.TIME_DESCENDING_COMPARATOR
import static util.TalkUtil.calculateTotalTimeOf

class TalkUtilSpecTest extends Specification {

    def "should sort in a descending order by the length"() {
        given:
        def dataSize = 10

        and: "a couple objects in an array"
        def talksData = new Talk[dataSize]
        (0..dataSize - 1).each {
            talksData[it] = new Talk("example", nextInt(5, 60))
        }

        when: "array is sorted"
        talksData.sort(TIME_DESCENDING_COMPARATOR)

        then: "each talks' length should be greater than or equal to the consequent length"
        (1..dataSize - 1).each {
            assert talksData[it - 1].lengthInMinutes >= talksData[it].lengthInMinutes
        }
    }

    def "should calculate the total time of talks"() {
        given:
        def talks = [
                new Talk("example", 5),
                new Talk("example", 10)
        ]

        when:
        def result = calculateTotalTimeOf talks

        then:
        15 == result
    }

    def "should return 0 if the list of talks is empty"() {
        given:
        List<Talk> talks = []

        when:
        def result = calculateTotalTimeOf talks

        then:
        0 == result
    }

    def "should throw if the list contains a null object"() {
        given:
        List<Talk> talks = [null as Talk]

        when:
        calculateTotalTimeOf talks

        then:
        thrown NullPointerException
    }
}
