package io

import dto.Talk
import dto.Track
import spock.lang.Specification
import spock.lang.Unroll

import static System.getProperty

class TrackToStringSerializerSpecTest extends Specification {

    static LINE_SEPARATOR = getProperty("line.separator")
    def serializer = new TrackDataToStringSerializer()

    def "should serialize input as in specification"() {
        when:
        def output = serializer.serialize(input, trackIndex)

        then:
        expectedOutput == output

        where:
        input                                                             | trackIndex || expectedOutput
        new Track([new Talk("Writing Fast Tests Against Enterprise Rails", 60),
                   new Talk("Overdoing it in Python", 45),
                   new Talk("Lua for the Masses", 30),
                   new Talk("Ruby Errors from Mismatched Gem Versions", 45)],
                  [new Talk("Ruby on Rails: Why We Should Move On", 60),
                       new Talk("Common Ruby Errors", 45),
                       new Talk("Pair Programming vs Noise", 45),
                       new Talk("Programming in the Boondocks of Seattle", 30),
                       new Talk("Ruby vs. Clojure for Back-End Development", 30),
                       new Talk("User Interface CSS in Rails Apps", 30)]) |
                1                                                                          ||
                'Track 1' + LINE_SEPARATOR +
                '09:00AM Writing Fast Tests Against Enterprise Rails 60min' + LINE_SEPARATOR +
                '10:00AM Overdoing it in Python 45min' + LINE_SEPARATOR +
                '10:45AM Lua for the Masses 30min' + LINE_SEPARATOR +
                '11:15AM Ruby Errors from Mismatched Gem Versions 45min' + LINE_SEPARATOR +
                '12:00PM Lunch' + LINE_SEPARATOR +
                '01:00PM Ruby on Rails: Why We Should Move On 60min' + LINE_SEPARATOR +
                '02:00PM Common Ruby Errors 45min' + LINE_SEPARATOR +
                '02:45PM Pair Programming vs Noise 45min' + LINE_SEPARATOR +
                '03:30PM Programming in the Boondocks of Seattle 30min' + LINE_SEPARATOR +
                '04:00PM Ruby vs. Clojure for Back-End Development 30min' + LINE_SEPARATOR +
                '04:30PM User Interface CSS in Rails Apps 30min' + LINE_SEPARATOR +
                '05:00PM Networking Event'

        new Track([new Talk("Communicating Over Distance", 60),
                   new Talk("Rails Magic", 60),
                   new Talk("Woah", 30),
                   new Talk("Sit Down and Write", 30)],
                  [new Talk("Accounting-Driven Development", 45),
                       new Talk("Clojure Ate Scala (on my project)", 45),
                       new Talk("A World Without HackerNews", 30),
                       new Talk("Ruby on Rails Legacy App Maintenance", 60),
                       new Talk("Rails for Python Developers", 5)])       |
                2                                                                          ||
                'Track 2' + LINE_SEPARATOR +
                '09:00AM Communicating Over Distance 60min' + LINE_SEPARATOR +
                '10:00AM Rails Magic 60min' + LINE_SEPARATOR +
                '11:00AM Woah 30min' + LINE_SEPARATOR +
                '11:30AM Sit Down and Write 30min' + LINE_SEPARATOR +
                '12:00PM Lunch' + LINE_SEPARATOR +
                '01:00PM Accounting-Driven Development 45min' + LINE_SEPARATOR +
                '01:45PM Clojure Ate Scala (on my project) 45min' + LINE_SEPARATOR +
                '02:30PM A World Without HackerNews 30min' + LINE_SEPARATOR +
                '03:00PM Ruby on Rails Legacy App Maintenance 60min' + LINE_SEPARATOR +
                '04:00PM Rails for Python Developers lightning' + LINE_SEPARATOR +
                '04:05PM Networking Event'
    }

    def "should put networking event at 4PM even if afternoon talks finish earlier"() {
        when:
        def output = serializer.serialize(input, trackIndex)

        then:
        expectedOutput == output

        where:
        input                                               | trackIndex || expectedOutput
        new Track([new Talk("Writing Fast Tests Against Enterprise Rails", 60),
                   new Talk("Overdoing it in Python", 45),
                   new Talk("Lua for the Masses", 30),
                   new Talk("Ruby Errors from Mismatched Gem Versions", 45)],
                  [new Talk("Ruby on Rails: Why We Should Move On", 60),
                       new Talk("Common Ruby Errors", 45)]) |
                1                                                            ||
                'Track 1' + LINE_SEPARATOR +
                '09:00AM Writing Fast Tests Against Enterprise Rails 60min' + LINE_SEPARATOR +
                '10:00AM Overdoing it in Python 45min' + LINE_SEPARATOR +
                '10:45AM Lua for the Masses 30min' + LINE_SEPARATOR +
                '11:15AM Ruby Errors from Mismatched Gem Versions 45min' + LINE_SEPARATOR +
                '12:00PM Lunch' + LINE_SEPARATOR +
                '01:00PM Ruby on Rails: Why We Should Move On 60min' + LINE_SEPARATOR +
                '02:00PM Common Ruby Errors 45min' + LINE_SEPARATOR +
                '04:00PM Networking Event'
    }

    def "should throw if input is null"() {
        given:
        Track input = null

        when:
        serializer.serialize(input, 1)

        then:
        thrown Exception
    }

    @Unroll
    "should throw if #field session talks are null"() {
        given:
        def input = new Track(morningSessionTalks, afternoonSessionTalks)

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
