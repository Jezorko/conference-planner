package io;

import core.TalkData;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
public class TalkDataReader {

    private final TalkDataFromStringDeserializer deserializer;

    public List<TalkData> readAllFrom(Iterator<String> dataSource) {
        final List<TalkData> result = new ArrayList<>();
        while (dataSource.hasNext()) {
            result.add(deserializer.deserialize(dataSource.next()));
        }
        return result;
    }

}
