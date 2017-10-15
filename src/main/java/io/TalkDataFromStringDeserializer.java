package io;

import dto.TalkData;
import org.apache.commons.validator.routines.RegexValidator;

import static io.vavr.control.Option.of;
import static java.lang.Integer.valueOf;
import static util.SpecificationConstants.*;

public class TalkDataFromStringDeserializer {

    private static final String EMPTY_STRING = "";

    private static final int QUICK_TALK_TIME = 5;

    private final RegexValidator talkDataValidator = new RegexValidator(TALK_DATA_PATTERN);

    public TalkData deserialize(String line) {
        return of(line).filter(talkDataValidator::isValid)
                       .map(this::extractFromValidString)
                       .getOrElseThrow(this::dataInvalidException);
    }

    private TalkData extractFromValidString(String line) {
        return new TalkData(extractTitleFrom(line), extractTimeFrom(line));
    }

    private String extractTitleFrom(String line) {
        return line.replaceAll(TALK_DATA_TIME_PATTERN, EMPTY_STRING);
    }

    private int extractTimeFrom(String line) {
        return line.endsWith(QUICK_TALK_TIME_PATTERN) ? QUICK_TALK_TIME : valueOf(line.replaceAll(TALK_DATA_TITLE_PATTERN, EMPTY_STRING));
    }

    private RuntimeException dataInvalidException() {
        return new IllegalArgumentException("Data does not match the pattern '" + TALK_DATA_PATTERN + "'");
    }
}
