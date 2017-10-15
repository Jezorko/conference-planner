package io

import core.TrackData
import spock.lang.Specification

class TrackDataWriterSpecTest extends Specification {

    def serializer = Mock TrackDataToStringSerializer
    def writer = new TrackDataWriter(serializer)

    def "should serialize each TrackData using serializer and write it to the stream"() {
        given:
        def outputStream = Mock PrintStream

        and:
        def firstTrackData = new TrackData(null, null)
        def secondTrackData = new TrackData(null, null)
        def input = [firstTrackData, secondTrackData]

        when:
        writer.writeTo(outputStream, input)

        then: "first track data should be serialized and printed"
        1 * serializer.serialize(firstTrackData, 1) >> "serializedFirst"
        1 * outputStream.println("serializedFirst")

        and: "second track data should be serialized and printed"
        1 * serializer.serialize(secondTrackData, 2) >> "serializedSecond"
        1 * outputStream.println("serializedSecond")
    }

    def "should rethrow the exception thrown by the serializer"() {
        given:
        def outputStream = Mock PrintStream

        and:
        def trackData = new TrackData(null, null)
        def input = [trackData]

        and:
        def serializerException = new IllegalArgumentException()

        when:
        writer.writeTo(outputStream, input)

        then: "first track data should be serialized"
        1 * serializer.serialize(trackData, 1) >> { throw serializerException }

        and: "no other action should be performed"
        0 * _._

        and: "exception thrown by the serializer should be rethrown"
        def actualException = thrown(IllegalArgumentException)
        serializerException == actualException
    }

    def "should throw if output stream is null"() {
        when:
        writer.writeTo(null, [])

        then:
        thrown IllegalArgumentException
    }

    def "should throw if input is null"() {
        when:
        writer.writeTo(Mock(PrintStream), null as List)

        then:
        thrown IllegalArgumentException
    }
}
