package io;

import core.TrackData;
import lombok.RequiredArgsConstructor;

import java.io.PrintStream;
import java.util.List;

@RequiredArgsConstructor
public class TrackDataWriter {

    private final TrackDataToStringSerializer serializer;

    public void writeTo(PrintStream printStream, List<TrackData> conferenceData) {
        // TODO: provide implementation
    }
}
