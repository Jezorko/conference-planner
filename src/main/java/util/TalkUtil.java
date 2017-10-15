package util;

import dto.TalkData;

import java.util.List;

public class TalkUtil {
    public static int calculateTotalTimeOf(List<TalkData> talks) {
        return talks.stream()
                    .map(TalkData::getLengthInMinutes)
                    .reduce(Integer::sum)
                    .orElse(0);
    }
}
