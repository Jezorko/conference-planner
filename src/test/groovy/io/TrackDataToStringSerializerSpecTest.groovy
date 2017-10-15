package io

import core.TalkData
import core.TrackData
import spock.lang.Specification

class TrackDataToStringSerializerSpecTest extends Specification {

    def serializer = new TrackDataToStringSerializer()

    def "should serialize input as in specification"() {
        given:
        def input = new TrackData([new TalkData("Communicating Over Distance", 60),
                                   new TalkData("Rails Magic", 60),
                                   new TalkData("Woah", 30),
                                   new TalkData("Sit Down and Write", 30)],
                                  [new TalkData("Accounting-Driven Development", 45),
                                   new TalkData("Clojure Ate Scala (on my project)", 45),
                                   new TalkData("A World Without HackerNews", 30),
                                   new TalkData("Ruby on Rails Legacy App Maintenance", 60),
                                   new TalkData("Rails for Python Developers", 5)])

        and:
        def expectedOutput = 'Track 1\n' +
                '09:00AM Communicating Over Distance 60min\n' +
                '10:00AM Rails Magic 60min\n' +
                '11:00AM Woah 30min\n' +
                '11:30AM Sit Down and Write 30min\n' +
                '12:00PM Lunch\n' +
                '01:00PM Accounting-Driven Development 45min\n' +
                '01:45PM Clojure Ate Scala (on my project) 45min\n' +
                '02:30PM A World Without HackerNews 30min\n' +
                '03:00PM Ruby on Rails Legacy App Maintenance 60min\n' +
                '04:00PM Rails for Python Developers lightning\n' +
                '05:00PM Networking Event'

        when:
        def output = serializer.serialize(input, 1)

        then:
        expectedOutput == output
    }

    def "should throw if input is null"() {
        given:
        TrackData input = null

        when:
        serializer.serialize(input, 1)

        then:
        thrown Exception
    }

    def "should throw if #field session talks are null"() {
        given:
        def input = new TrackData(morningSessionTalks, afternoonSessionTalks)

        when:
        serializer.serialize(input, 1)

        then:
        thrown Exception

        where:
        field       | morningSessionTalks | afternoonSessionTalks
        "morning"   | null as List        | []
        "afternoon" | []                  | null as List
        "both"      | null as List        | null as List
    }
}
