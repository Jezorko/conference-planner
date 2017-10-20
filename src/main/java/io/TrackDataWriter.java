package io;

import dto.Track;
import lombok.RequiredArgsConstructor;

import java.io.PrintStream;
import java.util.List;

import static io.vavr.Tuple.of;
import static java.util.stream.IntStream.range;

@RequiredArgsConstructor
class TrackDataWriter {

    private final TrackDataToStringSerializer serializer;

    void writeTo(PrintStream printStream, List<Track> conferenceData) {
        if (printStream == null || conferenceData == null) {
            throw new IllegalArgumentException("Input may not be null");
        }
        range(0, conferenceData.size()).mapToObj(index -> of(conferenceData.get(index), index + 1))
                                       .map(serializer::serialize)
                                       .forEach(printStream::println);
    }
}
