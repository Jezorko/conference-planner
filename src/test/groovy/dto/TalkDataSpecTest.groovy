package dto

import spock.lang.Specification

import static dto.TalkData.TIME_DESCENDING_COMPARATOR
import static org.apache.commons.lang3.RandomUtils.nextInt

class TalkDataSpecTest extends Specification {
    def "should be sorted in a descending order by the length"() {
        given:
        def dataSize = 10

        and: "a couple objects in an array"
        def talksData = new TalkData[dataSize]
        (0..dataSize - 1).each {
            talksData[it] = new TalkData("example", nextInt(5, 60))
        }

        when: "array is sorted"
        talksData.sort(TIME_DESCENDING_COMPARATOR)

        then: "each talks' length should be greater than or equal to the consequent length"
        (1..dataSize - 1).each {
            assert talksData[it - 1].lengthInMinutes >= talksData[it].lengthInMinutes
        }
    }
}
