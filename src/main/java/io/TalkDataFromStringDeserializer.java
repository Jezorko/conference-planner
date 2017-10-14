package io;

import core.TalkData;
import org.apache.commons.validator.routines.RegexValidator;

import static io.vavr.control.Option.of;
import static java.lang.Integer.valueOf;

public class TalkDataFromStringDeserializer {

    private static final String TALK_DATA_SPECIFICATION_PATTERN = "^\\D+ ((\\d+min)|(lightning))$";

    private final RegexValidator talkDataValidator = new RegexValidator(TALK_DATA_SPECIFICATION_PATTERN);

    public TalkData deserialize(String line) {
        return of(line).filter(talkDataValidator::isValid)
                       .map(this::extractFromValidString)
                       .getOrElseThrow(IllegalArgumentException::new);
    }

    private TalkData extractFromValidString(String line) {
        String name = line.replaceAll(" ((\\d+min)|(lightning))", "");
        return new TalkData(name, line.endsWith("lightning") ? 5 : valueOf(line.replaceAll("\\D", "")));
    }
}
