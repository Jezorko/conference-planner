package io;

import core.TalkData;
import org.apache.commons.validator.routines.RegexValidator;

import static io.vavr.control.Option.of;
import static java.lang.Integer.valueOf;
import static org.apache.commons.lang3.StringUtils.split;

public class TalkDataFromStringDeserializer {

    private static final String TALK_DATA_SPECIFICATION_PATTERN = "^\\D+ ((\\d+min)|(lightning))$";

    private final RegexValidator talkDataValidator = new RegexValidator(TALK_DATA_SPECIFICATION_PATTERN);

    public TalkData deserialize(String line) {
        return of(line).filter(talkDataValidator::isValid)
                       .map(this::extractFromValidString)
                       .getOrElseThrow(IllegalArgumentException::new);
    }

    private TalkData extractFromValidString(String line) {
        String[] lineParts = split(line);
        StringBuilder name = new StringBuilder();
        for (int i = 0; i < lineParts.length - 1; i++) {
            name.append(lineParts[i]);
            if (i < lineParts.length - 2) {
                name.append(" ");
            }
        }
        final String timePart = lineParts[lineParts.length - 1];
        int timeInMinutes = ("lightning".equals(timePart) ? 5 : valueOf(timePart.substring(0, timePart.indexOf("min"))));
        return new TalkData(name.toString(), timeInMinutes);
    }
}
