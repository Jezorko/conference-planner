package dto;

import lombok.Value;

@Value
public class TalkData {
    private final String name;
    private final int lengthInMinutes;
}
