package dto;

import lombok.Value;

import java.util.List;

@Value
public class TrackData {
    private final List<TalkData> morningSessionTalks;
    private final List<TalkData> afternoonSessionTalks;
}
