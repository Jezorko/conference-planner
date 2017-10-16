package core

import dto.TalkData
import spock.lang.Specification
import spock.lang.Unroll

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic
import static org.apache.commons.lang3.RandomUtils.nextInt

class ConferenceTracksAssigningAlgorithmFactorySpecTest extends Specification {

    def factory = new ConferenceTracksAssigningAlgorithmFactory()

    @Unroll
    "should always be able to separate Talks to Tracks with the safe algorithm (testcase ID = #testcase)"() {
        given:
        def safeAlgorithm = factory.getSafeAlgorithm()

        and:
        def testTalksAmount = testcase + 20
        def inputTalks = generateTalks testTalksAmount

        when:
        def result = safeAlgorithm.assignToTracks inputTalks

        then:
        noExceptionThrown()

        and: "no talk data is lost"
        def resultTalks = []
        result.forEach {
            resultTalks.addAll(it.morningSessionTalks)
            resultTalks.addAll(it.afternoonSessionTalks)
        }
        resultTalks.containsAll inputTalks

        and: "each track contains at least one talk in each session"
        result.forEach {
            assert it.morningSessionTalks.size() >= 1
            assert it.afternoonSessionTalks.size() >= 1
        }

        where:
        testcase << (0..50)
    }

    def generateTalks(int amount) {
        def talks = []
        (0..amount).each {
            talks.add(new TalkData(randomAlphabetic(10), nextInt(5, 60)))
        }
        return talks
    }
}
