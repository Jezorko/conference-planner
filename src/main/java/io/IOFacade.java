package io;

import dto.Talk;
import dto.Track;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.List;

import static java.nio.charset.Charset.defaultCharset;

public class IOFacade {
    @SneakyThrows
    public List<Talk> readAllFromFile(String pathToFile) {
        List<String> lines = FileUtils.readLines(new File(pathToFile), defaultCharset());
        return new TalkDataReader(new TalkDataFromStringDeserializer()).readAllFrom(lines.iterator());
    }

    public void writeToConsole(List<Track> conferenceData) {
        new TrackDataWriter(new TrackDataToStringSerializer()).writeTo(System.out, conferenceData);
    }
}
