package io;

import core.TalkData;
import lombok.RequiredArgsConstructor;

import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
public class TalkDataParser {

    private final TalkDataFromStringDeserializer deserializer;

    public List<TalkData> parseAllFrom(Iterator<String> dataSource) {
        return null;
    }

}
