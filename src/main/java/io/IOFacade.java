package io;

import dto.TalkData;
import dto.TrackData;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.List;

import static java.nio.charset.Charset.defaultCharset;

public class IOFacade {
    @SneakyThrows
    public List<TalkData> readAllFromFile(String pathToFile) {
        List<String> lines = FileUtils.readLines(new File(pathToFile), defaultCharset());
        return new TalkDataReader(new TalkDataFromStringDeserializer()).readAllFrom(lines.iterator());
    }

    public void writeToConsole(List<TrackData> conferenceData) {
        new TrackDataWriter(new TrackDataToStringSerializer()).writeTo(System.out, conferenceData);
    }
}
