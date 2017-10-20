package dto;

import lombok.Value;

import java.util.List;

@Value
public class Track {
    private final List<Talk> morningSessionTalks;
    private final List<Talk> afternoonSessionTalks;
}
