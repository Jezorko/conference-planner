package io

import dto.Talk
import spock.lang.Specification

class TalkReaderSpecTest extends Specification {

    def deserializer = Mock TalkDataFromStringDeserializer
    def reader = new TalkDataReader(deserializer)

    def "should drain the data source and deserialize each line using the deserializer"() {
        given:
        def dataSource = Mock Iterator
        def firstDataEntry = "example 10min"
        def firstDeserializedEntry = new Talk("example", 10)
        def secondDataEntry = "example lightning"
        def secondDeserializedEntry = new Talk("example", 5)

        when:
        def result = reader.readAllFrom dataSource

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
        reader.readAllFrom dataSource

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
        reader.readAllFrom dataSource

        then:
        thrown Exception
    }
}
