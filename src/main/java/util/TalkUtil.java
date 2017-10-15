package util;

import dto.TalkData;

import java.util.Comparator;
import java.util.List;

import static java.lang.Integer.valueOf;

public class TalkUtil {

    public final static Comparator<TalkData> TIME_DESCENDING_COMPARATOR = (a, b) -> valueOf(b.getLengthInMinutes()).compareTo(a.getLengthInMinutes());

    public static int calculateTotalTimeOf(List<TalkData> talks) {
        return talks.stream()
                    .map(TalkData::getLengthInMinutes)
                    .reduce(Integer::sum)
                    .orElse(0);
    }
}
