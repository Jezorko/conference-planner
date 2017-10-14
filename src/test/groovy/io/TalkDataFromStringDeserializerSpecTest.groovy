package io

import core.TalkData
import spock.lang.Specification
import spock.lang.Unroll

class TalkDataFromStringDeserializerSpecTest extends Specification {

    def deserializer = new TalkDataFromStringDeserializer()

    @Unroll
    "should correctly deserialize talk data='#input' to a TalkData object=#expectedResult"() {
        when:
        def actualResult = deserializer.deserialize input

        then:
        noExceptionThrown()

        and:
        expectedResult == actualResult

        where:
        input                              || expectedResult
        "example 60min"                    || new TalkData("example", 60)
        "example lightning"                || new TalkData("example", 5)
        "example multiple words 10min"     || new TalkData("example multiple words", 10)
        "example multiple words lightning" || new TalkData("example multiple words", 5)
    }

    def "should throw if string provided for deserialization is null"() {
        given:
        String input = null

        when:
        deserializer.deserialize input

        then:
        thrown Exception
    }

    @Unroll
    "should throw if data does not comply with the specification format (#input)"() {
        when:
        deserializer.deserialize input

        then:
        thrown Exception

        where:
        input << [
                "only the talks' title is present",
                "the time is neither lightning nor in minutes, for instance 1h",
                "the title has numbers like 5 in it but a valid time like 10min",
                "the talk's time is negative like -10min"
        ]
    }
}
