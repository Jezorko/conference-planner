package io

import core.TalkData
import spock.lang.Specification

class TalkDataParserSpecTest extends Specification {

    def deserializer = Mock TalkDataFromStringDeserializer
    def parser = new TalkDataParser(deserializer)

    def "should drain the data source and deserialize each line using the deserializer"() {
        given:
        def dataSource = Mock Iterator
        def firstDataEntry = "example 10min"
        def firstDeserializedEntry = new TalkData("example", 10)
        def secondDataEntry = "example lightning"
        def secondDeserializedEntry = new TalkData("example", 5)

        when:
        def result = parser.parseAllFrom dataSource

        then: "the first data entry is parsed"
        1 * dataSource.hasNext() >> true
        1 * dataSource.next() >> firstDataEntry
        1 * deserializer.deserialize(firstDataEntry) >> firstDeserializedEntry

        and: "the second data entry is parsed"
        1 * dataSource.hasNext() >> true
        1 * dataSource.next() >> secondDataEntry
        1 * deserializer.deserialize(secondDataEntry) >> secondDeserializedEntry

        and: "after the data source is drained, the result is returned"
        1 * dataSource.hasNext() >> false
        0 * deserializer._

        and: "the result contains two deserialized entries"
        result.containsAll([firstDeserializedEntry, secondDeserializedEntry])
    }

    def "should rethrow the exception thrown by the deserializer"() {
        given:
        def dataSource = Mock Iterator
        def firstDataEntry = "some invalid data"
        def deserializerException = new IllegalArgumentException()

        when:
        parser.parseAllFrom dataSource

        then: "the first data entry is retrieved"
        1 * dataSource.hasNext() >> true
        1 * dataSource.next() >> firstDataEntry

        and: "deserializer throws an exception"
        1 * deserializer.deserialize(firstDataEntry) >> { throw deserializerException }

        and: "no other actions are performed"
        0 * dataSource._
        0 * deserializer._

        and: "exception thrown by the deserializer is rethrown"
        def actualException = thrown(IllegalArgumentException)
        deserializerException == actualException
    }

    def "should throw if the data source is null"() {
        given:
        Iterator<String> dataSource = null

        when:
        parser.parseAllFrom dataSource

        then:
        thrown Exception
    }
}
