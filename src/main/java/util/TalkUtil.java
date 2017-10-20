package util;

import dto.Talk;

import java.util.Comparator;
import java.util.List;

import static java.lang.Integer.compare;

public class TalkUtil {

    public final static Comparator<Talk> TIME_DESCENDING_COMPARATOR = (a, b) -> compare(b.getLengthInMinutes(), a.getLengthInMinutes());

    public static int calculateTotalTimeOf(List<Talk> talks) {
        return talks.stream()
                    .map(Talk::getLengthInMinutes)
                    .reduce(Integer::sum)
                    .orElse(0);
    }
}
