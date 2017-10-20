package dto;

import lombok.Value;

@Value
public class Talk {
    private final String name;
    private final int lengthInMinutes;
}
