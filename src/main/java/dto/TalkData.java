package dto;

import lombok.Value;

import java.util.Comparator;

import static java.lang.Integer.valueOf;

@Value
public class TalkData {
    public final static Comparator<TalkData> TIME_DESCENDING_COMPARATOR = (first, second) -> valueOf(second.lengthInMinutes).compareTo(first.lengthInMinutes);

    private final String name;
    private final int lengthInMinutes;
}
