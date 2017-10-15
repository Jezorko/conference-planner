package io

import core.TalkData
import core.TrackData
import spock.lang.Specification
import spock.lang.Unroll

class TrackDataToStringSerializerSpecTest extends Specification {

    def serializer = new TrackDataToStringSerializer()

    def "should serialize input as in specification"() {
        when:
        def output = serializer.serialize(input, trackIndex)

        then:
        expectedOutput == output

        where:
        input                                                                 | trackIndex || expectedOutput
        new TrackData([new TalkData("Writing Fast Tests Against Enterprise Rails", 60),
                       new TalkData("Overdoing it in Python", 45),
                       new TalkData("Lua for the Masses", 30),
                       new TalkData("Ruby Errors from Mismatched Gem Versions", 45)],
                      [new TalkData("Ruby on Rails: Why We Should Move On", 60),
                       new TalkData("Common Ruby Errors", 45),
                       new TalkData("Pair Programming vs Noise", 45),
                       new TalkData("Programming in the Boondocks of Seattle", 30),
                       new TalkData("Ruby vs. Clojure for Back-End Development", 30),
                       new TalkData("User Interface CSS in Rails Apps", 30)]) |
                1                                                                          ||
                'Track 1\n' +
                '09:00AM Writing Fast Tests Against Enterprise Rails 60min\n' +
                '10:00AM Overdoing it in Python 45min\n' +
                '10:45AM Lua for the Masses 30min\n' +
                '11:15AM Ruby Errors from Mismatched Gem Versions 45min\n' +
                '12:00PM Lunch\n' +
                '01:00PM Ruby on Rails: Why We Should Move On 60min\n' +
                '02:00PM Common Ruby Errors 45min\n' +
                '02:45PM Pair Programming vs Noise 45min\n' +
                '03:30PM Programming in the Boondocks of Seattle 30min\n' +
                '04:00PM Ruby vs. Clojure for Back-End Development 30min\n' +
                '04:30PM User Interface CSS in Rails Apps 30min\n' +
                '05:00PM Networking Event'

        new TrackData([new TalkData("Communicating Over Distance", 60),
                       new TalkData("Rails Magic", 60),
                       new TalkData("Woah", 30),
                       new TalkData("Sit Down and Write", 30)],
                      [new TalkData("Accounting-Driven Development", 45),
                       new TalkData("Clojure Ate Scala (on my project)", 45),
                       new TalkData("A World Without HackerNews", 30),
                       new TalkData("Ruby on Rails Legacy App Maintenance", 60),
                       new TalkData("Rails for Python Developers", 5)])       |
                2                                                                          ||
                'Track 2\n' +
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
                '04:05PM Networking Event'
    }

    def "should throw if input is null"() {
        given:
        TrackData input = null

        when:
        serializer.serialize(input, 1)

        then:
        thrown Exception
    }

    @Unroll
    "should throw if #field session talks are null"() {
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
