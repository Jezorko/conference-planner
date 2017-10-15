package core

import spock.lang.Specification

import static java.lang.Integer.MAX_VALUE

class ConferenceTracksAssigningAlgorithmSpecTest extends Specification {

    static SPECIFICATION_TALKS = [
            new TalkData("Writing Fast Tests Against Enterprise Rails", 60),
            new TalkData("Overdoing it in Python", 45),
            new TalkData("Lua for the Masses", 30),
            new TalkData("Ruby Errors from Mismatched Gem Versions", 45),
            new TalkData("Common Ruby Errors", 45),
            new TalkData("Rails for Python Developers", 5),
            new TalkData("Communicating Over Distance", 60),
            new TalkData("Accounting-Driven Development", 45),
            new TalkData("Woah", 30),
            new TalkData("Sit Down and Write", 30),
            new TalkData("Pair Programming vs Noise", 45),
            new TalkData("Rails Magic", 60),
            new TalkData("Ruby on Rails: Why We Should Move On", 60),
            new TalkData("Clojure Ate Scala (on my project)", 45),
            new TalkData("Programming in the Boondocks of Seattle", 30),
            new TalkData("Ruby vs. Clojure for Back-End Development", 30),
            new TalkData("Ruby on Rails Legacy App Maintenance", 60),
            new TalkData("A World Without HackerNews", 30),
            new TalkData("User Interface CSS in Rails Apps", 30)
    ]

    def algorithm = new ConferenceTracksAssigningAlgorithm()

    def "should generate optimal solution"() {
        given:
        def morningSessionMaximumTimeInMinutes = 3 * 60
        def afternoonSessionMaximumTimeInMinutes = 4 * 60

        when:
        def tracks = algorithm.assignToTracks SPECIFICATION_TALKS

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
