package core;

import lombok.Value;

import static java.lang.Integer.valueOf;

@Value
public class TalkData implements Comparable<TalkData> {
    private final String name;
    private final int lengthInMinutes;

    @Override
    public int compareTo(TalkData other) {
        return valueOf(other.lengthInMinutes).compareTo(this.lengthInMinutes);
    }
}
