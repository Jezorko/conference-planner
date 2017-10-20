package io;

import dto.Talk;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
class TalkDataReader {

    private final TalkDataFromStringDeserializer deserializer;

    List<Talk> readAllFrom(Iterator<String> dataSource) {
        final List<Talk> result = new ArrayList<>();
        while (dataSource.hasNext()) {
            result.add(deserializer.deserialize(dataSource.next()));
        }
        return result;
    }

}
