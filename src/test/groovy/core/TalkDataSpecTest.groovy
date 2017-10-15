package core

import spock.lang.Specification

import static java.util.Arrays.sort
import static org.apache.commons.lang3.RandomUtils.nextInt

class TalkDataSpecTest extends Specification {
    def "should be sorted in a descending order by the length"() {
        given:
        def dataSize = 10

        and: "a couple objects in an array"
        def talkDataArray = new TalkData[dataSize]
        (0..dataSize - 1).each {
            talkDataArray[it] = new TalkData("example", nextInt(5, 60))
        }

        when: "array is sorted"
        sort(talkDataArray)

        then: "each talks' length should be greater than or equal to the consequent length"
        (1..dataSize - 1).each {
            assert talkDataArray[it - 1].lengthInMinutes >= talkDataArray[it].lengthInMinutes
        }
    }
}
